package frc.robot.commands.arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.cIntake;

public class grabbing extends Command{
    private final cIntake arm;
    private double targetAngle;
    private double currentAngle;

    public grabbing(cIntake arm){
        this.arm = arm;
        addRequirements(arm);
        targetAngle = -80;
    }
    @Override
    public void initialize(){
        
    }

    public void execute(){
        currentAngle = arm.getAngle()*360;
        if (currentAngle < targetAngle-2){
            arm.freeArm(true, false);
        }
        else if(currentAngle > targetAngle+2){
            arm.freeArm(false, true);
        }
        else{
            arm.freeArm(false, false);
        }
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}