package lt.buzzard.engine;

import static lt.buzzard.engine.GLDraw.quad;

import java.util.List;

import lt.buzzard.entities.Block;
import lt.buzzard.entities.Grid;

public class BlockRenderer {

    private Block block;
    private Grid blockGrid;
    private List<Block> blockList;
    private boolean initialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public void setBlockToRender(Grid blockGrid) {
        if (!initialized) {
            this.blockGrid = blockGrid;
            initialized = true;
        } else {
            System.err.println("can't set BlockGrid type, other type set");
        }
    }

    public void setBlockToRender(Block block) {
        if (!initialized) {
            this.block = block;
            initialized = true;
        } else {
            System.err.println("can't set Block type, other type set");
        }
    }

    public void setBlockToRender(List<Block> blockList) {
        if (!initialized) {
            this.blockList = blockList;
            initialized = true;
        } else {
            System.err.println("can't set BlockGrid type, other type set");
        }
    }

    public void draw() {
        if (initialized) {
            if (block != null) {
                drawBlock(block);
            } else if (blockGrid != null) {
                for (int x = 0; x < blockGrid.getGrid_width() - 1; x++) {
                    for (int y = 0; y < blockGrid.getGrid_height() - 1; y++) {
                        Block tempBlock = blockGrid.getBlock(x, y);
                        if (tempBlock.getTexture() != null) {
                            drawBlock(tempBlock);
                        } else {
                            drawBlock(tempBlock);
                            drawBlock(tempBlock);
                        }
                    }
                }
            } else if (blockList != null) {
                for (Block tempBlock : blockList) {
                    drawBlock(tempBlock);
                }
            }
        }
    }

    private void drawBlock(Block block) {
        if (block.getTexture() != null) {
            quad(block.getX(), block.getY(), block.getWidth(), block.getHeight(),
                    block.getTexture(), block.getColor());
        } else {
            quad(block.getX(), block.getY(), block.getWidth(), block.getHeight(),
                    block.getColor());
        }
    }

    public void clean() {
        block = null;
        blockGrid = null;
        if (blockList != null) {
            blockList.clear();
        }
        initialized = false;
    }
}