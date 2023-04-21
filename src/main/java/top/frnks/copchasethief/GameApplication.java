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
import top.frnks.copchasethief.type.GameMapShapeType;
import top.frnks.copchasethief.type.RandomStringType;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.logging.Logger;

public class GameApplication extends Application {
    // TODO: move variables to GameVars class
    public static final Logger LOGGER = Logger.getGlobal();
    public static double time = 0;
    public static final char[] charTable = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final Random gameRandom = new Random();
    public static final AnchorPane root = new AnchorPane();
    public static final Font counterFont = new Font("Noto Sans", 40);
    public static final Font stringFont = new Font("Consolas", 40);
    public static final Text targetText = new Text();
    public static final Text firstCharacterText = new Text();
    public static final Text gameOverText = new Text();
    public static final Text timerText = new Text();
    public static final Text correctCountText = new Text();
    public static final Text wrongCountText = new Text();
    public static final Text cpsText = new Text();
    private Parent createMainMenu() {return null;} // TODO: createMainMenu method
    private Parent createMainGame() {
        root.setPrefSize(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);

        root.getChildren().add(targetText);
        targetText.setFont(stringFont);
        AnchorPane.setBottomAnchor(targetText, 50.0);
        AnchorPane.setLeftAnchor(targetText, 30.0);

        root.getChildren().add(firstCharacterText);
        firstCharacterText.setFont(stringFont);
        AnchorPane.setBottomAnchor(firstCharacterText, 50.0);
        AnchorPane.setLeftAnchor(firstCharacterText, 30.0);

        root.getChildren().add(timerText);
        timerText.setFont(counterFont);
        AnchorPane.setTopAnchor(timerText, 20.0);
        AnchorPane.setLeftAnchor(timerText, 10.0);

        root.getChildren().add(correctCountText);
        correctCountText.setText("Correct: " + GameVars.correctCount); // TODO: make this translatable
        correctCountText.setFont(counterFont);
        AnchorPane.setTopAnchor(correctCountText, 80.0);
        AnchorPane.setLeftAnchor(correctCountText, 10.0);

        root.getChildren().add(wrongCountText);
        wrongCountText.setText("Wrong: " + GameVars.wrongCount); // TODO: make this translatable
        wrongCountText.setFont(counterFont);
        AnchorPane.setTopAnchor(wrongCountText, 140.0);
        AnchorPane.setLeftAnchor(wrongCountText, 10.0);

        root.getChildren().add(cpsText);
        cpsText.setText("CPS: " + String.format("%.1f", GameVars.cps)); // TODO: make this translatable
        cpsText.setFont(counterFont);
        AnchorPane.setTopAnchor(cpsText,20.0);
        AnchorPane.setRightAnchor(cpsText, 40.0);

        root.getChildren().add(gameOverText);
        gameOverText.setVisible(false);
        gameOverText.setFont(counterFont);
        AnchorPane.setTopAnchor(gameOverText, 270.0);
        AnchorPane.setLeftAnchor(gameOverText, 100.0);


        GameMap.generateGameMap(GameMapShapeType.Rectangle);
        root.getChildren().add(GameMap.mapPane);
        AnchorPane.setTopAnchor(GameMap.mapPane, 200.0);
        AnchorPane.setLeftAnchor(GameMap.mapPane, 112.0);

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
        if ( GameVars.gameOver ) return;

        // game runs in 60fps, so timer increases approx. 0.016667 per frame
        time += 0.016667;

        // update timerText
        timerText.setText(String.format("%.1f", time));

        // calculate CPS ( Character Per Second )
        GameVars.cps = GameVars.correctCount / time;
        cpsText.setText("CPS: " + String.format("%.1f", GameVars.cps));

        // timeout check
        if ( (int)time >= GameSettings.TIMEOUT ) {
            GameVars.gameOver = true;
            GameVars.timeoutGameOver = true;
            gameOverText.setVisible(true);
            setGameOverString();
        }

        // TODO: catch check

    }

    private static void setGameOverString() {
        // TODO: make them translatable
        if ( GameVars.timeoutGameOver ) gameOverText.setText("Times Up! The thief has gone away!");
        else if ( GameVars.caughtGameOver) gameOverText.setText("Congrats! The thief has been caught!"); // TODO: player plays as thief
        else  gameOverText.setText("I don't know what's going on, but the game is over...");
    }

    private static void setNewString() {
        targetText.setText(generateRandomString(GameSettings.STRING_LENGTH, GameSettings.STRING_TYPE));
        firstCharacterText.setText(targetText.getText().substring(0,1));
    }

    static String generateRandomString(int length, RandomStringType randomStringType) {

        StringBuilder sb = new StringBuilder();
        switch (randomStringType) {
            case ALL_LOWERCASE -> {
                for ( int i=0 ; i<length ; i++ ) {
                    int index = gameRandom.nextInt(0, 26);
                    char ch = charTable[index];
                    sb.append(ch);
                }
            }
            case ALL_UPPERCASE -> {
                for ( int i=0 ; i<length ; i++ ) {
                    int index = gameRandom.nextInt(26, charTable.length);
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
        if (GameVars.gameOver) {
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
        GameVars.wrongCount += 1;
        wrongCountText.setText("Wrong: " + GameVars.wrongCount);  // TODO: make this translatable

        firstCharacterText.setFill(Color.RED);
        // TODO: play sound if input is wrong
    }

    static void correctInputAction() {
        GameVars.correctCount += 1;
        correctCountText.setText("Correct: " + GameVars.correctCount); // TODO: make this translatable

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

