package frc.robot.simulation;

import org.photonvision.simulation.*;
import org.photonvision.*;
import org.photonvision.estimation.TargetModel;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import java.io.IOException;

public class VisionSim {
    private VisionSystemSim visionSim;
    private PhotonCameraSim cameraSim;
    private Field2d debugField;

    public VisionSim(String label, String cameraName) {
        // Initialize Vision System Simulation
        visionSim = new VisionSystemSim(label);
        
        // Initialize Camera Properties
        SimCameraProperties cameraProp = new SimCameraProperties();
        cameraProp.setCalibration(640, 480, Rotation2d.fromDegrees(100));
        cameraProp.setCalibError(0.25, 0.08);
        cameraProp.setFPS(20);
        cameraProp.setAvgLatencyMs(35);
        cameraProp.setLatencyStdDevMs(5);

        // Create the PhotonCamera and Simulation
        PhotonCamera camera = new PhotonCamera(cameraName);
        cameraSim = new PhotonCameraSim(camera, cameraProp);
        
        // Enable the raw and processed streams
        cameraSim.enableRawStream(true);
        cameraSim.enableProcessedStream(true);

        // Enable wireframe visualization (resource-intensive, so only use it when needed)
        cameraSim.enableDrawWireframe(true);

        // Define Camera Position Relative to Robot
        Transform3d robotToCamera = new Transform3d(
            new Translation3d(0.1, 0, 0.5),
            new Rotation3d(0, Math.toRadians(-15), 0)
        );
        
        // Add Camera to Vision System Simulation
        visionSim.addCamera(cameraSim, robotToCamera);

        // Load AprilTag Field Layout with Exception Handling
        try {
            AprilTagFieldLayout tagLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2025ReefscapeWelded.m_resourceFile);
            visionSim.addAprilTags(tagLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a Custom Vision Target
        TargetModel targetModel = new TargetModel(0.5, 0.25); // A 0.5m x 0.25m rectangular target
        Pose3d targetPose = new Pose3d(16, 4, 2, new Rotation3d(0, 0, Math.PI)); // Positioned against the far wall
        VisionTargetSim visionTarget = new VisionTargetSim(targetPose, targetModel);
        visionSim.addVisionTargets(visionTarget);
        
        // Get Debug Field for Visualization
        debugField = visionSim.getDebugField();
    }

    public void update(Pose2d robotPose) {
        // Update the Vision System Simulation with Robot Pose
        visionSim.update(robotPose);
    }

    public Field2d getDebugField() {
        return debugField;
    }
}