package frc.robot.commands; 

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.CommandBase; 
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Drivetrain;

public class DefaultDrive extends CommandBase {

    private final Drivetrain drivetrain;
    private final CommandXboxController m_joystick;

    double forwardSpeed;
    double rearSpeed;
    double turnVal;

    public DefaultDrive(Drivetrain p_drivetrain, CommandXboxController joystick) {

        drivetrain = p_drivetrain;
        m_joystick = joystick;

        addRequirements(drivetrain);
        
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {

        // LT forward, RT rear, LSB turnval
        forwardSpeed = m_joystick.getLeftTriggerAxis();
        rearSpeed = m_joystick.getRightTriggerAxis();
        turnVal = -m_joystick.getLeftX();

        // SmartDashboard.putNumber("Forward Speed", forwardSpeed);
        // SmartDashboard.putNumber("Reverse Speed", Math.round(rearSpeed * 100));
        // SmartDashboard.putNumber("Turn Amount", Math.round(turnVal * 100));

        drivetrain.arcadeDrive(forwardSpeed - rearSpeed, turnVal);

    }

    @Override
    public void end(boolean interrupted) {}


    @Override
    public boolean isFinished() {
        return false;
    }
}