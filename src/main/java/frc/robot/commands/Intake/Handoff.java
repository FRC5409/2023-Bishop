// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;


import frc.robot.Constants.kClaw;
import frc.robot.Constants.kIntake;
import frc.robot.Constants.kArmSubsystem.kSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kWristSetpoints;
import frc.robot.commands.arm.ArmRotation;
import frc.robot.commands.claw.ClawMovement;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.NewClaw;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeRoller;
import frc.robot.subsystems.Intake.IntakeWrist;

public class Handoff extends SequentialCommandGroup { 
    public Handoff(IntakePivot pivot, IntakeWrist wrist, IntakeRoller roller, ArmPIDSubsystem arm, NewClaw claw) {
        addCommands (
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new PivotMove(pivot, kPivotSetpoints.kPivotDown),

                    new ParallelCommandGroup(
                        new WristMove(wrist, kWristSetpoints.kWristFlat),
                        new ClawMovement(claw, kClaw.openPosition).withTimeout(0.5),
                        new ArmRotation(arm, kSetpoints.kToHandoff).withTimeout(0.5)
                    ),

                    new WristMove(wrist, kWristSetpoints.kWristFolded).withTimeout(0.5)
                ),

                new RollerMove(roller, kIntake.kRollerGripVolts)
            ),

            new ParallelDeadlineGroup(
                Commands.waitSeconds(kIntake.kRollerReleaseTime),
                new RollerMove(roller, kIntake.kRollerReleaseVolts)
            ),

            new ClawMovement(claw, kClaw.coneClosePosition).withTimeout(0.5),
            new ArmRotation(arm, kSetpoints.kIdling).withTimeout(0.5),

            new PivotMove(pivot, kPivotSetpoints.kPivotInward)
        );
    }
}