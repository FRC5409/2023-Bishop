package frc.robot.commands.claw;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.NewClaw;

public class DetectGamepiece extends CommandBase {

    private final NewClaw sys_claw;
    private int threshhold;
    private int rumbleTime = -1;
    private boolean rumblingDone;
    private CommandXboxController joystickMain;
    private CommandXboxController joystickSecondary;

    public DetectGamepiece(NewClaw subsystem, int threshhold, CommandXboxController joystickMain, CommandXboxController joystickSecondary) {
        sys_claw = subsystem;
        this.threshhold = threshhold;
        this.joystickMain = joystickMain;
        this.joystickSecondary = joystickSecondary;
        rumblingDone = false;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(sys_claw);
        
    }

    public void rumbleController(double value, int time) {
        rumbleTime = time;
        joystickMain.getHID().setRumble(RumbleType.kBothRumble, value);
        joystickSecondary.getHID().setRumble(RumbleType.kBothRumble, value);
    }

    public void updateRumble() {
        if (rumbleTime == 0) {
            joystickMain.getHID().setRumble(RumbleType.kBothRumble, 0);
            joystickSecondary.getHID().setRumble(RumbleType.kBothRumble, 0);
            rumbleTime = -1;
            rumblingDone = true;
        } else {
            rumbleTime--;
        }
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        rumblingDone = false;
        if (sys_claw.getDistanceToFLeft() < threshhold || sys_claw.getDistanceToFRight() < threshhold) {
            rumbleController(0.5, 40);
        } else {
            rumblingDone = true;
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        updateRumble();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        
        return rumblingDone;
    }

}