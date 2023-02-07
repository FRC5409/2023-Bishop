package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Brake;

public class BrakeCommand extends InstantCommand {

    private final Brake sys_brake;
    private double time;

    public BrakeCommand(Brake subsystem, double time) {
        sys_brake = subsystem;
        this.time = time;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(sys_brake);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (!sys_brake.getBrakeState()) {
            sys_brake.brake();
            sys_brake.setBrakeState();
        } else {
            sys_brake.releaseBreak();
            sys_brake.setBrakeState();
        }
        Timer.delay(time);
        sys_brake.stopBrake();
    }
}