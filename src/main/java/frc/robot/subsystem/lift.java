package frc.robot.subsystem;
//Assuming we are using Krakens
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.math.controller.PIDController;


public class lift extends SubsystemBase{

    private static lift elevator = null; //put this if you want the robot to periodically call this

    private final PIDController cPID;
    private TalonFX liftMotor;
    private double liftStates[] = {0, 1440, 2160, 3240}; //temporary value while true encoder values are determines

    private lift(){
        liftMotor = new TalonFX(7);  
        cPID = new PIDController(0.5, 0, 0);
    }
        
        
    void freeLift(double yStick){
        if(yStick > 0.3 || yStick < -0.3){ //0.3 to avoid lifting elevator while driving error
            liftMotor.set(yStick);
        }
    }   

    //this requires the size of the sprocket on the gear to calculate needed encoder values
    //also requires use of pov to use the Dpad
    public void setLift(int input1, int input2){ //assuming input1 is up and input 2 is down

        int index = 1;

        if(input1 == 0 && input1 != 180){
            index +=1;
            if( index > 4){
                index = 4;
            }
        }

        if(input2 == 180 && input2 != 0){
            index -=1;
            if( index < 1){
                index = 1;
            }
        }

        switch (index){
            default:
                
                int position1 = 0;
                liftPID(position1);
              
            case 2:

               int position2 = 1;
               liftPID(position2);
            
            case 3:

                int position3 = 2; 
                liftPID(position3);
    
            case 4:

                int position4 = 3;   
                liftPID(position4); 
        }
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