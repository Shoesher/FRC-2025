package frc.robot.subsystem;
import edu.wpi.first.wpilibj.Encoder;
//Assuming we are using Krakens
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class lift extends SubsystemBase{

    private static lift elevator = null; //put this if you want the robot to periodically call this

    private TalonFX Rlift;
    private TalonFX Llift;
    private Encoder liftEncoder;
    private double liftStates[] = {};

    private lift(){
        Rlift = new TalonFX(3);
        Llift = new TalonFX(4);
        liftEncoder = new Encoder(0, 4);
    }

    void freeLift(double yStick){
        if(yStick > 0.3 || yStick < -0.3){ //0.3 to avoid lifting elevator while driving error
            Rlift.set(yStick);
        }
    }

    //this requires the size of the sprocket on the gear to calculate needed encoder values
    //also requires use of pov to use the Dpad
    private void stageLift(){

    }

    public static lift getInstance(){
        if (elevator == null){
            elevator = new lift();
        }
        return elevator;
    }

}
