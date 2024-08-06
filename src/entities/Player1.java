package entities;

import inputs.KeyboardInputs;
import java.awt.Color;
import static utilz.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import main.Game;
import playerz.CharacterPick;
import static utilz.HelperMethods.CanMoveHere;
import static utilz.HelperMethods.GetEntityXPosNextToWall;
import static utilz.HelperMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelperMethods.IsEntityOnFloor;
import utilz.LoadSave;


public class Player1 extends Entity {
        CharacterPick p1 = new CharacterPick();
	private BufferedImage[][] animations;
        private boolean paused = true;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false, parrying = false, attacking1 = false, checkplayerhit1 = false, finish = false;
        private boolean getdmg1 = false, getdmg2 = false, getdmg3 = false, deathh = false, killed2 = false;
	private boolean left, up, right, down, jump, defend;
	private float playerSpeed = 1.8f;
	private int[][] lvlData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.0255f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
        
        private BufferedImage HealthBarImg;

	private int statusBarWidth = (int) (34 * Game.SCALE);
	private int statusBarHeight = (int) (193 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);
        

	private int healthBarWidth = (int) (4 * Game.SCALE);
	private int healthBarHeight = (int) (150 * Game.SCALE);
	private int healthBarXStart = (int) (17 * Game.SCALE);
	private int healthBarYStart = (int) (34 * Game.SCALE);

        
        private int varvalue = p1.getChosen();  

    	private int maxHealth = maxhealth(varvalue);
	public int currentHealth = maxHealth ;
	private int healthHeight = healthBarHeight;
        
        private int flipX = 0;
        private int flipW = 1; 
        public Rectangle2D.Float attackBox1;
        public KeyboardInputs KI;
        
        
        
        
        
	public Player1(float x, float y, int width, int height) {
		super(x, y, width, height);
                
		loadAnimations();
		initHitbox(x, y, 18 * Game.SCALE, 27 * Game.SCALE);
                initAttackBox();        
	}
        
        
        
        
        public void update() {
            if (currentHealth <= 0){
                     pauseAnimation();
                     nomoving();
                }
            if (killed2 || finish){
                    nomoving();
                }
            
                
                updateHealthBar();
                updateAttackBox();
		updatePos();
		updateAnimationTick();
		setAnimation();
                getdmgs1();
	}

        public void render(Graphics g) {
               if (currentHealth <= 0){playerAction = DEAD;
               
               float seconds = (float) (6);
               }
            
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
                drawUI(g);
	}
        
        public int health2(){
            return currentHealth;
        }
        
                
        public void hurt(int amount) {
            int startAni = playerAction;
		currentHealth -= amount;
		if (currentHealth <= 0){
			startAni = DEAD;

                }
                else{
                        startAni = HURT;
                }
	}

        
        public static void exitAfter(float seconds) {
         Timer timer = new Timer();
         TimerTask task = new TimerTask() {
                public void run() {
                    System.exit(0);
              }
            };
            timer.schedule(task, (long) (seconds * 1000L));
        }
        
        

        private void initAttackBox() {
		attackBox1 = new Rectangle2D.Float(x, y, (int) (30 * Game.SCALE), (int) (20 * Game.SCALE));
        }       
        
	
        
        private void updateHealthBar() {
		healthHeight = (int) ((currentHealth / (float) maxHealth) * healthBarHeight);
	}
        
        private void updateAttackBox() {
		if (right)
			attackBox1.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
		else if (left)
			attackBox1.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);

