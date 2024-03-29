// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class MoveArmManual extends CommandBase {
  private final Arm sys_arm;
  private double m_voltage;

  /** Creates a new MoveArmManual. */
  
  public MoveArmManual(Arm arm, double voltage) {
    sys_arm = arm;
    this.m_voltage = voltage;

    addRequirements(sys_arm);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_arm.disable();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    sys_arm.moveVolts(m_voltage);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    double newSetpoint = sys_arm.getMeasurement();
    sys_arm.setSetpoint(newSetpoint);
    sys_arm.enable();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
