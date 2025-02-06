package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

// pathplanner
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;

import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.geometry.Pose2d;




// import com.pathplanner.lib.auto.AutoBuilder;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;




public class drivetrain extends SubsystemBase {

    private static drivetrain drivetrain = null; //put this if you want the robot to periodically call this
    // Initializing objects

    //Motors and Controller
    private SparkMax leftfront;
    private SparkMax leftrear;
    private SparkMax rightfront;
    private SparkMax rightrear;
    private double[] Modes = {0.25,0.5,0.75,1};

    //Gyro stuff
    private Pigeon2 gyro;
    private RelativeEncoder rightEncoder;
    private RelativeEncoder leftEncoder;
    private DifferentialDriveOdometry odometry;
    public AutoBuilder autoBuilder;
    private DifferentialDriveKinematics kinematics;
    private final PIDController leftPID;
    private final PIDController rightPID;
    private final SimpleMotorFeedforward feedforward;
    private RobotConfig config;
    
    private drivetrain(){
        leftfront = new SparkMax(3, MotorType.kBrushless);
        leftrear = new SparkMax(4, MotorType.kBrushless);
        rightfront = new SparkMax(1, MotorType.kBrushless);
        rightrear = new SparkMax(2, MotorType.kBrushless);
       
        //PathPlanner
        autoBuilder = new AutoBuilder();
        gyro = new Pigeon2(0);
        rightEncoder = rightfront.getEncoder();
        leftEncoder = leftfront.getEncoder();
        //odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(navx.getYaw()), leftEncoder.getDistance(), rightEncoder.getDistance());
        autoBuilder = new AutoBuilder();
        kinematics = new DifferentialDriveKinematics(1.09);
        leftPID = new PIDController(1, 0, 0);
        rightPID = new PIDController(1, 0, 0);
        feedforward = new SimpleMotorFeedforward(1, 3);
        

        //motor configuration
        SparkMaxConfig config1 = new SparkMaxConfig();
        SparkMaxConfig config2 = new SparkMaxConfig();
        SparkMaxConfig config3 = new SparkMaxConfig();
        SparkMaxConfig config4 = new SparkMaxConfig();

        config1.inverted(true);
        config2.inverted(true).follow(3);
        config4.follow(1);
       
        leftfront.configure(config1, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        leftrear.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightfront.configure(config3, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightrear.configure(config4, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //pathplanner
    
        try{
            config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            e.printStackTrace();
        }

    

        // Configure AutoBuilder last
        AutoBuilder.configure(
            this::getPose, // Robot pose supplier
            this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
            this::getCurrentSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            (speeds, feedforwards) -> drive(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
            new PPLTVController(0.02), // PPLTVController is the built in path following controller for differential drive trains
            config, // The robot configuration
            () -> {
                // Boolean supplier that controls when the path will be mirrored for the red alliance
                // This will flip the path being followed to the red side of the field.
                // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

            var alliance = DriverStation.getAlliance();
            if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
            }
                return false;
                },
                this // Reference to this subsystem to set requirements
        );

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
        if (Lbumper) {
            speed-=1;
            if (speed<1){speed = 1;}
        }
        else if(Rbumper) {
            speed+=1;
            if (speed>4){speed = 4;}
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

    public void Drivecode(double Leftjoy, double Rightjoy, boolean LeftBumper, boolean RightBumper){
        double gear = SpeedMode(LeftBumper, RightBumper);

        // if(Math.abs(Leftjoy) > 0.1|| Math.abs(Rightjoy) > 0.1){
        //     leftfront.set((Leftjoy + Rightjoy)*gear);
        //     rightfront.set((Leftjoy - Rightjoy)*gear);
        // } else{
        //     Stopdrive();
        // }

        if (Math.abs(Leftjoy) > 0.1) {
            leftfront.set(Leftjoy * gear);
            rightfront.set(Leftjoy * gear);
        } else if (Math.abs(Rightjoy) > 0.1) {
            leftfront.set(Rightjoy * gear);
            rightfront.set(Rightjoy * gear);
        } else {
            Stopdrive();
        }
    }
    
        //note that the encoder spin rate values will be off because it's not connected to the shaft at the end of the gearbox 
        //so the code might have to account for gear ratio
        
        // //auto methods:
    
        // //Gyro Stuff
    
        //getPosition (pose)
        public Pose2d getPose(){
            return odometry.getPoseMeters();
        }
        //resetPosition (reset pose)
        public void resetPose(Pose2d pose) {
            leftEncoder.setPosition(0);
            rightEncoder.setPosition(0);
            gyro.reset();
            odometry.resetPosition(gyro.getRotation2d() ,leftEncoder.getPosition(), rightEncoder.getPosition(), pose);
        }
    
    
        //Kinematics stuff
    
        //get Chasis speed (getCurrentSpeeds) 
        public ChassisSpeeds getCurrentSpeeds() {
            return kinematics.toChassisSpeeds(new DifferentialDriveWheelSpeeds((leftEncoder.getVelocity() * 18.85)/60, rightEncoder.getVelocity()/60));
        }
        //drive method
        public void drive(ChassisSpeeds chassisSpeeds) {
            DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);
            setSpeeds(wheelSpeeds);
        }
        //set speeds
        public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
            final double leftFeedforward = feedforward.calculate(speeds.leftMetersPerSecond);
            final double rightFeedforward = feedforward.calculate(speeds.rightMetersPerSecond);
        
            final double leftOutput = leftPID.calculate(leftEncoder.getVelocity(), speeds.leftMetersPerSecond);
            final double rightOutput = rightPID.calculate(rightEncoder.getVelocity(), speeds.rightMetersPerSecond);
        
            leftfront.setVoltage(leftOutput + leftFeedforward);
            rightfront.setVoltage(rightOutput + rightFeedforward);
        }



    //sends to operator interface
    public static drivetrain getInstance(){
        if (drivetrain == null){
            drivetrain = new drivetrain();
        }
        return drivetrain;
    }
}