package top.frnks.copchasethief;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import top.frnks.copchasethief.type.GameMapShapeType;
import top.frnks.copchasethief.type.RandomStringType;
import top.frnks.copchasethief.type.SpriteType;

public class GameSettingsMenu {
    public static final AnchorPane settingsMenuRoot = new AnchorPane();
    public static final Rectangle settingsMenuBackground = new Rectangle();
    public static final VBox settingsBox = new VBox(20);
    public static final HBox timeoutBox = new HBox();
    public static final Label timeoutLabel = new Label("Timeout (Second):");
    public static final TextField timeoutField = new TextField(String.valueOf(GameSettings.timeout));
    public static final HBox charactersToMoveBox = new HBox();
    public static final Label charactersToMoveLabel = new Label("Characters to move Player:");
    public static final Slider charactersToMoveSlider = new Slider(1, 30, GameSettings.charactersToMove);
    public static final TextField charactersToMoveField = new TextField();
    public static final HBox enemySpeedBox = new HBox();
    public static final Label enemySpeedLabel = new Label("Seconds to move Enemy:");
    public static final Slider enemySpeedSlider = new Slider(0, 10, GameSettings.enemySpeed);
    public static final TextField enemySpeedField = new TextField();
    public static final CheckBox randomStringModeCheckBox = new CheckBox("Random String Mode");
    public static final HBox randomStringTypeBox = new HBox();
    public static final Label randomStringTypeLabel = new Label("Random String Type:");
    public static final ComboBox<RandomStringType> randomStringTypeComboBox = new ComboBox<>();
    public static final HBox articleBox = new HBox();
    public static final Label articleLabel = new Label("Article:");
    public static final ComboBox<String> articleComboBox = new ComboBox<>();
    public static final HBox playerSpriteBox = new HBox();
    public static final Label playerSpriteLabel = new Label("Player Character:");
    public static final ComboBox<SpriteType> playerSpriteComboBox = new ComboBox<>();
    public static final HBox mapShapeTypeBox = new HBox();
    public static final Label mapShapeTypeLabel = new Label("Map Shape:");
    public static final ComboBox<GameMapShapeType> mapShapeTypeComboBox = new ComboBox<>();
    public static final HBox buttonsBox = new HBox(5);
    public static final Button resetButton = new Button("Reset");
    public static final Button applyButton = new Button("Apply");
    public static final Button acceptButton = new Button("Accept");
    public static final Button backButton = new Button("Back");

    public static void createSettingsMenu() {
        settingsMenuRoot.getChildren().add(settingsBox);
        settingsBox.getChildren().add(timeoutBox);
        settingsBox.getChildren().add(charactersToMoveBox);
        settingsBox.getChildren().add(enemySpeedBox);
        settingsBox.getChildren().add(randomStringModeCheckBox);
        settingsBox.getChildren().add(randomStringTypeBox);
        settingsBox.getChildren().add(articleBox);
        settingsBox.getChildren().add(playerSpriteBox);
        settingsBox.getChildren().add(mapShapeTypeBox);
        settingsBox.getChildren().add(buttonsBox);

        buttonsBox.getChildren().add(applyButton);
        buttonsBox.getChildren().add(resetButton);
        buttonsBox.getChildren().add(acceptButton);
        buttonsBox.getChildren().add(backButton);

        applyButton.setOnAction(event -> {
            saveSettings();
        });
        resetButton.setOnAction(event -> {
            // TODO: reset settings
        });
        acceptButton.setOnAction(event -> {
            saveSettings();
            settingsMenuRoot.getScene().setRoot(GameMainMenu.mainMenuRoot);
        });
        backButton.setOnAction(event -> {
            settingsMenuRoot.getScene().setRoot(GameMainMenu.mainMenuRoot);
        });



        timeoutBox.getChildren().add(timeoutLabel);
        timeoutBox.getChildren().add(timeoutField);
        timeoutField.textProperty().addListener((observable, oldValue, newValue) -> {
            if ( !newValue.isEmpty() ) {
                if (!newValue.matches("\\d*")) {
                    timeoutField.setText(newValue.replaceAll("\\D", ""));
                }
                if ( Integer.parseInt(newValue) > 9999 ) timeoutField.setText("9999");
            }
        });

        charactersToMoveBox.getChildren().add(charactersToMoveLabel);
        charactersToMoveBox.getChildren().add(charactersToMoveSlider);
        charactersToMoveBox.getChildren().add(charactersToMoveField);
        charactersToMoveSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            charactersToMoveSlider.setValue(Math.round(newValue.doubleValue()));
        });
        charactersToMoveField.textProperty().bind(charactersToMoveSlider.valueProperty().asString("%.0f"));

        enemySpeedBox.getChildren().add(enemySpeedLabel);
        enemySpeedBox.getChildren().add(enemySpeedSlider);
        enemySpeedBox.getChildren().add(enemySpeedField);
        enemySpeedSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            enemySpeedSlider.setValue(Math.round(newValue.doubleValue() * 10.0) / 10.0);
        }));
        enemySpeedField.textProperty().bind(enemySpeedSlider.valueProperty().asString("%.1f"));

        randomStringModeCheckBox.setSelected(GameSettings.randomStringMode);

        randomStringTypeBox.getChildren().add(randomStringTypeLabel);
        randomStringTypeBox.getChildren().add(randomStringTypeComboBox);

        randomStringTypeComboBox.setItems(FXCollections.observableArrayList(RandomStringType.values()));
        randomStringTypeComboBox.getSelectionModel().select(GameSettings.randomStringType);

        articleBox.getChildren().add(articleLabel);
        articleBox.getChildren().add(articleComboBox);

        articleComboBox.setItems(FXCollections.observableArrayList(GameArticle.ARTICLE_NAMES));
        articleComboBox.getSelectionModel().select(GameSettings.articleName);

        playerSpriteBox.getChildren().add(playerSpriteLabel);
        playerSpriteBox.getChildren().add(playerSpriteComboBox);
        playerSpriteComboBox.setItems(FXCollections.observableArrayList(SpriteType.values()));
        playerSpriteComboBox.getSelectionModel().select(GameSettings.playerSprite);

        mapShapeTypeBox.getChildren().add(mapShapeTypeLabel);
        mapShapeTypeBox.getChildren().add(mapShapeTypeComboBox);
        mapShapeTypeComboBox.setItems(FXCollections.observableArrayList(GameMapShapeType.values()));
        mapShapeTypeComboBox.getSelectionModel().select(GameSettings.mapShapeType);
//        articleComboBox.setOnAction(event -> {
//            GameSettings.articleName = articleComboBox.getValue();
//        });

//        randomStringTypeComboBox.setOnAction(event -> {
//            GameSettings.randomStringType = randomStringTypeComboBox.getValue();
//        });
    }

    private static void saveSettings() {
        GameSettings.timeout = Integer.parseInt(timeoutField.getText());
        GameSettings.charactersToMove = Integer.parseInt(charactersToMoveField.getText());
        GameSettings.enemySpeed = enemySpeedSlider.getValue();
        GameSettings.randomStringMode = randomStringModeCheckBox.isSelected();
        GameSettings.articleName = articleComboBox.getValue();
        GameSettings.playerSprite = playerSpriteComboBox.getValue();
        GameSettings.mapShapeType = mapShapeTypeComboBox.getValue();
        GameSettings.randomStringType = randomStringTypeComboBox.getValue();
    }
}
