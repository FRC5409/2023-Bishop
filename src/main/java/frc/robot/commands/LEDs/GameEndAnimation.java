package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kCANdle.kColors;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class GameEndAnimation extends CommandBase {

    private final LED m_LEDs;

    private int timer;

    public GameEndAnimation(LED LEDs) {
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
        if (timer % 5 < 3) {
            m_LEDs.setColor(kColors.idle);
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
        return timer >= 140;
    }

}