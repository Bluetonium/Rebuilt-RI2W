package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.subsystems.SubsystemTesting;

public class Intake extends SubsystemBase {

        private TalonFX m_intakeMotor;
        private TalonFX m_intakeMotorFollower;

        private TalonFX m_intakeExtendMotor;

        private final VoltageOut m_intakeMotor_SysIdControl = new VoltageOut(0);
        private final VoltageOut m_intakeExtendMotor_SysIdControl = new VoltageOut(0);

        private TalonFXConfiguration m_intakeMotorConfig;
        private TalonFXConfiguration m_intakeMotorFollowerConfig;

        private TalonFXConfiguration m_intakeExtendMotorConfig;

        private MotionMagicVelocityVoltage m_intakeMotorVelocityVoltage = new MotionMagicVelocityVoltage(0)
                        .withAcceleration(IntakeConstants.INTAKE_MOTOR.ACCELERATION);

        private MotionMagicVelocityVoltage m_intakeExtendMotorVelocityVoltage = new MotionMagicVelocityVoltage(0)
                        .withAcceleration(IntakeConstants.INTAKE_EXTEND_MOTOR.ACCELERATION);

        private final SysIdRoutine m_intakeMotor_SysIdRoutine = new SysIdRoutine(new SysIdRoutine.Config(
                        null,
                        Volts.of(4),
                        null,
                        (state) -> SignalLogger.writeString("SysIdIntake", state.toString())),
                        new SysIdRoutine.Mechanism(
                                        (volts) -> m_intakeMotor.setControl(
                                                        m_intakeMotor_SysIdControl.withOutput(volts.in(Volts))),
                                        null,
                                        this));

        private final SysIdRoutine m_intakeExtendMotor_SysIdRoutine = new SysIdRoutine(new SysIdRoutine.Config(
                        null,
                        Volts.of(4),
                        null,
                        (state) -> SignalLogger.writeString("SysIdIntakeExtender", state.toString())),
                        new SysIdRoutine.Mechanism(
                                        (volts) -> m_intakeExtendMotor.setControl(
                                                        m_intakeExtendMotor_SysIdControl.withOutput(volts.in(Volts))),
                                        null,
                                        this));

        public Intake() {
                m_intakeMotor = new TalonFX(IntakeConstants.INTAKE_MOTOR.ID);
                m_intakeMotor.setNeutralMode(IntakeConstants.INTAKE_MOTOR.NEUTRAL_MODE);
                m_intakeMotorConfig = new TalonFXConfiguration();
                m_intakeMotorConfig.MotorOutput.Inverted = IntakeConstants.INTAKE_MOTOR.INVERTED_VALUE;
                m_intakeMotorConfig.CurrentLimits = IntakeConstants.INTAKE_MOTOR.CURRENT_LIMITS;

                Slot0Configs slot0_intake = m_intakeMotorConfig.Slot0;
                slot0_intake.kP = IntakeConstants.INTAKE_MOTOR.kP;
                slot0_intake.kI = IntakeConstants.INTAKE_MOTOR.kI;
                slot0_intake.kD = IntakeConstants.INTAKE_MOTOR.kD;
                slot0_intake.kS = IntakeConstants.INTAKE_MOTOR.kS;
                slot0_intake.kV = IntakeConstants.INTAKE_MOTOR.kV;
                slot0_intake.kA = IntakeConstants.INTAKE_MOTOR.kA;

                SubsystemTesting.registerSysIdTests(m_intakeMotor_SysIdRoutine, "Intake Motor");

                m_intakeMotorFollower = new TalonFX(IntakeConstants.INTAKE_MOTOR.FOLLOWER_ID);
                m_intakeMotorFollower.setNeutralMode(IntakeConstants.INTAKE_MOTOR.NEUTRAL_MODE);
                m_intakeMotorFollowerConfig = new TalonFXConfiguration();
                m_intakeMotorFollowerConfig.CurrentLimits = IntakeConstants.INTAKE_MOTOR.CURRENT_LIMITS;

                m_intakeMotorFollower.setControl(new Follower(m_intakeMotor.getDeviceID(),
                                MotorAlignmentValue.Opposed));

                // ------------------------Intake Extender------------------------------
                m_intakeExtendMotor = new TalonFX(IntakeConstants.INTAKE_EXTEND_MOTOR.ID);
                m_intakeExtendMotor.setNeutralMode(IntakeConstants.INTAKE_EXTEND_MOTOR.NEUTRAL_MODE);
                m_intakeExtendMotorConfig = new TalonFXConfiguration();
                m_intakeExtendMotorConfig.MotorOutput.Inverted = IntakeConstants.INTAKE_EXTEND_MOTOR.INVERTED_VALUE;
                m_intakeExtendMotorConfig.CurrentLimits = IntakeConstants.INTAKE_EXTEND_MOTOR.CURRENT_LIMITS;

                Slot0Configs slot0_intakeExtend = m_intakeExtendMotorConfig.Slot0;
                slot0_intakeExtend.kP = IntakeConstants.INTAKE_EXTEND_MOTOR.kP;
                slot0_intakeExtend.kI = IntakeConstants.INTAKE_EXTEND_MOTOR.kI;
                slot0_intakeExtend.kD = IntakeConstants.INTAKE_EXTEND_MOTOR.kD;
                slot0_intakeExtend.kS = IntakeConstants.INTAKE_EXTEND_MOTOR.kS;
                slot0_intakeExtend.kV = IntakeConstants.INTAKE_EXTEND_MOTOR.kV;
                slot0_intakeExtend.kA = IntakeConstants.INTAKE_EXTEND_MOTOR.kA;

                SubsystemTesting.registerSysIdTests(m_intakeExtendMotor_SysIdRoutine, "Intake Extender Motor");

                applyConfig();

        }

        public void setup() {
                setDefaultCommand(run(() -> {
                        m_intakeMotor.setControl(m_intakeMotorVelocityVoltage.withVelocity(0));
                        m_intakeExtendMotor.setControl(m_intakeExtendMotorVelocityVoltage.withVelocity(0));
                }).withName("Intake Subsystem Stopped"));

                IntakeStates.setupStates();
        }

        public Command runIntake() {
                return run(() -> {
                        m_intakeMotor.setControl(m_intakeMotorVelocityVoltage
                                        .withVelocity(IntakeConstants.INTAKE_MOTOR.VELOCITY_FORWARD));
                }).finallyDo(() -> {
                        m_intakeMotor.setControl(m_intakeMotorVelocityVoltage.withVelocity(0));
                }).withName("IntakeForward");
        }

        public void applyConfig() {
                StatusCode intake_status = m_intakeMotor.getConfigurator().apply(m_intakeMotorConfig);
                StatusCode intake_follower_status = m_intakeMotorFollower.getConfigurator()
                                .apply(m_intakeMotorFollowerConfig);
                StatusCode intakeExtend_status = m_intakeExtendMotor.getConfigurator().apply(m_intakeExtendMotorConfig);

                if (!intake_status.isOK())
                        DriverStation.reportWarning(intake_status.getName() + "Failed to apply configs to intake"
                                        + intake_status.getDescription(), false);

                if (!intake_follower_status.isOK())
                        DriverStation.reportWarning(intake_follower_status.getName()
                                        + "Failed to apply configs to intake" + intake_follower_status.getDescription(),
                                        false);

                if (!intakeExtend_status.isOK())
                        DriverStation.reportWarning(
                                        intakeExtend_status.getName() + "Failed to apply configs to intake extender"
                                                        + intakeExtend_status.getDescription(),
                                        false);
        }
}
