package top.frnks.copchasethief;

import top.frnks.copchasethief.type.GameMapShapeType;
import top.frnks.copchasethief.type.RandomStringType;
import top.frnks.copchasethief.type.SpriteType;

public class GameSettings {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int TIMEOUT = 60;
    public static final int STRING_LENGTH = 2;
    public static final int SPRITE_SIZE = 64;
    public static final SpriteType PLAYER_SPRITE = SpriteType.Police;
    public static final GameMapShapeType MAP_SHAPE_TYPE = GameMapShapeType.Rectangle;
    public static RandomStringType STRING_TYPE = RandomStringType.ALL_LOWERCASE;
}
