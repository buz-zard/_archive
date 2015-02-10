package lt.buzzard.ui;

import java.io.File;
import java.io.IOException;

import lt.buzzard.engine.Game;

public class Main {

    private static int grid_width;
    private static int grid_height;
    private static int signs2win;

    public static void main(String[] args) throws IOException {
        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
        grid_width = 37;
        grid_height = 31;
        signs2win = 5;
        new Game(grid_width, grid_height, signs2win).start();
    }

}
