package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kCANdle.*;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class DisabledAnimation extends CommandBase {

    private final LED m_LEDs;
    private final Color offColor;

    private Alliance currentAlliance;

    private double animationTime = 0;
    private int timer = 0;

    public DisabledAnimation(LED LEDs, Color offColor) {
        m_LEDs = LEDs;
        this.offColor = offColor;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_LEDs);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer = 0;
        animationTime = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        timer++;
        
        Color color;

        currentAlliance = DriverStation.getAlliance();

        if (!DriverStation.isDSAttached()) {
            color = Color.kBlack;
        } else if (currentAlliance == Alliance.Red) {
            color = Color.kPureRed;
        } else if (currentAlliance == Alliance.Blue) {
            color = Color.kPureBlue;
        } else {
            color = Color.kWhite;
        }

        animationTime = Math.sin(timer * kColors.sinFrequency) * kColors.sinFrequencySpeed;
    
        if (Math.abs(animationTime) >= 19.5) {
            timer += 4;
        }
    
        animationTime = Math.floor(animationTime);
    
        for (int i = -kColors.LEDSinCount * 2; i < kConfig.LEDCount; i += kColors.LEDSinCount) {
            int index = (int) (i + animationTime);
            if (Math.abs(i % (kColors.LEDSinCount * 2)) == 0) {
                m_LEDs.setLEDColorAtForWithMinAndMax(index, kColors.LEDSinCount, 7, kConfig.LEDCount - kConfig.LEDInnerLeft, color);
            } else {
                m_LEDs.setLEDColorAtForWithMinAndMax(index, kColors.LEDSinCount, 7, kConfig.LEDCount - kConfig.LEDInnerLeft, offColor);
            }
        }
    
        animationTime *= -1;
    
        for (int i = kConfig.LEDCount - kConfig.LEDInnerLeft - kColors.LEDSinCount * 4; i < kConfig.LEDCount + kColors.LEDSinCount * 2; i += kColors.LEDSinCount) {
            int index = (int) (i + animationTime);
            if (Math.abs(i % (kColors.LEDSinCount * 2)) <= kColors.LEDSinCount) {
                m_LEDs.setLEDColorAtForWithMinAndMax(index, kColors.LEDSinCount, kConfig.LEDCount - kConfig.LEDInnerLeft - 1, kConfig.LEDCount, offColor);
            } else {
                m_LEDs.setLEDColorAtForWithMinAndMax(index, kColors.LEDSinCount, kConfig.LEDCount - kConfig.LEDInnerLeft - 1, kConfig.LEDCount, color);
            }

        }

    }

}
