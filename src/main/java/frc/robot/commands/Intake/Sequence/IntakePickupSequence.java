// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake.Sequence;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeWrist;
import frc.robot.subsystems.Intake.IntakeRoller;

public class IntakePickupSequence extends SequentialCommandGroup
{
  public IntakePickupSequence(IntakePivot pivot, IntakeWrist wrist, IntakeRoller roller)
  {
    addCommands
    (
      new PivotOutward(pivot),
      new WristPickup(wrist),
      new RollerPickup(roller)
    );
  }
}
