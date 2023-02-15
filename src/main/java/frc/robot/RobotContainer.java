// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.kDrivetrain;
import frc.robot.Constants.kTrajectoryPath;
import frc.robot.Constants.kDrivetrain.kAuto;
import frc.robot.Constants.kOperator;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.auto.Auto;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
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
    private final ExampleSubsystem sys_exampleSubsystem;
    public final Drivetrain sys_drivetrain;

    // Commands
    private final DefaultDrive cmd_defaultDrive;

    // Trajectory
    private PathPlannerTrajectory m_placeConeWallGridAndBalance;
    private PathPlannerTrajectory m_placeConeLoadingGridAndBalance;
    private PathPlannerTrajectory m_placeConeMidGridAndBalance;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Driver controllers
        joystickMain = new CommandXboxController(kOperator.port_joystickMain);
        joystickSecondary = new CommandXboxController(kOperator.port_joystickSecondary);

        // Subsystems
        sys_exampleSubsystem = new ExampleSubsystem();
        sys_drivetrain = new Drivetrain();

        // Commands
        cmd_defaultDrive = new DefaultDrive(sys_drivetrain, joystickMain);

        // Trajectories
        m_placeConeWallGridAndBalance = PathPlanner.loadPath(kTrajectoryPath.PLACE_CONE_WALL_GRID_AND_BALANCE,
                                                            new PathConstraints(kAuto.kMaxSpeed, kAuto.kMaxAcceleration),
                                                            true);
        
        m_placeConeLoadingGridAndBalance = PathPlanner.loadPath(kTrajectoryPath.PLACE_CONE_LOADING_GRID_AND_BALANCE,
                                                                new PathConstraints(kAuto.kMaxSpeed, kAuto.kMaxAcceleration),
                                                                true);

        m_placeConeMidGridAndBalance = PathPlanner.loadPath(kTrajectoryPath.PLACE_CONE_MID_GRID_AND_BALANCE,
                                                            new PathConstraints(kAuto.kMaxSpeed, kAuto.kMaxAcceleration),
                                                            true);

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
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous

        // Disable ramp rate
        sys_drivetrain.rampRate(0);
        // Run auto path, then stop and re-set ramp rate
        return new Auto(sys_drivetrain, m_placeConeWallGridAndBalance)
            .andThen(() -> sys_drivetrain.tankDriveVoltages(0, 0))
            .andThen(() -> sys_drivetrain.rampRate(kDrivetrain.kMotor.rampRate));
    }
}

