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

        int code = e.getKeyCode();
        
        // Player 1
        if (code == KeyBindings.p1Left) gamePanel.getGame().getPlayer1().setLeft(false);
        else if (code == KeyBindings.p1Right) gamePanel.getGame().getPlayer1().setRight(false);
        else if (code == KeyBindings.p1Jump) gamePanel.getGame().getPlayer1().setJump(false);
        else if (code == KeyBindings.p1Defend) gamePanel.getGame().getPlayer1().setDefend(false);

        // Player 2
        else if (code == KeyBindings.p2Left) gamePanel.getGame().getPlayer2().setLeft2(false);
        else if (code == KeyBindings.p2Right) gamePanel.getGame().getPlayer2().setRight2(false);
        else if (code == KeyBindings.p2Jump) gamePanel.getGame().getPlayer2().setJump2(false);
        else if (code == KeyBindings.p2Defend) gamePanel.getGame().getPlayer2().setDefend2(false);
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

        int code = e.getKeyCode();

        // Player 1 Movement
        if (code == KeyBindings.p1Left) gamePanel.getGame().getPlayer1().setLeft(true);
        else if (code == KeyBindings.p1Right) gamePanel.getGame().getPlayer1().setRight(true);
        else if (code == KeyBindings.p1Jump) gamePanel.getGame().getPlayer1().setJump(true);
        else if (code == KeyBindings.p1Defend) gamePanel.getGame().getPlayer1().setDefend(true);

        // Player 1 Attacks
        else if (code == KeyBindings.p1Attack1) gamePanel.getGame().getPlayer1().executeSkill1(gamePanel.getGame().getPlayer2());
        else if (code == KeyBindings.p1Attack2) gamePanel.getGame().getPlayer1().executeSkill2(gamePanel.getGame().getPlayer2());
        else if (code == KeyBindings.p1Attack3) gamePanel.getGame().getPlayer1().executeSkill3(gamePanel.getGame().getPlayer2());
        else if (code == KeyBindings.p1Special) gamePanel.getGame().getPlayer1().executeHadouken();

        // Player 2 Movement
        else if (code == KeyBindings.p2Left) gamePanel.getGame().getPlayer2().setLeft2(true);
        else if (code == KeyBindings.p2Right) gamePanel.getGame().getPlayer2().setRight2(true);
        else if (code == KeyBindings.p2Jump) gamePanel.getGame().getPlayer2().setJump2(true);
        else if (code == KeyBindings.p2Defend) gamePanel.getGame().getPlayer2().setDefend2(true);

        // Player 2 Attacks
        else if (code == KeyBindings.p2Attack1) gamePanel.getGame().getPlayer2().executeSkill1(gamePanel.getGame().getPlayer1());
        else if (code == KeyBindings.p2Attack2) gamePanel.getGame().getPlayer2().executeSkill2(gamePanel.getGame().getPlayer1());
        else if (code == KeyBindings.p2Attack3) gamePanel.getGame().getPlayer2().executeSkill3(gamePanel.getGame().getPlayer1());
        else if (code == KeyBindings.p2Special) gamePanel.getGame().getPlayer2().executeHadouken();
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
