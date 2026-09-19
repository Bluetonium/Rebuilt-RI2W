package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.controller.Controller;

public class RobotStates {
  // states
  public static Trigger m_teleop;
  public static Trigger m_autoMode;
  public static Trigger m_testMode;
  public static Trigger m_disabled;
  public static Trigger m_dsAttached;
  public static Trigger m_endGame;
  public static Trigger m_Estopped;
  public static Trigger m_isRed;

  // pid value getting states
  public static Trigger sysDyn;
  public static Trigger sysSta;
  public static Trigger sysDynRev;
  public static Trigger sysStaRev;

  // chassis states
  public static Trigger m_wheelXPosition;
  public static Trigger m_zeroHeading;

  public static Trigger m_slowMode;
  public static Trigger m_pointWheel;

  public static Trigger m_chassisRunIntake;
  public static Trigger m_chassisReverseIntake;

  public static Trigger m_chassisOpenBlocker;
  public static Trigger m_chassisCloseBlocker;

  // arm states
  public static Trigger m_runShooter;
  public static Trigger m_reverseShooter;

  public static Trigger m_runShooterAtHub;
  public static Trigger m_runShooterAtCorner;
  public static Trigger m_runShooterAtTrench;
  public static Trigger m_runShooterAtTower;

  public static Trigger m_runIntake;
  public static Trigger m_reverseIntake;

  public static Trigger m_openIntake;
  public static Trigger m_closeIntake;

  public static Trigger m_runIndexerBelt;
  public static Trigger m_reverseIndexerBelt;

  public static Trigger m_openBlocker;
  public static Trigger m_closeBlocker;

  public static void setupStates() {
    m_teleop = new Trigger(DriverStation::isTeleopEnabled);
    m_autoMode = new Trigger(RobotState::isAutonomous);
    m_testMode = new Trigger(RobotState::isTest);
    m_disabled = new Trigger(RobotState::isDisabled);
    m_dsAttached = new Trigger(DriverStation::isDSAttached);
    m_Estopped = new Trigger(DriverStation::isEStopped);
    m_isRed = new Trigger(RobotStates::isRed);

    m_endGame = m_teleop.and(() -> DriverStation.getMatchTime() < 20);

    // chassis
    m_wheelXPosition = Controller.m_wheelsXPosition;
    m_zeroHeading = Controller.m_zeroHeading;
    m_pointWheel = Controller.m_pointWheels;

    m_chassisRunIntake = Controller.m_chassisRunIntake;
    m_chassisReverseIntake = Controller.m_chassisReverseIntake;
    m_chassisOpenBlocker = Controller.m_chassisOpenBlocker;
    m_chassisCloseBlocker = Controller.m_chassisOpenBlocker;

    // arm
    m_runShooter = Controller.m_runShooter;
    m_reverseShooter = Controller.m_reverseShooter;

    m_runIntake = Controller.m_runIntake;
    m_reverseIntake = Controller.m_reverseIntake;

    m_openIntake = Controller.m_openIntake;
    m_closeIntake = Controller.m_closeIntake;

    m_runIndexerBelt = Controller.m_runIndexerBelt;
    m_reverseIndexerBelt = Controller.m_reverseIndexerBelt;

    m_openBlocker = Controller.m_openBlocker;
    m_closeBlocker = Controller.m_closeBlocker;

    // shoot positions
    m_runShooterAtHub = Controller.m_runShooterAtHub;
    m_runShooterAtCorner = Controller.m_runShooterAtCorner;
    m_runShooterAtTower = Controller.m_runShooterAtTower;
    m_runShooterAtTrench = Controller.m_runShooterAtTrench;

  }

  public static boolean isRed() {

    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent())
      return alliance.get().equals(Alliance.Red);

    return false;
  }

  private RobotStates() {
    setupStates();
  } // hide constructor
}
