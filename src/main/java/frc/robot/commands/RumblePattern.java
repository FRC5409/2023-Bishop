// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.RumbleJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RumblePattern extends CommandBase {
  private final CommandXboxController m_joystick; 
  private final double[] m_pulseTime;
  private final double[] m_intervalTime; 
  private final double m_intensity;
  private double timerStart;
  private double timerWaitStart; 
  private boolean isRumbling; 
  private boolean isWaiting;
  boolean validConfig;

  public RumblePattern(CommandXboxController joystick, double[] pulseTime, double[] intervalTime, double intensity) {
    m_pulseTime = pulseTime;
    m_intervalTime = intervalTime;
    m_intensity = intensity;
    m_joystick = joystick;

    validConfig = true;
    isRumbling = false; 
    timerStart = System.currentTimeMillis();
    timerWaitStart = System.currentTimeMillis();
  }

  public int doRumble(double length){
    if (!isRumbling){
      timerStart = System.currentTimeMillis();
      m_joystick.getHID().setRumble(null, m_intensity);
    } else {
      if ((System.currentTimeMillis() - timerStart) >= length){
        m_joystick.getHID().setRumble(null, m_intensity);
        isRumbling = false;
        return 1; 
      }
    }

    return 0; 
  }

public void doPattern() {
  for (int i = 0; i <= m_pulseTime.length;){
    if (!rumblePause(m_intervalTime[i])){
      i += doRumble(m_pulseTime[i]); //only itterates to the next index once the pulse has been completed 
    }
  }
}

private boolean rumblePause(double length){
  if (!isWaiting){
    timerWaitStart = System.currentTimeMillis();
    isWaiting = true;
  } else {
    if ((System.currentTimeMillis() - timerWaitStart) >= length){
      isWaiting = false; 
      return false; 
    }
  }
  return true;
}

private void checkConfig(){
  if (m_pulseTime.length != m_intervalTime.length){
    validConfig = false; 
  }
}
  @Override
  public void initialize() {
    checkConfig();
    if (validConfig){
      doPattern();
    }
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    m_joystick.getHID().setRumble(null, 0); //SAFETY
  }

  @Override
  public boolean isFinished() {
    return (validConfig ? false: true);  
  }
}
