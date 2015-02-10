package lt.buzzard.engine;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.buzzard.connection.Connection;
import lt.buzzard.connection.PacketRefactorer;
import lt.buzzard.entities.Block;
import lt.buzzard.entities.Grid;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Game implements Runnable {

    public static int width = 800;
    public static int height = 480;
    private int mouseX, mouseY;

    private boolean isRunning;
    public static boolean regenerateTable;
    private Thread thread;
    private Input input;
    private Grid blockGrid;
    private BlockRenderer blockGridRenderer;
    private BlockRenderer uiblockRenderer;
    private List<Block> UIBlocks;
    private Map<String, String> texturePaths;
    private static Connection connection;
    public Date date;
    public static TextureManager texturePack;
    public static UnicodeFont fontA, fontB, fontC;
    public static final int BLOCK_SIZE = 16;
    public static int playerID = 0;
    private static int GRID_WIDTH = 37, GRID_HEIGHT = 31, SIGNS2WIN = 5;
    private static final String PAPER_PLAIN = "/paper_plain.png";
    private static final String PAPER_X = "/paper_x.png";
    private static final String PAPER_O = "/paper_o.png";
    private static final String UI_BG = "/ui_bg.png";
    private static final String ERASER = "/eraser.png";
    private boolean oponentCleansTable;

    public Game(int grid_width, int grid_height, int signs2win, int playerID, Connection connection) {
        GRID_WIDTH = grid_width + 1;
        GRID_HEIGHT = grid_height + 1;
        SIGNS2WIN = signs2win;
        width = GRID_WIDTH * BLOCK_SIZE + 210;
        if (grid_height * BLOCK_SIZE > 300) {
            height = grid_height * BLOCK_SIZE;
        }
        Game.playerID = playerID;
        Game.connection = connection;
        texturePaths = new HashMap<String, String>();
        blockGrid = new Grid(GRID_WIDTH, GRID_HEIGHT, SIGNS2WIN);
        blockGridRenderer = new BlockRenderer();
        uiblockRenderer = new BlockRenderer();
        refactory = new PacketRefactorer();
        input = new Input(playerID, connection, refactory);
        date = new Date();
    }

    public Game(int grid_width, int grid_height, int signs2win) {
        GRID_WIDTH = grid_width + 1;
        GRID_HEIGHT = grid_height + 1;
        SIGNS2WIN = signs2win;
        width = GRID_WIDTH * BLOCK_SIZE + 210;
        if (grid_height * BLOCK_SIZE > 300) {
            height = grid_height * BLOCK_SIZE;
        }
        texturePaths = new HashMap<String, String>();
        blockGrid = new Grid(GRID_WIDTH, GRID_HEIGHT, SIGNS2WIN);
        blockGridRenderer = new BlockRenderer();
        uiblockRenderer = new BlockRenderer();
        input = new Input();
        date = new Date();
    }

    private Block background;
    private Block uibgBlock;
    private Block eraserButton;

    @SuppressWarnings("unchecked")
    public void init() {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            if (connection == null) {
                Display.setTitle("octoX v1.1.0");
            } else {
                Display.setTitle("octoX v1.1.0 [" + connection.toString() + "]");
            }

            Display.create();

            glMatrixMode(GL_PROJECTION);
            glOrtho(0, width, height, 0, 0, 1.0f);
            glMatrixMode(GL_MODELVIEW);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            // Font init
            fontA = new UnicodeFont(new Font("Calibri", Font.BOLD, 15));
            fontA.getEffects().add(new ColorEffect(Color.GRAY));
            fontA.addAsciiGlyphs();
            fontB = new UnicodeFont(new Font("Calibri", Font.BOLD, 30));
            fontB.getEffects().add(new ColorEffect(Color.darkGray));
            fontB.addAsciiGlyphs();
            fontC = new UnicodeFont(new Font("Calibri", Font.BOLD, 12));
            fontC.getEffects().add(new ColorEffect(new Color(115, 3, 22)));
            fontC.addAsciiGlyphs();
            try {
                fontA.loadGlyphs();
                fontB.loadGlyphs();
                fontC.loadGlyphs();
            } catch (SlickException e) {
                e.printStackTrace();
            }

            // Texture init
            texturePaths.put("paper_plain", PAPER_PLAIN);
            texturePaths.put("paper_x", PAPER_X);
            texturePaths.put("paper_o", PAPER_O);
            texturePaths.put("ui_bg", UI_BG);
            texturePaths.put("eraser", ERASER);
            texturePack = new TextureManager(texturePaths);

            UIBlocks = new ArrayList<Block>();
            uibgBlock = new Block(BLOCK_SIZE * (GRID_WIDTH - 1), 0, width - BLOCK_SIZE
                    * (GRID_WIDTH - 1), height, TextureManager.getTexture("ui_bg"));
            background = new Block(0, 0, BLOCK_SIZE * GRID_WIDTH, height);
            float[] bgColor = { 0.9f, 0.9f, 0.6f };
            background.setColor(bgColor);
            eraserButton = new Block(BLOCK_SIZE * (GRID_WIDTH - 1) + 60, height - 100, 100, 60,
                    TextureManager.getTexture("eraser"));
            UIBlocks.add(background);
            UIBlocks.add(uibgBlock);
            UIBlocks.add(eraserButton);
            uiblockRenderer.setBlockToRender(UIBlocks);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        isRunning = true;
        regenerateTable = true;
        thread = new Thread(this, "Display");
        if (connection != null) {
            connection.connect();
            oponentCleansTable = true;
        }
        thread.run();
    }

    @Override
    public void run() {
        init();
        try {
            while (isRunning) {
                update();
                render();
                check();

                Display.update();
                Display.sync(60);
            }
            if (connection != null) {
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Display.destroy();
    }

    public int fps = 0;
    private int fpsTicks = 0;
    private long lastFrame = 0;
    private long lastFPS = System.nanoTime() / 1000000;

    public void fpsUpdate() {
        if (System.nanoTime() / 1000000 - lastFPS > 1000) {
            fps = fpsTicks;
            fpsTicks = 0;
            lastFPS += 1000;
        }
        fpsTicks++;
    }

    public int getDelta() {
        long time = System.nanoTime() / 1000000;
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public void update() throws IOException {
        fpsUpdate();
        mouseX = Mouse.getX();
        mouseY = height - Mouse.getY() - 1;
        date = Calendar.getInstance().getTime();
        if (connection != null) {
            managePackets();
        }
    }

    @SuppressWarnings("deprecation")
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glColor3f(1f, 1f, 1f);

        uiblockRenderer.draw();
        blockGridRenderer.draw();

        fontA.drawString(uibgBlock.getX() + 165, uibgBlock.getY() + 5, "FPS : " + fps);
        fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 5, "Mouse X : " + mouseX);
        fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 20, "Mouse Y : " + mouseY);
        fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 45, date.toLocaleString());

        if (!blockGrid.getWinLogic().isGameMode()) {
            fontB.drawString(uibgBlock.getX() + 3, uibgBlock.getHeight() / 2 + 55, "THE WINNER IS "
                    + blockGrid.getWinLogic().getWinner());
            fontC.drawString(uibgBlock.getX() + 50, uibgBlock.getHeight() / 2 + 90,
                    "CLICK ERASER TO RESTART");
        }

        // Timer
        fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 162, "Match time :");
        fontB.drawString(uibgBlock.getX() + 100, uibgBlock.getY() + 145, matchOverAllTimeMins + ":"
                + matchOverAllTimeSeconds);
        fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 192, "X time :");
        fontB.drawString(uibgBlock.getX() + 100, uibgBlock.getY() + 175, matchXtimeMins + ":"
                + matchXtimeSeconds);
        fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 222, "O time :");
        fontB.drawString(uibgBlock.getX() + 100, uibgBlock.getY() + 205, matchOtimeMins + ":"
                + matchOtimeSeconds);

        if (connection != null) {
            switch (input.getTurnOfPlayer()) {
            case 0:
                fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 100, "WE HAVE A WINNER!");
                break;
            case 1:
                fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 100,
                        "Waiting for X to make a move...");
                break;
            case 2:
                fontA.drawString(uibgBlock.getX() + 10, uibgBlock.getY() + 100,
                        "Waiting for O to make a move...");
                break;
            }
        } else {

        }
    }

    public void check() {
        if (Display.isCloseRequested() || input.doExit()) {
            isRunning = false;
        }

        updateMatchTimer();

        if (input.isRegenerateTable() && connection == null) {
            resetGame();
        } else if (input.isRegenerateTable() && oponentCleansTable) {
            resetGame();
            oponentCleansTable = false;
        }

        if (!Mouse.isInsideWindow() || mouseX > GRID_WIDTH * BLOCK_SIZE
                || mouseY > GRID_HEIGHT * BLOCK_SIZE) {
            input.superUnhover();
        }
        try {
            input.input(mouseX, mouseY, blockGrid, eraserButton, connection);
        } catch (IOException e) {
            System.out.println("I/O exception");
            e.printStackTrace();
        }

        if (input.doCheckWinner()) {
            blockGrid.getWinLogic().analize(input.getLastGridX(), input.getLastGridY());

        }
    }

    public void resetGame() {
        matchStartTime = System.currentTimeMillis() / 1000;
        matchXstartTime = System.currentTimeMillis() / 1000;
        matchOstartTime = System.currentTimeMillis() / 1000;
        matchOverAllTimeMins = 0;
        matchXtimeSeconds = 0;
        matchXtimeMins = 0;
        matchOtimeSeconds = 0;
        matchOtimeMins = 0;

        blockGridRenderer.clean();
        for (int x = 0; x < GRID_WIDTH - 1; x++) {
            for (int y = 0; y < GRID_HEIGHT - 1; y++) {
                blockGrid.setBlock(x, y, new Block(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE,
                        TextureManager.getTexture("paper_plain")));
            }
        }
        blockGridRenderer.setBlockToRender(blockGrid);
        blockGrid.getWinLogic().setGameMode(true);
        input.setRegenerateTable(false);
        System.out.println("Erasing board...");
    }

    private long matchStartTime = System.currentTimeMillis() / 1000;
    private long matchOverAllTimeSeconds = 0;
    private long matchOverAllTimeMins = 0;

    private long matchXstartTime = System.currentTimeMillis() / 1000;
    private long matchXtimeSeconds = 0;
    private long matchXtimeMins = 0;
    private long matchOstartTime = System.currentTimeMillis() / 1000;
    private long matchOtimeSeconds = 0;
    private long matchOtimeMins = 0;
    private long tempXtime = 0;
    private long tempOtime = 0;

    public void updateMatchTimer() {
        if (blockGrid.getWinLogic().isGameMode()) {
            matchOverAllTimeSeconds = System.currentTimeMillis() / 1000 - matchStartTime;
        }

        if (matchOverAllTimeSeconds >= 60) {
            matchOverAllTimeSeconds -= 60;
            matchStartTime = System.currentTimeMillis() / 1000;
            matchOverAllTimeMins++;
        }

        if (input.getTurnOfPlayer() == 1) {
            tempXtime = System.currentTimeMillis() / 1000 - matchXstartTime;
            matchOstartTime = System.currentTimeMillis() / 1000;
            if (tempXtime > 0) {
                matchXtimeSeconds++;
                matchXstartTime = System.currentTimeMillis() / 1000;
            }
        } else if (input.getTurnOfPlayer() == 2) {
            tempOtime = System.currentTimeMillis() / 1000 - matchOstartTime;
            matchXstartTime = System.currentTimeMillis() / 1000;
            if (tempOtime > 0) {
                matchOtimeSeconds++;
                matchOstartTime = System.currentTimeMillis() / 1000;
            }
        }

        if (matchXtimeSeconds >= 60) {
            matchXtimeSeconds -= 60;
            matchXstartTime = System.currentTimeMillis() / 1000;
            matchXtimeMins++;
        }

        if (matchOtimeSeconds >= 60) {
            matchOtimeSeconds -= 60;
            matchOstartTime = System.currentTimeMillis() / 1000;
            matchOtimeMins++;
        }
    }

    private int receivedX, receivedY;
    private PacketRefactorer refactory;
    private Map<String, Integer> coordMap;

    public void managePackets() throws IOException {

        if (!connection.isSent()) {
            if (input.isRegenerateTable()) {
                System.out.print("sending reset request");
                connection.sendPacket("$reset-1$");
            } else {
                connection.sendPacket("$null-0$");
            }
        }

        if (connection.isSent()) {
            coordMap = refactory.coordMap(connection.getPacket());

            if (coordMap.get("x") != null && coordMap.get("y") != null) {
                receivedX = coordMap.get("x");
                receivedY = coordMap.get("y");

                if (playerID == 1) {
                    blockGrid.getBlock(receivedX, receivedY).setTexture(
                            TextureManager.getTexture("paper_o"));
                    input.setTurnOfPlayer(1);
                } else if (playerID == 2) {
                    blockGrid.getBlock(receivedX, receivedY).setTexture(
                            TextureManager.getTexture("paper_x"));
                    input.setTurnOfPlayer(2);
                }
                input.setToCheckWinner(true);
                input.setLastCoords(receivedX, receivedY);
            } else if (coordMap.get("reset") != null) {
                if (coordMap.get("reset") == 1) {
                    oponentCleansTable = true;
                }
            } else {
                coordMap.clear();
            }
        }
    }
}
