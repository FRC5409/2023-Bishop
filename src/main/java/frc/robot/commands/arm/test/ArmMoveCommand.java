package frc.robot.commands.arm.test;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.test.ArmTest;

/**
 * @author Logan Dhillon
 */
public class ArmMoveCommand extends CommandBase {
    private final ArmTest arm;
    private final double setPoint;

    public ArmMoveCommand(ArmTest arm, double setPoint) {
        this.arm = arm;
        this.setPoint = setPoint;

        addRequirements(this.arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        arm.rotateTo(setPoint);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
        return Math.abs(setPoint - arm.getAngle()) <= 1;
    }

}