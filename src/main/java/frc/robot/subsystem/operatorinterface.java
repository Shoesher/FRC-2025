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
    private aIntake algae = aIntake.getInstance();
    private cIntake coral = cIntake.getInstance();

    //constructors
    private operatorinterface(){
        controller = new XboxController(0);
    }

    //Integrating Subsystem and driver inputs
    private void updateDrive(){
        //find proper controller method for d pad later
        drive.Drivecode(controller.getLeftY(), controller.getRightX(), controller.getLeftBumper(), controller.getRightBumper());
    }

    private void updateAlgae(){
        algae.angleArm(controller.getXButton(), controller.getYButton(), controller.getBButton());
        algae.intakeAlgae(controller.getLeftTriggerAxis(), controller.getRightTriggerAxis());
    }

    private void updateCoral(){
        coral.setIntake(controller.getAButton());
    }

    @Override
    public void periodic(){
        updateDrive();
        updateCoral();
        updateAlgae();
    }
    
    
    public static operatorinterface getInstance(){
        if (oi == null){
            oi = new operatorinterface();
        }
        return oi;
    }
}
