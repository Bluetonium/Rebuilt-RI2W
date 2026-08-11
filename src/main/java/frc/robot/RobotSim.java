package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class RobotSim {
    // TODO setup the dimensions here
    public static final double m_height = 1.2;
    public static final double m_width = 0.6;

    // views for the simulation
    public static final Mechanism2d m_rightView = new Mechanism2d(m_width * 2, m_height);

    public static void SetupSim() {
        SmartDashboard.putData("RobotRightView", m_rightView);
        m_rightView.setBackgroundColor(new Color8Bit(Color.kLightGray));
    }

    private RobotSim() {
    }// Hide constructor
}
