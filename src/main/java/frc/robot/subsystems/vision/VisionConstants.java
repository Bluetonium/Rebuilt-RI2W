package frc.robot.subsystems.vision;

public class VisionConstants {
    /**
     * This is where all your limelights should go. The name should match the
     * name configured on the limelight LIMELIGHTS
     */
    public enum LIMELIGHTS {
        // EXAMPLE("example", 1, 1, 1, 0, 0, 0);
        ;

        public final String m_name;
        public final double m_x;
        public final double m_y;
        public final double m_z;
        public final double m_pitch;
        public final double m_yaw;
        public final double m_roll;

        public final boolean m_localization;

        private LIMELIGHTS(String name, double x, double y, double z, double pitch, double yaw, double roll,
        boolean localization) {
            m_name = name;
            m_x = x;
            m_y = y;
            m_z = z;

            m_pitch = pitch;
            m_yaw = yaw;
            m_roll = roll;
            m_localization = localization; // has a position, can be used for
                                           // localization
        }

        private LIMELIGHTS(String name, double x, double y, double z, double pitch, double yaw, double roll) {
            this(name, x, y, z, pitch, yaw, roll, true);
        }
    }

    /***
     * All the pipelines that are on the limelights. it is assumed that all
     * limelights have the same set of pipelines on them. LIMELIGHT_PIPELINES
     */
    public enum LIMELIGHT_PIPELINES {
        m_localization(0);

        public final int m_pipeline;

        private LIMELIGHT_PIPELINES(int pipeline) {
            this.m_pipeline = pipeline;
        }
    }

    private VisionConstants() {
    } // hide constructor
}
