package frc.robot.subsystems.drivetrain;

import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.Supplier;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.RobotStates;
import frc.robot.Telemetry;
import frc.robot.generated.TunerConstants.TunerSwerveDrivetrain;
import frc.robot.subsystems.SubsystemTesting;
import frc.robot.subsystems.controller.Controller;
import frc.robot.subsystems.controller.ControllerConstants;

public class CommandSwerveDrivetrain extends TunerSwerveDrivetrain implements Subsystem {
  private static final double kSimLoopPeriod = 0.004; // 4 ms
  /* Blue alliance sees forward as 0 degrees (toward red alliance wall) */
  private static final Rotation2d kBlueAlliancePerspectiveRotation = Rotation2d.kZero;
  /* Red alliance sees forward as 180 degrees (toward blue alliance wall) */
  private static final Rotation2d kRedAlliancePerspectiveRotation = Rotation2d.k180deg;

  private static final SwerveRequest.FieldCentric m_fieldCentricDrive = new SwerveRequest.FieldCentric()
  .withDeadband(DriveTrainConstants.kMaxSpeed * ControllerConstants.ChassisControls.kTranslationDeadband)
  .withRotationalDeadband(DriveTrainConstants.kMaxAngularSpeed * ControllerConstants.ChassisControls.kRotationDeadband)
  .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private static final SwerveRequest.PointWheelsAt m_point = new SwerveRequest.PointWheelsAt();

  private Notifier m_simNotifier = null;

  private double m_lastSimTime;

  /* Keep track if we've ever applied the operator perspective before or not */
  private boolean m_hasAppliedOperatorPerspective = false;

  /* Swerve requests to apply during SysId characterization */
  private final SwerveRequest.SysIdSwerveTranslation m_translationCharacterization = new SwerveRequest.SysIdSwerveTranslation();
  private final SwerveRequest.SysIdSwerveSteerGains m_steerCharacterization = new SwerveRequest.SysIdSwerveSteerGains();
  private final SwerveRequest.SysIdSwerveRotation m_rotationCharacterization = new SwerveRequest.SysIdSwerveRotation();

  private final Telemetry m_logger = new Telemetry(DriveTrainConstants.kMaxSpeed);

  /*
   * SysId routine for characterizing translation. This is used to find PID
   * gains for the drive motors.
   */
  private final SysIdRoutine m_sysIdRoutineTranslation = new SysIdRoutine(new SysIdRoutine.Config(null,

  Volts.of(4), null, state -> SignalLogger.writeString("SysIdTranslation_State", state.toString())),
  new SysIdRoutine.Mechanism(output -> setControl(m_translationCharacterization.withVolts(output)), null, this));

  /*
   * SysId routine for characterizing steer. This is used to find PID gains for
   * the steer motors.
   */
  private final SysIdRoutine m_sysIdRoutineSteer = new SysIdRoutine(
  new SysIdRoutine.Config(null, Volts.of(7), null,
  state -> SignalLogger.writeString("SysIdSteer_State", state.toString())),
  new SysIdRoutine.Mechanism(volts -> setControl(m_steerCharacterization.withVolts(volts)), null, this));

  /*
   * SysId routine for characterizing rotation. This is used to find PID gains
   * for the FieldCentricFacingAngle HeadingController. See the documentation of
   * SwerveRequest.SysIdSwerveRotation for info on importing the log to SysId.
   */
  private final SysIdRoutine m_sysIdRoutineRotation = new SysIdRoutine(new SysIdRoutine.Config(
  /*
   * This is in radians per second², but SysId only supports "volts per second"
   */
  Volts.of(Math.PI / 6).per(Second),
  /* This is in radians per second, but SysId only supports "volts" */
  Volts.of(Math.PI), null, // Use default timeout (10 s)
  // Log state with SignalLogger class
  state -> SignalLogger.writeString("SysIdRotation_State", state.toString())), new SysIdRoutine.Mechanism(output -> {
    /* output is actually radians per second, but SysId only supports "volts" */
    setControl(m_rotationCharacterization.withRotationalRate(output.in(Volts)));
    /* also log the requested output for SysId */
    SignalLogger.writeDouble("Rotational_Rate", output.in(Volts));
  }, null, this));

  /**
   * Constructs a CTRE SwerveDrivetrain using the specified constants.
   *
   * <p>
   * This constructs the underlying hardware devices, so users should not
   * construct the devices themselves. If they need the devices, they can access
   * them through getters in the classes.
   *
   * @param drivetrainConstants Drivetrain-wide constants for the swerve drive
   * @param modules Constants for each specific module
   */
  public CommandSwerveDrivetrain(SwerveDrivetrainConstants drivetrainConstants,
  SwerveModuleConstants<?, ?, ?>... modules) {
    super(drivetrainConstants, modules);
    if (Utils.isSimulation()) {
      startSimThread();
    }

    setupPathPlanner();
    registerTelemetry(m_logger::telemeterize);
    registerTests();
  }

  /**
   * Constructs a CTRE SwerveDrivetrain using the specified constants.
   *
   * <p>
   * This constructs the underlying hardware devices, so users should not
   * construct the devices themselves. If they need the devices, they can access
   * them through getters in the classes.
   *
   * @param drivetrainConstants Drivetrain-wide constants for the swerve drive
   * @param odometryUpdateFrequency The frequency to run the odometry loop. If
   * unspecified or set to 0 Hz, this is 250 Hz on CAN FD, and 100 Hz on CAN
   * 2.0.
   * @param modules Constants for each specific module
   */
  public CommandSwerveDrivetrain(SwerveDrivetrainConstants drivetrainConstants, double odometryUpdateFrequency,
  SwerveModuleConstants<?, ?, ?>... modules) {
    super(drivetrainConstants, odometryUpdateFrequency, modules);
    if (Utils.isSimulation()) {
      startSimThread();
    }
  }

