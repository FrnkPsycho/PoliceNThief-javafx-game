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
import top.frnks.copchasethief.type.SpriteType;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.logging.Logger;

public class GameApplication extends Application {
    public static final Logger LOGGER = Logger.getGlobal();
    public static double secondTimer = 0;
    public static double keepTimer = 0;
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

        // TODO: reconstruct map shapes
        GameMap.generateGameMap(GameSettings.MAP_SHAPE_TYPE);
        if ( GameSettings.MAP_SHAPE_TYPE == GameMapShapeType.Rectangle) GameVars.mapLength = GameMapShapes.RECTANGLE_POINTS;
        //
//        GameMap.generateGameMap(GameMapShapeType.Hexagon);
        root.getChildren().add(GameMap.mapPane);
        AnchorPane.setTopAnchor(GameMap.mapPane, 200.0);
        AnchorPane.setLeftAnchor(GameMap.mapPane, 112.0);

        root.getChildren().add(gameOverText);
        gameOverText.setVisible(false);
        gameOverText.setFont(counterFont);
        AnchorPane.setTopAnchor(gameOverText, 270.0);
        AnchorPane.setLeftAnchor(gameOverText, 100.0);

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
        secondTimer += 0.016667;
        keepTimer += 0.016667;

        // if reach a second, clear secondTimer, add one second to totalTime
        if ( secondTimer > 1 ) {
            secondTimer = 0;
            GameVars.totalTimeSeconds += 1;

            // enemy auto forward/backward
            // TODO: player act as thief
            // TODO: customizable speed
//            if ( gameRandom.nextInt(2) == 0 ) GameMap.thief.moveForward(1);
            GameMap.thief.setBetterDirection();
            GameMap.police.setBetterDirection();
            GameMap.thief.moveForward(1);

        }
//        if ( calculateNearestDistance(GameMap.police.mapIndex, GameMap.thief.mapIndex, GameVars.mapLength) < 4 ) {
//            // TODO: Strange behavior of thief if police nearly catch up
//            GameMap.thief.takeTurn();
//            if ( GameSettings.PLAYER_SPRITE == SpriteType.Police ) {
//                GameMap.thief.moveForward(2);
//            }
//        }
//        if ( GameMap.thief.direction == GameMap.police.direction) {
//            if ( calculateNearestDistance(GameVars.mapLength) < 4 ) {
//                GameMap.thief.direction = !GameMap.police.direction;
//                GameMap.thief.moveForward(2);
//                GameMap.police.direction = GameMap.thief.direction;
//            }
//        }



        // update timerText
        timerText.setText(String.format("%.1f", keepTimer));

        // calculate CPS ( Character Per Second )
        GameVars.cps = GameVars.correctCount / keepTimer;
        cpsText.setText("CPS: " + String.format("%.1f", GameVars.cps));


        // timeout check
        if ( GameVars.totalTimeSeconds >= GameSettings.TIMEOUT ) {
            GameVars.gameOver = true;
            GameVars.timeoutGameOver = true;
            gameOverText.setVisible(true);
            setGameOverString();
        }

        if ( GameMap.police.mapIndex == GameMap.thief.mapIndex ) {
            GameMap.police.setFill(Color.GREEN);
            GameVars.gameOver = true;
            GameVars.caughtGameOver = true;
            gameOverText.setVisible(true);
            setGameOverString();
        }
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
            // TODO: player speed relates to CPS.
            if ( GameSettings.PLAYER_SPRITE == SpriteType.Police ) GameMap.police.moveForward(1);
            else GameMap.thief.moveForward(1);
            setNewString();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

