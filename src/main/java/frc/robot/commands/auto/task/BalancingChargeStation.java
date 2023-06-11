package frc.robot.commands.auto.task;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.kBalancing;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Drivetrain;

public class BalancingChargeStation extends PIDCommand {

    private final LED m_LEDs;

    private boolean isBalanced = false;

    private static double kP = kBalancing.kP;
    private static double kI = kBalancing.kI;
    private static double kD = kBalancing.kD;

    public BalancingChargeStation(Drivetrain drivetrain, LED LEDs) {
        super(
            new PIDController(kP, kI, kD),
            drivetrain::getPitch,
            kBalancing.targetPitch,
            output -> drivetrain.arcadeDrive(output, 0),
            drivetrain
        );

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drivetrain, LEDs);

        m_LEDs = LEDs;

        getController().enableContinuousInput(-kBalancing.maxAngle, kBalancing.maxAngle);
        getController().setTolerance(kBalancing.angleTolerance);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_LEDs.setColor(Color.kPureRed);
    }

    @Override
    public void execute() {
        super.execute();
        if (getController().atSetpoint()) {
            if (!isBalanced) {
                m_LEDs.setColor(Color.kPureGreen);
                isBalanced = true;
            }
        } else {
            if (isBalanced) {
                m_LEDs.setColor(Color.kPureRed);
                isBalanced = false;
            }
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
    // // Returns true when the command should end.
    // @Override
    // public boolean isFinished() {
    //     return getController().atSetpoint() && Math.abs(m_drivetrain.getLeftVelocity()) < 0.01;
    // }

}