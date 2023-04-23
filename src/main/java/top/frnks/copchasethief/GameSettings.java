package top.frnks.copchasethief;

import top.frnks.copchasethief.type.GameMapShapeType;
import top.frnks.copchasethief.type.RandomStringType;
import top.frnks.copchasethief.type.SpriteType;

public class GameSettings {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int RANDOM_STRING_LENGTH = 128;
    public static final int MAX_SHOW_LENGTH = 16;
    public static final int SPRITE_SIZE = 32;
    public static int timeout = 120;
    public static int forwardTypes = 5;
    public static double enemySpeed = 2; // moveForward every {enemySpeed} second(s)
    public static boolean isRandomString = false;
    public static SpriteType playerSprite = SpriteType.Police;
    public static GameMapShapeType mapShapeType = GameMapShapeType.Rectangle;
    public static RandomStringType stringType = RandomStringType.ALL_LOWERCASE;
}
