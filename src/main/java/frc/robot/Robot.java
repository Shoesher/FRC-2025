// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;

// import frc.robot.subsystem.aIntake;

import frc.robot.subsystem.drivetrain;
import frc.robot.subsystem.operatorinterface;

import frc.robot.subsystem.LEDS;


public class Robot extends TimedRobot {

  public drivetrain drive;
  public operatorinterface oi;
  
  public LEDS led;

  public Command AutonomousCommand;
  // public Command driveF;
  // private VisionSim visionSim;


  @Override
  public void robotInit() {
    drive = drivetrain.getInstance();
    oi = operatorinterface.getInstance();

    led = LEDS.getInstance();

  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    // Update VisionSim with the new robot pose
    // Pose2d robotPose = drive.getPose(); // Get the updated pose from the drivetrain
    // visionSim.update(robotPose);

    
  }

  @Override
  public void autonomousInit() {

    if (AutonomousCommand != null) {
      AutonomousCommand.schedule();
    }


  }

  @Override
  public void autonomousPeriodic() {
    led.AutonWave();
  }

  @Override
  public void teleopInit() {
    if (AutonomousCommand != null) {
      AutonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    led.Wave();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}