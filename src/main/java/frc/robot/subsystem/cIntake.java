package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

//this code for now is mostly driver oriented
//will be modified to use commands for pid
public class cIntake extends SubsystemBase {

    private static cIntake cIntake = null; //put this if you want the robot to periodically call this

    private SparkMax clawMotor;
    private SparkMax armMotor;
    private SparkMaxConfig clawConfig;
    private SparkMaxConfig armConfig;

    private cIntake(){
        clawMotor = new SparkMax(5, MotorType.kBrushless);
        armMotor = new SparkMax(6, MotorType.kBrushless);
        clawConfig = new SparkMaxConfig();
        clawConfig = new SparkMaxConfig();

        armMotor.configure(clawConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        clawMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    void runClaw(boolean button1, boolean button2){
        if (button1){
            clawMotor.set(1);
        } else if(button2) {
            clawMotor.set(-1);
        }
    }

    public static cIntake getInstance(){
        if (cIntake == null){
            cIntake = new cIntake();
        }
        return cIntake;
    }
}
