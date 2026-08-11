package frc.robot.subsystems.vision;

import java.util.HashSet;
import java.util.Iterator;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.vision.VisionConstants.LIMELIGHTS;
import frc.robot.subsystems.vision.VisionConstants.LIMELIGHT_PIPELINES;

public class Vision extends SubsystemBase {
    private final HashSet<LIMELIGHTS> m_localization_limelights = new HashSet<>();
    private CommandSwerveDrivetrain m_drivetrain;
    private Pigeon2 m_gyro;

    public Vision() {
        for (LIMELIGHTS limelight : LIMELIGHTS.values()) {
            setLimelightPos(limelight);
        }

        // SendableRegistry.add(this, "Outtake");
        SmartDashboard.putData(this);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Vision");
        builder.addStringArrayProperty("Localization", this::listLocalizationLimelights, null);
    }

    /**
     * A command that will set all localization limelights to the localization
     * pipeline then does nothing.
     * 
     * @return The command
     */
    public Command SetAllLocalization() {
        return runOnce(() -> {
            for (LIMELIGHTS limelight : LIMELIGHTS.values()) {
                setPipeline(limelight, LIMELIGHT_PIPELINES.m_localization);
            }
        }).andThen(Commands.idle()).withName("Vision.Localization").ignoringDisable(true);
    }

    /**
     * Sets the pipeline on the limelight
     * 
     * @param limelight the limelight to operate on
     * @param pipeline the pipeline to change it to
     */
    public void setPipeline(LIMELIGHTS limelight, LIMELIGHT_PIPELINES pipeline) {
        LimelightHelpers.setPipelineIndex(limelight.m_name, pipeline.m_pipeline);

        if (limelight.m_localization)
            if (pipeline == LIMELIGHT_PIPELINES.m_localization)
                m_localization_limelights.add(limelight);
            else
                m_localization_limelights.remove(limelight);
    }

    @Override
    public void periodic() {
        for (LIMELIGHTS limelight : m_localization_limelights) {
            localizationMeasurement(limelight);
        }
    }

    public void setup() {
        m_drivetrain = RobotContainer.getDrivetrain();
        m_gyro = m_drivetrain.getPigeon2();

        VisionStates.setStates();
    }

    private String[] listLocalizationLimelights() {
        String[] names = new String[m_localization_limelights.size()];
        int i = 0;
        Iterator<LIMELIGHTS> iter = m_localization_limelights.iterator();
        while (iter.hasNext()) {
            names[i++] = iter.next().m_name;
        }

        return names;

    }

    private void setLimelightPos(LIMELIGHTS limelight) {
        LimelightHelpers.setCameraPose_RobotSpace(limelight.m_name, limelight.m_x, limelight.m_y, limelight.m_z,
        limelight.m_roll, limelight.m_pitch, limelight.m_yaw);

    }

    private void localizationMeasurement(LIMELIGHTS limelight) {
        LimelightHelpers.SetRobotOrientation(limelight.m_name, m_drivetrain.getState().Pose.getRotation().getDegrees(),
        m_gyro.getAngularVelocityZWorld().getValueAsDouble(), 0, 0, 0, 0);

        LimelightHelpers.PoseEstimate estimatedPosition = LimelightHelpers
        .getBotPoseEstimate_wpiBlue_MegaTag2(limelight.m_name);

        if (estimatedPosition == null || estimatedPosition.tagCount == 0
        || Math.abs(m_gyro.getAngularVelocityZWorld().getValueAsDouble()) > 720)
            return;// reject measurements if not seeing a tag or going too fast

        double timeStamp = m_drivetrain.getState().Timestamp - estimatedPosition.latency / 1000;
        m_drivetrain.addVisionMeasurement(estimatedPosition.pose, timeStamp);

    }
}
