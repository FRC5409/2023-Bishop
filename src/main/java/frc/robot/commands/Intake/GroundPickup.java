// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.Constants.kIntake;
import frc.robot.Constants.kArmSubsystem.kSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kWristSetpoints;
import frc.robot.commands.arm.ArmRotation;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeWrist;
import frc.robot.subsystems.Intake.IntakeRoller;

//sequential command for ground pickup. 
// pivot move down
// wrist move out
// arm move
// roller stars intaking

public class GroundPickup extends SequentialCommandGroup {
  public GroundPickup(IntakePivot pivot, IntakeWrist wrist, IntakeRoller roller, ArmPIDSubsystem arm) {
    addCommands(
      new PivotMove(pivot, kPivotSetpoints.kPivotDown),
      new WristMove(wrist, kWristSetpoints.kWristHover),
      new ArmRotation(arm, kSetpoints.kIntakeAim).withTimeout(1),
      new RollerMove(roller, kIntake.kRollerCaptureVolts)
    );
  }
}
