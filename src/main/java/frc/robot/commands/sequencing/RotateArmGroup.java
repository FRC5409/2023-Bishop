package frc.robot.commands.sequencing;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.kClaw;
import frc.robot.Constants.kTelescope;
import frc.robot.commands.ArmRotation;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.TelescopeTo;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Telescope;

public class RotateArmGroup extends ParallelDeadlineGroup{


    public RotateArmGroup(
        Telescope sys_telescope, 
        ArmPIDSubsystem sys_arm, 
        Claw sys_claw, 
        double armDirection
    ) {


        // Use addRequirements() here to declare subsystem dependencies.
        super(
            new ArmRotation(sys_arm, armDirection),
            new ConditionalCommand(
                new TelescopeTo(
                    sys_telescope, 
                    kTelescope.kDestinations.kRetracted
                ), 
                new WaitCommand(0), 
                () -> Math.abs(armDirection - sys_arm.getMeasurement()) > 0.10 && sys_telescope.getDistance() >= 1
            ),
            new ConditionalCommand(
                new CloseClaw(sys_claw, kClaw.coneClosePosition), 
                new WaitCommand(0), 
                () -> sys_claw.getPrevPos() == kClaw.coneClosePosition
            ),
            new ConditionalCommand(
                new CloseClaw(sys_claw, kClaw.cubeClosePosition), 
                new WaitCommand(0), 
                () -> sys_claw.getPrevPos() == kClaw.cubeClosePosition
            )
        );
    }
}