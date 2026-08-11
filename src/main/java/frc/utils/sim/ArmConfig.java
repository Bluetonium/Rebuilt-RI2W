package frc.utils.sim;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import lombok.Getter;
import lombok.Setter;

public class ArmConfig {

    @Getter @Setter
    private int m_numMotors = 1;
    @Getter @Setter
    private double m_initialX = 0.7;
    @Getter @Setter
    private double m_initialY = 0.3;
    @Getter @Setter
    private double m_pivotX = 0.7;
    @Getter @Setter
    private double m_pivotY = 0.3;

    @Getter @Setter
    private double m_ratio = 50; // the number of rotations it takes for the
                                 // mechanism to do one revolution

    @Getter @Setter
    private double m_length = 0.5;
    @Getter @Setter
    private double m_simMOI = 1.2;
    @Getter @Setter
    private double m_simCGLength = 0.2;
    @Getter @Setter
    private double m_minAngle = Math.toRadians(-60);
    @Getter @Setter
    private double m_maxAngle = Math.toRadians(90);
    @Getter @Setter
    private double m_startingAngle = Math.toRadians(90);
    @Getter @Setter
    private boolean m_simulateGravity = true;
    @Getter
    private boolean m_mounted = false;
    @Getter
    private Mount m_mount;
    @Getter
    private double m_initMountX;
    @Getter
    private double m_initMountY;
    @Getter
    private double m_initMountAngle;
    @Getter
    private boolean m_absAngle;
    @Getter
    private Color8Bit m_color = new Color8Bit(Color.kBlue);

    public ArmConfig(double initialX, double initialY, double ratio, double length, double minAngleDegrees,
    double maxAngleDegrees, double startingAngleDegrees) {
        this.m_ratio = ratio;
        this.m_length = length;
        this.m_minAngle = Math.toRadians(minAngleDegrees);
        this.m_maxAngle = Math.toRadians(maxAngleDegrees);
        this.m_startingAngle = Math.toRadians(startingAngleDegrees);
        this.m_initialX = initialX;
        this.m_initialY = initialY;
        this.m_pivotX = initialX;
        this.m_pivotY = initialY;
    }

    public ArmConfig setM_color(Color8Bit color) {
        this.m_color = color;
        return this;
    }

    public ArmConfig setMount(LinearSim sim, boolean fixedAngle) {
        if (sim != null) {
            m_mounted = true;
            m_mount = sim;
            m_initMountX = sim.getConfig().getInitialX();
            m_initMountY = sim.getConfig().getInitialY();
            m_initMountAngle = Math.toRadians(sim.getConfig().getAngle());
            this.m_absAngle = fixedAngle;
        }
        return this;
    }

    public ArmConfig setMount(ArmSim sim, boolean absAngle) {
        if (sim != null) {
            m_mounted = true;
            m_mount = sim;
            m_initMountX = sim.getConfig().getInitialX();
            m_initMountY = sim.getConfig().getInitialY();
            m_initMountAngle = sim.getConfig().getStartingAngle();
            this.m_absAngle = absAngle;
        }
        return this;
    }
}
