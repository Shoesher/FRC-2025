package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class cIntake {

    private static cIntake cIntake = null; //put this if you want the robot to periodically call this

    private WPI_TalonSRX motor1;
    private WPI_TalonSRX motor2;

    private cIntake(){
        motor1 = new WPI_TalonSRX(0);
        motor2 = new WPI_TalonSRX(1);
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