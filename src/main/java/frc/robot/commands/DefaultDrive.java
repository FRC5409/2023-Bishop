package frc.robot.commands; 

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.CommandBase; 
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Drivetrain;

public class DefaultDrive extends CommandBase {

    private final Drivetrain drivetrain;
    private final ArrayList<CommandXboxController> m_joysticks;

    double forwardSpeed;
    double rearSpeed;
    double turnVal;

    public DefaultDrive(Drivetrain p_drivetrain, ArrayList<CommandXboxController> joysticks) {

        drivetrain = p_drivetrain;
        m_joysticks = joysticks;

        addRequirements(drivetrain);
        
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {

        // LT forward, RT rear, LSB turnval
        forwardSpeed = m_joysticks.get(drivetrain.getCurrentJoystick()).getLeftTriggerAxis();
        rearSpeed = m_joysticks.get(drivetrain.getCurrentJoystick()).getRightTriggerAxis();
        turnVal = m_joysticks.get(drivetrain.getCurrentJoystick()).getLeftX();

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