package frc.robot.subsystems.blocker;

import frc.robot.RobotContainer;
import frc.robot.RobotStates;

public class BlockerStates {

    private static Blocker blocker = RobotContainer.getBlocker();

    public static void setupStates() {
        RobotStates.m_openBlocker.onTrue(blocker.openBlocker());
        RobotStates.m_chassisOpenBlocker.onTrue(blocker.openBlocker());
    }

}
