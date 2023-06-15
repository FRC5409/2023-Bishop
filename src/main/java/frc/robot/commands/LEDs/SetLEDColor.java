package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class SetLEDColor extends CommandBase {

    private final LED LEDs;
    private       Color color;

    private boolean getColor;

    public SetLEDColor(LED LEDs, Color color) {
        this.LEDs = LEDs;
        this.color = color;
        getColor = false;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(LEDs);
        
    }

    public SetLEDColor(LED LEDs) {
        this.LEDs = LEDs;
        getColor = true;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (getColor) {
            color = LEDs.getLastColor();
        }
        
        LEDs.setColor(color);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
