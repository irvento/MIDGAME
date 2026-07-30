package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;

public class CollisionSpark {
    private float x, y;
    private BufferedImage sprite;
    private float currentSize;
    private float maxSize;
    private float growthSpeed = 6.0f;
    private boolean active = true;
    private int lifetime = 0;
    private int maxLifetime = 18; // Snappier, cleaner impact
    private float startSize = 20.0f;
    private float[] particleAngles;
    private float[] particleDistances;

    public CollisionSpark(float x, float y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.currentSize = startSize;
        this.maxSize = 120 * Game.SCALE;
        
        // Initialize particle shards
        int count = 6;
        particleAngles = new float[count];
        particleDistances = new float[count];
        for (int i = 0; i < count; i++) {
            particleAngles[i] = (float) (i * Math.PI * 2 / count + Math.random() * 0.5);
            particleDistances[i] = 0f;
        }
    }

    public void update() {
        try {
            if (active) {
                lifetime++;

                // Growth with ease-out curve
                float progress = (float) lifetime / maxLifetime;
                currentSize = startSize + (maxSize - startSize) * (float) Math.sin(progress * Math.PI / 2);

                for (int i = 0; i < particleDistances.length; i++) {
                    particleDistances[i] += 5.0f * Game.SCALE;
                }

                if (lifetime >= maxLifetime) {
                    active = false;
                }
            }
        } catch (Exception e) {
            active = false;
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        try {
            if (active && g != null) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                java.awt.Composite origComposite = g2d.getComposite();

                // Calculate alpha fade (1.0 -> 0.0)
                float alpha = Math.max(0.0f, 1.0f - ((float) lifetime / maxLifetime));
                g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));

                if (sprite != null && currentSize > 0) {
                    int drawX = (int) (x - currentSize / 2 - xLvlOffset);
                    int drawY = (int) (y - currentSize / 2);
                    int drawSize = (int) currentSize;

                    if (drawSize > 0) {
                        g2d.drawImage(sprite, drawX, drawY, drawSize, drawSize, null);
                    }
                }

                // Render radiating energy spark particles
                g2d.setColor(java.awt.Color.YELLOW);
                for (int i = 0; i < particleAngles.length; i++) {
                    int px = (int) (x - xLvlOffset + Math.cos(particleAngles[i]) * particleDistances[i]);
                    int py = (int) (y + Math.sin(particleAngles[i]) * particleDistances[i]);
                    g2d.fillOval(px - 3, py - 3, 6, 6);
                }

                g2d.setComposite(origComposite);
            }
        } catch (Exception e) {
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
