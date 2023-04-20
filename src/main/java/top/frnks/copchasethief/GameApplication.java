package top.frnks.copchasethief;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameApplication extends Application {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static double time = 0;
    public static char[] charTable = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ ".toCharArray();
    private static final Random gameRandom = new Random();
    public static final AnchorPane root = new AnchorPane();
    public static final Text targetText = new Text(generateRandomString(10, RandomStringType.ALL_LOWERCASE));
    public static final Text firstCharacterText = new Text(targetText.getText().substring(0,1));
    public static final Text gameoverText = new Text();
    public static final Text timerText = new Text(String.valueOf(time));
    private Parent createMainMenu() {return null;} // TODO: createMainMenu method
    private Parent createMainGame() {
        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().add(targetText);
        root.getChildren().add(firstCharacterText);
        targetText.setFont(new Font("Consolas", 36));
        firstCharacterText.setFont(new Font("Consolas", 36));
        AnchorPane.setBottomAnchor(targetText, 50.0);
        AnchorPane.setLeftAnchor(targetText, 30.0);
        AnchorPane.setBottomAnchor(firstCharacterText, 50.0);
        AnchorPane.setLeftAnchor(firstCharacterText, 30.0);

        AnimationTimer timer = new AnimationTimer() {
            long last = 0;
            @Override
            public void handle(long now) {
                if ( now - last >= 16000000 ) {
                    update();
                    last = now;
                }
            }
        };
        timer.start();

        return root;
    }

    private List<Sprite> sprites() {
        return root.getChildren().stream().map(node -> (Sprite)node).collect(Collectors.toList());
    }

    private void update() {
        time += 1;
        if ( !targetText.getText().isEmpty() ) {
            String firstChar = targetText.getText().substring(0,1);
            firstCharacterText.setText(firstChar);
        } else {
            firstCharacterText.setText("");

        }
    }

    static String generateRandomString(int length, RandomStringType randomStringType) {

        StringBuilder sb = new StringBuilder();
        switch (randomStringType) {
            case ALL_LOWERCASE -> {
                for ( int i=0 ; i<length ; i++ ) {
                    int index = gameRandom.nextInt(0, 27);
                    char ch = charTable[index];
                    sb.append(ch);
                }
            }
            case ALL_UPPERCASE -> {
                for ( int i=0 ; i<length ; i++ ) {
                    int index = gameRandom.nextInt(27, charTable.length);
                    char ch = charTable[index];
                    sb.append(ch);
                }
            }
            case SPONGEBOB_TEXT -> {
                for ( int i=0 ; i<length ; i++ ) {
                    int index = gameRandom.nextInt(0, charTable.length);
                    char ch = charTable[index];
                    sb.append(ch);
                }
            }
        }

        return sb.toString();
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
            inputAction(inputCharacter);

        });

        stage.setScene(gameScene);
        stage.show();
    }

    static void inputAction(String input) {
        String targetString = targetText.getText();
        String firstCharacter = String.valueOf(targetString.charAt(0));
        if ( input.equals(firstCharacter) ) {
            correctInputAction();
        } else {
            wrongInputAction();
        }
    }

    static void wrongInputAction() {
        firstCharacterText.setFill(Color.RED);
        // TODO: play sound if input is wrong
    }

    static void correctInputAction() {
        firstCharacterText.setFill(Color.BLACK);
        targetText.setText(targetText.getText().substring(1));
    }

    public static void main(String[] args) {
        launch();
    }
}

