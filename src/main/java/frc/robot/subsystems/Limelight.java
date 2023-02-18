// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.Arrays;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;

public class Limelight extends SubsystemBase {
  //networktables
  NetworkTable limelightTable;

  //shuffleboard
  private final ShuffleboardLayout localizationPos; 
  private final ShuffleboardLayout localizationRot;
  private final ShuffleboardLayout localizationPipeline; 
  private final ShuffleboardLayout localizationTarget;
  private final GenericEntry xWidget, yWidget, zWidget;
  private final GenericEntry rxWidget, ryWidget, rzWidget;
  private final GenericEntry pipelineIndexWidget, pipelineLatencyWidget;
  private final GenericEntry targetSizeWidget; 

  //robot
  private double targetDistance; 
  private double[] robotPos;

  //time 
  private double lastLightUpdate;

  private double[] positionDefaults = new double[]{0};
  public Limelight() {
    //networktables
    NetworkTableInstance.getDefault().startServer();
    NetworkTableInstance.getDefault().setServerTeam(5409);

    //shuffleboard
    Shuffleboard.getTab("Field Localization").add("Position", 0);
    Shuffleboard.getTab("Field Localization").add("Rotation", 0);
    Shuffleboard.getTab("Field Localization").add("Pipeline Info", 0);
    Shuffleboard.getTab("Field Localization").add("Target Size", 0);

    localizationPos = Shuffleboard.getTab("Field Localization")
        .getLayout("Position", BuiltInLayouts.kGrid)
        .withSize(1, 2);
    
    xWidget = localizationPos.add("X", 0).getEntry();
    yWidget = localizationPos.add("Y", 0).getEntry();
    zWidget = localizationPos.add("Z", 0).getEntry();

    localizationRot = Shuffleboard.getTab("Field Localization")
        .getLayout("Rotation", BuiltInLayouts.kGrid)
        .withSize(1, 2);
      
    rxWidget = localizationRot.add("rX", 0).getEntry();
    ryWidget = localizationRot.add("rY", 0).getEntry();
    rzWidget = localizationRot.add("rZ", 0).getEntry();

    localizationPipeline = Shuffleboard.getTab("Field Localization")
      .getLayout("Pipeline Info", BuiltInLayouts.kGrid)
      .withSize(1, 2);
    
    localizationTarget = Shuffleboard.getTab("Field Localization")
        .getLayout("Pipeline Info", BuiltInLayouts.kGrid)
        .withSize(1, 1);

    pipelineIndexWidget = localizationPipeline.add("Pipeline", 0).getEntry();
    pipelineLatencyWidget = localizationPipeline.add("Latency", 0).getEntry();
    targetSizeWidget = localizationTarget.add("Size", 0).getEntry();


    //setting startup millis
    lastLightUpdate = System.currentTimeMillis();
  }

  @Override
  public void periodic() {
    updateRobotPosition();
    autoLight();
    double[] cropValues = new double[4];
    cropValues[0] = 0;
    cropValues[1] = 1.0;
    cropValues[2] = 0;
    cropValues[3] = 1.0;

    setCropSize(cropValues);
  }

  public void updateRobotPosition() {
    LimelightHelpers.LimelightResults llresults = LimelightHelpers.getLatestResults("");
    
    //get the position of the robot in 3d fieldspace as calculated by fiducial targets
    robotPos = NetworkTableInstance.getDefault()
      .getTable("limelight")
      .getEntry("botpose")
      .getDoubleArray(positionDefaults); //TEMPORARY
    
    //updating target size data to shuffleboard 
    targetDistance = LimelightHelpers.getTA("");
    targetSizeWidget.setDouble(LimelightHelpers.getTA(""));

    //updating pipeline data to shuffleboard
    pipelineIndexWidget.setDouble(LimelightHelpers.getCurrentPipelineIndex("limelight"));
    pipelineLatencyWidget.setDouble(LimelightHelpers.getLatency_Pipeline("limelight"));
    
    //Shuffleboard robotpos update
    //System.out.println(Arrays.toString(robotPos));
    if (robotPos.length != 0){
      //update Rotation and Position here  
      xWidget.setDouble(robotPos[0]);
      yWidget.setDouble(robotPos[1]);
      zWidget.setDouble(robotPos[2]);

      rxWidget.setDouble(robotPos[3]);
      ryWidget.setDouble(robotPos[4]);
      rzWidget.setDouble(robotPos[5]);
    }
  }

  public void autoLight(){
    //MIGHT BE EXPENSIVE ON THE CPU
    System.out.println(System.currentTimeMillis() - lastLightUpdate);
    if (Constants.kLimelight.kDoAutoLight){
      lastLightUpdate = System.currentTimeMillis();
      if (targetDistance >= Constants.kLimelight.kALTriggerDistance && (System.currentTimeMillis() - lastLightUpdate) >= Constants.kLimelight.kAutoLightTimeout){
      LimelightHelpers.setLEDMode_ForceOn("");
    } else if (targetDistance <= Constants.kLimelight.kALTriggerDistance && (System.currentTimeMillis() - lastLightUpdate) >= Constants.kLimelight.kAutoLightTimeout){
      LimelightHelpers.setLEDMode_ForceOff("");
    }
    }
  }

  public void setCropSize(double[] cropSize){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("crop").setDoubleArray(cropSize);
  }
}
