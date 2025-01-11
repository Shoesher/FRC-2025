package frc.robot.subsystem;
import edu.wpi.first.wpilibj.Encoder;
//This life is assuming we are using two cim motors for the elevator (Apapt if using other motors)
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class lift extends SubsystemBase{

    private static lift elevator = null; //put this if you want the robot to periodically call this

    private WPI_TalonSRX Rlift;
    private WPI_TalonSRX Llift;
    private Encoder liftEncoder;
    private double liftStates[] = {};

    private lift(){
        Rlift = new WPI_TalonSRX(3);
        Llift = new WPI_TalonSRX(4);
        liftEncoder = new Encoder(0, 4);
        
        Llift.follow(Rlift);
        Llift.setInverted(true);
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
