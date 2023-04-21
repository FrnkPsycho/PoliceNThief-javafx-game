package top.frnks.copchasethief;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import top.frnks.copchasethief.type.GameMapShapeType;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public static final AnchorPane mapPane = new AnchorPane();
    public static List<Point2D> mapPoints = new ArrayList<>();

    public static void generateGameMap(GameMapShapeType type) {
        int points = 0;
        String[] mapShape = new String[] { "" };

        if (type == GameMapShapeType.Rectangle) {
            points = GameMapShapes.RECTANGLE_POINTS;
            mapShape = GameMapShapes.RECTANGLE;
        };
        for (int i = 0; i < mapShape.length; i++ ) {
            for ( int j = 0 ; j < mapShape[i].length(); j++ ) {
                if ( mapShape[i].charAt(j) == '#') {
                    var p = new Point2D(j * GameSettings.SPRITE_SIZE, i * GameSettings.SPRITE_SIZE);
                    // debug
                    System.out.println(p);
                    mapPoints.add(p);
                }
            }
        }
        // debug
        for ( int i = 0 ; i < mapPoints.size(); i++ ) {
            Point2D p = mapPoints.get(i);
            Rectangle roadShape = new Rectangle(p.getX(), p.getY(), GameSettings.SPRITE_SIZE, GameSettings.SPRITE_SIZE);
            roadShape.setFill(Color.GRAY);
            roadShape.setOpacity(80.0);
            mapPane.getChildren().add(roadShape);
        }
    }

}
