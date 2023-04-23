package top.frnks.copchasethief;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import top.frnks.copchasethief.type.SpriteType;

public class Sprite extends Rectangle {
    boolean direction = false; // false: clockwise; true: reversed
    int mapIndex;
    final SpriteType type;
    final int BASE = GameSettings.SPRITE_SIZE;

    public Sprite(int x, int y, int w, int h, int mapIndex, SpriteType type, Color color) {
        super(w, h, color);
        this.type = type;
        this.mapIndex = mapIndex;
        setTranslateX(x);
        setTranslateY(y);
        setTexture();
    }

    public void moveForward(int step) {
        int nextIndex = 0;
//        if ( type == SpriteType.Thief ) {
//            if ( calculateNearestDistance(GameVars.mapLength) < 4 ) {
//                takeTurn();
//            }
//        }
        if (direction) nextIndex = (mapIndex - step);
        else nextIndex = (mapIndex + step);

        if ( nextIndex < 0 ) nextIndex = GameMap.mapPoints.size()-1;
        else if (nextIndex > GameMap.mapPoints.size()-1) nextIndex = 0;

        var p = GameMap.mapPoints.get(nextIndex);
        setTranslateX(p.getX());
        setTranslateY(p.getY());
        mapIndex = nextIndex;
    }

    public void takeTurn() {
        direction = !direction;
    }

    public void setBetterDirection() {
        int forwardDist = 0;
        int reverseDist = 0;
        Sprite enemy = GameMap.thief;
        if ( type == SpriteType.Thief ) enemy = GameMap.police;


        if ( !direction ) { // if clockwise
            if ( mapIndex > enemy.mapIndex ) forwardDist = GameVars.mapLength - mapIndex + enemy.mapIndex;
            else  forwardDist = enemy.mapIndex - mapIndex;
            reverseDist = GameVars.mapLength - forwardDist;
        } else {
             if ( mapIndex > enemy.mapIndex) forwardDist = mapIndex - enemy.mapIndex;
             else forwardDist = enemy.mapIndex - mapIndex;
             reverseDist = GameVars.mapLength - forwardDist;
        }
        if ( reverseDist > forwardDist) takeTurn();
    }

    public void setTexture() {
        String spriteName = "";
        switch (type) {
            case Police -> spriteName = "police";
            case Thief -> spriteName = "thief";
        }
        var asset = getClass().getClassLoader().getResourceAsStream("assets/textures/"+spriteName+".png");
        setFill(new ImagePattern(new Image(asset)));
    }

//    static int calculateNearestDistance(int mapLength) {
//        int a = Math.abs(GameMap.police.mapIndex - GameMap.thief.mapIndex);
//        int b = Math.abs(GameMap.thief.mapIndex - GameMap.police.mapIndex);
//        return Math.min(a, b);
//    }


}
