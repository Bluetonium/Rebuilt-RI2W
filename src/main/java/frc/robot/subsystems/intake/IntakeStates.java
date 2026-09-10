package frc.robot.subsystems.intake;

import frc.robot.RobotContainer;
import frc.robot.RobotStates;

public class IntakeStates {
    private static Intake intake = RobotContainer.getIntake();

    public static void setupStates() {
        // TODO add commands and stuff for intaking
        RobotStates.runIntake.whileTrue(intake.runIntake());
    }
}
