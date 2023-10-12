package frc.robot.commands.drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ToggleBreakModeWhileHeld extends CommandBase {

    private final Drivetrain m_drive;
    private final boolean coast;

    public ToggleBreakModeWhileHeld(Drivetrain drive, boolean coast) {
        m_drive = drive;
        this.coast = coast;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (coast) m_drive.setNeutralMode(NeutralMode.Coast);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        if (coast) m_drive.setNeutralMode(NeutralMode.Brake);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
