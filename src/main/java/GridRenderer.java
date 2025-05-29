import java.awt.*;

public class GridRenderer {

    void drawGrid(Graphics2D g2, double z, int xCount, int yCount, int width, int height) {
        g2.setColor(new Color(80, 80, 80));

        int centerX = width / 2;
        int centerY = height / 2;
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


    void draw3dGrid(Graphics2D g2, double xCount, int zCount, double yValue, int width, int height) {
        g2.setColor(new Color(40, 80, 0));
        int centerX = width / 2;
        int centerY = height / 2 + 20; // Horisonten

        double xStart = -xCount / 2;
        double z = 1.0;
        double zMax = zCount;

        // Horisontale linjer i z-retning
        while (z < zMax) {
            double scale = (z + 3) / 3.0;
            int y = (int) (centerY + yValue * scale * 80);

            int xLeft = (int) (centerX + xStart * scale * 80);
            int xRight = (int) (centerX + (xStart + xCount) * scale * 80);

            g2.drawLine(xLeft, y, xRight, y);

            z += Math.pow(z, 1.1) * 0.3 + 0.50;
        }

        // Vertikale linjer i x-retning
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
}
