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
  // chassis
  public static Trigger m_wheelXPosition;
  public static Trigger m_zeroHeading;

  public static Trigger m_slowMode;
  public static Trigger m_pointWheel;

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

  }

  public static boolean isRed() {

    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent())
      return alliance.get().equals(Alliance.Red);

    return false;
  }

  private RobotStates() {
  } // hide constructor
}
