// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.kIntake;
import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kWristSetpoints;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeRoller;
import frc.robot.subsystems.Intake.IntakeWrist;

public class SubstationPickup extends SequentialCommandGroup {
  public SubstationPickup(IntakePivot pivot, IntakeWrist wrist, IntakeRoller roller) {
    addCommands(
      new PivotMove(pivot, kPivotSetpoints.kPivotPortal),
      new WristMove(wrist, kWristSetpoints.kWristFlat),
      new RollerMove(roller, kIntake.kRollerCaptureVolts)
    );
  }
}
