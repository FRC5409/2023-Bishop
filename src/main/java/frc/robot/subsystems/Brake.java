package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Brake extends SubsystemBase {

    private final WPI_TalonSRX mot_brake;
    private final SupplyCurrentLimitConfiguration currentLimitConfiguration;

    private boolean brake = false;

    public Brake() {
        mot_brake = new WPI_TalonSRX(Constants.kDrivetrain.kBrake.brakeID);

        currentLimitConfiguration = new SupplyCurrentLimitConfiguration();
        currentLimitConfiguration.enable = true;
        currentLimitConfiguration.currentLimit = 10;

        mot_brake.enableCurrentLimit(true);
        mot_brake.configSupplyCurrentLimit(currentLimitConfiguration);
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

    public void stopBrake() {
        mot_brake.set(ControlMode.PercentOutput, 0);
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