package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kClaw;

public class Claw extends SubsystemBase {

    private final CANSparkMax clawMot;
    private final SparkMaxPIDController pidController_claw;
    private final RelativeEncoder clawEncoder;

    private final ShuffleboardTab clawTab;
    private final GenericEntry encoderPosEntry, encoderVeloEntry, isStalledEntry, tempEntry;
    // private final GenericEntry kP;

    // private final GenericEntry currentEntry;

    // private double currentOverTime[] = new double[kClaw.currentDataLength];

    // private int currentIndex = 0;

    public Claw() {
        clawMot = new CANSparkMax(kClaw.clawCANID, MotorType.kBrushless);

        pidController_claw = clawMot.getPIDController();

        configMot();

        clawEncoder = clawMot.getEncoder();

        clawTab = Shuffleboard.getTab("Claw");

        // kP = clawTab.add("kP", 0).getEntry();
        // kI = clawTab.add("kI", 0).getEntry();
        // kD = clawTab.add("kD", 0).getEntry();
        // kF = clawTab.add("kF", 0).getEntry();

        // currentEntry = clawTab.add("Current: ", 0).getEntry();
        encoderPosEntry = clawTab.add("Encoder: ", getEncoderPosition()).getEntry();
        encoderVeloEntry = clawTab.add("Encoder Velo: ", getEncoderVelocity()).getEntry();
        isStalledEntry = clawTab.add("Is Stalled: ", isStalled()).getEntry();
        tempEntry = clawTab.add("Motor Temp: ", getMotorTempature()).getEntry();        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        // currentOverTime[currentIndex] = clawMot.getOutputCurrent();
        // currentIndex++;
        // currentIndex %= currentOverTime.length;


        // lastEncoder = getEncoderPosition();
        encoderPosEntry.setDouble(getEncoderPosition());
        encoderVeloEntry.setDouble(getEncoderVelocity());
        isStalledEntry.setBoolean(isStalled());
        tempEntry.setDouble(getMotorTempature());
        // currentEntry.setDouble(getAverageCurrent());
        // setPIDF(kP.getDouble(0), 0, 0, 0);
        
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

    public void configMot() {
        clawMot.restoreFactoryDefaults();

        clawMot.setIdleMode(IdleMode.kBrake);

        clawMot.setSmartCurrentLimit(kClaw.currentLimit);

        setPIDF(kClaw.kP, kClaw.kI, kClaw.kD, kClaw.kF);

        clawMot.burnFlash();
    }

    public void setPIDF(double p, double i, double d, double f) {
        pidController_claw.setP(p);
        pidController_claw.setI(i);
        pidController_claw.setD(d);
        pidController_claw.setFF(f);
    }

    public void stopMot() {
        clawMot.set(0);
    }

    public void openClaw() {
        pidController_claw.setReference(kClaw.openPosition, ControlType.kPosition);
    }

    public void closeClaw() {
        pidController_claw.setReference(kClaw.closePosition, ControlType.kPosition);
    }

    public void clawGoTo(double pos) {
        pidController_claw.setReference(pos, ControlType.kPosition);
    }

    public double getEncoderPosition() {
        return clawEncoder.getPosition();
    }

    public double getEncoderVelocity() {
        return Math.abs(clawEncoder.getVelocity());
    }

    public void zeroEncoder() {
        clawEncoder.setPosition(0);
    }

    public void spinAt(double speed) {
        clawMot.set(speed);
    }

    // public double getAverageCurrent() {
    //     double total = 0;
    //     for (int i = 0; i < currentOverTime.length; i++) {
    //         if (currentOverTime[i] == 0) {
    //             total = -1;
    //             break;
    //         } else {
    //             total += currentOverTime[i];
    //         }
    //     }
    //     return total / currentOverTime.length;
    // }

    public boolean isStalled() {
        double velo = getEncoderVelocity();
        if (clawMot.get() != 0) {
            if (velo <= 0.1) {
                //motor stalled
                return true;
            } else {
                //motor not stalled
                return false;
            }

        } else {
            //motor not running
            return false;
        }
    }

    public double getMotorTempature() {
        return clawMot.getMotorTemperature();
    }

    public void setIdleMode(IdleMode idle) {
        clawMot.setIdleMode(idle);
    }

}