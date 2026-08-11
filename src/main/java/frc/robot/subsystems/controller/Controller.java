package frc.robot.subsystems.controller;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.controller.ControllerConstants.CONTROLLABLE_SYSTEMS;

public class Controller {
  // Control axis
  // chassis
  public static DoubleSupplier m_chassisControlTranslation = null;
  public static DoubleSupplier m_chassisControlStrafe = null;
  public static DoubleSupplier m_chassisControlRotation = null;

  // Triggers
  // Chassis
  public static Trigger m_wheelsXPosition = null;
  public static Trigger m_pointWheels = null;
  public static Trigger m_zeroHeading = null;

  // Tests
  public static Trigger m_runTest = null;

  // Instance variables
  private final CommandXboxController m_controller;

  public Controller(int port) {
    m_controller = new CommandXboxController(port);
  }

  public Controller withControl(CONTROLLABLE_SYSTEMS control) {
    switch (control) {
    case kChassis:
      m_chassisControlTranslation = this::translate;

      m_chassisControlStrafe = () -> m_controller.getRawAxis(ControllerConstants.ChassisControls.kStrafe);
      m_chassisControlRotation = () -> m_controller.getRawAxis(ControllerConstants.ChassisControls.kRotation);

      m_wheelsXPosition = m_controller.button(ControllerConstants.ChassisControls.kWheelXPosition);
      m_pointWheels = m_controller.button(ControllerConstants.ChassisControls.kPointWheels);
      m_zeroHeading = m_controller.button(ControllerConstants.ChassisControls.kZeroHeading);
      break;

    case kTests:
      m_runTest = m_controller.button(ControllerConstants.TestControls.kRunTest);
      break;

    default:
      throw new RuntimeException("No setup configured for control " + control.name());
    }

    return this;
  }

  public boolean isDisconnected() {
    return !m_controller.isConnected();
  }

  private double translate() {
    return m_controller.getRawAxis(ControllerConstants.ChassisControls.kTranslation);
  }
}
// hello world
