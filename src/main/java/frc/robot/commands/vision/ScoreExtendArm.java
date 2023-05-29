// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Constants.kLimelight;
import frc.robot.Constants.kLimelight.kConeNodeAim;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Telescope;

public class ScoreExtendArm extends CommandBase {

  private final Limelight sys_Limelight;
  private final Arm sys_arm;
  private final Telescope sys_Telescope;

  private ShuffleboardTab sb_scoreExtendArmTab;
  private GenericEntry nt_kP, nt_kI, nt_kD;

  private final PIDController m_PidController;

  private final boolean debugmode = false;

  double[] lowNodeCrop, highNodeCrop;
  double cropmode;

  double armSetpoint;
  double scoreHeight;
  private boolean extended = false;
  

  /** Creates a new ScoreExtendArm. */
  public ScoreExtendArm(Limelight limelight, double cropmode, Arm arm, double armSetpoint, Telescope telescope) {
    sys_Limelight = limelight;
    sys_arm = arm;
    sys_Telescope = telescope;

    m_PidController = new PIDController(kLimelight.kdistancevalues.kP,kLimelight.kdistancevalues.kI, kLimelight.kdistancevalues.kD);
    m_PidController.setSetpoint(0);
    m_PidController.setTolerance(kLimelight.kdistancevalues.kDistanceTolerance);

    addRequirements(sys_Limelight,sys_arm, sys_Telescope);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // shuffleboard
  public void getShuffleboardPID(){
    if (debugmode){
      sb_scoreExtendArmTab = Shuffleboard.getTab("ScoreExtendArm");
      nt_kP = sb_scoreExtendArmTab.add("kP", kLimelight.kdistancevalues.kP).getEntry();
      nt_kI = sb_scoreExtendArmTab.add("kI",kLimelight.kdistancevalues.kI).getEntry();
      nt_kD = sb_scoreExtendArmTab.add("kD",kLimelight.kdistancevalues.kD).getEntry();
      m_PidController.setPID(nt_kP.getDouble(0), nt_kI.getDouble(0), nt_kD.getDouble(0));
    }
  }

  // gets the distance to extend the arm
  public double getDistance(){
    // scoring low 
    if (cropmode == 0){
      scoreHeight = Constants.kLimelight.kdistancevalues.kScoreHeightLow;
    }
    // scoring high
    else{
      scoreHeight = Constants.kLimelight.kdistancevalues.kScoreHeightHigh;
    }

   // gets angle 
   double limelightAngle = sys_Limelight.getYOffset();
   double angleToScore = kLimelight.kdistancevalues.kMountingAngle + limelightAngle;
   double angleToScoreRadians = angleToScore * (3.13159 /180);
   
   // calculates distance 
   double distance = (scoreHeight-kLimelight.kdistancevalues.kLimelightHeight)/(Math.sin(angleToScoreRadians)) - kLimelight.kdistancevalues.kArmLength;
   double extendingDistance = distance* kLimelight.kdistancevalues.kExtendingConversion;
   return extendingDistance;
  }

  // crops the limelight screen to focus on one row of nodes 
  public void setTargetMode(double cropmode){
    if (cropmode == 0){
      sys_Limelight.setCropSize(lowNodeCrop);
    }

    else{
      sys_Limelight.setCropSize(highNodeCrop);
    }
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    extended = false;
    sys_Limelight.turnOn();
    sys_Limelight.setData("pipeline", 1);
    getShuffleboardPID();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    setTargetMode(cropmode);
    sys_arm.setSetpoint(armSetpoint);
    sys_arm.enable();

    if (Math.abs(sys_arm.getMeasurement()-armSetpoint) < .1){
      sys_Telescope.extend(getDistance());
      sys_Limelight.turnOff();
      sys_Telescope.setPrevPos(Constants.kTelescope.kDestinations.kExtended);
      extended = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return extended;
  }
}
