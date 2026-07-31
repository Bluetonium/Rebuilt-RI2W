package frc.robot.subsystems.drivetrain;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import frc.robot.generated.TunerConstants;

public class DriveTrainConstants {
  private DriveTrainConstants() {} // Hide constructor

  private static final double MAX_SPEED_PERCENT = 1; // in percent (1 = 100% of max speed)

  private static final double MAX_ANGULAR_SPEED_RPS = 3.0 / 4; // Rotations per second

  // some calculated values, separate cause i think its nicer this way.
  public static double MAX_SPEED =
      Math.min(MAX_SPEED_PERCENT, 1.0) * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
  public static double MAX_ANGULAR_SPEED =
      RotationsPerSecond.of(MAX_ANGULAR_SPEED_RPS).in(RadiansPerSecond);
}
