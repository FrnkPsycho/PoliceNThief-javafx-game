package top.frnks.copchasethief;

import top.frnks.copchasethief.type.GameMapShapeType;
import top.frnks.copchasethief.type.RandomStringType;
import top.frnks.copchasethief.type.SpriteType;

public class GameSettings {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int RANDOM_STRING_LENGTH = 128;
    public static final int MAX_SHOW_LENGTH = 16;
    public static final int SPRITE_SIZE = 64;
    public static int TIMEOUT = 60;
    public static int FORWARD_TYPES = 5;
    public static boolean isRandomString = true;
    public static SpriteType PLAYER_SPRITE = SpriteType.Police;
    public static GameMapShapeType MAP_SHAPE_TYPE = GameMapShapeType.Rectangle;
    public static RandomStringType STRING_TYPE = RandomStringType.ALL_LOWERCASE;
}
