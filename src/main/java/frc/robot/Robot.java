// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.RobotContainer;
import frc.robot.subsystem.aIntake;
import frc.robot.subsystem.cIntake;
import frc.robot.subsystem.drivetrain;
import frc.robot.subsystem.operatorinterface;
import frc.robot.subsystem.lift;



//We're only gonna use this for initializing path planner and autonomous lol



public class Robot extends TimedRobot {

  public drivetrain drive;
  public operatorinterface oi;
  public aIntake algae;
  public cIntake coral;
  public lift elevator;
  public RobotContainer robotContainer;
  public Command getAutonomousCommand;

  @Override
  public void robotInit() {
    // drive = drivetrain.getInstance();
    // oi= operatorinterface.getInstance();
    // algae = aIntake.getInstance();
    // coral = cIntake.getInstance();
    elevator = lift.getInstance();
    // robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    
  }

  @Override
  public void autonomousInit() {
    if(getAutonomousCommand != null){
      getAutonomousCommand.schedule();
  }
  }

  @Override
  public void autonomousPeriodic() {
  }


  @Override
  public void teleopInit() {
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
