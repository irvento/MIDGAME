package playerz;

import java.awt.*;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.event.KeyEvent;
import main.MusicPlayer;

public class LoadScreen extends JFrame {

    private Image backgroundImg;
    private MusicPlayer musicPlayer = new MusicPlayer();
    private JProgressBar progressBar;
    private Timer loadingTimer;

    public LoadScreen() {
        // Load Background using resource safely
        try {
            URL bgUrl = getClass().getResource("/loading_images/loading_bg6.gif");
            if (bgUrl != null) {
                backgroundImg = Toolkit.getDefaultToolkit().createImage(bgUrl);
            } else {
                System.out.println("Background image not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initFrame();
        initUI();
        startLoading();

        // Play Music
        musicPlayer.play3(true);
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(1664, 896); // Match game resolution
        setLocationRelativeTo(null);
        setTitle("Loading...");
    }

    private void initUI() {
        // Main Panel with Background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImg != null) {
                    g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // --- Controls Overlay ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 100, 0));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 150, 200, 150));

        // Player 1 Controls
        JPanel p1Panel = createControlsPanel("PLAYER 1", new Color(100, 200, 255));
        String p1Move = KeyEvent.getKeyText(inputs.KeyBindings.p1Left) + ", " + KeyEvent.getKeyText(inputs.KeyBindings.p1Right) + ", " + KeyEvent.getKeyText(inputs.KeyBindings.p1Jump) + ", " + KeyEvent.getKeyText(inputs.KeyBindings.p1Defend);
        addControlLine(p1Panel, p1Move, "Move / Jump / Defend");
        addControlLine(p1Panel, KeyEvent.getKeyText(inputs.KeyBindings.p1Attack1), "Attack 1");
        addControlLine(p1Panel, KeyEvent.getKeyText(inputs.KeyBindings.p1Attack2), "Attack 2 (Skill)");
        addControlLine(p1Panel, KeyEvent.getKeyText(inputs.KeyBindings.p1Attack3), "Attack 3 (Ult)");
        addControlLine(p1Panel, KeyEvent.getKeyText(inputs.KeyBindings.p1Special), "Hadouken");

        // Player 2 Controls
        JPanel p2Panel = createControlsPanel("PLAYER 2", new Color(255, 100, 100));
        String p2Move = KeyEvent.getKeyText(inputs.KeyBindings.p2Left) + ", " + KeyEvent.getKeyText(inputs.KeyBindings.p2Right) + ", " + KeyEvent.getKeyText(inputs.KeyBindings.p2Jump) + ", " + KeyEvent.getKeyText(inputs.KeyBindings.p2Defend);
        addControlLine(p2Panel, p2Move, "Move / Jump / Defend");
        addControlLine(p2Panel, KeyEvent.getKeyText(inputs.KeyBindings.p2Attack1), "Attack 1");
        addControlLine(p2Panel, KeyEvent.getKeyText(inputs.KeyBindings.p2Attack2), "Attack 2 (Skill)");
        addControlLine(p2Panel, KeyEvent.getKeyText(inputs.KeyBindings.p2Attack3), "Attack 3 (Ult)");
        addControlLine(p2Panel, KeyEvent.getKeyText(inputs.KeyBindings.p2Special), "Hadouken");

        centerPanel.add(p1Panel);
        centerPanel.add(p2Panel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Bottom Progress Bar ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(255, 204, 0)); // Gold color
        progressBar.setBackground(new Color(0, 0, 0));
        progressBar.setFont(new Font("Monospaced", Font.BOLD, 20));
        progressBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        progressBar.setPreferredSize(new Dimension(100, 40));

        bottomPanel.add(progressBar, BorderLayout.CENTER);

        JLabel tipLabel = new JLabel("Tip: Master your combos to dominate the arena!", SwingConstants.CENTER);
        tipLabel.setFont(new Font("Monospaced", Font.ITALIC, 18));
        tipLabel.setForeground(Color.WHITE);
        tipLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        bottomPanel.add(tipLabel, BorderLayout.NORTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createControlsPanel(String title, Color themeColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 0, 180)); // Darker overlay
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(themeColor, 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        titleLabel.setForeground(themeColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));

        return panel;
    }

    private void addControlLine(JPanel parent, String key, String action) {
        JPanel line = new JPanel(new BorderLayout());
        line.setOpaque(false);
        line.setMaximumSize(new Dimension(500, 40));

        JLabel keyLabel = new JLabel(key);
        keyLabel.setFont(new Font("Monospaced", Font.BOLD, 22));
        keyLabel.setForeground(Color.YELLOW);

        JLabel actionLabel = new JLabel(action);
        actionLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        actionLabel.setForeground(Color.WHITE);

        line.add(keyLabel, BorderLayout.WEST);
        line.add(actionLabel, BorderLayout.EAST);

        parent.add(line);
        parent.add(Box.createVerticalStrut(10));
    }

    private void startLoading() {
        loadingTimer = new Timer();
        loadingTimer.scheduleAtFixedRate(new TimerTask() {
            int progress = 0;

            @Override
            public void run() {
                progress++;
                progressBar.setValue(progress);

                if (progress >= 100) {
                    loadingTimer.cancel();
                    finishedLoading();
                }
            }
        }, 0, 50); // 50ms * 100 = 5 seconds load time (faster than 90ms)
    }

    private void finishedLoading() {
        SwingUtilities.invokeLater(() -> {
            musicPlayer.play3(false); // Stop load music

            CharacterPick charPick = new CharacterPick();
            charPick.setVisible(true);

            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoadScreen().setVisible(true));
    }
}
