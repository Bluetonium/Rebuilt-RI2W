package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IntakeConstants {

    class INTAKE_MOTOR {
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

    class INTAKE_EXTEND_MOTOR {
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

}
