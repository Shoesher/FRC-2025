package frc.robot.commands.arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.cIntake;

public class holding extends Command{
    private final cIntake arm;

    public holding(cIntake arm){
        this.arm = arm;
        addRequirements(arm);
    }
    @Override
    public void initialize(){
        
    }

    public void execute(){
        arm.armPID(3);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
