package top.frnks.copchasethief;

public class HexagonMap extends GameMapShapes {
    public HexagonMap() {
        SHAPE = new String[] {
                "  DDC  ",
                " E   C ",
                "E     Z",
                " Q   Z ",
                "  QAA  ",
        };
        POINTS = 12;
        START = new int[][] {
                {0, 2},
                {6, 2},
        };
    }
}
