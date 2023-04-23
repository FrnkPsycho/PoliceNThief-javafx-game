package top.frnks.copchasethief;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import top.frnks.copchasethief.type.GameMapShapeType;
import top.frnks.copchasethief.type.RandomStringType;
import top.frnks.copchasethief.type.SpriteType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.logging.Logger;

public class GameApplication extends Application {
    public static final Logger LOGGER = Logger.getGlobal();
    public static double enemyTimer = 0;
    public static double secondTimer = 0;
    public static double keepTimer = 0;
    public static final char[] charTable = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final Random gameRandom = new Random();
    public static final AnchorPane root = new AnchorPane();
    public static final FlowPane stringPane = new FlowPane();
    public static final Font counterFont = new Font("Noto Sans", 40);
    public static final Font stringFont = new Font("Consolas", 40);
    public static final Text targetText = new Text();
    public static final Text firstCharacterText = new Text();
    public static final Text finishedText = new Text();
    public static final Text gameOverText = new Text();
    public static final Text timerText = new Text();
    public static final Text correctCountText = new Text();
    public static final Text wrongCountText = new Text();
    public static final Text cpsText = new Text();
    public static final Rectangle cursor = new Rectangle(24, 2);

    private Parent createMainMenu() {return null;} // TODO: createMainMenu method
    private Parent createMainGame() throws IOException {
        root.setPrefSize(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);

        root.getChildren().add(finishedText);
        finishedText.setFont(stringFont);
        finishedText.setFill(Color.GRAY);
        finishedText.setTextAlignment(TextAlignment.RIGHT);
        AnchorPane.setBottomAnchor(finishedText, 50.0);
        AnchorPane.setRightAnchor(finishedText, 420.0);
        LOGGER.info("Created finishedText");

        root.getChildren().add(targetText);
        targetText.setFont(stringFont);
        AnchorPane.setBottomAnchor(targetText, 50.0);
        AnchorPane.setLeftAnchor(targetText, 380.0);
        LOGGER.info("Created targetText");

        root.getChildren().add(firstCharacterText);
        firstCharacterText.setFont(stringFont);
        AnchorPane.setBottomAnchor(firstCharacterText, 50.0);
        AnchorPane.setLeftAnchor(firstCharacterText, 380.0);
        LOGGER.info("Created firstCharacterText");

        root.getChildren().add(cursor);
        AnchorPane.setBottomAnchor(cursor, 45.0);
        AnchorPane.setLeftAnchor(cursor, 380.0);
        LOGGER.info("Created cursor");

        root.getChildren().add(timerText);
        timerText.setFont(counterFont);
        AnchorPane.setTopAnchor(timerText, 20.0);
        AnchorPane.setLeftAnchor(timerText, 10.0);
        LOGGER.info("Created timerText");

        root.getChildren().add(correctCountText);
        correctCountText.setText("Correct: " + GameVars.correctCount); // TODO: make this translatable
        correctCountText.setFont(counterFont);
        AnchorPane.setTopAnchor(correctCountText, 80.0);
        AnchorPane.setLeftAnchor(correctCountText, 10.0);
        LOGGER.info("Created correctCountText");

        root.getChildren().add(wrongCountText);
        wrongCountText.setText("Wrong: " + GameVars.wrongCount); // TODO: make this translatable
        wrongCountText.setFont(counterFont);
        AnchorPane.setTopAnchor(wrongCountText, 140.0);
        AnchorPane.setLeftAnchor(wrongCountText, 10.0);
        LOGGER.info("Created wrongCountText");

        root.getChildren().add(cpsText);
        cpsText.setText("CPS: " + String.format("%.1f", GameVars.cps)); // TODO: make this translatable
        cpsText.setFont(counterFont);
        AnchorPane.setTopAnchor(cpsText,20.0);
        AnchorPane.setRightAnchor(cpsText, 40.0);
        LOGGER.info("Created cpsText");

        GameMap.generateGameMap(GameSettings.mapShapeType);
        if ( GameSettings.mapShapeType == GameMapShapeType.Rectangle) GameVars.mapLength = GameMapShapes.RECTANGLE_POINTS;

        root.getChildren().add(GameMap.mapPane);
        AnchorPane.setTopAnchor(GameMap.mapPane, 200.0);
        AnchorPane.setLeftAnchor(GameMap.mapPane, 112.0);
        LOGGER.info("Created mapPane");

        root.getChildren().add(gameOverText);
        gameOverText.setVisible(false);
        gameOverText.setFont(counterFont);
        AnchorPane.setTopAnchor(gameOverText, 270.0);
        AnchorPane.setLeftAnchor(gameOverText, 100.0);
        LOGGER.info("Created gameOverText");

        if ( GameSettings.isRandomString ) setNewString();
        else readArticleToString(GameArticle.ARTICLE_NAMES[0]);

        // renderer
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
        enemyTimer += 0.016667;
        secondTimer += 0.016667;
        keepTimer += 0.016667;

        // if reach a second, clear enemyTimer, add one second to totalTime
        if ( secondTimer > 1 ) {
            secondTimer = 0;
            GameVars.totalTimeSeconds += 1;
        }

        // enemy auto-forward/backward
        if ( enemyTimer > GameSettings.enemySpeed ) {
            enemyTimer = 0;
            if ( GameSettings.playerSprite == SpriteType.Police) GameMap.thief.moveForward(1);
            else GameMap.police.moveForward(1);
        }

        // set direction every frame to avoid some strange behavior
        GameMap.thief.setBetterDirection();

        // update timerText
        timerText.setText(String.format("%.1f", keepTimer));

        // calculate CPS ( Character Per Second )
        GameVars.cps = GameVars.correctCount / keepTimer;
        cpsText.setText("CPS: " + String.format("%.1f", GameVars.cps));


        // timeout check
        if ( GameVars.totalTimeSeconds >= GameSettings.timeout) {
            GameVars.gameOver = true;
            GameVars.timeoutGameOver = true;
            gameOverText.setVisible(true);
            setGameOverString();
        }

        // catch check
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

    private void readArticleToString(String articleName) throws IOException {
        LOGGER.info("Start reading article to targetString...");
        StringBuilder sb = new StringBuilder();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("assets/articles/" + articleName + ".txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(streamReader);
        for (String line; (line = br.readLine()) != null;) {
            sb.append(line).append(" ");
        }

        GameVars.targetString = sb.toString();
        GameVars.finishedString = "";
        targetText.setText(GameVars.targetString.substring(0, GameSettings.MAX_SHOW_LENGTH));
        firstCharacterText.setText(targetText.getText().substring(0,1));
        finishedText.setText("");
        LOGGER.info("Successfully read article to targetString");
    }

    private static void setNewString() {
        GameVars.targetString = generateRandomString(GameSettings.RANDOM_STRING_LENGTH, GameSettings.stringType);
        GameVars.finishedString = "";
        targetText.setText(GameVars.targetString.substring(0, GameSettings.MAX_SHOW_LENGTH));
        firstCharacterText.setText(targetText.getText().substring(0,1));
    }

    static String generateRandomString(int length, RandomStringType randomStringType) {
        LOGGER.info("Generating random string...");

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
        LOGGER.info("Random string generated: " + sb);

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

        stage.setResizable(false);
        stage.setScene(gameScene);

        String version = getClass().getPackage().getImplementationVersion();
        if ( version == null ) version = "DEV";
        else version = "v" + version;
        stage.setTitle("PoliceNThief - " + version);

        stage.show();
    }

    static void inputAction(String input) {
        if (GameVars.gameOver) {
            if ( input.equals("r") ) {
                // TODO: reset the game
            }
            return;
        }

        // TODO: play some stupid mechanical keyboard sounds

        String firstCharacter = String.valueOf(GameVars.targetString.charAt(0));
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
        // accumulate correctCount
        GameVars.correctCount += 1;
        correctCountText.setText("Correct: " + GameVars.correctCount); // TODO: make this translatable

        // set finishedText
        String correctChar = targetText.getText().substring(0, 1);
        GameVars.finishedString = GameVars.finishedString + correctChar;
        int startIndex = GameVars.finishedString.length() - GameSettings.MAX_SHOW_LENGTH;
        int endIndex = GameVars.finishedString.length();
        if ( startIndex < 0 ) startIndex = 0;
        finishedText.setText(GameVars.finishedString.substring(startIndex, endIndex));

        // set targetText
        int targetTextLength = Math.min(GameVars.targetString.length(), GameSettings.MAX_SHOW_LENGTH);
        targetText.setText(GameVars.targetString.substring(1, targetTextLength));
        GameVars.targetString = GameVars.targetString.substring(1);

        // set the character in the middle
        if ( !targetText.getText().isEmpty() ) {
            String firstChar = targetText.getText().substring(0,1);
            firstCharacterText.setText(firstChar);
        } else {
            setNewString();
        }

        // player moves
        if ( GameVars.correctCount % GameSettings.forwardTypes == 0 ) { // TODO: player speed relates to CPS.
            if ( GameSettings.playerSprite == SpriteType.Police ) GameMap.police.moveForward(1);
            else GameMap.thief.moveForward(1);
        }

        // if correct make the character black not red
        firstCharacterText.setFill(Color.BLACK);
    }

    public static void main(String[] args) {
        launch();
    }
}

