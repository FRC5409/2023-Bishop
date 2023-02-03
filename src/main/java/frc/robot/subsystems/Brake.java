package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Brake extends SubsystemBase {

    private final WPI_TalonSRX mot_brake;

    private boolean brake = false;

    public Brake() {
        mot_brake = new WPI_TalonSRX(Constants.kDrivetrain.kBrake.brakeID);
    }

    public void setBrakeState() {
        brake = !brake;
    }

    public boolean getBrakeState() {
        return brake;
    }

    public void brake() {
        mot_brake.set(ControlMode.PercentOutput, 0.15);
    }
    
    public void releaseBreak() {
        mot_brake.set(ControlMode.PercentOutput, -0.15);

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}