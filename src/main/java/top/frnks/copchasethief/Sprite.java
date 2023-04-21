package top.frnks.copchasethief;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import top.frnks.copchasethief.type.SpriteType;

class Sprite extends Rectangle {
    boolean dead = false;
    final SpriteType type;
    Pane pane = GameApplication.root;

    public Sprite(int x, int y, int w, int h, SpriteType type, Color color) {
        super(w, h, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void moveForward() {
        // TODO: set sprite to next map node?
    }
}
