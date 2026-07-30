package utilz;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;

/**
 * Utility for dynamically scaling and fitting Swing JFrames to any display screen size.
 */
public class WindowScaler {

    public static Dimension getScreenBounds() {
        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        return new Dimension(bounds.width, bounds.height);
    }

    public static void fitFrame(JFrame frame, int targetWidth, int targetHeight) {
        Dimension screen = getScreenBounds();

        double scaleX = (double) screen.width / targetWidth;
        double scaleY = (double) screen.height / targetHeight;

        // Ensure window fits comfortably on screen (using up to 95% of available work area)
        double scale = Math.min(1.0, Math.min(scaleX, scaleY) * 0.95);

        if (scale < 1.0) {
            int newW = (int) (targetWidth * scale);
            int newH = (int) (targetHeight * scale);
            frame.setSize(newW, newH);
            frame.setPreferredSize(new Dimension(newW, newH));

            Container contentPane = frame.getContentPane();
            if (contentPane != null) {
                scaleComponents(contentPane, scale);
            }
        } else {
            frame.setSize(targetWidth, targetHeight);
            frame.setPreferredSize(new Dimension(targetWidth, targetHeight));
        }

        frame.setLocationRelativeTo(null);
    }

    private static void scaleComponents(Container container, double scale) {
        for (Component comp : container.getComponents()) {
            Rectangle b = comp.getBounds();
            comp.setBounds(
                (int) (b.x * scale),
                (int) (b.y * scale),
                (int) (b.width * scale),
                (int) (b.height * scale)
            );

            Font f = comp.getFont();
            if (f != null) {
                int newFontSize = Math.max(8, (int) (f.getSize() * scale));
                comp.setFont(f.deriveFont((float) newFontSize));
            }

            if (comp instanceof Container) {
                scaleComponents((Container) comp, scale);
            }
        }
    }
}