  /**
   * Constructs a CTRE SwerveDrivetrain using the specified constants.
   *
   * <p>
   * This constructs the underlying hardware devices, so users should not
   * construct the devices themselves. If they need the devices, they can access
   * them through getters in the classes.
   *
   * @param drivetrainConstants Drivetrain-wide constants for the swerve drive
   * @param odometryUpdateFrequency The frequency to run the odometry loop. If
   * unspecified or set to 0 Hz, this is 250 Hz on CAN FD, and 100 Hz on CAN
   * 2.0.
   * @param odometryStandardDeviation The standard deviation for odometry
   * calculation in the form [x, y, theta]ᵀ, with units in meters and radians
   * @param visionStandardDeviation The standard deviation for vision
   * calculation in the form [x, y, theta]ᵀ, with units in meters and radians
   * @param modules Constants for each specific module
   */
  public CommandSwerveDrivetrain(SwerveDrivetrainConstants drivetrainConstants, double odometryUpdateFrequency,
  Matrix<N3, N1> odometryStandardDeviation, Matrix<N3, N1> visionStandardDeviation,
  SwerveModuleConstants<?, ?, ?>... modules) {
    super(drivetrainConstants, odometryUpdateFrequency, odometryStandardDeviation, visionStandardDeviation, modules);
    if (Utils.isSimulation()) {
      startSimThread();
    }
  }

  public Command teleopDrive() {
    return applyRequest(() -> m_fieldCentricDrive
    .withVelocityX(-Controller.m_chassisControlTranslation.getAsDouble() * DriveTrainConstants.kMaxSpeed)
    .withVelocityY(-Controller.m_chassisControlStrafe.getAsDouble() * DriveTrainConstants.kMaxSpeed)
    .withRotationalRate(-Controller.m_chassisControlRotation.getAsDouble() * DriveTrainConstants.kMaxAngularSpeed))
    .withName("Chassis.TeleopDrive");
  }

  public Command pointWheels() {
    return applyRequest(
    () -> m_point.withModuleDirection(new Rotation2d(-Controller.m_chassisControlTranslation.getAsDouble(),
    -Controller.m_chassisControlStrafe.getAsDouble()))).withName("Chassis.PointWheels");
  }

  /**
   * Returns a command that applies the specified control request to this swerve
   * drivetrain.
   *
   * @param request Function returning the request to apply
   * @return Command to run
   */
  public Command applyRequest(Supplier<SwerveRequest> request) {
    return run(() -> this.setControl(request.get()));
  }

  @Override
  public void periodic() {
    /*
     * Periodically try to apply the operator perspective. If we haven't applied
     * the operator perspective before, then we should apply it regardless of DS
     * state. This allows us to correct the perspective in case the robot code
     * restarts mid-match. Otherwise, only check and apply the operator
     * perspective if the DS is disabled. This ensures driving behavior doesn't
     * change until an explicit disable event occurs during testing.
     */
    if (!m_hasAppliedOperatorPerspective || DriverStation.isDisabled()) {

      DriverStation.getAlliance().ifPresent(allianceColor -> {
        setOperatorPerspectiveForward(
        allianceColor == Alliance.Red ? kRedAlliancePerspectiveRotation : kBlueAlliancePerspectiveRotation);
        m_hasAppliedOperatorPerspective = true;
      });
    }
  }

  public Pose2d getPose() {
    return this.getState().Pose;
  }

  public ChassisSpeeds getRobotRelativeSpeeds() {
    return this.getState().Speeds;
  }

  public void driveRobotRelative(ChassisSpeeds speeds) {
    setControl(new SwerveRequest.ApplyRobotSpeeds().withSpeeds(speeds));
  }

  public void setup() {
    DrivetrainStates.setStates();
  }

  private void registerTests() {
    SubsystemTesting.registerSysIdTests(m_sysIdRoutineRotation, "Rotation");
    SubsystemTesting.registerSysIdTests(m_sysIdRoutineSteer, "Steer");
    SubsystemTesting.registerSysIdTests(m_sysIdRoutineTranslation, "Translation");
  }

  private void setupPathPlanner() {
    try {
      RobotConfig config = RobotConfig.fromGUISettings();
      // Configure AutoBuilder last
      AutoBuilder.configure(this::getPose, this::resetPose, this::getRobotRelativeSpeeds,
      (speeds, feedforwards) -> driveRobotRelative(speeds),
      new PPHolonomicDriveController(new PIDConstants(5.0, 0.0, 0.0), // Translation
                                                                      // PID
                                                                      // constants
      new PIDConstants(5.0, 0.0, 0.0) // Rotation PID constants
      ), config, RobotStates::isRed, this);
    } catch (Exception e) {
      DriverStation.reportError(e.getMessage(), false);
      DriverStation.reportError("Configure the path planner configs!", e.getStackTrace());
    }

  }

  private void startSimThread() {
    m_lastSimTime = Utils.getCurrentTimeSeconds();

    /* Run simulation at a faster rate so PID gains behave more reasonably */
    m_simNotifier = new Notifier(() -> {
      final double currentTime = Utils.getCurrentTimeSeconds();
      double deltaTime = currentTime - m_lastSimTime;
      m_lastSimTime = currentTime;

      /* use the measured time delta, get battery voltage from WPILib */
      updateSimState(deltaTime, RobotController.getBatteryVoltage());
    });
    m_simNotifier.startPeriodic(kSimLoopPeriod);
  }
}
