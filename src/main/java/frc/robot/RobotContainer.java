// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.auton.Auton;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.SubsystemTesting;
import frc.robot.subsystems.controller.Controller;
import frc.robot.subsystems.controller.ControllerConstants.CONTROLLABLE_SYSTEMS;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.Vision;
import lombok.Getter;

public class RobotContainer {
  // Subsystems
  @Getter
  private static CommandSwerveDrivetrain m_drivetrain = null;

  @Getter
  private static Shooter m_shooter;

  @Getter
  private static Intake m_intake;

  @Getter
  public static Controller m_controller1 = null;

  @Getter
  public static Controller m_controller2 = null;

  @Getter
  public static Controller m_testingController = null;// used for running the
  // subsystem tests - someone else (and PID tuning? - KD)
  @Getter
  private static Vision m_vision = null;

  public RobotContainer() {
    initializeSubsystems();
    RobotStates.setupStates();
    setupSubsystems();
    RobotSim.SetupSim();
    Auton.initializeAuton();
  }

  private void initializeSubsystems() {
    // controllers
    m_controller1 = new Controller(0).withControl(CONTROLLABLE_SYSTEMS.kChassis);
    m_controller2 = new Controller(1).withControl(CONTROLLABLE_SYSTEMS.kArm);
    m_testingController = new Controller(2).withControl(CONTROLLABLE_SYSTEMS.kTests);

    // main subsystems
    m_drivetrain = TunerConstants.createDrivetrain();
    m_shooter = new Shooter();
    m_intake = new Intake();

    // limelights
    m_vision = new Vision();
  }

  private void setupSubsystems() {
    SubsystemTesting.setupTests();

    m_drivetrain.setup();
    m_shooter.setup();
    m_intake.setup();
    m_intake.setup();
    m_vision.setup();
  }
}
