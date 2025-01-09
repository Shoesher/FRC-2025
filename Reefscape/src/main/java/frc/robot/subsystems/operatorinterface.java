package frc.robot.subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class operatorinterface extends SubsystemBase {
    private static operatorinterface oi = null; //add comments pls
    private XboxController controller;
    private drivetrain drive = drivetrain.getInstance();

    //constructors
    private operatorinterface(){
        controller = new XboxController(0);
    }

    //Integrating Subsystem and driver inputs
    private void updateDrive(){
        //find proper controller method for d pad later
        drive.Drivecode(controller.getLeftY(), controller.getRightY(), controller.getLeftBumper(), controller.getRightBumper());
    }

    @Override
    public void periodic(){
        updateDrive();
    }
    
    
    public static operatorinterface getInstance(){
        if (oi == null){
            oi = new operatorinterface();
        }
        return oi;
    }
}
