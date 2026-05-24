package playerz;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import utilz.LoadSave;
import utilz.Constants.PlayerConstants;

public class AnimatedCharacterPanel extends JPanel {
    private BufferedImage[] idleAnimation;
    private BufferedImage[] hoverAnimation;
    private int characterId;
    private int aniTick, aniIndex, aniSpeed = 15;
    private javax.swing.Timer timer;
    private boolean isHovered = false;
    private boolean isSelected = false;
    private double currentScaleMultiplier = 1.0;
    private double targetScaleMultiplier = 1.0;

    public AnimatedCharacterPanel(int characterId) {
        this.characterId = characterId;
        setOpaque(false);
        setVisible(true);
        loadAnimations();

        // 120 Tick Rate equivalent
        timer = new javax.swing.Timer(16, e -> updateAnimationTick());
        timer.start();
    }
    
    public void setHovered(boolean hovered) {
        this.isHovered = hovered;
        this.targetScaleMultiplier = (hovered || isSelected) ? 1.2 : 1.0;
        if (!hovered && aniIndex >= idleAnimation.length) {
            aniIndex = 0;
        }
    }
    
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        this.targetScaleMultiplier = (isHovered || isSelected) ? 1.2 : 1.0;
    }

    private void loadAnimations() {
        BufferedImage spriteSheet = null;

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
        }

        int frameWidth = 64;
        int frameHeight = 40;
        
        int idleRow = PlayerConstants.IDLE;
        int idleFrames = PlayerConstants.GetSpriteAmount(idleRow);
        
        int hoverRow = PlayerConstants.RUNNING;
        int hoverFrames = PlayerConstants.GetSpriteAmount(hoverRow);

        idleAnimation = new BufferedImage[idleFrames];
        hoverAnimation = new BufferedImage[hoverFrames];

        try {
            if (spriteSheet != null) {
                for (int i = 0; i < idleFrames; i++) {
                    idleAnimation[i] = spriteSheet.getSubimage(i * frameWidth, idleRow * frameHeight, frameWidth, frameHeight);
                }
                for (int i = 0; i < hoverFrames; i++) {
                    hoverAnimation[i] = spriteSheet.getSubimage(i * frameWidth, hoverRow * frameHeight, frameWidth, frameHeight);
                }
            } else {
                createPlaceholder(idleFrames, hoverFrames, frameWidth, frameHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
            createPlaceholder(idleFrames, hoverFrames, frameWidth, frameHeight);
        }
    }

    private void createPlaceholder(int idleF, int hoverF, int w, int h) {
        idleAnimation = new BufferedImage[idleF];
        for (int i = 0; i < idleF; i++) {
            idleAnimation[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }
        hoverAnimation = new BufferedImage[hoverF];
        for (int i = 0; i < hoverF; i++) {
            hoverAnimation[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            BufferedImage[] currentAnim = (isHovered || isSelected) ? hoverAnimation : idleAnimation;
            if (currentAnim != null && aniIndex >= currentAnim.length) {
                aniIndex = 0;
            }
        }
        
        // Smooth scaling interpolation
        currentScaleMultiplier += (targetScaleMultiplier - currentScaleMultiplier) * 0.15;
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage[] currentAnim = (isHovered || isSelected) ? hoverAnimation : idleAnimation;
        if (currentAnim == null || currentAnim.length == 0) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        if (isSelected) {
            // Draw glowing background if selected
            g2d.setColor(new java.awt.Color(255, 255, 255, 40));
            g2d.fillRoundRect(5, 5, panelWidth - 10, panelHeight - 10, 20, 20);
        } else if (isHovered) {
            g2d.setColor(new java.awt.Color(255, 255, 255, 20));
            g2d.fillRoundRect(5, 5, panelWidth - 10, panelHeight - 10, 20, 20);
        }

        // Make sure aniIndex is within bounds for current animation
        int safeIndex = aniIndex % currentAnim.length;
        BufferedImage frame = currentAnim[safeIndex];

        // Scale to fit with multiplier
        double scale = Math.min((double) panelWidth / frame.getWidth(), (double) panelHeight / frame.getHeight()) * 0.9 * currentScaleMultiplier;
        int w = (int) (frame.getWidth() * scale);
        int h = (int) (frame.getHeight() * scale);
        int x = (panelWidth - w) / 2;
        int y = (panelHeight - h) / 2;

        g2d.drawImage(frame, x, y, w, h, null);
    }

    public void stopAnimation() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
}
