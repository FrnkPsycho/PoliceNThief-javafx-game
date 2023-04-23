package top.frnks.copchasethief;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import top.frnks.copchasethief.type.GameMapShapeType;
import top.frnks.copchasethief.type.SpriteType;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class GameMap {
    public static final Logger LOGGER = Logger.getGlobal();
    public static final AnchorPane mapPane = new AnchorPane();
    public static final int BASE = GameSettings.SPRITE_SIZE;
    public static List<Point2D> mapPoints = new ArrayList<>();
    public static Sprite police = null;
    public static Sprite thief = null;

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


//        mapPoints.add(new Point2D(x*BASE, y*BASE));
        for ( int acc=0 ; acc < points; acc++ ) {
            String roadDirection = "";
            double rotate = 0;
            var p = new Point2D(x * BASE, y * BASE);
            LOGGER.info(p.toString());
            mapPoints.add(p);



            boolean isFill = false;
            char mark = mapShape[y].charAt(x);
            switch (mark) {
                // TODO: make map point contains corner data?
                case 'D' -> { x++; roadDirection = "horizontal";}
                case 'S' -> { y++; roadDirection = "vertical";}
                case 'A' -> { x--; roadDirection = "horizontal";}
                case 'W' -> { y--; roadDirection = "vertical";}
                case 'Q' -> { x--; y--; rotate = -90;}
                case 'E' -> { x++; y--; rotate = 0;}
                case 'C' -> { x++; y++; rotate = 90;}
                case 'Z' -> { x--; y++; rotate = 180;}
                case ' ' -> { isFill = true;}
            }

            if ( y<0 ) y = 0;
            if ( x<0 ) x = 0;
            if ( y>mapShape.length-1) y = mapShape.length-1;
            if ( x>mapShape[y].length()-1) x = mapShape[y].length()-1;

            Rectangle mapSprite = new Rectangle(p.getX(), p.getY(), BASE, BASE);

            InputStream asset = null;
            if ( isFill ) {
                int index = new Random().nextInt(1, GameSettings.FILL_TEXTURES);
                asset = GameApplication.class.getClassLoader().getResourceAsStream("assets/textures/fill_" + index + ".png");
            }
            else if ( roadDirection.isEmpty() ) asset = GameApplication.class.getClassLoader().getResourceAsStream("assets/textures/road_corner.png");
            else asset = GameApplication.class.getClassLoader().getResourceAsStream("assets/textures/road_" + roadDirection + ".png");

            var img = new ImagePattern(new Image(asset));
            mapSprite.setFill(img);
            mapSprite.setRotate(rotate);
            mapPane.getChildren().add(mapSprite);

        }

//        for (Point2D p : mapPoints) {
//            Rectangle roadShape = new Rectangle(p.getX(), p.getY(), BASE, BASE);
//            roadShape.setFill(Color.GRAY);
//            roadShape.setOpacity(80.0);
//            mapPane.getChildren().add(roadShape);
//        }

        int thiefIndex = findSpriteMapIndex(thiefStartX*BASE, thiefStartY*BASE);
        thief = new Sprite(thiefStartX*BASE, thiefStartY*BASE, BASE, BASE, thiefIndex, SpriteType.Thief, Color.RED);
        police = new Sprite(policeStartX*BASE, policeStartY*BASE, BASE, BASE,0, SpriteType.Police, Color.BLUE);
        mapPane.getChildren().add(thief);
        mapPane.getChildren().add(police);

        LOGGER.info("GameMap Initialized");
    }

    static int findSpriteMapIndex(int x, int y) {
        for ( int i=0; i<mapPoints.size(); i++ ) {
            var p = mapPoints.get(i);
            if ( x == p.getX() && y == p.getY() ) return i;
        }
        return 0;
    }

}
