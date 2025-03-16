package frc.robot.commands;

//SmartDashboard AutoChooser
import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.path.PathPlannerPath;
// import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

//importing commands

//elevator commands
import com.pathplanner.lib.auto.NamedCommands;
import frc.robot.commands.lift.L1;
import frc.robot.commands.lift.L2;
import frc.robot.commands.lift.L3;
import frc.robot.commands.lift.L4;
//arm commands
import frc.robot.commands.arm.scoring;
import frc.robot.commands.arm.grabbing;
import frc.robot.commands.arm.holding;
import frc.robot.commands.arm.dAlgae;

public class RobotContainer {
      private final SendableChooser<Command> autoChooser;
      //lift
      private L1 L1;
      private L2 L2;
      private L3 L3;
      private L4 L4;
      //arm
      private scoring score;
      private grabbing grab;
      private holding hold;
      private dAlgae top;

      public RobotContainer(){
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
        NamedCommands.registerCommand("L1", L1);
        NamedCommands.registerCommand("L2", L2);
        NamedCommands.registerCommand("L3", L3);
        NamedCommands.registerCommand("L4", L4);

        NamedCommands.registerCommand("hold", hold);
        NamedCommands.registerCommand("score", score);
        NamedCommands.registerCommand("armDown", grab);
        NamedCommands.registerCommand("armUp", top);
      }

      public Command getAutonomousCommand(){
        return autoChooser.getSelected();
      }

}    