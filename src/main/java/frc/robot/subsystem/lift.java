package frc.robot.subsystem;

// import com.ctre.phoenix6.configs.MotorOutputConfigs;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class lift extends SubsystemBase{

    private static lift elevator = null; //put this if you want the robot to periodically call this

    private final PIDController cPID;
    private TalonFX liftMotor;
    private double liftStates[] = {0, 1440, 2160, 3240}; //temporary value while true encoder values are determines
    private ElevatorFeedforward eFeedforward;
    // private TalonFXConfiguration liftConfig;
    private int index = 1;


    private lift(){
        liftMotor = new TalonFX(7);  
        cPID = new PIDController(1, 0, 0);
        eFeedforward = new ElevatorFeedforward(0.027, 0.27, 9.99, 0.027);
    }                 
        
    public void freeLift(double yStick){
        if(yStick > 0.1){ 
            liftMotor.set(yStick*0.45);
        }
        else if(yStick < -0.1){
            liftMotor.set(yStick*0.4);
        }
        else{
            liftMotor.set(0);
        }
    }   

    public double getAngle() {
        return (liftMotor.getPosition().getValueAsDouble()/16);
    }

    //this requires the size of the sprocket on the gear to calculate needed encoder values
    //also requires use of pov to use the Dpad
    public void setLift(boolean input1, boolean input2, boolean input3, boolean input4){ //assuming input1 is up and input 2 is down

        if(input1){
            liftPID(0);
        }
        else if(input2){
            liftPID(1);
        }
        else if(input3){
            liftPID(2);
         }
         else if(input4){
           liftPID(3);
         }
        else{
            liftMotor.set(0);
        }

        liftPID(index-1);
        SmartDashboard.putNumber("index :", index);
    }

    public void liftPID(int state) {
        double oPosition = liftMotor.getPosition().getValueAsDouble(); 
        double setAngle = liftStates[state];
        double calcAngle = (oPosition/16)*360;
        double velocity = liftMotor.getVelocity().getValueAsDouble();
        //Feed PID your current angle and your target angle
        final double calcPID = cPID.calculate(calcAngle, setAngle);
        final double calcFeedForward = eFeedforward.calculate(velocity);
        //Give the motor the calculated power
        liftMotor.setVoltage(calcPID + calcFeedForward);
    }

    public static lift getInstance(){
        if (elevator == null){
            elevator = new lift();
        }
        return elevator;
    }
}