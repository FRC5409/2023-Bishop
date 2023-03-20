// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.util.List;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.kClaw;
import frc.robot.Constants.kTelescope;
import frc.robot.commands.arm.TelescopeTo;
import frc.robot.commands.claw.ClawMovement;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NewClaw;
import frc.robot.subsystems.Telescope;

public class OneConeOnePickupConeAuto extends SequentialCommandGroup {

    public OneConeOnePickupConeAuto(
            Drivetrain sys_drivetrain,
            ArmPIDSubsystem sys_armPIDSubsystem,
            Telescope sys_telescope,
            NewClaw sys_claw,
            List<PathPlannerTrajectory> pathGroup) {

        addCommands(
                Commands.runOnce(() -> sys_drivetrain.resetOdometry(pathGroup.get(0).getInitialPose())), // Reset odometry

                new PlaceConeOnMidAtStart(sys_armPIDSubsystem, sys_telescope, sys_claw),
                Commands.waitSeconds(1),
                new AutoPathPlanning(sys_drivetrain, pathGroup.get(0)),

                // Grab cone from ground
                new TelescopeTo(sys_telescope, kTelescope.kDestinations.kGroundBack),
                new ClawMovement(sys_claw, kClaw.coneClosePosition).withTimeout(1),
                new TelescopeTo(sys_telescope, kTelescope.kDestinations.kRetracted),

                new AutoPathPlanning(sys_drivetrain, pathGroup.get(1)),
                new BalancingChargeStation(sys_drivetrain)
        );
    }
}