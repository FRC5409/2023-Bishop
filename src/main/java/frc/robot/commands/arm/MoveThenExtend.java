// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Telescope;

public class MoveThenExtend extends CommandBase {
  private final ArmPIDSubsystem sys_arm;
  private double armSetpoint;
  private final double telescopeSetpoint;
  private final Telescope sys_telescope;
  private boolean extended = false;
  /** Creates a new ArmToTop. */
  public MoveThenExtend(ArmPIDSubsystem armPIDSubsystem, double armSetpoint,Telescope telescope, double telescopeSetpoint){
    sys_arm = armPIDSubsystem;
    sys_telescope = telescope;
    this.armSetpoint = armSetpoint;
    this.telescopeSetpoint = telescopeSetpoint;

    addRequirements(sys_arm, sys_telescope);
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    extended = false;
    sys_arm.setSetpoint(armSetpoint);
    sys_arm.enable();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  if (Math.abs(sys_arm.getMeasurement()-armSetpoint) < .1){
    sys_telescope.extend(telescopeSetpoint);
    extended = true;

  }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sys_telescope.setPrevPos(Constants.kTelescope.kDestinations.kExtended);  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return extended;
  }
}
