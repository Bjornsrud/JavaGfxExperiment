import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CubeExperiment extends JPanel {

    private double angleZ = 0;
    private double angleY = 0;
    private double angleX = 0;

    GridRenderer grid;
    ScrollTextRenderer scrollText;


    public CubeExperiment() {
        Timer timer = new Timer(30, e -> {
            angleZ += 0.01;
            angleY += 0.015;
            angleX += 0.008;
            repaint();
        });
        timer.start();
        grid = new GridRenderer();
        String textForScroller = """
                              *** Welcome to this fun, little experiment!       
                Have you ever had sudden flashbacks to things you saw or did in your youth? I had one yesterday, where 
                I remembered the intros to cracked computer games on the C64, usually with a scroll text, music, and some
                kind of graphical effect. Well, as an adult, I work as a software developer, but as I work mostly backend, 
                I haven't done much coding that could be usefull for recreating the C64 intros. It was time to learn, and 
                try to make something fun in Java. :) All code by me (CKB78). 
               \s""";

        String fontFileName = "MinecartLCD.ttf";
        scrollText = new ScrollTextRenderer(fontFileName, textForScroller, 5f, new Color(240, 200, 0), 48f);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;

        // grid.drawGrid(g2, -1, 7, 7, getWidth(), getHeight());
        grid.draw3dGrid(g2, 32, 20, 0.5, getWidth(), getHeight());
        scrollText.draw(g2, getWidth());
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("CubeExperiment");
        CubeExperiment panel = new CubeExperiment();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(panel);
        frame.setVisible(true);
    }
}
