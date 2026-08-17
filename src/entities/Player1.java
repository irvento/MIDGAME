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

	// Smooth Movement Physics & Momentum
	private float currentSpeedX = 0f;
	private float accel = 0.35f;
	private float friction = 0.30f;

	// Coyote Time, Jump Buffering & Knockback Physics
	private int coyoteTimer = 0;
	private final int MAX_COYOTE_TIME = 8;
	private int jumpBufferTimer = 0;
	private final int MAX_JUMP_BUFFER = 8;
	private float knockbackX = 0f;

	// After-Image Motion Ghost Trails
	private java.util.LinkedList<float[]> dashTrails = new java.util.LinkedList<>();
        
        private BufferedImage HealthBarImg;

	private int statusBarWidth = (int) (34 * Game.SCALE);
	private int statusBarHeight = (int) (193 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);
        

	private int healthBarWidth = (int) (4 * Game.SCALE);
	private int healthBarHeight = (int) (150 * Game.SCALE);
	private int healthBarXStart = (int) (17 * Game.SCALE);
	private int healthBarYStart = (int) (34 * Game.SCALE);

        
        private int varvalue = CharacterPick.getChosen();  

    	private int maxHealth = maxhealth(varvalue);
	public int currentHealth = maxHealth ;
	private int healthHeight = healthBarHeight;
        
        // Poison system for Plague Doctor
        private boolean isPoisoned = false;
        private long poisonStartTime = 0;
        private long lastPoisonTick = 0;
        private int poisonDuration = 5000; // 5 seconds in milliseconds
        private int maxHealthForPoison = maxHealth; // Store max health for poison calculation
        
        private int flipX = 0;
        private int flipW = 1; 
        public Rectangle2D.Float attackBox1;
        public KeyboardInputs KI;
        
        // Hadouken management
        private boolean canShootHadouken = true;
        
        // Beheaded knockback flag
        private boolean beheadedKnockbackReady = false;
        
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
            maxHealthForPoison = maxHealth; // Update max health reference
        }
        
        public boolean isPoisoned() {
            return isPoisoned;
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
            if (currentHealth <= 0) {
                playerAction = DEAD;
            }
            
            drawDashTrails(g);
            if (animations != null && playerAction >= 0 && playerAction < animations.length && animations[playerAction] != null && animations[playerAction].length > 0) {
                int safeAniIndex = Math.max(0, Math.min(aniIndex, animations[playerAction].length - 1));
                g.drawImage(animations[playerAction][safeAniIndex], (int) (hitbox.x - xDrawOffset) + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
            }
            drawStatusEffects(g);
            drawUI(g);
        }
        
        public int health2(){
            return currentHealth;
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
		currentHealth -= amount;
                
                // Knockback impulse
                float kbDir = (flipW == 1) ? -1.0f : 1.0f;
                knockbackX = kbDir * 3.5f * Game.SCALE;
                
                // Spawn collision spark at random location within hitbox
                spawnCollisionSpark();
                
		if (currentHealth <= 0){
			playerAction = DEAD;
                } else {
                        playerAction = HURT;
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
                System.out.println("Error in Player1 spawnCollisionSpark: " + e.getMessage());
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
        
        public void updateAttackBox() {
		if (right)
			attackBox1.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
		else if (left)
			attackBox1.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
		else {
			// If neither left nor right, use facing direction from flipW
			if (flipW == 1) {
				// Facing right
				attackBox1.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
			} else {
				// Facing left
				attackBox1.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
			}
		}

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
        
	private int getAniSpeed(int action) {
		switch (action) {
			case ATTACK_1:
			case ATTACK_JUMP_1:
			case ATTACK_JUMP_2:
				return 7; // Fast, snappy attack frames
			case RUNNING:
				return 10; // Fluid running stance
			case JUMP:
			case FALLING:
				return 11; // Dynamic airborne frames
			case HURT:
				return 8;
			case DEAD:
				return 14;
			case IDLE:
			default:
				return 18; // Smooth breathing stance
		}
	}

	private void updateAnimationTick() {
		if (paused) {
			aniTick++;
			int targetSpeed = getAniSpeed(playerAction);
			if (aniTick >= targetSpeed) {
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
        
        
        
        int varvalues = CharacterPick.getPicked();
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

		if (movementDisabled) {
			return;
		}

		// Coyote time & Jump buffer logic
		if (IsEntityOnFloor(hitbox, lvlData)) {
			coyoteTimer = MAX_COYOTE_TIME;
		} else if (coyoteTimer > 0) {
			coyoteTimer--;
		}

		if (jump) {
			jumpBufferTimer = MAX_JUMP_BUFFER;
		} else if (jumpBufferTimer > 0) {
			jumpBufferTimer--;
		}

		if ((jump || jumpBufferTimer > 0) && (coyoteTimer > 0 || !inAir)) {
			jump();
		}

		// Variable jump height
		if (!jump && inAir && airSpeed < 0) {
			airSpeed *= 0.85f;
		}

		float targetSpeedX = 0;
		if (left) {
			targetSpeedX -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) {
			targetSpeedX += playerSpeed;
			flipX = 0;
			flipW = 1;
		}

		// Smooth acceleration & friction
		if (targetSpeedX != 0) {
			currentSpeedX += Math.signum(targetSpeedX) * accel;
			if (Math.abs(currentSpeedX) > playerSpeed) {
				currentSpeedX = Math.signum(targetSpeedX) * playerSpeed;
			}
			moving = true;
		} else {
			if (currentSpeedX > 0) {
				currentSpeedX = Math.max(0, currentSpeedX - friction);
			} else if (currentSpeedX < 0) {
				currentSpeedX = Math.min(0, currentSpeedX + friction);
			}
			if (Math.abs(currentSpeedX) > 0.05f) {
				moving = true;
			}
		}

		float xSpeed = currentSpeedX + knockbackX;
		knockbackX *= 0.82f;
		if (Math.abs(knockbackX) < 0.05f) {
			knockbackX = 0f;
		}

		if (!inAir) {
			if (!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			}
		}

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0) {
					resetInAir();
				} else {
					airSpeed = fallSpeedAfterCollision;
				}
				updateXPos(xSpeed);
			}
		} else {
			updateXPos(xSpeed);
		}

		updateDashTrail();
	}

	private void updateDashTrail() {
		if (attacking || parrying || attacking1 || isSwapping || Math.abs(currentSpeedX) > playerSpeed * 0.9f) {
			dashTrails.add(new float[]{hitbox.x, hitbox.y, flipX, flipW, playerAction, aniIndex});
			if (dashTrails.size() > 4) {
				dashTrails.removeFirst();
			}
		} else if (!dashTrails.isEmpty()) {
			dashTrails.removeFirst();
		}
	}

	private void drawDashTrails(Graphics g) {
		if (dashTrails.isEmpty() || g == null) return;
		java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
		java.awt.Composite origComp = g2d.getComposite();
		int i = 0;
		int count = dashTrails.size();
		for (float[] trail : dashTrails) {
			float alpha = (float) (i + 1) / (count + 1) * 0.35f;
			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
			int tx = (int) (trail[0] - xDrawOffset) + (int) trail[2];
			int ty = (int) (trail[1] - yDrawOffset);
			int tw = width * (int) trail[3];
			int act = (int) trail[4];
			int idx = (int) trail[5];
			if (act >= 0 && act < animations.length && idx >= 0 && idx < animations[act].length && animations[act][idx] != null) {
				g2d.drawImage(animations[act][idx], tx, ty, tw, height, null);
			}
			i++;
		}
		g2d.setComposite(origComp);
	}

	private void jump() {
		inAir = true;
		airSpeed = jumpSpeed;
		coyoteTimer = 0;
		jumpBufferTimer = 0;
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


          int varvalue = CharacterPick.getChosen();  
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

    private long lastSkill1Time = 0;
    private long lastSkill2Time = 0;
    private long lastSkill3Time = 0;
    private long lastHadoukenTime = 0;

    private final long COOLDOWN_TIME_SKILL1 = 600;
    private final long COOLDOWN_TIME_SKILL2 = 2000;
    private final long COOLDOWN_TIME_SKILL3 = 3500;
    private final long COOLDOWN_TIME_HADOUKEN = 1500;

    public void adjustCooldownsForPause(long pauseDuration) {
        this.lastSkill1Time += pauseDuration;
        this.lastSkill2Time += pauseDuration;
        this.lastSkill3Time += pauseDuration;
        this.lastHadoukenTime += pauseDuration;
    }

    private long getCurrentOrPausedTime() {
        if (gameInstance != null && gameInstance.isPaused()) {
            return gameInstance.getPauseStartTime();
        }
        return System.currentTimeMillis();
    }

    public long getSkill1CooldownRemaining() {
        long elapsed = getCurrentOrPausedTime() - lastSkill1Time;
        return Math.max(0, COOLDOWN_TIME_SKILL1 - elapsed);
    }

    public long getSkill2CooldownRemaining() {
        long elapsed = getCurrentOrPausedTime() - lastSkill2Time;
        return Math.max(0, COOLDOWN_TIME_SKILL2 - elapsed);
    }

    public long getSkill3CooldownRemaining() {
        long elapsed = getCurrentOrPausedTime() - lastSkill3Time;
        return Math.max(0, COOLDOWN_TIME_SKILL3 - elapsed);
    }

    public long getHadoukenCooldownRemaining() {
        long elapsed = getCurrentOrPausedTime() - lastHadoukenTime;
        return Math.max(0, COOLDOWN_TIME_HADOUKEN - elapsed);
    }

    public void executeSkill1(Player2 enemy) {
        if (System.currentTimeMillis() - lastSkill1Time > COOLDOWN_TIME_SKILL1) {
            main.Game.soundeffects("src\\sounds\\slice-wtr3.wav");
            player1attack1(true);
            
            // Apply damage if in range
            if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                enemy.hurt(utilz.Constants.PlayerConstants.damage1(varvalue));
                enemy.player2getdmg1(true);
                enemy.checkhit2(true);
            }
            
            lastSkill1Time = System.currentTimeMillis();
        }
    }

    public void executeSkill2(Player2 enemy) {
        if (System.currentTimeMillis() - lastSkill2Time > COOLDOWN_TIME_SKILL2) {
            int charId = CharacterPick.getChosen();
            main.Game.soundeffects("src\\sounds\\kny-slice.wav");
            
            if (charId == 2) {
                // Beheaded teleport
                teleportInFrontOfEnemy(enemy);
                player1attack2(true);
                if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                    enemy.hurt(800);
                    enemy.player2getdmg2(true);
                    enemy.checkhit2(true);
                }
            } else if (charId == 3) {
                // Ender Void Swap
                swapPositions(enemy);
                player1attack2(true);
                enemy.hurt(800);
                enemy.player2getdmg2(true);
                enemy.checkhit2(true);
            } else if (charId == 4) {
                // Plague doctor poison
                player1attack2(true);
                if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage2(charId));
                    enemy.applyPoison();
                    enemy.player2getdmg2(true);
                    enemy.checkhit2(true);
                }
            } else if (charId == 5) {
                // Paladin Holy Shield (Invincibility & push back nearby enemies)
                player1attack2(true);
                isInvincible = true;
                shieldStartTime = System.currentTimeMillis();
                shieldDuration = 1500; // 1.5 seconds holy shield
                if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                    enemy.hurt(600);
                    knockbackEnemy(enemy, 50 * Game.SCALE);
                    enemy.player2getdmg2(true);
                    enemy.checkhit2(true);
                }
            } else {
                // Normal slash
                player1attack2(true);
                if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage2(charId));
                    enemy.player2getdmg2(true);
                    enemy.checkhit2(true);
                }
            }
            lastSkill2Time = System.currentTimeMillis();
        }
    }

    public void executeSkill3(Player2 enemy) {
        if (System.currentTimeMillis() - lastSkill3Time > COOLDOWN_TIME_SKILL3) {
            int charId = CharacterPick.getChosen();
            main.Game.soundeffects("src\\sounds\\sword_slash.wav");
            
            if (charId == 2) {
                setBeheadedKnockbackReady(true);
                player1attack3(true);
                if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                    knockbackEnemy(enemy, (main.Game.GAME_WIDTH / 4.0f) * 0.5f);
                    enemy.player2getdmg3(true);
                    enemy.checkhit2(true);
                    setBeheadedKnockbackReady(false);
                }
            } else if (charId == 1) {
                dashAndPushEnemy(enemy, 100 * Game.SCALE);
            } else if (charId == 3) {
                // Ender Void Pull & Stun
                player1attack3(true);
                pullEnemy(enemy);
                enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                enemy.player2getdmg3(true);
                enemy.checkhit2(true);
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
                player1attack3(true);
                if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                    enemy.player2getdmg3(true);
                    enemy.checkhit2(true);
                }
                slashAndDashBack(Game.GAME_WIDTH / 8.0f);
            } else if (charId == 5) {
                // Paladin Holy Strike & Heal 2000 HP
                player1attack3(true);
                if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                    enemy.player2getdmg3(true);
                    enemy.checkhit2(true);
                }
                currentHealth += 800;
                if (currentHealth > maxHealth) {
                    currentHealth = maxHealth;
                }
            } else {
                player1attack3(true);
                if (attackBox1 != null && enemy.getHitbox() != null && attackBox1.intersects(enemy.getHitbox())) {
                    enemy.hurt(utilz.Constants.PlayerConstants.damage3(charId));
                    enemy.player2getdmg3(true);
                    enemy.checkhit2(true);
                }
            }
            lastSkill3Time = System.currentTimeMillis();
        }
    }

    public void executeHadouken() {
        if (System.currentTimeMillis() - lastHadoukenTime > COOLDOWN_TIME_HADOUKEN && canShootHadouken) {
            main.Game.soundeffects("src\\sounds\\sword_slash.wav");
            gameInstance.spawnPlayer1Hadouken();
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
        public void teleportInFrontOfEnemy(Player2 enemy) {
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

        public void swapPositions(Player2 enemy) {
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
        
        public void pullEnemy(Player2 enemy) {
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
            boolean wasRight = right;
            boolean wasLeft = left;
            
            if (flipW == 1) {
                right = true;
                left = false;
            } else {
                right = false;
                left = true;
            }
            
            updateAttackBox();
            
            // Restore original flags
            right = wasRight;
            left = wasLeft;
        }
        
        public void knockbackEnemy(Player2 enemy, float distance) {
            if (enemy == null) return;
            
            // Determine knockback direction based on relative positions
            float knockbackX;
            if (hitbox.x < enemy.getHitbox().x) {
                // Player is to the left, knockback right
                knockbackX = enemy.getHitbox().x + distance;
            } else {
                // Player is to the right, knockback left
                knockbackX = enemy.getHitbox().x - distance;
            }
            
            // Ensure enemy doesn't go out of bounds
            if (knockbackX < 0) knockbackX = 0;
            if (knockbackX > Game.GAME_WIDTH - enemy.getHitbox().width) {
                knockbackX = Game.GAME_WIDTH - enemy.getHitbox().width;
            }
            
            enemy.getHitbox().x = knockbackX;
        }
        
        public void setBeheadedKnockbackReady(boolean ready) {
            this.beheadedKnockbackReady = ready;
        }
        
        public boolean isBeheadedKnockbackReady() {
            return beheadedKnockbackReady;
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
        private Player2 dashTargetEnemy = null;
        private float dashPushDistance = 0;
        
        public void dashAndPushEnemy(Player2 enemy, float dashDistance) {
            if (enemy == null || isDashing || currentHealth <= 0) return; // Don't dash if dead
            
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
            
            player1attack3(true); // Trigger attack animation
        }
        
        public void updateDash() {
            if (isDashing && hitbox != null && currentHealth > 0) { // Don't dash if dead
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
                        if (dashTargetEnemy != null && dashTargetEnemy.getHitbox() != null && !dashTargetEnemy.isdead2()) {
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
                        if (dashTargetEnemy.getHitbox() != null && dashBox.intersects(dashTargetEnemy.getHitbox())) {
                            try {
                                // Deal damage first
                                int damage = utilz.Constants.PlayerConstants.damage3(varvalue);
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
                                final Player2 capturedEnemy = dashTargetEnemy;
                                Timer disableTimer = new Timer();
                                disableTimer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (capturedEnemy != null) {
                                                capturedEnemy.setMovementDisabled(false);
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


}

