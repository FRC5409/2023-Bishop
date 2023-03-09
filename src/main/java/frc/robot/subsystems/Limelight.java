// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

public class Limelight extends SubsystemBase {
    // networktables
    NetworkTable limelightTable;

    // shuffleboard
    private final ShuffleboardLayout localizationPos, localizationRot, localizationPipeline, localizationTarget, retroTarget;
    private final GenericEntry xWidget, yWidget, zWidget;
    private final GenericEntry rxWidget, ryWidget, rzWidget;
    private final GenericEntry pipelineIndexWidget, pipelineLatencyWidget;
    private final GenericEntry targetSizeWidget, retroDistanceWidget;
    // Retroreflective-related shuffleboard
    private final ShuffleboardTab sb_limelightR;
    private final GenericEntry xOffEntry, yOffEntry, targetAreaEntry, visibilityEntry, ledModeEntry;

    // robot
    private double targetDistance;
    private double[] robotPos;

    // time
    private double lastLightUpdate;
    private double[] positionDefaults = new double[] { 0 };
    
    //retrodistance
    private double retroTargetDistance;
    private double lastRetroDistance; //DEBUGGING

    double lastTick = 0;

    // Retro-reflective
    double turningDir = 0;
    private final CommandXboxController c_joystick; // For getting the direction input from the user

    public Limelight() {
        // networktables
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableInstance.getDefault().startServer();
        NetworkTableInstance.getDefault().setServerTeam(5409);

        // shuffleboard
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

        retroTarget = Shuffleboard.getTab("Field Localization")
            .getLayout("Retro Distance", BuiltInLayouts.kGrid)
            .withSize(1, 1);
        retroDistanceWidget = retroTarget.add("Distance", 0).getEntry();

        // setting startup millis
        lastLightUpdate = System.currentTimeMillis();

        //debugging retro
        lastRetroDistance = 0;

        // Retroreflective-related Shuffleboard
        sb_limelightR = Shuffleboard.getTab("Limelight (Retro-reflective)");
        xOffEntry = sb_limelightR.add("X Offset", limelightTable.getEntry("tx").getDouble(0)).getEntry();
        yOffEntry = sb_limelightR.add("Y Offset", limelightTable.getEntry("ty").getDouble(0)).getEntry();
        targetAreaEntry = sb_limelightR.add("Target Area", limelightTable.getEntry("ta").getDouble(-1)).getEntry();
        visibilityEntry = sb_limelightR.add("Target Visibility", isVisible()).getEntry();
        ledModeEntry = sb_limelightR.add("LED Mode", limelightTable.getEntry("ledMode").getDouble(-1)).getEntry();

        c_joystick = new CommandXboxController(0);
    }

    @Override
    public void periodic() {
        updateRobotPosition();
        updateRetroDistance();
        updateRetroReflectiveData();
        updateDirFromPOV();
    }

    public void updateRobotPosition() {
        LimelightHelpers.LimelightResults llresults = LimelightHelpers.getLatestResults("");

        // get the position of the robot in 3d fieldspace as calculated by fiducial
        // targets
        robotPos = NetworkTableInstance.getDefault()
                .getTable("limelight")
                .getEntry("botpose")
                .getDoubleArray(positionDefaults); // TEMPORARY

        // updating target size data to shuffleboard
        targetDistance = LimelightHelpers.getTA("");
        targetSizeWidget.setDouble(LimelightHelpers.getTA(""));

        // updating pipeline data to shuffleboard
        pipelineIndexWidget.setDouble(LimelightHelpers.getCurrentPipelineIndex("limelight"));
        pipelineLatencyWidget.setDouble(LimelightHelpers.getLatency_Pipeline("limelight"));

        // Shuffleboard robotpos update
        // System.out.println(Arrays.toString(robotPos));
        if (robotPos.length >= 6) {
            // update Rotation and Position here
            xWidget.setDouble(robotPos[0]);
            yWidget.setDouble(robotPos[1]);
            zWidget.setDouble(robotPos[2]);

            rxWidget.setDouble(robotPos[3]);
            ryWidget.setDouble(robotPos[4]);
            rzWidget.setDouble(robotPos[5]);
        }

        // setting startup millis
        lastLightUpdate = System.currentTimeMillis();
    }
    

