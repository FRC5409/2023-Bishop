// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class PivotOutward extends CommandBase
{
  private final Intake sys_intake;
  private final double setpoint;

  public PivotOutward(Intake subsystem)
  {
    sys_intake = subsystem;
    setpoint = kPivotSetpoints.kPivotExtended;

    addRequirements(sys_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    sys_intake.pivotToSetpoint(setpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    if (Math.abs(setpoint - sys_intake.getPivotPos()) < 1)
    {
      return true;
    }

    return false;
  }
}
