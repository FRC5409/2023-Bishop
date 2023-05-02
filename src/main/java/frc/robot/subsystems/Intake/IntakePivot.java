// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
  
package frc.robot.subsystems.Intake;

//imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.kIntake;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class IntakePivot extends PIDSubsystem {
  private final CANSparkMax motor;
  private final DutyCycleEncoder encoder;

  //set up fields for the PID values in Constants file
  boolean debugMode = true;
  private ShuffleboardTab tab_intake;
  private GenericEntry kP, kI, kD, encPos;

  //setting up Pivot PID Subsystem 
  public IntakePivot() {
    super(new PIDController(kIntake.kPivotP, kIntake.kPivotI, kIntake.kPivotD));

    getController().setTolerance(kIntake.kPivotTolerance);

    motor = new CANSparkMax(kIntake.id_motPivot, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(kIntake.kIntakeCurrentLimit);
    motor.setInverted(true);
    motor.burnFlash();

    encoder = new DutyCycleEncoder(kIntake.port_encPivot);

    //debug mode :)
    if (debugMode) {
      tab_intake = Shuffleboard.getTab("Intake");
      kP = tab_intake.add("kPivotP", kIntake.kPivotP).getEntry();
      kI = tab_intake.add("kPivotI", kIntake.kPivotI).getEntry();
      kD = tab_intake.add("kPivotD", kIntake.kPivotD).getEntry();
      encPos = tab_intake.add("Pivot Abs Pos", getMeasurement()).getEntry();
    }
  }

  @Override
  public void useOutput(double output, double setpoint) {

    //setting voltage limits  
    if (output > kIntake.kVoltageLimits.kPivotVoltageLimit) {
      motor.setVoltage(kIntake.kVoltageLimits.kPivotVoltageLimit);
    } else if (output < -kIntake.kVoltageLimits.kPivotVoltageLimit) {
      motor.setVoltage(-kIntake.kVoltageLimits.kPivotVoltageLimit);
    } else {
      motor.setVoltage(output);
    }
  }

  @Override
  //getting absolute position using returned values on the encoder
  public double getMeasurement() {
    return encoder.getAbsolutePosition();
  }

  public void pivotControl(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void periodic() {
    super.periodic();
    if (debugMode) {
      encPos.setDouble(encoder.getAbsolutePosition());
    }
  }
}
