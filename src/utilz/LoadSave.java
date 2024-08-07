package utilz;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
        public static final String PLAYER_ATLAS1 = "RhinoPlayerSprite.png";
        public static final String PLAYER_ATLAS2 = "TheBeheaded_Sprite.png";
        public static final String PLAYER_ATLAS3 = "EnderPlayerSprite.png";
        public static final String PLAYER_ATLAS4 = "PlagueDoctorPlayerSprite.png";
        public static final String PLAYER_ATLAS5 = "PaladinPlayerSprite.png";
        
	public static final String LEVEL_ATLAS1 = "outside_spriteslava.png";
        public static final String LEVEL_ATLAS2 = "outside_spritesmist.png";
        public static final String LEVEL_ATLAS3 = "outside_spritegreeneries.png";
        public static final String LEVEL_ATLAS4 = "outside_spriteshades.png";
        
	public static final String LEVEL_DATA1 = "map1.png";
        public static final String LEVEL_DATA2 = "map2.png";
        public static final String LEVEL_DATA3 = "map3.png";
        public static final String LEVEL_DATA4 = "map4.png";
        public static final String LEVEL_DATA5 = "map5.png";
        public static final String HEALTH_BAR = "HEALTHBARZ.png";
        public static final String PLAYER1WINS = "Player1Wins.png"; 
        public static final String PLAYER2WINS = "Player2Wins.png";
        public static final String BGIMG1 = "bg1.png";
        
        public static final String BGIMG2 = "bg2.gif";
        public static final String BGIMG3 = "bg3.gif";
        public static final String BGIMG4 = "bg4.png";
        public static final String BGIMG5 = "bg5.jpg";
        
        public static final String BD = "BD.png";
        public static final String BE = "BE.png";
        public static final String ED = "ED.png";
        public static final String PB = "PB.png";
        public static final String PD = "PD.png";
        public static final String PE = "PE.png"; 
        public static final String PR = "PR.png";
        public static final String RB = "RB.png";
        public static final String RD = "RD.png";
        public static final String RE = "RE.png";
        
        public static final String ROUND1 = "round1.png";
        public static final String ROUND2 = "round2.png";
        public static final String ROUND3 = "round3.png";
        public static final String ROUND4 = "round4.png";
        public static final String ROUND5 = "round5.png";
        public static final String LOAD1 = "gifload1.png"; 
        public static final String LOAD2 = "gifload2.png";
        public static final String LOAD3 = "gifload3.png";
        public static final String LOAD4 = "gifload4.png";
        public static final String LOAD5 = "gifload5.png";
        public static final String LOAD6 = "gifload6.png";
        public static final String LOAD7 = "gifload7.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
        

        
	public static int[][] GetLevelData() {
            
            
            BufferedImage img = null;
            int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
            int randomnum;
            Random random = new Random();
            int min = 1;
            int max = 5;
            int randomNumber = random.nextInt(max - min + 1) + min;
            randomnum = randomNumber;
            System.out.println(randomnum);
            if (randomnum == 1){
            img = GetSpriteAtlas(LEVEL_DATA1);           
            }
            if (randomnum == 2){
            img = GetSpriteAtlas(LEVEL_DATA2);
            }
            if (randomnum == 3){
            img = GetSpriteAtlas(LEVEL_DATA3);
            }
            if (randomnum == 4){
            img = GetSpriteAtlas(LEVEL_DATA4);
            }
            if (randomnum == 5){
            img = GetSpriteAtlas(LEVEL_DATA5);
            }


		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}
}