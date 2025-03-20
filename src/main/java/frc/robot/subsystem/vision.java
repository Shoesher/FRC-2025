package frc.robot.subsystem;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class vision extends SubsystemBase {
    private final PhotonCamera camera = new PhotonCamera("photonvision");
    private final PIDController turnPID = new PIDController(0.02, 0, 0.002); // Tune PID values
    
    public vision() {
        turnPID.setTolerance(1.0); // Degrees of tolerance
    }
    
    public double getTurnAdjustment() {
        PhotonPipelineResult result = camera.getLatestResult();
        if (result.hasTargets()) {
            PhotonTrackedTarget target = result.getBestTarget();
            double yaw = target.getYaw();
            return turnPID.calculate(yaw, 0.0); // Adjust turn based on yaw offset
        }
        return 0.0;
    }
}