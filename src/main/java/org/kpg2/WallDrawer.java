package org.kpg2;

import javafx.scene.paint.Color;

public final class WallDrawer {
    private WallDrawer() {
        // disable instantiation
    }

    public static void displayWall(Maze maze, SquarePlate squarePlate, int x, int y) {
        if (maze.getBlock(x,y).isWall()) {
            drawWall(squarePlate, x,y);
        }
    }

    public static void drawWall(SquarePlate squarePlate, int x, int y) {
        squarePlate.changeColor(Color.BLACK,y,x);
    }
}
