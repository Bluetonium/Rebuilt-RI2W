// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import com.ctre.phoenix6.HootAutoReplay;
import com.ctre.phoenix6.SignalLogger;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auton.Auton;
import frc.utils.Elastic;
import frc.utils.Elastic.Notification;
import frc.utils.Elastic.NotificationLevel;

public class Robot extends LoggedRobot {

  private Command m_autonomousCommand;

  @SuppressWarnings("unused")
  private final RobotContainer m_robotContainer;

  /* log and replay timestamp and joystick data */
  private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay().withTimestampReplay()
  .withJoystickReplay();

  public Robot() {
    m_robotContainer = new RobotContainer();

    SendableRegistry.add(CommandScheduler.getInstance(), "Command Scheduler");
    SmartDashboard.putData(CommandScheduler.getInstance());

    if (RobotBase.isReal()) {
      Logger.addDataReceiver(new WPILOGWriter()); // write logs to USB
    }

    Logger.addDataReceiver(new NT4Publisher()); // I don't really know what this
                                                // does
    Logger.start();
  }

  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
    m_timeAndJoystickReplay.update();

    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    SignalLogger.stop();
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = Auton.getSelectedAuton();
    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    } else {
      Elastic.sendNotification(new Notification(NotificationLevel.WARNING, "No auto", "No auto to run"));
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void autonomousExit() {
  }

  @Override
  public void teleopInit() {
    SignalLogger.start();

    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().cancel(m_autonomousCommand);
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void testExit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
