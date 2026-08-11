package frc.robot.subsystems;

import java.util.HashSet;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.subsystems.controller.Controller;

public class SubsystemTesting {
    private static SendableChooser<Command> testSelector = new SendableChooser<>();
    private static HashSet<String> registeredTests = new HashSet<>();

    public static void setupTests() {
        SmartDashboard.putData("Test Chooser", testSelector);

        Controller.m_runTest.whileTrue(Commands.deferredProxy(() -> {
            return testSelector.getSelected();
        }));
    }

    /**
     * Registers a test with the given name and command. The name will be
     * prefixed with the subsystems it uses. Example: A command named "auto
     * align" using drivetrain and vision would be [drivetrain,vision].auto
     * align
     * 
     * @param testCommand the command to run for the test
     * @param name the name of the command
     */
    public static void registerTest(Command testCommand, String name) {
        String requirements = testCommand.getRequirements().stream().map((s) -> s.getName()).sorted()
        .collect(Collectors.joining(","));

        String fullName = String.format("[%s].%s", requirements, name);
        if (registeredTests.contains(fullName)) {
            DriverStation.reportWarning(String.format("TEST %s is already registered!", name), true);
            return;
        }

        registeredTests.add(fullName);
        testSelector.addOption(fullName, testCommand);
    }

    /**
     * Registers a command as a test. Automatically constructors the test name
     * from its requirements and commands name; Example: A command named "auto
     * align" using drivetrain and vision would be [drivetrain,vision].auto
     * align
     * 
     * @param testCommand The command to run for the test
     */
    public static void registerTest(Command testCommand) {
        String commandName = testCommand.getName();
        registerTest(testCommand, commandName);
    }

    public static void registerSysIdTests(SysIdRoutine routine, String name) {
        SubsystemTesting.registerTest(routine.dynamic(Direction.kForward), String.format("%s.dynamic.forward", name));

        SubsystemTesting.registerTest(routine.dynamic(Direction.kReverse), String.format("%s.dynamic.reverse", name));

        SubsystemTesting.registerTest(routine.quasistatic(Direction.kForward),
        String.format("%s.quasistatic.forward", name));

        SubsystemTesting.registerTest(routine.quasistatic(Direction.kReverse),
        String.format("%s.quasistatic.reverse", name));
    }
}
