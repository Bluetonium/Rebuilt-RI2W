# Robot-Code-Template

## Getting Started
### Steps:
1. Create a new repo based off this template. Repo name should follow the naming convention [GAME NAME]-[Specific repo (Off season, Kitbot, etc), omit this for the main repo for a game]

2. Clone and open the new repo.

3. Use CTRE's Phoenix tuner X software to setup swerve drive. Then take the TunerConstants.java file and replace the existing [TunerConstants.java](src\main\java\frc\robot\generated\TunerConstants.java)

4. Use Pathplanner ui to setup the robot configs (0 hamburger button in the top left -> settings -> configure robot)

5. Configure limelights by following limelight docs. Setup all needed pipelines. (All limelights should have the exact same pipelines). Then setup the limelights in code -> [VisionConstants.java](src/main/java/frc/robot/subsystems/vision/VisionConstants.java) 

6. Have fun C:


## Architecture
This project uses triggers in order to facilitate communication of robot systems. The [RobotStates.java](src/main/java/frc/robot/RobotStates.java) acts a middle man between robot inputs (controllers, sensors, auton) and outputs (subsystem commands). The states file can combine triggers. A typical flow of information from a controller to a subsystem would be ```Controller -> RobotStates -> Subsystem```. See the wheelsXPosition control on drivetrain for example. Information can also flow from subsystems to [RobotStates.java](src/main/java/frc/robot/RobotStates.java) in the event of sensors or other states.

An example of how triggers can be combined is shown bellow (this would be in [RobotStates.java](src/main/java/frc/robot/RobotStates.java))
```java
public static Trigger m_intake = new Trigger(Controllers.intake).and(Intake::isEmpty);
```

## Subsystems
Subsystems are organized within the subsystem folder. Each subsystem will get its own folder. A typical subsystem will have 3 files: a constants file, the subsystem file, and states file. For explanation on the states file see [Architecture](#architecture)

### Subsystem testing and tuning
To register a command to be used for testing use the [SubsystemTesting.java](src/main/java/frc/robot/subsystems/SubsystemTesting.java)

### Drivetrain
Allows the robot to drive around. Uses a Holonomic (Swerve) style drive train. Most of the code required for the drive train to work is created by CTRE's phoenix library.

### Vision
Controls all the limelights on the robots. By default all not in use limelights will be used for localization (unless marked not for localization). The subsystem defines functions that should be used for switching the pipelines for limelights. The default command for the vision subsystem sets all limelights as unused and switches to localization.

## Simulating
The simulation util is mostly borrowed from the team 3847 Spectrum. The simulation uses 2D mechanisms to verify that things operate as they should. By default we only have a right view for simulation. This is a 2d window that will show the right side of the robot. Others views can be made if desired.

An example of simulation is bellow for a roller based intake
```java
private final double k_WheelDiameterInches = 4;

private RollerConfig m_config = new RollerConfig(k_WheelDiameterInches).setPosition(1.341, .35);
private RollerSim m_sim = new RollerSim(m_config,RobotSim.rightView, motor.getSimState(), "Outtake");

@Override
public void simulationPeriodic() {
    sim.simulationPeriodic();
}

```

## Authors
* Henry Kirk - henrykirk2007@gmail.com

