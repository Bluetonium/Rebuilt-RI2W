package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.drivers.Drivers;

public class RobotStates {
  private RobotStates() {} // hide constructor

  private static boolean isRed() {

    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent()) return alliance.get().equals(Alliance.Red);

    return false;
  }

  // states
  public static Trigger teleop;
  public static Trigger autoMode;
  public static Trigger testMode;
  public static Trigger disabled;
  public static Trigger dsAttached;
  public static Trigger endGame;
  public static Trigger Estopped;
  public static Trigger isRed; // TODO create this

  // chassis
  public static Trigger wheelXPosition;
  public static Trigger zeroHeading;
  public static Trigger slowMode;
  public static Trigger pointWheel;

  public static void setupStates() {
    teleop = new Trigger(DriverStation::isTeleopEnabled);
    autoMode = new Trigger(RobotState::isAutonomous);
    testMode = new Trigger(RobotState::isTest);
    disabled = new Trigger(RobotState::isDisabled);
    dsAttached = new Trigger(DriverStation::isDSAttached);
    Estopped = new Trigger(DriverStation::isEStopped);
    isRed = new Trigger(RobotStates::isRed);

    endGame = teleop.and(() -> DriverStation.getMatchTime() < 20);

    // chassis
    wheelXPosition = Drivers.wheelsXPosition;
    zeroHeading = Drivers.zeroHeading;
    pointWheel = Drivers.pointWheels;
  }
}
