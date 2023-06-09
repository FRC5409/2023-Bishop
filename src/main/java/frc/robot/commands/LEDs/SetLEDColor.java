package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class SetLEDColor extends InstantCommand {

    private final LED LEDs;
    private final Color color;

    public SetLEDColor(LED LEDs, Color color) {

        this.LEDs = LEDs;
        this.color = color;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(LEDs);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        LEDs.setColor(color);
    }

}
