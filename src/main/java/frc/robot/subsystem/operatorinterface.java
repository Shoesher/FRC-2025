package frc.robot.subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class operatorinterface extends SubsystemBase {
    private static operatorinterface oi = null; //add comments pls
    private XboxController controller;
    private XboxController controller2;
    private drivetrain drive = drivetrain.getInstance();
    private aIntake algae = aIntake.getInstance();
    private cIntake coral = cIntake.getInstance();
    private lift elevator = lift.getInstance();
    private climber climb = climber.getInstance();

    //constructors
    private operatorinterface(){
        controller = new XboxController(0);
        controller2 = new XboxController(1);
    }

    //Integrating Subsystem and driver inputs
    private void updateDrive(){
        //find proper controller method for d pad later
        drive.Drivecode(controller.getLeftY(), controller.getRightX(), controller.getLeftBumperButton(), controller.getRightBumperButton());
    }

    private void updateAlgae(){
        algae.intakeAlgae(controller2.getLeftTriggerAxis(), controller2.getRightTriggerAxis());
    }

    private void updateCoral(){
        //coral.runClaw(controller2.getXButton(), controller2.getYButton());
        coral.setArm(controller2.getYButtonPressed(), controller2.getBButtonPressed(), controller2.getAButtonPressed(), controller2.getXButtonPressed());
    }

    private void updateLift(){
        elevator.freeLift(controller2.getRightY());
        elevator.setLift(controller2.getRightBumperButton(), controller2.getLeftBumperButton());
    }

    private void updateClimb(){
        climb.pullClimb(controller.getLeftTriggerAxis(), controller.getRightTriggerAxis());
    }

    @Override
    public void periodic(){
        updateDrive();
        updateCoral();
        updateAlgae();
        updateLift();
        updateClimb();
    }
    
    
    public static operatorinterface getInstance(){
        if (oi == null){
            oi = new operatorinterface();
        }
        return oi;
    }
}