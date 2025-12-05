package playerz;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import utilz.LoadSave;
import utilz.Constants.PlayerConstants;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimatedCharacterPanel extends JPanel {
    private BufferedImage[] idleFrames;
    private static AtomicInteger globalAnimationTick = new AtomicInteger(0);
    private static Thread sharedAnimationThread;
    private static int panelCount = 0;
    private static java.util.List<AnimatedCharacterPanel> allPanels = new java.util.ArrayList<>();
    private int characterId;
    private int animationSpeed = 8; // Frames per animation update
    
    public AnimatedCharacterPanel(int characterId) {
        this.characterId = characterId;
        setOpaque(false);
        setVisible(true);
        loadIdleAnimation();
        
        // Register this panel
        synchronized (AnimatedCharacterPanel.class) {
            allPanels.add(this);
            panelCount++;
            if (sharedAnimationThread == null || !sharedAnimationThread.isAlive()) {
                sharedAnimationThread = new Thread(() -> {
                    while (panelCount > 0) {
                        try {
                            Thread.sleep(150); // Update every 150ms (slower for less lag)
                            globalAnimationTick.incrementAndGet();
                            // Repaint all panels - use a static list to track all panels
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                synchronized (AnimatedCharacterPanel.class) {
                                    for (AnimatedCharacterPanel panel : allPanels) {
                                        if (panel.isDisplayable() && panel.isVisible()) {
                                            panel.repaint();
                                        }
                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            break;
                        } catch (Exception e) {
                            // Silently handle errors
                        }
                    }
                });
                sharedAnimationThread.setDaemon(true);
                sharedAnimationThread.start();
            }
        }
    }
    
    private void loadIdleAnimation() {
        BufferedImage spriteSheet = null;
        
        // Load appropriate sprite sheet based on character ID
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
        
        if (spriteSheet == null) {
            // Create placeholder if sprite not found
            idleFrames = new BufferedImage[1];
            idleFrames[0] = new BufferedImage(64, 40, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = idleFrames[0].createGraphics();
            g2d.setColor(java.awt.Color.GRAY);
            g2d.fillRect(0, 0, 64, 40);
            g2d.dispose();
            return;
        }
        
        // Extract idle animation frames (row 0, columns 0-4)
        int idleFrameCount = PlayerConstants.GetSpriteAmount(PlayerConstants.IDLE);
        idleFrames = new BufferedImage[idleFrameCount];
        
        int frameWidth = 64;
        int frameHeight = 40;
        int idleRow = PlayerConstants.IDLE;
        
        for (int i = 0; i < idleFrameCount; i++) {
            try {
                idleFrames[i] = spriteSheet.getSubimage(i * frameWidth, idleRow * frameHeight, frameWidth, frameHeight);
            } catch (Exception e) {
                // If extraction fails, use first frame
                idleFrames[i] = spriteSheet.getSubimage(0, idleRow * frameHeight, frameWidth, frameHeight);
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (idleFrames == null || idleFrames.length == 0) {
            return;
        }
        
        // Calculate current frame based on global tick
        int tick = globalAnimationTick.get();
        int currentFrame = (tick / animationSpeed) % idleFrames.length;
        
        if (currentFrame >= idleFrames.length) {
            currentFrame = 0;
        }
        
        BufferedImage frame = idleFrames[currentFrame];
        if (frame == null) {
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
        int spriteWidth = frame.getWidth();
        int spriteHeight = frame.getHeight();
        
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
        
        g2d.drawImage(frame, x, y, scaledWidth, scaledHeight, null);
    }
    
    public void stopAnimation() {
        synchronized (AnimatedCharacterPanel.class) {
            allPanels.remove(this);
            panelCount--;
            if (panelCount <= 0 && sharedAnimationThread != null) {
                sharedAnimationThread.interrupt();
                sharedAnimationThread = null;
            }
        }
    }
}

