package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//motor initialization
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

//automated arm states
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;

//code has the following dependencies

//PID has to be adjusted
//encoder value may be weird after more uses because it is never reset
//May have to use co-terminal angle logic for the target angle as motor gains more roations for the same angles
public class cIntake extends SubsystemBase {

    private static cIntake cIntake = null; //put this if you want the robot to periodically call this

    //claw initialization
    private SparkMax clawMotor;
    private SparkMax armMotor;
    private SparkMaxConfig clawConfig;
    private SparkMaxConfig armConfig;

    //arm initialiation
    private final PIDController cPID;
    private RelativeEncoder armCoder;
    private double encoderDPP = 42;
    private double armStates[] = {270,45,0}; //proccessing angle to be determined, same with intake angle

    private cIntake(){
        //Motor initialization
        clawMotor = new SparkMax(5, MotorType.kBrushless);
        armMotor = new SparkMax(6, MotorType.kBrushless);
        clawConfig = new SparkMaxConfig();
        armConfig = new SparkMaxConfig();

        armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        clawMotor.configure(clawConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //Attack modes
        cPID = new PIDController(0.01, 0, 0);
        armCoder = armMotor.getEncoder();
    }

    public void runClaw(double input1, double input2){
        if (input1 > 0.1 && input1 < 0.9){
            clawMotor.set(1);
        } else if(input2 > 0.1 && input2 < 0.9) {
            clawMotor.set(-1);
        }
    }

    public void setArm(boolean button1, boolean button2, boolean button3){
        if(button1){//reset button
            var target = armStates[0];
            armPID(target);
        }
        else if(button2){//attack angle
            var target = armStates[1];
            armPID(target);
        }
        else if(button3){//lvl1 angle
            var target = armStates[2];
            armPID(target);
        }
    }

    public void armPID(double setAngle){
        double calcAngle = (armCoder.getPosition()/encoderDPP)*360;
        //multiply set angle by # of roations which is (armCoder ticks/ DPP) then multiply the target angle to get the co terminal equivilent
        //if the PID is having trouble try above solution
        double calcPID = cPID.calculate(calcAngle, setAngle);

        armMotor.set(calcPID);
    }



    public static cIntake getInstance(){
        if (cIntake == null){
            cIntake = new cIntake();
        }
        return cIntake;
    }
}
