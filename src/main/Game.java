package main;

import java.awt.Graphics;

import entities.Player1;
import entities.Player2;
import entities.trapp;
import inputs.KeyboardInputs;
import java.awt.Image;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import levels.LevelManager;
import playerz.CharacterPick;

public class Game implements Runnable {

    
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
        private trapp trap;
	private Player1 player1;
        private Player2 player2;
        private KeyboardInputs KI;
	private LevelManager levelManager;
        
        private boolean health1 = false, health2 = false, killed1 = false, killed2 = false, win = false;
        private boolean rr1 = false, rr2 = false, rr3 = false, playagain = true;
        private boolean round1 = true, round2 = false, round3 = false, round4 = false, round5 = false;
        private boolean stopsound = false, stopgif = false;
        
        private boolean start = false, won = false, player1wins = false, player2wins = false, winwin = false, gif1 = true, gif2 = false, gif3 = false, gif4 = false, gif5 = false, gif6 = false, gif7 = false, dialog = false;
        private int A = 0, B = 0, a, b;
        private Clip clip;
        
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);//64px
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;//1664px
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;//896px
        
        private MusicPlayer sound;
        private MusicPlayer musicPlayer = new MusicPlayer();      
        private CharacterPick p1 = new CharacterPick();
        
	public Game() {
                System.out.println("mapa "+ p1.getmapinfo());
		initClasses();
                
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop(); 
                /*if(true){
                music();
                }*/
            
       
             
	}
        
        private void music(){
        try {
            Thread.sleep(1500); // delay for 10.5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        musicPlayer.play1(!stopsound);

        }
        
        private void initClasses(){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    soundeffects("src\\sounds\\round-1-fight.wav");
                    rr1 = true;
                    musicPlayer.play1(!stopsound);
                }
            }, 3200); // delay for 10.5 seconds

                if(round1){   
                    
                    levelManager = new LevelManager(this);
                    player1 = new Player1(325, 150, (int) (64 * SCALE), (int) (40 * SCALE));
                    player1.loadLvlData(levelManager.getCurrentLevel().getLevelData());
                    player2 = new Player2(1300, 150, (int) (64 * SCALE), (int) (40 * SCALE));
                    player2.loadLvlData2(levelManager.getCurrentLevel().getLevelData());
                    trap = new trapp(0,847, (int)(64 * SCALE), (int)(40 * SCALE));
                
                    round2 = true;
                    round1 = false;
                    dialog = true;
                }
        }
            
        
        
        private void resetcords2(){
            if(round2){
                soundeffects("src\\sounds\\round-2-fight.wav");
                player1 = new Player1(325, 150, (int) (64 * SCALE), (int) (40 * SCALE));
                player1.loadLvlData(levelManager.getCurrentLevel().getLevelData());
                
                player2 = new Player2(1300, 150, (int) (64 * SCALE), (int) (40 * SCALE));
                player2.loadLvlData2(levelManager.getCurrentLevel().getLevelData());
                killed1 = false;
                killed2 = false;
                rr1 = false; 
            
                round3 = true;
                round2 = false;
                round1 = false;
            }
        }
        private void resetcords3(){
            if(round3){
                soundeffects("src\\sounds\\round-3-fight.wav");
            player1 = new Player1(325, 150, (int) (64 * SCALE), (int) (40 * SCALE));
            player1.loadLvlData(levelManager.getCurrentLevel().getLevelData());
                
            player2 = new Player2(1300, 150, (int) (64 * SCALE), (int) (40 * SCALE));
            player2.loadLvlData2(levelManager.getCurrentLevel().getLevelData());
            killed1 = false;
            killed2 = false;
            rr2 = false;
            
            round4 = true;
            round3 = false;
            round2 = false;
            round1 = false;
            }
        }
         

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
                start = true;
	}

	public void update() throws InterruptedException {

            if(dialog){
                delaydialog();
            }
            
            
            
            if(rr1){
                delay1();
            }
            if(rr2){
                delay2();
            }
            if(rr3){
                delay3();
            }
            
            if(win){
                delayplayer1wins();
            }
            if(win){
                delayplayer2wins();
            }
           
                whowon();
		levelManager.update();
		player1.update();
                player2.update();
                resetClasses();
               
	}
        
        private int play1 = p1.getChosen();
        private int play2 = p1.getPicked();

	public void render(Graphics g) {
                
                trap.drawBG(g);
                trap.render(g);
                levelManager.draw(g);
                player1.render(g);
                player2.render(g);
                trap.drawR1(g, rr1);
                trap.drawR2(g, rr2);
                trap.drawR3(g, rr3);
                
                trap.drawScores1(g, A);
                trap.drawScores2(g,B);
                trap.drawOver1(g);
                trap.drawOver2(g);
                
                trap.drawLine(g);
                
                /* trap.drawgif1(g, gif1);
                trap.drawgif2(g, gif2);
                trap.drawgif3(g, gif3);
                trap.drawgif4(g, gif4);
                trap.drawgif5(g, gif5);
                trap.drawgif6(g, gif6);
                trap.drawgif7(g, gif7);*/
                
                
                trap.drawdialogs(g, play1, play2, dialog);
                
                trap.drawplayerwin(g, player1wins, player2wins, winwin);
                

            
	}

        private void player1won(boolean win){
            this.win = win;
            if(win){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    soundeffects("src\\sounds\\round-1-fight.wav");
                    musicPlayer.play3(true);
                }
            }, 4000); // delay for 10.5 seconds
            musicPlayer.play3(false);
            win = false;
            }
        }
        
        private void player2won(boolean win){
            this.win = win;
            if(win){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    soundeffects("src\\sounds\\round-1-fight.wav");
                    musicPlayer.play4(true);
                }
            }, 4000); // delay for 10.5 seconds
            musicPlayer.play4(false);
            win = false;
            }
        }
        
        
	@Override
	public void run() {

		double timePerFrame = 1000000000.9999 / FPS_SET;
		double timePerUpdate = 1000000000.9999 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
                            try {
                                update();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				//System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}
            
        }
        
        
         private void resetClasses() throws InterruptedException{
                      
            
                    if (killed1 || killed2 && round2) {
                            rr2 = true;     
                            resetcords2();
                        
                        
                    }
                    if(killed1 || killed2 && round3){
                            rr3 = true;
                            resetcords3();    
                            
                    }
            
                    if(killed1 || killed2 && winwin){
                            rr1 = false;
                            rr2 = false;
                            rr3 = false;
                            round5 = false;
                            round4 = true;
                            round3 = false;
                            round2 = false;
                            round1 = false;
                            won = false;
                            killed1 = false;
                            killed2 = false;
                            winwin = true;
                           
                    }
            
        }
        
        private void whowon(){
            
            //whowonnnn
                        if(A >= 2){
                            winwin = true;
                            win = true;
                            player1wins = true;
                            player2wins = false;
                                rr1 = false;
                                rr2 = false;
                                rr3 = false;
                                
                            
                                round1 = false;
                                round2 = false;
                                round3 = false;
                                round4 = false;
                        }
                        
                        if(B >= 2){
                            winwin = true;
                            win = true;
                            player2wins = true;
                            player1wins = false;
                                rr1 = false;
                                rr2 = false;
                                rr3 = false;
                                
                                
                                round1 = false;
                                round2 = false;
                                round3 = false;
                                round4 = false;
                        }
                
            //r1
                if(killed1 && round2){
                    b = B + 1;
                    B = b;
                }
                if(killed2 && round2){
                    a = A + 1;
                    A = a;
                }
            //r2
                if(killed1 && round3){
                    b = B + 1;
                    B = b;
                }
                if(killed2 && round3){
                    a = A + 1;
                    A = a;
                } 
            //r3
                if(killed1 && round4){
                    b = B + 1;
                    B = b;
                }
                if(killed2 && round4){
                    a = A + 1;
                    A = a;
                }
            //r4
                if(killed1 && round5){
                    b = B + 1;
                    B = b;
                }
                if(killed2 && round5){
                    a = A + 1;
                    A = a;
                }
            //r5
                if(killed1 && won){
                    b = B + 1;
                    B = b;
                }
                if(killed2 && won){
                    a = A + 1;
                    A = a;
                }   
                
                
            }
        
        
        private void error(){
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // Error placeholder removed to fix syntax error
                    }
                },1);
        }
        
        
       
        private void delaydialog(){
            if(dialog){
            try{                 
                       Timer timer = new Timer();
                       timer.schedule(new TimerTask() {
                     @Override
                    public void run() {
                        dialog = false;
                            error();        
                    }
                },4000);
                       
            }
            catch(Exception e){
                dialog = false;
            }  
            }
        }
        private void delayplayer1wins(){
            if(win){
            try{                 
                       Timer timer = new Timer();
                       timer.schedule(new TimerTask() {
                     @Override
                    public void run() {
                        
                        win = false;
                            error();        
                    }
                },10);
                       
            }
            catch(Exception e){
                win = false;
            }  
            }
        }
        private void delayplayer2wins(){
            if(win){
            try{                 
                       Timer timer = new Timer();
                       timer.schedule(new TimerTask() {
                     @Override
                    public void run() {
                        
                        win = false;
                            error();        
                    }
                },10);
                       
            }
            catch(Exception e){
                win = false;
            }  
            }
        }
        
  
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH        
        
        
        
        
       
        
        private void delay1(){
            if(rr1){
            try{                 
                       Timer timer = new Timer();
                       timer.schedule(new TimerTask() {
                     @Override
                    public void run() {
                        rr1 = false;
                        
                            error();        
                    }
                },2500);
                       
            }
            catch(Exception e){
                rr1 = false;

            }  
            }
        }
        
         private void delay2(){
            if(rr2){
            try{                 
                       Timer timer = new Timer();
                       timer.schedule(new TimerTask() {
                     @Override
                    public void run() {
                        rr2 = false;
                            error();        
                    }
                },2500);
                       
            }
            catch(Exception e){
                rr2 = false;
            }  
            }
        }
         
         private void delay3(){
            if(rr3){
            try{                 
                       Timer timer = new Timer();
                       timer.schedule(new TimerTask() {
                     @Override
                    public void run() {
                        rr3 = false;
                            error();        
                    }
                },2500);
                       
            }
            catch(Exception e){
                rr3 = false;
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
        
        public boolean life1(){           
            return health1;    
        }     
        
        public boolean life2(){       
            return health2;    
        } 

        public boolean finished(){
            return winwin;
        }
        
        
        public void healthy1(boolean health1){
            this.health1 = health1;
        }
        
        public void healthy2(boolean health2){
            this.health2 = health2;
        }
        
        
        public void reset(boolean killed1, boolean killed2){
            this.killed1 = killed1;
            this.killed2 = killed2;
        }
       
        
        
        
        
	public void windowFocusLost() {
		player1.resetDirBooleans();
                player2.resetDirBooleans2();
	}

	public Player1 getPlayer1() {
		return player1;
	}
        
        public Player2 getPlayer2() {
		return player2;
	}
        
        public trapp getdeath(){
                return trap;
        }
        
        public KeyboardInputs getKeyboardInputs(){
                return KI;
        }
       
        
        public void stopsounds(boolean stopsound){
            this.stopsound = stopsound;
            if(stopsound == true){
                musicPlayer.play1(false);
            
            }
        }
        
        
         



        
   


}
