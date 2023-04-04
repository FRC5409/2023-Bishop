package frc.robot.commands.disabled;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.ConeTipper;
import frc.robot.subsystems.NewClaw;
import frc.robot.subsystems.ConeTipper;

public class DisablePIDSubsystems extends InstantCommand {

    private final ArmPIDSubsystem sys_arm;

    private final ConeTipper sys_tipper;

    private final NewClaw sys_claw;

    public DisablePIDSubsystems(ConeTipper sys_tipper, ArmPIDSubsystem sys_arm, NewClaw sys_claw) {
        this.sys_arm = sys_arm;
        this.sys_tipper = sys_tipper;
        this.sys_claw = sys_claw;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.sys_arm, this.sys_tipper);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        sys_arm.disable();
        sys_tipper.disable();
        sys_claw.disable();
    }


    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

}