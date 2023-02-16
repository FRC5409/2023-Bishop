// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands.Intake.Manual_old;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Intake;
 
// public class WristMove extends CommandBase
// {
//   private final Intake_old intake;
//   private double voltage;
//   private boolean inverted;

//   public WristMove(Intake_old subsystem, double voltage, boolean inverted)
//   {
//       intake = subsystem;
//       this.voltage = voltage;
//       this.inverted = inverted;

//       // Use addRequirements() here to declare subsystem dependencies.
//       addRequirements(intake);   
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize()
//   {
//     if (inverted)
//     {
//       intake.setWristDirection(true);
//     }
//     else
//     {
//       intake.setWristDirection(false);
//     }

//     intake.wristControl(voltage);
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {}

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted)
//   {
//     intake.wristControl(0);
//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished()
//   {
//     return false;
//   }
// }
