package playerz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import main.MusicPlayer;
import utilz.LoadSave;

public class CharacterPick extends JFrame {

    private Image backgroundImg;
    private MusicPlayer musicPlayer = new MusicPlayer();

    // Selection Data
    private int chosenP1 = 0;
    private int chosenP2 = 0;
    private int chosenMap = 0;

    // UI Components
    private ButtonGroup p1Group = new ButtonGroup();
    private ButtonGroup p2Group = new ButtonGroup();
    private ButtonGroup mapGroup = new ButtonGroup();

    private JTextArea p1Description;
    private JTextArea p2Description;
    private JButton playButton;

    public CharacterPick() {
        // Load Background using resource safely
        try {
            URL bgUrl = getClass().getResource("/loading_images/loading_bg3.gif");
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
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setTitle("Choose Your Character");
        utilz.WindowScaler.fitFrame(this, 1664, 896);
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

        // --- Header ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerPanel.setOpaque(false);
        JButton closeBtn = createButton("X");
        closeBtn.setBackground(Color.RED);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.addActionListener(e -> System.exit(0));
        headerPanel.add(closeBtn);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- Center Content (Split for P1, P2) ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Player 1 Section
        JPanel p1Panel = createPlayerSection("PLAYER 1", 1);
        // Player 2 Section
        JPanel p2Panel = createPlayerSection("PLAYER 2", 2);

        centerPanel.add(p1Panel);
        centerPanel.add(p2Panel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Bottom Content (Maps + Play) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 30, 50));

        // Map Selection
        JPanel mapsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(15, 15, 20, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.setColor(new Color(100, 100, 100, 100));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        mapsContainer.setOpaque(false);
        mapsContainer.setBorder(BorderFactory.createTitledBorder(null, "SELECT ARENA",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 18),
                new Color(220, 220, 220)));

        addMapOption(mapsContainer, LoadSave.BGIMG1, 1);
        addMapOption(mapsContainer, LoadSave.BGIMG2, 2);
        addMapOption(mapsContainer, LoadSave.BGIMG3, 3);
        addMapOption(mapsContainer, LoadSave.BGIMG4, 4);

        bottomPanel.add(mapsContainer, BorderLayout.CENTER);

        // Play Button Area
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        playButton = createButton("START GAME");
        playButton.setEnabled(false); // Disabled until ready
        playButton.setPreferredSize(new Dimension(250, 60));
        playButton.setBackground(new Color(50, 50, 50));
        playButton.setForeground(Color.GRAY);
        playButton.setFont(new Font("SansSerif", Font.BOLD, 22));
        playButton.addActionListener(e -> startGame());

        actionPanel.add(playButton);
        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createPlayerSection(String title, int playerNum) {
        JPanel panel = new JPanel(new BorderLayout(0, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Draw dark translucent background
                g2d.setColor(new Color(15, 15, 20, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
                // Draw sleek glowing border
                Color glowColor = playerNum == 1 ? new Color(0, 200, 255, 150) : new Color(255, 50, 50, 150);
                g2d.setColor(glowColor);
                g2d.setStroke(new BasicStroke(3f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(playerNum == 1 ? new Color(100, 220, 255) : new Color(255, 100, 100)); // Blue vs Red tint
        
        panel.add(titleLabel, BorderLayout.NORTH);

        // Character Grid
        JPanel charGrid = new JPanel(new GridLayout(1, 5, 10, 0));
        charGrid.setOpaque(false);

        // Add Characters
        addCharacterOption(charGrid, playerNum, 1, "taric_select.wav");
        addCharacterOption(charGrid, playerNum, 2, "none.wav");
        addCharacterOption(charGrid, playerNum, 3, "nautilus_select.wav");
        addCharacterOption(charGrid, playerNum, 4, "zed_select.wav");
        addCharacterOption(charGrid, playerNum, 5, "omen.wav");

        panel.add(charGrid, BorderLayout.CENTER);

        // Description Area
        JTextArea descArea = new JTextArea("Select a Character...");
        descArea.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 16));
        descArea.setForeground(Color.LIGHT_GRAY);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setPreferredSize(new Dimension(0, 60));

        if (playerNum == 1)
            p1Description = descArea;
        else
            p2Description = descArea;

        panel.add(descArea, BorderLayout.SOUTH);

        return panel;
    }

    private void addCharacterOption(JPanel parent, int playerNum, int charId, String soundFile) {
        JToggleButton btn = new JToggleButton();
        btn.setPreferredSize(new Dimension(100, 250)); // Tall buttons
        btn.setLayout(new BorderLayout());
        btn.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add Animation Panel
        AnimatedCharacterPanel aniPanel = new AnimatedCharacterPanel(charId);
        btn.add(aniPanel, BorderLayout.CENTER);

        // Hover & Selection Logic
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aniPanel.setHovered(true);
                if (!btn.isSelected()) btn.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                aniPanel.setHovered(false);
                if (!btn.isSelected()) btn.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));
            }
        });
        
        btn.addItemListener(e -> {
            boolean selected = (e.getStateChange() == java.awt.event.ItemEvent.SELECTED);
            aniPanel.setSelected(selected);
            Color glowColor = (playerNum == 1) ? new Color(0, 204, 255) : new Color(255, 50, 50);
            btn.setBorder(BorderFactory.createLineBorder(selected ? glowColor : new Color(50, 50, 50), selected ? 3 : 2));
        });

        // Logic
        ButtonGroup group = (playerNum == 1) ? p1Group : p2Group;
        group.add(btn);

        btn.addActionListener(e -> {
            playSound("/sounds/test-select.wav");
            playSound("/sounds/" + soundFile);

            if (playerNum == 1) {
                chosenP1 = charId;
                updateDescription(1, charId);
            } else {
                chosenP2 = charId;
                updateDescription(2, charId);
            }
            checkReady();
        });

        parent.add(btn);
    }

