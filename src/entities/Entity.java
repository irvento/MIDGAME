package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import playerz.CharacterPick;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
        CharacterPick p1 = new CharacterPick();

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;


	}
        int map = p1.getmapinfo();
        String color;
        protected void drawHitbox(Graphics g) {
             if(map == 1){
                 g.setColor(Color.orange);
            } else if(map == 2){
                g.setColor(new Color(96, 64, 32));
            } else if(map == 3){
                g.setColor(new Color(170, 140, 86));
            } else if(map == 4){
                g.setColor(new Color(148, 0, 211));
            } 
		// For debugging the hitbox
		
                g.fill3DRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height, true);
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
                }


	public void initHitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

    void paint(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

        

}

