package frc.robot.commands.sequencing;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.kArmSubsystem;
import frc.robot.Constants.kClaw;
import frc.robot.Constants.kTelescope;
import frc.robot.commands.ClawMovement;
import frc.robot.commands.Intake.PivotMove;
import frc.robot.commands.Intake.RollerMove;
import frc.robot.commands.Intake.WristMove;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.NewClaw;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeRoller;
import frc.robot.subsystems.Intake.IntakeWrist;

public class ExcecuteHandoff extends SequentialCommandGroup {
    public ExcecuteHandoff (
        Telescope sys_telescope,
        ArmPIDSubsystem sys_arm,
        NewClaw sys_claw,
        IntakePivot sys_pivot,
        IntakeWrist sys_wrist,
        IntakeRoller sys_roller
    ) {
        super(
            new ParallelCommandGroup(
                new ArmToPos(
                    sys_telescope,
                    sys_arm, 
                    kArmSubsystem.kSetpoints.kIdling,
                    kTelescope.kDestinations.kRetracted)
            ),
            new ParallelCommandGroup(
                new PivotMove(
                    sys_pivot,
                    Constants.kIntake.kSetpoints.kPivotSetpoints.kHandoff
                ),
                new WristMove(
                    sys_wrist,
                    Constants.kIntake.kSetpoints.kWristSetpoints.kWristHandoff
                )
            ),
            new ParallelCommandGroup(
                new ArmToPos(
                    sys_telescope,
                    sys_arm,
                    Constants.kArmSubsystem.kSetpoints.kToHandoff,
                    Constants.kTelescope.kDestinations.kHandoff
                ),
                new ClawMovement(
                    sys_claw, 
                    kClaw.openPosition
                )
            ),
            new ParallelCommandGroup(
                new RollerMove(sys_roller,
                -1.0
                )
                .deadlineWith(
                    new WaitCommand(2)
                ),
                new ClawMovement(
                    sys_claw, 
                    kClaw.coneClosePosition
                )
            ),
            new ArmToPos(
                sys_telescope,
                sys_arm,
                Constants.kArmSubsystem.kSetpoints.kIdling,
                Constants.kTelescope.kDestinations.kRetracted
            ),
            new ParallelCommandGroup(
                new PivotMove(
                    sys_pivot,
                    Constants.kIntake.kSetpoints.kPivotSetpoints.kPivotResting
                ),
                new WristMove(
                    sys_wrist,
                    Constants.kIntake.kSetpoints.kWristSetpoints.kWristResting
                )
            )
        );
    }
}