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
import frc.robot.commands.TelescopeTo;
import frc.robot.commands.auto.Auto;
import frc.robot.commands.auto.BalancingChargeStation;
import frc.robot.subsystems.Candle;
import frc.robot.subsystems.Claw;
import frc.robot.Constants.kDrivetrain.kDriveteam;
import frc.robot.Constants.kDrivetrain.kDriveteam.GearState;
import frc.robot.commands.GearShift;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.commands.ArmRotation;
import frc.robot.subsystems.Drivetrain;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import frc.robot.subsystems.Telescope;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    // Driver controllers
    private final CommandXboxController joystickMain;
    private final CommandXboxController joystickSecondary;

    // Subsystems
    public final Drivetrain sys_drivetrain;
    public final Claw sys_claw;
    // public final Candle sys_candle;
    public final ArmPIDSubsystem sys_ArmPIDSubsystem;
    public final Telescope sys_telescope;

    // Commands
    private final DefaultDrive cmd_defaultDrive;
    private final BalancingChargeStation cmd_balance;
    
    private final GearShift cmd_lowSpeed;
    private final GearShift cmd_midSpeed;
    private final GearShift cmd_highSpeed;

    // Trajectory & autonomous path chooser
    private PathPlannerTrajectory m_testingPath;
    private PathPlannerTrajectory m_placeConeWallGridAndBalance;
    private PathPlannerTrajectory m_placeConeLoadingGridAndBalance;
    private PathPlannerTrajectory m_placeConeMidGridAndBalance;
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
        sys_claw = new Claw();
        // sys_candle = new Candle();
        sys_ArmPIDSubsystem = new ArmPIDSubsystem();
        sys_telescope = new Telescope();

        // Commands
        cmd_defaultDrive = new DefaultDrive(sys_drivetrain, joystickMain);
        cmd_balance = new BalancingChargeStation(sys_drivetrain);

        // Trajectory & autonomous path chooser
        PathConstraints pathConstraints = new PathConstraints(kAuto.kMaxSpeed, kAuto.kMaxAcceleration);
        m_testingPath = PathPlanner.loadPath(
                                                    kTrajectoryPath.TESTING_PATH,
                                                    pathConstraints);
        m_placeConeWallGridAndBalance = PathPlanner.loadPath(
                                                    kTrajectoryPath.PLACE_CONE_WALL_GRID_AND_BALANCE,
                                                    pathConstraints,
                                                    true);
        m_placeConeLoadingGridAndBalance = PathPlanner.loadPath(
                                                    kTrajectoryPath.PLACE_CONE_LOADING_GRID_AND_BALANCE,
                                                    pathConstraints,
                                                    true);
        m_placeConeMidGridAndBalance = PathPlanner.loadPath(
                                                    kTrajectoryPath.PLACE_CONE_MID_GRID_AND_BALANCE,
                                                    pathConstraints,
                                                    true);

        sb_driveteam = Shuffleboard.getTab("Drive Team");
        sc_choosePath = new SendableChooser<PathPlannerTrajectory>();
        sc_choosePath.setDefaultOption("Place cone wall grid and balance", m_placeConeWallGridAndBalance);
        sc_choosePath.addOption("Place cone loading grid and balance", m_placeConeLoadingGridAndBalance);
        sc_choosePath.addOption("Place cone mid grid and balance", m_placeConeMidGridAndBalance);
        sc_choosePath.addOption("Testing path", m_testingPath);
        sb_driveteam.add("Auto path", sc_choosePath);
        
        cmd_lowSpeed = new GearShift(GearState.kSlow, sys_drivetrain);
        cmd_midSpeed = new GearShift(GearState.kDefault, sys_drivetrain);
        cmd_highSpeed = new GearShift(GearState.kBoost, sys_drivetrain);

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
    private void configureBindings() {

        // joystickMain.x()
        //     .onTrue(new OpenClaw(sys_claw).andThen(new CloseClaw(sys_claw)))
        //     .onFalse(new CloseClaw(sys_claw));
        joystickMain.x()
        .onTrue(new OpenClaw(sys_claw))
        .onFalse(new CloseClaw(sys_claw));
        

        joystickMain.y()
            .onTrue(Commands.runOnce(() -> sys_claw.zeroEncoder()));

        joystickMain.leftBumper()
            .onTrue(cmd_lowSpeed)
            .onFalse(cmd_midSpeed);

        joystickMain.rightBumper()
            .onTrue(cmd_highSpeed)
            .onFalse(cmd_midSpeed);

        joystickSecondary.povUp().onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kExtended));
        joystickSecondary.povLeft().onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kMid));
        joystickSecondary.povDown().onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kRetracted));

        // joystickSecondary.x().onTrue(new ArmRotation(sys_ArmPIDSubsystem, 0.55)); // intake back
        // joystickSecondary.b().onTrue(new ArmRotation(sys_ArmPIDSubsystem, -.06)); // intake front
        // joystickSecondary.y().onTrue(new ArmRotation(sys_ArmPIDSubsystem, .057)); // placement forward
        // joystickSecondary.a().onTrue(new ArmRotation(sys_ArmPIDSubsystem, 0.44)); // placement back
      //  joystickSecondary.leftBumper().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kIdlepos));
        joystickSecondary.x().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kfront)); // pickup from loading station
        joystickSecondary.b().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kback)); // pickup from floor
       joystickSecondary.y().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kPlaceHigh));

       joystickMain.b()
        .whileTrue(cmd_balance);
       // joystickSecondary.a().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kplacelow));
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
        return new Auto(sys_drivetrain, sys_ArmPIDSubsystem, sys_telescope, sys_claw, chosenTrajectory)
            .andThen(() -> sys_drivetrain.tankDriveVoltages(0, 0))

            .andThen(() -> sys_drivetrain.rampRate(kDriveteam.rampRate));
    }

}


