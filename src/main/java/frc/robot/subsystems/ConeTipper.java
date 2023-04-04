// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.kConeTipper;

public class ConeTipper extends PIDSubsystem
{
  private final CANSparkMax motor;
  private final DutyCycleEncoder encoder;

  boolean debugMode = true;
  private ShuffleboardTab tab_intake;
  private GenericEntry kP, kI, kD, encPos;

  public ConeTipper()
  {
    super(new PIDController(kConeTipper.kP, kConeTipper.kI, kConeTipper.kD));

    motor = new CANSparkMax(kConeTipper.id_mot, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(kConeTipper.kCurrentLimit);
    motor.setInverted(true);
    motor.burnFlash();

    encoder = new DutyCycleEncoder(kConeTipper.port_enc);

    if (debugMode) {
      tab_intake = Shuffleboard.getTab("Intake");
      kP = tab_intake.add("kPivotP", kConeTipper.kP).getEntry();
      kI = tab_intake.add("kPivotI", kConeTipper.kI).getEntry();
      kD = tab_intake.add("kPivotD", kConeTipper.kD).getEntry();
      encPos = tab_intake.add("Pivot Abs Pos", getMeasurement()).getEntry();
    }
  }

  @Override
  public void useOutput(double output, double setpoint)
  {
    
    if (output > kConeTipper.kVoltageLimit)
    {
      motor.setVoltage(kConeTipper.kVoltageLimit);
    }
    else if (output < -kConeTipper.kVoltageLimit)
    {
      motor.setVoltage(-kConeTipper.kVoltageLimit);
    }
    else
    {
      motor.setVoltage(output);
    }
  }

  @Override
  public double getMeasurement()
  {
    return encoder.getAbsolutePosition();
  }

  public void pivotControl(double voltage)
  {
    motor.setVoltage(voltage);
  }

  @Override
  public void periodic()
  {
    super.periodic();
    if (debugMode) {
      encPos.setDouble(encoder.getAbsolutePosition());
    }
  }
}
