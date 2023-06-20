package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kCANdle;
import frc.robot.Constants.kCANdle.kColors;
import frc.robot.Constants.kCANdle.kConfig;
import frc.robot.Util.Color;

public class LED extends SubsystemBase {

    private final CANdle candle;

    private int LEDColors[][] = new int[kCANdle.kConfig.LEDCount][3];
    private int lastLEDColors[][] = new int[kCANdle.kConfig.LEDCount][3];

    private int timer = 0;
    private Alliance currentAlliance;

    private Color lastColor;

    public LED() {
        candle = new CANdle(kConfig.CANID);

        candle.configFactoryDefault();
        candle.configLEDType(LEDStripType.GRB);
        candle.animate(null, 0);

        setColor(Color.kBlack);

        configBrightness(kConfig.defaultBrightness);

    }

    /**
     * Configs the brightness of all the LEDs
     * @param brightness brightness between 0 and 1;
     */


    public void configBrightness(double brightness) {
        candle.configBrightnessScalar(brightness);
    }

    /**
     * Sets all of the LEDs to
     * @param r : Red 0   - 255
     * @param g : Green 0 - 255
     * @param b : Blue 0  - 255
     */

    public void setColor(Color color) {
      for (int i = 0; i < kCANdle.kConfig.LEDCount; i++) {
        setArrayLEDs(i, color.red, color.green, color.blue);
        lastLEDColors[i][0] = color.red;
        lastLEDColors[i][1] = color.green;
        lastLEDColors[i][2] = color.blue;
      }
      candle.setLEDs(color.red, color.green, color.blue);
    }

    /**
     * Turn on LED
     * @param index at index
     * @param brightness brightness of LED
     */

    public void setLEDColorAt(int index, Color color) {
      setArrayLEDs(index, color.red, color.green, color.blue);
    }

    /**
     * Turn o LnEDs
     * @param index at index
     * @param count count to turn on
     */

    public void setLEDColorAtFor(int index, int count, Color color) {
      for (int i = index; i < index + count; i++) {
        setArrayLEDs(i, color.red, color.green, color.blue);
      }
    }

    /**
     * Turn on LEDs
     * @param index at index
     * @param count count to turn on
     * @param MIN minimum value
     * @param MAX maximum value
     */

    public void setLEDColorAtForWithMinAndMax(int index, int count, int MIN, int MAX, Color color) {
      if (MAX >= kCANdle.kConfig.LEDCount) {
        MAX = kCANdle.kConfig.LEDCount - 1;
      }
      for (int i = index; i < index + count; i++) {
        if (i < MAX && i > MIN) {
          for (int j = index; j < index + count; j++) {
            setArrayLEDs(j, color.red, color.green, color.blue);
          }
        }
      }
    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run

      // System.out.println(getCurrentCommand());

      if (getCurrentCommand() == null && DriverStation.isDisabled()) {
        timer++;
        
        Color color;

        currentAlliance = DriverStation.getAlliance();

        if (!DriverStation.isDSAttached()) {
            color = Color.kBlack;
        } else if (currentAlliance == Alliance.Red) {
            color = Color.kPureRed;
        } else if (currentAlliance == Alliance.Blue) {
            color = Color.kPureBlue;
        } else {
            color = Color.kWhite;
        }

        double animationTime = Math.sin(timer * kColors.sinFrequency) * kColors.sinFrequencySpeed;
    
        if (Math.abs(animationTime) >= 19.5) {
            timer += 4;
        }
    
        animationTime = Math.floor(animationTime);
    
        for (int i = -kColors.LEDSinCount * 2; i < kConfig.LEDCount; i += kColors.LEDSinCount) {
            int index = (int) (i + animationTime);
            if (Math.abs(i % (kColors.LEDSinCount * 2)) == 0) {
                setLEDColorAtForWithMinAndMax(index, kColors.LEDSinCount, 7, kConfig.LEDCount - kConfig.LEDInnerLeft, color);
            } else {
                setLEDColorAtForWithMinAndMax(index, kColors.LEDSinCount, 7, kConfig.LEDCount - kConfig.LEDInnerLeft, kColors.idle);
            }
        }
    
        animationTime *= -1;
    
        for (int i = kConfig.LEDCount - kConfig.LEDInnerLeft - kColors.LEDSinCount * 4; i < kConfig.LEDCount + kColors.LEDSinCount * 2; i += kColors.LEDSinCount) {
            int index = (int) (i + animationTime);
            if (Math.abs(i % (kColors.LEDSinCount * 2)) <= kColors.LEDSinCount) {
                setLEDColorAtForWithMinAndMax(index, kColors.LEDSinCount, kConfig.LEDCount - kConfig.LEDInnerLeft - 1, kConfig.LEDCount, kColors.idle);
            } else {
                setLEDColorAtForWithMinAndMax(index, kColors.LEDSinCount, kConfig.LEDCount - kConfig.LEDInnerLeft - 1, kConfig.LEDCount, color);
            }

        }
      }

      //Setting LED colors
      for (int i = 8; i < kCANdle.kConfig.LEDCount; i++) {
        if (LEDColors[i][0] != lastLEDColors[i][0] || LEDColors[i][1] != lastLEDColors[i][1] || LEDColors[i][2] != lastLEDColors[i][2]) {
          candle.setLEDs(LEDColors[i][0], LEDColors[i][1], LEDColors[i][2], 0, i, 1);
        }
        lastLEDColors[i][0] = LEDColors[i][0];
        lastLEDColors[i][1] = LEDColors[i][1];
        lastLEDColors[i][2] = LEDColors[i][2];
      }
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation

    }

    /**
     * Prints what the LED is at index
     * @param index the index of LED you are looking for
     */
    public void printLEDAtIndex(int index) {
      System.out.printf("LEDs are currently set to:%nRed: %s, green: %s, blue: %s %n", LEDColors[index][0], LEDColors[index][1], LEDColors[index][2]);
    }

    /**
     * Sets the idle color of the LEDs
     */
    public void idleAnimation() {
      setColor(kColors.idle);
    }

    /**
     * Gets the current color applied to the LEDs
     * @return The current color
     */
    public Color getColor() {
      return new Color(LEDColors[9][0], LEDColors[9][1], LEDColors[9][2]);
    }

    /**
     * @param index at array index
     * @param r red value
     * @param g green value
     * @param b blue value
     */

    public void setArrayLEDs(int index, int r, int g, int b) {
      if (index < kCANdle.kConfig.LEDCount) {
        LEDColors[index][0] = r;
        LEDColors[index][1] = g;
        LEDColors[index][2] = b;
      }
    }

    /**
     * Gets the current team color
     * @return The team color, if no team color can be reached it returns black
     */
    public Color getTeamColor() {
      if (DriverStation.getAlliance() == Alliance.Red) {
        return Color.kPureRed;
      } else if (DriverStation.getAlliance() == Alliance.Blue) {
        return Color.kPureBlue;
      }

      return Color.kBlack;
    }

    public void setLastColor(Color color) {
      lastColor = color;
    }

    public Color getLastColor() {
      return lastColor;
    }

}