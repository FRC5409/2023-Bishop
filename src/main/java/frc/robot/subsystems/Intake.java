// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kIntake;

public class Intake extends SubsystemBase
{
  private final CANSparkMax pivot;
  private final CANSparkMax wrist;
  private final WPI_TalonFX roller;

  private final PIDController pid_pivot;
  private final PIDController pid_wrist;

  private final DutyCycleEncoder enc_pivot;
  private final RelativeEncoder enc_wrist;

  private final ShuffleboardTab tab_intake;
  private final GenericEntry pos_encPivot, pos_encWrist;
  
  public Intake()
  {
    pivot = new CANSparkMax(kIntake.id_motPivot, MotorType.kBrushless);
    pivot.restoreFactoryDefaults();
    pivot.setIdleMode(IdleMode.kBrake);

    wrist = new CANSparkMax(kIntake.id_motPivot, MotorType.kBrushless);
    wrist.restoreFactoryDefaults();
    wrist.setIdleMode(IdleMode.kBrake);

    roller = new WPI_TalonFX(kIntake.id_motRoller);
    roller.configFactoryDefault();
    roller.setNeutralMode(NeutralMode.Brake);

    pid_pivot = new PIDController(kIntake.kPivotP, kIntake.kPivotI, kIntake.kPivotD);
    pid_wrist = new PIDController(kIntake.kWristP, kIntake.kWristI, kIntake.kWristD);

    enc_pivot = new DutyCycleEncoder(kIntake.id_encPivot);
    enc_wrist = wrist.getEncoder();

    tab_intake = Shuffleboard.getTab("Intake");
    pos_encPivot = tab_intake.add("Pivot Pos", getPivotPos()).getEntry();
    pos_encWrist = tab_intake.add("Wrist Pos", getWristPos()).getEntry();;
  }

  public double getPivotPos()
  {
    return enc_pivot.getAbsolutePosition();
  }

  public double getWristPos()
  {
    return enc_wrist.getPosition();
  }

  public void pivotControl(double voltage)
  {
    pivot.setVoltage(voltage);
  }

  public void setPivotDirection(boolean inverted)
  {
    if (inverted)
    {
      pivot.setInverted(true);
    }
    else
    {
      pivot.setInverted(false);
    }
  }

  public void wristControl(double voltage)
  {
    wrist.setVoltage(voltage);
  }

  public void setWristDirection(boolean inverted)
  {
    if (inverted)
    {
      wrist.setInverted(true);
    }
    else
    {
      wrist.setInverted(false);
    }
  }

  public void rollerControl(double voltage)
  {
    roller.setVoltage(voltage);
  }

  public void setRollerDirection(boolean inverted)
  {
    if (inverted)
    {
      roller.setInverted(true);
    }
    else
    {
      roller.setInverted(false);
    }
  }

  @Override
  public void periodic()
  {
    pos_encPivot.setDouble(getPivotPos());
    pos_encWrist.setDouble(getWristPos());
  }
}