    private void addMapOption(JPanel parent, String imgName, int mapId) {
        JToggleButton btn = new JToggleButton();
        btn.setPreferredSize(new Dimension(200, 100));
        btn.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Load Icon
        java.awt.image.BufferedImage img = LoadSave.GetSpriteAtlas(imgName);
        if (img != null) {
            // Scale it smoothly
            Image scaled = img.getScaledInstance(196, 96, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
        } else {
            btn.setText("MAP " + mapId);
            btn.setForeground(Color.WHITE);
        }

        // Hover & Selection Logic
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.isSelected()) btn.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.isSelected()) btn.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));
            }
        });
        
        btn.addItemListener(e -> {
            boolean selected = (e.getStateChange() == java.awt.event.ItemEvent.SELECTED);
            btn.setBorder(BorderFactory.createLineBorder(selected ? new Color(0, 255, 100) : new Color(50, 50, 50), selected ? 4 : 2));
        });

        mapGroup.add(btn);

        btn.addActionListener(e -> {
            playSound("/sounds/test-select.wav");
            chosenMap = mapId;
            checkReady();
        });

        parent.add(btn);
    }

    private void updateDescription(int player, int charId) {
        String text = "";
        switch (charId) {
            case 1:
                text = "Rhino\nA heavily armored tank with immense strength.";
                break;
            case 2:
                text = "The Beheaded\nA swift and deadly warrior from the depths.";
                break;
            case 3:
                text = "Ender\nA mysterious entity capable of warping across the battlefield.";
                break;
            case 4:
                text = "Plague Doctor\nA master of alchemy who controls the zone.";
                break;
            case 5:
                text = "Paladin\nA righteous knight with balanced offense and defense.";
                break;
        }

        if (player == 1 && p1Description != null)
            p1Description.setText(text);
        if (player == 2 && p2Description != null)
            p2Description.setText(text);
    }

    private void checkReady() {
        boolean ready = (chosenP1 > 0 && chosenP2 > 0 && chosenMap > 0);

        // Sync with static fields for Game compatibility
        staticChosenP1 = chosenP1;
        staticChosenP2 = chosenP2;
        staticChosenMap = chosenMap;

        if (playButton != null) {
            playButton.setEnabled(ready);
            if (ready) {
                playButton.setBackground(new Color(0, 200, 100)); // Bright modern green
                playButton.setForeground(Color.WHITE);
                playButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(50, 255, 150), 3),
                    BorderFactory.createEmptyBorder(5, 15, 5, 15)
                ));
            } else {
                playButton.setBackground(new Color(50, 50, 50));
                playButton.setForeground(Color.GRAY);
                playButton.setBorder(BorderFactory.createEmptyBorder());
            }
        }
    }

    private void startGame() {
        if (chosenP1 > 0 && chosenP2 > 0 && chosenMap > 0) {
            playSound("/sounds/ps4-select-button1.wav");
            musicPlayer.play3(false); // Stop menu music? Logic copied from original

            // Create Loading Screen
            Loading3 l3 = new Loading3();
            l3.setVisible(true);
            l3.setLocationRelativeTo(null);

            // Dispose this
            this.dispose();
        }
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void playSound(final String path) {
        final String soundPath;
        if (path.startsWith("/")) {
            soundPath = path.substring(1);
        } else {
            soundPath = path;
        }
        new Thread(() -> {
            try {
                URL url = getClass().getResource("/" + soundPath);
                if (url == null) {
                    url = getClass().getResource(soundPath);
                }
                if (url != null) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                    clip.start();
                }
            } catch (Exception e) {
                System.err.println("Failed to play sound: " + path);
            }
        }).start();
    }

    // Getters for Game access if needed (static for now to match old style if
    // accessed statically,
    // BUT best to define instance getters if possible. Original used statics.
    // To be safe for Game.java usage, we might need these static fields or game
    // needs to pass instance.
    // Original had: public static int getChosen() etc.
    // I will add STATIC getters that return the LAST CHOSEN values to preserve
    // compatibility with existing Game code
    // that likely calls CharacterPick.getChosen() directly without an instance.

    // Compatibility Static Fields (updated by instance)
    private static int staticChosenP1;
    private static int staticChosenP2;
    private static int staticChosenMap;

    public static int getChosen() {
        return staticChosenP1;
    }

    public static int getPicked() {
        return staticChosenP2;
    }

    public static int getmapinfo() {
        return staticChosenMap;
    } // Original method name was non-static in file? Let's check view_file.

    // Original had instance methods getmapinfo/getPicked/getChosen accessing static
    // fields.
    // I'll update the static fields in my logic.

    // Update statics when selection changes
    private void updateStatics() {
        staticChosenP1 = chosenP1;
        staticChosenP2 = chosenP2;
        staticChosenMap = chosenMap;
    }

    // Override logic to ensure updateStatics is called
    // actually just doing it in addCharacterOption listener is easier.
    // I will add it to checkReady

    {
        // Add update listener hook if needed, but simple assignment is enough.
        // Re-injecting assignment in listeners.
    }

    // Overriding listeners to update statics
    private void updateGlobalState() {
        staticChosenP1 = chosenP1;
        staticChosenP2 = chosenP2;
        staticChosenMap = chosenMap;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CharacterPick().setVisible(true));
    }
}