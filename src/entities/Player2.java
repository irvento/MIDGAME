package entities;


import java.awt.Color;
import static utilz.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


import main.Game;
import playerz.CharacterPick;
import static utilz.HelperMethods.CanMoveHere;
import static utilz.HelperMethods.GetEntityXPosNextToWall;
import static utilz.HelperMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelperMethods.IsEntityOnFloor;
import utilz.LoadSave;


public class Player2 extends Entity {
        CharacterPick p2 = new CharacterPick();
        Rectangle2D.Float ATTACKBOX1;
	private BufferedImage[][] animations2;
        private boolean paused = true;
	private int aniTick2, aniIndex2, aniSpeed2 = 25;
	private int playerAction2 = IDLE;
	private boolean moving = false, attacking2 = false, parrying2 = false, attacking22 = false, checkplayerhit2 = false, finish = false;
        private boolean getdmg1 = false, getdmg2 = false, getdmg3 = false, deathh = false, killed1 = false;
	private boolean left2, up2, right2, down2, jump2, defend;
	private float playerSpeed2 = 1.8f;
	private int[][] lvlData2;
	private float xDrawOffset2 = 21 * Game.SCALE;
	private float yDrawOffset2 = 4 * Game.SCALE;
        private boolean inAir2 = false;
        protected boolean attackChecked;
        
        // Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.0255f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
        private int varvalues = p2.getPicked();
        private boolean b = false;
        private BufferedImage HealthBarImg2;

	private int statusBarWidth2 = (int) (34 * Game.SCALE );
	private int statusBarHeight2 = (int) (193 * Game.SCALE);
	private int statusBarX2 = (int) (810 * Game.SCALE);
	private int statusBarY2 = (int) (10 * Game.SCALE);

       
               
	private int healthBarWidth2 = (int) (4 * Game.SCALE );
	private int healthBarHeight2 = (int) (150 * Game.SCALE);
	private int healthBarXStart2 = (int) (-20 * Game.SCALE);
	private int healthBarYStart2 = (int) (34 * Game.SCALE);

	private int maxHealth2 = maxhealth(varvalues);
	private int currentHealth2 = maxHealth2;
	private int healthHeight2 = healthBarHeight2;
        
        
        public Rectangle2D.Float attackBox2;
        public Rectangle2D.Float hitBox2;

        private int flipX = 0;
        private int flipW = 1;     

        
                                         
	public Player2(float x, float y, int width, int height) { 
		super(x, y, width, height);
                
		loadAnimations2();
		initHitbox(x, y, 18 * Game.SCALE, 27 * Game.SCALE);
                initAttackBox2();
                
                
	}
         private static void soundeffects(String path){
            
            try{
                File musicPath = new File(path);
                AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();  
            }
            catch(Exception e){
                System.out.println("error");
            }
        }
      
	public void update() {
            if (currentHealth2 <= 0){
                    pauseAnimation();
                    nomoving();
                }
            if (killed1 || finish){
                    nomoving();
                }
            
            
                updateHealthBar();
                updateAttackBox();
		updatePos();
		updateAnimationTick();
		setAnimation();
                getdmgs2();
	}
        
        private void initAttackBox2() {
		attackBox2 = new Rectangle2D.Float(x, y, (int) (30 * Game.SCALE), (int) (20 * Game.SCALE));
	}
 
        
        private void updateHealthBar() {
		healthHeight2 = (int) ((currentHealth2 / (float) maxHealth2) * healthBarHeight2);

	}
        
        private void updateAttackBox() {
		if (right2)
			attackBox2.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
		else if (left2)
			attackBox2.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);

