// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake.Sequence;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;

public class IntakeHandoffSequence extends SequentialCommandGroup
{
  
  public IntakeHandoffSequence(Intake intake)
  {
    addCommands
    (
      new WristHandoff(intake),
      new PivotInward(intake)
    );
  }
}
