package playerz;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import utilz.LoadSave;
import utilz.Constants.PlayerConstants;

public class AnimatedCharacterPanel extends JPanel {
    private BufferedImage characterImage; // Single static image, no animation
    private int characterId;

    public AnimatedCharacterPanel(int characterId) {
        this.characterId = characterId;
        setOpaque(false);
        setVisible(true);
        loadCharacterImage(); // Load only a single static frame
    }

    private void loadCharacterImage() {
        BufferedImage spriteSheet = null;

        // Load ONLY the sprite sheet for THIS specific character ID
        switch (characterId) {
            case 1:
                spriteSheet = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS1);
                break;
            case 2:
                spriteSheet = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS2);
                break;
            case 3:
                spriteSheet = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS3);
                break;
            case 4:
                spriteSheet = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS4);
                break;
            case 5:
                spriteSheet = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS5);
                break;
            default:
                // Unknown character ID - create placeholder
                characterImage = new BufferedImage(64, 40, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = characterImage.createGraphics();
                g2d.setColor(java.awt.Color.GRAY);
                g2d.fillRect(0, 0, 64, 40);
                g2d.dispose();
                return;
        }

        if (spriteSheet == null) {
            // Create placeholder if sprite not found
            characterImage = new BufferedImage(64, 40, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = characterImage.createGraphics();
            g2d.setColor(java.awt.Color.GRAY);
            g2d.fillRect(0, 0, 64, 40);
            g2d.dispose();
            return;
        }

        // Extract ONLY the first idle frame (static image, no animation)
        int frameWidth = 64;
        int frameHeight = 40;
        int idleRow = PlayerConstants.IDLE;

        try {
            // Get the first idle frame (column 0, row 0)
            if (frameWidth <= spriteSheet.getWidth() &&
                    frameHeight <= spriteSheet.getHeight()) {
                characterImage = spriteSheet.getSubimage(0, idleRow * frameHeight, frameWidth, frameHeight);
            } else {
                // Fallback: use top-left corner
                characterImage = spriteSheet.getSubimage(0, 0,
                        Math.min(frameWidth, spriteSheet.getWidth()),
                        Math.min(frameHeight, spriteSheet.getHeight()));
            }
        } catch (Exception e) {
            // If extraction fails, create placeholder
            characterImage = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = characterImage.createGraphics();
            g2d.setColor(java.awt.Color.GRAY);
            g2d.fillRect(0, 0, frameWidth, frameHeight);
            g2d.dispose();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Only draw if we have a valid image for THIS character
        if (characterImage == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        // Use faster rendering hints for better performance
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Calculate scaling to fit panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }

        // Scale sprite to fit panel while maintaining aspect ratio
        int spriteWidth = characterImage.getWidth();
        int spriteHeight = characterImage.getHeight();

        if (spriteWidth <= 0 || spriteHeight <= 0) {
            return;
        }

        double scaleX = (double) panelWidth / spriteWidth;
        double scaleY = (double) panelHeight / spriteHeight;
        double scale = Math.min(scaleX, scaleY) * 0.9; // 90% of panel size

        int scaledWidth = (int) (spriteWidth * scale);
        int scaledHeight = (int) (spriteHeight * scale);

        // Center the sprite
        int x = (panelWidth - scaledWidth) / 2;
        int y = (panelHeight - scaledHeight) / 2;

        // Draw ONLY this character's static image (no animation)
        g2d.drawImage(characterImage, x, y, scaledWidth, scaledHeight, null);
    }

    public void stopAnimation() {
        // No animation to stop - method kept for compatibility
    }
}
