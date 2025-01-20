package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class aIntake extends SubsystemBase {

    private static aIntake algae = null; //put this if you want the robot to periodically call this

    private WPI_TalonSRX intake;

    private aIntake(){
       intake = new WPI_TalonSRX(8);
    }

    public void intakeAlgae(int input1, int input2){
        if(input1 == 90 && input1 != 270){
            intake.set(1);
        } else if(input2 == 270 && input2 != 90){
            intake.set(-1);
        }
    }

    public static aIntake getInstance(){
        if (algae == null){
            algae = new aIntake();
        }
        return algae;
    }
    
}
