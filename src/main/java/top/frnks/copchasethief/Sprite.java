package top.frnks.copchasethief;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import top.frnks.copchasethief.type.SpriteType;

public class Sprite extends Rectangle {
    boolean reverse = false;
    int mapIndex;
    final SpriteType type;
    final int BASE = GameSettings.SPRITE_SIZE;

    public Sprite(int x, int y, int w, int h, int mapIndex, SpriteType type, Color color) {
        super(w, h, color);
        this.type = type;
        this.mapIndex = mapIndex;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void moveForward() {
        if ( reverse ) mapIndex = (mapIndex - 1);
        else mapIndex = (mapIndex + 1);

        if ( mapIndex < 0 ) mapIndex += GameMap.mapPoints.size();
        else if (mapIndex > GameMap.mapPoints.size()-1) mapIndex -= GameMap.mapPoints.size();

        var p = GameMap.mapPoints.get(mapIndex);
        setTranslateX(p.getX());
        setTranslateY(p.getY());
    }

    public void takeTurn() {
        reverse = !reverse;
    }
}
