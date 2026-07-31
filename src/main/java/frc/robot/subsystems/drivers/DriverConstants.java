package frc.robot.subsystems.drivers;

import edu.wpi.first.wpilibj.XboxController;

public class DriverConstants {
  private DriverConstants() {} // Hide constructor

  public static enum CONTROLLABLE_SYSTEMS {
    CHASSIS,
  }

  public static class ChassisControls {
    private ChassisControls() {} // Hide constructor

    public static int TRANSLATION = XboxController.Axis.kLeftY.value;
    public static int STRAFE = XboxController.Axis.kLeftX.value;
    public static int ROTATION = XboxController.Axis.kRightX.value;

    public static int WHEEL_X_POSITION = XboxController.Button.kX.value;
    public static int POINT_WHEELS = XboxController.Button.kA.value;
    public static int ZERO_HEADING = XboxController.Button.kB.value;

    public static double TRANSLATION_DEADBAND = 0.1; // 10% deadband
    public static double ROTATION_DEADBAND = 0.1; // 10% deadband
  }
}
