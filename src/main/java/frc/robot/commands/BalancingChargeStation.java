package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.kBalancing;
import frc.robot.subsystems.Drivetrain;

public class BalancingChargeStation extends PIDCommand {

    private final Drivetrain m_drivetrain;
    private final ShuffleboardTab sb_balancingTab;
    private final GenericEntry nt_kP;
    private final GenericEntry nt_kI;
    private final GenericEntry nt_kD;

    public BalancingChargeStation(Drivetrain drivetrain) {
        super(
            new PIDController(0, 0, 0),
            drivetrain::getPitch,
            kBalancing.targetPitch,
            output -> drivetrain.arcadeDrive(output, 0),
            drivetrain
        );

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drivetrain);

        m_drivetrain = drivetrain;

        sb_balancingTab = Shuffleboard.getTab("Balancing");
        nt_kP = sb_balancingTab.add("kP", 0).getEntry();
        nt_kI = sb_balancingTab.add("kI", 0).getEntry();
        nt_kD = sb_balancingTab.add("kD", 0).getEntry();

        getController().setPID(nt_kP.getDouble(0),
                                nt_kI.getDouble(0),
                                nt_kD.getDouble(0));

        getController().enableContinuousInput(-kBalancing.maxAngle, kBalancing.maxAngle);
        getController().setTolerance(kBalancing.angleTolerance);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // Update PID values
        if (nt_kP.getDouble(0) != getController().getP()) {
            getController().setP(nt_kP.getDouble(0));
        }

        if (nt_kI.getDouble(0) != getController().getI()) {
            getController().setI(nt_kI.getDouble(0));
        }

        if (nt_kD.getDouble(0) != getController().getD()) {
            getController().setD(nt_kD.getDouble(0));
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }

}