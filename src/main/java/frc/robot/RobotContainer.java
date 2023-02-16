// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.kDrivetrain;
import frc.robot.Constants.kOperator;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.Intake.Sequence.IntakeHandoffSequence;
import frc.robot.commands.Intake.Sequence.IntakePickupSequence;
import frc.robot.commands.auto.Auto;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeWrist;
import frc.robot.subsystems.Intake.IntakeRoller;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
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
    private final IntakePivot sys_intakePivot;
    private final IntakeWrist sys_intakeWrist;
    private final IntakeRoller sys_intakeRoller;

    // Commands
    private final DefaultDrive cmd_defaultDrive;

    // Sequential commands
    private final IntakePickupSequence seq_intakePickup;
    private final IntakeHandoffSequence seq_intakeHandoff;

    // Trajectory
    private Trajectory m_trajectory;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(Trajectory trajectory)
    {

        // Driver controllers
        joystickMain = new CommandXboxController(kOperator.port_joystickMain);
        joystickSecondary = new CommandXboxController(kOperator.port_joystickSecondary);

        // Trajectory paths
        m_trajectory = trajectory;

        // Subsystems
        sys_drivetrain = new Drivetrain();
        sys_intakePivot = new IntakePivot();
        sys_intakeWrist = new IntakeWrist();
        sys_intakeRoller = new IntakeRoller();

        // Commands
        cmd_defaultDrive = new DefaultDrive(sys_drivetrain, joystickMain);
        // cmd_pivotMoveUp =  new PivotMove(sys_intake, kVoltage.kPivotVoltage, true);
        // cmd_pivotMoveDown = new PivotMove(sys_intake, kVoltage.kPivotVoltage, false);
        // cmd_rollerCapture = new RollerMove(sys_intake, kVoltage.kRollerVoltage, true);
        // cmd_rollerRelease = new RollerMove(sys_intake, kVoltage.kRollerVoltage, false);

        // Sequential commands
        seq_intakePickup = new IntakePickupSequence(sys_intakePivot, sys_intakeWrist, sys_intakeRoller);
        seq_intakeHandoff = new IntakeHandoffSequence(sys_intakePivot, sys_intakeWrist, sys_intakeRoller);
        
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


    private void configureBindings()
    {
        // joystickMain.y()
        //     .whileTrue(cmd_pivotMoveUp);

        // joystickMain.a()
        //     .whileTrue(cmd_pivotMoveDown);

        // joystickMain.x()
        //     .whileTrue(cmd_rollerCapture);
        
        // joystickMain.b()
        //     .whileTrue(cmd_rollerRelease);
        
        joystickMain.rightBumper()
            .whileTrue(seq_intakePickup);

        joystickMain.rightBumper()
            .whileFalse(seq_intakeHandoff);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // An example command will be run in autonomous

        // Disable ramp rate
        sys_drivetrain.rampRate(0);
        // Reset odometry
        sys_drivetrain.resetOdometry(m_trajectory.getInitialPose());
        // Run auto path, then stop and re-set ramp rate
        return new Auto(sys_drivetrain, m_trajectory)
            .andThen(() -> sys_drivetrain.tankDriveVoltages(0, 0))
            .andThen(() -> sys_drivetrain.rampRate(kDrivetrain.kMotor.rampRate));
    }
}

