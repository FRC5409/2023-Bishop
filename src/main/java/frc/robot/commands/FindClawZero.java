package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kCANBus;
import frc.robot.Constants.kClaw;
import frc.robot.subsystems.Claw;

public class FindClawZero extends CommandBase {

    private final Claw m_claw;
    private double timer = 0;

    public FindClawZero(Claw claw) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_claw = claw;

        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_claw.spinAt(kClaw.zeroSpeed);
        timer = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        timer++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_claw.zeroEncoder();
        m_claw.stopMot();
        new changeClaw(m_claw, kClaw.openPosition);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() { 
        // return m_claw.getAverageCurrent() >= kClaw.outputCurrentMaxLimit;
        return m_claw.isStalled() && timer >= 5;
    }

}
