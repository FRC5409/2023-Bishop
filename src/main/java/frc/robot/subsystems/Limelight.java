// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;

public class Limelight extends SubsystemBase {
  //networktables
  NetworkTable limelightTable;

  //shuffleboard
  private final ShuffleboardLayout localizationPos; 
  private final ShuffleboardLayout localizationRot;
  private final ShuffleboardLayout localizationPipeline; 
  private final GenericEntry xWidget, yWidget, zWidget;
  private final GenericEntry rxWidget, ryWidget, rzWidget;
  private final GenericEntry pipelineIndexWidget, pipelineLatencyWidget;

  private double[] positionDefaults = new double[]{0};
  public Limelight() {
    //networktables
    NetworkTableInstance.getDefault().startServer();
    NetworkTableInstance.getDefault().setServerTeam(5409);

    //shuffleboard
    Shuffleboard.getTab("Field Localization").add("Position", 0);
    Shuffleboard.getTab("Field Localization").add("Rotation", 0);
    Shuffleboard.getTab("Field Localization").add("Pipeline Info", 0);

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
      .getLayout("Pipeline Info")
      .withSize(1, 2);

    pipelineIndexWidget = localizationPipeline.add("Pipeline", 0).getEntry();
    pipelineLatencyWidget = localizationPipeline.add("Latency", 0).getEntry();
  }

  @Override
  public void periodic() {
    updateRobotPosition();
  }

  public void updateRobotPosition() {
    //get the position of the robot in 3d fieldspace as calculated by fiducial targets
    double[] robotPos = NetworkTableInstance.getDefault()
      .getTable("limelight")
      .getEntry("botpose")
      .getDoubleArray(positionDefaults); //TEMPORARY
    
    //pushing to shuffleboard1
    if (robotPos.length != 0){
      //update Rotation and Position here 
      xWidget.setDouble(robotPos[0]);
      yWidget.setDouble(robotPos[1]);
      zWidget.setDouble(robotPos[2]);

      rxWidget.setDouble(robotPos[3]);
      ryWidget.setDouble(robotPos[4]);
      rzWidget.setDouble(robotPos[5]);

      pipelineIndexWidget.setDouble(LimelightHelpers.getCurrentPipelineIndex("limelight"));
      pipelineLatencyWidget.setDouble(LimelightHelpers.getLatency_Pipeline("limelight"));
    }
  }
}
