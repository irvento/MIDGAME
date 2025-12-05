package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import main.Game;

public class Hadouken extends Entity {

    private BufferedImage[] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private boolean active = true;
    private int dir; // 1 for right, -1 for left
    private float speed = 5f;
    public Rectangle2D.Float attackBox;
    private int damage = 3500; 
    private int flipX = 0;
    private int flipW = 1;

    public Hadouken(float x, float y, int width, int height, int dir) {
        super(x, y, width, height);
        this.dir = dir;
        initHitbox(x, y, width, height);
        initAttackBox();
        // Set flip based on direction
        if (dir < 0) {
            flipX = width;
            flipW = -1;
        } else {
            flipX = 0;
            flipW = 1;
        }
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, width, height);
    }

    public void update() {
        if (active) {
            updateAnimationTick();
            updatePos();
            updateAttackBox();
        }
    }

    private void updatePos() {
        x += dir * speed;
        hitbox.x = x;
        hitbox.y = y;
        
        // Deactivate if out of bounds
        if (x < -100 || x > Game.GAME_WIDTH + 100) {
            active = false;
        }
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x;
        attackBox.y = hitbox.y;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= animations.length) {
                aniIndex = 0;
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (active && animations != null && aniIndex < animations.length) {
            g.drawImage(animations[aniIndex], 
                (int) (hitbox.x - xLvlOffset) + flipX, 
                (int) hitbox.y, 
                width * flipW, 
                height, 
                null);
        }
    }

    public void setAnimations(BufferedImage[] animations) {
        this.animations = animations;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Rectangle2D.Float getAttackBox() {
        return attackBox;
    }

    public int getDamage() {
        return damage;
    }
}
