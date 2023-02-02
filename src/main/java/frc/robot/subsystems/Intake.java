// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kIntake;

public class Intake extends SubsystemBase
{
  private final CANSparkMax mot_pivot;
  private final WPI_TalonFX mot_wrist;
  private final WPI_TalonFX mot_roller;

  private final DutyCycleEncoder enc_wrist;

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

    enc_wrist = new DutyCycleEncoder(kIntake.id_encWrist);
  }

  @Override
  public void periodic() {

  }
}
