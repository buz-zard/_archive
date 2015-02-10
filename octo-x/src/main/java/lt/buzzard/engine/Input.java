package lt.buzzard.engine;

import static lt.buzzard.engine.Game.BLOCK_SIZE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.buzzard.connection.Connection;
import lt.buzzard.connection.PacketRefactorer;
import lt.buzzard.entities.Block;
import lt.buzzard.entities.Grid;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {

    private int turnOfPlayer;
    private int x, y;
    private int grid_x;
    private int grid_y;
    private boolean checkWinner;
    private boolean regenerateTable;
    private boolean isOfflineMode;
    private boolean isNoWinner;
    private boolean exit;
    public float[] hoverColor = { 0.9f, 0.9f, 0.9f };
    public float[] defaultColor = { 1.0f, 1.0f, 1.0f };
    private List<Block> hoveredBlockList = new ArrayList<Block>();
    private int playerID;
    private Connection connectionType;
    private PacketRefactorer refactory;

    private int lastGridX = 0, lastGridY = 0;

    public Input(int playerID, Connection connectionType, PacketRefactorer refactory) {
        this.playerID = playerID;
        this.connectionType = connectionType;
        turnOfPlayer = 1;
        checkWinner = false;
        regenerateTable = true;
        isOfflineMode = false;
        isNoWinner = true;
        exit = false;
        this.refactory = refactory;
    }

    public Input() {
        turnOfPlayer = 1;
        checkWinner = false;
        regenerateTable = true;
        isOfflineMode = true;
        isNoWinner = true;
        exit = false;
    }

    public void input(int x, int y, Grid blockGrid, Block eraserButton, Connection connection)
            throws IOException {
        this.x = x;
        this.y = y;
        isNoWinner = blockGrid.getWinLogic().isGameMode();
        gridCoords(this.x, this.y);

        if (!isNoWinner) {
            turnOfPlayer = 0;
        }

        while (Mouse.next()) {
            hover(blockGrid);

            while (Keyboard.next()) {
                // controls to reset game
                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    exit = true;
                }
            }

            if (Mouse.isButtonDown(0) && Mouse.getEventButtonState()) {
                // Eraser button
                if (x > eraserButton.getX() && x < eraserButton.getX() + eraserButton.getWidth()
                        && y > eraserButton.getY()
                        && y < eraserButton.getY() + eraserButton.getHeight()) {
                    setRegenerateTable(true);
                    blockGrid.getWinLogic().setWinner(null);
                    turnOfPlayer = 1;
                }

                // controls for placing signs
                if (isOfflineMode && isNoWinner) {
                    try {
                        if (blockGrid.getBlock(grid_x, grid_y).getTexture() == TextureManager
                                .getTexture("paper_plain")
                                && blockGrid.getBlock(grid_x, grid_y) != null) {

                            setLastCoords(grid_x, grid_y);

                            switch (turnOfPlayer) {
                                case 1:
                                    blockGrid.getBlock(grid_x, grid_y).setTexture(
                                            TextureManager.getTexture("paper_x"));
                                    turnOfPlayer = 2;
                                    break;
                                case 2:
                                    blockGrid.getBlock(grid_x, grid_y).setTexture(
                                            TextureManager.getTexture("paper_o"));
                                    turnOfPlayer = 1;
                                    break;
                            }
                            checkWinner = true;
                        }
                    } catch (Exception e) {
                    }
                }
            }

            try {
                if (!isOfflineMode && isNoWinner) {
                    if (Mouse.isButtonDown(0) && Mouse.getEventButtonState()) {
                        if (blockGrid.getBlock(grid_x, grid_y).getTexture() == TextureManager
                                .getTexture("paper_plain")
                                && blockGrid.getBlock(grid_x, grid_y) != null) {

                            setLastCoords(grid_x, grid_y);

                            if (!connectionType.isSent() && playerID == getTurnOfPlayer()) {
                                connectionType.sendPacket(refactory.coordString(grid_x, grid_y));
                                if (playerID == 1) {
                                    blockGrid.getBlock(grid_x, grid_y).setTexture(
                                            TextureManager.getTexture("paper_x"));
                                } else if (playerID == 2) {
                                    blockGrid.getBlock(grid_x, grid_y).setTexture(
                                            TextureManager.getTexture("paper_o"));
                                }
                                if (playerID == 1) {
                                    setTurnOfPlayer(2);
                                } else {
                                    setTurnOfPlayer(1);
                                }
                                checkWinner = true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("online input exception");
            }
        }
    }

    private void hover(Grid blockGrid) {
        try {
            if (!hoveredBlockList.contains(blockGrid.getBlock(grid_x, grid_y))
                    && blockGrid.getBlock(grid_x, grid_y).getTexture() == TextureManager
                            .getTexture("paper_plain")) {
                hoveredBlockList.add(blockGrid.getBlock(grid_x, grid_y));
                blockGrid.getBlock(grid_x, grid_y).setColor(hoverColor);
                unhover(hoveredBlockList, blockGrid.getBlock(grid_x, grid_y));
            } else if (!hoveredBlockList.contains(blockGrid.getBlock(grid_x, grid_y))) {
                superUnhover();
            }
        } catch (Exception e) {
        }
    }

    public void superUnhover() {
        try {
            if (hoveredBlockList.size() > 0) {
                for (Block cleanBlock : hoveredBlockList) {
                    cleanBlock.setColor(defaultColor);
                }
                hoveredBlockList.clear();
            }
        } catch (Exception e) {
        }
    }

    private void unhover(List<Block> hoveredBlockList, Block block) {
        try {
            if (hoveredBlockList.size() > 0) {
                Block tempBlock = block;
                hoveredBlockList.remove(block);
                for (Block cleanBlock : hoveredBlockList) {
                    cleanBlock.setColor(defaultColor);
                }
                hoveredBlockList.clear();
                hoveredBlockList.add(tempBlock);
            }
        } catch (Exception e) {
        }
    }

    public void setRegenerateTable(boolean regenerateTable) {
        this.regenerateTable = regenerateTable;
    }

    public boolean isRegenerateTable() {
        return regenerateTable;
    }

    public boolean doExit() {
        return exit;
    }

    public void setLastCoords(int x, int y) {
        lastGridX = x;
        lastGridY = y;
    }

    public int getLastGridX() {
        return lastGridX;
    }

    public int getLastGridY() {
        return lastGridY;
    }

    private void gridCoords(int x, int y) {
        grid_x = Math.round(x / BLOCK_SIZE);
        grid_y = Math.round(y / BLOCK_SIZE);
    }

    public int getTurnOfPlayer() {
        return turnOfPlayer;
    }

    public void setTurnOfPlayer(int id) {
        turnOfPlayer = id;
    }

    public boolean doCheckWinner() {
        boolean tempCheck = checkWinner;
        checkWinner = false;
        return tempCheck;
    }

    public void setToCheckWinner(boolean arg) {
        checkWinner = arg;
    }

}
