package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
//importing commands
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
//elevator commands
import frc.robot.commands.lift.L1;
import frc.robot.commands.lift.L2;
import frc.robot.commands.lift.L3;
import frc.robot.commands.lift.L4;
//arm commands
import frc.robot.commands.arm.scoring;
import frc.robot.commands.arm.grabbing;
import frc.robot.commands.arm.holding;
import frc.robot.commands.arm.dAlgae;
//Emergancy drive command
import frc.robot.commands.drive.forwards;

public class RobotContainer {
      //lift
      private L1 L1;
      private L2 L2;
      private L3 L3;
      private L4 L4;
      //arm
      private scoring score;
      private grabbing grab;
      private holding hold;
      private dAlgae dAlgae;

      public RobotContainer(){
      }
      //drive forward for a set distance and speed
            public Command driveF(){
        Timer.delay(2);
        return new forwards(6, 6, 1, 1);
      }

      public Command timedAuton(double time) { 
        return new ParallelRaceGroup(driveF(), new WaitCommand(time));
      }
      //Grab coral by going down and back up
      public Command grabCoral(double time){
        return new SequentialCommandGroup(L2, new WaitCommand(time), grab, new WaitCommand(time), L1);
      }
      //Go to the L1 scoring position
      public Command scoreL1(double time){
        return new SequentialCommandGroup(L1, new WaitCommand(time), hold);
      }
      //Go to the L2 scoring position
      public Command scoreL2(double time){
        return new SequentialCommandGroup(score, new WaitCommand(time), L2);
      }
      //Go to the L3 scoring position
      public Command scoreL3(double time){
        return new SequentialCommandGroup(score, new WaitCommand(time), L3);
      }
      //Go to the L4 scoring position
      public Command scoreL4(double time){
        return new SequentialCommandGroup(dAlgae, new WaitCommand(time), L4);
      }
      //Put arm back down to complete the score
      public Command confirm(){
        return grab;
      }

}    