package top.frnks.copchasethief;

public class GameMapShapes {
    /*
    * Custom Map Markup Language:
    * W: Up, S: Down, A: Left, D: Right
    *
    * QWE
    * ASD
    * Z C
    * */
    public static final String[] RECTANGLE = new String[] {
            "EDDDDDDDC",
            "W       S",
            "QAAAAAAAZ",
    };
    public static final int RECTANGLE_POINTS = 20;
    // Set the start point of police and thief, 0->police, 1->thief
    public static final int[][] RECTANGLE_START = new int[][] {
            {0, 0},
            {0, 8},
    };

    // TODO: translate other maps to CMML.
    public static final String[] HEXAGON = new String[] {
            "  DDC  ",
            " E   C ",
            "E     Z",
            " Q   Z ",
            "  QAA  ",
    };
    public static final int HEXAGON_POINTS = 12;
    public static final int[][] HEXAGON_START = new int[][] {
            {2, 0},
            {2, 6},
    };

}

