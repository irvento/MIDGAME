package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {


        private KeyboardInputs keyboardInputs;
	private Game game;
        private GameRounds gameRounds;
        private GameWindow Window;

	public GamePanel(Game game) {                
                keyboardInputs = new KeyboardInputs(this);
		this.game = game;
		setPanelSize();
		addKeyListener(keyboardInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (g instanceof java.awt.Graphics2D) {
			java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
			g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);
		}
		game.render(g);
	}

	public Game getGame() {
		return game;
	}
       
        
        public GameWindow getGameWindow(){
            return Window;
        }

}