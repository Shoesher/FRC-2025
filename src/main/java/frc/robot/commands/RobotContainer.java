package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.path.PathPlannerPath;

// import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;

public class RobotContainer {
      private final SendableChooser<Command> autoChooser;

      public RobotContainer(){
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
      }


      public Command getAutonomousCommand(){
        return autoChooser.getSelected();
      }


  //   public Command getAutonomousCommand() {
  //   // Using autobuilder instead of manual path
  //   try{
  //     PathPlannerPath testPath= PathPlannerPath.fromPathFile("Example Path");
  //     return AutoBuilder.followPath(testPath);
  //   } catch(Exception e){
  //     DriverStation.reportError("The error is:" + e.getMessage(), e.getStackTrace());
  //     return Commands.none();
  //   }
  // }
}    