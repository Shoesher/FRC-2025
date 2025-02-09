package frc.robot.commands.arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.cIntake;

public class scoring extends Command{
    private final cIntake arm;

    public scoring(cIntake arm){
        this.arm = arm;
        addRequirements(arm);
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
