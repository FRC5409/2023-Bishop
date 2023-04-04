// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.tipper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kConeTipper;
import frc.robot.subsystems.ConeTipper;

public class ConeTipperMove extends CommandBase {
  private final ConeTipper sys_Tipper;
  private double setpoint;

  public ConeTipperMove(ConeTipper subsystem, double setpoint) {
    sys_Tipper = subsystem;
    this.setpoint = setpoint;

    addRequirements(sys_Tipper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_Tipper.setSetpoint(setpoint);
    sys_Tipper.enable();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return sys_Tipper.getController().atSetpoint();
  }
}