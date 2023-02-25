package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RumbleJoystick extends InstantCommand {
    private final CommandXboxController m_joystick;
    private final double m_time;
    private final double m_intensity;
    private double m_startTime;

    public RumbleJoystick(CommandXboxController joystick, double time, double intensity) {
        m_intensity = intensity;
        m_joystick = joystick;
        m_time = time;
    }

    @Override
    public void initialize() {
        m_startTime = System.currentTimeMillis();
        System.out.println("Rumble has started");
        m_joystick.getHID().setRumble(null, m_intensity);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Rumble has stopped");
        m_joystick.getHID().setRumble(null, 0);
    }

    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - m_startTime) > m_time;
    }
}