package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class EStopAnimation extends CommandBase {

    private final LED m_LEDs;

    private int timer;

    public EStopAnimation(LED LEDs) {
        m_LEDs = LEDs;

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
        timer++;
        if (timer % 10 <= 5) {
            m_LEDs.setColor(Color.kPureRed);
        } else {
            m_LEDs.setColor(Color.kBlack);
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
