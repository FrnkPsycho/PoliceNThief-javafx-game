package top.frnks.copchasethief;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class GamePauseMenu {
    public static final AnchorPane pauseMenuRoot = new AnchorPane();
    public static final Rectangle pauseMenuBackground = new Rectangle();
    public static final VBox buttonsBox = new VBox(20);
    public static final Button resumeGameButton = new Button("Resume Game");
    public static final Button resetGameButton = new Button("Reset Game");

    public static final Button quitToMainMenuButton = new Button("Quit to Main Menu");

    public static void createPauseMenu() {
        pauseMenuRoot.setPrefSize(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);
        pauseMenuRoot.getChildren().add(buttonsBox);

        // TODO: draw a simple settings menu background

        buttonsBox.getChildren().add(resumeGameButton);
        buttonsBox.getChildren().add(resetGameButton);
        buttonsBox.getChildren().add(quitToMainMenuButton);
        resumeGameButton.setPrefSize(200, 100);
        resetGameButton.setPrefSize(200, 100);
        quitToMainMenuButton.setPrefSize(200, 100);

        quitToMainMenuButton.setOnAction(event -> {
            pauseMenuRoot.getScene().setRoot(GameMainMenu.mainMenuRoot);
        });
        resetGameButton.setOnAction(event -> {
            GameApplication.resetGame();
            pauseMenuRoot.getScene().setRoot(GameApplication.mainGameRoot);
        });
        resumeGameButton.setOnAction(event -> {
            GameVars.gamePaused = false;
            pauseMenuRoot.getScene().setRoot(GameApplication.mainGameRoot);
        });
    }
}