		attackBox2.y = hitbox.y + (Game.SCALE * 10);
                
	}

        public static void exitAfter(float seconds) {
         Timer timer = new Timer();
         TimerTask task = new TimerTask() {
                public void run() {
                 System.out.println("Time's up! Exiting now. GGWP!");
                 System.exit(0);
              }
            };
            timer.schedule(task, (long) (seconds * 1000L));
        }
        
        
	public void render(Graphics g) {
            if (currentHealth2 <= 0){playerAction2 = DEAD;
              float seconds = (float) (6);
            
            }
		g.drawImage(animations2[playerAction2][aniIndex2], (int) (hitbox.x - xDrawOffset2) + flipX, (int) (hitbox.y - yDrawOffset2), width * flipW, height, null);

                //drawHitbox(g);
                //drawAttackBox2(g, (int) 4); 
                drawUI2(g);
	}
        
        private void drawAttackBox2(Graphics g, int lvlOffsetX) {
		g.setColor(Color.red);
		g.drawRect((int) attackBox2.x - lvlOffsetX, (int) attackBox2.y, (int) attackBox2.width, (int) attackBox2.height);
                
	}
               
        
        private void pauseAnimation() {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    paused = false;
                }
            },474);
        }

	private void updateAnimationTick() {
            if (paused){
		aniTick2++;
		if (aniTick2 >= aniSpeed2) {
			aniTick2 = 0;
			aniIndex2++;
			if (aniIndex2 >= GetSpriteAmount(playerAction2)) {
				aniIndex2 = 0;
				attacking2 = false;
                                attacking22 = false;
                                parrying2 = false;
                        }
			}

		}

	}

	private void setAnimation() {
		int startAni = playerAction2;

		if (moving)
			playerAction2 = RUNNING;
		else
			playerAction2 = IDLE;

		if (inAir2) {
			if (airSpeed < 0)
				playerAction2 = JUMP;
			else
				playerAction2 = FALLING;
		}

		if (attacking2){ 

			playerAction2 = ATTACK_1; 

  

		if (startAni != playerAction2) 

			resetAniTick2(); 

                } 

                 

                if (parrying2){ 
			playerAction2 = ATTACK_JUMP_1; 
		if (startAni != playerAction2) 
			resetAniTick2(); 
                }   
                
                if (attacking22){ 
			playerAction2 = ATTACK_JUMP_2; 
		if (startAni != playerAction2) 
			resetAniTick2(); 
                } 
	}
        
        
        int varvalue = p2.getChosen(); 
        public void getdmgs2(){
       
             if(getdmg1 == true && checkplayerhit2 == true){
                 
                   hurt(damage1(varvalue));
              }else if(getdmg2 == true && checkplayerhit2 == true){
                  
                    hurt(damage2(varvalue));
              }else if(getdmg3 == true && checkplayerhit2 == true){
                  
                  hurt(damage3(varvalue));
               }else if(deathh){
                   hurt(maxhealth(varvalues));
               }    

        }
        

        public void hurt(int amount) {
            int startAni = playerAction2;
		currentHealth2 -= amount;
		if (currentHealth2 <= 0){
			startAni = DEAD;
                        
        }
                else{
                        startAni = HURT;
                }
	}
       
        
        
	private void resetAniTick2() {
		aniTick2 = 0;
		aniIndex2 = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump2)
			jump();
		if (!left2 && !right2 && !inAir2)
			return;

		float xSpeed = 0;

		if (left2){
			xSpeed -= playerSpeed2;
                        flipX = width;
                        flipW = -1;
                }
		if (right2){                       
			xSpeed += playerSpeed2;
                        flipX = 0;
                        flipW = 1;
                }        
		if (!inAir2)
			if (!IsEntityOnFloor(hitbox, lvlData2))
				inAir2 = true;

		if (inAir2) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData2)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir2();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}

	private void jump() {
		if (inAir2)
			return;
		inAir2 = true;
		airSpeed = jumpSpeed;

	}

	private void resetInAir2() {
		inAir2 = false;
		airSpeed = 0;

	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData2)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}

	}

        private BufferedImage png2 = null; 
	private void loadAnimations2() { 

           
          if (varvalues == 1) {  
            png2 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS1); 
         } else if (varvalues == 2){  
            png2 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS2); 
         } else if (varvalues == 3){  
            png2 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS3); 
         } else if (varvalues == 4){  
            png2 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS4); 
         } else if (varvalues == 5){  
            png2 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS5); 
         }
          
          
		BufferedImage img2 = png2; 

		animations2 = new BufferedImage[9][6];
                
		for (int j = 0; j < animations2.length; j++)
			for (int i = 0; i < animations2[j].length; i++)
				animations2[j][i] = img2.getSubimage(i * 64, j * 40, 64, 40);
        
                    HealthBarImg2 = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
                    
	}
        
        private void drawUI2(Graphics g) {
                
		g.drawImage(HealthBarImg2, statusBarX2, statusBarY2, statusBarWidth2 * -1, statusBarHeight2, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart2 + statusBarX2, healthBarYStart2 + statusBarY2, healthBarWidth2, healthHeight2);
	}

	public void loadLvlData2(int[][] lvlData2) {
		this.lvlData2 = lvlData2;
		if (!IsEntityOnFloor(hitbox, lvlData2))
			inAir2 = true;

	}

        
        
        private void nomoving(){
            attacking2 = false;
            parrying2 = false;       
            attacking22 = false;       
            checkplayerhit2 = false;      
            getdmg1 = false;       
            getdmg2 = false;       
            getdmg3 = false;       
            deathh = false;
            up2   = false;     
            down2 = false;       
            left2  = false;      
            right2 = false;       
            jump2  = false;      
        }

 
	public void resetDirBooleans2() {
		left2 = false;
		right2 = false;
		up2 = false;
		down2 = false;
	}

	public void player2attack1(boolean attacking) { 
		this.attacking2 = attacking; 
	} 

        
        public void player2attack2(boolean parrying) { 
                this.parrying2 = parrying; 
	} 
        
        public void player2attack3(boolean attacking2) { 
		this.attacking22 = attacking2; 
	} 
        
        public void checkhit2(boolean checkplayerhit2){
            this.checkplayerhit2 = checkplayerhit2;
        }
        

        
        //player1's keypresses
        public void player2getdmg1(boolean getdmg1){
               this.getdmg1 = getdmg1;
        }
        public void player2getdmg2(boolean getdmg2){
               this.getdmg2 = getdmg3;
        }
        public void player2getdmg3(boolean getdmg3){
               this.getdmg3 = getdmg3;
        }
        
        //DEFEND
		public void defend(boolean defend){
			if (defend) {
				getdmg1 = false;
				getdmg2 = false;
				getdmg3 = false;
			}
	 }
        
        public void isdeath1(boolean killed1){
            this.killed1 = killed1;
        }
            
        public boolean isdead2(){
                if (currentHealth2 <= 0){
                    return true;
                }
                return false;
        }
        
        
        public void getDeath2(boolean deathh){
                this.deathh = deathh;
        }
        
        public boolean deadlife2(){
            
                if (currentHealth2 <= 0){
                    return true;
                }
            return false;
        }
        
        public void finish(boolean finish){
            this.finish = finish;
            
        }
        
        


	public boolean isLeft2() {
		return left2;
	}

	public void setLeft2(boolean left) {
		this.left2 = left;
	}

	public boolean isUp2() {
		return up2;
	}

	public void setUp2(boolean up) {
		this.up2 = up;
	}

	public boolean isRight2() {
		return right2;
	}

	public void setRight2(boolean right) {
		this.right2 = right;
	}

	public boolean isDown2() {
		return down2;
	}

	public void setDown2(boolean down) {
		this.down2 = down;
	}

	public void setJump2(boolean jump) {
		this.jump2 = jump;
	}

	
	public void setDefend(boolean defend){
		this.defend = defend;
	
			defend(defend);
		
	}


}


