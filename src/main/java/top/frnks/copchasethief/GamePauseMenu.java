package top.frnks.copchasethief;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class GamePauseMenu {
    public static final AnchorPane pauseMenuRoot = new AnchorPane();
    public static final Rectangle pauseMenuBackground = new Rectangle();
    public static final Button resumeGameButton = new Button("Resume Game");
    public static final Button quitToMainMenuButton = new Button("Quit to Main Menu");

    public static void createPauseMenu() {
        pauseMenuRoot.setPrefSize(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);

        // TODO: draw a simple settings menu background

        pauseMenuRoot.getChildren().add(resumeGameButton);
        resumeGameButton.setPrefSize(300, 100);
        pauseMenuRoot.getChildren().add(quitToMainMenuButton);
        quitToMainMenuButton.setPrefSize(300, 100);
        AnchorPane.setTopAnchor(quitToMainMenuButton, 100.0);

        quitToMainMenuButton.setOnAction(event -> {
            pauseMenuRoot.getScene().setRoot(GameMainMenu.mainMenuRoot);
        });
        resumeGameButton.setOnAction(event -> {
            GameVars.gamePaused = false;
            pauseMenuRoot.getScene().setRoot(GameApplication.mainGameRoot);
        });
    }
}
