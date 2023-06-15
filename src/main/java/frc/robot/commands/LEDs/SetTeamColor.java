package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class SetTeamColor extends CommandBase {

    private final LED m_LEDs;

    private Color lastColor;

    public SetTeamColor(LED LEDs) {
        m_LEDs = LEDs;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_LEDs);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        lastColor = m_LEDs.getTeamColor();
        m_LEDs.setColor(lastColor);
    }

    @Override
    public void execute() {
        if (lastColor != m_LEDs.getTeamColor()) {
            lastColor = m_LEDs.getTeamColor();
            m_LEDs.setColor(lastColor);
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
