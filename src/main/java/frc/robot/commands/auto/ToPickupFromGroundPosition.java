// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.kArmSubsystem;
import frc.robot.Constants.kTelescope;
import frc.robot.commands.ArmRotation;
import frc.robot.commands.TelescopeTo;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.Telescope;

public class ToPickupFromGroundPosition extends ParallelCommandGroup {

    public ToPickupFromGroundPosition(ArmPIDSubsystem sys_arm, Telescope sys_telescope) {
        addCommands(
            new ArmRotation(sys_arm, kArmSubsystem.kSetpoints.kPickupConeFromGround),
            new TelescopeTo(sys_telescope, kTelescope.kDestinations.kPickupConeFromGround)
        );
    }
}
