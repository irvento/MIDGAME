package playerz;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import utilz.LoadSave;
import utilz.Constants.PlayerConstants;

public class AnimatedCharacterPanel extends JPanel {
    private BufferedImage[] animation;
    private int characterId;
    private int aniTick, aniIndex, aniSpeed = 15;
    private javax.swing.Timer timer;

    public AnimatedCharacterPanel(int characterId) {
        this.characterId = characterId;
        setOpaque(false);
        setVisible(true);
        loadAnimations();

        // 120 Tick Rate equivalent (approx 8ms, but for animation 15 is fine)
        // Using a Swing Timer for animation loop
        timer = new javax.swing.Timer(16, e -> updateAnimationTick());
        timer.start();
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
        int frames = PlayerConstants.GetSpriteAmount(idleRow);

        animation = new BufferedImage[frames];

        try {
            if (spriteSheet != null) {
                for (int i = 0; i < frames; i++) {
                    animation[i] = spriteSheet.getSubimage(i * frameWidth, idleRow * frameHeight, frameWidth,
                            frameHeight);
                }
            } else {
                // Fallback placeholder
                createPlaceholder(frames, frameWidth, frameHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
            createPlaceholder(frames, frameWidth, frameHeight);
        }
    }

    private void createPlaceholder(int frames, int w, int h) {
        animation = new BufferedImage[frames];
        for (int i = 0; i < frames; i++) {
            animation[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = animation[i].createGraphics();
            g2d.setColor(i % 2 == 0 ? java.awt.Color.GRAY : java.awt.Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, w, h);
            g2d.dispose();
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= animation.length) {
                aniIndex = 0;
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (animation == null || animation.length == 0)
            return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        BufferedImage frame = animation[aniIndex];

        // Scale to fit
        double scale = Math.min((double) panelWidth / frame.getWidth(), (double) panelHeight / frame.getHeight()) * 0.9;
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
