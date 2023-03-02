// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.kLimelight;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class ConeNodeAim extends CommandBase {

    private final Limelight sys_limelight;
    private final Drivetrain sys_drivetrain;
    private final CommandXboxController m_joystick;

    double forwardSpeed, dirInRad, turnRate;

    /** Creates a new TargetAim. */
    public ConeNodeAim(Limelight limelight, Drivetrain drivetrain, CommandXboxController joystick) {

        sys_limelight = limelight;
        sys_drivetrain = drivetrain;
        m_joystick = joystick;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(sys_drivetrain, sys_limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        sys_limelight.turnOn();
        sys_limelight.setData("pipeline", 1);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        forwardSpeed = m_joystick.getRightTriggerAxis() - m_joystick.getLeftTriggerAxis();

        if (!sys_limelight.isVisible()) {
            dirInRad = sys_limelight.getTurningDir() * (Math.PI / 180); // dir on the controller converted to radians
            sys_drivetrain.arcadeDrive(forwardSpeed, m_joystick.getLeftX()); // Lets the driver drive around
        } else {
            dirInRad = sys_limelight.getXOffset() * (Math.PI / 180);
            sys_drivetrain.arcadeDrive(forwardSpeed, (-turnRate));
        }

        if (dirInRad != 0) {
            turnRate = (Math.pow(Math.E, (Math.abs(dirInRad))) - 1);
            if (turnRate < kLimelight.KretroTargetFF) {
                turnRate = kLimelight.KretroTargetFF;
            }
            turnRate *= ((Math.abs(dirInRad) / dirInRad));
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        sys_drivetrain.arcadeDrive(0, 0);
        sys_limelight.turnOff();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (Math.abs(sys_limelight.getXOffset()) <= kLimelight.KretroTargetTolerance
                && sys_limelight.isVisible());
    }
}