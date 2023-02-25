// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.kDrivetrain;
import frc.robot.Constants.kTrajectoryPath;
import frc.robot.Constants.kDrivetrain.kAuto;
import frc.robot.Constants.kOperator;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.PivotManualMove;
import frc.robot.commands.TelescopeTo;
import frc.robot.commands.Intake.IntakeHandoffSequence;
import frc.robot.commands.Intake.IntakePickupSequence;
import frc.robot.commands.Intake.PivotMove;
import frc.robot.commands.Intake.PivotZeroEncoder;
import frc.robot.commands.Intake.RollerMove;
import frc.robot.commands.Intake.WristMove;
import frc.robot.commands.auto.MidConeAuto;
import frc.robot.commands.auto.BalancingChargeStation;
import frc.robot.subsystems.Candle;
import frc.robot.subsystems.Claw;
import frc.robot.Constants.kDrivetrain.kDriveteam;
import frc.robot.Constants.kDrivetrain.kDriveteam.GearState;
import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import frc.robot.Constants.kIntake.kSetpoints.kWristSetpoints;
import frc.robot.commands.GearShift;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.commands.ArmRotation;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeWrist;
import frc.robot.subsystems.Intake.IntakeRoller;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import frc.robot.subsystems.Telescope;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

    // Driver controllers
    private final CommandXboxController joystickMain;
    private final CommandXboxController joystickSecondary;

    // Subsystems
    public final Drivetrain sys_drivetrain;
    // private final IntakePivot sys_intakePivot;
    // private final IntakeWrist sys_intakeWrist;
    // private final IntakeRoller sys_intakeRoller;

    // Commands
    private final DefaultDrive cmd_defaultDrive;
    // private final PivotZeroEncoder cmd_pivotZero;

    // Sequential commands
    // private final IntakePickupSequence seq_intakePickup;
    // private final IntakeHandoffSequence seq_intakeHandoff;
    public final Claw sys_claw;
    public final Candle sys_candle;
    public final ArmPIDSubsystem sys_ArmPIDSubsystem;
    public final Telescope sys_telescope;

    private final GearShift cmd_lowSpeed;
    private final GearShift cmd_midSpeed;
    private final GearShift cmd_highSpeed;

    // Trajectory & autonomous path chooser
    private PathPlannerTrajectory[] m_paths = new PathPlannerTrajectory[kTrajectoryPath.paths.length];
    private ShuffleboardTab sb_driveteam;
    private SendableChooser<PathPlannerTrajectory> sc_choosePath;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Driver controllers
        joystickMain = new CommandXboxController(kOperator.port_joystickMain);
        joystickSecondary = new CommandXboxController(kOperator.port_joystickSecondary);

        // Subsystems
        sys_drivetrain = new Drivetrain();
        // sys_intakePivot = new IntakePivot();
        // sys_intakeWrist = new IntakeWrist();
        // sys_intakeRoller = new IntakeRoller();

        // Sequential commands
        // seq_intakePickup = new IntakePickupSequence(sys_intakePivot, sys_intakeWrist, sys_intakeRoller);
        // seq_intakeHandoff = new IntakeHandoffSequence(sys_intakePivot, sys_intakeWrist, sys_intakeRoller);
        
        sys_claw = new Claw();
        sys_candle = new Candle();
        sys_ArmPIDSubsystem = new ArmPIDSubsystem();
        sys_telescope = new Telescope();

        // Commands
        cmd_defaultDrive = new DefaultDrive(sys_drivetrain, joystickMain);

        // Trajectory & autonomous path chooser
        PathConstraints pathConstraints = new PathConstraints(kAuto.kMaxSpeed, kAuto.kMaxAcceleration);

        for (int i = 0; i < m_paths.length; i++) {
            m_paths[i] = PathPlanner.loadPath(kTrajectoryPath.paths[i], pathConstraints, true);
        }

        sb_driveteam = Shuffleboard.getTab("Drive Team");
        sc_choosePath = new SendableChooser<PathPlannerTrajectory>();
        sc_choosePath.setDefaultOption(kTrajectoryPath.paths[0], m_paths[0]);
        for (int i = 0; i < m_paths.length; i++) {
            sc_choosePath.addOption(kTrajectoryPath.paths[i], m_paths[i]);
        }
        sb_driveteam.add("Auto path", sc_choosePath);
        
        cmd_lowSpeed = new GearShift(GearState.kSlow, sys_drivetrain);
        cmd_midSpeed = new GearShift(GearState.kDefault, sys_drivetrain);
        cmd_highSpeed = new GearShift(GearState.kBoost, sys_drivetrain);
        // cmd_pivotZero = new PivotZeroEncoder(sys_intakePivot);

        // Set default drive as drivetrain's default command
        sys_drivetrain.setDefaultCommand(cmd_defaultDrive);

        // Configure the trigger bindings
        configureBindings();
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be
     * created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
     * an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
     * {@link
     * CommandXboxController
     * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or
     * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    /**
     * y-intake pivot ascend
     * a-intake pivot descend
     * x-intake roller roll backwards
     * b-intake roller roll forwards */

     /*
      * starting with - 20% of 12 = 2.4
      */


    private void configureBindings() {

        joystickMain.x()
            .onTrue(new CloseClaw(sys_claw, false))
            .onFalse(new OpenClaw(sys_claw, false));
        
        // joystickMain.a()
        //     .whileTrue(seq_intakePickup)
        //     .whileFalse(seq_intakeHandoff);

        // joystickMain.rightStick()
        //     .onTrue(cmd_pivotZero);

        //TODO: FIX zeroing

        joystickMain.y()
            .onTrue(Commands.runOnce(() -> sys_claw.zeroEncoder()));

        joystickMain.povLeft()
            .onTrue(Commands.runOnce(() -> sys_claw.spinAt(-0.1)))
            .onFalse(Commands.runOnce(() -> sys_claw.stopMot()));

        joystickMain.povRight()
            .onTrue(Commands.runOnce(() -> sys_claw.spinAt(0.1)))
            .onFalse(Commands.runOnce(() -> sys_claw.stopMot()));



        joystickMain.leftBumper()
            .onTrue(cmd_lowSpeed)
            .onFalse(cmd_midSpeed);

        joystickMain.rightBumper()
            .onTrue(cmd_highSpeed)
            .onFalse(cmd_midSpeed);

        joystickSecondary.povUp()
            .onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kExtended));
        // joystickSecondary.povLeft()
            // .onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kMid));
        joystickSecondary.povDown()
            .onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kRetracted));

        // joystickSecondary.povUp()
        //     .onTrue(new ArmToPos(sys_telescope, sys_ArmPIDSubsystem, kArmSubsystem.kSetpoints.kToTop, kTelescope.kDestinations.kExtended));
        // joystickSecondary.povRight()
        //     .onTrue(new ArmToPos(sys_telescope, sys_ArmPIDSubsystem, kArmSubsystem.kSetpoints.kToMid, kTelescope.kDestinations.kMid));
        // joystickSecondary.povDown()
        //     .onTrue(new ArmToPos(sys_telescope, sys_ArmPIDSubsystem, kArmSubsystem.kSetpoints.kToHandoff, 0));

        joystickSecondary.x()
            .onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kfront)); // pickup from loading station
        joystickSecondary.b()
            .onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kback)); // pickup from floor

        // joystickSecondary.x().onTrue(new RotateArmGroup(sys_telescope, sys_ArmPIDSubsystem, kArmSubsystem.kSetpoints.kfront));
        // joystickSecondary.b().onTrue(new RotateArmGroup(sys_telescope, sys_ArmPIDSubsystem, kArmSubsystem.kSetpoints.kback));
    }

    

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous

        Shuffleboard.update();
        PathPlannerTrajectory chosenTrajectory = sc_choosePath.getSelected();

        // Disable ramp rate
        sys_drivetrain.rampRate(0);
        // Reset odometry
        sys_drivetrain.resetOdometry(chosenTrajectory.getInitialPose());
        // Run auto path, then stop and re-set ramp rate
        return new MidConeAuto(sys_drivetrain, sys_ArmPIDSubsystem, sys_telescope, sys_claw, chosenTrajectory)
            .andThen(() -> sys_drivetrain.tankDriveVoltages(0, 0))
            .andThen(() -> sys_drivetrain.rampRate(kDrivetrain.kDriveteam.rampRate));
    }

}


