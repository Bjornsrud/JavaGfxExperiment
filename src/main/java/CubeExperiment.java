import javax.swing.*;
import java.awt.*;

public class CubeExperiment extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;

        drawGrid(g2, -1, 7, 7);

        drawPlaneZ(-1, g2, new Color(0, 0, 200), 3, 3);   // Bakplan
        drawPlaneZ(0, g2, new Color(0, 200, 0), 3, 3);     // Midtplan
        drawPlaneZ(1, g2, new Color(200, 0, 0), 3, 3);    // Frontplan
    }

    private void drawPlaneZ(double z, Graphics2D g2, Color color, int xCount, int yCount) {
        g2.setColor(color);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int xStart = -xCount / 2;
        int yStart = -yCount / 2;

        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                int xi = xStart + x;
                int yi = yStart + y;

                double scale = (z + 3) / 3.0;
                int x2d = (int) (centerX + xi * scale * 80);
                int y2d = (int) (centerY + yi * scale * 80);
                int radius = (int) (scale * 10);
                g2.fillOval(x2d - radius / 2, y2d - radius / 2, radius, radius);
            }
        }
    }

    private void drawGrid(Graphics2D g2, double z, int xCount, int yCount) {
        g2.setColor(new Color(50, 50, 50));

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double scale = (z + 3) / 3.0;

        int xStart = -xCount / 2;
        int yStart = -yCount / 2;

        for (int i = 0; i < xCount; i++) {
            int xi = xStart + i;
            int x = (int) (centerX + xi * scale * 80);
            int yTop = (int) (centerY + yStart * scale * 80);
            int yBottom = (int) (centerY + (yStart + yCount - 1) * scale * 80);
            g2.drawLine(x, yTop, x, yBottom); // vertikale
        }

        for (int j = 0; j < yCount; j++) {
            int yi = yStart + j;
            int y = (int) (centerY + yi * scale * 80);
            int xLeft = (int) (centerX + xStart * scale * 80);
            int xRight = (int) (centerX + (xStart + xCount - 1) * scale * 80);
            g2.drawLine(xLeft, y, xRight, y); // horisontale
        }

        // Ramme
        int topLeftX = (int)(centerX + xStart * scale * 80);
        int topLeftY = (int)(centerY + yStart * scale * 80);
        int sizeX = (int)(scale * 80 * (xCount - 1));
        int sizeY = (int)(scale * 80 * (yCount - 1));
        g2.drawRect(topLeftX, topLeftY, sizeX, sizeY);
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
