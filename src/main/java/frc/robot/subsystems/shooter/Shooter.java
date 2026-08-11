package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private TalonFX m_shooterMotor;
    private TalonFX m_shooterMotorFollower;
    private TalonFX m_feederMotor;
    private TalonFX m_indexerMotor;

    public Shooter() {
        m_shooterMotor = new TalonFX(ShooterConstants.SHOOTER_MOTOR_ID);
        m_shooterMotor.setNeutralMode(ShooterConstants.SHOOTER_MOTOR_NEUTRAL_MODE);

        m_shooterMotorFollower = new TalonFX(ShooterConstants.SHOOTER_MOTOR_FOLLOWER_ID);
        m_shooterMotorFollower.setNeutralMode(ShooterConstants.SHOOTER_MOTOR_FOLLOWER_NEUTRAL_MODE);

        m_feederMotor = new TalonFX(ShooterConstants.FEEDER_MOTOR_ID);
        m_feederMotor.setNeutralMode(ShooterConstants.FEEDER_MOTOR_NEUTRAL_MODE);

        m_indexerMotor = new TalonFX(ShooterConstants.INDEXER_MOTOR_ID);
        m_indexerMotor.setNeutralMode(ShooterConstants.INDEXER_MOTOR_NEUTRAL_MODE);

    }
}
