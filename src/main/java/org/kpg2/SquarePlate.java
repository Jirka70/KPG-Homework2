package org.kpg2;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquarePlate {
    private final Rectangle[][] plate;
    private final int squareSize;

    public SquarePlate(int rows, int cols, int squareSize) {
        plate = new Rectangle[rows][cols];
        this.squareSize = squareSize;
        initPlate();
    }

    private void initPlate() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                Rectangle square = new Rectangle(j*squareSize,i*squareSize,squareSize,squareSize);
                square.setFill(Color.WHITE);
                plate[i][j] = square;
            }
        }
    }

    public void changeColor(Color color, int row, int col) {
        plate[row][col].setFill(color);
    }

    public Rectangle getSquare(int row, int col) {
        return plate[row][col];
    }
    public int getRows() {
        return plate.length;
    }

    public int getCols() {
        return plate[0].length;
    }
}
