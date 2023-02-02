package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kClaw;
import frc.robot.subsystems.Claw;

public class changeClaw extends CommandBase {

    private final Claw m_claw;
    private final int pos;

    public changeClaw(Claw claw, int value) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_claw = claw;
        pos = value;

        addRequirements(m_claw);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_claw.clawGoTo(pos);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_claw.stopMot();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (pos - m_claw.getEncoderPosition()) >= kClaw.encoderOffset;
    }

}
