package frc.utils.sim;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import lombok.Getter;

public class RollerConfig {
    @Getter
    private double m_rollerDiameterInches = 2;
    @Getter
    private int m_backgroundLines = 36;
    @Getter
    private double m_gearRatio = 5;
    @Getter
    private double m_simMOI = 0.01;
    @Getter
    private Color8Bit m_offColor = new Color8Bit(Color.kBlack);
    @Getter
    private Color8Bit m_fwdColor = new Color8Bit(Color.kGreen);
    @Getter
    private Color8Bit m_revColor = new Color8Bit(Color.kRed);
    @Getter
    private double m_initialX = 0;
    @Getter
    private double m_initialY = 0;
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

    public RollerConfig(double diameterInches) {
        m_rollerDiameterInches = diameterInches;
    }

    public RollerConfig setM_gearRatio(double ratio) {
        m_gearRatio = ratio;
        return this;
    }

    public RollerConfig setM_simMOI(double moi) {
        m_simMOI = moi;
        return this;
    }

    public RollerConfig setPosition(double x, double y) {
        m_initialX = x;
        m_initialY = y;
        return this;
    }

    public RollerConfig setMount(LinearSim sim) {
        if (sim != null) {
            m_mounted = true;
            m_mount = sim;
            m_initMountX = sim.getConfig().getInitialX();
            m_initMountY = sim.getConfig().getInitialY();
            m_initMountAngle = Math.toRadians(sim.getConfig().getAngle());
        }

        return this;
    }

    public RollerConfig setMount(ArmSim sim) {
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
