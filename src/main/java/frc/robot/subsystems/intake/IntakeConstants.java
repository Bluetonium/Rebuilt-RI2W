package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IntakeConstants {

    class INTAKE_MOTOR {
        public static final int ID = 0;
        public static final int FOLLOWER_ID = 1;

        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;

        public static final double ACCELERATION = 70;
        public static final double VELOCITY_FORWARD = 70;
        public static final double VELOCITY_BACKWARD = -70;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.CounterClockwise_Positive;
        public static final double GEAR_RATIO = 1;

        public static final CurrentLimitsConfigs CURRENT_LIMITS = new CurrentLimitsConfigs()
                .withStatorCurrentLimit(50)
                .withSupplyCurrentLimit(30);

        // Correct as of 9/14/2026
        public static final double kP = 0.0062923;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kS = 0.057481;
        public static final double kV = 0.11556;
        public static final double kA = 0.0017576;
    }

    class INTAKE_EXTEND_MOTOR {
        public static final int ID = 16;
        public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Brake;

        public static final double ACCELERATION = 50;
        public static final double VELOCITY_FORWARD = 50;
        public static final double VELOCITY_BACKWARD = -50;

        public static final InvertedValue INVERTED_VALUE = InvertedValue.Clockwise_Positive;

        // with 25:1 gear ratio, 25 motor rotations is one output rotation
        public static final double GEAR_RATIO = 25;
        // for this system, ~2 output rotations traverses full intake extender
        private static final double MAX_ROTATIONS = 1.24; // plugs into below
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
