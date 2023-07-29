package frc.robot.Utils;

import java.util.ArrayList;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;

public interface AutoCommand extends Command {

    public Trajectory getTrajectory();

    public ArrayList<AutoAction> getActions();
    
}
