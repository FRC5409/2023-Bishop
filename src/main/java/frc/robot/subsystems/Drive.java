package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kDrivetrain.kMotor;
import frc.robot.Constants.kDrivetrain.kDriveteam;

public class Drive extends SubsystemBase {
    // Motors
    /**
     * Front motors are leaders
     * Back motors are followers
     */
    private final WPI_TalonFX leftFrontMotor;
    private final WPI_TalonFX leftBackMotor;
    private final WPI_TalonFX rightFrontMotor;
    private final WPI_TalonFX rightBackMotor;

    private final DifferentialDrive diffDrive;

    private static Drive instance = null;

    private Drive() {
        leftFrontMotor = new WPI_TalonFX(kMotor.id_leftFrontDrive);
        leftBackMotor = new WPI_TalonFX(kMotor.id_leftRearDrive);
        rightFrontMotor = new WPI_TalonFX(kMotor.id_rightFrontDrive);
        rightBackMotor = new WPI_TalonFX(kMotor.id_rightRearDrive);

        diffDrive = new DifferentialDrive(rightFrontMotor, leftFrontMotor);

        initMotor(leftBackMotor, null, true);
        initMotor(rightBackMotor, null, false);
        initMotor(leftFrontMotor, leftBackMotor, true);
        initMotor(rightFrontMotor, rightBackMotor, false);
    }

    private void initMotor(WPI_TalonFX leader, WPI_TalonFX follower, boolean invert) {
        leader.configFactoryDefault();

        SupplyCurrentLimitConfiguration _config = new SupplyCurrentLimitConfiguration();
        _config.currentLimit = kMotor.currentLimit;
        _config.enable = true;
        leader.configSupplyCurrentLimit(_config);

        leader.setInverted(invert);

        leader.configOpenloopRamp(kDriveteam.rampRate);

        if (follower != null) follower.follow(leader);
    }

    // Get subsystem
    public static Drive getInstance() {
        if (instance == null) instance = new Drive();

        return instance;
    }

    public void arcadeDrive(double xSpeed, double zRot) {
        diffDrive.arcadeDrive(xSpeed, zRot);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // TODO: shuffleboard temp for all motors later
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}
