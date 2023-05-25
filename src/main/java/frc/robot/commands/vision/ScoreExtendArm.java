// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.kLimelight;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Telescope;

public class ScoreExtendArm extends CommandBase {

  private final Limelight sys_Limelight;
  private final Telescope sys_Telescope;
  //private CommandXboxController m_joystick;

  private ShuffleboardTab sb_scoreExtendArmTab;
  private GenericEntry nt_kP, nt_kI, nt_kD;

  private final PIDController m_PidController;

  private final boolean debugmode = false;

  /** Creates a new ScoreExtendArm. */
  public ScoreExtendArm(Limelight limelight, Telescope telescope) {
    sys_Limelight = limelight;
    sys_Telescope = telescope;

    m_PidController = new PIDController(kLimelight.kdistancevalues.kP,kLimelight.kdistancevalues.kI, kLimelight.kdistancevalues.kD);
    m_PidController.setSetpoint(0);
    m_PidController.setTolerance(kLimelight.kdistancevalues.kDistanceTolerance);

    addRequirements(sys_Limelight,sys_Telescope);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public void getShuffleboardPID(){
    if (debugmode){
      sb_scoreExtendArmTab = Shuffleboard.getTab("ScoreExtendArm");
    }

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
