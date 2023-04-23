package top.frnks.copchasethief;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class GameMainMenu {
    public static final AnchorPane mainMenuRoot = new AnchorPane();
    public static final Rectangle mainMenuBackground = new Rectangle();
    public static final Button startGameButton = new Button("Start");
    public static final Button settingsMenuButton = new Button("Settings");
    public static final Button quitGameButton = new Button("Quit");
    public static void createMainMenu() {
        mainMenuRoot.setPrefSize(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);

//        var asset = getClass().getClassLoader().getResourceAsStream("assets/ui/main_menu.png");
//        ImagePattern image = new ImagePattern(new Image(asset));
//        mainMenuBackground.setFill(image);
        // TODO: draw a simple main menu background
        mainMenuRoot.getChildren().add(startGameButton);
        startGameButton.setPrefSize(300, 100);
        mainMenuRoot.getChildren().add(settingsMenuButton);
        settingsMenuButton.setPrefSize(300, 100);
        AnchorPane.setTopAnchor(settingsMenuButton, 100.0);
//        AnchorPane.setTopAnchor(startGameButton, 300.0);
//        AnchorPane.setLeftAnchor(startGameButton, 240.0);
        startGameButton.setOnAction(event -> {
            GameVars.gamePaused = false;
            GameApplication.mainGameRoot = new AnchorPane();
            GameApplication.resetGame();
            try {
                GameApplication.createMainGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mainMenuRoot.getScene().setRoot(GameApplication.mainGameRoot);
        });
        settingsMenuButton.setOnAction(event -> {
            mainMenuRoot.getScene().setRoot(GameSettingsMenu.settingsMenuRoot);
        });

    }
}
