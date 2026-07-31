package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj.util.Color;

public class RobotSim {
    public static final double height = 120;
    public static final double width = 60;
    public static final Mechanism2d rightView = new Mechanism2d(Units.inchesToMeters(width) * 2,
            Units.inchesToMeters(height));

    public RobotSim() {
        
        SmartDashboard.putData("RightView", rightView);
        rightView.setBackgroundColor(new Color8Bit(Color.kLightGray));
    }
}
