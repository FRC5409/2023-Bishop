package frc.robot.commands.sequencing;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Constants;

import frc.robot.commands.Intake.PivotMove;
import frc.robot.commands.Intake.RollerMove;
import frc.robot.commands.Intake.WristMove;

import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Telescope;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeRoller;
import frc.robot.subsystems.Intake.IntakeWrist;

public class PrepareHandoffCommandGroup extends SequentialCommandGroup {
    public PrepareHandoffCommandGroup(
            Telescope sys_telescope,
            ArmPIDSubsystem sys_arm,
            Claw sys_claw,
            IntakePivot sys_pivot,
            IntakeWrist sys_wrist,
            IntakeRoller sys_roller
    ) {
        super(
            new ArmToPos(
                sys_telescope,
                sys_arm,
                sys_claw,
                Constants.kArmSubsystem.kSetpoints.kIdling,
                Constants.kTelescope.kDestinations.kRetracted
            ),
            new ParallelCommandGroup(
                new PivotMove(sys_pivot, Constants.kIntake.kSetpoints.kPivotSetpoints.kPivotExtended),
                new WristMove(sys_wrist, Constants.kIntake.kSetpoints.kWristSetpoints.kWristPickup),
                new RollerMove(sys_roller, 3.6)
            )
        );
    }
}