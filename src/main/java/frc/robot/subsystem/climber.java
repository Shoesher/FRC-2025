package frc.robot.subsystem;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class climber extends SubsystemBase{

    private static climber climb = null;

    SparkMax climbMotor;
    SparkMaxConfig climbConfig;

    private climber(){
        climbMotor = new SparkMax(8, MotorType.kBrushless);
        climbConfig = new SparkMaxConfig();

        climbMotor.configure(climbConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        climbConfig.inverted(true);
    }

    public void pullClimb(double input1, double input2){
        if(input1 > 0.1){
            climbMotor.set(1);
        }
        else if(input2 > 0.1){
            climbMotor.set(-1);
        }
    }

    public static climber getInstance(){
        if (climb == null){
            climb = new climber();
        }
        return climb;
    }
} 