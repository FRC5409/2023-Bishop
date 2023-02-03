// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCone extends CommandBase {
	/** Creates a new intakeBall */

	private final Intake m_intake;

	public IntakeCone(Intake intake) {

		m_intake = intake;

		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(m_intake);
	}

  //intake-up, down, forward, backward, stop. 
	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		m_intake.setWristTurn(0);
    m_intake.setPivotTurn(0);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		m_intake.setRollerTurn(0.2);
	}

	// Called once the command ends or is interrupted
	@Override
	public void end(boolean interrupted) {
		m_intake.setRollerTurn(0.2);
		m_intake.setWristTurn(0.1);
    m_intake.setPivotTurn(0.2);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
