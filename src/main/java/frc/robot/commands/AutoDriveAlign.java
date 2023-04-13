// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Constants.kLimelight.KAutoDriveAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class AutoDriveAlign extends CommandBase {
  private final Limelight sys_Limelight;
  private final Drivetrain sys_Drivetrain;
  private final PIDController m_pidController;
  private CommandXboxController m_joystick;


  public AutoDriveAlign(Limelight limelight, Drivetrain drivetrain) {
    //initializing
    sys_Limelight = limelight;
    sys_Drivetrain = drivetrain;
    m_joystick = null; 

    //PID controller
    m_pidController = new PIDController(KAutoDriveAlign.kP, KAutoDriveAlign.kI, KAutoDriveAlign.kD);
    m_pidController.setSetpoint(0);
    m_pidController.setTolerance(KAutoDriveAlign.driveTolerance);
  }

  public AutoDriveAlign(Limelight limelight, Drivetrain drivetrain, CommandXboxController joystick) {
    //initializes default constructor
    this(limelight, drivetrain);
    m_joystick = joystick;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
