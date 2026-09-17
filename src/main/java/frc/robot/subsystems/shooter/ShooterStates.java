package frc.robot.subsystems.shooter;

import frc.robot.RobotContainer;
import frc.robot.RobotStates;

public class ShooterStates {
    private static Shooter shooter = RobotContainer.getShooter();

    public static void setupStates() {
        // TODO add commands for shooting subsystem

        // RobotStates.runShooter.whileTrue(shooter.runShooter());
        // RobotStates.reverseShooter.whileTrue(shooter.reverseShooter());

        RobotStates.runIndexerBelt.whileTrue(shooter.runIndexer());
        RobotStates.reverseIndexerBelt.whileTrue(shooter.reverseIndexer());

        // shoot positions
        RobotStates.runShooterAtHub
                .whileTrue(shooter.runShooterAtPosition(ShooterConstants.SHOOTER_MOTOR.HUB_SHOOT_VELOCITY));
        RobotStates.runShooterAtTower
                .whileTrue(shooter.runShooterAtPosition(ShooterConstants.SHOOTER_MOTOR.TOWER_SHOOT_VELOCITY));
        RobotStates.runShooterAtTrench
                .whileTrue(shooter.runShooterAtPosition(ShooterConstants.SHOOTER_MOTOR.TRENCH_SHOOT_VELOCITY));
        RobotStates.runShooterAtCorner
                .whileTrue(shooter.runShooterAtPosition(ShooterConstants.SHOOTER_MOTOR.CORNER_SHOOT_VELOCITY));
    }
}
