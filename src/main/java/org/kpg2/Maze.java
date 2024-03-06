package org.kpg2;

public class Maze {
    private final Block[][] maze;
    private final int rows;
    private final int cols;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new Block[rows][cols];
    }

    public Block[][] getCells() {
        return maze;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Block getBlock(int x, int y) {
        return maze[y][x];
    }
}
