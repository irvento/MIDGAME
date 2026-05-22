package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import main.GamePanel;
import playerz.LoadScreen;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    private static void soundeffects(String path) {
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null || gamePanel.getGame().getPlayer2() == null) {
            return;
        }

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
            case KeyEvent.VK_X:
            case KeyEvent.VK_C:
            case KeyEvent.VK_V:
                // Attacks are edge-triggered on press, no release logic needed for boolean flags anymore
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
            case KeyEvent.VK_DOWN:
                gamePanel.getGame().getPlayer2().setDefend2(false);
                break;

            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_NUMPAD4:
                // Attacks are edge-triggered on press, no release logic needed for boolean flags anymore
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (gamePanel.getGame() != null) {
                    gamePanel.getGame().stopsounds(true);
                }
                LoadScreen cp = new LoadScreen();
                cp.setLocationRelativeTo(null);
                cp.setVisible(true);
                gamePanel.disable();
                gamePanel.setVisible(false);
                break;

            case KeyEvent.VK_ESCAPE:
                int z = JOptionPane.showConfirmDialog(null, "ARE YOU SURE YOU WANT TO EXIT?", "EXIT?",
                        JOptionPane.YES_NO_OPTION);
                if (z == 0) {
                    System.exit(0);
                }
                break;
        }

        if (gamePanel.getGame() == null || gamePanel.getGame().getPlayer1() == null || gamePanel.getGame().getPlayer2() == null) {
            return;
        }

        switch (e.getKeyCode()) {
            // Player 1 Movement
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer1().setLeft(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer1().setRight(true);
                break;
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer1().setJump(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer1().setDefend(true);
                break;

            // Player 1 Attacks
            case KeyEvent.VK_Z:
                gamePanel.getGame().getPlayer1().executeSkill1(gamePanel.getGame().getPlayer2());
                break;
            case KeyEvent.VK_X:
                gamePanel.getGame().getPlayer1().executeSkill2(gamePanel.getGame().getPlayer2());
                break;
            case KeyEvent.VK_C:
                gamePanel.getGame().getPlayer1().executeSkill3(gamePanel.getGame().getPlayer2());
                break;
            case KeyEvent.VK_V:
                gamePanel.getGame().getPlayer1().executeHadouken();
                break;

            // Player 2 Movement
            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getPlayer2().setLeft2(true);
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getPlayer2().setRight2(true);
                break;
            case KeyEvent.VK_UP:
                gamePanel.getGame().getPlayer2().setJump2(true);
                break;
            case KeyEvent.VK_DOWN: // Ensure defend exists for P2
                gamePanel.getGame().getPlayer2().setDefend2(true);
                break;

            // Player 2 Attacks
            case KeyEvent.VK_NUMPAD1:
                gamePanel.getGame().getPlayer2().executeSkill1(gamePanel.getGame().getPlayer1());
                break;
            case KeyEvent.VK_NUMPAD2:
                gamePanel.getGame().getPlayer2().executeSkill2(gamePanel.getGame().getPlayer1());
                break;
            case KeyEvent.VK_NUMPAD3:
                gamePanel.getGame().getPlayer2().executeSkill3(gamePanel.getGame().getPlayer1());
                break;
            case KeyEvent.VK_NUMPAD4:
                gamePanel.getGame().getPlayer2().executeHadouken();
                break;
        }
    }

    public static void restart() {
        boolean shouldStop = true;
        try {
            String javaBin = System.getProperty("java.home") + "/bin/java";
            String classpath = System.getProperty("java.class.path");
            String className = main.Game.class.getCanonicalName();
            String[] command = new String[] { javaBin, "-cp", classpath, className };
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
