// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.kLimelight;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Telescope;

public class NewScoreExtendArm extends CommandBase {

  public static enum cropMode {
    kMid,
    kHigh
  }

  private final Limelight sys_Limelight;
  private final Telescope sys_Telescope;
  private final cropMode cropMode;

  private double[] lowNodeCrop, highNodeCrop;
  private double scoreHeight;
  
  /** Creates a new ScoreExtendArm. */
  public NewScoreExtendArm(Limelight limelight, cropMode crop, Telescope telescope) {
    sys_Limelight = limelight;
    sys_Telescope = telescope;
    cropMode = crop;

    addRequirements(sys_Limelight, sys_Telescope);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // gets the distance to extend the arm
  public double getDistance(){
    switch (cropMode) {
      case kMid:
        scoreHeight = Constants.kLimelight.kdistancevalues.kScoreHeightLow;
        break;
      case kHigh:
        scoreHeight = Constants.kLimelight.kdistancevalues.kScoreHeightHigh;
        break;
      default:
        scoreHeight = Constants.kLimelight.kdistancevalues.kScoreHeightLow;
        break;
    }

   // gets angle 
   double limelightAngle = sys_Limelight.getYOffset();
   double angleToScore = (kLimelight.kdistancevalues.kMountingAngle + limelightAngle) * (Math.PI / 180);
   
   // calculates distance 
   double distance = (scoreHeight - kLimelight.kdistancevalues.kLimelightHeight) / (Math.tan(angleToScore));
   double extendingDistance = (Math.sqrt(Math.pow(distance, 2) + Math.pow((scoreHeight - Constants.kLimelight.kdistancevalues.kArmHeight + Constants.kLimelight.kdistancevalues.kConeHeight), 2))) - Constants.kLimelight.kdistancevalues.kArmLength;
   double finalExtendingDistance = extendingDistance * Constants.kLimelight.kdistancevalues.kExtendingConversion;
   
   return finalExtendingDistance;
  }

  // crops the limelight screen to focus on one row of nodes 
  public void setTargetMode(cropMode cropmode){
    lowNodeCrop = kLimelight.KretroTarget.lowNodeCrop;
    highNodeCrop = kLimelight.KretroTarget.highNodeCrop;
    
    switch(cropmode) {
      case kMid:
        sys_Limelight.setCropSize(lowNodeCrop);
        break;
      case kHigh:
        sys_Limelight.setCropSize(highNodeCrop);
        break;
      default:
        sys_Limelight.setCropSize(lowNodeCrop);
        break;
      
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_Limelight.setData("pipeline", 1);

    if (!sys_Limelight.isOn())
      sys_Limelight.turnOn();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    setTargetMode(cropMode);
    sys_Telescope.extend(getDistance());
    sys_Telescope.setPrevPos(Constants.kTelescope.kDestinations.kExtended);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sys_Limelight.turnOff();
    sys_Telescope.stopExtending();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}