package frc.robot.commands.sequencing;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.ArmRotation;
import frc.robot.commands.TelescopeTo;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Telescope;

public class RotateArmGroup extends ParallelCommandGroup{


    public RotateArmGroup(Telescope sys_telescope, ArmPIDSubsystem sys_arm, double armDirection) {
        // Use addRequirements() here to declare subsystem dependencies.
        super(
            Commands.either(new TelescopeTo(sys_telescope, 2), null, () -> sys_telescope.getDistance() > 2.5),
            new ArmRotation(sys_arm, armDirection)
        );
    }
}