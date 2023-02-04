// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakePID;;
 
public class RollerMove extends CommandBase {

  private final IntakePID sys_IntakePID;
  private double speed;

  public RollerMove(IntakePID subsystem, double speed) {
      sys_IntakePID = subsystem;
      this.speed = speed;

      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(sys_IntakePID);   
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_IntakePID.rollerControl(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sys_IntakePID.rollerControl(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
