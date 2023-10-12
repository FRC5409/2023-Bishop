package frc.robot.commands;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.kOperator;;
import frc.robot.subsystems.Drive;

/**
 * @author 
 */
public class TestDrive extends CommandBase {
    private final CommandXboxController controller;

    private final Drive drive;

    public TestDrive(Drive drive) {
        this.drive = drive;
        this.controller = new CommandXboxController(kOperator.port_joystickMain);

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.drive);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double xSpeed = controller.getRightTriggerAxis() - controller.getLeftTriggerAxis();
    }
}
