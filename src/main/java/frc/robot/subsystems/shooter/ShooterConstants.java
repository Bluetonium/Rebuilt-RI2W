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

    // TODO PID TUNING
    class SHOOTER_MOTOR {
        public static final int ID = 17;
        public static final int FOLLOWER_ID = 18;

        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;

        public static final double ACCELERATION = 80;
        public static final double VELOCITY_FORWARD = 80;
        public static final double VELOCITY_BACKWARD = -80;

        // velocity that the motor must be at before feeding flywheel balls
        public static final double TARGET_SHOOT_VELOCITY = VELOCITY_FORWARD;

        // Only change these values for the positions
        public static final double HUB_SHOOT_VELOCITY = 50; // x
        public static final double TOWER_SHOOT_VELOCITY = 60; // dpad up
        public static final double TRENCH_SHOOT_VELOCITY = 65; // y
        public static final double CORNER_SHOOT_VELOCITY = 90;// dpad left
        // James, Position, Check Here

        public static final InvertedValue INVERTED_VALUE = InvertedValue.CounterClockwise_Positive;
        public static final double GEAR_RATIO = 1;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        public static final double kP = 0.09327;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.14667;
        public static final double kV = 0.11981;
        public static final double kA = 0.017895;
    }

    class FEEDER_MOTOR {
        public static final int ID = 20;
        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;
        public static final double ACCELERATION = 30;
        public static final double VELOCITY_FORWARD = 30;
        public static final double VELOCITY_BACKWARD = -30;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.CounterClockwise_Positive;
        public static final double GEAR_RATIO = 1;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        // These are finished PID values as of 9/14/2026 - KD
        public static final double kP = 0.010853;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.16519;
        public static final double kV = 0.11775;
        public static final double kA = 0.0020396;
    }

    class INDEXER_MOTOR {
        public static final int ID = 19;
        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;

        public static final double ACCELERATION = 25;
        public static final double VELOCITY_FORWARD = 25;
        public static final double VELOCITY_BACKWARD = -25;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.Clockwise_Positive;
        public static final double GEAR_RATIO = 4;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        // Correct as of 9/14/2026
        // TODO Redo PID values after changing gear ratio
        public static final double kP = 0.078162;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.39401;
        public static final double kV = 0.10481;
        public static final double kA = 0.0079173;
    }

}
