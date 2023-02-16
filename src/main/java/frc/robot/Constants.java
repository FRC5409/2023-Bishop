// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2.AxisDirection;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class kOperator {
        public static final int port_joystickMain               = 0;
        public static final int port_joystickSecondary          = 1;
    }

    public static final class kCANBus {
        public static final String bus_rio                      = "rio";
        public static final String bus_drive                    = "drive";
    }

    public static final class kDrivetrain {

        public static final class kMotor {
            public static final int id_leftFrontDrive           = 20;
            public static final int id_leftCentreDrive          = 21;
            public static final int id_leftRearDrive            = 22;

            public static final int id_rightFrontDrive          = 23;
            public static final int id_rightCentreDrive         = 24;
            public static final int id_rightRearDrive           = 25;

            public static final double rampRate                 = 0.3; // seconds

            public static final int currentLimit                = 40;
        }

        public static final class kCANCoder {
            public static final int id_leftEncoder              = 30;
            public static final int id_rightEncoder             = 29;
            public static final double enc_CountsPerRevolution  = 4096;
            public static final double enc_SensorCoefficient    = (Math.PI * kDrivetrain.kWheel.wheelDiameter) / enc_CountsPerRevolution;
            public static final String enc_UnitString           = "m";
        }

        public static class kWheel {
            public static final double wheelDiameter            = 0.1; // metres, placeholder value
            public static final double wheelCircumference       = Math.PI * wheelDiameter; // metres
        }

        public static final double ksVolts                      = 0.08122;
        public static final double kvVolts                      = 2.796;
        public static final double kaVolts                      = 0.28485;

        public static final double kPDriveVel                   = 3.3466;

        public static final double kTrackWidth                  = 0.6;
        public static final DifferentialDriveKinematics kDriveKinematics
            = new DifferentialDriveKinematics(kTrackWidth);

        public static class kAuto {
            public static final double kMaxVolts                = 10;

            public static final double kMaxSpeed                = 3;
            public static final double kMaxAcceleration         = 3;

            // Default baseline values
            // https://docs.wpilib.org/en/stable/docs/software/pathplanning/trajectory-tutorial/entering-constants.html#ramsete-parameters
            public static final double kRamseteB                = 2;
            public static final double kRamseteZeta             = 0.7;
        }
    }

    public static final class kClaw {

        public static final int clawCANID                        = 29;

        public static final int ToFCANID                         = 36;

        public static final int currentLimit                     = 30;

        public static final double openPosition                  = -6841;
        public static final double closePosition                 = -13923;

        public static final double zeroSpeed                     = 0.1;

        public static final double encoderOffset                 = 300;

        public static final double kP                            = 0.1;
        public static final double kI                            = 0;
        public static final double kD                            = 0;
        public static final double kF                            = 0.02;

        //distance from the claw to the object in front of it
        public static final double objectRange                   = 150;

    }

    public static final class kGyro {
        public static final int id_gyro                         = 10;

        public static final AxisDirection mountPoseForward      = AxisDirection.NegativeY;
        public static final AxisDirection mountPoseUp           = AxisDirection.PositiveZ;
    }

    public static final class kTrajectoryPath {
        public static final String path1 = "Path1";
    }

    public static final class kBalancing {
        public static final double targetPitch                  = 0;
        public static final double maxAngle                     = 33.25;
        public static final double angleTolerance               = 1.5;

        public static final double kP                           = 0.04;
        public static final double kI                           = 0;
        public static final double kD                           = 0;
    }

    public static final class kTurn90DegreesChargeStation {
        public static final double maxAngle                     = 90;
        public static final double angleTolerance               = 1.5;

        public static final double kP_chargeStation             = 0.0125;
        public static final double kI_chargeStation             = 0;
        public static final double kD_chargeStation             = 0;
    }

    public static class kCANdle {
        public final static int staticTime = 750;

        public static class kConfig {

            public final static int CANID = 19;
            public final static int LEDCount = 94;

            public final static int LEDInnerRight = 30;
            public final static int LEDInnerLeft = 26;
            public final static int LEDOutter = 15;
        }

        public static class kColors {

            public final static int[] idle = {255, 134 , 0};
            public final static int[] cube = {142, 39, 245};
            public final static int[] cone = {237, 120, 0};

            public final static int LEDSinCount = 8;
            public final static double kSpeed = 0.5;

            public final static double sinFrequency = 0.025;
            public final static double sinFrequencySpeed = 20;

            public final static int chargeSpeed = 4;

            public final static double gameSpeed = 0.2;
            
        }

        public enum AnimationTypes {
            Static,
            ColorFlow,
            //custom
            SinWave,
            SinFlow,
            ChargedUp
        }
    }
}
