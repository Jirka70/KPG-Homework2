package org.kpg2.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.kpg2.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class StretchingWallsAnimation {
    private static final Random RANDOM = new Random();
    private static final int FPS = 50;
    private final Timeline timeline = new Timeline();
    private final Maze maze;
    private final SquarePlate squarePlate;
    private final List<Block> unprocessedWalls;
    private int numberOfDrawnCells = 0;

    public StretchingWallsAnimation(Maze maze, SquarePlate squarePlate, List<Block> unprocessedWalls) {
        this.maze = maze;
        this.squarePlate = squarePlate;
        this.unprocessedWalls = unprocessedWalls;
        stretchWalls();
        timeline.setOnFinished(e -> solveMaze());
    }

    private void solveMaze() {
        Position start = new Position(1, 1);
        Position end = new Position(maze.getCols() - 2, maze.getRows() - 2);
        MazeSolvingAnimation solvingAnimation = new MazeSolvingAnimation(maze, squarePlate, start, end);
        solvingAnimation.play();
    }

    public void play() {
        timeline.play();
    }

    private void stretchWalls() {
        Set<Block> processedWalls = new HashSet<>();
        while (!unprocessedWalls.isEmpty()) {
            Block processingWall = removeRandomWall();
            Direction direction = generateRandomDirection();
            createWall(processedWalls, processingWall, direction);
        }
    }

    private void createWall(Set<Block> processedWalls, Block processingWall, Direction direction) {
        int wallX = processingWall.getX();
        int wallY = processingWall.getY();
        while (isInBounds(wallX, wallY)) {
            Block block = maze.getBlock(wallX, wallY);
            if (block.isWall() && processedWalls.contains(block)) {
                break;
            } else {
                if (block.isWall()) {
                    processedWalls.add(block);
                    unprocessedWalls.remove(block);
                }
                block.setWall(true);
            }
            final int fWallX = wallX;
            final int fWallY = wallY;
            numberOfDrawnCells++;
            KeyFrame kf = new KeyFrame(Duration.seconds((double) numberOfDrawnCells / FPS),
                    event -> WallDrawer.displayWall(maze, squarePlate, fWallX, fWallY));
            timeline.getKeyFrames().add(kf);
            block.setWall(true);
            wallX += direction.x;
            wallY += direction.y;
        }
    }

    private boolean isInBounds(int x, int y) {
        Block[][] mazeCells = maze.getCells();
        return x >= 0 && x < mazeCells[0].length && y >= 0 && y < mazeCells.length;
    }

    private Block removeRandomWall() {
        int size = unprocessedWalls.size();
        return unprocessedWalls.remove(RANDOM.nextInt(size));
    }

    private Direction generateRandomDirection() {
        Direction[] directions = Direction.values();
        int numberOfDirections = directions.length;
        int randomValue = RANDOM.nextInt(numberOfDirections);
        return directions[randomValue];
    }
}
