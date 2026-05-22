package playerz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SettingsScreen extends JFrame {

    private PVP_dashboard dashboard;

    public SettingsScreen(PVP_dashboard dashboard) {
        this.dashboard = dashboard;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Settings & Controls");
        setUndecorated(true);
        setResizable(false);
        setSize(1000, 700);

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
        p1Panel.setBounds(100, 150, 350, 400);
        p1Panel.setLayout(null);
        
        JLabel p1Title = new JLabel("PLAYER 1");
        p1Title.setFont(new Font("Yu Gothic", Font.BOLD, 24));
        p1Title.setForeground(new Color(255, 100, 100));
        p1Title.setBounds(20, 10, 300, 40);
        p1Panel.add(p1Title);

        String[] p1Controls = {
            "MOVEMENT: W A S D",
            "JUMP: W",
            "ATTACK 1: Z",
            "ATTACK 2: X",
            "ATTACK 3: C",
            "SPECIAL: V"
        };
        
        int y = 70;
        for (String ctrl : p1Controls) {
            JLabel lbl = new JLabel(ctrl);
            lbl.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
            lbl.setForeground(Color.WHITE);
            lbl.setBounds(20, y, 300, 30);
            p1Panel.add(lbl);
            y += 40;
        }
        mainPanel.add(p1Panel);

        // Player 2 Controls Box
        JPanel p2Panel = new JPanel();
        p2Panel.setBackground(new Color(40, 40, 40));
        p2Panel.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 255), 2));
        p2Panel.setBounds(550, 150, 350, 400);
        p2Panel.setLayout(null);
        
        JLabel p2Title = new JLabel("PLAYER 2");
        p2Title.setFont(new Font("Yu Gothic", Font.BOLD, 24));
        p2Title.setForeground(new Color(100, 150, 255));
        p2Title.setBounds(20, 10, 300, 40);
        p2Panel.add(p2Title);

        String[] p2Controls = {
            "MOVEMENT: ARROWS",
            "JUMP: UP ARROW",
            "ATTACK 1: NUMPAD 1",
            "ATTACK 2: NUMPAD 2",
            "ATTACK 3: NUMPAD 3",
            "SPECIAL: NUMPAD 4"
        };
        
        y = 70;
        for (String ctrl : p2Controls) {
            JLabel lbl = new JLabel(ctrl);
            lbl.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
            lbl.setForeground(Color.WHITE);
            lbl.setBounds(20, y, 300, 30);
            p2Panel.add(lbl);
            y += 40;
        }
        mainPanel.add(p2Panel);

        // General Info Label
        JLabel infoLabel = new JLabel("Controls are currently fixed. Custom keybinds coming soon!");
        infoLabel.setFont(new Font("Yu Gothic", Font.ITALIC, 14));
        infoLabel.setForeground(new Color(150, 150, 150));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setBounds(0, 545, 1000, 30);
        mainPanel.add(infoLabel);

        // Back Button
        JButton backBtn = new JButton("BACK");
        backBtn.setBackground(new Color(50, 50, 50));
        backBtn.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBounds(400, 580, 200, 60);
        backBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (dashboard != null) {
                    dashboard.setVisible(true);
                }
            }
        });
        mainPanel.add(backBtn);

        getContentPane().add(mainPanel);
    }
}
