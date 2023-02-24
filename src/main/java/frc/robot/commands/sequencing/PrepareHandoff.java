package frc.robot.commands.sequencing;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.Intake.PivotMove;
import frc.robot.commands.Intake.RollerMove;
import frc.robot.commands.Intake.WristMove;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeRoller;
import frc.robot.subsystems.Intake.IntakeWrist;
import frc.robot.subsystems.Telescope;

public class PrepareHandoff extends SequentialCommandGroup {

    public PrepareHandoff(
        Telescope sys_telescope,
        ArmPIDSubsystem sys_arm,
        IntakePivot sys_pivot,
        IntakeWrist sys_wrist,
        IntakeRoller sys_roller
    ) {
        super(
            Commands.either(
                new ArmToPos(
                    sys_telescope,
                    sys_arm,
                    Constants.kArmSubsystem.kSetpoints.kback,
                    Constants.kTelescope.kDestinations.kRetracted),
                new WaitCommand(0),
                () -> sys_arm.getPrevPos() != Constants.kArmSubsystem.kSetpoints.kToHandoff
            ),
            new PivotMove(sys_pivot, Constants.kIntake.kSetpoints.kPivotSetpoints.kPivotExtended),
            new WristMove(sys_wrist, Constants.kIntake.kSetpoints.kWristSetpoints.kWristPickup),
            new RollerMove(sys_roller, 3.6)
        );
    }
}