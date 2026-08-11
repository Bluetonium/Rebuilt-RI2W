package frc.utils.sim;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import lombok.Getter;
import lombok.Setter;

public class LinearConfig {
    @Getter
    private int m_numMotors = 1;
    @Getter
    private double m_elevatorGearing = 5;
    @Getter
    private double m_carriageMassKg = 1;
    @Getter
    private double m_drumRadius = Units.inchesToMeters(0.955 / 2);
    @Getter
    private double m_minHeight = 0;

    @Getter
    private double m_maxHeight = 10000; // Units.inchesToMeters(Robot.config.elevator.maxHeight);

    // Display Config
    @Getter
    private double m_angle = 90; // O is horizontal, 90 is vertical, CCW is
                                 // positive
    @Getter
    private Color8Bit m_color = new Color8Bit(Color.kPurple);
    @Getter
    private double m_lineWidth = 10;
    @Getter
    private double m_initialX = 0.5;
    @Getter
    private double m_initialY = 0;
    @Getter @Setter
    private double m_staticRootX = 0.5;
    @Getter @Setter
    private double m_staticRootY = 0;
    @Getter
    private double m_staticLength = 20;
    @Getter
    private double m_movingLength = 20;
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

    public LinearConfig(double x, double y, double gearing, double drumRadius) {
        this.m_initialX = x;
        this.m_initialY = y;
        m_staticRootX = m_initialX;
        m_staticRootY = m_initialY;
        m_elevatorGearing = gearing;
        this.m_drumRadius = drumRadius;
    }

    public LinearConfig setM_numMotors(int numMotors) {
        this.m_numMotors = numMotors;
        return this;
    }

    public LinearConfig setCarriageMass(double carriageMassKg) {
        this.m_carriageMassKg = carriageMassKg;
        return this;
    }

    public LinearConfig setM_angle(double angle) {
        this.m_angle = angle;
        return this;
    }

    public LinearConfig setM_color(Color8Bit color) {
        this.m_color = color;
        return this;
    }

    public LinearConfig setM_lineWidth(double lineWidth) {
        this.m_lineWidth = lineWidth;
        return this;
    }

    public LinearConfig setM_staticLength(double lengthInches) {
        this.m_staticLength = Units.inchesToMeters(lengthInches);
        ;
        return this;
    }

    public LinearConfig setM_movingLength(double lengthInches) {
        this.m_movingLength = Units.inchesToMeters(lengthInches);
        return this;
    }

    public LinearConfig setM_maxHeight(double lengthInches) {
        this.m_maxHeight = Units.inchesToMeters(lengthInches);
        return this;
    }

    public LinearConfig setMount(LinearSim sim) {
        if (sim != null) {
            m_mounted = true;
            m_mount = sim;
            m_initMountX = sim.getConfig().getInitialX();
            m_initMountY = sim.getConfig().getInitialY();
            m_initMountAngle = Math.toRadians(sim.getConfig().getAngle());
        }

        return this;
    }

    public LinearConfig setMount(ArmSim sim) {
        if (sim != null) {
            m_mounted = true;
            m_mount = sim;
            m_initMountX = sim.getConfig().getInitialX();
            m_initMountY = sim.getConfig().getInitialY();
            m_initMountAngle = sim.getConfig().getStartingAngle();
        }

        return this;
    }
}
