package inputs;

import entities.Player1;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import main.Game;

import main.GamePanel;
import main.GameWindow;
import playerz.CharacterPick;
import playerz.LoadScreen;

public class KeyboardInputs implements KeyListener {
        Timer timer;
	private GamePanel gamePanel;
        
        
        private boolean canPress = true;

        private final long COOLDOWN_TIME_SKILL1 = 1000;
        private final long COOLDOWN_TIME_SKILL2 = 3000;
        private final long COOLDOWN_TIME_SKILL3 = 4500;
        private final long COOLDOWN_TIME_HADOUKEN = 2000;
        
        
        private boolean zKeyPressed = false;
        private long zLastPressedTime = 0;
        private boolean xKeyPressed = false;
        private long xLastPressedTime = 0;
        private boolean cKeyPressed = false;
        private long cLastPressedTime = 0;
        private boolean num1KeyPressed = false;
        private long num1LastPressedTime = 0;
        private boolean num2KeyPressed = false;
        private long num2LastPressedTime = 0;
        private boolean num3KeyPressed = false;
        private long num3LastPressedTime = 0;
        private boolean vKeyPressed = false;
        private long vLastPressedTime = 0;
        private boolean num4KeyPressed = false;
        private long num4LastPressedTime = 0;
        
            
        
        
	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
                    timer = new Timer();
                    timer.schedule(new UpdateTask(), 0, 125);
                    timer.schedule(new deadtask(), 0, 1438);
	}
        
class deadtask extends TimerTask{
        public void run() {
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null || gamePanel.getGame().getPlayer2() == null) {
                    return;
                }
                
                boolean player1daed = gamePanel.getGame().getPlayer1().isdead1();
                boolean player2daed = gamePanel.getGame().getPlayer2().isdead2();       
                    
                    gamePanel.getGame().getPlayer1().isdeath2(player2daed);
                    gamePanel.getGame().getPlayer2().isdeath1(player1daed);
                    
                    gamePanel.getGame().reset(player1daed, player2daed);
            } catch (Exception e) {
                // Silently handle errors to prevent crashes
            }
        }
}
        
class UpdateTask extends TimerTask {
        public void run() {
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null || gamePanel.getGame().getPlayer2() == null) {
                    return;
                }
                
                boolean result1 = checkCollisions1();
                boolean result2 = checkCollisions2();
                boolean dead1 = collisionwithdeath1();
                boolean dead2 = collisionwithdeath2();
                boolean player1daed = gamePanel.getGame().getPlayer1().isdead1();
                boolean player2daed = gamePanel.getGame().getPlayer2().isdead2();       
                boolean finished = gamePanel.getGame().finished();

                    gamePanel.getGame().healthy1(deadhealth1());
                    gamePanel.getGame().healthy2(deadhealth2());
                
                    gamePanel.getGame().getPlayer2().checkhit2(result2);
                    gamePanel.getGame().getPlayer1().checkhit1(result1);
                    gamePanel.getGame().getPlayer2().checkhit2(result1);
                    gamePanel.getGame().getPlayer1().checkhit1(result2);
                
                // Check for beheaded C skill knockback (only when attackbox hits)
                checkBeheadedKnockback(result1);
                checkBeheadedKnockback(result2); // Also check Player2's knockback
                    
                    gamePanel.getGame().getPlayer1().getDeath1(dead1);
                    gamePanel.getGame().getPlayer2().getDeath2(dead2);
                    
                // Check hadouken collisions
                checkHadoukenCollisions();
                    
