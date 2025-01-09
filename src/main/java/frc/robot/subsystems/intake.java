package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj.XboxController;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class intake extends SubsystemBase {
    // Check if the driver is pressing the toggle button
    // If they aren't, don't do anything
    // If the toggle button is pressed, check if the arm state is up or down, depending on what it is, do the opposite
    private static intake intake = null;
    private WPI_VictorSPX RightMotor; // Sets up object representing the real arm motor
    private WPI_VictorSPX LeftMotor; // Sets up object representing the real arm motor
    // private final XboxController controller = new XboxController(0); // Sets up object for the controller

    //constructors
    public intake() {
        RightMotor =  new WPI_VictorSPX(5);
        LeftMotor =  new WPI_VictorSPX(6);
    }

    public void runIntake(boolean forward, boolean reverse){
        if (forward){
            RightMotor.set(1);
        }
        else if (reverse){
            LeftMotor.set(-1);
        }
        else{
            stopIntake();
        }
    }

    //Methods
    private void stopIntake() {
        RightMotor.set(0);
        LeftMotor.set(0);
    }
    
        //send to operator interface
    public static intake getInstance(){
        if (intake == null){
            intake = new intake();
        }
        return intake;
    }
}