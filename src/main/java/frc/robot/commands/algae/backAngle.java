package frc.robot.commands.algae;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.aIntake;

public class backAngle extends Command{
    private final aIntake algae;

    public backAngle(aIntake algae){
        this.algae = algae;
        addRequirements(algae);
    }

    @Override
    public void initialize(){

    }

    public void execute(){
        boolean run = true;
        algae.angleInverse(run);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
