// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake.Sequence;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;


public class IntakePickupSequence extends SequentialCommandGroup
{
  public IntakePickupSequence(Intake intake)
  {
    addCommands
    (
      new PivotOutward(intake),
      new WristPickup(intake),
      new RollerPickup(intake),
      new WristHandoff(intake),
      new PivotInward(intake)
    );
  }
}
