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
    // TODO: move finals to GameSettings class
    // TODO: move variables to GameVars class
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int TIMEOUT = 10;
    public static double time = 0;
    public static long correctCount = 0;
    public static boolean gameOver = false;
    public static boolean timeoutGameOver = false;
    public static boolean catchedGameOver = false;
    public static char[] charTable = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ ".toCharArray();
    private static final Random gameRandom = new Random();
    public static final AnchorPane root = new AnchorPane();
    public static final Text targetText = new Text();
    public static final Text firstCharacterText = new Text();
    public static final Text gameOverText = new Text();
    public static final Text timerText = new Text();
    public static final Text correctCountText = new Text("0");
    private Parent createMainMenu() {return null;} // TODO: createMainMenu method
    private Parent createMainGame() {
        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().add(targetText);
        targetText.setFont(new Font("Consolas", 36));
        AnchorPane.setBottomAnchor(targetText, 50.0);
        AnchorPane.setLeftAnchor(targetText, 30.0);

        root.getChildren().add(firstCharacterText);
        firstCharacterText.setFont(new Font("Consolas", 36));
        AnchorPane.setBottomAnchor(firstCharacterText, 50.0);
        AnchorPane.setLeftAnchor(firstCharacterText, 30.0);

        root.getChildren().add(timerText);
        timerText.setFont(new Font("Noto Sans", 40));
        AnchorPane.setTopAnchor(timerText, 20.0);
        AnchorPane.setLeftAnchor(timerText, 10.0);

        root.getChildren().add(gameOverText);
        gameOverText.setVisible(false);
        gameOverText.setFont(new Font("Noto Sans", 36));
        AnchorPane.setTopAnchor(gameOverText, 270.0);
        AnchorPane.setLeftAnchor(gameOverText, 100.0);

        root.getChildren().add(correctCountText);

        setNewString();



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
        if ( gameOver ) return;

        // game runs in 60fps, so timer increases approx. 0.016667 per frame
        time += 0.016667;

        // update timerText
        timerText.setText(String.format("%.1f", time));

        // timeout check
        if ( (int)time >= TIMEOUT ) {
            gameOver = true;
            timeoutGameOver = true;
            gameOverText.setVisible(true);
            setGameOverString();
        }

        // TODO: catch check

    }

    private static void setGameOverString() {
        // TODO: localization texts
        if ( timeoutGameOver ) gameOverText.setText("Times Up! The thief has gone away!");
        else if ( catchedGameOver ) gameOverText.setText("Congrats! The thief has been caught!"); // TODO: player plays as thief
        else  gameOverText.setText("I don't know what's going on, but the game is over...");
    }

    private static void setNewString() {
        targetText.setText(generateRandomString(3, RandomStringType.ALL_LOWERCASE));
        firstCharacterText.setText(targetText.getText().substring(0,1));
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
        if (gameOver) {
            if ( input.equals("r") ) {
                // TODO: reset the game
            }
            return;
        }

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
        correctCount += 1;
        correctCountText.setText(String.valueOf(correctCount));

        targetText.setText(targetText.getText().substring(1));
        if ( !targetText.getText().isEmpty() ) {
            String firstChar = targetText.getText().substring(0,1);
            firstCharacterText.setText(firstChar);
            firstCharacterText.setFill(Color.BLACK);
        } else {
            setNewString();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

