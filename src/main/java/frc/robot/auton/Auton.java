package frc.robot.auton;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.utils.Elastic;
import frc.utils.Elastic.NotificationLevel;

public class Auton {
    private static SendableChooser<Command> m_autonChooser = null;

    // Event triggers

    /*
     * EXAMPLE
     * 
     * public static final EventTrigger autonExample = new
     * EventTrigger("example");
     * 
     * EventTriggers should be used to tie into the existing robotStates. This
     * should be used whenever a command ending doesn't matter example: priming
     * a shooter, intaking,
     */

    public static Command getSelectedAuton() {
        if (m_autonChooser != null)
            return m_autonChooser.getSelected();
        return null;
    }

    /**
     * setups the chooser for auton and registers named commands
     */
    public static void initializeAuton() {
        setupAutonChooser();
        registerNamedCommands();
    }

    /**
     * 
     * @return a command that does nothing besides a message
     */
    private static Command doNothing() {
        return Commands.print("Do Nothing Auto ran").withName("Do Nothing");
    }

    /**
     * sets up the auto chooser for selecting an auton. IF the auto builder is
     * not configured then this will fail.
     */
    private static void setupAutonChooser() {
        if (AutoBuilder.isConfigured()) {
            m_autonChooser = AutoBuilder.buildAutoChooser();
            m_autonChooser.setDefaultOption("Do nothing", doNothing());

            SmartDashboard.putData("Autonomous", m_autonChooser);
        } else {
            Elastic.sendNotification(new Elastic.Notification(NotificationLevel.ERROR, "Auto chooser not configured",
            "Pathplanner auto builder is not configured. Make sure robot is configured in the pathplanner gui"));
        }
    }

    /**
     * Registers named commands
     */
    private static void registerNamedCommands() {
        /*
         * EXAMPLE: NamedCommands.registerCommand("example",
         * RandomSubsystem.exampleCommand());
         * 
         * Named commands should be used whenever a command should be run
         * sequentially. example of usage would be a auto that goes path ->
         * named command -> path In this example a named command should be used
         * over eventTrigger since we need to wait for the command to run and
         * finish
         */
    }
}
