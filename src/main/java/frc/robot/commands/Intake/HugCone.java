// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.Constants.kArmSubsystem.kSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kWristSetpoints;
import frc.robot.commands.arm.ArmRotation;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeWrist;

public class HugCone extends SequentialCommandGroup {
  public HugCone(IntakePivot pivot, IntakeWrist wrist, ArmPIDSubsystem arm) {
    addCommands (
      new ArmRotation(arm, kSetpoints.kIdling).withTimeout(1),
      new WristMove(wrist, kWristSetpoints.kWristHugging),
      new PivotMove(pivot, kPivotSetpoints.kPivotUpright)
    );
  }
}
