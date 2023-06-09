package frc.robot.commands.LEDs.Legacy;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.kCANdle.kColors;
import frc.robot.Constants.kCANdle.kConfig;
import frc.robot.Util.Color;
import frc.robot.subsystems.LED;

public class ChargedUpAnimation extends CommandBase {

    private final LED m_LEDs;

    private final Color off = kColors.black;
    private final Color yellow = kColors.idle;

    private int maxCharge = 0;
    private int currentChargeLocation = 0;

    public ChargedUpAnimation(LED LEDs) {
        m_LEDs = LEDs;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_LEDs);
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        maxCharge = 0;
        currentChargeLocation = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //No clue how it works but it works
        //Also looks ugly

        for (int i = 0; i < 2; i++) {
            m_LEDs.setLEDColorAt((kConfig.LEDOutter * 2 + kConfig.LEDInnerRight + 9) - currentChargeLocation, off);
            m_LEDs.setLEDColorAt(kConfig.LEDOutter * 2 + kConfig.LEDInnerRight + 2 + currentChargeLocation, off);

            if ((kConfig.LEDOutter + 9) - currentChargeLocation > 8) {
                m_LEDs.setLEDColorAt((kConfig.LEDOutter + 6) - currentChargeLocation, off);
                m_LEDs.setLEDColorAt((kConfig.LEDOutter * 2 + 9) - currentChargeLocation, off);
            }

            currentChargeLocation++;
            m_LEDs.setLEDColorAt((kConfig.LEDOutter * 2 + kConfig.LEDInnerRight + 9) - currentChargeLocation, yellow);
            m_LEDs.setLEDColorAt(kConfig.LEDOutter * 2 + kConfig.LEDInnerRight + 2 + currentChargeLocation, yellow);

            if ((kConfig.LEDOutter + 9) - currentChargeLocation > 8) {
                m_LEDs.setLEDColorAt((kConfig.LEDOutter + 6) - currentChargeLocation, yellow);
                m_LEDs.setLEDColorAt((kConfig.LEDOutter * 2 + 9) - currentChargeLocation, yellow);
            }

            if (currentChargeLocation == kConfig.LEDInnerLeft - maxCharge + 1) {
                currentChargeLocation = 1;
                maxCharge++;
            } else if (currentChargeLocation > kConfig.LEDInnerLeft) {
                maxCharge = 0;
                currentChargeLocation = 1;
                m_LEDs.setColor(off);
            }

        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
