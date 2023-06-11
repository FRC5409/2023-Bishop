package frc.robot.Util;

public class Color {
    public final int red;
    public final int green;
    public final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static final Color kBlack = new Color(0, 0, 0);
    public static final Color kWhite = new Color(255, 255, 255);

    public static final Color kPureRed = new Color(255, 0, 0);
    public static final Color kPureGreen = new Color(0, 255, 0);
    public static final Color kPureBlue = new Color(0, 0, 255);



}
