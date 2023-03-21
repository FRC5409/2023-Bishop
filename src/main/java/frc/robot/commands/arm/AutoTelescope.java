// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import frc.robot.Constants;
import frc.robot.Constants.kArmSubsystem;
import frc.robot.Constants.kDrivetrain.kAuto;
import frc.robot.Constants.kTelescope.kAutoTelescope;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Telescope;


public class AutoTelescope extends CommandBase {
  /** Creates a new AutoTelescope. */

  private static Telescope sys_telescope;
  private static Limelight sys_limelight;
  
  private TelescopeTo cmd_telescopeTo;
  
  private double yDist;
  private double extensionDist;
  private double lastDistanceUpdate;

  public AutoTelescope(Telescope sys_telescope, Limelight sys_limelight) {
    this.sys_telescope = sys_telescope;
    this.sys_limelight = sys_limelight;
    
    yDist = Constants.kLimelight.KretroTarget.lowNodeHeight;
    lastDistanceUpdate = System.currentTimeMillis();
    extensionDist = 0;

    addRequirements(sys_telescope);
  }

  public double getTelescopeDist() {
    double xDist = sys_limelight.getRetroDistance();

    if (xDist != 0 && xDist <= kAutoTelescope.maxDist){
      return Math.hypot(xDist + kAutoTelescope.xOffset, yDist + kAutoTelescope.yOffset) - kAutoTelescope.defaultArmLength;
    } else {
      if (xDist > kAutoTelescope.maxDist && kAutoTelescope.debugMode){
        System.out.println("[Retro-Targeting] TARGET OUT OF BOUNDS");
      } else {
        System.out.println("[Retro-Targeting] NO VALID TARGET");
      }
      return 0;
    }
  }

  public void updateDistance() {
    double extensionDist = getTelescopeDist();
    cmd_telescopeTo = new TelescopeTo(sys_telescope, extensionDist);

    if ((System.currentTimeMillis() - lastDistanceUpdate) >= kAutoTelescope.updateInterval && extensionDist != 0){
      //scheduling
      if (kAutoTelescope.enableArm){
        new ProxyCommand(cmd_telescopeTo);
      }
      
      //debug
      if (kAutoTelescope.debugMode){
        System.out.printf("[Extension Distance] %d",extensionDist);
      }

      lastDistanceUpdate = System.currentTimeMillis();
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sys_limelight.turnOn();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    updateDistance();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sys_limelight.turnOff();
    
    if (kAutoTelescope.debugMode){
      System.out.println("COMMAND ENDED");
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
