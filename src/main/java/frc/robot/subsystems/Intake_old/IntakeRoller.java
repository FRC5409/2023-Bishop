// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake_old;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kIntake;

public class IntakeRoller extends SubsystemBase
{
  private final WPI_TalonFX motor;

  private final ShuffleboardTab tab_intake;
  private final GenericEntry rollerSpeed;

  public IntakeRoller()
  {
    motor = new WPI_TalonFX(kIntake.id_motRoller);
    motor.configFactoryDefault();
    motor.setNeutralMode(NeutralMode.Brake);

    tab_intake = Shuffleboard.getTab("Intake");
    rollerSpeed = tab_intake.add("Roller Speed", getRollerSpeed()).getEntry();
  }

  public double getRollerSpeed()
  {
    return motor.getMotorOutputPercent();
  }

  @Override
  public void periodic()
  {
    rollerSpeed.setDouble(getRollerSpeed());
  }
}
