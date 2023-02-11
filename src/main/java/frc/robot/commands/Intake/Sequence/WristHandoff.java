// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake.Sequence;

import frc.robot.Constants.kIntake.kSetpoints.kWristSetpoints;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class WristHandoff extends CommandBase
{
  private final Intake sys_intake;
  private final double setpoint;

  public WristHandoff(Intake subsystem)
  {
    sys_intake = subsystem;
    setpoint = kWristSetpoints.kWristHandoff;

    addRequirements(sys_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    sys_intake.wristToSetpoint(setpoint);
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
    return false;
  }
}
