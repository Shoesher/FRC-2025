// package frc.robot.subsystem; 

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import java.util.List;
// import org.photonvision.PhotonCamera;
// import org.photonvision.PhotonUtils;
// import org.photonvision.targeting.PhotonPipelineResult;
// import org.photonvision.targeting.PhotonTrackedTarget;
// import org.photonvision.targeting.TargetCorner;
// import edu.wpi.first.math.geometry.Pose3d;
// import edu.wpi.first.math.geometry.Transform2d;
// import edu.wpi.first.math.geometry.Transform3d;
// import edu.wpi.first.apriltag.AprilTagFieldLayout;
// import java.util.Optional;


// public class vision extends SubsystemBase {
//     private final PhotonCamera camera;
//     private PhotonPipelineResult latestResult;
//     private final AprilTagFieldLayout aprilTagFieldLayout;
//     private final Transform3d cameraToRobot;

//     public vision(AprilTagFieldLayout fieldLayout, Transform3d cameraToRobot) {
//         camera = new PhotonCamera("photonvision");
//         this.aprilTagFieldLayout = fieldLayout;
//         this.cameraToRobot = cameraToRobot;
//     }

//     @Override
//     public void periodic() {
//         latestResult = camera.getLatestResult();

//         if (!latestResult.hasTargets()) {
//             return; // No targets, exit early to prevent null issues
//         }

//         List<PhotonTrackedTarget> targets = latestResult.getTargets();
//         PhotonTrackedTarget target = latestResult.getBestTarget();

//         if (target == null) {
//             return; // No valid best target
//         }

//         // Get information from target.
//         double yaw = target.getYaw();
//         double pitch = target.getPitch();
//         double area = target.getArea();
//         double skew = target.getSkew();
        
//         // Get additional target information.
//         int targetID = target.getFiducialId();
//         double poseAmbiguity = target.getPoseAmbiguity();
//         Transform3d bestCameraToTarget = target.getBestCameraToTarget();
//         Transform3d alternateCameraToTarget = target.getAlternateCameraToTarget();

//         // Calculate robot's field-relative pose
//         Optional<Pose3d> tagPose = aprilTagFieldLayout.getTagPose(targetID);
//         if (tagPose.isPresent()) {
//             Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(bestCameraToTarget, tagPose.get(), cameraToRobot);
//         }
//     }

//     public boolean hasTargets() {
//         return latestResult != null && latestResult.hasTargets();
//     }

//     public PhotonTrackedTarget getBestTarget() {
//         return hasTargets() ? latestResult.getBestTarget() : null;
//     }
// }
