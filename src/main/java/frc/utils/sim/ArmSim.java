package frc.utils.sim;

import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import lombok.Getter;

public class ArmSim implements Mount, Mountable {
    private SingleJointedArmSim m_armSim;
    @Getter
    private ArmConfig m_config;

    private MechanismRoot2d m_armPivot;
    private MechanismLigament2d m_armMech2d;
    private TalonFXSimState m_armMotorSim;

    private final MountType kMountType = MountType.kArm;

    public ArmSim(ArmConfig config, Mechanism2d mech, TalonFXSimState armMotorSim, String name) {
        this.m_config = config;
        this.m_armMotorSim = armMotorSim;
        m_armSim = new SingleJointedArmSim(DCMotor.getKrakenX60Foc(config.getNumMotors()), config.getRatio(),
        config.getSimMOI(), config.getSimCGLength(), config.getMinAngle(), config.getMaxAngle(), false, // Simulate
                                                                                                        // gravity
                                                                                                        // (change
                                                                                                        // back
                                                                                                        // to
                                                                                                        // true)
        config.getStartingAngle());

        m_armPivot = mech.getRoot(name + " Arm Pivot", config.getPivotX(), config.getPivotY());
        m_armMech2d = m_armPivot.append(
        new MechanismLigament2d(name + " Arm", config.getLength(), config.getMinAngle(), 5.0, config.getColor()));
    }

    @Override
    public MountType getMountType() {
        return kMountType;
    }

    public void simulationPeriodic() {
        // armMotorSim.setSupplyVoltage(RobotController.getBatteryVoltage());
        m_armSim.setInput(m_armMotorSim.getMotorVoltage());
        m_armSim.update(TimedRobot.kDefaultPeriod);

        // armMotorSim.setRawRotorPosition(
        // (armSim.getAngleRads() - config.getStartingAngle())
        // * config.getRatio()
        // / (2.0 * Math.PI));

        // armMotorSim.setRotorVelocity(
        // armSim.getVelocityRadPerSec() * config.getRatio() / (2.0 * Math.PI));
        m_armMotorSim.setRawRotorPosition(
        (Units.radiansToRotations(m_armSim.getAngleRads() - m_config.getStartingAngle())) * m_config.getRatio());

        m_armMotorSim.setRotorVelocity(Units.radiansToRotations(m_armSim.getVelocityRadPerSec()) * m_config.getRatio());

        // ------ Update viz based on sim
        if (m_config.isMounted()) {
            m_config.setPivotX(getUpdatedX(m_config));
            m_config.setPivotY(getUpdatedY(m_config));
            if (m_config.isAbsAngle()) {
                m_armMech2d.setAngle(Math.toDegrees(m_armSim.getAngleRads()));
            } else {
                m_armMech2d
                .setAngle(Math.toDegrees(m_armSim.getAngleRads()) + Math.toDegrees(m_config.getMount().getAngle()));
            }
        } else {
            m_armMech2d.setAngle(Math.toDegrees(m_armSim.getAngleRads()));
        }

        m_armPivot.setPosition(m_config.getPivotX(), m_config.getPivotY());
    }

    public double getAngleRads() {
        return m_armSim.getAngleRads();
    }

    @Override
    public double getDisplacementX() {
        return m_config.getPivotX() - m_config.getInitialX();
    }

    @Override
    public double getDisplacementY() {
        return m_config.getPivotY() - m_config.getInitialY();
    }

    public double getAngle() {
        if (m_config.isMounted()) {
            if (m_config.isAbsAngle()) {
                return getAngleRads(); // + config.getMount().getAngle();
            } else {
                return getAngleRads() + m_config.getMount().getAngle();
            }
        }
        return getAngleRads();
    }

    @Override
    public double getMountX() {
        return m_config.getPivotX();
    }

    @Override
    public double getMountY() {
        return m_config.getPivotY();
    }
}
