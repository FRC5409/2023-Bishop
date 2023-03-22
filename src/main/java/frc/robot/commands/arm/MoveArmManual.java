// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kCANdle;
import frc.robot.Constants.kCANdle.AnimationTypes;
import frc.robot.Constants.kCANdle.LEDColorType;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Candle;

public class MoveArmManual extends CommandBase {
  private final ArmPIDSubsystem sys_arm;
  private final Candle sys_candle;
  private LEDColorType currentType;
  private double m_voltage;

  private int timer;
  /** Creates a new MoveArmManual. */
  
  public MoveArmManual(ArmPIDSubsystem armPIDSubsystem, double voltage, Candle candle) {
    sys_arm = armPIDSubsystem;
    this.m_voltage = voltage;
    sys_candle = candle;

    addRequirements(sys_arm, candle);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_arm.disable();
    currentType = sys_candle.getLEDType();
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;
    sys_arm.moveVolts(m_voltage);
    
    if (timer % 6 < 3) {
      sys_candle.setAnimation(AnimationTypes.Static, 255, 255, 255);
    } else {
      switchToDefaultColor();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    double newSetpoint = sys_arm.getMeasurement();
    sys_arm.setSetpoint(newSetpoint);
    sys_arm.enable();

    switchToDefaultColor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void switchToDefaultColor() {
    if (currentType == LEDColorType.Cone) {
      sys_candle.setAnimation(
        AnimationTypes.Static,
        kCANdle.kColors.cone[0],
        kCANdle.kColors.cone[1],
        kCANdle.kColors.cone[2]
      );
    } else {
      sys_candle.setAnimation(
        AnimationTypes.Static,
        kCANdle.kColors.cube[0],
        kCANdle.kColors.cube[1],
        kCANdle.kColors.cube[2]
      );
    }
  }
}
