package top.frnks.copchasethief;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import top.frnks.copchasethief.type.GameMapShapeType;
import top.frnks.copchasethief.type.SpriteType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameMap {
    public static final Logger LOGGER = Logger.getGlobal();
    public static final AnchorPane mapPane = new AnchorPane();
    public static final int BASE = GameSettings.SPRITE_SIZE;
    public static List<Point2D> mapPoints = new ArrayList<>();

    public static void generateGameMap(GameMapShapeType type) {
        LOGGER.info("Initializing GameMap...");

        int points = 0;
        String[] mapShape = new String[] { "" };

        int policeStartX = 0, policeStartY = 0;
        int thiefStartX = 0, thiefStartY = 0;
        int x=0, y=0;
        if (type == GameMapShapeType.Rectangle) {
            points = GameMapShapes.RECTANGLE_POINTS;
            mapShape = GameMapShapes.RECTANGLE;
            policeStartX = GameMapShapes.RECTANGLE_START[0][0];
            policeStartY = GameMapShapes.RECTANGLE_START[0][1];
            thiefStartX = GameMapShapes.RECTANGLE_START[1][0];
            thiefStartY = GameMapShapes.RECTANGLE_START[1][1];
            x = policeStartX;
            y = policeStartY;
        } else if ( type == GameMapShapeType.Hexagon ){
            points = GameMapShapes.HEXAGON_POINTS;
            mapShape = GameMapShapes.HEXAGON;
            policeStartX = GameMapShapes.HEXAGON_START[0][0];
            policeStartY = GameMapShapes.HEXAGON_START[0][1];
            thiefStartX = GameMapShapes.HEXAGON_START[1][0];
            thiefStartY = GameMapShapes.HEXAGON_START[1][1];
            x = policeStartX;
            y = policeStartY;
        }


        mapPoints.add(new Point2D(x*BASE, y*BASE));
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
            var p = new Point2D(x * BASE, y * BASE);
            LOGGER.info(p.toString());
            mapPoints.add(p);
        }

        for (Point2D p : mapPoints) {
            Rectangle roadShape = new Rectangle(p.getX(), p.getY(), BASE, BASE);
            roadShape.setFill(Color.GRAY);
            roadShape.setOpacity(80.0);
            mapPane.getChildren().add(roadShape);
        }

        Sprite police = new Sprite(policeStartX*BASE, policeStartY*BASE, BASE, BASE, SpriteType.Police, Color.BLUE);
        Sprite thief = new Sprite(thiefStartX*BASE, thiefStartY*BASE, BASE, BASE, SpriteType.Thief, Color.RED);
        mapPane.getChildren().add(police);
        mapPane.getChildren().add(thief);

        LOGGER.info("GameMap Initialized");
    }

}
