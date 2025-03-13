// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.commands.RobotContainer;
import frc.robot.subsystem.aIntake;
import frc.robot.subsystem.cIntake;
import frc.robot.subsystem.drivetrain;
import frc.robot.subsystem.operatorinterface;
import frc.robot.subsystem.lift;
import frc.robot.simulation.VisionSim;


public class Robot extends TimedRobot {

  public drivetrain drive;
  public operatorinterface oi;
  public aIntake algae;
  public cIntake coral;
  public lift elevator;
  public Command getAutonomousCommand;
  private VisionSim visionSim;
  private double leftJoy;
  private double rightJoy;
  private boolean leftBumper;
  private boolean rightBumper;

  @Override
  public void robotInit() {
    drive = drivetrain.getInstance();
    oi = operatorinterface.getInstance();
    algae = aIntake.getInstance();
    coral = cIntake.getInstance();
    elevator = lift.getInstance();
    visionSim = new VisionSim("main", "cameraName");

    // No need for SendableRegistry here for these simple values
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    // Get movement values from SmartDashboard
    leftJoy = SmartDashboard.getNumber("Left Joystick", 0.0); // Left joystick input
    rightJoy = SmartDashboard.getNumber("Right Joystick", 0.0); // Right joystick input
    leftBumper = SmartDashboard.getBoolean("Left Bumper", false); // Left bumper button state
    rightBumper = SmartDashboard.getBoolean("Right Bumper", false); // Right bumper button state

    // Update the robot's movement based on these values
    drive.Drivecode(leftJoy, rightJoy, leftBumper, rightBumper);

    // Update VisionSim with the new robot pose
    Pose2d robotPose = drive.getPose(); // Get the updated pose from the drivetrain
    visionSim.update(robotPose);

    // Display debug field for visualization in SmartDashboard
    SmartDashboard.putData("Field", visionSim.getDebugField());
  }

  @Override
  public void autonomousInit() {
    if (getAutonomousCommand != null) {
      getAutonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (getAutonomousCommand != null) {
      getAutonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
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