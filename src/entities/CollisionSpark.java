package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;

public class CollisionSpark {
    private float x, y;
    private BufferedImage sprite;
    private float currentSize;
    private float maxSize;
    private float growthSpeed = 1.5f; // Faster growth for explosion effect
    private boolean active = true;
    private int lifetime = 0;
    private int maxLifetime = 30; // Frames to display (longer for explosion)
    private float startSize = 2.0f; // Start tiny
    
    public CollisionSpark(float x, float y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.currentSize = startSize; // Start tiny
        this.maxSize = 48 * Game.SCALE; // Maximum size (bigger explosion)
    }
    
    public void update() {
        try {
            if (active) {
                lifetime++;
                
                // Explosion animation: tiny to big
                if (currentSize < maxSize) {
                    // Accelerating growth for explosion effect
                    float growthMultiplier = 1.0f + (lifetime * 0.1f); // Faster as it grows
                    currentSize += growthSpeed * Game.SCALE * growthMultiplier;
                    if (currentSize > maxSize) {
                        currentSize = maxSize;
                    }
                } else {
                    // After reaching max size, start shrinking slightly
                    currentSize *= 0.98f;
                }
                
                // Deactivate after max lifetime
                if (lifetime >= maxLifetime) {
                    active = false;
                }
            }
        } catch (Exception e) {
            // Deactivate on error to prevent crashes
            active = false;
        }
    }
    
    public void draw(Graphics g, int xLvlOffset) {
        try {
            if (active && sprite != null && currentSize > 0 && g != null) {
                int drawX = (int) (x - currentSize / 2 - xLvlOffset);
                int drawY = (int) (y - currentSize / 2);
                int drawSize = (int) currentSize;
                
                if (drawSize > 0) {
                    g.drawImage(sprite, drawX, drawY, drawSize, drawSize, null);
                }
            }
        } catch (Exception e) {
            // Silently handle drawing errors
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

