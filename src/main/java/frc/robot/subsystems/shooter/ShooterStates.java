package frc.robot.subsystems.shooter;

import frc.robot.RobotContainer;
import frc.robot.RobotStates;

public class ShooterStates {
    private static Shooter shooter = RobotContainer.getShooter();

    public static void setupStates() {
        // TODO add commands for shooting subsystem
        RobotStates.runShooter.whileTrue(shooter.runShooter());
    }
}
