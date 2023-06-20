package frc.robot.Util;

public class Color {
    
    public final int red;
    public final int green;
    public final int blue;

    /**
     * Constructs a color with the given RGB values
     * @param red (0 - 255)
     * @param green (0 - 255)
     * @param blue (0 - 255)
     */
    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static final Color kDenim = new Color(21, 96, 189);
    public static final Color kFirstRed = new Color(237, 28, 36);
    public static final Color kFirstBlue = new Color(0, 102, 179);

    public static final Color kBlack = new Color(0, 0, 0);
    public static final Color kWhite = new Color(255, 255, 255);

    public static final Color kGray = new Color(92, 92, 92);
    public static final Color kLightGray = new Color(117, 117, 117);
    public static final Color kDarkGray = new Color(54, 54, 54);

    public static final Color kPureRed = new Color(255, 0, 0);
    public static final Color kPureGreen = new Color(0, 255, 0);
    public static final Color kPureBlue = new Color(0, 0, 255);

    public static final Color kRed = new Color(217, 31, 17);
    public static final Color kOrange = new Color(247, 119, 27);
    public static final Color kYellow = new Color(242, 242, 5);
    public static final Color kLime = new Color(118, 247, 12);
    public static final Color kGreen = new Color(30, 222, 0);
    public static final Color kTurquoise = new Color(35, 252, 205);
    public static final Color kBlue = new Color(18, 108, 252);
    public static final Color kPurple = new Color(141, 16, 230);
    public static final Color kPink = new Color(242, 66, 255);
    public static final Color kMagenta = new Color(227, 7, 235);

    public static final Color kLightRed = new Color(247, 60, 35);
    public static final Color kLightOrange = new Color(247, 143, 57);
    public static final Color kLightYellow = new Color(250, 232, 67);
    public static final Color kLightLime = new Color(149, 252, 88);
    public static final Color kLightGreen = new Color(149, 252, 88);
    public static final Color kLightTurquoise = new Color(107, 250, 248);
    public static final Color kLightBlue = new Color(82, 194, 250);
    public static final Color kLightPurple = new Color(184, 106, 247);
    public static final Color kLightPink = new Color(255, 135, 205);
    public static final Color kLightMagenta = new Color(255, 97, 237);

    public static final Color kDarkRed = new Color(181, 20, 5);
    public static final Color kDarkOrange = new Color(171, 72, 7);
    public static final Color kDarkYellow = new Color(163, 150, 29);
    public static final Color kDarkLime = kGreen;
    public static final Color kDarkGreen = new Color(55, 105, 9);
    public static final Color kDarkTurquoise = new Color(17, 143, 116);
    public static final Color kDarkBlue = new Color(3, 17, 148);
    public static final Color kDarkPurple = new Color(58, 10, 84);
    public static final Color kDarkPink = new Color(130, 10, 116);
    public static final Color kDarkMagenta = new Color(110, 22, 99);


    public static final Color kRose = new Color(235, 7, 125);

    public static final Color kSalmon = new Color(252, 110, 91);

    public static final Color kGold = new Color(128, 117, 24);
    public static final Color kSilver = new Color(189, 189, 189);
    public static final Color kBronze = new Color(153, 101, 58);

    public static final Color kCyan = kDarkTurquoise;

    public static final Color kNavy = new Color(4, 12, 84);

    public static final Color kBrown = new Color(110, 72, 1);
    public static final Color kLightBrown = new Color(148, 108, 34);
    public static final Color kDarkBrown = new Color(97, 63, 0);

}
