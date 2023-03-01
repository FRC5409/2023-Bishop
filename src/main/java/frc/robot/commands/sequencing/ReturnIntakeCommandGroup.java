package frc.robot.commands.sequencing;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Intake.PivotMove;
import frc.robot.commands.Intake.WristMove;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeRoller;
import frc.robot.subsystems.Intake.IntakeWrist;

public class ReturnIntakeCommandGroup extends ParallelCommandGroup {
    public ReturnIntakeCommandGroup(
            IntakePivot sys_pivot,
            IntakeWrist sys_wrist,
            IntakeRoller sys_roller
    ) {
        super(
            new PivotMove(
                sys_pivot,
                Constants.kIntake.kSetpoints.kPivotSetpoints.kPivotResting
            ),
            new WristMove(
                sys_wrist,
                Constants.kIntake.kSetpoints.kWristSetpoints.kWristResting
            )
        );
    }
}