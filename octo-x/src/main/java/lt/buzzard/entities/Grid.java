package lt.buzzard.entities;

import lt.buzzard.engine.TextureManager;

public class Grid {

    private Block[][] blocks;
    private int grid_width, grid_height;
    private WinLogicRules winLogic;

    public Grid(int grid_width, int grid_height) {
        this(grid_width, grid_height, 5);
    }

    public Grid(int grid_width, int grid_height, int signs2win) {
        this.grid_width = grid_width;
        this.grid_height = grid_height;
        blocks = new Block[grid_width][grid_height];
        winLogic = new WinLogicRules(blocks, signs2win);
    }

    public void setBlock(int x, int y, Block block) {
        if (x > 0 || y > 0 || x < grid_width - 1 || y < grid_height - 1) {
            blocks[x][y] = block;
        } else {
            System.err.format("can't set block with %d or %d\n", x, y);
        }
    }

    public Block getBlock(int x, int y) {
        if (x > 0 || y > 0 || x < grid_width - 1 || y < grid_height - 1) {
            return blocks[x][y];
        } else {
            System.err.format("can't get block with %d or %d\n", x, y);
            return null;
        }
    }

    public WinLogicRules getWinLogic() {
        return winLogic;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public int getGrid_width() {
        return grid_width;
    }

    public int getGrid_height() {
        return grid_height;
    }

    public class WinLogicRules {
        private int scoreX = 0, scoreO = 0;
        private Block[][] blocks;
        private boolean isGameMode;
        private String player1 = "X";
        private String player2 = "O";
        private String winner;
        private int signs2win;

        public WinLogicRules(Block[][] blocks, int signs2win) {
            this.blocks = blocks;
            this.signs2win = signs2win;
            isGameMode = true;
            winner = null;
        }

        private int tempX;
        private int tempY;

        public void analize(int lastX, int lastY) {
            if (isGameMode()) {
                // horizontal
                tempX = 0;
                tempY = 0;

                for (tempX = lastX - (signs2win - 1); tempX <= lastX + signs2win - 1; tempX++) {
                    winCounter(tempX, lastY);
                }

                // vertical
                tempY = 0;
                tempX = 0;

                for (tempY = lastY - (signs2win - 1); tempY <= lastY + signs2win - 1; tempY++) {
                    winCounter(lastX, tempY);
                }

                // downhill
                tempX = 0;
                tempY = 0;

                for (int i = -(signs2win - 1); i <= signs2win - 1; i++) {
                    tempX = lastX + i;
                    tempY = lastY + i;
                    winCounter(tempX, tempY);
                }

                // uphill
                tempX = 0;
                tempY = 0;

                for (int i = -(signs2win - 1); i <= signs2win - 1; i++) {
                    tempX = lastX + i;
                    tempY = lastY - i;
                    winCounter(tempX, tempY);
                }
            }
        }

        private void winCounter(int x, int y) {
            if (x >= 0 || y >= 0 || x < grid_width || y < grid_height) {
                try {
                    if (blocks[x][y].getTexture() == TextureManager.getTexture("paper_x")) {
                        scoreX++;
                        scoreO = 0;
                        if (scoreX == signs2win) {
                            setWinner(player1);
                            System.out.println(getWinner() + " wins");
                            setGameMode(false);
                        }
                    } else if (blocks[x][y].getTexture() == TextureManager.getTexture("paper_o")) {
                        scoreO++;
                        scoreX = 0;
                        if (scoreO == signs2win) {
                            setWinner(player2);
                            System.out.println(getWinner() + " wins");
                            setGameMode(false);
                        }
                    } else {
                        scoreX = 0;
                        scoreO = 0;
                    }
                } catch (Exception e) {
                    scoreX = 0;
                    scoreO = 0;
                }
            }
        }

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }

        public boolean isGameMode() {
            return isGameMode;
        }

        public void setGameMode(boolean isGameMode) {
            this.isGameMode = isGameMode;
        }
    }

}
