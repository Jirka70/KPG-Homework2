package org.kpg2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.kpg2.animation.MazeAnimation;


public class MazeApp extends Application {
    private static final int WIDTH = 1020;
    private static final int HEIGHT = 620;
    private static final int SQUARE_SIZE = 20;
    private final Pane root = new Pane();
    private final Maze maze = new Maze(HEIGHT / SQUARE_SIZE, WIDTH / SQUARE_SIZE);
    private final SquarePlate squarePlate = new SquarePlate(HEIGHT / SQUARE_SIZE,
            WIDTH / SQUARE_SIZE, SQUARE_SIZE);


    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Maze App (KPG2)");
        stage.setScene(scene);
        stage.show();

        addPlateToRootPane();

        MazeAnimation animation = new MazeAnimation(maze, squarePlate);
        animation.play();
    }

    private void addPlateToRootPane() {
        for (int i = 0; i < squarePlate.getRows(); i++) {
            for (int j = 0; j < squarePlate.getCols(); j++) {
                Rectangle square = squarePlate.getSquare(i,j);
                root.getChildren().add(square);
            }
        }
    }
}