package frc.robot.subsystems.test;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kArmTest;

// logan's arm subsystem that is an arm by logan the arm developer
public class ArmTest extends SubsystemBase {
    private final CANSparkMax motor1;
    private final CANSparkMax motor2;
    private final SparkMaxPIDController controller;

    private final RelativeEncoder encoder;

    private final ShuffleboardTab sb_tab;
    private final GenericEntry sb_kp;

    private static ArmTest instance = null;

    public ArmTest() {
        motor1 = new CANSparkMax(kArmTest.kCAN1, MotorType.kBrushless);
        motor2 = new CANSparkMax(kArmTest.kCAN2, MotorType.kBrushless);

        initMotor(motor1);
        initMotor(motor2);

        motor2.follow(motor1);

        encoder = motor1.getEncoder();
        zeroEncoder();
        encoder.setPositionConversionFactor(kArmTest.kEncoderCoefficient);

        controller = motor1.getPIDController();

        motor1.burnFlash();
        motor2.burnFlash();

        sb_tab = Shuffleboard.getTab("Arm");
        sb_kp = sb_tab.add("P", controller.getP()).getEntry();

        setPIDF(sb_kp.getDouble(0), kArmTest.kI, kArmTest.kD, kArmTest.kF);
    }

    // Make sure to call CANSparkMax#burnMoter, this method does not do that
    private void initMotor(CANSparkMax motor) {
        motor.restoreFactoryDefaults();
        motor.setSmartCurrentLimit(30);
    }

    public void zeroEncoder() {
        encoder.setPosition(0);
    }

    /**
     * Sets controller PIDF to values in Constants.kArmTest
     */
    public void setPIDF(double p, double i, double d, double ff) {
        controller.setP(p);
        controller.setI(i);
        controller.setD(d);
        controller.setFF(ff);
    }

    public void rotateTo(double setPoint) {
        controller.setReference(setPoint, ControlType.kPosition);
    }
    
    public double getAngle() {
        return encoder.getPosition();
    }

    // Get subsystem
    public static ArmTest getInstance() {
        if (instance == null) instance = new ArmTest();

        return instance;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        sb_kp.setDouble(controller.getP());
        setPIDF(sb_kp.getDouble(0), kArmTest.kI, kArmTest.kD, kArmTest.kF);
    }
}