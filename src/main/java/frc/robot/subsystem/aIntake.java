package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//motor initialization
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

public class aIntake extends SubsystemBase {

    private static aIntake algae = null; //put this if you want the robot to periodically call this

    private SparkMax intake;
    private WPI_TalonSRX algaeMotor;
    private SparkMaxConfig algaeConfig;

    private aIntake(){
        //Motor initialization
        intake = new SparkMax(6, MotorType.kBrushless);
        algaeConfig = new SparkMaxConfig();
        intake.configure(algaeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        algaeMotor = new WPI_TalonSRX(9);
        algaeMotor.setInverted(true);
    }
    
    //for teleop
    public void intakeAlgae(boolean input1, boolean input2){
        if(input1){
            intake.set(1);
            algaeMotor.set(1);
           
        } else if(input2){
            intake.set(-1);
            algaeMotor.set(-1);
        }
    }

    //algae commajnds
    public void angleAlgae(boolean command){
        if(command){
            algaeMotor.set(0.5);   
        } 
    }

    public void angleInverse(boolean command){
        if (command){
            algaeMotor.set(-0.5);    
        }
    }

    public void runForward(boolean command){
        if(command){
            intake.set(1);
        }
    }
    
    public void runBackward(boolean command){
        if(command){
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