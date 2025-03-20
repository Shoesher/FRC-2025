package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

// pathplanner
// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.config.RobotConfig;
// import com.pathplanner.lib.controllers.PPLTVController;

import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

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
    private int speed = 1;

    //Gyro stuff
    private Pigeon2 gyro;
    private RelativeEncoder rightEncoder;
    private RelativeEncoder leftEncoder;
    private DifferentialDriveOdometry odometry;
    // public AutoBuilder autoBuilder;
    private DifferentialDriveKinematics kinematics;
    private final PIDController leftPID;
    private final PIDController rightPID;
    private final SimpleMotorFeedforward feedforward;
    // private RobotConfig config;
    
    private drivetrain(){
        leftfront = new SparkMax(4, MotorType.kBrushless);
        leftrear = new SparkMax(3, MotorType.kBrushless);
        rightfront = new SparkMax(2, MotorType.kBrushless);
        rightrear = new SparkMax(1, MotorType.kBrushless);
       
        //PathPlanner
        // autoBuilder = new AutoBuilder();
        gyro = new Pigeon2(10);
        rightEncoder = rightfront.getEncoder();
        leftEncoder = leftfront.getEncoder();
        odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(gyro.getYaw().getValueAsDouble()), getLeftDistance(), getRightDistance());
        // autoBuilder = new AutoBuilder();
        kinematics = new DifferentialDriveKinematics(0.608);
        leftPID = new PIDController(0.001, 0, 0);
        rightPID = new PIDController(0.001, 0, 0);
        feedforward = new SimpleMotorFeedforward(0.2, 2.16, 0.39);
        

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
    
        //pathplanner
    
    //     try{
    //         config = RobotConfig.fromGUISettings();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     // Configure AutoBuilder last
    //     AutoBuilder.configure(
    //         this::getPose, // Robot pose supplier
    //         this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
    //         this::getCurrentSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
    //         (speeds, feedforwards) -> drive(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
    //         new PPLTVController(0.02), // PPLTVController is the built in path following controller for differential drive trains
    //         config, // The robot configuration
    //         () -> {
    //             // Boolean supplier that controls when the path will be mirrored for the red alliance
    //             // This will flip the path being followed to the red side of the field.
    //             // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

    //         var alliance = DriverStation.getAlliance();
    //         if (alliance.isPresent()) {
    //             return alliance.get() == DriverStation.Alliance.Red;
    //         }
    //             return false;
    //             },
    //             this // Reference to this subsystem to set requirements
    //     );

    }

    //Teleop Drivetrain code

    private void Stopdrive() {
        leftfront.set(0);
        rightfront.set(0);
    }

    private double SpeedMode(boolean Lbumper, boolean Rbumper){
        //gear1 = 25% speed, gear2 = 50%, gear3 = 75%, gear4 = 100%
    
        //using bumpers to increase or decrease gear
        if (Lbumper) {
            speed--;
            if (speed < 1){
                speed = 1;
            }
        }
        else if(Rbumper) {
            speed++;
            if (speed > 4){
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
                return Modes[0];
        }
    }

    public void Drivecode(double Leftjoy, double Rightjoy, boolean LeftBumper, boolean RightBumper){
        double gear = SpeedMode(LeftBumper, RightBumper);

        if(Math.abs(Leftjoy) > 0.1|| Math.abs(Rightjoy) > 0.1){
            leftfront.set((Leftjoy + Rightjoy)*gear);
            rightfront.set((Leftjoy - Rightjoy)*gear);
        } else{
            Stopdrive();
        }
    }
    
    //auto methods:
    
        //getPosition (pose)
        public Pose2d getPose(){
            return odometry.getPoseMeters();
        }
        //resetPosition (reset pose)
        public void resetPose(Pose2d pose) {
            leftEncoder.setPosition(0);
            rightEncoder.setPosition(0);
            gyro.reset();
            odometry.resetPosition(gyro.getRotation2d() , 0, 0, pose);
        }

        //get Chasis speed (getCurrentSpeeds)  
        public ChassisSpeeds getCurrentSpeeds() {
            return kinematics.toChassisSpeeds(new DifferentialDriveWheelSpeeds(getLeftSpeedMetersPerSecond(), getRightSpeedMetersPerSecond()));
        }
        //drive method
        public void drive(ChassisSpeeds speeds) {
            DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(speeds);
            setSpeeds(wheelSpeeds);
            // setRate(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
        }

        //set speeds
        public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
            final double leftFeedforward = feedforward.calculate(speeds.leftMetersPerSecond);
            final double rightFeedforward = feedforward.calculate(speeds.rightMetersPerSecond);
        
            final double leftOutput = leftPID.calculate(getLeftSpeedMetersPerSecond(), speeds.leftMetersPerSecond);
            final double rightOutput = rightPID.calculate(getRightSpeedMetersPerSecond(), speeds.rightMetersPerSecond);
        
            leftfront.setVoltage(leftOutput + leftFeedforward);
            rightfront.setVoltage(rightOutput + rightFeedforward);
        }

        // public void setRate(double leftSpeed, double rightSpeed) {
        //     final double leftFeedforward = feedforward.calculate(leftSpeed);
        //     final double rightFeedforward = feedforward.calculate(rightSpeed);
        
        //     final double leftOutput = leftPID.calculate(getLeftSpeedMetersPerSecond(), leftSpeed);
        //     final double rightOutput = rightPID.calculate(getRightSpeedMetersPerSecond(), rightSpeed);
        
        //     leftfront.setVoltage(leftOutput + leftFeedforward);
        //     rightfront.setVoltage(-(rightOutput + rightFeedforward));
        // }

        public void setVoltage(double leftVoltage, double rightVoltage){
            leftfront.setVoltage(leftVoltage);
            rightfront.setVoltage(rightVoltage);
        }

        //Calculations
        public double getLeftDistance(){
            return (leftEncoder.getPosition()/8.450)*0.478;
        }

        public double getRightDistance(){
            return (rightEncoder.getPosition()/8.450)*0.478;
        }

        public double getLeftSpeedMetersPerSecond(){
            return (leftEncoder.getVelocity()/ 60 / 8.450)*0.478;
        }

        public double getRightSpeedMetersPerSecond(){
            return (rightEncoder.getVelocity()/ 60 / 8.450)*0.478;
        }

        //Update Gyros
        @Override
        public void periodic() {
            odometry.update(gyro.getRotation2d(), getLeftDistance(), getRightDistance());
        }

    //sends to operator interface
    public static drivetrain getInstance(){
        if (drivetrain == null){
            drivetrain = new drivetrain();
        }
        return drivetrain;
    }
}