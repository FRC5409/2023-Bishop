package frc.robot.commands.sequencing;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.kTelescope;
import frc.robot.commands.ArmRotation;
import frc.robot.commands.TelescopeTo;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Telescope;

public class RotateArmGroup extends ParallelCommandGroup{


    public RotateArmGroup(Telescope sys_telescope, ArmPIDSubsystem sys_arm, double armDirection) {
        // Use addRequirements() here to declare subsystem dependencies.
        super(
            Commands.either(new TelescopeTo(sys_telescope, kTelescope.kDestinations.kRetracted).andThen(new TelescopeTo(sys_telescope, sys_telescope.getPrevPos())), Commands.waitSeconds(0), () -> Math.abs(armDirection - sys_arm.getMeasurement()) > 0.30 && sys_telescope.getDistance() >= 3),
            new ArmRotation(sys_arm, armDirection)
        );
    }
}