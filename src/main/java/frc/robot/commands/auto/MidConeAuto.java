package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.kArmSubsystem;
import frc.robot.Constants.kTelescope;
import frc.robot.commands.ArmRotation;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.TelescopeTo;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Telescope;

public class MidConeAuto extends SequentialCommandGroup {

    public MidConeAuto(
                Drivetrain sys_drivetrain,
                ArmPIDSubsystem sys_armPIDSubsystem,
                Telescope sys_telescope,
                Claw sys_claw,
                PathPlannerTrajectory trajectory) {
        super(
            new CloseClaw(sys_claw, false),
            new ArmRotation(sys_armPIDSubsystem, kArmSubsystem.kSetpoints.kScoreMidShoulderSide),
            // new TelescopeTo(sys_telescope, kTelescope.kDestinations.kExtended),
            Commands.waitSeconds(1),
            new OpenClaw(sys_claw, false),
            Commands.waitSeconds(1),
            // new TelescopeTo(sys_telescope, kTelescope.kDestinations.kRetracted),
            new ArmRotation(sys_armPIDSubsystem, kArmSubsystem.kSetpoints.kback),
            new AutoPathPlanning(sys_drivetrain, trajectory),
            new BalancingChargeStation(sys_drivetrain)
            // new Turn90DegreesChargeStation(sys_drivetrain, TurnDirection.LEFT)
        );
    }

}