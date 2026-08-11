package frc.robot.subsystems.drivetrain;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import frc.robot.generated.TunerConstants;

public class DriveTrainConstants {
    private static final double kMaxSpeedPercent = 1; // in percent (1 = 100%
                                                      // of max speed)

    private static final double kMaxAngularSpeedRPS = 3.0 / 4; // Rotations
                                                               // per second

    // some calculated values, separate cause i think its nicer this way.
    public static double kMaxSpeed = Math.min(kMaxSpeedPercent, 1.0)
    * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);

    public static double kMaxAngularSpeed = RotationsPerSecond.of(kMaxAngularSpeedRPS).in(RadiansPerSecond);

    private DriveTrainConstants() {
    } // Hide constructor
}
