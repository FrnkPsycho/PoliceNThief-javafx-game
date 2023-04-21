package top.frnks.copchasethief;

public class GameMapShapes {
    /*
    * Custom Map Markup Language:
    * W: Up, S: Down, A: Left, D: Right
    *
    * */
    public static final String[] RECTANGLE = new String[] {
            "DDDDDDDDS",
            "W       S",
            "WAAAAAAAA",
    };
    public static final int RECTANGLE_POINTS = 20;

    // TODO: translate other maps to CMML.
    public static final String[] HEXAGON = new String[] {
            "  ###  ",
            " #   # ",
            "#     #",
            " #   # ",
            "  ###  ",
    };
    public static final int HEXAGON_POINTS = 12;
}

