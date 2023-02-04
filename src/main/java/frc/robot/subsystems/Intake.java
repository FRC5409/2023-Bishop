// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.kIntake;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase
{
  private final CANSparkMax mot_pivot;
  private final WPI_TalonFX mot_wrist;
  private final WPI_TalonFX mot_roller;

  private final DutyCycleEncoder enc_pivot;
  private final DutyCycleEncoder enc_wrist;

  private final ShuffleboardTab intake;
  private final GenericEntry entry_encPivot;
  private final GenericEntry entry_encWrist;

  public Intake()
  {
    mot_pivot = new CANSparkMax(kIntake.id_motorPivot, CANSparkMaxLowLevel.MotorType.kBrushless);
    mot_pivot.restoreFactoryDefaults();
    mot_pivot.setIdleMode(IdleMode.kBrake);
    
    mot_wrist = new WPI_TalonFX(kIntake.id_motorWrist);
    mot_wrist.configFactoryDefault();
    mot_wrist.setNeutralMode(NeutralMode.Brake);

    mot_roller = new WPI_TalonFX(kIntake.id_motorRoller);
    mot_roller.configFactoryDefault();
    mot_roller.setNeutralMode(NeutralMode.Brake);

    enc_pivot = new DutyCycleEncoder(kIntake.id_encPivot);
    enc_wrist = new DutyCycleEncoder(kIntake.id_encWrist);

    intake = Shuffleboard.getTab("Intake");
    entry_encPivot = intake.add("Pivot Pos", getPivotPos()).getEntry();
    entry_encWrist = intake.add("Wrist Pos", getWristPos()).getEntry();
  }

  public double getPivotPos()
  {
    return enc_pivot.getAbsolutePosition();
  }

  public double getWristPos()
  {
    return enc_wrist.getAbsolutePosition();
  }

  public void setPivotTurn(double speed)
  {
    mot_pivot.set(speed);
  }

  public void setWristTurn(double speed)
  {
    mot_wrist.set(speed);
  }

  public void setRollerTurn(double speed)
  {
    mot_roller.set(speed);
  }

  @Override
  public void periodic()
  {
    entry_encPivot.setDouble(getPivotPos());
    entry_encWrist.setDouble(getWristPos());
  }
}