package frc.robot.commands.lift;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.lift;

public class L4 extends Command{
    private final lift elevator;
    private double currentAngle;
    private double targetAngle;

    public L4(lift elevator){
        this.elevator = elevator;
        addRequirements(elevator);
        targetAngle = 3240;
        targetAngle = 3240;
    }   

    @Override
    public void initialize(){

    }

    public void execute(){
        currentAngle = elevator.getAngle()*360;
        if (currentAngle < targetAngle-50){
            elevator.freeLift(0.25);
        }
        else if(currentAngle > targetAngle+50){
            elevator.freeLift(-0.25);
        }
        else{
            elevator.freeLift(0);
        }
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
