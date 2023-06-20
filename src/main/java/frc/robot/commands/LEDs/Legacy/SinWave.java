package frc.robot.commands.LEDs.Legacy;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kCANdle.kColors;
import frc.robot.Constants.kCANdle.kConfig;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class SinWave extends CommandBase {

    private final LED m_LEDs;
    private final Color onColor;
    private final Color offColor;

    private int timer;

    public SinWave(LED LEDs, Color onColor, Color offColor) {
        m_LEDs = LEDs;
        this.onColor = onColor;
        this.offColor = offColor;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_LEDs);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        timer += kColors.kSpeed;

        for (int i = -kColors.LEDSinCount; i < kConfig.LEDCount + kColors.LEDSinCount * 2; i += kColors.LEDSinCount) {
            int index = (int) (i + Math.floor(timer) % (kColors.LEDSinCount * 2));
            if (Math.abs(i % (kColors.LEDSinCount * 2)) == 0) {
                m_LEDs.setLEDColorAtFor(index, kColors.LEDSinCount, onColor);
            } else {
                m_LEDs.setLEDColorAtFor(index, kColors.LEDSinCount, offColor);
            }
        } 
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
