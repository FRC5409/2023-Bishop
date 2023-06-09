package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class EStopAnimation extends CommandBase {

    private final LED m_LEDs;
    private final Color onColor;
    private final Color offColor;

    private int timer;

    public EStopAnimation(LED LEDs, Color onColor, Color offColor) {
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
        if (timer % 10 <= 5) {
            m_LEDs.setColor(onColor);
        } else {
            m_LEDs.setColor(offColor);
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
