package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import playerz.CharacterPick;
import utilz.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
        private CharacterPick p1 = new CharacterPick();
        
        
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelOne = new Level(LoadSave.GetLevelData());
	}
        
        private BufferedImage img = null;

		//debug purposes
		private int map = 1;
        //private int map = p1.getmapinfo();
        
	private void importOutsideSprites() {
            if(map == 1){
                img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS1);
            } else if(map == 2){
                img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS2);
            } else if(map == 3){
                img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS3);
            } else if(map == 4){
                img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS4);
            } 
            
            
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levelOne;
	}

}