    public void autoLight() {
        // MIGHT BE EXPENSIVE ON THE CPU
        // System.out.println(System.currentTimeMillis() - lastLightUpdate);
        if (Constants.kLimelight.kDoAutoLight) {
            lastLightUpdate = System.currentTimeMillis();
            if (targetDistance >= Constants.kLimelight.kALTriggerDistance
                    && (System.currentTimeMillis() - lastLightUpdate) >= Constants.kLimelight.kAutoLightTimeout) {
                LimelightHelpers.setLEDMode_ForceOn("");
            } else if (targetDistance <= Constants.kLimelight.kALTriggerDistance
                    && (System.currentTimeMillis() - lastLightUpdate) >= Constants.kLimelight.kAutoLightTimeout) {
                LimelightHelpers.setLEDMode_ForceOff("");
            }
        }
    }

    public void updateRetroDistance() {
        double cameraTargetAngle = LimelightHelpers.getTY("");
        double realTargetAngle = Constants.kLimelight.Kmounting.angle + cameraTargetAngle;
        double realTargetAngleRadians = realTargetAngle * (3.14159 / 180.0); //converting angle to radians

        if (cameraTargetAngle != 0){
            retroTargetDistance = (Constants.kLimelight.KretroTarget.lowNodeHeight - Constants.kLimelight.Kmounting.limeLightHeight)/Math.tan(realTargetAngleRadians); 
        } else  { 
            retroTargetDistance = 0; 
            System.out.println("No-RetroTarget");
        }

        //Pushing readings to shuffleboard
        if (retroTargetDistance != lastRetroDistance){
            if (Constants.kLimelight.KretroTarget.retroDistanceDebug){
                System.out.printf("[Update] Retro-Distance: %d", retroTargetDistance);
            }
            retroDistanceWidget.setDouble(retroTargetDistance); //pushing value to shuffleboard
        }
        lastRetroDistance = retroTargetDistance;
    }

    /** Updates retroreflective related data to relevant Shuffleboard entries */
    public void updateRetroReflectiveData() {
        xOffEntry.setDouble(getXOffset());
        yOffEntry.setDouble(getYOffset());
        targetAreaEntry.setDouble(limelightTable.getEntry("ta").getDouble(0.0));
        ledModeEntry.setDouble(limelightTable.getEntry("ledMode").getDouble(0.0));
        visibilityEntry.setBoolean(isVisible());
    }

    /**
     * Updates the direction input from the user so that the robot can be rotated
     * manually
     */
    public void updateDirFromPOV() {
        double pov = c_joystick.getHID().getPOV();
        if (pov == 270)
            turningDir = -1;
        else if (pov == 90)
            turningDir = 1;
    }

    /** Turns the limelight off */
    public void turnOff() {
        limelightTable.getEntry("ledMode").setNumber(1);
    }

    /** Turns the limelight on */
    public void turnOn() {
        limelightTable.getEntry("ledMode").setNumber(3);
    }

    /** Gets the X position/offset */
    public double getXOffset() {
        return limelightTable.getEntry("tx").getDouble(0);
    }

    /** Gets the Y position/offset */
    public double getYOffset() {
        return limelightTable.getEntry("ty").getDouble(0);
    }

    /** Checks if the target is visible or not */
    public boolean isVisible() {
        return limelightTable.getEntry("tv").getDouble(0) == 1;
    }

    /** Sets data in an entry */
    public void setData(String key, double data) {
        limelightTable.getEntry(key).setDouble(data);
    }

    /** Gets the turning direction */
    public double getTurningDir() {
        return turningDir;
    }

    /** Gets data from an entry */
    public double getData(String key) {
        return limelightTable.getEntry(key).getDouble(0);
    }

    public void setCropSize(double[] cropSize) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("crop").setDoubleArray(cropSize);
    }

    public void dynamicCrop(char targetType, double[] targetPos) {
        ;
    }

}