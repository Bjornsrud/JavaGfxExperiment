import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CubeExperiment extends JPanel {

    private double rotationAngleZ = 0;
    private double rotationAngleY = 0;

    public CubeExperiment() {
        Timer timer = new Timer(40, e -> {
            rotationAngleZ += 0.02;
            rotationAngleY += 0.01;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;

        drawGrid(g2, -1, 7, 7);
        drawCubePlanes(g2);
    }

    private void drawCubePlanes(Graphics2D g2) {
        drawPlaneAtZ(-1, g2, new Color(0, 0, 200), 3, 3);  // Bakplan
        drawPlaneAtZ(0, g2, new Color(0, 200, 0), 3, 3);    // Midtplan
        drawPlaneAtZ(1, g2, new Color(200, 0, 0), 3, 3);    // Frontplan
    }

    private void drawPlaneAtZ(double z, Graphics2D g2, Color color, int xCount, int yCount) {
        List<Position3D> points = generatePlanePoints(z, xCount, yCount);
        transformAndDrawPoints(points, g2, color);
    }

    private List<Position3D> generatePlanePoints(double z, int xCount, int yCount) {
        List<Position3D> points = new ArrayList<>();
        int xStart = -xCount / 2;
        int yStart = -yCount / 2;

        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                int xi = xStart + x;
                int yi = yStart + y;
                points.add(new Position3D(xi, yi, z));
            }
        }
        return points;
    }

    private void transformAndDrawPoints(List<Position3D> points, Graphics2D g2, Color color) {
        g2.setColor(color);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (Position3D point : points) {
            point.rotateAroundZ(rotationAngleZ);
            point.rotateAroundY(rotationAngleY);

            double scale = (point.z + 3) / 3.0;
            int x2d = (int) (centerX + point.x * scale * 80);
            int y2d = (int) (centerY + point.y * scale * 80);
            int radius = (int) (scale * 10);
            g2.fillOval(x2d - radius / 2, y2d - radius / 2, radius, radius);
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
            g2.drawLine(x, yTop, x, yBottom);
        }

        for (int j = 0; j < yCount; j++) {
            int yi = yStart + j;
            int y = (int) (centerY + yi * scale * 80);
            int xLeft = (int) (centerX + xStart * scale * 80);
            int xRight = (int) (centerX + (xStart + xCount - 1) * scale * 80);
            g2.drawLine(xLeft, y, xRight, y);
        }

        int topLeftX = (int) (centerX + xStart * scale * 80);
        int topLeftY = (int) (centerY + yStart * scale * 80);
        int sizeX = (int) (scale * 80 * (xCount - 1));
        int sizeY = (int) (scale * 80 * (yCount - 1));
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
