import java.awt.*;
import java.io.File;

public class ScrollTextRenderer {

    private final Font font;
    private final String text;
    private final float scrollSpeed;
    private final Color baseColor;

    private float offsetX = 0;
    private final long startTime;

    public ScrollTextRenderer(String fontFilePath, String text, float scrollSpeed, Color baseColor, float fontSize) {
        this.text = text;
        this.scrollSpeed = scrollSpeed;
        this.baseColor = baseColor;
        this.startTime = System.currentTimeMillis();
        this.font = loadCustomFont(fontFilePath, fontSize);
    }

    private Font loadCustomFont(String fontPath, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(size);
        } catch (Exception e) {
            System.err.println("Feil ved lasting av font: " + e.getMessage());
            return new Font("SansSerif", Font.BOLD, (int) size);
        }
    }

    public void draw(Graphics2D g2, int panelWidth) {
        g2.setFont(font);

        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int y = fm.getAscent() + -2;

        // Beregn glødeffekt (sinuskurve for alfaverdi)
        float glowFactor = (float) (0.4 + 0.6 * (0.5 + 0.5 * Math.sin((System.currentTimeMillis() - startTime) / 300.0)));
        int alpha = (int) (glowFactor * 255);
        Color glowingColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);

        // Tegn tekst med scroll og glød
        g2.setColor(glowingColor);
        g2.drawString(text, (int) offsetX, y);

        // Oppdater offset
        offsetX -= scrollSpeed;
        if (offsetX < -textWidth) {
            offsetX = panelWidth;
        }
    }
}
