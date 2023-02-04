// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeMove extends CommandBase
{
  private final Intake intake;
  private final int joint;
  private final double speed;

  public IntakeMove(Intake _intake, int _joint, double _speed)
  {
    intake = _intake;
    joint = _joint;
    speed = _speed;

    addRequirements(intake);
  }

  @Override
  public void initialize()
  {
    switch (joint)
    {
      case 1:
        intake.setPivotTurn(speed);
        break;
      
      case 2:
        intake.setWristTurn(speed);
        break;
      
      case 3:
        intake.setRollerTurn(speed);
        break;
    }
  }

  @Override
  public void execute() {}
  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished()
  {
    return false;
  }
}
