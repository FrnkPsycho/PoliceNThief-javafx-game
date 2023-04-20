package top.frnks.copchasethief;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final AnchorPane ROOT = new AnchorPane();

    private Parent createMainMenu() {return null;} // TODO: createMainMenu method
    private Parent createMainGame() {
        ROOT.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        return ROOT;
    }
    @Override
    public void start(Stage stage) throws IOException {
        StackPane stack = new StackPane();
        stack.getChildren().add(createMainGame());
//        stack.getChildren().add(createMainMenu()); TODO: make main menu invisible after game start
        Scene gameScene = new Scene(stack);

        // Keyboard input
        gameScene.setOnKeyTyped(event -> {
            String inputCharacter = event.getCharacter();
            // TODO: match input to target
        });

        stage.setScene(gameScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

