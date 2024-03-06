package org.kpg2.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kpg2.*;

import java.util.*;

public class MazeSolvingAnimation {
    private static final int FPS = 50;
    private static final Color ENDING_BLOCK_COLOR = Color.web("rgb(50,150,50)");
    private static final Color BLOCK_TO_EXPLORE_COLOR = Color.web("rgb(150,50,40)");
    private static final Color EXPLORED_BLOCK_COLOR = Color.web("rgba(150,50,40,0.5)");
    private static final Color BLOCK_IN_ROUTE_COLOR = Color.web("rgba(50,150,40,0.5)");
    private final Timeline timeline = new Timeline();
    private final Maze maze;
    private final SquarePlate squarePlate;

    public MazeSolvingAnimation(Maze maze, SquarePlate squarePlate, Position start, Position end) {
        this.maze = maze;
        this.squarePlate = squarePlate;
        squarePlate.changeColor(ENDING_BLOCK_COLOR,end.y,end.x);
        Route foundRoute = makeRoute(start, end);
        if (foundRoute != null) {
            timeline.setOnFinished(e -> showRoute(foundRoute));
        }
    }

    private void showRoute(Route foundRoute) {
        for (Position routePosition : foundRoute.getRoute()) {
            squarePlate.changeColor(BLOCK_IN_ROUTE_COLOR, routePosition.y, routePosition.x);
        }
    }

    private Route makeRoute(Position start, Position end) {
        Map<Block, Block> adjacentBlocks = new HashMap<>();
        int startX = start.x;
        int startY = start.y;
        int endX = end.x;
        int endY = end.y;

        int iteration = 0;

        Block startingBlock = maze.getBlock(startX, startY);
        Block endingBlock = maze.getBlock(endX, endY);

        if (startingBlock.isWall() || endingBlock.isWall()) {
            return null;
        }

        Queue<Block> blocksToExplore = new LinkedList<>();
        blocksToExplore.add(startingBlock);
        Set<Block> exploredBlocks = new HashSet<>();
        while (!exploredBlocks.contains(endingBlock) && !blocksToExplore.isEmpty()) {
            Block blockToExplore = blocksToExplore.poll();
            List<Block> adjacentBlocksList = getAdjacentBlocks(blocksToExplore, exploredBlocks, adjacentBlocks,
                    blockToExplore);
            blocksToExplore.addAll(adjacentBlocksList);
            exploredBlocks.add(blockToExplore);

            displayExploredBlock(blockToExplore, iteration);
            displayBlocksToExplore(adjacentBlocksList, iteration);
            iteration++;
        }

        Route route = new Route();
        Block findingBlock = endingBlock;
        while (findingBlock != null) {
            route.addPosition(new Position(findingBlock.getX(), findingBlock.getY()));
            findingBlock = adjacentBlocks.get(findingBlock);
        }

        return route;
    }

    private void displayExploredBlock(Block exploredBlock, int iteration) {
        int x = exploredBlock.getX();
        int y = exploredBlock.getY();
        KeyFrame kf = new KeyFrame(Duration.seconds((double) iteration/FPS), e ->
                squarePlate.changeColor(EXPLORED_BLOCK_COLOR, y, x));
        timeline.getKeyFrames().add(kf);
    }

    private void displayBlocksToExplore(List<Block> blocksToExplore, int iteration) {
        for (Block blockToExplore : blocksToExplore) {
            int x = blockToExplore.getX();
            int y = blockToExplore.getY();
            KeyFrame kf = new KeyFrame(Duration.seconds((double) iteration/FPS),
                    e -> squarePlate.changeColor(BLOCK_TO_EXPLORE_COLOR, y, x));
            timeline.getKeyFrames().add(kf);

        }
    }

    private List<Block> getAdjacentBlocks(Queue<Block> queue, Set<Block> exploredBlocks,
                                          Map<Block, Block> adjacentBlocks, Block centerBlock) {
        List<Block> adjacentBlocksList = new ArrayList<>();
        int centerBlockX = centerBlock.getX();
        int centerBlockY = centerBlock.getY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!isInBounds(centerBlockX + j, centerBlockY + i)) {
                    continue;
                }

                Block adjacentBlock = maze.getBlock(centerBlockX + j, centerBlockY + i);
                if (adjacentBlock.isWall()) {
                    continue;
                }
                if (i == 0 && j == 0) {
                    continue;
                }
                if (Math.abs(i) + Math.abs(j) == 2) {
                    continue;
                }

                if (exploredBlocks.contains(adjacentBlock)) {
                    continue;
                }

                if (queue.contains(adjacentBlock)) {
                    continue;
                }
                //System.out.println(adjacentBlock);
                adjacentBlocks.put(adjacentBlock, centerBlock);
                adjacentBlocksList.add(adjacentBlock);
            }
        }
        return adjacentBlocksList;
    }

    private boolean isInBounds(int x, int y) {
        Block[][] mazeCells = maze.getCells();
        return x >= 0 && x < mazeCells[0].length && y >= 0 && y < mazeCells.length;
    }

    public void play() {
        timeline.play();
    }
}
