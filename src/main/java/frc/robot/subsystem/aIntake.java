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
        intake = new SparkMax(7, MotorType.kBrushless);
        algaeConfig = new SparkMaxConfig();
        intake.configure(algaeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        algaeMotor = new WPI_TalonSRX(7);
        algaeMotor.setInverted(true);
    }
    
    public void intakeAlgae(boolean input1, boolean input2){
        if(input1){
            intake.set(1);
            algaeMotor.set(1);
           
        } else if(input2){
            intake.set(-1);
            algaeMotor.set(-1);
        }
    }

    public static aIntake getInstance(){
        if (algae == null){
            algae = new aIntake();
        }
        return algae;
    }
    
}