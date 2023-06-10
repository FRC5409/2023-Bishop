package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.MathUtil;


/**
 * Creates a RampRate the runs on the roborio.
 * This lets you configure the ramprates without delay
 * @author Alexander Szura
 */
public class RoborioRampRate {

    private double rampRate;

    /**
     * Creates a new ramp rate class
     */
    public RoborioRampRate(double ramprate) {
        this.rampRate = ramprate;
    }

    /**
     * Sets the current applied ramp rate to the new ramp rate
     */
    public void setRampRate(double rate) {
        rampRate = rate;
    }

    /**
     * Calculates and applies to the motor with the applied ramp rate
     */
    public void calculate(CANSparkMax motor, double desiredSpeed) {
        motor.set(calculate(desiredSpeed, motor.get()));
    }

    /**
     * Calculates and applies to the motor with the applied ramp rate
     */
    public void calculate(TalonFX motor, double desiredSpeed) {
        motor.set(ControlMode.PercentOutput, calculate(desiredSpeed, motor.getMotorOutputPercent()));
    }

    /**
     * Calculates and applies to the motor with the applied ramp rate
     */
    public void calculate(TalonSRX motor, double desiredSpeed) {
        motor.set(ControlMode.PercentOutput, calculate(desiredSpeed, motor.getMotorOutputPercent()));
    }

    /**
     * Calculates the ramp rate with the desired speed and the initial speed
     * @return Returns the rampate % output
     */
    public double calculate(double desiredSpeed, double initialSpeed) {
        return MathUtil.applyDeadband((desiredSpeed - initialSpeed) / rampRate, 1);
    }
}
