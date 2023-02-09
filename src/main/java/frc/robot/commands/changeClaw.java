package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kClaw;
import frc.robot.subsystems.Claw;

public class changeClaw extends CommandBase {

    private final Claw m_claw;
    private final boolean openState;
    private double pos;

    public changeClaw(Claw claw, boolean open) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_claw = claw;
        openState = open;

        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (openState) {
           pos = kClaw.openPosition;
           m_claw.openClaw();
        } else {
            pos = kClaw.closePosition;
            m_claw.closeClaw();
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_claw.stopMot();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return 
        //open state
        (Math.abs(pos - m_claw.getEncoderPosition()) <= kClaw.encoderOffset && m_claw.getDistanceFromClaw() <= kClaw.objectRange && openState)
        || 
        //close state
        (Math.abs(pos - m_claw.getEncoderPosition()) <= kClaw.encoderOffset && !openState);
        // Math.abs(pos - m_claw.getEncoderPosition()) <= kClaw.encoderOffset;
    }

}
