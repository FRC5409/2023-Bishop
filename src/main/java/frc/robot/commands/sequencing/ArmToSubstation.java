// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequencing;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.kArmSubsystem;
import frc.robot.Constants.kClaw;
import frc.robot.Constants.kTelescope;
import frc.robot.commands.ArmRotation;
import frc.robot.commands.ClawMovement;
import frc.robot.commands.TelescopeTo;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.NewClaw;
import frc.robot.subsystems.Telescope;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html

public class ArmToSubstation extends ParallelCommandGroup {
  /** Creates a new ArmToSubstation. */

  public ArmToSubstation(
    ArmPIDSubsystem sys_arm,
    Telescope sys_telescope,
    NewClaw sys_claw
  ) {
    super();
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ArmRotation(sys_arm, kArmSubsystem.kSetpoints.kToLoadingshoulder),
      new TelescopeTo(sys_telescope, kTelescope.kDestinations.kRetracted),
      new ClawMovement(sys_claw, kClaw.openPosition)
    );
  }
}