// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.drivers.DriverConstants.CONTROLLABLE_SYSTEMS;
import frc.robot.subsystems.drivers.Drivers;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import lombok.Getter;

public class RobotContainer {
  // Subsystems
  @Getter private static CommandSwerveDrivetrain drivetrain = null;

  @Getter private static Drivers driver1 = null;

  @Getter private static Drivers driver2 = null;

  // audio
  private SendableChooser<Command> autoChooser; // TODO implement pathplanner
  private static Command currentAuto;

  public RobotContainer() {
    initializeSubsystems();
    RobotStates.setupStates();

    setupSubsystems();

    // autoChooser = AutoBuilder.buildAutoChooser();
    // currentAuto = autoChooser.getSelected();
    // autoChooser.onChange((command) -> currentAuto = command);
    // SmartDashboard.putData("Autonomous", autoChooser);
  }

  public Command getAutonomousCommand() {
    return currentAuto;
  }

  private void initializeSubsystems() {
    driver1 = new Drivers(0).withControl(CONTROLLABLE_SYSTEMS.CHASSIS);
    driver2 = new Drivers(1);

    drivetrain = TunerConstants.createDrivetrain();
  }

  private void setupSubsystems() {
    drivetrain.setup();
  }
}
