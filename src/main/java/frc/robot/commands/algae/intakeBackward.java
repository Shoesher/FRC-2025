package frc.robot.commands.algae;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.aIntake;

public class intakeBackward extends Command{
    private final aIntake algae;

    public intakeBackward(aIntake algae){
        this.algae = algae;
        addRequirements(algae);
    }

    @Override
    public void initialize(){

    }

    public void execute(){
        boolean run = true;
        algae.runBackward(run);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
    