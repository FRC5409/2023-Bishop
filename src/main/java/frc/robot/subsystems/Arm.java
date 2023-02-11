// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import frc.robot.Constants;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Arm extends SubsystemBase {
  private final CANSparkMax m_motor1;
  private final CANSparkMax m_motor2;
  private final AbsoluteEncoder m_encoder;
  private SparkMaxPIDController m_pidController;
  private final ShuffleboardTab sb_armTab;
  private final GenericEntry kP,kI,kD,AbsolutePosition,Angle;

  /** Creates a new Arm. */
  public Arm() {
    m_motor1 = new CANSparkMax(Constants.kArmSubsystem.kMotor1ID, MotorType.kBrushless);
    m_motor2 = new CANSparkMax(Constants.kArmSubsystem.kMotor2ID, MotorType.kBrushless);
    m_encoder = m_motor1.getAbsoluteEncoder(Type.kDutyCycle);
    m_pidController = m_motor1.getPIDController(); 
    setPIDFvalues();

    m_motor1.restoreFactoryDefaults();
    m_motor1.setIdleMode(IdleMode.kCoast);
    m_motor1.setSmartCurrentLimit(Constants.kArmSubsystem.kCurrentLimit);

    m_motor2.restoreFactoryDefaults();
    m_motor2.follow(m_motor1);
    m_motor2.setIdleMode(IdleMode.kCoast);
    m_motor2.setSmartCurrentLimit(Constants.kArmSubsystem.kCurrentLimit);

    sb_armTab = Shuffleboard.getTab("Arm"); // shuffleboard tab and values
    kP = sb_armTab.add("kP", Constants.kArmSubsystem.kPID.kP).getEntry();
    kI = sb_armTab.add("kI", Constants.kArmSubsystem.kPID.kI).getEntry();
    kD = sb_armTab.add("kD", Constants.kArmSubsystem.kPID.kD).getEntry();
    AbsolutePosition = sb_armTab.add("AbsolutePosition", getPosition()).withWidget(BuiltInWidgets.kTextView).getEntry();
    Angle = sb_armTab.add("Angle", getAngle()).getEntry();
    
  }

  public double getPosition() { // gets absolute position and returns the value 
    double ecd_value = m_encoder.getPosition();

    if (ecd_value < 0.3){  // used to fix the weird values from encoder
      AbsolutePosition.setDouble(ecd_value + 1 - Constants.kArmSubsystem.knintydegreepos);
      return ecd_value + 1 - Constants.kArmSubsystem.knintydegreepos;
    }else{
      AbsolutePosition.setDouble(ecd_value - Constants.kArmSubsystem.knintydegreepos);
      return ecd_value - Constants.kArmSubsystem.knintydegreepos;
    }
    // Return the process variable measurement here 
  }

  public void setPIDFvalues(){ // sets PID values
    m_pidController.setP(Constants.kArmSubsystem.kPID.kP);
    m_pidController.setI(Constants.kArmSubsystem.kPID.kI);
    m_pidController.setD(Constants.kArmSubsystem.kPID.kD);
  }
  
  public double calculateFF(){
    return Constants.kArmSubsystem.kg*Math.cos(Math.toRadians(getAngle()));
  }

  public double getAngle(){
    return getPosition()*360;
  }

  public void setReference(double setReference){
    m_pidController.setReference(setReference, ControlType.kPosition, 0, calculateFF());
  }

  @Override
  public void periodic() {
    Angle.setDouble(getAngle());

    // This method will be called once per scheduler run
  }
}
