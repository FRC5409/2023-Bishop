package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kDrivetrain.kDriveteam;
import frc.robot.subsystems.Drivetrain;

public class GearShift extends CommandBase {

    // private final ShuffleboardTab driveTab;
    // private final GenericEntry speedMultiplierEntry;

    private final double forwardSpeed;
    private final double turningSpeed;

    private final Drivetrain m_drivetrain;

    private int timer;

    public GearShift(boolean fast, Drivetrain drivetrain) {
        // Use addRequirements() here to declare subsystem dependencies.
        if (fast) {
            forwardSpeed = kDriveteam.defaultSpeedMultiplier;
            this.turningSpeed = kDriveteam.defaultSpeedMultiplier;
        } else {
            forwardSpeed = kDriveteam.slowSpeed * kDriveteam.defaultSpeedMultiplier;
            this.turningSpeed = kDriveteam.kSlowTurn * kDriveteam.defaultTurningMultiplier;
        }
        m_drivetrain = drivetrain;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_drivetrain.setSpeed(forwardSpeed, turningSpeed);
        m_drivetrain.rampRate(kDriveteam.kChangeRamp);
        timer = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        timer++;
        if (timer == kDriveteam.timerLength) {
            m_drivetrain.rampRate(kDriveteam.rampRate);
        }
        System.out.println(timer);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drivetrain.rampRate(kDriveteam.rampRate);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // return m_drivetrain.getRampRate() == kDrivetrain.rampRate;
        return timer >= kDriveteam.timerLength;
    }

}
