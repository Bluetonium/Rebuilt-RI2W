package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.signals.NeutralModeValue;

public class ShooterConstants {

    // can ids
    public static int SHOOTER_MOTOR_ID = 1;
    public static int SHOOTER_MOTOR_FOLLOWER_ID = 2;
    public static int FEEDER_MOTOR_ID = 3;
    public static int INDEXER_MOTOR_ID = 4;

    // neutral mode (usually keep on coast)
    public static final NeutralModeValue SHOOTER_MOTOR_NEUTRAL_MODE = NeutralModeValue.Coast;
    public static final NeutralModeValue SHOOTER_MOTOR_FOLLOWER_NEUTRAL_MODE = NeutralModeValue.Coast;
    public static final NeutralModeValue FEEDER_MOTOR_NEUTRAL_MODE = NeutralModeValue.Coast;
    public static final NeutralModeValue INDEXER_MOTOR_NEUTRAL_MODE = NeutralModeValue.Coast;

    // accelerations
    public static final double SHOOTER_MOTOR_ACCELERATION = 90;
    public static final double SHOOTER_MOTOR_FOLLOWER_ACCELERATION = 90;
    public static final double FEEDER_MOTOR_ACCELERATION = 90;
    public static final double INDEXER_MOTOR_ACCELERATION = 90;

    // gear ratios

    // current limits

    // pid values
}
