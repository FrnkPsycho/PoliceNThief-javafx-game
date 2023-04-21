package top.frnks.copchasethief;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import top.frnks.copchasethief.type.GameMapShapeType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameMap {
    public static final Logger LOGGER = Logger.getGlobal();
    public static final AnchorPane mapPane = new AnchorPane();
    public static List<Point2D> mapPoints = new ArrayList<>();

    public static void generateGameMap(GameMapShapeType type) {
        LOGGER.info("Initializing GameMap...");

        int points = 0;
        String[] mapShape = new String[] { "" };

        int x=0, y=0;
        if (type == GameMapShapeType.Rectangle) {
            points = GameMapShapes.RECTANGLE_POINTS;
            mapShape = GameMapShapes.RECTANGLE;
            x = GameMapShapes.RECTANGLE_START[0][0];
            y = GameMapShapes.RECTANGLE_START[0][1];
        } else if ( type == GameMapShapeType.Hexagon ){
            points = GameMapShapes.HEXAGON_POINTS;
            mapShape = GameMapShapes.HEXAGON;
            x = GameMapShapes.HEXAGON_START[0][0];
            y = GameMapShapes.HEXAGON_START[0][1];
        }


        mapPoints.add(new Point2D(x*GameSettings.SPRITE_SIZE, y*GameSettings.SPRITE_SIZE));
        for ( int acc=1 ; acc < points; acc++ ) {
            char mark = mapShape[y].charAt(x);
            switch (mark) {
                // TODO: make map point contains corner data?
                case 'D' -> x++;
                case 'S' -> y++;
                case 'A' -> x--;
                case 'W' -> y--;
                case 'Q' -> { x--; y--;}
                case 'E' -> { x++; y--;}
                case 'C' -> { x++; y++;}
                case 'Z' -> { x--; y++;}
            }
            if ( y<0 ) y = 0;
            if ( x<0 ) x = 0;
            if ( y>mapShape.length-1) y = mapShape.length-1;
            if ( x>mapShape[y].length()-1) x = mapShape[y].length()-1;
            var p = new Point2D(x * GameSettings.SPRITE_SIZE, y * GameSettings.SPRITE_SIZE);
            LOGGER.info(p.toString());
            mapPoints.add(p);
        }

        for (Point2D p : mapPoints) {
            Rectangle roadShape = new Rectangle(p.getX(), p.getY(), GameSettings.SPRITE_SIZE, GameSettings.SPRITE_SIZE);
            roadShape.setFill(Color.GRAY);
            roadShape.setOpacity(80.0);
            mapPane.getChildren().add(roadShape);
        }

        LOGGER.info("GameMap Initialized");
    }

}
