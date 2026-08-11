package frc.utils.sim;

import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import lombok.Getter;

public class LinearSim implements Mount, Mountable {
    private ElevatorSim m_elevatorSim;

    private final MechanismRoot2d m_staticRoot;
    private final MechanismRoot2d m_root;
    private final MechanismLigament2d staticMech2d;
    private final MechanismLigament2d m_elevatorMech2d;
    @Getter
    private LinearConfig m_config;
    private TalonFXSimState m_linearMotorSim;

    private final MountType kMountType = MountType.kLinear;

    public LinearSim(LinearConfig config, Mechanism2d mech, TalonFXSimState linearMotorSim, String name) {
        this.m_config = config;
        this.m_linearMotorSim = linearMotorSim;

        this.m_elevatorSim = new ElevatorSim(DCMotor.getKrakenX60Foc(config.getNumMotors()),
        config.getElevatorGearing(), config.getCarriageMassKg(), config.getDrumRadius(), config.getMinHeight(),
        config.getMaxHeight(), true, 0);

        m_staticRoot = mech.getRoot(name + " 1StaticRoot", config.getInitialX(), config.getInitialY());
        staticMech2d = m_staticRoot.append(new MechanismLigament2d(name + " 1Static", config.getStaticLength(),
        config.getAngle(), config.getLineWidth(), new Color8Bit(Color.kOrange)));

        m_root = mech.getRoot(name + " Root", config.getInitialX(), config.getInitialY());
        m_elevatorMech2d = m_root.append(new MechanismLigament2d(name, config.getMovingLength(), config.getAngle(),
        config.getLineWidth(), new Color8Bit(Color.kBlack)));
    }

    @Override
    public MountType getMountType() {
        return kMountType;
    }

    public MechanismLigament2d getElevatorMech2d() {
        return m_elevatorMech2d;
    }

    public void simulationPeriodic() {
        m_elevatorSim.setInput(m_linearMotorSim.getMotorVoltage());
        m_elevatorSim.update(TimedRobot.kDefaultPeriod);

        m_linearMotorSim.setRotorVelocity(getRotationPerSec());
        m_linearMotorSim.setRawRotorPosition(getRotations());

        double displacement = m_elevatorSim.getPositionMeters();

        if (m_config.isMounted()) {
            double angle;

            if (m_config.getMount().getMountType() == MountType.kArm) {
                angle = m_config.getAngle() + Math.toDegrees(m_config.getMount().getAngle());
            } else if (m_config.getMount().getMountType() == MountType.kLinear) {
                angle = m_config.getAngle()
                + Math.toDegrees(m_config.getMount().getAngle() - m_config.getInitMountAngle());
            } else {
                angle = m_config.getAngle();
            }

            m_config.setStaticRootX(getUpdatedX(m_config));
            m_config.setStaticRootY(getUpdatedY(m_config));

            m_staticRoot.setPosition(m_config.getStaticRootX(), m_config.getStaticRootY());
            m_root.setPosition(m_config.getStaticRootX() + (displacement * Math.cos(Math.toRadians(angle))),
            m_config.getStaticRootY() + (displacement * Math.sin(Math.toRadians(angle))));

            staticMech2d.setAngle(angle);
            m_elevatorMech2d.setAngle(angle);

        } else {
            m_root.setPosition(m_config.getInitialX() + (displacement * Math.cos(Math.toRadians(m_config.getAngle()))),
            m_config.getInitialY() + (displacement * Math.sin(Math.toRadians(m_config.getAngle()))));
        }
    }

    @Override
    public double getDisplacementX() {
        double angle;

        if (!m_config.isMounted()) {
            angle = m_config.getAngle();
        } else if (m_config.getMount().getMountType() == MountType.kArm) {
            angle = m_config.getAngle() + Math.toDegrees(m_config.getMount().getAngle());
        } else if (m_config.getMount().getMountType() == MountType.kLinear) {
            angle = m_config.getAngle() + Math.toDegrees(m_config.getMount().getAngle() - m_config.getInitMountAngle());
        } else {
            angle = m_config.getAngle();
        }

        return m_elevatorSim.getPositionMeters() * Math.cos(Math.toRadians(angle))
        + (m_config.getStaticRootX() - m_config.getInitialX());
    }

    @Override
    public double getDisplacementY() {
        double angle;

        if (!m_config.isMounted()) {
            angle = m_config.getAngle();
        } else if (m_config.getMount().getMountType() == MountType.kArm) {
            angle = m_config.getAngle() + Math.toDegrees(m_config.getMount().getAngle());
        } else if (m_config.getMount().getMountType() == MountType.kLinear) {
            angle = m_config.getAngle() + Math.toDegrees(m_config.getMount().getAngle() - m_config.getInitMountAngle());
        } else {
            angle = m_config.getAngle();
        }

        return m_elevatorSim.getPositionMeters() * Math.sin(Math.toRadians(angle))
        + (m_config.getStaticRootY() - m_config.getInitialY());
    }

    @Override
    public double getAngle() {
        if (m_config.isMounted()) {
            return m_config.getMount().getAngle() + Math.toRadians(m_config.getAngle());
        } else {
            return Math.toRadians(m_config.getAngle());
        }
    }

    @Override
    public double getMountX() {
        return m_config.getStaticRootX();
    }

    @Override
    public double getMountY() {
        return m_config.getStaticRootY();
    }

    private double getRotationPerSec() {
        return (m_elevatorSim.getVelocityMetersPerSecond() / (2 * Math.PI * m_config.getDrumRadius()))
        * m_config.getElevatorGearing();
    }

    private double getRotations() {
        return (m_elevatorSim.getPositionMeters() / (2 * Math.PI * m_config.getDrumRadius()))
        * m_config.getElevatorGearing();
    }
}
