package frc.robot.subsystems.shooter;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class Shooter extends SubsystemBase {
    private TalonFX m_shooterMotor;
    private TalonFX m_shooterMotorFollower;
    private TalonFX m_feederMotor;
    private TalonFX m_indexerMotor;

    private final VoltageOut m_shooterMotor_SysIdControl = new VoltageOut(0);
    private final VoltageOut m_shooterMotorFollower_SysIdControl = new VoltageOut(0);
    private final VoltageOut m_feederMotor_SysIdControl = new VoltageOut(0);
    private final VoltageOut m_indexerMotor_SysIdControl = new VoltageOut(0);

    private TalonFXConfiguration m_shooterMotorConfig;
    private TalonFXConfiguration m_shooterMotorFollowerConfig;
    private TalonFXConfiguration m_feederMotorConfig;
    private TalonFXConfiguration m_indexerMotorConfig;

    private MotionMagicVelocityVoltage m_shooterMotorVelocityVoltage = new MotionMagicVelocityVoltage(0)
            .withAcceleration(ShooterConstants.SHOOTER_MOTOR_ACCELERATION);

    // kaiden left off here 8/26/26

    private final SysIdRoutine m_shooterMotor_SysIdRoutine = new SysIdRoutine(
            new SysIdRoutine.Config(
                    null,
                    Volts.of(4),
                    null,
                    (state) -> SignalLogger.writeString("SysIdShooter", state.toString())),
            new SysIdRoutine.Mechanism(
                    (volts) -> m_shooterMotor.setControl(m_shooterMotor_SysIdControl.withOutput(volts.in(Volts))),
                    null,
                    this));

    private final SysIdRoutine m_shooterMotorFollower_SysIdRoutine = new SysIdRoutine(
            new SysIdRoutine.Config(
                    null,
                    Volts.of(4),
                    null,
                    (state) -> SignalLogger.writeString("SysIdShooterFollower", state.toString())),
            new SysIdRoutine.Mechanism(
                    (volts) -> m_shooterMotorFollower
                            .setControl(m_shooterMotorFollower_SysIdControl.withOutput(volts.in(Volts))),
                    null,
                    this));

    private final SysIdRoutine m_feederMotor_SysIdRoutine = new SysIdRoutine(
            new SysIdRoutine.Config(
                    null,
                    Volts.of(4),
                    null,
                    (state) -> SignalLogger.writeString("SysIdFeeder", state.toString())),
            new SysIdRoutine.Mechanism(
                    (volts) -> m_feederMotor.setControl(m_feederMotor_SysIdControl.withOutput(volts.in(Volts))),
                    null,
                    this));

    private final SysIdRoutine m_indexerMotor_SysIdRoutine = new SysIdRoutine(
            new SysIdRoutine.Config(
                    null,
                    Volts.of(4),
                    null,
                    (state) -> SignalLogger.writeString("SysIdIndexer", state.toString())),
            new SysIdRoutine.Mechanism(
                    (volts) -> m_indexerMotor.setControl(m_indexerMotor_SysIdControl.withOutput(volts.in(Volts))),
                    null,
                    this));

    public Shooter() {
        m_shooterMotor = new TalonFX(ShooterConstants.SHOOTER_MOTOR_ID);
        m_shooterMotor.setNeutralMode(ShooterConstants.SHOOTER_MOTOR_NEUTRAL_MODE);

        m_shooterMotorConfig = new TalonFXConfiguration();

        m_shooterMotorFollower = new TalonFX(ShooterConstants.SHOOTER_MOTOR_FOLLOWER_ID);
        m_shooterMotorFollower.setNeutralMode(ShooterConstants.SHOOTER_MOTOR_FOLLOWER_NEUTRAL_MODE);

        m_feederMotor = new TalonFX(ShooterConstants.FEEDER_MOTOR_ID);
        m_feederMotor.setNeutralMode(ShooterConstants.FEEDER_MOTOR_NEUTRAL_MODE);

        m_indexerMotor = new TalonFX(ShooterConstants.INDEXER_MOTOR_ID);
        m_indexerMotor.setNeutralMode(ShooterConstants.INDEXER_MOTOR_NEUTRAL_MODE);

    }

    // public void setup() {
    // setDefaultCommand(run(() -> {
    // m_shooterMotor.setControl(m_shooterMotorVelocityVoltage.withVelocity(0));
    // }).withName("Shooter Stopped");
    // }

    // ---------------------------------------Commands-----------------------------------------------------------------

    // shooter motor sys id routines
    public Command m_shooterMotor_SysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_shooterMotor_SysIdRoutine.quasistatic(direction);
    }

    public Command m_shooterMotor_SysIdDynamic(SysIdRoutine.Direction direction) {
        return m_shooterMotor_SysIdRoutine.dynamic(direction);
    }

    // shooter follower motor sys id routines
    public Command m_shooterMotorFollower_SysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_shooterMotorFollower_SysIdRoutine.quasistatic(direction);
    }

    public Command m_shooterMotorFollower_SysIdDynamic(SysIdRoutine.Direction direction) {
        return m_shooterMotorFollower_SysIdRoutine.dynamic(direction);
    }

    // feeder motor sys id routines
    public Command m_feederMotor_SysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_feederMotor_SysIdRoutine.quasistatic(direction);
    }

    public Command m_feederMotor_SysIdDynamic(SysIdRoutine.Direction direction) {
        return m_feederMotor_SysIdRoutine.dynamic(direction);
    }

    // indexer motor sys id routines
    public Command m_indexMotor_SysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_indexerMotor_SysIdRoutine.quasistatic(direction);
    }

    public Command m_indexerMotor_SysIdDynamic(SysIdRoutine.Direction direction) {
        return m_indexerMotor_SysIdRoutine.dynamic(direction);
    }
}
