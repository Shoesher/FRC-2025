package frc.robot.commands.lift;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.lift;

public class L4 extends Command{
    private final lift elevator;

    public L4(lift elevator){
        this.elevator = elevator;
        addRequirements(elevator);
    }   

    @Override
    public void initialize(){

    }

    public void execute(){
        elevator.liftPID(3);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
