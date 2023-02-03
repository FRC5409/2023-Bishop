// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Intake;

public class IntakeMove extends CommandBase
{
  private final Intake intake;
  private final CommandXboxController controller;

  public IntakeMove(Intake _intake, CommandXboxController _controller)
  {
    intake = _intake;
    controller = _controller;

    addRequirements(intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute()
  {
    if (controller.povUp().getAsBoolean())
    {
      
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
