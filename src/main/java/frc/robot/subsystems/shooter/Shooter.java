package frc.robot.subsystems.shooter;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
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
                        .withAcceleration(ShooterConstants.SHOOTER_MOTOR.ACCELERATION);

        private MotionMagicVelocityVoltage m_shooterMotorFollowerVelocityVoltage = new MotionMagicVelocityVoltage(0)
                        .withAcceleration(ShooterConstants.SHOOTER_MOTOR_FOLLOWER.ACCELERATION);

        private MotionMagicVelocityVoltage m_feederMotorVelocityVoltage = new MotionMagicVelocityVoltage(0)
                        .withAcceleration(ShooterConstants.FEEDER_MOTOR.ACCELERATION);

        private MotionMagicVelocityVoltage m_indexerMotorVelocityVoltage = new MotionMagicVelocityVoltage(0)
                        .withAcceleration(ShooterConstants.INDEXER_MOTOR.ACCELERATION);

        // TODO Do we really need one of these for each motor? That seems like a lot -
        // genuine question, KD
        // that we didn't have last year right? - KD
        private final SysIdRoutine m_shooterMotor_SysIdRoutine = new SysIdRoutine(
                        new SysIdRoutine.Config(
                                        null,
                                        Volts.of(4),
                                        null,
                                        (state) -> SignalLogger.writeString("SysIdShooter", state.toString())),
                        new SysIdRoutine.Mechanism(
                                        (volts) -> m_shooterMotor.setControl(
                                                        m_shooterMotor_SysIdControl.withOutput(volts.in(Volts))),
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
                                                        .setControl(m_shooterMotorFollower_SysIdControl
                                                                        .withOutput(volts.in(Volts))),
                                        null,
                                        this));

        private final SysIdRoutine m_feederMotor_SysIdRoutine = new SysIdRoutine(
                        new SysIdRoutine.Config(
                                        null,
                                        Volts.of(4),
                                        null,
                                        (state) -> SignalLogger.writeString("SysIdFeeder", state.toString())),
                        new SysIdRoutine.Mechanism(
                                        (volts) -> m_feederMotor.setControl(
                                                        m_feederMotor_SysIdControl.withOutput(volts.in(Volts))),
                                        null,
                                        this));

        private final SysIdRoutine m_indexerMotor_SysIdRoutine = new SysIdRoutine(
                        new SysIdRoutine.Config(
                                        null,
                                        Volts.of(4),
                                        null,
                                        (state) -> SignalLogger.writeString("SysIdIndexer", state.toString())),
                        new SysIdRoutine.Mechanism(
                                        (volts) -> m_indexerMotor.setControl(
                                                        m_indexerMotor_SysIdControl.withOutput(volts.in(Volts))),
                                        null,
                                        this));

        public Shooter() {
                m_shooterMotor = new TalonFX(ShooterConstants.SHOOTER_MOTOR.ID);
                m_shooterMotor.setNeutralMode(ShooterConstants.SHOOTER_MOTOR.NEUTRAL_MODE);

                m_shooterMotorConfig = new TalonFXConfiguration();
                // TODO fix these two lines need to be accurate, and add them for 3 other motors
                // (shouldn't be urgent) KD
                m_shooterMotorConfig.MotorOutput.Inverted = ShooterConstants.SHOOTER_MOTOR.INVERTED_VALUE;
                m_shooterMotorConfig.CurrentLimits = ShooterConstants.SHOOTER_MOTOR.CURRENT_LIMITS;

                Slot0Configs slot0_shooter = m_shooterMotorConfig.Slot0;
                slot0_shooter.kP = ShooterConstants.SHOOTER_MOTOR.kP;
                slot0_shooter.kI = ShooterConstants.SHOOTER_MOTOR.kI;
                slot0_shooter.kD = ShooterConstants.SHOOTER_MOTOR.kD;
                slot0_shooter.kS = ShooterConstants.SHOOTER_MOTOR.kS;
                slot0_shooter.kV = ShooterConstants.SHOOTER_MOTOR.kV;
                slot0_shooter.kA = ShooterConstants.SHOOTER_MOTOR.kA;

                m_shooterMotorFollower = new TalonFX(ShooterConstants.SHOOTER_MOTOR_FOLLOWER.ID);
                m_shooterMotorFollower.setNeutralMode(ShooterConstants.SHOOTER_MOTOR_FOLLOWER.NEUTRAL_MODE);
                m_shooterMotorFollowerConfig = new TalonFXConfiguration();
                m_shooterMotorFollowerConfig.MotorOutput.Inverted = ShooterConstants.SHOOTER_MOTOR_FOLLOWER.INVERTED_VALUE;
                m_shooterMotorFollowerConfig.CurrentLimits = ShooterConstants.SHOOTER_MOTOR_FOLLOWER.CURRENT_LIMITS;

                Slot0Configs slot0_shooterFollower = m_shooterMotorFollowerConfig.Slot0;
                slot0_shooterFollower.kP = ShooterConstants.SHOOTER_MOTOR_FOLLOWER.kP;
                slot0_shooterFollower.kI = ShooterConstants.SHOOTER_MOTOR_FOLLOWER.kI;
                slot0_shooterFollower.kD = ShooterConstants.SHOOTER_MOTOR_FOLLOWER.kD;
                slot0_shooterFollower.kS = ShooterConstants.SHOOTER_MOTOR_FOLLOWER.kS;
                slot0_shooterFollower.kV = ShooterConstants.SHOOTER_MOTOR_FOLLOWER.kV;
                slot0_shooterFollower.kA = ShooterConstants.SHOOTER_MOTOR_FOLLOWER.kA;

                m_feederMotor = new TalonFX(ShooterConstants.FEEDER_MOTOR.ID);
                m_feederMotor.setNeutralMode(ShooterConstants.FEEDER_MOTOR.NEUTRAL_MODE);

                m_feederMotorConfig = new TalonFXConfiguration();
                m_feederMotorConfig.MotorOutput.Inverted = ShooterConstants.FEEDER_MOTOR.INVERTED_VALUE;
                m_feederMotorConfig.CurrentLimits = ShooterConstants.FEEDER_MOTOR.CURRENT_LIMITS;

                Slot0Configs slot0_feeder = m_feederMotorConfig.Slot0;
                slot0_feeder.kP = ShooterConstants.FEEDER_MOTOR.kP;
                slot0_feeder.kI = ShooterConstants.FEEDER_MOTOR.kI;
                slot0_feeder.kD = ShooterConstants.FEEDER_MOTOR.kD;
                slot0_feeder.kS = ShooterConstants.FEEDER_MOTOR.kS;
                slot0_feeder.kV = ShooterConstants.FEEDER_MOTOR.kV;
                slot0_feeder.kA = ShooterConstants.FEEDER_MOTOR.kA;

                m_indexerMotor = new TalonFX(ShooterConstants.INDEXER_MOTOR.ID);
                m_indexerMotor.setNeutralMode(ShooterConstants.INDEXER_MOTOR.NEUTRAL_MODE);

                m_indexerMotorConfig = new TalonFXConfiguration();
                m_indexerMotorConfig.MotorOutput.Inverted = ShooterConstants.INDEXER_MOTOR.INVERTED_VALUE;
                m_indexerMotorConfig.CurrentLimits = ShooterConstants.INDEXER_MOTOR.CURRENT_LIMITS;

                Slot0Configs slot0_indexer = m_indexerMotorConfig.Slot0;
                slot0_indexer.kP = ShooterConstants.INDEXER_MOTOR.kP;
                slot0_indexer.kI = ShooterConstants.INDEXER_MOTOR.kI;
                slot0_indexer.kD = ShooterConstants.INDEXER_MOTOR.kD;
                slot0_indexer.kS = ShooterConstants.INDEXER_MOTOR.kS;
                slot0_indexer.kV = ShooterConstants.INDEXER_MOTOR.kV;
                slot0_indexer.kA = ShooterConstants.INDEXER_MOTOR.kA;

                applyConfig();

        }

        public void setup() {
                setDefaultCommand(run(() -> {
                        m_shooterMotor.setControl(m_shooterMotorVelocityVoltage.withVelocity(0));
                        m_shooterMotorFollower.setControl(m_shooterMotorFollowerVelocityVoltage.withVelocity(0));
                        m_shooterMotor.setControl(m_feederMotorVelocityVoltage.withVelocity(0));
                        m_shooterMotor.setControl(m_indexerMotorVelocityVoltage.withVelocity(0));
                }).withName("Shooter Subsystem Stopped"));

                ShooterStates.setupStates();
        }

        // shooter motor sys id routines
        public Command m_shooterMotor_SysIdQuasistatic(SysIdRoutine.Direction direction) {
                return m_shooterMotor_SysIdRoutine.quasistatic(direction);
        }

        // ---------------------------------------Commands-----------------------------------------------------------------

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

        private void applyConfig() {
                StatusCode shooter_status = m_shooterMotor.getConfigurator().apply(m_shooterMotorConfig);
                StatusCode shooter_follower_status = m_shooterMotor.getConfigurator().apply(m_shooterMotorConfig);
                StatusCode feeder_status = m_shooterMotor.getConfigurator().apply(m_shooterMotorConfig);
                StatusCode indexer_status = m_shooterMotor.getConfigurator().apply(m_shooterMotorConfig);

                if (!shooter_status.isOK())
                        DriverStation.reportWarning(shooter_status.getName() + "Failed to apply configs to shooter"
                                        + shooter_status.getDescription(), false);

                if (!shooter_follower_status.isOK())
                        DriverStation.reportWarning(shooter_follower_status.getName()
                                        + "Failed to apply configs to shooter follower"
                                        + shooter_follower_status.getDescription(), false);

                if (!feeder_status.isOK())
                        DriverStation.reportWarning(feeder_status.getName() + "Failed to apply configs to feeder"
                                        + feeder_status.getDescription(), false);

                if (!indexer_status.isOK())
                        DriverStation.reportWarning(indexer_status.getName() + "Failed to apply configs to indexer"
                                        + indexer_status.getDescription(), false);
        }
}
