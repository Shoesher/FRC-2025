package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.Timer;


// import edu.wpi.first.wpilibj.DriverStation;




public class drivetrain extends SubsystemBase {

    private static drivetrain drivetrain = null; //put this if you want the robot to periodically call this
    // Initializing objects

    //Motors and Controller
    private SparkMax leftfront;
    private SparkMax leftrear;
    private SparkMax rightfront;
    private SparkMax rightrear;
    private boolean nitroActive = false; //speedboost
    private double normalSpeed = 0.15;
    private double boostedSpeed = 0.25;
    private Timer timer;
    private Timer cooldownTimer;



    
    
    private drivetrain(){
        leftfront = new SparkMax(4, MotorType.kBrushless);
        leftrear = new SparkMax(3, MotorType.kBrushless);
        rightfront = new SparkMax(2, MotorType.kBrushless);
        rightrear = new SparkMax(1, MotorType.kBrushless);
       
        //motor configuration
        SparkMaxConfig config1 = new SparkMaxConfig();
        SparkMaxConfig config2 = new SparkMaxConfig();
        SparkMaxConfig config3 = new SparkMaxConfig();
        SparkMaxConfig config4 = new SparkMaxConfig();

        config1.inverted(true);
        config2.inverted(true).follow(4);
        config3.inverted(false);
        config4.inverted(false).follow(2);

        leftfront.configure(config1, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        leftrear.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightfront.configure(config3, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightrear.configure(config4, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
        timer = new Timer();
        cooldownTimer = new Timer();
    }

    //Teleop Drivetrain code

    private void Stopdrive() {
        leftfront.set(0);
        rightfront.set(0);
    }

    

    public void Drivecode(double Leftjoy, double Rightjoy, boolean buttonY){
        if (buttonY && !nitroActive && (cooldownTimer.get() > 3 || !cooldownTimer.isRunning())) {
            nitroActive = true;
            timer.start();
            cooldownTimer.stop();
            cooldownTimer.reset();
        } 
        if(timer.get() > 3){
            nitroActive = false;
            timer.stop();
            timer.reset();
            cooldownTimer.start();
        }
        double speed = nitroActive ? boostedSpeed : normalSpeed;

        if(Math.abs(Leftjoy) > 0.1|| Math.abs(Rightjoy) > 0.1){
            leftfront.set((Leftjoy - Rightjoy)*speed);
            rightfront.set((Leftjoy + Rightjoy)*speed);
        } else{
            Stopdrive();
        }
    }

        

        //Update Gyros
        @Override
        public void periodic() {
            
        }

    //sends to operator interface
    public static drivetrain getInstance(){
        if (drivetrain == null){
            drivetrain = new drivetrain();
        }
        return drivetrain;
    }
}