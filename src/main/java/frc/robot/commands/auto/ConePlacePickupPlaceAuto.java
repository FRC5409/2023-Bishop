// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.util.List;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kArmSubsystem;
import frc.robot.Constants.kCANdle;
import frc.robot.Constants.kClaw;
import frc.robot.Constants.kTelescope;
import frc.robot.Constants.kCANdle.AnimationTypes;
import frc.robot.Constants.kCANdle.LEDColorType;
import frc.robot.commands.LEDs.BlinkLEDs;
import frc.robot.commands.arm.ArmRotation;
import frc.robot.commands.arm.TelescopeTo;
import frc.robot.commands.auto.task.AutoPathPlanning;
import frc.robot.commands.auto.task.CloseClawInAuto;
import frc.robot.commands.auto.task.PlaceConeOnMidAtStart;
import frc.robot.commands.claw.ClawMovement;
import frc.robot.commands.vision.ConeNodeAim;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Candle;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.NewClaw;
import frc.robot.subsystems.Telescope;

public class ConePlacePickupPlaceAuto extends SequentialCommandGroup {

    public ConePlacePickupPlaceAuto(
            Drivetrain sys_drivetrain,
            ArmPIDSubsystem sys_armPIDSubsystem,
            Telescope sys_telescope,
            NewClaw sys_claw,
            Candle sys_LEDs,
            Limelight sys_limelight,
            List<PathPlannerTrajectory> pathGroup) {

        addCommands(
                Commands.runOnce(() -> sys_drivetrain.resetOdometry(pathGroup.get(0).getInitialPose())), // Reset odometry

                new PlaceConeOnMidAtStart(sys_armPIDSubsystem, sys_telescope, sys_claw),
                Commands.waitSeconds(0.5),

                new AutoPathPlanning(sys_drivetrain, pathGroup.get(0))
                    .alongWith(
                        // Ready to grab cone
                        new TelescopeTo(sys_telescope, kTelescope.kDestinations.kAutoGroundBack)
                    ),

                new CloseClawInAuto(sys_claw, sys_LEDs),

                // Drive to node
                new AutoPathPlanning(sys_drivetrain, pathGroup.get(1))
                    .alongWith(
                        // Cone grabbed
                        new TelescopeTo(sys_telescope, kTelescope.kDestinations.kRetracted).withTimeout(0.5),
                        new ArmRotation(sys_armPIDSubsystem, kArmSubsystem.kSetpoints.kAutoDrivingWithCone).withTimeout(1)
                    ),

                // Lineup using Limelight
                new ConeNodeAim(sys_limelight, sys_telescope, sys_drivetrain).withTimeout(0.75)
                .alongWith(
                    // Place cone
                    new PlaceConeOnMidAtStart(sys_armPIDSubsystem, sys_telescope, sys_claw)
                )
        );
    }
}
