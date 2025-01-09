package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton; // unused rn
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class arm extends SubsystemBase {
    private final WPI_VictorSPX ArmMotor; // Sets up object representing the real arm motor
    private static arm arm = null;
   
    // Note that the current arm won't work as intended because it isn't utilizing PID to get into states
    //We don't want to use a toggle because if you hold it will be weird not to mention we are setting arm speed not position
    //to work around this problem I'm using two buttons instead of a toggle
    //Ideally we use enums and PIDs when possible 

    //constructor
    public arm() {
        ArmMotor = new WPI_VictorSPX(5);
    }

    public void runArm(boolean button1, boolean button2){ 
        if(button1){
            ArmMotor.set(1);
        }
        else if(button2){
            ArmMotor.set(-1);
        }
        else{
            stopMotor(); // Stops the motor
        }
    }

    //Methods
    private void stopMotor() {
        ArmMotor.set(0);
    }

    public static arm getInstance(){
        if (arm == null){
            arm = new arm();
        }
        return arm;
    }
}