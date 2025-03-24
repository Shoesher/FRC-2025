package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.cIntake;

public class intake extends Command{
    private final cIntake arm;
    private double currentAngle;
    private double targetAngle;

    public intake(cIntake arm){
        this.arm = arm;
        addRequirements(arm);
        targetAngle = 7200;
    }

    @Override
    public void initialize(){
    }

    public void execute(){
        currentAngle = arm.getIntakeAngle();
        if(currentAngle < targetAngle){
            arm.runIntake(1, 0);
        }
        else{
            arm.runIntake(0, 0);
        }
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}