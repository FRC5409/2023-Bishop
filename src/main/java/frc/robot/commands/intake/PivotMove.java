// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake.IntakePivot;

public class PivotMove extends CommandBase {

  private final IntakePivot sys_IntakePivot;
  private double speed;

  public PivotMove(IntakePivot subsystem, double speed) {
      sys_IntakePivot = subsystem;
      this.speed = speed;

      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(sys_IntakePivot);   
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_IntakePivot.pivotMove(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sys_IntakePivot.pivotMove(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
