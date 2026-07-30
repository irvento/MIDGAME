package playerz;

import java.awt.*;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import main.Game;
import main.MusicPlayer;

public class Loading3 extends JFrame {

    private Image backgroundImg;
    private MusicPlayer musicPlayer = new MusicPlayer();
    private JProgressBar progressBar;

    public Loading3() {
        // Load Background using resource safely
        try {
            // Use the same bg as requested
            URL bgUrl = getClass().getResource("/loading_images/loading_bg6.gif");
            if (bgUrl != null) {
                backgroundImg = Toolkit.getDefaultToolkit().createImage(bgUrl);
            } else {
                System.out.println("Background image not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        musicPlayer.play3(true);
        initFrame();
        initUI();
        startLoading();
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setTitle("VS Screen");
        utilz.WindowScaler.fitFrame(this, 1664, 896);
    }

    private void initUI() {
        // Main Panel
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

        // Center VS Display
        JPanel vsPanel = new JPanel(new GridLayout(1, 3));
        vsPanel.setOpaque(false);

        // P1 Section
        JPanel p1Panel = createPlayerPanel(CharacterPick.getChosen(), "PLAYER 1", new Color(100, 200, 255));

        // VS Label Section
        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setOpaque(false);
        JLabel vsLabel = new JLabel("VS");
        vsLabel.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 150));
        vsLabel.setForeground(Color.RED);
        // Add outline/shadow effect roughly by painting or just simple label for now
        vsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        middlePanel.add(vsLabel);

        // P2 Section
        JPanel p2Panel = createPlayerPanel(CharacterPick.getPicked(), "PLAYER 2", new Color(255, 100, 100));

        vsPanel.add(p1Panel);
        vsPanel.add(middlePanel);
        vsPanel.add(p2Panel);

        mainPanel.add(vsPanel, BorderLayout.CENTER);

        // Bottom Progress
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(255, 50, 50));
        progressBar.setBackground(Color.BLACK);
        progressBar.setPreferredSize(new Dimension(0, 30));
        progressBar.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        bottom.add(progressBar, BorderLayout.CENTER);
        mainPanel.add(bottom, BorderLayout.SOUTH);
    }

    private JPanel createPlayerPanel(int charId, String title, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

        JLabel nameLabel = new JLabel(title, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        nameLabel.setForeground(color);
        panel.add(nameLabel, BorderLayout.NORTH);

        // Use a container for the animated panel to scale it up
        JPanel charContainer = new JPanel(new BorderLayout());
        charContainer.setOpaque(false);
        charContainer.setBorder(BorderFactory.createLineBorder(color, 2));

        // Increase scale of animation
        if (charId > 0) {
            AnimatedCharacterPanel ani = new AnimatedCharacterPanel(charId);
            charContainer.add(ani, BorderLayout.CENTER);
        }

        panel.add(charContainer, BorderLayout.CENTER);

        JLabel charName = new JLabel(getCharName(charId), SwingConstants.CENTER);
        charName.setFont(new Font("Monospaced", Font.BOLD, 30));
        charName.setForeground(Color.WHITE);
        panel.add(charName, BorderLayout.SOUTH);

        return panel;
    }

    private String getCharName(int id) {
        switch (id) {
            case 1:
                return "RHINO";
            case 2:
                return "THE BEHEADED";
            case 3:
                return "ENDER";
            case 4:
                return "PLAGUE DOCTOR";
            case 5:
                return "PALADIN";
            default:
                return "UNKNOWN";
        }
    }

    private void startLoading() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                i++;
                progressBar.setValue(i);
                if (i >= 100) {
                    timer.cancel();
                    startGame();
                }
            }
        }, 0, 40); // 4 seconds total
    }

    private void startGame() {
        SwingUtilities.invokeLater(() -> {
            musicPlayer.play3(false);
            new Game();
            dispose();
        });
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Loading3().setVisible(true));
    }
}
