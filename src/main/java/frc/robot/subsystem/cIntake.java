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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//code has the following dependencies

//PID has to be adjusted
//May have to use co-terminal angle logic for the target angle as motor gains more roations for the same angles
public class cIntake extends SubsystemBase {
    private static cIntake cIntake = null; //put this if you want the robot to periodically call this

    private SparkMax armMotor;
    private SparkMaxConfig armConfig;
    private SparkMax intakeMotor;
    private SparkMaxConfig intakeConfig;

    //arm initialiation
    private final PIDController cPID;
    private RelativeEncoder armCoder;
    private RelativeEncoder intakeCoder;
    // private double encoderDPP = 42;
    private double armStates[] = {0,35,80,-80}; //proccessing angle to be determined, same with intake angle
    private int position = 1;

    private cIntake(){
        //Motor initialization
        armMotor = new SparkMax(5, MotorType.kBrushless);
        intakeMotor = new SparkMax(6, MotorType.kBrushless);

        armConfig = new SparkMaxConfig();
        intakeConfig = new SparkMaxConfig();
        armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        //Attack modes
        cPID = new PIDController(0.0005, 0.000005, 0);
        armCoder = armMotor.getEncoder();
        intakeCoder = intakeMotor.getEncoder();
    }

    public void freeArm(boolean button1, boolean button2){
        if(button1){
            armMotor.set(0.2);
        }
        else if(button2){
            armMotor.set(-0.2);
        }
        else{
            armMotor.set(0);
        }
    }

    public void runIntake(double input1, double input2){
        if(input1 > 0.1){
            intakeMotor.set(0.4);
        }
        else if(input2 > 0.1){
            intakeMotor.set(-0.4);
        }
        else{
            intakeMotor.set(0);
        }
    }

    public double getAngle(){
        return (armCoder.getPosition())/60;
    }

    public double getIntakeAngle(){
        return (intakeCoder.getPosition());
    }

    public void setArm(boolean button1, boolean button2, boolean button3, boolean button4){
        if(button1){
            armPID(0);
            SmartDashboard.putNumber("position :", position);
        }
        else if(button2){
            armPID(1);
            SmartDashboard.putNumber("position :", position);
        }
        else if(button3){
            armPID(2);
            SmartDashboard.putNumber("position :", position);
        }
        else if(button4){
            armPID(3);
            SmartDashboard.putNumber("position :", position);
        }
        else{
            armMotor.set(0);
        }
        //The position value minus one will be the correct state value for the armPID to recieve the correct setAngle
        armPID(position-1);
    }

    public void armPID(int state){
        double setAngle = armStates[state];
        double calcAngle = (armCoder.getPosition()/60)*360;
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