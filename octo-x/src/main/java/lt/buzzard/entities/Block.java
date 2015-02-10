package lt.buzzard.entities;

import lombok.Data;

import org.newdawn.slick.opengl.Texture;

@Data
public class Block {

    private float x, y, width, height;
    private Texture texture;
    private float[] color = { 1.0f, 1.0f, 1.0f };

    public Block(int x, int y, int size) {
        this(x, y, size, size);
    }

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Block(int x, int y, int size, Texture texture) {
        this(x, y, size, size, texture);
    }

    public Block(int x, int y, int width, int height, Texture texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }

    public void setColor(float[] color) {
        try {
            this.color = color;
        } catch (Exception e) {
        }
    }

}
