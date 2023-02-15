package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class Auto extends SequentialCommandGroup {

    public Auto(Drivetrain sys_drivetrain, PathPlannerTrajectory trajectory) {
        super(
            Commands.runOnce(() -> sys_drivetrain.resetOdometry(trajectory.getInitialPose()), sys_drivetrain),
            new AutoPathPlanning(sys_drivetrain, trajectory),
            new BalancingChargeStation(sys_drivetrain)
            // new Turn90DegreesChargeStation(sys_drivetrain, TurnDirection.LEFT)
        );
    }

}