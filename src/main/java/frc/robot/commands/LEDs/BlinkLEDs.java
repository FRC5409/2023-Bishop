package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class BlinkLEDs extends CommandBase {

    private final LED LEDs;
    private final Color onColor;
    private       Color offColor;
    private final int blinkSpeed;
    private final int blinkCount;

    private int timer;

    private boolean getColor = false;

    public BlinkLEDs(LED LEDs, Color colorOn, Color colorOff, int blinkSpeed, int blinkCount) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.LEDs = LEDs;
        onColor = colorOn;
        offColor = colorOff;
        this.blinkSpeed = blinkSpeed;
        this.blinkCount = blinkCount;

        addRequirements(LEDs);
    }

    public BlinkLEDs(LED LEDs, Color colorOff, int blinkSpeed, int blinkCount) {
      this(LEDs, null, colorOff, blinkSpeed, blinkCount);
      getColor = true;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer = 0;
        if (getColor) {
          offColor = LEDs.getColor();
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        timer++;
        if (timer % blinkSpeed < blinkSpeed / 2) {
          LEDs.setColor(offColor);
        } else {
          LEDs.setColor(onColor);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      if (getInterruptionBehavior() == InterruptionBehavior.kCancelSelf) {
        LEDs.setColor(onColor);
      }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.floor(timer / blinkSpeed) >= blinkCount && blinkCount != -1;
    }

}