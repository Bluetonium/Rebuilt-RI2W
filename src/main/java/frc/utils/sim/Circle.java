package frc.utils.sim;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import lombok.Getter;
import lombok.Setter;

public class Circle {

    private MechanismRoot2d m_rollerAxle;
    @SuppressWarnings("unused")
    private MechanismLigament2d m_rollerViz;
    @Getter
    private MechanismLigament2d[] m_circleBackground;
    @Getter
    private int m_backgroundLines;
    private double m_diameterInches;
    private MechanismRoot2d m_root;
    @Setter
    private Color8Bit m_color = new Color8Bit(Color.kBlack);
    @Setter
    private String m_name;

    public Circle(int backgroundLines, double diameterInches, String name, MechanismRoot2d root, Mechanism2d mech) {
        this.m_backgroundLines = backgroundLines;
        this.m_diameterInches = diameterInches;
        this.m_name = name;
        this.m_root = root;
        this.m_circleBackground = new MechanismLigament2d[this.m_backgroundLines];
        this.m_rollerAxle = mech.getRoot(name + " Axle", 0.0, 0.0);
        drawCircle();
    }

    public Circle(Mechanism2d mech, int backgroundLines, double diameterInches, String name, MechanismRoot2d root,
    Color8Bit color) {
        this(backgroundLines, diameterInches, name, root, mech);
        this.m_color = color;
    }

    public void drawCircle() {
        for (int i = 0; i < m_backgroundLines; i++) {
            m_circleBackground[i] = m_root.append(new MechanismLigament2d(m_name + " Background " + i,
            Units.inchesToMeters(m_diameterInches) / 2.0, (360 / m_backgroundLines) * i, m_diameterInches, m_color));
        }
    }

    public void drawViz() {
        m_rollerViz = m_rollerAxle.append(new MechanismLigament2d(m_name + " Roller",
        Units.inchesToMeters(m_diameterInches) / 2.0, 0.0, 5.0, new Color8Bit(Color.kWhite)));
    }

    public void setBackgroundColor(Color8Bit color) {
        for (int i = 0; i < m_backgroundLines; i++) {
            m_circleBackground[i].setColor(color);
        }
    }

    public void setHalfBackground(Color8Bit color8Bit, Color8Bit color8Bit2) {
        for (int i = 0; i < m_backgroundLines; i++) {
            if (i % 2 == 0) {
                m_circleBackground[i].setColor(color8Bit);
            } else {
                m_circleBackground[i].setColor(color8Bit2);
            }
        }
    }
}
