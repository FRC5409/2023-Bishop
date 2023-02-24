// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2.AxisDirection;
import com.pathplanner.lib.PathPlanner;

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
                                                          /*   Team 5409   */
        public static final int teamNumber                      = 5409;
                                                          /*  The Chargers */
                                                          
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

            public static final int currentLimit                = 40;

        }

        public static final class kCANCoder {
            public static final int id_leftEncoder              = 29;
            public static final int id_rightEncoder             = 30;
            public static final double enc_CountsPerRevolution  = 4096;
            public static final double enc_SensorCoefficient    = (Math.PI * kDrivetrain.kWheel.wheelDiameter) / enc_CountsPerRevolution;
            public static final String enc_UnitString           = "m";
        }

        public static final class kWheel {
            public static final double wheelDiameter            = 0.15;
            public static final double wheelCircumference       = Math.PI * wheelDiameter; // metres
        }

        public static final double ksVolts                      = 0.18289;
        public static final double kvVolts                      = 1.9159;
        public static final double kaVolts                      = 0.29742;

        public static final double kPDriveVel                   = 2.5398;

        public static final double kTrackWidth                  = 0.6;
        public static final DifferentialDriveKinematics kDriveKinematics
            = new DifferentialDriveKinematics(kTrackWidth);

        public static final class kAuto {
            public static final double kMaxVolts                = 10;

            public static final double kMaxSpeed                = 2;
            public static final double kMaxAcceleration         = 2;

            // Default baseline values
            // https://docs.wpilib.org/en/stable/docs/software/pathplanning/trajectory-tutorial/entering-constants.html#ramsete-parameters
            public static final double kRamseteB                = 2;
            public static final double kRamseteZeta             = 0.7;
        }

        public static final class kDriveteam {
            public static final double rampRate                 = 0.2;

            public static final double defaultSpeedMultiplier   = 0.8;
            public static final double defaultTurningMultiplier = 0.8;
                
            public static final double slowSpeed                = 0.5;
            public static final double slowTurn                 = 0.6;

            public static final double boostSpeed               = 1;
            public static final double boostTurningSpeed        = 1;
                
            public static final double kChangeRamp              = 0.5;
            public static final int timerLength                 = 50;

            public static final double maxSpinSpeed             = 3;
            public static final double lowerSpinSpeed           = 0.7;
            public static final double spinRamp                 = 1;
            public static final int lowerTimer                  = 10;

            public static final double rumbleIntensity          = 1;

            public static enum GearState {
                kSlow,
                kDefault,
                kBoost
            }
        }
    }

    public static final class kClaw {

        public static final int clawCANID                       = 29;

        public static final int dutyCycleChannel                 = 3;

        public static final int ToFCANID                         = 36;

        public static final int currentLimit                    = 30;

        public static final double openPosition                  = 3300;
        public static final double closePosition                 = 24500;

        public static final double zeroSpeed                    = 0.1;

        public static final double encoderOffset                = 400;

        public static final double kP                            = 0.2;
        public static final double kI                            = 0;
        public static final double kD                            = 0;
        public static final double kF                            = 0;

        //distance from the claw to the object in front of it
        public static final double objectRange                  = 150;

    }

    public static final class kGyro {
        public static final int id_gyro                         = 10;

        public static final AxisDirection mountPoseForward      = AxisDirection.NegativeY;
        public static final AxisDirection mountPoseUp           = AxisDirection.PositiveZ;
    }

    public static final class kTrajectoryPath {
        public static final String MID_GRID_PLACE_AND_BALANCE
            = "MID Grid place and balance";
        public static final String WALL_GRID_PLACE_AND_BALANCE
            = "WALL Grid place and balance";
        public static final String LOADING_GRID_PLACE_AND_BALANCE
            = "LOADING Grid place and balance";
        public static final String SIDE_GRIDS_PLACE_AND_LEAVE_COMMMUNITY
            = "SIDE Grids place and leave community";

        public static final String[] paths = new String[] {
            MID_GRID_PLACE_AND_BALANCE, WALL_GRID_PLACE_AND_BALANCE,
            LOADING_GRID_PLACE_AND_BALANCE, SIDE_GRIDS_PLACE_AND_LEAVE_COMMMUNITY};
    }

    public static final class kBalancing {
        public static final double targetPitch                  = 0;
        public static final double maxAngle                     = 33.25;
        public static final double angleTolerance               = 1.5;

        public static final double kP                           = 0.036;
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
        public static final int staticTime                      = 750;

        public static class kConfig {

            public static final int CANID                       = 19;
            public static final int LEDCount                    = 94;

            public static final int LEDInnerRight               = 30;
            public static final int LEDInnerLeft                = 26;
            public static final int LEDOutter                   = 15;
        }

        public static class kColors {

            public static final int[] idle                      = {255, 134 , 0};
            public static final int[] cube                      = {142, 39, 245};
            public static final int[] cone                      = {237, 120, 0};

            public static final int LEDSinCount                 = 8;
            public static final double kSpeed                   = 0.5;

            public static final double sinFrequency             = 0.025;
            public static final double sinFrequencySpeed        = 20;

            public static final int chargeSpeed                 = 4;

            public static final double gameSpeed                = 0.2;
            
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

    public static class kArmSubsystem {
        public static final int kMotor1ID                       = 32;
        public static final int kMotor2ID                       = 33;
        public static final int kEncoderChannel                 = 4;

        public static final double kVoltageLimit                = 10.5;
        public static final int kCurrentLimit                   = 30;
        public static final double kPositionTolerance           = 0.1;
        public static final double kg                           = 0.4;
        public static final double knintydegreepos              = -0.042;

        public static class kPID {
            public static final double kP                       = 50;
            public static final double kI                       = 0;
            public static final double kD                       = 0;
        }
    
        public static class kSetpoints{
            public static final double kfront                   = 0.5; 
            public static final double kback                    = 0.0;

            public static final double kHandoffPosition         = 0.55;
            public static final double kGetConeFromIntake       = 0.55;

            public static final double kScoreHighShoulderSide   = 0.077;
            public static final double kScoreMidShoulderSide    = 0.5;
            public static final double kScoreMidIntakeSide      = 0.47;
        }
    }

    public static final class kTelescope {

        public static final double kCentemetreSafetyFactor      = 1.0;

        public static final class kDeviceID {
            public static final int MOTOR_CAN_ID                = 24;

            public static final int MAX_LIMIT_SWITCH_ID         = 1;
            public static final int MIN_LIMIT_SWITCH_ID         = 2;
        }

        public static final class kPID {
            public static final double kP                       = 0.1;
            public static final double kI                       = 0.0;
            public static final double kD                       = 0.0;
            public static final double kF                       = 0.0;
        }

        public static final class kSprocket {
            public static final double kGearPitchDiameter       = 3.637; // Pitch diameter of sprocket in cm
            public static final double kGearPitchCircumfrence   = Math.PI * kGearPitchDiameter; // Pitch Circumfrence in cm
            
            // public static final double kNumberOfEncoderTicks        = 42;
            public static final double kGearConversionFactor    = kGearPitchCircumfrence * 0.05;
        }

        public static final class kDestinations {
            public static final double kRetracted               = 0.0;

            public static final double kExtended                = 32.9;
            public static final double kMidFront                = 1.50;
            public static final double kMidBack                 = 32.8;
            
            public static final double kHandoff                 = 0.0;

            public static final double kGroundFront             = 0.0; // placeholder
            public static final double kGroundBack              = 0.0; // placeholder
            
            public static final double kLoading                 = 0.0; // placeholder

        }
    }

    public static final class kIntake {
        public static final int id_motPivot                     = 35;
        public static final int id_motWrist                     = 34;
        public static final int id_motRoller                    = 28;

        public static final int chnl_encWrist                   = 0;

        public static final double kPivotP                      = 100; /* placeholder */
        public static final double kWristP                      = 20;

        public static final double kPivotI                      = 0.0; /* placeholder */
        public static final double kWristI                      = 0.0; /* placeholder */

        public static final double kPivotD                      = 0.0; /* placeholder */
        public static final double kWristD                      = 0.0; /* placeholder */

        public static final class kSetpoints {
            public static final class kPivotSetpoints {
                public static final double kPivotExtended       = -4.5; /* -6.6 */
                public static final double kPivotStoring        = -2.3; /* 0 */
            }

            public static final class kWristSetpoints {
                public static final double kWristPickup         = 0.77;
                public static final double kWristHandoff        = 0.41;
                public static final double kWristStoring        = 0.0;
            }
        }

        public static final class kVoltageLimits {
            public static final double kPivotVoltageLimit       = 12;
            public static final double kWristVoltageLimit       = 6;
        }

        public static final class kCurrentLimits {
            public static final int kPivotCurrentLimit          = 30;
            public static final int kWristCurrentLimit          = 30;
            public static final int kRollerCurrentLimit         = 30;
        }
    }
}
