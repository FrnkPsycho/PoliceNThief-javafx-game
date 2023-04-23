package top.frnks.copchasethief;

public class RectangleMap extends GameMapShapes{
    public RectangleMap() {
        SHAPE = new String[] {
                "EDDDDDDDDDDDDDC",
                "W             S",
                "W             S",
                "W             S",
                "QAAAAAAAAAAAAAZ",
        };
        POINTS = 36;
        START =  new int[][] {
                {0, 0},
                {8, 0},
        };
    }
}