                    gamePanel.getGame().getPlayer1().finish(finished);
                    gamePanel.getGame().getPlayer2().finish(finished);
            } catch (Exception e) {
                // Silently handle all errors to prevent crashes
                // Don't print stack trace to avoid spam
            }
        }
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
        
        
        
        private boolean deadhealth1(){
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null) {
                    return false;
                }
            return gamePanel.getGame().getPlayer1().deadlife1();
            } catch (Exception e) {
                return false;
            }
        }
        private boolean deadhealth2(){
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer2() == null) {
                    return false;
                }
            return gamePanel.getGame().getPlayer2().deadlife2();
            } catch (Exception e) {
                return false;
            }
        }
        
        
        
        private boolean checkCollisions1() {
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null || gamePanel.getGame().getPlayer2() == null) {
                    return false;
                }
            Rectangle2D.Float ATTACKBOX1 = gamePanel.getGame().getPlayer1().attackBox1;
            Rectangle2D.Float HITBOX2 = gamePanel.getGame().getPlayer2().getHitbox();
                if (ATTACKBOX1 != null && HITBOX2 != null && ATTACKBOX1.intersects(HITBOX2)) {
                return true;
                }
            } catch (Exception e) {
                // Silently handle errors
            }
                        return false;
            } 
        
        private boolean checkCollisions2() {
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null || gamePanel.getGame().getPlayer2() == null) {
                    return false;
                }
            Rectangle2D.Float ATTACKBOX2 = gamePanel.getGame().getPlayer2().attackBox2;
            Rectangle2D.Float HITBOX1 = gamePanel.getGame().getPlayer1().getHitbox(); 
            
                if (ATTACKBOX2 != null && HITBOX1 != null && ATTACKBOX2.intersects(HITBOX1)) {
                return true;
                }
            } catch (Exception e) {
                // Silently handle errors
            }
                return false;
            }
        
        private boolean collisionwithdeath1(){
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getdeath() == null || gamePanel.getGame().getPlayer1() == null) {
                    return false;
                }
            Rectangle2D.Float DEATH = gamePanel.getGame().getdeath().getHitbox();
            Rectangle2D.Float HITBOX1 = gamePanel.getGame().getPlayer1().getHitbox(); 
            
                if (DEATH != null && HITBOX1 != null && DEATH.intersects(HITBOX1)) {
                return true;
                }
            } catch (Exception e) {
                // Silently handle errors
            }
                return false; 
        }
        
        private boolean collisionwithdeath2(){
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getdeath() == null || gamePanel.getGame().getPlayer2() == null) {
                    return false;
                }
            Rectangle2D.Float DEATH = gamePanel.getGame().getdeath().getHitbox();
            Rectangle2D.Float HITBOX2 = gamePanel.getGame().getPlayer2().getHitbox();
                if (DEATH != null && HITBOX2 != null && DEATH.intersects(HITBOX2)) {
                return true;
                }
            } catch (Exception e) {
                // Silently handle errors
            }
                return false; 
        }
        
        private void checkBeheadedKnockback(boolean attackHit) {
            try {
                if (!attackHit || gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null || gamePanel.getGame().getPlayer2() == null) {
                    return;
                }
                
                // Check if player1 is beheaded and C skill is ready
                playerz.CharacterPick p1 = new playerz.CharacterPick();
                int charId = p1.getChosen();
                
                if (charId == 2 && gamePanel.getGame().getPlayer1().isBeheadedKnockbackReady()) {
                    // Calculate 50% of 1/4 map distance (50% reduction)
                    float knockbackDistance = (main.Game.GAME_WIDTH / 4.0f) * 0.5f; // 50% of original
                    
                    // Knockback enemy
                    gamePanel.getGame().getPlayer1().knockbackEnemy(gamePanel.getGame().getPlayer2(), knockbackDistance);
                    
                    // Reset flag
                    gamePanel.getGame().getPlayer1().setBeheadedKnockbackReady(false);
                }
                
                // Check if player2 is beheaded and Numpad 3 skill is ready
                playerz.CharacterPick p2 = new playerz.CharacterPick();
                int charId2 = p2.getPicked();
                
                if (charId2 == 2 && gamePanel.getGame().getPlayer2().isBeheadedKnockbackReady()) {
                    // Calculate 50% of 1/4 map distance (50% reduction)
                    float knockbackDistance = (main.Game.GAME_WIDTH / 4.0f) * 0.5f; // 50% of original
                    
                    // Knockback enemy
                    gamePanel.getGame().getPlayer2().knockbackEnemy(gamePanel.getGame().getPlayer1(), knockbackDistance);
                    
                    // Reset flag
                    gamePanel.getGame().getPlayer2().setBeheadedKnockbackReady(false);
                }
            } catch (Exception e) {
                // Silently handle errors to prevent crashes
            }
        }
        
        private void checkHadoukenCollisions() {
            try {
                if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null || gamePanel.getGame().getPlayer2() == null) {
                    return;
                }
                
                Rectangle2D.Float player1Hitbox = gamePanel.getGame().getPlayer1().getHitbox();
                Rectangle2D.Float player2Hitbox = gamePanel.getGame().getPlayer2().getHitbox();
                
                if (player1Hitbox == null || player2Hitbox == null) {
                    return;
                }
            
            // Check player2 hadoukens against player1
            java.util.ArrayList<entities.Hadouken> player2Hadoukens = gamePanel.getGame().getPlayer2Hadoukens();
            if (player2Hadoukens != null) {
                java.util.Iterator<entities.Hadouken> it2 = player2Hadoukens.iterator();
                while (it2.hasNext()) {
                    entities.Hadouken h = it2.next();
                    if (h != null && h.isActive() && h.getAttackBox() != null) {
                        if (h.getAttackBox().intersects(player1Hitbox)) {
                            try {
                                // Spawn spark at collision point
                                if (h.getAttackBox() != null) {
                                    float sparkX = h.getAttackBox().x + h.getAttackBox().width / 2;
                                    float sparkY = h.getAttackBox().y + h.getAttackBox().height / 2;
                                    gamePanel.getGame().spawnCollisionSpark(sparkX, sparkY);
                                }
                                
                                // Deal damage to player1
                                gamePanel.getGame().getPlayer1().hurt(h.getDamage());
                                h.setActive(false);
                                it2.remove();
                            } catch (Exception e) {
                                // Handle error and continue
                                h.setActive(false);
                                it2.remove();
                            }
                        }
                    }
                }
            }
            
            // Check player1 hadoukens against player2
            java.util.ArrayList<entities.Hadouken> player1Hadoukens = gamePanel.getGame().getPlayer1Hadoukens();
            if (player1Hadoukens != null) {
                java.util.Iterator<entities.Hadouken> it1 = player1Hadoukens.iterator();
                while (it1.hasNext()) {
                    entities.Hadouken h = it1.next();
                    if (h != null && h.isActive() && h.getAttackBox() != null) {
                        if (h.getAttackBox().intersects(player2Hitbox)) {
                            try {
                                // Spawn spark at collision point
                                if (h.getAttackBox() != null) {
                                    float sparkX = h.getAttackBox().x + h.getAttackBox().width / 2;
                                    float sparkY = h.getAttackBox().y + h.getAttackBox().height / 2;
                                    gamePanel.getGame().spawnCollisionSpark(sparkX, sparkY);
                                }
                                
                                // Deal damage to player2
                                gamePanel.getGame().getPlayer2().hurt(h.getDamage());
                                h.setActive(false);
                                it1.remove();
                            } catch (Exception e) {
                                // Handle error and continue
                                h.setActive(false);
                                it1.remove();
                            }
                        }
                    }
                }
            }
            } catch (Exception e) {
                // Silently handle errors to prevent crashes
            }
        }

        
        
        
        
