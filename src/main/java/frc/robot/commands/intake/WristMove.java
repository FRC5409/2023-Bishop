// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake.IntakeWrist;
 
public class WristMove extends CommandBase {

  private final IntakeWrist sys_IntakeWrist;
  private double voltage;

  public WristMove(IntakeWrist subsystem, double voltage) {
      sys_IntakeWrist = subsystem;
      this.voltage = voltage;

      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(sys_IntakeWrist);   
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_IntakeWrist.wristControl(voltage);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sys_IntakeWrist.wristControl(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
