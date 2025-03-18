package frc.robot.commands.drive;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class forwards extends Command{
    private double leftVoltage;
    private double rightVoltage;

    private double leftDistance;
    private double rightDistance;

    private double leftStart;
    private double rightStart;

    private frc.robot.subsystem.drivetrain drive;

    public forwards(double leftVoltage, double rightVoltage, double leftDistance, double rightDistance){
        this.leftVoltage = leftVoltage;
        this.rightVoltage = rightVoltage;
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
        leftStart = 0;
        rightStart = 0;
        drive = frc.robot.subsystem.drivetrain.getInstance();
        
    
    }

    public void setSpeeds(double leftVoltage, double rightVoltage, double leftDistance, double rightDistance){
        this.leftVoltage = leftVoltage;
        this.rightVoltage = rightVoltage;
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
    }

    @Override
    public void initialize(){
        leftStart = drive.getLeftDistance();
        rightStart = drive.getRightDistance();
    }

    @Override
    public void execute(){
        drive.setVoltage(this.leftVoltage, this.rightVoltage);
    }

    @Override
    public boolean isFinished(){
        return (drive.getLeftDistance()) >= (leftStart + leftDistance) && (drive.getRightDistance()) >= (rightStart + rightDistance);
    }
    

}
