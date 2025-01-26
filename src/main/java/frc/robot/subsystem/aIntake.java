package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//motor initialization
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

//automated arm states
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;


public class aIntake extends SubsystemBase {

    private static aIntake algae = null; //put this if you want the robot to periodically call this

    private WPI_TalonSRX intake;
    private SparkMax algaeMotor;
    private SparkMaxConfig algaeConfig;

    //arm initialiation
    private final PIDController aPID;
    private RelativeEncoder aCoder;
    private double encoderDPP = 42;
    private double armStates[] = {0,90}; //proccessing angle to be determined, same with intake angle

    private aIntake(){
       intake = new WPI_TalonSRX(8);
        //Motor initialization
        algaeMotor = new SparkMax(7, MotorType.kBrushless);
        algaeConfig = new SparkMaxConfig();

        algaeMotor.configure(algaeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
        //Attack modes
        aPID = new PIDController(0.01, 0, 0);
        aCoder = algaeMotor.getEncoder();
    }
    
    public void intakeAlgae(double input1, double input2){
        if(input1 > 0.05){
            intake.set(1);
            algaePID(armStates[0]);
        } else if(input2 > 0.05){
            intake.set(-1);
            algaePID(armStates[1]);
        }
    }

    public void algaePID(double setAngle){
        double calcAngle = (aCoder.getPosition()/encoderDPP)*360;
        //multiply set angle by # of roations which is (armCoder ticks/ DPP) then multiply the target angle to get the co terminal equivilent
        //if the PID is having trouble try above solution
        double calcPID = aPID.calculate(calcAngle, setAngle);

        algaeMotor.set(calcPID);
    }

    public static aIntake getInstance(){
        if (algae == null){
            algae = new aIntake();
        }
        return algae;
    }
    
}