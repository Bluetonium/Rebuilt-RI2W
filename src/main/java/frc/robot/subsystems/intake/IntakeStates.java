package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.RobotContainer;
import frc.robot.RobotStates;

public class IntakeStates {
    private static Intake intake = RobotContainer.getIntake();

    public static void setupStates() {
        RobotStates.sysDyn.whileTrue(intake.m_intakeMotor_SysIdDynamic(Direction.kForward));
        RobotStates.sysSta.whileTrue(intake.m_intakeMotor_SysIdDynamic(Direction.kForward));
        RobotStates.sysDynRev.whileTrue(intake.m_intakeMotor_SysIdDynamic(Direction.kReverse));
        RobotStates.sysStaRev.whileTrue(intake.m_intakeMotor_SysIdDynamic(Direction.kReverse));

        RobotStates.sysDyn.whileTrue(intake.m_intakeExtendMotor_SysIdDynamic(Direction.kForward));
        RobotStates.sysSta.whileTrue(intake.m_intakeExtendMotor_SysIdDynamic(Direction.kForward));
        RobotStates.sysDynRev.whileTrue(intake.m_intakeExtendMotor_SysIdDynamic(Direction.kReverse));
        RobotStates.sysStaRev.whileTrue(intake.m_intakeExtendMotor_SysIdDynamic(Direction.kReverse));

    }
}
