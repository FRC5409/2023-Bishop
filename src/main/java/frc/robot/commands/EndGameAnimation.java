package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kCANdle;
import frc.robot.subsystems.Candle;

public class EndGameAnimation extends CommandBase {

    private final Candle m_candle;

    private int timer = 0;

    public EndGameAnimation(Candle candle) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_candle = candle;

        addRequirements(m_candle);
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
        if (timer % 2 == 0) {
            m_candle.setColor(255, 255, 255);
        } else {
            m_candle.setColor(kCANdle.kColors.idle[0], kCANdle.kColors.idle[1], kCANdle.kColors.idle[2]);
        }
    }

    // Allow this command to run when disabled
    @Override
    public boolean runsWhenDisabled() {
        return true;
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