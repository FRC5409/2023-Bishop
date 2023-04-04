// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.tipper;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.kArmSubsystem.kSetpoints;
import frc.robot.Constants.kConeTipper.kTipperSetpoints;
import frc.robot.commands.arm.ArmRotation;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.ConeTipper;

public class TipperUp extends SequentialCommandGroup {
  public TipperUp(ConeTipper tipper, ArmPIDSubsystem arm) {
    addCommands(
      new ConeTipperMove(tipper, kTipperSetpoints.kTipperInward),
      new ArmRotation(arm, kSetpoints.kGroundPickupCone).withTimeout(1)
    );
  }
}
