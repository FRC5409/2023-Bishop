package frc.robot.commands.Intake.Sequence;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kIntake.kSetpoints.kPivotSetpoints;
import frc.robot.subsystems.Intake;

public class PivotInward extends CommandBase
{
  private final Intake sys_intake;
  private final double setpoint;

  public PivotInward(Intake subsystem)
  {
    sys_intake = subsystem;
    setpoint = kPivotSetpoints.kPivotDefault;

    addRequirements(sys_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    sys_intake.pivotToSetpoint(setpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    if (Math.abs(setpoint - sys_intake.getPivotPos()) < 0.5)
    {
      return true;
    }
        
    return false;
  }
}