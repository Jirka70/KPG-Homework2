package org.kpg2.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.kpg2.Block;
import org.kpg2.WallDrawer;
import org.kpg2.Maze;
import org.kpg2.SquarePlate;

import java.util.ArrayList;
import java.util.List;

public class MazeAnimation {
    private static final int FPS = 50;
    private final Timeline timeline = new Timeline();
    private final Maze maze;
    private final SquarePlate squarePlate;
    private final List<Block> unprocessedWalls = new ArrayList<>();

    public MazeAnimation(Maze maze, SquarePlate squarePlate) {
        this.maze = maze;
        this.squarePlate = squarePlate;
        buildPillars();
        timeline.setOnFinished(e -> stretchWalls());
    }

    public void play() {
        timeline.play();
    }

    private void buildPillars() {
        for (int y = 0; y < maze.getRows(); y++) {
            for (int x = 0; x < maze.getCols(); x++) {
                final int wallX = x;
                final int wallY = y;
                initCell(x, y);
                KeyFrame kf = new KeyFrame(Duration.seconds((double) (y + x) / FPS),
                        actionEvent -> WallDrawer.displayWall(maze, squarePlate, wallX, wallY));
                timeline.getKeyFrames().addAll(kf);
            }
        }
    }

    private void initCell(int x, int y) {
        int rows = maze.getRows();
        int cols = maze.getCols();
        Block[][] mazeCells = maze.getCells();
        Block block = new Block(x, y);
        if (y % 2 == 0 && x % 2 == 0 && x < cols - 1 && y < rows - 1 && x > 0 && y > 0) {
            block.setWall(true);
            unprocessedWalls.add(block);
        } else if (x == 0 || x == cols - 1) {
            block.setWall(true);
        } else if (y == 0 || y == rows - 1) {
            block.setWall(true);
        }
        mazeCells[y][x] = block;
    }

    private void stretchWalls() {
        StretchingWallsAnimation stretchingWallsAnimation = new StretchingWallsAnimation(maze, squarePlate,
                unprocessedWalls);
        stretchingWallsAnimation.play();
    }
}