		attackBox1.y = hitbox.y + (Game.SCALE * 10);
	}
        
	
        
        private void drawAttackBox1(Graphics g, int lvlOffsetX) {
		g.setColor(Color.blue);
		g.drawRect((int) attackBox1.x - lvlOffsetX, (int) attackBox1.y, (int) attackBox1.width, (int) attackBox1.height);               
	}
        
  
        private void pauseAnimation() {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    
                    paused = false;
                }
            },475);
        }
        
	private void updateAnimationTick() {
            if (paused){
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
                                attacking1 = false;
                                parrying = false;
                               
			}
                }

		}

	}
        
        private void nomoving(){
            attacking = false;
            parrying = false;
            attacking1 = false;        
            checkplayerhit1 = false;
            getdmg1 = false;      
            getdmg2 = false;
            getdmg3 = false;      
            left  = false;  
            up = false;
            right = false;        
            down = false;
            jump = false; 
            
        }

	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;

		if (inAir) {
			if (airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}

		if (attacking){ 

			playerAction = ATTACK_1; 

  

		if (startAni != playerAction) 

			resetAniTick(); 

                } 

                 

                if (parrying){ 
			playerAction = ATTACK_JUMP_1; 
		if (startAni != playerAction) 
			resetAniTick(); 
                }   
                
                if (attacking1){ 
			playerAction = ATTACK_JUMP_2; 
		if (startAni != playerAction) 
			resetAniTick(); 
                } 
	}
        
        
        
        int varvalues = p1.getPicked();
        public void getdmgs1(){
             if(getdmg1 == true && checkplayerhit1 == true){
                   hurt(damage1(varvalues));
              }else if(getdmg2 == true && checkplayerhit1 == true){
                    hurt(damage2(varvalues));
              }else if(getdmg3 == true  && checkplayerhit1 == true){
                  hurt(damage3(varvalues));
               }else if(deathh){
                   hurt(maxhealth(varvalue));
               }
             

        }
        
              
            
        
        

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();
		if (!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if (left){
			xSpeed -= playerSpeed;
                        flipX = width;
                        flipW = -1;
                }
		if (right){                       
			xSpeed += playerSpeed;
                        flipX = 0;
                        flipW = 1;
                }        
		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
                
                /* if(killed2 || currentHealth <= 0){
                resetPosition();
                }*/
                
	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}

	}
//charachter sprite picker
        BufferedImage png = null; 
	private void loadAnimations() { 


         int varvalue = p1.getChosen();  
          if (varvalue == 1) {  
            png = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS1); 
         }else if (varvalue == 2){  
            png = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS2); 
         } else if (varvalue == 3){  
            png = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS3); 
         } else if (varvalue == 4){  
            png = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS4); 
         } else if (varvalue == 5){  
            png = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS5); 
         }
          
//ANIMATIONS
		BufferedImage img = png; 

		animations = new BufferedImage[9][6];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
                HealthBarImg = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
                

	}
        
        private void drawUI(Graphics g) {

		g.drawImage(HealthBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthBarWidth, healthHeight);
	}
        

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;

	}
        
        public void resetPosition() {
            this.x = 0;  // set x-coordinate to 0
            this.y = 0;  // set y-coordinate to 0
            this.killed2 = false;  // set object to be movable again
            this.currentHealth = maxHealth;
}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

        
        
	public void player1attack1(boolean attacking) { 
		this.attacking = attacking; 
	} 

        public void player1attack2(boolean parrying) { 
                this.parrying = parrying; 
	} 

        public void player1attack3(boolean attacking1) { 
		this.attacking1 = attacking1; 
	} 

        public void checkhit1(boolean checkplayerhit1){
            this.checkplayerhit1 = checkplayerhit1;
        }
        
        
        
        
         //player2's keypresses
        public void player1getdmg1(boolean getdmg1){
               this.getdmg1 = getdmg1;
        }
        public void player1getdmg2(boolean getdmg2){
               this.getdmg2 = getdmg2;
        }
        public void player1getdmg3(boolean getdmg3){
               this.getdmg3 = getdmg3;
        }

		//DEFEND
		public void defend(boolean defend){
			if(defend){
				getdmg1 = false;
				getdmg2 = false;
				getdmg3 = false;	
				checkplayerhit1 = false;
			}
		}
        
        
        public void isdeath2(boolean killed2){
            this.killed2 = killed2;
        }
            
        public boolean isdead1(){
                if (currentHealth <= 0){
                    return true;
                }
                return false;
        }
                      
        public void getDeath1(boolean deathh){
                this.deathh = deathh;
        }
        
        public boolean deadlife1(){
                if (currentHealth <= 0){
                    return true;
                }
                return false;
        }
        
        public void finish(boolean finish){
            this.finish = finish;
            
        }
        



	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void setDefend(boolean defend){
		this.defend = defend;
	
			defend(defend);
		
	}


}

