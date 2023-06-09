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

    public LED() {
        candle = new CANdle(kConfig.CANID);

        candle.configFactoryDefault();
        candle.configLEDType(LEDStripType.GRB);
        candle.animate(null, 0);

        setColor(new Color(0, 0, 0));

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
      // timer++;
      // if (currentAnimationSlot == -1) {
      //   for (int i = 0; i < 4; i++) {
      //     candle.animate(null, i);
      //   }
      // } else if (currentAnimationSlot == 2) {
      //   //Sin Flow 
      // } else if (currentAnimationSlot == 3) {
      //   //Sin Wave
      // } else if (currentAnimationSlot == 4) {
      //   //Charged Up
      // } else if (currentAnimationSlot == 5) {
      //   //E Stopped
      // } else if (currentAnimationSlot == 6) {
        //End Game
        // animationTime++;
        // if (animationTime % 5 < 3) {
        //   setColor(kCANdle.kColors.idle[0], kCANdle.kColors.idle[1], kCANdle.kColors.idle[2]);
        // } else {
        //   setColor(LEDOff[0], LEDOff[1], LEDOff[2]);
        // }
        // if (animationTime >= 140) {
        //   idleAnimation();
        // }
      // }

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

    public void idleAnimation() {
      setColor(kColors.idle);
    }

    public Color getColor() {
      Color color = new Color(0, 0, 0);
      return color;
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
     * Setting the animation to the built in animation during game
     */

     public void setTeamColor() {
      if (DriverStation.getAlliance() == Alliance.Red) {
        setColor(new Color(255, 0, 0));
      } else {
        setColor(new Color(0, 0, 255));
      }
    }

    /**
     * Gets the current team color
     * @return The team color, if no team color can be reached it returns black
     */
    public Color getTeamColor() {
      if (DriverStation.getAlliance() == Alliance.Red) {
        return new Color(255, 0, 0);
      } else if (DriverStation.getAlliance() == Alliance.Blue) {
        return new Color(0, 0, 255);
      }

      return kColors.black;
    }

}