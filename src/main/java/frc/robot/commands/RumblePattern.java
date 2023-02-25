// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.RumbleJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RumblePattern extends CommandBase {
  /** Creates a new RumblePattern. */
  boolean validConfig;
  boolean activeRumble;
  private final double[] m_pulseTime;
  private final double[] m_intervalTime; 
  private final int rumbleIndex; 

  //millis
  double pulseBegin;

  public RumblePattern(CommandXboxController joystick, double[] pulseTime, double[] intervalTime) {
    validConfig = true;
    activeRumble = false; 
    rumbleIndex = 0;
    m_pulseTime = pulseTime;
    m_intervalTime = intervalTime;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //check config
    if (m_pulseTime.length == m_intervalTime.length){

    } else {
      validConfig = false; 
      System.out.println("RumblePatter.java | Invalid configuration");
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!validConfig){
      return true; 
    }
    return false;
  }
}
