package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.vision;
import frc.robot.subsystem.drivetrain;

public class autoalign extends Command {
    private final drivetrain drive;
    private final vision camera;
    private boolean aligned = false;
    private double initialDistance;
    private boolean drivingForward = false;

    public autoalign(drivetrain drive, vision camera) {
        this.drive = drive;
        this.camera = camera;
        addRequirements(drive, camera);
    }

    @Override
    public void initialize() {
        aligned = false;
        drivingForward = false;
        initialDistance = (drive.getLeftDistance() + drive.getRightDistance()) / 2.0;
    }

    @Override
    public void execute() {
        if (!aligned) {
            double turnPower = camera.getTurnAdjustment();
            if (Math.abs(turnPower) < 0.05 && Math.abs(drive.getLeftSpeedMetersPerSecond()) < 0.1 && Math.abs(drive.getRightSpeedMetersPerSecond()) < 0.1) {
                aligned = true;
            } else {
                drive.setVoltage(-turnPower * 12, turnPower * 12); // Rotate in place using voltage
            }
        } else if (!drivingForward) {
            drive.setVoltage(6, 6); // Drive forward at half voltage
            drivingForward = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (!drivingForward) {
            return false;
        }
        double currentDistance = (drive.getLeftDistance() + drive.getRightDistance()) / 2.0;
        return (currentDistance - initialDistance) >= 1.0; // Drive forward 1 meter
    }

    @Override
    public void end(boolean interrupted) {
        drive.setVoltage(0, 0);
    }
}
