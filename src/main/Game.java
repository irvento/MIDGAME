package main;

import entities.CollisionSpark;
import entities.Hadouken;
import entities.Player1;
import entities.Player2;
import entities.trapp;
import inputs.KeyboardInputs;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilz.ObjectPool;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import levels.LevelManager;
import playerz.CharacterPick;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    // Object pool for Hadouken projectiles
    private utilz.ObjectPool<entities.Hadouken> hadoukenPool = new utilz.ObjectPool<>(() -> new entities.Hadouken(0f, 0f, (int) (40 * SCALE), (int) (40 * SCALE), 1), 30);
    private trapp trap;
    private Player1 player1;
    private Player2 player2;
    private KeyboardInputs KI;
    private LevelManager levelManager;
    private ControllerManager controllers;

    // Hadouken management
    private ArrayList<Hadouken> player1Hadoukens = new ArrayList<>();
    private ArrayList<Hadouken> player2Hadoukens = new ArrayList<>();
    private BufferedImage[] hadoukenAnimations;

    // Collision spark management
    private ArrayList<CollisionSpark> collisionSparks = new ArrayList<>();
    private BufferedImage collisionSparkSprite;

    private boolean health1 = false, health2 = false, killed1 = false, killed2 = false, win = false;
    private boolean rr1 = false, rr2 = false, rr3 = false, playagain = true;
    private boolean round1 = true, round2 = false, round3 = false, round4 = false, round5 = false;
    private boolean stopsound = false, stopgif = false;

    private boolean start = false, won = false, player1wins = false, player2wins = false, winwin = false, gif1 = true,
            gif2 = false, gif3 = false, gif4 = false, gif5 = false, gif6 = false, gif7 = false, dialog = false;
    private int A = 0, B = 0, a, b;
    private Clip clip;
    
    // Controller previous states
    private boolean p1XPrev = false, p1YPrev = false, p1BPrev = false, p1RbPrev = false;
    private boolean p2XPrev = false, p2YPrev = false, p2BPrev = false, p2RbPrev = false;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);// 64px
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;// 1664px
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;// 896px

    private MusicPlayer sound;
    private MusicPlayer musicPlayer = new MusicPlayer();

    public Game() {
        System.out.println("mapa " + CharacterPick.getmapinfo());
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
        /*
         * if(true){
         * music();
         * }
         */

    }

    private void music() {
        try {
            Thread.sleep(1500); // delay for 10.5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        musicPlayer.play1(!stopsound);

    }

    private void initClasses() {
        try {
            controllers = new ControllerManager();
            controllers.initSDLGamepad();
            
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (controllers != null) {
                    controllers.quitSDLGamepad();
                }
            }));
        } catch (Throwable t) {
            System.out.println("Gamepad support unavailable: " + t.getMessage());
            controllers = null;
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                soundeffects("src\\sounds\\round-1-fight.wav");
                rr1 = true;
                musicPlayer.play1(!stopsound);
            }
        }, 3200); // delay for 10.5 seconds

        if (round1) {

            levelManager = new LevelManager(this);
            player1 = new Player1(325, 150, (int) (64 * SCALE), (int) (40 * SCALE));
            player1.loadLvlData(levelManager.getCurrentLevel().getLevelData());
            player1.setGameInstance(this);
            player2 = new Player2(1300, 150, (int) (64 * SCALE), (int) (40 * SCALE));
            player2.loadLvlData2(levelManager.getCurrentLevel().getLevelData());
            player2.setGameInstance(this);
            trap = new trapp(0, 847, (int) (64 * SCALE), (int) (40 * SCALE));

            round2 = true;
            round1 = false;
            dialog = true;
        }

        // Load hadouken animations
        loadHadoukenAnimations();
        // Load collision spark sprite
        try {
            collisionSparkSprite = utilz.LoadSave.GetSpriteAtlas(utilz.LoadSave.COLLISION_SPARK);
            if (collisionSparkSprite == null) {
                System.out.println("Warning: Collision spark sprite not found, generating fallback sprite");
                // Generate fallback sprite (Orange/Yellow energy ball)
                collisionSparkSprite = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                java.awt.Graphics2D g2d = collisionSparkSprite.createGraphics();

                // Outer glow
                g2d.setColor(new java.awt.Color(255, 100, 0, 200)); // Orange transparent
                g2d.fillOval(0, 0, 64, 64);

                // Core
                g2d.setColor(java.awt.Color.YELLOW);
                g2d.fillOval(16, 16, 32, 32);

                g2d.setColor(java.awt.Color.WHITE);
                g2d.fillOval(24, 24, 16, 16);

                g2d.dispose();
            }
        } catch (Exception e) {
            System.out.println("Error loading collision spark sprite: " + e.getMessage());
            // Fallback for exception case too
            collisionSparkSprite = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g2d = collisionSparkSprite.createGraphics();
            g2d.setColor(java.awt.Color.RED);
            g2d.fillOval(0, 0, 32, 32);
            g2d.dispose();
        }
    }

    private void loadHadoukenAnimations() {
        BufferedImage hadoukenSprite = utilz.LoadSave.GetSpriteAtlas(utilz.LoadSave.HADOUKEN_SPRITE);
        if (hadoukenSprite == null) {
            System.out.println("Warning: Hadouken sprite not found, generating fallback sprite");
            // Fallback: Blue Energy Ball
            hadoukenAnimations = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                hadoukenAnimations[i] = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                java.awt.Graphics2D g2d = hadoukenAnimations[i].createGraphics();

                // Outer Glow
                g2d.setColor(new java.awt.Color(0, 255, 255, 150)); // Cyan glow matching the spark style
                g2d.fillOval(0, 0, 64, 64);

                // Middle Layer
                g2d.setColor(new java.awt.Color(0, 100, 255));
                g2d.fillOval(10, 10, 44, 44);

                // Core
                g2d.setColor(java.awt.Color.WHITE);
                g2d.fillOval(20, 20, 24, 24);

                g2d.dispose();
            }
            return;
        }

        // Try to extract frames if sprite sheet, otherwise use single image
        int spriteWidth = hadoukenSprite.getWidth();
        int spriteHeight = hadoukenSprite.getHeight();

        // Check if it's a sprite sheet (multiple frames) or single image
        if (spriteWidth > spriteHeight * 2) {
            // Likely a sprite sheet - extract frames
            int frameCount = Math.min(4, spriteWidth / spriteHeight);
            hadoukenAnimations = new BufferedImage[frameCount];
            int frameWidth = spriteWidth / frameCount;

            for (int i = 0; i < frameCount; i++) {
                hadoukenAnimations[i] = hadoukenSprite.getSubimage(i * frameWidth, 0, frameWidth, spriteHeight);
            }
        } else {
            // Single image - create animation array with same image
            hadoukenAnimations = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                hadoukenAnimations[i] = hadoukenSprite;
            }
        }
    }

    public void spawnPlayer1Hadouken() {
        if (player1 != null && player1.canShootHadouken()) {
            int dir = player1.getFacingDirection();
            float spawnX = player1.getHadoukenSpawnX();
            float spawnY = player1.getHadoukenSpawnY();
            entities.Hadouken hadouken = hadoukenPool.acquire();
            hadouken.reset(spawnX, spawnY, dir);
            hadouken.setAnimations(hadoukenAnimations);
            player1Hadoukens.add(hadouken);
            player1.setCanShootHadouken(false);
        }
    }

    public void spawnPlayer2Hadouken() {
        if (player2 != null && player2.canShootHadouken()) {
            int dir = player2.getFacingDirection();
            float spawnX = player2.getHadoukenSpawnX();
            float spawnY = player2.getHadoukenSpawnY();
            entities.Hadouken hadouken = hadoukenPool.acquire();
            hadouken.reset(spawnX, spawnY, dir);
            hadouken.setAnimations(hadoukenAnimations);
            player2Hadoukens.add(hadouken);
            player2.setCanShootHadouken(false);
        }
    }

    private void resetcords2() {
        if (round2) {
            try {
                soundeffects("src\\sounds\\round-2-fight.wav");

                // Clear all projectiles and effects before creating new players
                player1Hadoukens.clear();
                player2Hadoukens.clear();
                collisionSparks.clear();

                // Create new player instances
                player1 = new Player1(325, 150, (int) (64 * SCALE), (int) (40 * SCALE));
                player1.loadLvlData(levelManager.getCurrentLevel().getLevelData());
                player1.setGameInstance(this);

                player2 = new Player2(1300, 150, (int) (64 * SCALE), (int) (40 * SCALE));
                player2.loadLvlData2(levelManager.getCurrentLevel().getLevelData());
                player2.setGameInstance(this);

                // Reset all state flags
                killed1 = false;
                killed2 = false;
                rr1 = false;

                // Reset hadouken cooldowns
                resetHadoukenCooldowns();

                // Update round flags
                round3 = true;
                round2 = false;
                round1 = false;
            } catch (Exception e) {
                System.out.println("Error in resetcords2: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void resetcords3() {
        if (round3) {
            try {
                soundeffects("src\\sounds\\round-3-fight.wav");

                // Clear all projectiles and effects before creating new players
                player1Hadoukens.clear();
                player2Hadoukens.clear();
                collisionSparks.clear();

                // Create new player instances
                player1 = new Player1(325, 150, (int) (64 * SCALE), (int) (40 * SCALE));
                player1.loadLvlData(levelManager.getCurrentLevel().getLevelData());
                player1.setGameInstance(this);

                player2 = new Player2(1300, 150, (int) (64 * SCALE), (int) (40 * SCALE));
                player2.loadLvlData2(levelManager.getCurrentLevel().getLevelData());
                player2.setGameInstance(this);

                // Reset all state flags
                killed1 = false;
                killed2 = false;
                rr2 = false;

                // Reset hadouken cooldowns
                resetHadoukenCooldowns();

                // Update round flags
                round4 = true;
                round3 = false;
                round2 = false;
                round1 = false;
            } catch (Exception e) {
                System.out.println("Error in resetcords3: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
        start = true;
    }

    public void update() throws InterruptedException {
        try {
            if (dialog) {
                delaydialog();
            }

            if (rr1) {
                delay1();
            }
            if (rr2) {
                delay2();
            }
            if (rr3) {
                delay3();
            }

            if (win) {
                delayplayer1wins();
            }
            if (win) {
                delayplayer2wins();
            }

            whowon();

            // Only update if players are initialized
            if (player1 != null && player2 != null) {
                
                // Poll controller inputs
                if (controllers != null) {
                    try {
                        controllers.update();
                        
                        // Player 1 Controller (Index 0)
                        ControllerState p1State = controllers.getState(0);
                        if (p1State.isConnected) {
                            player1.setLeft(p1State.dpadLeft || p1State.leftStickX < -0.5f);
                            player1.setRight(p1State.dpadRight || p1State.leftStickX > 0.5f);
                            player1.setJump(p1State.dpadUp || p1State.a || p1State.leftStickY > 0.5f);
                            player1.setDefend(p1State.dpadDown || p1State.leftStickY < -0.5f);
                            
                            if (p1State.x && !p1XPrev) player1.executeSkill1(player2);
                            if (p1State.y && !p1YPrev) player1.executeSkill2(player2);
                            if (p1State.b && !p1BPrev) player1.executeSkill3(player2);
                            if (p1State.rb && !p1RbPrev) player1.executeHadouken();
                            
                            p1XPrev = p1State.x;
                            p1YPrev = p1State.y;
                            p1BPrev = p1State.b;
                            p1RbPrev = p1State.rb;
                        }

                        // Player 2 Controller (Index 1)
                        ControllerState p2State = controllers.getState(1);
                        if (p2State.isConnected) {
                            player2.setLeft2(p2State.dpadLeft || p2State.leftStickX < -0.5f);
                            player2.setRight2(p2State.dpadRight || p2State.leftStickX > 0.5f);
                            player2.setJump2(p2State.dpadUp || p2State.a || p2State.leftStickY > 0.5f);
                            player2.setDefend2(p2State.dpadDown || p2State.leftStickY < -0.5f);
                            
                            if (p2State.x && !p2XPrev) player2.executeSkill1(player1);
                            if (p2State.y && !p2YPrev) player2.executeSkill2(player1);
                            if (p2State.b && !p2BPrev) player2.executeSkill3(player1);
                            if (p2State.rb && !p2RbPrev) player2.executeHadouken();
                            
                            p2XPrev = p2State.x;
                            p2YPrev = p2State.y;
                            p2BPrev = p2State.b;
                            p2RbPrev = p2State.rb;
                        }
                    } catch (Throwable t) {
                        // Disable controllers on error
                        controllers = null;
                    }
                }

                levelManager.update();
                player1.update();
                player2.update();
                updateHadoukens();
                checkHadoukenCollisions();
                updateCollisionSparks();
                
                // Trap collision checks
                if (trap != null) {
                    if (player1.getHitbox().intersects(trap.getHitbox())) {
                        player1.hurt(100000); // Instant death
                    }
                    if (player2.getHitbox().intersects(trap.getHitbox())) {
                        player2.hurt(100000); // Instant death
                    }
                }
                
                boolean player1daed = player1.isdead1();
                boolean player2daed = player2.isdead2();

                player1.isdeath2(player2daed);
                player2.isdeath1(player1daed);

                this.reset(player1daed, player2daed);
            }

            resetClasses();
        } catch (Exception e) {
            System.out.println("Error in Game.update(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int play1 = CharacterPick.getChosen();
    private int play2 = CharacterPick.getPicked();

    public void render(Graphics g) {

        trap.drawBG(g);
        trap.render(g);
        levelManager.draw(g);
        renderHadoukens(g);
        renderCollisionSparks(g);
        player1.render(g);
        player2.render(g);
        trap.drawR1(g, rr1);
        trap.drawR2(g, rr2);
        trap.drawR3(g, rr3);

        trap.drawScores1(g, A);
        trap.drawScores2(g, B);
        trap.drawOver1(g);
        trap.drawOver2(g);

        trap.drawLine(g);

        /*
         * trap.drawgif1(g, gif1);
         * trap.drawgif2(g, gif2);
         * trap.drawgif3(g, gif3);
         * trap.drawgif4(g, gif4);
         * trap.drawgif5(g, gif5);
         * trap.drawgif6(g, gif6);
         * trap.drawgif7(g, gif7);
         */

        trap.drawdialogs(g, play1, play2, dialog);

        trap.drawplayerwin(g, player1wins, player2wins, winwin);

        drawCooldowns(g);
    }

    private void drawCooldowns(Graphics g) {
        if (player1 == null || player2 == null) return;

        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        // Player 1 Overlay (Under Healthbar)
        int p1StartX = 20;
        int p1StartY = 80;
        drawSkillCircle(g2d, p1StartX, p1StartY, "S1", player1.getSkill1CooldownRemaining(), 600f, java.awt.Color.CYAN);
        drawSkillCircle(g2d, p1StartX + 60, p1StartY, "S2", player1.getSkill2CooldownRemaining(), 2000f, java.awt.Color.CYAN);
        drawSkillCircle(g2d, p1StartX + 120, p1StartY, "S3", player1.getSkill3CooldownRemaining(), 3500f, java.awt.Color.CYAN);
        drawSkillCircle(g2d, p1StartX + 180, p1StartY, "R", player1.getHadoukenCooldownRemaining(), 1500f, java.awt.Color.CYAN);

        // Player 2 Overlay (Under Healthbar, aligned right)
        int p2StartX = GAME_WIDTH - 240;
        int p2StartY = 80;
        drawSkillCircle(g2d, p2StartX, p2StartY, "S1", player2.getSkill1CooldownRemaining(), 600f, java.awt.Color.RED);
        drawSkillCircle(g2d, p2StartX + 60, p2StartY, "S2", player2.getSkill2CooldownRemaining(), 2000f, java.awt.Color.RED);
        drawSkillCircle(g2d, p2StartX + 120, p2StartY, "S3", player2.getSkill3CooldownRemaining(), 3500f, java.awt.Color.RED);
        drawSkillCircle(g2d, p2StartX + 180, p2StartY, "R", player2.getHadoukenCooldownRemaining(), 1500f, java.awt.Color.RED);
    }

    private void drawSkillCircle(java.awt.Graphics2D g2d, int x, int y, String name, long remaining, float max, java.awt.Color color) {
        int diameter = 40;
        
        // Background circle
        g2d.setColor(new java.awt.Color(0, 0, 0, 150));
        g2d.fillOval(x, y, diameter, diameter);
        
        // Border
        g2d.setColor(color);
        g2d.setStroke(new java.awt.BasicStroke(2));
        g2d.drawOval(x, y, diameter, diameter);
        
        if (remaining > 0) {
            // Cooldown sweep
            int angle = (int)(360 * (remaining / max));
            g2d.setColor(new java.awt.Color(0, 0, 0, 200));
            g2d.fillArc(x, y, diameter, diameter, 90, angle);

            // Time remaining text
            g2d.setColor(java.awt.Color.WHITE);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
            String text = String.format("%.1f", remaining / 1000.0f);
            int textWidth = g2d.getFontMetrics().stringWidth(text);
            g2d.drawString(text, x + (diameter - textWidth) / 2, y + 25);
        } else {
            // Ready text
            g2d.setColor(color);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
            int textWidth = g2d.getFontMetrics().stringWidth(name);
            g2d.drawString(name, x + (diameter - textWidth) / 2, y + 25);
        }
    }

    private void player1won(boolean win) {
        this.win = win;
        if (win) {
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

    private void player2won(boolean win) {
        this.win = win;
        if (win) {
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
                // System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }

    }

    private void resetClasses() throws InterruptedException {

        if (killed1 || killed2 && round2) {
            rr2 = true;
            resetcords2();

        }
        if (killed1 || killed2 && round3) {
            rr3 = true;
            resetcords3();

        }

        if (killed1 || killed2 && winwin) {
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

    private void whowon() {

        // whowonnnn
        if (A >= 2) {
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

        if (B >= 2) {
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

        // r1
        if (killed1 && round2) {
            b = B + 1;
            B = b;
        }
        if (killed2 && round2) {
            a = A + 1;
            A = a;
        }
        // r2
        if (killed1 && round3) {
            b = B + 1;
            B = b;
        }
        if (killed2 && round3) {
            a = A + 1;
            A = a;
        }
        // r3
        if (killed1 && round4) {
            b = B + 1;
            B = b;
        }
        if (killed2 && round4) {
            a = A + 1;
            A = a;
        }
        // r4
        if (killed1 && round5) {
            b = B + 1;
            B = b;
        }
        if (killed2 && round5) {
            a = A + 1;
            A = a;
        }
        // r5
        if (killed1 && won) {
            b = B + 1;
            B = b;
        }
        if (killed2 && won) {
            a = A + 1;
            A = a;
        }

    }

    private long dialogStartTime = 0;
    private void delaydialog() {
        if (dialog) {
            if (dialogStartTime == 0) dialogStartTime = System.currentTimeMillis();
            if (System.currentTimeMillis() - dialogStartTime > 4000) {
                dialog = false;
                dialogStartTime = 0;
            }
        }
    }

    private long p1WinStartTime = 0;
    private void delayplayer1wins() {
        if (win) {
            if (p1WinStartTime == 0) p1WinStartTime = System.currentTimeMillis();
            if (System.currentTimeMillis() - p1WinStartTime > 10) {
                win = false;
                p1WinStartTime = 0;
            }
        }
    }

    private long p2WinStartTime = 0;
    private void delayplayer2wins() {
        if (win) {
            if (p2WinStartTime == 0) p2WinStartTime = System.currentTimeMillis();
            if (System.currentTimeMillis() - p2WinStartTime > 10) {
                win = false;
                p2WinStartTime = 0;
            }
        }
    }

    // HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH

    private long rr1StartTime = 0;
    private void delay1() {
        if (rr1) {
            if (rr1StartTime == 0) rr1StartTime = System.currentTimeMillis();
            if (System.currentTimeMillis() - rr1StartTime > 2500) {
                rr1 = false;
                rr1StartTime = 0;
            }
        }
    }

    private long rr2StartTime = 0;
    private void delay2() {
        if (rr2) {
            if (rr2StartTime == 0) rr2StartTime = System.currentTimeMillis();
            if (System.currentTimeMillis() - rr2StartTime > 2500) {
                rr2 = false;
                rr2StartTime = 0;
            }
        }
    }

    private long rr3StartTime = 0;
    private void delay3() {
        if (rr3) {
            if (rr3StartTime == 0) rr3StartTime = System.currentTimeMillis();
            if (System.currentTimeMillis() - rr3StartTime > 2500) {
                rr3 = false;
                rr3StartTime = 0;
            }
        }
    }

    public static void soundeffects(String path) {
        try {
            File musicPath = new File(path);
            AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public boolean life1() {
        return health1;
    }

    public boolean life2() {
        return health2;
    }

    public boolean finished() {
        return winwin;
    }

    public void healthy1(boolean health1) {
        this.health1 = health1;
    }

    public void healthy2(boolean health2) {
        this.health2 = health2;
    }

    public void reset(boolean killed1, boolean killed2) {
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

    public trapp getdeath() {
        return trap;
    }

    public KeyboardInputs getKeyboardInputs() {
        return KI;
    }

    public void stopsounds(boolean stopsound) {
        this.stopsound = stopsound;
        if (stopsound == true) {
            musicPlayer.play1(false);

        }
    }

    private void updateHadoukens() {
        // Update player1 hadoukens
        Iterator<entities.Hadouken> it1 = player1Hadoukens.iterator();
        while (it1.hasNext()) {
            entities.Hadouken h = it1.next();
            h.update();
            if (!h.isActive()) {
                it1.remove();
                hadoukenPool.release(h);
            }
        }

        // Update player2 hadoukens
        Iterator<entities.Hadouken> it2 = player2Hadoukens.iterator();
        while (it2.hasNext()) {
            entities.Hadouken h = it2.next();
            h.update();
            if (!h.isActive()) {
                it2.remove();
                hadoukenPool.release(h);
            }
        }
    }

    private void renderHadoukens(Graphics g) {
        int xLvlOffset = 0; // You may need to adjust this based on camera offset

        // Render player1 hadoukens
        for (Hadouken h : player1Hadoukens) {
            h.draw(g, xLvlOffset);
        }

        // Render player2 hadoukens
        for (Hadouken h : player2Hadoukens) {
            h.draw(g, xLvlOffset);
        }
    }

    private void checkHadoukenCollisions() {
        if (player1 == null || player2 == null)
            return;

        Rectangle2D.Float player1Hitbox = player1.getHitbox();
        Rectangle2D.Float player2Hitbox = player2.getHitbox();

        // Check player2 hadoukens against player1
        Iterator<Hadouken> it2 = player2Hadoukens.iterator();
        while (it2.hasNext()) {
            Hadouken h = it2.next();
            if (h.getAttackBox().intersects(player1Hitbox)) {
                // Deal damage to player1
                player1.hurt(h.getDamage());
                h.setActive(false);
                it2.remove();
            }
        }

        // Check player1 hadoukens against player2
        Iterator<Hadouken> it1 = player1Hadoukens.iterator();
        while (it1.hasNext()) {
            Hadouken h = it1.next();
            if (h.getAttackBox().intersects(player2Hitbox)) {
                // Deal damage to player2
                player2.hurt(h.getDamage());
                h.setActive(false);
                it1.remove();
            }
        }
    }

    public void resetHadoukenCooldowns() {
        if (player1 != null) {
            player1.setCanShootHadouken(true);
        }
        if (player2 != null) {
            player2.setCanShootHadouken(true);
        }
    }

    public ArrayList<Hadouken> getPlayer1Hadoukens() {
        return player1Hadoukens;
    }

    public ArrayList<Hadouken> getPlayer2Hadoukens() {
        return player2Hadoukens;
    }

    private void updateCollisionSparks() {
        try {
            if (collisionSparks != null) {
                Iterator<CollisionSpark> it = collisionSparks.iterator();
                while (it.hasNext()) {
                    CollisionSpark spark = it.next();
                    if (spark != null) {
                        spark.update();
                        if (!spark.isActive()) {
                            it.remove();
                        }
                    } else {
                        it.remove();
                    }
                }
            }
        } catch (Exception e) {
            // Clear list on error to prevent crashes
            if (collisionSparks != null) {
                collisionSparks.clear();
            }
        }
    }

    private void renderCollisionSparks(Graphics g) {
        try {
            if (collisionSparks != null && g != null) {
                int xLvlOffset = 0;
                for (CollisionSpark spark : collisionSparks) {
                    if (spark != null) {
                        spark.draw(g, xLvlOffset);
                    }
                }
            }
        } catch (Exception e) {
            // Silently handle rendering errors
        }
    }

    public void spawnCollisionSpark(float x, float y) {
        try {
            if (collisionSparkSprite != null && collisionSparks != null) {
                CollisionSpark spark = new CollisionSpark(x, y, collisionSparkSprite);
                collisionSparks.add(spark);
            }
        } catch (Exception e) {
            // Silently handle errors to prevent crashes
            System.out.println("Error spawning collision spark: " + e.getMessage());
        }
    }

}
