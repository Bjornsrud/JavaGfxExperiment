import javax.swing.*;
import java.awt.*;

public class CubeExperiment extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(200, 200, 0)); // Yellow

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Hele z = 0-planet. x og y fra -1 til 1
        double z = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                double scale = (z + 3) / 3.0;
                int x2d = (int) (centerX + x * scale * 80);
                int y2d = (int) (centerY + y * scale * 80);
                int radius = (int) (scale * 10);
                g2.fillOval(x2d - radius / 2, y2d - radius / 2, radius, radius);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CubeExperiment");
        CubeExperiment panel = new CubeExperiment();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(panel);
        frame.setVisible(true);
    }
}