package frc.robot.subsystem;

// import com.ctre.phoenix6.configs.MotorOutputConfigs;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class lift extends SubsystemBase{

    private static lift elevator = null; //put this if you want the robot to periodically call this

    private final PIDController cPID;
    private TalonFX liftMotor;
    private double liftStates[] = {0, 1440, 2160, 3240}; //temporary value while true encoder values are determines
    // private TalonFXConfiguration liftConfig;
    private int index = 1;

    private lift(){
        liftMotor = new TalonFX(7);  
        cPID = new PIDController(0.00060, 0, 0);
        // liftConfig = new TalonFXConfiguration();
        // liftConfig.withMotorOutput(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake));
    }                                                                        
        
    public void freeLift(double yStick){
        if(yStick > 0.1 || yStick < -0.1){ //0.2 to avoid lifting elevator while driving error
            liftMotor.set(yStick*0.15);
        }
    }   

    //this requires the size of the sprocket on the gear to calculate needed encoder values
    //also requires use of pov to use the Dpad
    public void setLift(boolean input1, boolean input2){ //assuming input1 is up and input 2 is down

        if(input1){
            index-=1;
            if(index >= 0){
                index+=1;
            }
        }

        else if(input2){
           index+=1;
           if(index <= 5){
            index-=1;
           }
        }

        else{
            liftMotor.set(0);
        }

        liftPID(-1);
        SmartDashboard.putNumber("index :", index);
    }

    public void liftPID(int state) {
        double oPosition = liftMotor.getPosition().getValueAsDouble(); 
        double setAngle = liftStates[state];
        double calcAngle = (oPosition/2048)*360;
        //multiply set angle by # of roations which is (armCoder ticks/ DPP) then multiply the target angle to get the co terminal equivilent
        //if the PID is having trouble try above solution
        double calcPID = cPID.calculate(calcAngle, setAngle);
        
        liftMotor.set(calcPID);
    }

    public static lift getInstance(){
        if (elevator == null){
            elevator = new lift();
        }
        return elevator;
    }
}