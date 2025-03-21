package frc.robot.commands.arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.cIntake;

public class scoring extends Command{
    private final cIntake arm;
    private double targetAngle;
    private double currentAngle;

    public scoring(cIntake arm){
        this.arm = arm;
        addRequirements(arm);
        targetAngle = 35;
    }
    @Override
    public void initialize(){
        
    }

    public void execute(){
        currentAngle = arm.getAngle()*360;
        if (currentAngle < targetAngle){
            arm.freeArm(true, false);
        }
        else if(currentAngle > targetAngle){
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
