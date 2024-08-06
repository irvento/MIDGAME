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
        
            
        
        
	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
                    timer = new Timer();
                    timer.schedule(new UpdateTask(), 0, 125);
                    timer.schedule(new deadtask(), 0, 1438);
	}
        
class deadtask extends TimerTask{
        public void run() {
                boolean player1daed = gamePanel.getGame().getPlayer1().isdead1();
                boolean player2daed = gamePanel.getGame().getPlayer2().isdead2();       
                    
                    gamePanel.getGame().getPlayer1().isdeath2(player2daed);
                    gamePanel.getGame().getPlayer2().isdeath1(player1daed);
                    
                    gamePanel.getGame().reset(player1daed, player2daed);

                    
        }
}
        
class UpdateTask extends TimerTask {
        public void run() {
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
                    
                    gamePanel.getGame().getPlayer1().getDeath1(dead1);
                    gamePanel.getGame().getPlayer2().getDeath2(dead2);
                    

                    
                    gamePanel.getGame().getPlayer1().finish(finished);
                    gamePanel.getGame().getPlayer2().finish(finished);
                    
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
            return gamePanel.getGame().getPlayer1().deadlife1();
        }
        private boolean deadhealth2(){
            return gamePanel.getGame().getPlayer2().deadlife2();
        }
        
        
        
        private boolean checkCollisions1() {
            Rectangle2D.Float ATTACKBOX1 = gamePanel.getGame().getPlayer1().attackBox1;
            Rectangle2D.Float HITBOX2 = gamePanel.getGame().getPlayer2().getHitbox();
            if (ATTACKBOX1.intersects(HITBOX2)) {
                
                return true;
            }
                        return false;
            } 
        
        private boolean checkCollisions2() {
            Rectangle2D.Float ATTACKBOX2 = gamePanel.getGame().getPlayer2().attackBox2;
            Rectangle2D.Float HITBOX1 = gamePanel.getGame().getPlayer1().getHitbox(); 
            
            if (ATTACKBOX2.intersects(HITBOX1)) {
                return true;
            }

                return false;
            }
        
        private boolean collisionwithdeath1(){
            Rectangle2D.Float DEATH = gamePanel.getGame().getdeath().getHitbox();
            Rectangle2D.Float HITBOX1 = gamePanel.getGame().getPlayer1().getHitbox(); 
            
            if (DEATH.intersects(HITBOX1)) {
                return true;
            }

                return false; 
        }
        
        private boolean collisionwithdeath2(){
            Rectangle2D.Float DEATH = gamePanel.getGame().getdeath().getHitbox();
            Rectangle2D.Float HITBOX2 = gamePanel.getGame().getPlayer2().getHitbox();
            if (DEATH.intersects(HITBOX2)) {
                return true;
            }
                return false; 
            
            
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
                        soundeffects("src\\sounds\\kny-slice.wav");

                        
                        gamePanel.getGame().getPlayer2().player2getdmg2(true);
                        gamePanel.getGame().getPlayer1().player1attack2(true);
                        
                        xKeyPressed = true;
                        xLastPressedTime = System.currentTimeMillis();
                    }
                        break; 
                case KeyEvent.VK_C:
                   
                    
                    if (!cKeyPressed && System.currentTimeMillis() - cLastPressedTime > COOLDOWN_TIME_SKILL3) {
                         soundeffects("src\\sounds\\sword_slash.wav");
                        
                        
                        gamePanel.getGame().getPlayer2().player2getdmg3(true);
                        gamePanel.getGame().getPlayer1().player1attack3(true);
                        
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
                       soundeffects("src\\sounds\\kny-slice.wav");
  
                        gamePanel.getGame().getPlayer1().player1getdmg2(true);
                        gamePanel.getGame().getPlayer2().player2attack2(true);
                        
                        num2KeyPressed = true;
                        num2LastPressedTime = System.currentTimeMillis();
                    }
                        break; 
                        
                        
                case KeyEvent.VK_NUMPAD3:
                    
                    
                    if (!num3KeyPressed && System.currentTimeMillis() - num3LastPressedTime > COOLDOWN_TIME_SKILL3) {
                       soundeffects("src\\sounds\\sword_slash.wav");

                        
                        gamePanel.getGame().getPlayer1().player1getdmg3  (true);
                        gamePanel.getGame().getPlayer2().player2attack3(true);
                       
                        num3KeyPressed = true;
                        num3LastPressedTime = System.currentTimeMillis();
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



                 
                        


