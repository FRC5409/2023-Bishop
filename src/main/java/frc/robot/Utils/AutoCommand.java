package frc.robot.Utils;

import java.util.ArrayList;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;

public interface AutoCommand extends Command {

    /**
     * Gets the trajectory of the auto path
     * @return The auto path trajectory
     */
    public Trajectory getTrajectory();

    /**
     * Gets the list of actions that occur during the auto path
     * @return An array list of AutoActions
     */
    public ArrayList<AutoAction> getActions();

    /**
     * Gets the path name of the auto path
     * @return A string of the name of the path
     */
    default String getPathName() {
        return "No Given Name";
    }
    
}