/*HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH*/
	@Override
	public void keyTyped(KeyEvent e) {
            
	}

	@Override
	public void keyReleased(KeyEvent e) {
             Rectangle2D.Float ATTACKBOX1 = gamePanel.getGame().getPlayer1().attackBox1;
             Rectangle2D.Float ATTACKBOX2 = gamePanel.getGame().getPlayer2().attackBox2;
             Rectangle2D.Float HITBOX2 = gamePanel.getGame().getPlayer2().getHitbox();
             Rectangle2D.Float HITBOX1 = gamePanel.getGame().getPlayer1().getHitbox(); 
            
		switch (e.getKeyCode()) {
                    
		case KeyEvent.VK_A:
                        
                    

			gamePanel.getGame().getPlayer1().setLeft(false);
			break;
		case KeyEvent.VK_D:
                        
                    
			gamePanel.getGame().getPlayer1().setRight(false);
			break;
		case KeyEvent.VK_W:
                        

			gamePanel.getGame().getPlayer1().setJump(false);
			break;
        
        case KeyEvent.VK_S:
            gamePanel.getGame().getPlayer1().setDefend(false);
            break;
                    
                        
                case KeyEvent.VK_Z:

                        gamePanel.getGame().getPlayer2().player2getdmg1(false);
                        zKeyPressed = false;
                        break; 
                case KeyEvent.VK_X:

                        gamePanel.getGame().getPlayer2().player2getdmg2(false);
                        gamePanel.getGame().getPlayer1().player1attack2(false);
                        xKeyPressed = false;
                        break; 
                case KeyEvent.VK_C:

                        
                        gamePanel.getGame().getPlayer2().player2getdmg3(false);
                        gamePanel.getGame().getPlayer1().player1attack3(false);
                        cKeyPressed = false;
                        break;        
                        
                        
                        
                case KeyEvent.VK_LEFT:
                        

			gamePanel.getGame().getPlayer2().setLeft2(false);
			break;
		case KeyEvent.VK_RIGHT:
                        

			gamePanel.getGame().getPlayer2().setRight2(false);
			break;
		case KeyEvent.VK_UP:
                        

			gamePanel.getGame().getPlayer2().setJump2(false);
			break;
                   
                case KeyEvent.VK_NUMPAD1:

                        
                        gamePanel.getGame().getPlayer1().player1getdmg1(false);
                        num1KeyPressed = false;
                        break;         
                        
                case KeyEvent.VK_NUMPAD2:

                        
                        gamePanel.getGame().getPlayer1().player1getdmg2(false);
                        gamePanel.getGame().getPlayer2().player2attack2(false);
                        num2KeyPressed = false;
                        break; 
                case KeyEvent.VK_NUMPAD3:

                        
                        gamePanel.getGame().getPlayer1().player1getdmg3(false);
                        gamePanel.getGame().getPlayer2().player2attack3(false);
                        num3KeyPressed = false;
                        break;
                case KeyEvent.VK_V:
                        vKeyPressed = false;
                        break;
                case KeyEvent.VK_NUMPAD4:
                        num4KeyPressed = false;
                        break;    
                case KeyEvent.VK_ENTER:
                    /*CharacterPick cp = new CharacterPick();
                    cp.setLocationRelativeTo(null);
                    cp.setVisible(true);
                    gamePanel.disable();*/
                    
                        break;        
                        
                        
		}   
        }
        
	@Override
	public void keyPressed(KeyEvent e) {
            
            
            
            switch (e.getKeyCode()) {
                   
                case KeyEvent.VK_ENTER:
                    gamePanel.getGame().stopsounds(true);
                    
                    LoadScreen cp = new LoadScreen();
                    cp.setLocationRelativeTo(null);
                    cp.setVisible(true);
                    gamePanel.disable();
                    gamePanel.setVisible(false);
                    
                        break;

                 
                case KeyEvent.VK_ESCAPE:   
                    
                    int z = JOptionPane.showConfirmDialog(null, "ARE YOU SURE YOU WANT TO EXIT?", "EXIT?", JOptionPane.YES_NO_OPTION);
                        if(z == 0){System.exit(0);}else return;
                        
                        break;
                    
		case KeyEvent.VK_A: 
                        

			gamePanel.getGame().getPlayer1().setLeft(true);
			break;
		case KeyEvent.VK_D:
                        
                    

			gamePanel.getGame().getPlayer1().setRight(true);
			break;
		case KeyEvent.VK_W:
                        
                    

			gamePanel.getGame().getPlayer1().setJump(true);
			break;

            //defend
            case KeyEvent.VK_S:
            gamePanel.getGame().getPlayer1().setDefend(true);
            break;    
                        
                        
                case KeyEvent.VK_Z:   
                    if (!zKeyPressed && System.currentTimeMillis() - zLastPressedTime > COOLDOWN_TIME_SKILL1) {
                        soundeffects("src\\sounds\\slice-wtr3.wav");

                        
                        gamePanel.getGame().getPlayer2().player2getdmg1(true);
			gamePanel.getGame().getPlayer1().player1attack1(true);
                        zKeyPressed = true;
                        zLastPressedTime = System.currentTimeMillis();
                    }    
			break;     
                case KeyEvent.VK_X:
                    if (!xKeyPressed && System.currentTimeMillis() - xLastPressedTime > COOLDOWN_TIME_SKILL2) {
                        // Check if player1 is beheaded (character 2)
                        playerz.CharacterPick p1 = new playerz.CharacterPick();
                        int charId = p1.getChosen();
                        
                        if (charId == 2) {
                            // Beheaded teleport skill
                            soundeffects("src\\sounds\\kny-slice.wav");
                            
                            // Teleport in front of enemy
                            gamePanel.getGame().getPlayer1().teleportInFrontOfEnemy(gamePanel.getGame().getPlayer2());
                            
                            // Schedule slash after 0.3 seconds (300ms)
                            Timer teleportTimer = new Timer();
                            teleportTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        // Trigger attack animation
                                        gamePanel.getGame().getPlayer1().player1attack2(true);
                                        
                                        // Update attackbox position after teleport
                                        gamePanel.getGame().getPlayer1().updateAttackBoxAfterTeleport();
                                        
                                        // Small delay to ensure attackbox is updated
                                        Timer damageTimer = new Timer();
                                        damageTimer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                try {
                                                    // Force update attackbox again
                                                    gamePanel.getGame().getPlayer1().updateAttackBoxAfterTeleport();
                                                    
                                                    // Check collision manually and deal damage
                                                    Rectangle2D.Float attackBox1 = gamePanel.getGame().getPlayer1().attackBox1;
                                                    Rectangle2D.Float hitbox2 = gamePanel.getGame().getPlayer2().getHitbox();
                                                    
                                                    if (attackBox1 != null && hitbox2 != null && attackBox1.intersects(hitbox2)) {
                                                        // Deal damage directly - 3500 for teleport skill
                                                        int damage = 3500;
                                                        gamePanel.getGame().getPlayer2().hurt(damage);
                                                        
                                                        // Set flags for consistency with normal attack system
                                                        gamePanel.getGame().getPlayer2().player2getdmg2(true);
                                                        gamePanel.getGame().getPlayer2().checkhit2(true);
                                                    } else {
                                                        // Still set flags even if no collision (for animation)
                                                        gamePanel.getGame().getPlayer2().player2getdmg2(true);
                                                    }
                                                } catch (Exception e) {
                                                    // Handle errors
                                                }
                                            }
                                        }, 50); // Small delay to ensure attackbox is positioned
                                    } catch (Exception e) {
                                        // Handle errors
                                    }
                                }
                            }, 300); // 0.3 seconds = 300ms
                        } else if (charId == 4) {
                            // Plague Doctor skill 2 - Poison slash
                            soundeffects("src\\sounds\\kny-slice.wav");
                            
                            // Trigger attack animation
                            gamePanel.getGame().getPlayer1().player1attack2(true);
                            
                            // Check collision and apply poison
                            Timer poisonTimer = new Timer();
                            poisonTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        Rectangle2D.Float attackBox1 = gamePanel.getGame().getPlayer1().attackBox1;
                                        Rectangle2D.Float hitbox2 = gamePanel.getGame().getPlayer2().getHitbox();
                                        
                                        if (attackBox1 != null && hitbox2 != null && attackBox1.intersects(hitbox2)) {
                                            // Deal normal damage first
                                            int damage = utilz.Constants.PlayerConstants.damage2(charId);
                                            gamePanel.getGame().getPlayer2().hurt(damage);
                                            
                                            // Apply poison (3% HP per second for 5 seconds)
                                            gamePanel.getGame().getPlayer2().applyPoison();
                                            
                                            // Set flags
                                            gamePanel.getGame().getPlayer2().player2getdmg2(true);
                                            gamePanel.getGame().getPlayer2().checkhit2(true);
                                        } else {
                                            // Still set flags even if no collision (for animation)
                                            gamePanel.getGame().getPlayer2().player2getdmg2(true);
                                        }
                                    } catch (Exception e) {
                                        // Handle errors
                                    }
                                }
                            }, 100); // Small delay for attackbox
                        } else if (charId == 1) {
                            // Rhino skill 2 - Normal slash with damage
                            soundeffects("src\\sounds\\kny-slice.wav");
                            
                            // Trigger attack animation
                            gamePanel.getGame().getPlayer1().player1attack2(true);
                            
                            // Check collision and deal damage
                            Timer damageTimer = new Timer();
                            damageTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        Rectangle2D.Float attackBox1 = gamePanel.getGame().getPlayer1().attackBox1;
                                        Rectangle2D.Float hitbox2 = gamePanel.getGame().getPlayer2().getHitbox();
                                        
                                        if (attackBox1 != null && hitbox2 != null && attackBox1.intersects(hitbox2)) {
                                            // Deal normal damage
                                            int damage = utilz.Constants.PlayerConstants.damage2(charId);
                                            gamePanel.getGame().getPlayer2().hurt(damage);
                                            
                                            // Set flags
                                            gamePanel.getGame().getPlayer2().player2getdmg2(true);
                                            gamePanel.getGame().getPlayer2().checkhit2(true);
                                        } else {
                                            // Still set flags even if no collision (for animation)
                                            gamePanel.getGame().getPlayer2().player2getdmg2(true);
                                        }
                                    } catch (Exception e) {
                                        // Handle errors
                                    }
                                }
                            }, 100); // Small delay for attackbox
                        } else {
                            // Normal skill for other characters
                            soundeffects("src\\sounds\\kny-slice.wav");
                        gamePanel.getGame().getPlayer2().player2getdmg2(true);
                        gamePanel.getGame().getPlayer1().player1attack2(true);
                        }
                        
                        xKeyPressed = true;
                        xLastPressedTime = System.currentTimeMillis();
                    }
                        break; 
                case KeyEvent.VK_C:
                    if (!cKeyPressed && System.currentTimeMillis() - cLastPressedTime > COOLDOWN_TIME_SKILL3) {
                        // Check if player1 is beheaded (character 2)
                        playerz.CharacterPick p1 = new playerz.CharacterPick();
                        int charId = p1.getChosen();
                        
                        if (charId == 2) {
                            // Beheaded skill - knockback will happen in UpdateTask when attackbox hits
                            soundeffects("src\\sounds\\sword_slash.wav");
                            
                            // Set flag to enable knockback on hit
                            gamePanel.getGame().getPlayer1().setBeheadedKnockbackReady(true);
                            
                            // Still do damage
                            gamePanel.getGame().getPlayer2().player2getdmg3(true);
                            gamePanel.getGame().getPlayer1().player1attack3(true);
                        } else if (charId == 1) {
                            // Rhino skill - dash and push enemy
                            soundeffects("src\\sounds\\sword_slash.wav");
                            
                            // Dash distance (can be adjusted)
                            float dashDistance = 100 * Game.SCALE;
                            
                            // Perform dash and push enemy
                            gamePanel.getGame().getPlayer1().dashAndPushEnemy(
                                gamePanel.getGame().getPlayer2(), 
                                dashDistance
                            );
                            
                            // Trigger attack animation
                            gamePanel.getGame().getPlayer1().player1attack3(true);
                        } else if (charId == 4) {
                            // Plague Doctor skill 3 - Slash and dash back
                            soundeffects("src\\sounds\\sword_slash.wav");
                            
                            // Trigger attack animation
                            gamePanel.getGame().getPlayer1().player1attack3(true);
                            
                            // Check collision and deal damage, then dash back
                            Timer dashBackTimer = new Timer();
                            dashBackTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        Rectangle2D.Float attackBox1 = gamePanel.getGame().getPlayer1().attackBox1;
                                        Rectangle2D.Float hitbox2 = gamePanel.getGame().getPlayer2().getHitbox();
                                        
                                        if (attackBox1 != null && hitbox2 != null && attackBox1.intersects(hitbox2)) {
                                            // Deal normal damage
                                            int damage = utilz.Constants.PlayerConstants.damage3(charId);
                                            gamePanel.getGame().getPlayer2().hurt(damage);
                                            
                                            // Set flags
                                            gamePanel.getGame().getPlayer2().player2getdmg3(true);
                                            gamePanel.getGame().getPlayer2().checkhit2(true);
                                        } else {
                                            // Still set flags even if no collision (for animation)
                                            gamePanel.getGame().getPlayer2().player2getdmg3(true);
                                        }
                                        
                                        // Dash back 1/8 of map distance
                                        float dashBackDistance = Game.GAME_WIDTH / 8.0f;
                                        gamePanel.getGame().getPlayer1().slashAndDashBack(dashBackDistance);
                                    } catch (Exception e) {
                                        // Handle errors
                                    }
                                }
                            }, 100); // Small delay for attackbox
                        } else {
                            // Normal skill for other characters
                            soundeffects("src\\sounds\\sword_slash.wav");
                            gamePanel.getGame().getPlayer2().player2getdmg3(true);
                            gamePanel.getGame().getPlayer1().player1attack3(true);
                        }
                        
                        cKeyPressed = true;
                        cLastPressedTime = System.currentTimeMillis();
                    }
                        break;   
                        
                        
                case KeyEvent.VK_LEFT:
                        

			gamePanel.getGame().getPlayer2().setLeft2(true);
			break;
		case KeyEvent.VK_RIGHT:
                        

			gamePanel.getGame().getPlayer2().setRight2(true);
			break;
		case KeyEvent.VK_UP:
                        

			gamePanel.getGame().getPlayer2().setJump2(true);
			break;
                        
                        
                case KeyEvent.VK_NUMPAD1:
                    

                    if (!num2KeyPressed && System.currentTimeMillis() - num1LastPressedTime > COOLDOWN_TIME_SKILL1) {  
                        soundeffects("src\\sounds\\slice-wtr3.wav");
                     
                        gamePanel.getGame().getPlayer1().player1getdmg1(true);
			gamePanel.getGame().getPlayer2().player2attack1(true);
                        
                        num1KeyPressed = true;
                        num1LastPressedTime = System.currentTimeMillis();
                    }    
			break;     
                case KeyEvent.VK_NUMPAD2:
                    if (!num2KeyPressed && System.currentTimeMillis() - num2LastPressedTime > COOLDOWN_TIME_SKILL2) {
                        // Check if player2 is beheaded (character 2)
                        playerz.CharacterPick p2 = new playerz.CharacterPick();
                        int charId2 = p2.getPicked();
                        
                        if (charId2 == 2) {
                            // Beheaded teleport skill for Player 2
                       soundeffects("src\\sounds\\kny-slice.wav");
  
                            // Teleport in front of enemy
                            gamePanel.getGame().getPlayer2().teleportInFrontOfEnemy(gamePanel.getGame().getPlayer1());
                            
                            // Schedule slash after 0.3 seconds (300ms)
                            Timer teleportTimer = new Timer();
                            teleportTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        // Trigger attack animation
                                        gamePanel.getGame().getPlayer2().player2attack2(true);
                                        
                                        // Update attackbox position after teleport
                                        gamePanel.getGame().getPlayer2().updateAttackBoxAfterTeleport();
                                        
                                        // Small delay to ensure attackbox is updated
                                        Timer damageTimer = new Timer();
                                        damageTimer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                try {
                                                    // Force update attackbox again
                                                    gamePanel.getGame().getPlayer2().updateAttackBoxAfterTeleport();
                                                    
                                                    // Check collision manually and deal damage
                                                    Rectangle2D.Float attackBox2 = gamePanel.getGame().getPlayer2().attackBox2;
                                                    Rectangle2D.Float hitbox1 = gamePanel.getGame().getPlayer1().getHitbox();
                                                    
                                                    if (attackBox2 != null && hitbox1 != null && attackBox2.intersects(hitbox1)) {
                                                        // Deal damage directly - 3500 for teleport skill
                                                        int damage = 3500;
                                                        gamePanel.getGame().getPlayer1().hurt(damage);
                                                        
                                                        // Set flags for consistency with normal attack system
                                                        gamePanel.getGame().getPlayer1().player1getdmg2(true);
                                                        gamePanel.getGame().getPlayer1().checkhit1(true);
                                                    } else {
                                                        // Still set flags even if no collision (for animation)
                                                        gamePanel.getGame().getPlayer1().player1getdmg2(true);
                                                    }
                                                } catch (Exception e) {
                                                    // Handle errors
                                                }
                                            }
                                        }, 50); // Small delay to ensure attackbox is positioned
                                    } catch (Exception e) {
                                        // Handle errors
                                    }
                                }
                            }, 300); // 0.3 seconds = 300ms
                        } else {
                            // Normal skill for other characters
                            soundeffects("src\\sounds\\kny-slice.wav");
                        gamePanel.getGame().getPlayer1().player1getdmg2(true);
                        gamePanel.getGame().getPlayer2().player2attack2(true);
                        }
                        
                        num2KeyPressed = true;
                        num2LastPressedTime = System.currentTimeMillis();
                    }
                        break; 
                        
                        
                case KeyEvent.VK_NUMPAD3:
                    if (!num3KeyPressed && System.currentTimeMillis() - num3LastPressedTime > COOLDOWN_TIME_SKILL3) {
                        // Check character ID
                        playerz.CharacterPick p2 = new playerz.CharacterPick();
                        int charId2 = p2.getPicked();
                        
                        if (charId2 == 2) {
                            // Beheaded knockback skill for Player 2
                            soundeffects("src\\sounds\\sword_slash.wav");
                            
                            // Set knockback ready flag and trigger attack
                            gamePanel.getGame().getPlayer2().setBeheadedKnockbackReady(true);
                            gamePanel.getGame().getPlayer2().player2attack3(true);
                        } else if (charId2 == 1) {
                            // Rhino skill - dash and push enemy for Player 2
                            soundeffects("src\\sounds\\sword_slash.wav");
                            
                            // Dash distance (can be adjusted)
                            float dashDistance = 100 * Game.SCALE;
                            
                            // Perform dash and push enemy
                            gamePanel.getGame().getPlayer2().dashAndPushEnemy(
                                gamePanel.getGame().getPlayer1(), 
                                dashDistance
                            );
                            
                            // Trigger attack animation
                            gamePanel.getGame().getPlayer2().player2attack3(true);
                        } else if (charId2 == 4) {
                            // Plague Doctor skill 3 - Slash and dash back for Player 2
                            soundeffects("src\\sounds\\sword_slash.wav");
                            
                            // Trigger attack animation
                            gamePanel.getGame().getPlayer2().player2attack3(true);
                            
                            // Check collision and deal damage, then dash back
                            Timer dashBackTimer = new Timer();
                            dashBackTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        Rectangle2D.Float attackBox2 = gamePanel.getGame().getPlayer2().attackBox2;
                                        Rectangle2D.Float hitbox1 = gamePanel.getGame().getPlayer1().getHitbox();
                                        
                                        if (attackBox2 != null && hitbox1 != null && attackBox2.intersects(hitbox1)) {
                                            // Deal normal damage
                                            int damage = utilz.Constants.PlayerConstants.damage3(charId2);
                                            gamePanel.getGame().getPlayer1().hurt(damage);
                                            
                                            // Set flags
                                            gamePanel.getGame().getPlayer1().player1getdmg3(true);
                                            gamePanel.getGame().getPlayer1().checkhit1(true);
                                        } else {
                                            // Still set flags even if no collision (for animation)
                                            gamePanel.getGame().getPlayer1().player1getdmg3(true);
                                        }
                                        
                                        // Dash back 1/8 of map distance
                                        float dashBackDistance = Game.GAME_WIDTH / 8.0f;
                                        gamePanel.getGame().getPlayer2().slashAndDashBack(dashBackDistance);
                                    } catch (Exception e) {
                                        // Handle errors
                                    }
                                }
                            }, 100); // Small delay for attackbox
                        } else {
                            // Normal skill for other characters
                            soundeffects("src\\sounds\\sword_slash.wav");
                            gamePanel.getGame().getPlayer1().player1getdmg3(true);
                            gamePanel.getGame().getPlayer2().player2attack3(true);
                        }
                       
                        num3KeyPressed = true;
                        num3LastPressedTime = System.currentTimeMillis();
                    }    
                        break;
                case KeyEvent.VK_V:
                    // Player 1 Hadouken
                    if (!vKeyPressed && System.currentTimeMillis() - vLastPressedTime > COOLDOWN_TIME_HADOUKEN) {
                        soundeffects("src\\sounds\\sword_slash.wav");
                        gamePanel.getGame().spawnPlayer1Hadouken();
                        vKeyPressed = true;
                        vLastPressedTime = System.currentTimeMillis();
                        // Reset cooldown flag after cooldown period
                        Timer cooldownTimer = new Timer();
                        cooldownTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                vKeyPressed = false;
                                gamePanel.getGame().getPlayer1().setCanShootHadouken(true);
                            }
                        }, COOLDOWN_TIME_HADOUKEN);
                    }
                    break;
                case KeyEvent.VK_NUMPAD4:
                    // Player 2 Hadouken
                    if (!num4KeyPressed && System.currentTimeMillis() - num4LastPressedTime > COOLDOWN_TIME_HADOUKEN) {
                        soundeffects("src\\sounds\\sword_slash.wav");
                        gamePanel.getGame().spawnPlayer2Hadouken();
                        num4KeyPressed = true;
                        num4LastPressedTime = System.currentTimeMillis();
                        // Reset cooldown flag after cooldown period
                        Timer cooldownTimer = new Timer();
                        cooldownTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                num4KeyPressed = false;
                                gamePanel.getGame().getPlayer2().setCanShootHadouken(true);
                            }
                        }, COOLDOWN_TIME_HADOUKEN);
                    }    
                        break;
		} 

	}
        
        
         public static void restart() {
            // Set shouldStop to true
            boolean shouldStop = true;
       
       // Restart the program
       try {
           // Get the current command-line arguments
           String javaBin = System.getProperty("java.home") + "/bin/java";
           String classpath = System.getProperty("java.class.path");
           String className = Game.class.getCanonicalName();
           String[] command = new String[] { javaBin, "-cp", classpath, className };
           
           // Start a new process
           ProcessBuilder builder = new ProcessBuilder(command);
           builder.start();
           
           // Exit the current process
           System.exit(0);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
            
}



                 
                        


