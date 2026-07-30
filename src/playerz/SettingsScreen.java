package playerz;

import inputs.KeyBindings;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SettingsScreen extends JFrame {

    private PVP_dashboard dashboard;
    private JButton currentlyBindingButton = null;
    private Runnable currentBindingCallback = null;

    public SettingsScreen(PVP_dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
        
        // Ensure the frame can receive key events
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (currentlyBindingButton != null && currentBindingCallback != null) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        // Cancel binding if escape is pressed
                        currentlyBindingButton.setText(currentlyBindingButton.getName()); // Restore old name
                    } else {
                        currentBindingCallback.run(); // This will be set to update the correct variable
                        updateBinding(e.getKeyCode());
                    }
                    currentlyBindingButton.setForeground(Color.WHITE);
                    currentlyBindingButton = null;
                    currentBindingCallback = null;
                }
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Settings & Controls");
        setUndecorated(true);
        setResizable(false);
        utilz.WindowScaler.fitFrame(this, 1000, 700);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(20, 20, 20));
        mainPanel.setLayout(null);

        JLabel titleLabel = new JLabel("SETTINGS & CONTROLS");
        titleLabel.setFont(new Font("Viner Hand ITC", Font.PLAIN, 48));
        titleLabel.setForeground(new Color(255, 204, 51));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 30, 1000, 80);
        mainPanel.add(titleLabel);

        // Player 1 Controls Box
        JPanel p1Panel = new JPanel();
        p1Panel.setBackground(new Color(40, 40, 40));
        p1Panel.setBorder(BorderFactory.createLineBorder(new Color(255, 50, 50), 2));
        p1Panel.setBounds(50, 150, 425, 400);
        p1Panel.setLayout(null);
        
        JLabel p1Title = new JLabel("PLAYER 1");
        p1Title.setFont(new Font("Yu Gothic", Font.BOLD, 24));
        p1Title.setForeground(new Color(255, 100, 100));
        p1Title.setBounds(20, 10, 300, 40);
        p1Panel.add(p1Title);

        int y = 70;
        y = addBindRow(p1Panel, "LEFT:", KeyBindings.p1Left, y, code -> KeyBindings.p1Left = code);
        y = addBindRow(p1Panel, "RIGHT:", KeyBindings.p1Right, y, code -> KeyBindings.p1Right = code);
        y = addBindRow(p1Panel, "JUMP:", KeyBindings.p1Jump, y, code -> KeyBindings.p1Jump = code);
        y = addBindRow(p1Panel, "DEFEND:", KeyBindings.p1Defend, y, code -> KeyBindings.p1Defend = code);
        y = addBindRow(p1Panel, "ATTACK 1:", KeyBindings.p1Attack1, y, code -> KeyBindings.p1Attack1 = code);
        y = addBindRow(p1Panel, "ATTACK 2:", KeyBindings.p1Attack2, y, code -> KeyBindings.p1Attack2 = code);
        y = addBindRow(p1Panel, "ATTACK 3:", KeyBindings.p1Attack3, y, code -> KeyBindings.p1Attack3 = code);
        y = addBindRow(p1Panel, "SPECIAL:", KeyBindings.p1Special, y, code -> KeyBindings.p1Special = code);

        mainPanel.add(p1Panel);

        // Player 2 Controls Box
        JPanel p2Panel = new JPanel();
        p2Panel.setBackground(new Color(40, 40, 40));
        p2Panel.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 255), 2));
        p2Panel.setBounds(525, 150, 425, 400);
        p2Panel.setLayout(null);
        
        JLabel p2Title = new JLabel("PLAYER 2");
        p2Title.setFont(new Font("Yu Gothic", Font.BOLD, 24));
        p2Title.setForeground(new Color(100, 150, 255));
        p2Title.setBounds(20, 10, 300, 40);
        p2Panel.add(p2Title);

        y = 70;
        y = addBindRow(p2Panel, "LEFT:", KeyBindings.p2Left, y, code -> KeyBindings.p2Left = code);
        y = addBindRow(p2Panel, "RIGHT:", KeyBindings.p2Right, y, code -> KeyBindings.p2Right = code);
        y = addBindRow(p2Panel, "JUMP:", KeyBindings.p2Jump, y, code -> KeyBindings.p2Jump = code);
        y = addBindRow(p2Panel, "DEFEND:", KeyBindings.p2Defend, y, code -> KeyBindings.p2Defend = code);
        y = addBindRow(p2Panel, "ATTACK 1:", KeyBindings.p2Attack1, y, code -> KeyBindings.p2Attack1 = code);
        y = addBindRow(p2Panel, "ATTACK 2:", KeyBindings.p2Attack2, y, code -> KeyBindings.p2Attack2 = code);
        y = addBindRow(p2Panel, "ATTACK 3:", KeyBindings.p2Attack3, y, code -> KeyBindings.p2Attack3 = code);
        y = addBindRow(p2Panel, "SPECIAL:", KeyBindings.p2Special, y, code -> KeyBindings.p2Special = code);

        mainPanel.add(p2Panel);

        // General Info Label
        JLabel infoLabel = new JLabel("Click any button to rebind a key. Press ESC to cancel.");
        infoLabel.setFont(new Font("Yu Gothic", Font.ITALIC, 16));
        infoLabel.setForeground(new Color(150, 255, 150));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setBounds(0, 560, 1000, 30);
        mainPanel.add(infoLabel);

        // Back Button
        JButton backBtn = new JButton("SAVE & BACK");
        backBtn.setBackground(new Color(50, 50, 50));
        backBtn.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBounds(375, 610, 250, 60);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyBindings.save();
                dispose();
                if (dashboard != null) {
                    dashboard.setVisible(true);
                }
            }
        });
        mainPanel.add(backBtn);

        getContentPane().add(mainPanel);
    }
    
    private interface KeyBindCallback {
        void bind(int keyCode);
    }

    private int addBindRow(JPanel panel, String labelText, int currentKey, int y, KeyBindCallback callback) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setBounds(20, y, 120, 30);
        panel.add(lbl);

        JButton btn = new JButton(KeyEvent.getKeyText(currentKey));
        btn.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBounds(150, y, 200, 30);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addActionListener(e -> {
            if (currentlyBindingButton != null) {
                currentlyBindingButton.setText(currentlyBindingButton.getName());
                currentlyBindingButton.setForeground(Color.WHITE);
            }
            currentlyBindingButton = btn;
            currentlyBindingButton.setName(btn.getText()); // Store old text
            currentlyBindingButton.setText("Press Key...");
            currentlyBindingButton.setForeground(Color.YELLOW);
            
            currentBindingCallback = () -> {
                // The key listener will call this before updating the text
            };
            
            // Re-request focus to the frame so keylistener works
            SettingsScreen.this.requestFocusInWindow();
        });
        
        // Attach the real callback to the button so the key listener can execute it
        btn.putClientProperty("callback", callback);

        panel.add(btn);
        return y + 35;
    }
    
    private void updateBinding(int keyCode) {
        KeyBindCallback cb = (KeyBindCallback) currentlyBindingButton.getClientProperty("callback");
        if (cb != null) {
            cb.bind(keyCode);
            currentlyBindingButton.setText(KeyEvent.getKeyText(keyCode));
        }
    }
}
