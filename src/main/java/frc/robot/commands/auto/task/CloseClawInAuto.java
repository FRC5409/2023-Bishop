package frc.robot.commands.auto.task;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.kCANdle.kColors;
import frc.robot.Util.Color;
import frc.robot.Constants.kClaw;
import frc.robot.commands.LEDs.BlinkLEDs;
import frc.robot.commands.claw.ClawMovement;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Claw;

public class CloseClawInAuto extends SequentialCommandGroup {

    public CloseClawInAuto(Claw sys_claw, LED sys_LEDs) {

        Command cmdLED = new BlinkLEDs(
                sys_LEDs,
                kColors.idle,
                Color.kPureGreen,
                kColors.blinkSpeed, kColors.blinkTime
            );

        addCommands(
            new ParallelDeadlineGroup(
                    new ClawMovement(sys_claw, kClaw.coneClosePosition).withTimeout(0.8),
                    cmdLED
                )
        );
    }
    
}
