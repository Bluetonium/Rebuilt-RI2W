package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.RobotContainer;
import frc.robot.RobotStates;

public class ShooterStates {
    private static Shooter shooter = RobotContainer.getShooter();

    public static void setupStates() {

        // -------------------------PID-TUNING---------------------------------------------------

        // MAIN SHOOTER
        RobotStates.sysDyn.whileTrue(shooter.m_shooterMotor_SysIdDynamic(Direction.kForward));
        RobotStates.sysSta.whileTrue(shooter.m_shooterMotor_SysIdDynamic(Direction.kForward));
        RobotStates.sysDynRev.whileTrue(shooter.m_shooterMotor_SysIdDynamic(Direction.kReverse));
        RobotStates.sysStaRev.whileTrue(shooter.m_shooterMotor_SysIdDynamic(Direction.kReverse));

        // SHOOTER FOLLOWER
        // RobotStates.sysDyn.whileTrue(shooter.m_shooterMotorFollower_SysIdDynamic(Direction.kForward));
        // RobotStates.sysSta.whileTrue(shooter.m_shooterMotorFollower_SysIdDynamic(Direction.kForward));
        // RobotStates.sysDynRev.whileTrue(shooter.m_shooterMotorFollower_SysIdDynamic(Direction.kReverse));
        // RobotStates.sysStaRev.whileTrue(shooter.m_shooterMotorFollower_SysIdDynamic(Direction.kReverse));

        // FEEDER
        // RobotStates.sysDyn.whileTrue(shooter.m_feederMotor_SysIdDynamic(Direction.kForward));
        // RobotStates.sysSta.whileTrue(shooter.m_feederMotor_SysIdDynamic(Direction.kForward));
        // RobotStates.sysDynRev.whileTrue(shooter.m_feederMotor_SysIdDynamic(Direction.kReverse));
        // RobotStates.sysStaRev.whileTrue(shooter.m_feederMotor_SysIdDynamic(Direction.kReverse));

        // INDEXER (BELT)
        // RobotStates.sysDyn.whileTrue(shooter.m_indexerMotor_SysIdDynamic(Direction.kForward));
        // RobotStates.sysSta.whileTrue(shooter.m_indexerMotor_SysIdDynamic(Direction.kForward));
        // RobotStates.sysDynRev.whileTrue(shooter.m_indexerMotor_SysIdDynamic(Direction.kReverse));
        // RobotStates.sysStaRev.whileTrue(shooter.m_indexerMotor_SysIdDynamic(Direction.kReverse));
    }
}
