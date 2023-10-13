package frc.robot.commands;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.kOperator;
import frc.robot.subsystems.Drive;

/**
 * @author Add name here
 */
public class TestDrive extends CommandBase {
    private final CommandXboxController controller;

    private final Drive drive;

    public TestDrive(Drive drive) {
        this.drive = drive;
        // Instead of passing in Drive, we can do this.drive = Drive.getInstance(); if you wanted
        this.controller = new CommandXboxController(kOperator.port_joystickMain);
        // Don't need to create an already existing CommandXboxController, pass it in the command through robotContainer.java
        // Could also look into creating a subsystem for controllers and using a singleton method to get them anywhere

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.drive);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double xSpeed = controller.getRightTriggerAxis() - controller.getLeftTriggerAxis();
        double zRot = controller.getLeftX();
        drive.arcadeDrive(xSpeed, zRot);
    }
}
