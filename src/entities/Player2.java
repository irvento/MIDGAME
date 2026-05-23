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
        private int varvalues = CharacterPick.getPicked();
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
        
        // Poison system for Plague Doctor
        private boolean isPoisoned = false;
        private long poisonStartTime = 0;
        private long lastPoisonTick = 0;
        private int poisonDuration = 5000; // 5 seconds in milliseconds
        private int maxHealthForPoison = maxHealth2; // Store max health for poison calculation
        
        public Rectangle2D.Float attackBox2;
        public Rectangle2D.Float hitBox2;

        private int flipX = 0;
        private int flipW = 1;     

        // Hadouken management
        private boolean canShootHadouken = true;     

        // Ender teleport swap states
        private boolean isSwapping = false;
        private long swapTime = 0;
        public void setSwapping(boolean swap) {
            this.isSwapping = swap;
            if (swap) {
                this.swapTime = System.currentTimeMillis();
            }
        }
        
        // Paladin shield state variables
        private boolean isInvincible = false;
        private long shieldStartTime = 0;
        private int shieldDuration = 0;
        public boolean isInvincible() {
            return isInvincible;
        }

        
                                         
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
            
                if (isInvincible) {
                    if (System.currentTimeMillis() - shieldStartTime > shieldDuration) {
                        isInvincible = false;
                    }
                }
            
                updateHealthBar();
                updateAttackBox();
                updateDash(); // Update dash movement
		updatePos();
		updateAnimationTick();
		setAnimation();
                getdmgs2();
                updatePoison(); // Update poison damage over time
	}
        
        private void updatePoison() {
            if (isPoisoned) {
                long currentTime = System.currentTimeMillis();
                long elapsed = currentTime - poisonStartTime;
                
                if (elapsed < poisonDuration) {
                    // Apply 3% HP damage per second
                    // Check if 1 second has passed since last tick
                    if (currentTime - lastPoisonTick >= 1000) {
                        lastPoisonTick = currentTime;
                        int poisonDamage = (int)(maxHealthForPoison * 0.03f);
                        if (poisonDamage > 0) {
                            hurt(poisonDamage);
                        }
                    }
                } else {
                    // Poison expired
                    isPoisoned = false;
                }
            }
        }
        
        public void applyPoison() {
            isPoisoned = true;
            poisonStartTime = System.currentTimeMillis();
            lastPoisonTick = poisonStartTime;
            maxHealthForPoison = maxHealth2; // Update max health reference
        }
        
        public boolean isPoisoned() {
            return isPoisoned;
	}
        
        private void initAttackBox2() {
		attackBox2 = new Rectangle2D.Float(x, y, (int) (30 * Game.SCALE), (int) (20 * Game.SCALE));
	}
 
        
        private void updateHealthBar() {
		healthHeight2 = (int) ((currentHealth2 / (float) maxHealth2) * healthBarHeight2);

	}
        
        public void updateAttackBox() {
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
        
        
        private void drawStatusEffects(Graphics g) {
            // 1. Poison effect (Green particles/aura)
            if (isPoisoned) {
                g.setColor(new java.awt.Color(0, 200, 0, 100)); // Transparent green
                long time = System.currentTimeMillis();
                int offset1 = (int)(Math.sin(time / 200.0) * 10);
                int offset2 = (int)(Math.cos(time / 150.0) * 8);
                g.fillOval((int)(hitbox.x - 5 + offset1), (int)(hitbox.y + hitbox.height / 2 - 5 + offset2), 12, 12);
                g.fillOval((int)(hitbox.x + hitbox.width - 5 - offset2), (int)(hitbox.y + 10 + offset1), 10, 10);
                g.fillOval((int)(hitbox.x + hitbox.width / 2 - 4 + offset2), (int)(hitbox.y + hitbox.height - 12 - offset1), 14, 14);
            }
            
            // 2. Stun / Movement Disabled (Swirling yellow stars/dizzy line above head)
            if (movementDisabled) {
                g.setColor(java.awt.Color.YELLOW);
                long time = System.currentTimeMillis();
                double angle1 = (time / 150.0);
                double angle2 = angle1 + Math.PI;
                int centerX = (int)(hitbox.x + hitbox.width / 2);
                int centerY = (int)(hitbox.y - 10);
                int radiusX = 15;
                int radiusY = 5;
                
                int star1X = centerX + (int)(Math.cos(angle1) * radiusX);
                int star1Y = centerY + (int)(Math.sin(angle1) * radiusY);
                int star2X = centerX + (int)(Math.cos(angle2) * radiusX);
                int star2Y = centerY + (int)(Math.sin(angle2) * radiusY);
                
                g.fillRect(star1X - 3, star1Y - 3, 6, 6);
                g.fillRect(star2X - 3, star2Y - 3, 6, 6);
                
                g.setColor(new java.awt.Color(255, 255, 0, 80));
                g.drawOval(centerX - radiusX, centerY - radiusY, radiusX * 2, radiusY * 2);
            }
            
            // 3. Paladin Holy Shield (Glowing golden circular boundary)
            if (isInvincible) {
                g.setColor(new java.awt.Color(255, 204, 51, 120)); // Semi-transparent Gold
                int shieldSize = (int)(Math.max(hitbox.width, hitbox.height) * 1.3);
                int shieldX = (int)(hitbox.x + hitbox.width / 2 - shieldSize / 2);
                int shieldY = (int)(hitbox.y + hitbox.height / 2 - shieldSize / 2);
                g.drawOval(shieldX, shieldY, shieldSize, shieldSize);
                g.setColor(new java.awt.Color(255, 255, 150, 40));
                g.fillOval(shieldX, shieldY, shieldSize, shieldSize);
            }
            
            // 4. Void Swap Purple particle burst
            if (isSwapping) {
                long elapsed = System.currentTimeMillis() - swapTime;
                if (elapsed < 500) {
                    g.setColor(new java.awt.Color(153, 51, 255, (int)(255 * (1.0 - elapsed / 500.0)))); // Fading purple
                    int burstSize = (int)(20 + (elapsed / 500.0) * 60);
                    int bx = (int)(hitbox.x + hitbox.width / 2 - burstSize / 2);
                    int by = (int)(hitbox.y + hitbox.height / 2 - burstSize / 2);
                    g.drawOval(bx, by, burstSize, burstSize);
                    g.drawRect(bx + 4, by + 4, burstSize - 8, burstSize - 8);
                } else {
                    isSwapping = false;
                }
            }
        }

	public void render(Graphics g) {
            if (currentHealth2 <= 0){playerAction2 = DEAD;
              float seconds = (float) (6);
            
            }
		g.drawImage(animations2[playerAction2][aniIndex2], (int) (hitbox.x - xDrawOffset2) + flipX, (int) (hitbox.y - yDrawOffset2), width * flipW, height, null);
                drawStatusEffects(g);
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
        
        
        int varvalue = CharacterPick.getChosen(); 
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
        

        private main.Game gameInstance;
        
        public void setGameInstance(main.Game game) {
            this.gameInstance = game;
        }

        public void hurt(int amount) {
            try {
                if (isInvincible) {
                    return; // Ignore damage when Paladin's shield is active
                }
                if (defend) {
                    amount = amount / 3; // Blocking reduces damage to 1/3
                }
		currentHealth2 -= amount;
                
                // Spawn collision spark at random location within hitbox
                spawnCollisionSpark();
                
		if (currentHealth2 <= 0){
			playerAction2 = DEAD;
                } else {
                        playerAction2 = HURT;
                }
            } catch (Exception e) {
                // Silently handle errors to prevent crashes
            }
	}
        
        private void spawnCollisionSpark() {
            try {
                if (gameInstance != null && hitbox != null) {
                    // Get random position within hitbox
                    java.util.Random random = new java.util.Random();
                    float sparkX = hitbox.x + random.nextFloat() * hitbox.width;
                    float sparkY = hitbox.y + random.nextFloat() * hitbox.height;
                    
                    gameInstance.spawnCollisionSpark(sparkX, sparkY);
                }
            } catch (Exception e) {
                // Silently handle errors to prevent crashes
                System.out.println("Error in Player2 spawnCollisionSpark: " + e.getMessage());
                }
	}
       
        
        
	private void resetAniTick2() {
		aniTick2 = 0;
		aniIndex2 = 0;
	}

        // Movement disable flag
        private boolean movementDisabled = false;
        
        public void setMovementDisabled(boolean disabled) {
            this.movementDisabled = disabled;
        }
        
        public boolean isMovementDisabled() {
            return movementDisabled;
	}

	private void updatePos() {
		moving = false;
                
                // Don't allow movement if disabled
                if (movementDisabled) {
                    return;
                }

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

	public void setDefend2(boolean defend){
		this.defend = defend;
		defend(defend);
	}

    private long lastSkill1Time = 0;
    private long lastSkill2Time = 0;
    private long lastSkill3Time = 0;
    private long lastHadoukenTime = 0;

    private final long COOLDOWN_TIME_SKILL1 = 600;
    private final long COOLDOWN_TIME_SKILL2 = 2000;
    private final long COOLDOWN_TIME_SKILL3 = 3500;
    private final long COOLDOWN_TIME_HADOUKEN = 1500;

    public void executeSkill1(Player1 enemy) {
        if (System.currentTimeMillis() - lastSkill1Time > COOLDOWN_TIME_SKILL1) {
            main.Game.soundeffects("src\\sounds\\slice-wtr3.wav");
            player2attack1(true);
            
            if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                enemy.hurt(utilz.Constants.PlayerConstants.damage1(varvalues));
                enemy.player1getdmg1(true);
                enemy.checkhit1(true);
            }
            
            lastSkill1Time = System.currentTimeMillis();
        }
    }

    public void executeSkill2(Player1 enemy) {
        if (System.currentTimeMillis() - lastSkill2Time > COOLDOWN_TIME_SKILL2) {
            int charId = CharacterPick.getPicked();
            main.Game.soundeffects("src\\sounds\\kny-slice.wav");
            
            if (charId == 2) {
                teleportInFrontOfEnemy(enemy);
                player2attack2(true);
                if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                    enemy.hurt(800);
                    enemy.player1getdmg2(true);
                    enemy.checkhit1(true);
                }
            } else if (charId == 3) {
                // Ender Void Swap
                swapPositions(enemy);
                player2attack2(true);
                enemy.hurt(800);
                enemy.player1getdmg2(true);
                enemy.checkhit1(true);
            } else if (charId == 4) {
                player2attack2(true);
                if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage2(charId));
                    enemy.applyPoison();
                    enemy.player1getdmg2(true);
                    enemy.checkhit1(true);
                }
            } else if (charId == 5) {
                // Paladin Holy Shield (Invincibility & push back nearby enemies)
                player2attack2(true);
                isInvincible = true;
                shieldStartTime = System.currentTimeMillis();
                shieldDuration = 1500; // 1.5 seconds holy shield
                if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                    enemy.hurt(600);
                    knockbackEnemy(enemy, 50 * Game.SCALE);
                    enemy.player1getdmg2(true);
                    enemy.checkhit1(true);
                }
            } else {
                player2attack2(true);
                if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage2(charId));
                    enemy.player1getdmg2(true);
                    enemy.checkhit1(true);
                }
            }
            lastSkill2Time = System.currentTimeMillis();
        }
    }

    public void executeSkill3(Player1 enemy) {
        if (System.currentTimeMillis() - lastSkill3Time > COOLDOWN_TIME_SKILL3) {
            int charId = CharacterPick.getPicked();
            main.Game.soundeffects("src\\sounds\\sword_slash.wav");
            
            if (charId == 2) {
                setBeheadedKnockbackReady(true);
                player2attack3(true);
                if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                    knockbackEnemy(enemy, (main.Game.GAME_WIDTH / 4.0f) * 0.5f);
                    enemy.player1getdmg3(true);
                    enemy.checkhit1(true);
                    setBeheadedKnockbackReady(false);
                }
            } else if (charId == 1) {
                dashAndPushEnemy(enemy, 100 * Game.SCALE);
            } else if (charId == 3) {
                // Ender Void Pull & Stun
                player2attack3(true);
                pullEnemy(enemy);
                enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                enemy.player1getdmg3(true);
                enemy.checkhit1(true);
                enemy.setMovementDisabled(true);
                Timer disableTimer = new Timer();
                disableTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if (enemy != null) {
                                enemy.setMovementDisabled(false);
                            }
                        } catch (Exception e) {}
                    }
                }, 1200);
            } else if (charId == 4) {
                player2attack3(true);
                if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                    enemy.player1getdmg3(true);
                    enemy.checkhit1(true);
                }
                slashAndDashBack(Game.GAME_WIDTH / 8.0f);
            } else if (charId == 5) {
                // Paladin Holy Strike & Heal 2000 HP
                player2attack3(true);
                if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                    enemy.player1getdmg3(true);
                    enemy.checkhit1(true);
                }
                currentHealth2 += 800;
                if (currentHealth2 > maxHealth2) {
                    currentHealth2 = maxHealth2;
                }
            } else {
                player2attack3(true);
                if (attackBox2 != null && enemy.getHitbox() != null && attackBox2.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                    enemy.player1getdmg3(true);
                    enemy.checkhit1(true);
                }
            }
            lastSkill3Time = System.currentTimeMillis();
        }
    }

    public void executeHadouken() {
        if (System.currentTimeMillis() - lastHadoukenTime > COOLDOWN_TIME_HADOUKEN && canShootHadouken) {
            main.Game.soundeffects("src\\sounds\\sword_slash.wav");
            gameInstance.spawnPlayer2Hadouken();
            lastHadoukenTime = System.currentTimeMillis();
        }
    }

        // Hadouken methods
        public int getFacingDirection() {
            // Returns 1 for right, -1 for left
            if (flipW == 1) {
                return 1; // Facing right
            } else {
                return -1; // Facing left
            }
        }
        
        public float getHadoukenSpawnX() {
            if (getFacingDirection() == 1) {
                return hitbox.x + hitbox.width;
            } else {
                return hitbox.x - (32 * Game.SCALE);
            }
        }
        
        public float getHadoukenSpawnY() {
            return hitbox.y + (hitbox.height / 2) - (16 * Game.SCALE);
        }
        
        public void setCanShootHadouken(boolean canShoot) {
            this.canShootHadouken = canShoot;
        }
        
        public boolean canShootHadouken() {
            return canShootHadouken;
        }
        
        // Beheaded-specific skills
        public void teleportInFrontOfEnemy(Player1 enemy) {
            if (enemy == null) return;
            
            float enemyX = enemy.getHitbox().x;
            float enemyY = enemy.getHitbox().y;
            float enemyWidth = enemy.getHitbox().width;
            float enemyHeight = enemy.getHitbox().height;
            
            // Get enemy's facing direction
            int enemyFacingDir = enemy.getFacingDirection();
            
            // Determine teleport position based on ENEMY's facing direction
            // Teleport closer to enemy (reduced distance)
            float teleportX;
            float teleportY;
            
            if (enemyFacingDir == 1) {
                // Enemy facing right, teleport to the right (in front) of enemy, closer
                teleportX = enemyX + enemyWidth + (10 * Game.SCALE); // Closer distance
                // Face left to attack enemy
                flipX = width;
                flipW = -1;
            } else {
                // Enemy facing left, teleport to the left (in front) of enemy, closer
                teleportX = enemyX - hitbox.width - (10 * Game.SCALE); // Closer distance
                // Face right to attack enemy
                flipX = 0;
                flipW = 1;
            }
            
            // Teleport relative to enemy's Y position (align vertically)
            teleportY = enemyY + (enemyHeight / 2) - (hitbox.height / 2);
            
            // Ensure teleport Y is within bounds
            if (teleportY < 0) teleportY = 0;
            if (teleportY > Game.GAME_HEIGHT - hitbox.height) {
                teleportY = Game.GAME_HEIGHT - hitbox.height;
            }
            
            // Update position
            hitbox.x = teleportX;
            hitbox.y = teleportY;
            x = teleportX;
            y = teleportY;
            
            // Update attackbox immediately after teleport
            updateAttackBox();
        }

        public void swapPositions(Player1 enemy) {
            if (enemy == null) return;
            float myX = hitbox.x;
            float myY = hitbox.y;
            
            hitbox.x = enemy.getHitbox().x;
            hitbox.y = enemy.getHitbox().y;
            x = hitbox.x;
            y = hitbox.y;
            
            enemy.getHitbox().x = myX;
            enemy.getHitbox().y = myY;
            enemy.x = myX;
            enemy.y = myY;
            
            setSwapping(true);
            enemy.setSwapping(true);
            
            updateAttackBox();
            enemy.updateAttackBox();
        }
        
        public void pullEnemy(Player1 enemy) {
            if (enemy == null) return;
            float pullX;
            if (hitbox.x < enemy.getHitbox().x) {
                pullX = hitbox.x + hitbox.width + (15 * Game.SCALE);
            } else {
                pullX = hitbox.x - enemy.getHitbox().width - (15 * Game.SCALE);
            }
            
            pullX = Math.max(0, Math.min(pullX, Game.GAME_WIDTH - enemy.getHitbox().width));
            enemy.getHitbox().x = pullX;
            enemy.x = pullX;
            
            enemy.updateAttackBox();
        }
        
        public void updateAttackBoxAfterTeleport() {
            // Force update attackbox after teleport
            // Temporarily set direction flags based on facing direction
            boolean wasRight = right2;
            boolean wasLeft = left2;
            
            if (flipW == 1) {
                right2 = true;
                left2 = false;
            } else {
                right2 = false;
                left2 = true;
            }
            
            updateAttackBox();
            
            // Restore original flags
            right2 = wasRight;
            left2 = wasLeft;
        }
        
        public void knockbackEnemy(Player1 enemy, float distance) {
            if (enemy == null) return;
            
            // Determine knockback direction based on relative positions
            float knockbackX;
            if (hitbox.x < enemy.getHitbox().x) {
                // Player is to the left of enemy, knockback enemy to the right
                knockbackX = enemy.getHitbox().x + distance;
            } else {
                // Player is to the right of enemy, knockback enemy to the left
                knockbackX = enemy.getHitbox().x - distance;
            }
            
            // Ensure enemy stays within bounds
            knockbackX = Math.max(0, Math.min(knockbackX, Game.GAME_WIDTH - enemy.getHitbox().width));
            
            enemy.getHitbox().x = knockbackX;
            enemy.x = knockbackX;
        }
        
        // Plague Doctor-specific skills
        public void slashAndDashBack(float dashDistance) {
            // Determine dash back direction (opposite of facing direction)
            float dashX;
            if (flipW == 1) {
                // Facing right, dash back left
                dashX = hitbox.x - dashDistance;
            } else {
                // Facing left, dash back right
                dashX = hitbox.x + dashDistance;
            }
            
            // Ensure player stays within bounds
            dashX = Math.max(0, Math.min(dashX, Game.GAME_WIDTH - hitbox.width));
            
            // Update player position
            hitbox.x = dashX;
            x = dashX;
        }
        
        // Rhino-specific skills - Dash/Ram and Push Enemy
        private boolean isDashing = false;
        private float dashTargetX = 0;
        private float dashSpeed = 8.0f * Game.SCALE;
        private Player1 dashTargetEnemy = null;
        private float dashPushDistance = 0;
        
        public void dashAndPushEnemy(Player1 enemy, float dashDistance) {
            if (enemy == null || isDashing || currentHealth2 <= 0) return; // Don't dash if dead
            
            // Determine dash direction based on facing direction
            float targetX;
            if (flipW == 1) {
                // Facing right, dash right
                targetX = hitbox.x + dashDistance;
            } else {
                // Facing left, dash left
                targetX = hitbox.x - dashDistance;
            }
            
            // Ensure player stays within bounds
            targetX = Math.max(0, Math.min(targetX, Game.GAME_WIDTH - hitbox.width));
            
            // Start dashing (will be updated gradually in updateDash method)
            isDashing = true;
            dashTargetX = targetX;
            dashTargetEnemy = enemy;
            dashPushDistance = dashDistance;
            
            player2attack3(true); // Trigger attack animation
        }
        
        public void updateDash() {
            if (isDashing && hitbox != null && currentHealth2 > 0) { // Don't dash if dead
                try {
                    float currentX = hitbox.x;
                    float distance = Math.abs(dashTargetX - currentX);
                    
                    if (distance > dashSpeed) {
                        // Move towards target
                        if (dashTargetX > currentX) {
                            hitbox.x += dashSpeed;
                            x = hitbox.x;
                        } else {
                            hitbox.x -= dashSpeed;
                            x = hitbox.x;
                        }
                        
                        // Check collision during dash
                        if (dashTargetEnemy != null && dashTargetEnemy.getHitbox() != null && !dashTargetEnemy.isdead1()) {
                        Rectangle2D.Float dashBox = new Rectangle2D.Float();
                        if (flipW == 1) {
                            dashBox.x = hitbox.x;
                            dashBox.y = hitbox.y;
                            dashBox.width = dashPushDistance + hitbox.width;
                            dashBox.height = hitbox.height;
                        } else {
                            dashBox.x = hitbox.x - dashPushDistance;
                            dashBox.y = hitbox.y;
                            dashBox.width = dashPushDistance + hitbox.width;
                            dashBox.height = hitbox.height;
                        }
                        
                        // If enemy hitbox intersects with dash box, push them and disable movement
                        if (dashBox.intersects(dashTargetEnemy.getHitbox())) {
                            try {
                                // Deal damage first
                                int damage = utilz.Constants.PlayerConstants.damage3(varvalues);
                                dashTargetEnemy.hurt(damage);
                                
                                float pushDistance = Game.GAME_WIDTH / 8.0f; // 1/8 of map distance
                                float pushX;
                                
                                if (flipW == 1) {
                                    pushX = dashTargetEnemy.getHitbox().x + pushDistance;
                                } else {
                                    pushX = dashTargetEnemy.getHitbox().x - pushDistance;
                                }
                                
                                // Ensure enemy stays within bounds
                                pushX = Math.max(0, Math.min(pushX, Game.GAME_WIDTH - dashTargetEnemy.getHitbox().width));
                                
                                dashTargetEnemy.getHitbox().x = pushX;
                                dashTargetEnemy.x = pushX;
                                
                                // Disable enemy movement for 1.5 seconds
                                dashTargetEnemy.setMovementDisabled(true);
                                Timer disableTimer = new Timer();
                                disableTimer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (dashTargetEnemy != null) {
                                                dashTargetEnemy.setMovementDisabled(false);
                                            }
                                        } catch (Exception e) {
                                            // Handle errors
                                        }
                                    }
                                }, 1500); // 1.5 seconds
                                
                                dashTargetEnemy = null; // Clear reference after push
                            } catch (Exception e) {
                                // Handle errors to prevent crashes
                            }
                        }
                    }
                    } else {
                        // Reached target
                        hitbox.x = dashTargetX;
                        x = dashTargetX;
                        isDashing = false;
                        dashTargetEnemy = null;
                    }
                } catch (Exception e) {
                    // Handle errors to prevent crashes
                    isDashing = false;
                    dashTargetEnemy = null;
                }
            }
        }
        
        private boolean beheadedKnockbackReady = false;
        
        public boolean isBeheadedKnockbackReady() {
            return beheadedKnockbackReady;
        }
        
        public void setBeheadedKnockbackReady(boolean ready) {
            this.beheadedKnockbackReady = ready;
        }

}


