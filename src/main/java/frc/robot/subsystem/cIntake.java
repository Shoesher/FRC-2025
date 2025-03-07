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

    private SparkMax armMotor;
    private SparkMaxConfig armConfig;

    //arm initialiation
    private final PIDController cPID;
    private RelativeEncoder armCoder;
    private double encoderDPP = 42;
    private double armStates[] = {270,90,35,0}; //proccessing angle to be determined, same with intake angle
    private int position = 1;

    private cIntake(){
        //Motor initialization
        armMotor = new SparkMax(5, MotorType.kBrushless);
        armConfig = new SparkMaxConfig();
        armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //Attack modes
        cPID = new PIDController(0.01, 0, 0);
        armCoder = armMotor.getEncoder();
    }

    public void setArm(boolean button1, boolean button2){

        if(button1){
            position ++;
            if(position > 4){
                position = 4;
            }
        }

        if(button2){
            position --;
            if(position < 1){
                position = 1;
            }
        }

        //The position value minus one will be the correct state value for the armPID to recieve the correct setAngle
        armPID(position-1);
    }

    public void armPID(int state){
        double setAngle = armStates[state];
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