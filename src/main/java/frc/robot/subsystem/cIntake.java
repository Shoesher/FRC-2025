package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;

public class cIntake extends SubsystemBase {

    private static cIntake cIntake = null; //put this if you want the robot to periodically call this

    private TalonFX motor1;
    private TalonFX motor2;

    private cIntake(){
        motor1 = new TalonFX(1);
        motor2 = new TalonFX(2);
    }

    private void setIntakeAndEject(boolean button){
        if (button){
            motor1.set(1);
            motor2.set(-1); 
        } else {
            motor1.set(0);
            motor2.set(0);
        }
    }

    public static cIntake getInstance(){
        if (cIntake == null){
            cIntake = new cIntake();
        }
        return cIntake;
    }

    public void setIntake(boolean button){
        setIntakeAndEject(button);
    }
}
// 🐧