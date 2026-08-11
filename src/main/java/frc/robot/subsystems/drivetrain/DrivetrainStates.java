package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix6.swerve.SwerveRequest;

import frc.robot.RobotContainer;
import frc.robot.RobotStates;

/** DrivetrainStates defines all commands and states */
public class DrivetrainStates {

        // Swerve requests
        private static final SwerveRequest m_brake = new SwerveRequest.SwerveDriveBrake();
        private static final SwerveRequest m_idle = new SwerveRequest.Idle();

        public static void setStates() {
                CommandSwerveDrivetrain swerve = RobotContainer.getDrivetrain();

                RobotStates.m_wheelXPosition
                .whileTrue(swerve.applyRequest(() -> m_brake).withName("Chassis.WheelXPosition"));
                RobotStates.m_disabled.whileTrue(swerve.applyRequest(() -> m_idle).withName("Chassis.Idle"));
                RobotStates.m_zeroHeading
                .onTrue(swerve.runOnce(() -> swerve.seedFieldCentric()).withName("Chassis.ZeroHeading"));

                RobotStates.m_pointWheel.whileTrue(swerve.pointWheels());

                swerve.setDefaultCommand(swerve.teleopDrive());
        }

}
