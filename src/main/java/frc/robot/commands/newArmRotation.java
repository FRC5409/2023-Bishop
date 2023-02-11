// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class newArmRotation extends CommandBase {
  private final Arm sys_arm;
  private double setReference;
  /** Creates a new newArmRotation. */
  public newArmRotation(Arm arm, double setReference) {
    sys_arm = arm;
    this.setReference = setReference;

    addRequirements(sys_arm);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_arm.setReference(setReference);;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
