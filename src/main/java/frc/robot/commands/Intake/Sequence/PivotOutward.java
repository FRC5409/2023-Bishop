// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake.Sequence;

import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake.IntakePivot;

public class PivotOutward extends CommandBase
{
  private final IntakePivot sys_intakePivot;;

  public PivotOutward(IntakePivot subsystem)
  {
    sys_intakePivot = subsystem;

    addRequirements(sys_intakePivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    sys_intakePivot.setSetpoint(kPivotSetpoints.kPivotExtended);
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
    if (Math.abs(sys_intakePivot.getPivotPos() - kPivotSetpoints.kPivotExtended) < 0.5)
    {
      return true;
    }

    return false;
  }
}
