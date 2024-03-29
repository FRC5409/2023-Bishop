// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Util.Color;
import frc.robot.Constants.kClaw;
import frc.robot.Constants.kOperator;
import frc.robot.commands.LEDs.EStopAnimation;
import frc.robot.commands.LEDs.GameEndAnimation;
import frc.robot.commands.LEDs.SetTeamColor;
import frc.robot.commands.claw.ClawMovement;
import frc.robot.commands.disabled.DisablePIDSubsystems;
import frc.robot.commands.disabled.SetCoastMode;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    // Set coast mode after 5 seconds disabled
    new Trigger(this::isEnabled)
      .negate()
      .debounce(5)
      .onTrue(new SetCoastMode(m_robotContainer.sys_drivetrain, m_robotContainer.sys_telescope))
      .onTrue(new DisablePIDSubsystems(m_robotContainer.sys_intakeWrist, m_robotContainer.sys_intakePivot, m_robotContainer.sys_arm, m_robotContainer.sys_claw));

      new Trigger(
          () -> Math.round(DriverStation.getMatchTime()) == 1 && this.isTeleop()
        )
        .onTrue(new ClawMovement(m_robotContainer.sys_claw, kClaw.openPosition));

        
      new Trigger(() -> DriverStation.isEStopped())
        .onTrue(new EStopAnimation(m_robotContainer.sys_LED).ignoringDisable(true));

      new Trigger(this::isDisabled)
        .onTrue(new GameEndAnimation(m_robotContainer.sys_LED).ignoringDisable(true));
        
      new Trigger(this::isAutonomous)
        .negate()
        .and(this::isEnabled)
        .onTrue(new SetTeamColor(m_robotContainer.sys_LED));

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    m_robotContainer.rumbleController(0, 1);
    m_robotContainer.sys_claw.disable();
  }

  @Override
  public void disabledPeriodic() {
    
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    // Set brake mode
    m_robotContainer.sys_drivetrain.setNeutralMode(NeutralMode.Brake);
    m_robotContainer.sys_telescope.setNeutralMode(IdleMode.kBrake);

    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
    // Set brake mode
    m_robotContainer.sys_drivetrain.setNeutralMode(NeutralMode.Brake);
    m_robotContainer.sys_telescope.setNeutralMode(IdleMode.kBrake);

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    m_robotContainer.updateRumble();
    switch (Math.round((long) DriverStation.getMatchTime())) {
      case 30:
        m_robotContainer.rumbleController(kOperator.timerRumbleIntensity, 5);
        break;
      case 20:
        m_robotContainer.rumbleController(kOperator.timerRumbleIntensity, 5);
        break;
      case 10:
        //rumble controller
        m_robotContainer.rumbleController(kOperator.timerRumbleIntensity, 5);
        break;
      case 5:
        //switch to red
        m_robotContainer.rumbleController(kOperator.timerRumbleIntensity, 5);
        m_robotContainer.sys_LED.setColor(Color.kPureRed);
        break;
      case 3:
        //flashing red
        m_robotContainer.rumbleController(kOperator.timerRumbleIntensity, 10);
        new EStopAnimation(m_robotContainer.sys_LED).schedule();
        break;
      case 1:
        //rumble controller
        m_robotContainer.rumbleController(kOperator.timerRumbleIntensity, 10);
        break;
    }
  }
}