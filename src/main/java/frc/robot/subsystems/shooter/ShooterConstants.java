package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

// I have sorted the constants file by motor type, organized into subclasses
// I know this is different to how we've done it before but I think it will
// help compartmentalize things - KD

/*class motor_name {
 *    CAN ID
 *    Neutral Mode          (Usually coast)
 *    Acceleration value    (Unknown units)
 *    Forward Velocity
 *    Backward Velocity
 *    Gear Ratio
 *    
 *    PID values
 * 
 *    Current Limits
 * 
 * 
 *    (later) roller simulation config
 *} 
 */

public class ShooterConstants {

    // TODO set CAN ID's to the correct values (URGENT) KD
    // TODO PID TUNING (these values are completely random from last year) KD
    class SHOOTER_MOTOR {
        public static final int ID = 1;
        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;

        public static final double ACCELERATION = 90;
        public static final double VELOCITY_FORWARD = 90;
        public static final double VELOCITY_BACKWARD = 90;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.Clockwise_Positive;
        public static final double GEAR_RATIO = 1;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        public static final double kP = 0.030071;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.17242;
        public static final double kV = 0.12144;
        public static final double kA = 0.0032487;
    }

    class SHOOTER_MOTOR_FOLLOWER {
        public static final int ID = 2;
        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;

        public static final double ACCELERATION = 90;
        public static final double VELOCITY_FORWARD = 90;
        public static final double VELOCITY_BACKWARD = 90;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.Clockwise_Positive;
        public static final double GEAR_RATIO = 1;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        public static final double kP = 0.030071;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.17242;
        public static final double kV = 0.12144;
        public static final double kA = 0.0032487;
    }

    class FEEDER_MOTOR {
        public static final int ID = 3;
        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;
        public static final double ACCELERATION = 90;
        public static final double VELOCITY_FORWARD = 90;
        public static final double VELOCITY_BACKWARD = 90;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.Clockwise_Positive;
        public static final double GEAR_RATIO = 1;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        public static final double kP = 0.030071;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.17242;
        public static final double kV = 0.12144;
        public static final double kA = 0.0032487;
    }

    class INDEXER_MOTOR {
        public static final int ID = 4;
        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;

        public static final double ACCELERATION = 90;
        public static final double VELOCITY_FORWARD = 90;
        public static final double VELOCITY_BACKWARD = 90;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.Clockwise_Positive;
        public static final double GEAR_RATIO = 1;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        public static final double kP = 0.030071;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.17242;
        public static final double kV = 0.12144;
        public static final double kA = 0.0032487;
    }

}
