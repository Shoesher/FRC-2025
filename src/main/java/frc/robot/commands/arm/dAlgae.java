package frc.robot.commands.arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.cIntake;

public class dAlgae extends Command{
    private final cIntake arm;
    private double targetAngle;
    private double currentAngle;

    public dAlgae(cIntake arm){
        this.arm = arm;
        addRequirements(arm);
        targetAngle = 0;
    }
    @Override
    public void initialize(){
        
    }

    public void execute(){
        arm.armPID(2);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
