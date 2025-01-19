package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;

public class aIntake extends SubsystemBase {

    private static aIntake algae = null; //put this if you want the robot to periodically call this

    private SparkMax intake;
    private SparkMax angle;
    private final PIDController aPID;
    private Encoder aCoder;
    private double encoderDPP = 42;
    private double armStates[] = {90,270,0}; //proccessing angle to be determined, same with intake angle

    private aIntake(){
        intake = new SparkMax(5, MotorType.kBrushless);
        angle = new SparkMax(6, MotorType.kBrushless);
        aPID = new PIDController(0.01, 0, 0);
        aCoder = new Encoder(0,3);
        
        SparkMaxConfig config1 = new SparkMaxConfig();
        SparkMaxConfig config2 = new SparkMaxConfig();

        intake.configure(config1, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        angle.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    
    public void angleArm(boolean button1, boolean button2, boolean button3){
       

        if(button1){
            double reset = armStates[1];
            setArm(reset);
        } else if(button2){
            double armUp = armStates[0];
            setArm(armUp);
        } else if(button3){
            double armProcess = armStates[2];
            setArm(armProcess); 
        }
    }

    public void intakeAlgae(double leftTrigger, double rightTrigger){
        if(leftTrigger > 0.1){
            intake.set(1);
        } else if(rightTrigger > 0.1){
            intake.set(-1);
        }
    }

    public void setArm(double setAngle){
        double calcAngle =  (aCoder.get()/encoderDPP)*360;
        double calcPID = aPID.calculate(calcAngle, setAngle);
        angle.set(calcPID);
    }

    public static aIntake getInstance(){
        if (algae == null){
            algae = new aIntake();
        }
        return algae;
    }
    
}
