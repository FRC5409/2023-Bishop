// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
 
public class RollerMove extends CommandBase {

  private final Intake intake;
  private double voltage;
  private boolean inverted;

  public RollerMove(Intake subsystem, double voltage, boolean inverted) {
      intake = subsystem;
      this.voltage = voltage;
      this.inverted = inverted;

      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(intake);   
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (inverted)
    {
      intake.setRollerDirection(true);
    }
    else
    {
      intake.setRollerDirection(false);
    }

    intake.rollerControl(voltage);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.rollerControl(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
