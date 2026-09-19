package frc.robot.subsystems.blocker;

//import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class Blocker extends SubsystemBase {

    private TalonFX m_blockerMotor;

    private final VoltageOut m_blockerMotor_SysIdControl = new VoltageOut(0);

    private TalonFXConfiguration m_blockerMotorConfig;

    private MotionMagicVoltage m_blockerMotorPositionVoltage = new MotionMagicVoltage(0).withSlot(0);

    private final SysIdRoutine m_blockerMotor_SysIdRoutine = new SysIdRoutine(
            new SysIdRoutine.Config(
                    null,
                    Volts.of(4),
                    null,
                    (state) -> SignalLogger.writeString("SysIdShooter", state.toString())),
            new SysIdRoutine.Mechanism(
                    (volts) -> m_blockerMotor.setControl(
                            m_blockerMotor_SysIdControl.withOutput(volts.in(Volts))),
                    null,
                    this));

    public Blocker() {
        m_blockerMotor = new TalonFX(BlockerConstants.BLOCKER_MOTOR.ID);
        m_blockerMotor.setNeutralMode(BlockerConstants.BLOCKER_MOTOR.NEUTRAL_MODE);
        m_blockerMotorConfig = new TalonFXConfiguration();

        m_blockerMotorConfig.MotorOutput.Inverted = BlockerConstants.BLOCKER_MOTOR.INVERTED_VALUE;
        m_blockerMotorConfig.CurrentLimits = BlockerConstants.BLOCKER_MOTOR.CURRENT_LIMITS;

        Slot0Configs slot0_blocker = m_blockerMotorConfig.Slot0;
        slot0_blocker.kP = BlockerConstants.BLOCKER_MOTOR.kP;
        slot0_blocker.kI = BlockerConstants.BLOCKER_MOTOR.kI;
        slot0_blocker.kD = BlockerConstants.BLOCKER_MOTOR.kD;
        slot0_blocker.kS = BlockerConstants.BLOCKER_MOTOR.kS;
        slot0_blocker.kV = BlockerConstants.BLOCKER_MOTOR.kV;
        slot0_blocker.kA = BlockerConstants.BLOCKER_MOTOR.kA;

        // SubsystemTesting.registerSysIdTests(m_intakeMotor_SysIdRoutine, "Intake
        // Motor");

        applyConfig();
    }

    public void setup() {
        setDefaultCommand(run(() -> {
            m_blockerMotor.setControl(
                    m_blockerMotorPositionVoltage.withPosition(BlockerConstants.BLOCKER_MOTOR.MAX_POSITION));
        }));

        BlockerStates.setupStates();
    }

    public Command openBlocker() {
        return run(() -> {
            if (blockerIsUp()) {
                m_blockerMotor.setControl(
                        m_blockerMotorPositionVoltage.withPosition(BlockerConstants.BLOCKER_MOTOR.MIN_POSITION));
            } else {
                m_blockerMotor.setControl(
                        m_blockerMotorPositionVoltage.withPosition(BlockerConstants.BLOCKER_MOTOR.MAX_POSITION));
            }
        });
    }

    public void applyConfig() {
        StatusCode blocker_status = m_blockerMotor.getConfigurator().apply(m_blockerMotorConfig);
        if (!blocker_status.isOK())
            DriverStation.reportWarning(blocker_status.getName() + "Failed to apply configs to blocker"
                    + blocker_status.getDescription(), false);

    }

    public boolean blockerIsUp() {
        return (m_blockerMotor.getPosition().getValueAsDouble() >= BlockerConstants.BLOCKER_MOTOR.MAX_POSITION / 2);
    }
}
