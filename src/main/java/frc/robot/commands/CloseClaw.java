package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.kClaw;
import frc.robot.subsystems.Claw;

public class CloseClaw extends InstantCommand {

    private final Claw m_claw;

    public CloseClaw(Claw claw) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_claw = claw;

        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_claw.clawGoTo(kClaw.closePosition);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // m_claw.stopMot();
    }

}