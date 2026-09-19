package frc.robot.subsystems.intake;

import frc.robot.RobotContainer;
import frc.robot.RobotStates;

public class IntakeStates {
    private static Intake intake = RobotContainer.getIntake();

    public static void setupStates() {
        // TODO add commands and stuff for intaking
        RobotStates.m_runIntake.whileTrue(intake.runIntake());
        RobotStates.m_reverseIntake.whileTrue(intake.reverseIntake());

        RobotStates.m_openIntake.onTrue(intake.openIntake());
        RobotStates.m_closeIntake.onTrue(intake.closeIntake());

        // chassis intake controls
        RobotStates.m_chassisRunIntake.whileTrue(intake.runIntake());
        RobotStates.m_chassisReverseIntake.whileTrue(intake.reverseIntake());
    }
}
