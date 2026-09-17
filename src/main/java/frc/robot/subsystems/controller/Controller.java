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

  // Arm
  public static Trigger m_runShooter = null;
  public static Trigger m_reverseShooter = null;

  public static Trigger m_runIntake = null;
  public static Trigger m_reverseIntake = null;

  public static Trigger m_openIntake = null;
  public static Trigger m_closeIntake = null;

  public static Trigger m_runIndexerBelt = null;
  public static Trigger m_reverseIndexerBelt = null;

  // shoot positions
  public static Trigger m_runShooterAtHub = null;
  public static Trigger m_runShooterAtTrench = null;
  public static Trigger m_runShooterAtTower = null;
  public static Trigger m_runShooterAtCorner = null;

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

      case kArm:
        // m_runShooter = m_controller.pov(ControllerConstants.ArmControls.kDpadUp);
        // m_reverseShooter =
        // m_controller.pov(ControllerConstants.ArmControls.kDpadDown);

        m_runIndexerBelt = m_controller.rightTrigger(ControllerConstants.ArmControls.kShooterThreshold);
        m_reverseIndexerBelt = m_controller.button(ControllerConstants.ArmControls.kShooterReverse);

        m_runIntake = m_controller.leftTrigger(ControllerConstants.ArmControls.kIntakeThreshold);
        m_reverseIntake = m_controller.button(ControllerConstants.ArmControls.kIntakeReverse);

        m_openIntake = m_controller.button(ControllerConstants.ArmControls.kOpenIntake);
        m_closeIntake = m_controller.button(ControllerConstants.ArmControls.kCloseIntake);

        // shoot positions
        m_runShooterAtHub = m_controller.button(ControllerConstants.ArmControls.kX);
        m_runShooterAtTrench = m_controller.button(ControllerConstants.ArmControls.kY);
        m_runShooterAtTower = m_controller.button(ControllerConstants.ArmControls.kDpadUp);
        m_runShooterAtCorner = m_controller.button(ControllerConstants.ArmControls.kDpadLeft);

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
