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

  private final Limelight sys_Limelight;

  private final Telescope sys_Telescope;

  double[] lowNodeCrop, highNodeCrop;
  double cropmode;
  double scoreHeight;
  private boolean extended = false;
  
  /** Creates a new ScoreExtendArm. */
  public NewScoreExtendArm(Limelight limelight, double cropmode, Telescope telescope) {
    sys_Limelight = limelight;
    sys_Telescope = telescope;
    this.cropmode = cropmode;

    addRequirements(sys_Limelight, sys_Telescope);
    // Use addRequirements() here to declare subsystem dependencies.
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
   double angleToScore = (kLimelight.kdistancevalues.kMountingAngle + limelightAngle) * (Math.PI/180);
   
   // calculates distance 
   double distance = (scoreHeight-kLimelight.kdistancevalues.kLimelightHeight)/(Math.tan(angleToScore));
   double extendingDistance = (Math.sqrt (Math.pow(distance, 2) + Math.pow((scoreHeight-Constants.kLimelight.kdistancevalues.kArmHeight), 2))) - Constants.kLimelight.kdistancevalues.kArmLength;
   double finalExtendingDistance = extendingDistance*Constants.kLimelight.kdistancevalues.kExtendingConversion;
   return finalExtendingDistance;
  }

  // crops the limelight screen to focus on one row of nodes 
  public void setTargetMode(double cropmode){
    lowNodeCrop = kLimelight.KretroTarget.lowNodeCrop;
    highNodeCrop = kLimelight.KretroTarget.highNodeCrop;
    
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
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    setTargetMode(cropmode);
    sys_Telescope.extend(getDistance());
    sys_Telescope.setPrevPos(Constants.kTelescope.kDestinations.kExtended);
    extended = true;
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sys_Limelight.turnOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return extended;
  }


}