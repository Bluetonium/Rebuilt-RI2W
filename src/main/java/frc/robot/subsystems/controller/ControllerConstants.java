package frc.robot.subsystems.controller;

import edu.wpi.first.wpilibj.XboxController;

public class ControllerConstants {
  public static enum CONTROLLABLE_SYSTEMS {
    kChassis, kTests
  }

  public static class ChassisControls {
    public static int kTranslation = XboxController.Axis.kLeftY.value;

    public static int kStrafe = XboxController.Axis.kLeftX.value;
    public static int kRotation = XboxController.Axis.kRightX.value;
    public static int kWheelXPosition = XboxController.Button.kX.value;

    public static int kPointWheels = XboxController.Button.kA.value;
    public static int kZeroHeading = XboxController.Button.kB.value;

    public static double kTranslationDeadband = 0.1; // 10% deadband
    public static double kRotationDeadband = 0.1; // 10% deadband

    private ChassisControls() {
    } // Hide constructor
  }

  public static class TestControls {
    public static int kRunTest = XboxController.Button.kA.value;

    private TestControls() {
    }// Hide constructor
  }

  private ControllerConstants() {
  } // Hide constructor
}
