// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Intake.Manual.PivotMove;
import frc.robot.commands.Intake.Manual.WristMove;
import frc.robot.commands.Intake.Manual.RollerMove;
import frc.robot.Constants.kDrivetrain;
import frc.robot.Constants.kOperator;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.Intake.Sequence.IntakeHandoffSequence;
import frc.robot.commands.Intake.Sequence.IntakePickupSequence;
import frc.robot.commands.Intake.Sequence.PivotZeroEncoder;
import frc.robot.commands.Intake.Sequence.WristHandoff;
import frc.robot.commands.Intake.Sequence.WristPickup;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.TelescopeTo;
import frc.robot.commands.auto.Auto;
import frc.robot.subsystems.Candle;
import frc.robot.subsystems.Claw;
import frc.robot.Constants.kDrivetrain.kDriveteam;
import frc.robot.Constants.kDrivetrain.kDriveteam.GearState;
import frc.robot.commands.GearShift;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.commands.ArmRotation;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeWrist;
import frc.robot.subsystems.Intake.IntakeRoller;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.trajectory.Trajectory;
import frc.robot.subsystems.Telescope;

import com.pathplanner.lib.PathPlannerTrajectory;
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
    private final IntakeWrist sys_intakeWrist;
    private final IntakeRoller sys_intakeRoller;

    // Commands
    private final DefaultDrive cmd_defaultDrive;
    // private final PivotMove cmd_pivotUp;
    // private final PivotMove cmd_pivotDown;
    private final WristPickup cmd_wristPickup;
    private final WristHandoff cmd_wristHandoff;
    private final WristMove cmd_wristDown;
    private final RollerMove cmd_rollerCapture;
    private final RollerMove cmd_rollerRelease;
    // private final PivotZeroEncoder cmd_pivotInwardZero;

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

    // // Trajectory
    private PathPlannerTrajectory m_trajectory;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(PathPlannerTrajectory trajectory)
    {

        // Driver controllers
        joystickMain = new CommandXboxController(kOperator.port_joystickMain);
        joystickSecondary = new CommandXboxController(kOperator.port_joystickSecondary);

        // // Trajectory paths
        m_trajectory = trajectory;

        // Subsystems
        sys_drivetrain = new Drivetrain();
        // sys_intakePivot = new IntakePivot();
        sys_intakeWrist = new IntakeWrist();
        sys_intakeRoller = new IntakeRoller();

        // Commands
        // cmd_pivotUp =  new PivotMove(sys_intakePivot, 0.2);
        // cmd_pivotDown = new PivotMove(sys_intakePivot, -0.2);
        cmd_wristPickup = new WristPickup(sys_intakeWrist);
        cmd_wristHandoff = new WristHandoff(sys_intakeWrist);
        cmd_wristDown = new WristMove(sys_intakeWrist, -0.2);
        cmd_rollerCapture = new RollerMove(sys_intakeRoller, 0.3);
        cmd_rollerRelease = new RollerMove(sys_intakeRoller, -0.3);
        // cmd_pivotInwardZero = new PivotZeroEncoder(sys_intakePivot);

        // Sequential commands
        // seq_intakePickup = new IntakePickupSequence(sys_intakePivot, sys_intakeWrist, sys_intakeRoller);
        // seq_intakeHandoff = new IntakeHandoffSequence(sys_intakePivot, sys_intakeWrist, sys_intakeRoller);
        
        sys_claw = new Claw();
        sys_candle = new Candle();
        sys_ArmPIDSubsystem = new ArmPIDSubsystem();
        sys_telescope = new Telescope();

        // Commands
        cmd_defaultDrive = new DefaultDrive(sys_drivetrain, joystickMain);

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
        // joystickMain.povUp()
        //     .whileTrue(cmd_pivotUp);
        
        // joystickMain.povDown()
        //     .whileTrue(cmd_pivotDown);

        joystickMain.y()
             .onTrue(cmd_wristPickup);

        joystickMain.a()
             .whileTrue(cmd_wristHandoff);

        joystickMain.x()
            .whileTrue(cmd_rollerCapture);
        
        joystickMain.b()
            .whileTrue(cmd_rollerRelease);
        
        // joystickMain.rightBumper()
        //     .whileTrue(seq_intakePickup);

        // joystickMain.rightBumper()
        //     .whileFalse(seq_intakeHandoff);
        
        // joystickMain.rightStick()
        //     .onTrue(cmd_pivotInwardZero);
    // private void configureBindings() {

    //     // joystickMain.x()
    //     //     .onTrue(new OpenClaw(sys_claw).andThen(new CloseClaw(sys_claw)))
    //     //     .onFalse(new CloseClaw(sys_claw));
    //     joystickMain.x()
    //     .onTrue(new OpenClaw(sys_claw))
    //     .onFalse(new CloseClaw(sys_claw));
        

    //     joystickMain.y()
    //         .onTrue(Commands.runOnce(() -> sys_claw.zeroEncoder()));

    //     joystickMain.leftBumper()
    //         .onTrue(cmd_lowSpeed)
    //         .onFalse(cmd_midSpeed);

    //     joystickMain.rightBumper()
    //         .onTrue(cmd_highSpeed)
    //         .onFalse(cmd_midSpeed);

    //     joystickSecondary.povUp().onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kExtended));
    //     joystickSecondary.povLeft().onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kMid));
    //     joystickSecondary.povDown().onTrue(new TelescopeTo(sys_telescope, Constants.kTelescope.kDestinations.kRetracted));

    //     // joystickSecondary.x().onTrue(new ArmRotation(sys_ArmPIDSubsystem, 0.55)); // intake back
    //     // joystickSecondary.b().onTrue(new ArmRotation(sys_ArmPIDSubsystem, -.06)); // intake front
    //     // joystickSecondary.y().onTrue(new ArmRotation(sys_ArmPIDSubsystem, .057)); // placement forward
    //     // joystickSecondary.a().onTrue(new ArmRotation(sys_ArmPIDSubsystem, 0.44)); // placement back
    //   //  joystickSecondary.leftBumper().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kIdlepos));
    //     joystickSecondary.x().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kfront)); // pickup from loading station
    //     joystickSecondary.b().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kback)); // pickup from floor
    //    // joystickSecondary.y().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kplacehigh));
    //    // joystickSecondary.a().onTrue(new ArmRotation(sys_ArmPIDSubsystem, Constants.kArmSubsystem.kSetpoints.kplacelow));
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
            .andThen(() -> sys_drivetrain.rampRate(kDrivetrain.kDriveteam.rampRate));
    }

}


