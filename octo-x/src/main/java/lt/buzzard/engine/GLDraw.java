package lt.buzzard.engine;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.newdawn.slick.opengl.Texture;

public class GLDraw {

    public static final float[] DEFAULT_COLOR = { 1.0f, 1.0f, 1.0f };

    public static void quad(float x, float y, float blockSize) {
        quad(x, y, blockSize, blockSize);
    }

    public static void quad(float x, float y, float width, float height) {
        quad(x, y, width, height, DEFAULT_COLOR);
    }

    public static void quad(float x, float y, float width, float height, float[] color) {
        glDisable(GL_TEXTURE_2D);
        glLoadIdentity();
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_TRIANGLE_STRIP);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x, y + height);
        glVertex2f(x + width, y + height);
        glEnd();
        glLoadIdentity();
    }

    public static void quad(float x, float y, float blockSize, Texture texture) {
        quad(x, y, blockSize, blockSize, texture);
    }

    public static void quad(float x, float y, float width, float height, Texture texture) {
        quad(x, y, width, height, texture, DEFAULT_COLOR);
    }

    public static void quad(float x, float y, float width, float height, Texture texture,
            float[] color) {
        glEnable(GL_TEXTURE_2D);
        texture.bind();
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_TRIANGLE_STRIP);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glEnd();
    }

}
