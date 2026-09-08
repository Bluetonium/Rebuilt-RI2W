package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private TalonFX m_intakeMotor;
    private TalonFX m_intakeExtendMotor;

    private final VoltageOut m_intakeMotor_sysIdControl = new VoltageOut(0);
    private final VoltageOut m_intakeExtendMotor_sysIdControl = new VoltageOut(0);

    private TalonFXConfiguration m_intakeMotorConfig;
    private TalonFXConfiguration m_intakeExtendMotorConfig;

    private MotionMagicVelocityVoltage m_intakeMotorVelocityVoltage = new MotionMagicVelocityVoltage(0)
            .withAcceleration(IntakeConstants.INTAKE_MOTOR.ACCELERATION);

    private MotionMagicVelocityVoltage m_intakeExtendMotorVelocityVoltage = new MotionMagicVelocityVoltage(0)
            .withAcceleration(IntakeConstants.INTAKE_EXTEND_MOTOR.ACCELERATION);

}
