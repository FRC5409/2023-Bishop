// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kWristSetpoints;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeWrist;
import frc.robot.subsystems.Intake.IntakeRoller;

public class IntakePickupSequence extends ParallelCommandGroup {
  public IntakePickupSequence(IntakePivot pivot, IntakeWrist wrist, IntakeRoller roller) {
    addCommands(
      new PivotMove(pivot, kPivotSetpoints.kPivotPickupHigh),
      new WristMove(wrist, kWristSetpoints.kWristPickupHigh),
      new RollerMove(roller, 3.6)
    );
  }
}
