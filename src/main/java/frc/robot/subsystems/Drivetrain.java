package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorTimeBase;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.kDrivetrain;
import frc.robot.Constants.kGyro;

public class Drivetrain extends SubsystemBase {

    private final WPI_TalonFX mot_leftFrontDrive;
    private final WPI_TalonFX mot_leftCentreDrive;
    private final WPI_TalonFX mot_leftRearDrive;

    private final WPI_TalonFX mot_rightFrontDrive;
    private final WPI_TalonFX mot_rightCentreDrive;
    private final WPI_TalonFX mot_rightRearDrive;

    private final DifferentialDrive m_diffDrive;

    private final WPI_CANCoder enc_leftDrive;
    private final WPI_CANCoder enc_rightDrive;
    private final CANCoderConfiguration enc_config;

    private final WPI_Pigeon2 m_gyro;
    private final DifferentialDriveOdometry m_odometry;


    public Drivetrain() {

        // Instantiate motors and differential drive
        mot_leftFrontDrive = new WPI_TalonFX(kDrivetrain.kMotor.id_leftFrontDrive);
        mot_leftCentreDrive = new WPI_TalonFX(kDrivetrain.kMotor.id_leftCentreDrive);
        mot_leftRearDrive = new WPI_TalonFX(kDrivetrain.kMotor.id_leftRearDrive);

        mot_rightFrontDrive = new WPI_TalonFX(kDrivetrain.kMotor.id_rightFrontDrive);
        mot_rightCentreDrive = new WPI_TalonFX(kDrivetrain.kMotor.id_rightCentreDrive);
        mot_rightRearDrive = new WPI_TalonFX(kDrivetrain.kMotor.id_rightRearDrive);

        configMotors();

        m_diffDrive = new DifferentialDrive(mot_leftRearDrive, mot_rightFrontDrive);

        // Instantiate CANCoders
        enc_leftDrive = new WPI_CANCoder(kDrivetrain.kCANCoder.id_leftEncoder);
        enc_rightDrive = new WPI_CANCoder(kDrivetrain.kCANCoder.id_rightEncoder);

        enc_config = new CANCoderConfiguration();
        enc_config.sensorCoefficient = kDrivetrain.kCANCoder.enc_SensorCoefficient;
        enc_config.unitString = kDrivetrain.kCANCoder.enc_UnitString;
        enc_config.sensorTimeBase = SensorTimeBase.PerSecond;
        enc_leftDrive.configAllSettings(enc_config);
        enc_rightDrive.configAllSettings(enc_config);

        // Gyro and odometry
        m_gyro = new WPI_Pigeon2(kGyro.id_gyro);
        m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d(), getLeftDistance(), getRightDistance());
    }

    /**
     * Config factory default
     * 
     * Set followers
     * 
     * Ramp rate
     */
    private void configMotors() {
        // Reset factory default
        mot_leftFrontDrive.configFactoryDefault();
        mot_leftCentreDrive.configFactoryDefault();
        mot_leftRearDrive.configFactoryDefault();

        mot_rightFrontDrive.configFactoryDefault();
        mot_rightCentreDrive.configFactoryDefault();
        mot_rightRearDrive.configFactoryDefault();

        // Set followers
        mot_leftCentreDrive.follow(mot_leftFrontDrive);
        mot_leftRearDrive.follow(mot_leftFrontDrive);

        mot_rightCentreDrive.follow(mot_rightFrontDrive);
        mot_rightRearDrive.follow(mot_rightFrontDrive);

        rampRate(kDrivetrain.kMotor.rampRate);
    }

    /**
     * Set ramp rate on motors
     * @param seconds time
     */
    public void rampRate(double seconds) {
        mot_leftFrontDrive.configOpenloopRamp(seconds);
        mot_leftCentreDrive.configOpenloopRamp(seconds);
        mot_leftRearDrive.configOpenloopRamp(seconds);

        mot_rightFrontDrive.configOpenloopRamp(seconds);
        mot_rightCentreDrive.configOpenloopRamp(seconds);
        mot_rightRearDrive.configOpenloopRamp(seconds);
    }

    /**
     * Arcade drive
     * @param xSpeed forward speed
     * @param zRotation rotation
     */
    public void arcadeDrive(double xSpeed, double zRotation) {
        m_diffDrive.arcadeDrive(xSpeed, zRotation);
    }

    /**
     * Tank drive voltages, for trajectory
     * @param leftVolts
     * @param rightVolts
     */
    public void tankDriveVoltages(double leftVolts, double rightVolts) {
        mot_leftFrontDrive.setVoltage(leftVolts);
        mot_rightFrontDrive.setVoltage(rightVolts);
        m_diffDrive.feed();
    }

    // CANCoders ----------

    /**
     * Set encoder position to 0
     */
    public void resetEncoders() {
        enc_leftDrive.setPosition(0);
        enc_rightDrive.setPosition(0);
    }

    /**
     * @return left encoder distance in metres
     */
    public double getLeftDistance() {
        return enc_leftDrive.getPosition();
    }

    /**
     * @return right encoder distance in metres
     */
    public double getRightDistance() {
        return enc_rightDrive.getPosition();
    }

    /**
     * @return left encoder velocity in metres per second
     */
    public double getLeftVelocity() {
        return enc_leftDrive.getVelocity();
    }

    /**
     * @return right encoder velocity in metres per second
     */
    public double getRightVelocity() {
        return enc_rightDrive.getVelocity();
    }

    // ----------

    // Gyro and odometry

    /**
     * Reset gyro heading to zero
     */
    public void resetGyro() {
        m_gyro.reset();
    }

    public Rotation2d getRotation2d() {
        return m_gyro.getRotation2d();
    }
    
    public double getHeading() {
        return getRotation2d().getDegrees();
    }

    public double getTurnRate() {
        return m_gyro.getRate();
    }

    public Pose2d getPose2d() {
        return m_odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
    }

    public void resetOdometry() {
        resetEncoders();
        m_odometry.resetPosition(m_gyro.getRotation2d(), getLeftDistance(), getRightDistance(), getPose2d());
    }

    @Override
    public void periodic() {
        // Update odometry
        m_odometry.update(m_gyro.getRotation2d(), enc_leftDrive.getPosition(), enc_rightDrive.getPosition());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}