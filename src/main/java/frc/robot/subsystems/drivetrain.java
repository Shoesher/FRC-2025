package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
//import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
//import edu.wpi.first.math.kinematics.ChassisSpeeds;
//import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Rotation2d;

//import com.pathplanner.lib.auto.AutoBuilder;
//import com.pathplanner.lib.util.ReplanningConfig;
//import com.kauailabs.navx.frc.AHRS;
//import edu.wpi.first.wpilibj.SPI;
//import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.math.controller.SimpleMotorFeedforward;

// import com.pathplanner.lib.auto.ReplanningConfig;


public class drivetrain extends SubsystemBase {

    private static drivetrain drivetrain = null; //put this if you want the robot to periodically call this
    // Initializing objects

    //Motors and Controller
    private CANSparkMax leftfront;
    private CANSparkMax leftrear;
    private CANSparkMax rightfront;
    private CANSparkMax rightrear;
    private double[] Modes = {0.25,0.5,0.75,1};

    // //Gyro stuff
    // private AHRS navx;
    // private Encoder rightEncoder;
    // private Encoder leftEncoder;
    // // private final Rotation2d rotation2d = new Rotation2d();
    // private DifferentialDriveOdometry odometry;
    // public AutoBuilder autoBuilder;
    // private DifferentialDriveKinematics kinematics;
    // private final PIDController leftPID;
    // private final PIDController rightPID;
    // private final SimpleMotorFeedforward feedforward;
    
    private drivetrain(){
        leftfront = new CANSparkMax(3, MotorType.kBrushless);
        leftrear = new CANSparkMax(4, MotorType.kBrushless);
        rightfront = new CANSparkMax(1, MotorType.kBrushless);
        rightrear = new CANSparkMax(2, MotorType.kBrushless);
        // navx = new AHRS(SPI.Port.kMXP);
        // rightEncoder = new Encoder(0,1);
        // leftEncoder = new Encoder(0,2);
        // odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(navx.getYaw()), leftEncoder.getDistance(), rightEncoder.getDistance());
        // autoBuilder = new AutoBuilder();
        // kinematics = new DifferentialDriveKinematics(1.09);
        // leftPID = new PIDController(1, 0, 0);
        // rightPID = new PIDController(1, 0, 0);
        // feedforward = new SimpleMotorFeedforward(1, 3);

        leftfront.restoreFactoryDefaults();
        leftrear.restoreFactoryDefaults();
        rightfront.restoreFactoryDefaults();
        rightrear.restoreFactoryDefaults();

        leftfront.setInverted(true);
        leftrear.setInverted(true);

        leftrear.follow(leftfront);
        rightrear.follow(rightfront);
    }

    //Drive Methods
    private void Stopdrive() {
        leftfront.set(0);
        rightfront.set(0);
    }

    private double SpeedMode(boolean Lbumper, boolean Rbumper){
        //gear1 = 25% speed, gear2 = 50%, gear3 = 75%, gear4 = 100%
        //keep gear at 3 by default make a constant switch case statment that sets returns a mode based the speed value
        int speed = 3;
        //using bumpers to increase or decrease gear
        if(Lbumper){
            speed = speed-1;
            if(speed<1){
                speed = 1;
            }
        }
        else if(Rbumper){
            speed = speed+1;
            if(speed>4){
                speed = 4;
            }
        }
        //setting speed based on gear
        switch (speed){
            case 1:
                return Modes[0];
                
            case 2:
                return Modes[1];
                
            case 3:
                return Modes[2];
              
            case 4:
                return Modes[3];
                
            default:
                return Modes[2];
        }
    }

    public void Drivecode(double Leftjoy, double Rightjoy, boolean LeftBumper, boolean RightBumper) {
 
        double gear = SpeedMode(LeftBumper, RightBumper);

        if(Math.abs(Leftjoy) > 0.1|| Math.abs(Rightjoy) > 0.1){
            leftfront.set((Leftjoy + Rightjoy)*gear);
            rightfront.set((Leftjoy - Rightjoy)*gear);
        } else{
            Stopdrive();
        }
    }
    
    //sends to operator interface
    public static drivetrain getInstance(){
        if (drivetrain == null){
            drivetrain = new drivetrain();
        }
        return drivetrain;
    }
}
