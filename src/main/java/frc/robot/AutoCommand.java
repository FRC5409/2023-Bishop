package frc.robot;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;

public interface AutoCommand extends Command {

    default Trajectory getTrajectory() {
        return new Trajectory();
    }
    
}
