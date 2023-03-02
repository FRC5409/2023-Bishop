package frc.robot.commands.sequencing;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.kArmSubsystem;
import frc.robot.Constants.kTelescope;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.Intake.PivotMove;
import frc.robot.commands.Intake.RollerMove;
import frc.robot.commands.Intake.WristMove;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeRoller;
import frc.robot.subsystems.Intake.IntakeWrist;
import frc.robot.subsystems.Telescope;

public class ExcecuteHandoffCommandGroup extends SequentialCommandGroup {
    public ExcecuteHandoffCommandGroup(
        Telescope sys_telescope,
        ArmPIDSubsystem sys_arm,
        Claw sys_claw,
        IntakePivot sys_pivot,
        IntakeWrist sys_wrist,
        IntakeRoller sys_roller
    ) {
        super(
            new ParallelCommandGroup(
                new ArmToPos(
                    sys_telescope,
                    sys_arm, 
                    sys_claw,
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
            new WaitCommand(3),
            new ParallelCommandGroup(
                new ArmToPos(
                    sys_telescope,
                    sys_arm,
                    sys_claw,
                    Constants.kArmSubsystem.kSetpoints.kToHandoff,
                    Constants.kTelescope.kDestinations.kHandoff
                ),
                new OpenClaw(
                    sys_claw,
                    Constants.kClaw.fullyOpenPosition,
                    false
                )
            ),
            new WaitCommand(3),
            new ParallelCommandGroup(
                new RollerMove(sys_roller,
                -1.0
                )
                .deadlineWith(
                    new WaitCommand(2)
                ),
                new CloseClaw(
                    sys_claw,
                    Constants.kClaw.coneClosePosition
                )
            ),
            new WaitCommand(3),
            new ArmToPos(
                sys_telescope,
                sys_arm,
                sys_claw,
                Constants.kArmSubsystem.kSetpoints.kIdling,
                Constants.kTelescope.kDestinations.kRetracted
            ),
            new WaitCommand(3),
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