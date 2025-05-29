import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CubeExperiment extends JPanel {

    private double angleZ = 0;
    private double angleY = 0;
    private double angleX = 0;

    public CubeExperiment() {
        Timer timer = new Timer(30, e -> {
            angleZ += 0.01;
            angleY += 0.015;
            angleX += 0.008;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;

        // drawGrid(g2, -1, 7, 7);
        draw3dGrid(g2, 32, 20, 0.5);
        drawCubePlanes(g2);
    }

    private void drawCubePlanes(Graphics2D g2) {
        List<ColoredPoint> allPoints = new ArrayList<>();
        allPoints.addAll(generateColoredPoints(-1, new Color(0, 0, 200), 3, 3));  // Bakplan
        allPoints.addAll(generateColoredPoints(0, new Color(0, 200, 0), 3, 3));   // Midtplan
        allPoints.addAll(generateColoredPoints(1, new Color(200, 0, 0), 3, 3));   // Frontplan

        transformAndDrawPoints(allPoints, g2);
    }

    private List<ColoredPoint> generateColoredPoints(double z, Color color, int xCount, int yCount) {
        List<ColoredPoint> points = new ArrayList<>();
        int xStart = -xCount / 2;
        int yStart = -yCount / 2;

        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                int xi = xStart + x;
                int yi = yStart + y;
                points.add(new ColoredPoint(new Position3D(xi, yi, z), color));
            }
        }
        return points;
    }

    private void transformAndDrawPoints(List<ColoredPoint> coloredPoints, Graphics2D g2) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (ColoredPoint cp : coloredPoints) {
            cp.position.rotateAroundX(angleX);
            cp.position.rotateAroundY(angleY);
            cp.position.rotateAroundZ(angleZ);
        }

        // Sorter etter z-verdi (dybde) slik at bakerste tegnes først
        coloredPoints.sort(Comparator.comparingDouble(cp -> cp.position.z));

        for (ColoredPoint cp : coloredPoints) {
            Position3D p = cp.position;
            double scale = (p.z + 3) / 3.0;
            int x2d = (int) (centerX + p.x * scale * 80);
            int y2d = (int) (centerY + p.y * scale * 80);
            int radius = (int) (scale * 10);
            g2.setColor(cp.color);
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

    private void draw3dGrid(Graphics2D g2, double xCount, int zCount, double yValue) {
        g2.setColor(new Color(40, 80, 0));
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 + 20; // Horisonten nær midten

        double xStart = -xCount / 2;
        double z = 1.0;
        double zMax = zCount;

        // Horisontale linjer (i z-retning) med justert progresjon
        while (z < zMax) {
            double scale = (z + 3) / 3.0;
            int y = (int) (centerY + yValue * scale * 80);

            int xLeft = (int) (centerX + xStart * scale * 80);
            int xRight = (int) (centerX + (xStart + xCount) * scale * 80);

            g2.drawLine(xLeft, y, xRight, y);

            z += Math.pow(z, 1.1) * 0.3 + 0.50;
        }

        // Vertikale linjer (i x-retning)
        for (double xi = xStart; xi <= xStart + xCount; xi += 0.5) {
            boolean first = true;
            int prevX = 0, prevY = 0;

            for (int zi = 1; zi <= zCount; zi++) {
                double scale = (zi + 3) / 3.0;
                int x = (int) (centerX + xi * scale * 80);
                int y = (int) (centerY + yValue * scale * 80);

                if (!first) {
                    g2.drawLine(prevX, prevY, x, y);
                }
                prevX = x;
                prevY = y;
                first = false;
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
