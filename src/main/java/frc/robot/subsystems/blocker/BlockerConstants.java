package frc.robot.subsystems.blocker;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class BlockerConstants {

    public class BLOCKER_MOTOR {
        // id

        // rotations
        public static final int ID = 21;
        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Brake;

        public static final double ACCELERATION = 40;
        public static final double VELOCITY_FORWARD = 40;
        public static final double VELOCITY_BACKWARD = -40;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.CounterClockwise_Positive;

        // with 4:1 gear ratio, 4 motor rotations is one output rotation
        public static final double GEAR_RATIO = 4;
        // rotations 3/8s (12/8s)
        private static final double MAX_ROTATIONS = 12 / 8; // plugs into below
        public static final double MAX_POSITION = GEAR_RATIO * MAX_ROTATIONS;
        public static final double MIN_POSITION = 0;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        public static final double kP = 0.096571;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.3603;
        public static final double kV = 0.10745;
        public static final double kA = 0.010043;

    }

}
