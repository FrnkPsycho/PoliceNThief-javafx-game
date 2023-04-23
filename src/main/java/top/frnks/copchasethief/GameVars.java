package top.frnks.copchasethief;

public class GameVars {
    public static boolean gamePaused = true;
    public static long totalTimeSeconds = 0;
    public static long correctCount = 0;
    public static long wrongCount = 0;
    public static double cps = 0;
    public static boolean gameOver = false;
    public static boolean timeoutGameOver = false;
    public static boolean caughtGameOver = false;
    public static int mapLength = 0;

    public static String targetString = "";
    public static String finishedString = "";

    public static void resetGameVars() {
        gamePaused = false;
        totalTimeSeconds = 0;
        correctCount = 0;
        wrongCount = 0;
        cps = 0;
        gameOver = false;
        timeoutGameOver = false;
        caughtGameOver = false;
        mapLength = 0;
        targetString = "";
        finishedString = "";
    }
}
