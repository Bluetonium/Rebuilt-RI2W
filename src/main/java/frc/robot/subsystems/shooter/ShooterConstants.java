package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.signals.NeutralModeValue;

public class ShooterConstants {
    public static int SHOOTER_MOTOR_ID = 1;
    public static int SHOOTER_MOTOR_FOLLOWER_ID = 2;
    public static int FEEDER_MOTOR_ID = 3;
    public static int INDEXER_MOTOR_ID = 4;

    public static final NeutralModeValue SHOOTER_MOTOR_NEUTRAL_MODE = NeutralModeValue.Coast;
    public static final NeutralModeValue SHOOTER_MOTOR_FOLLOWER_NEUTRAL_MODE = NeutralModeValue.Coast;
    public static final NeutralModeValue FEEDER_MOTOR_NEUTRAL_MODE = NeutralModeValue.Coast;
    public static final NeutralModeValue INDEXER_MOTOR_NEUTRAL_MODE = NeutralModeValue.Coast;

}
