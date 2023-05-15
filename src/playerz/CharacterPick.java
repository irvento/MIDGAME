
package playerz;



import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.Game;
import main.GameRounds;
import main.MusicPlayer;

public class CharacterPick extends javax.swing.JFrame {
    
    MusicPlayer musicPlayer = new MusicPlayer(); 
    
    public CharacterPick() {
        initComponents();
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pick2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        charac1b = new javax.swing.JToggleButton();
        charac2b = new javax.swing.JToggleButton();
        charac3b = new javax.swing.JToggleButton();
        charac4b = new javax.swing.JToggleButton();
        charac5b = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        charac1 = new javax.swing.JToggleButton();
        charac2 = new javax.swing.JToggleButton();
        charac3 = new javax.swing.JToggleButton();
        charac4 = new javax.swing.JToggleButton();
        charac5 = new javax.swing.JToggleButton();
        map1 = new javax.swing.JToggleButton();
        map2 = new javax.swing.JToggleButton();
        map3 = new javax.swing.JToggleButton();
        map4 = new javax.swing.JToggleButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\dbbg.jpg")); // NOI18N
        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1664, 896));
        setMinimumSize(new java.awt.Dimension(1664, 896));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1664, 896));

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.setMaximumSize(new java.awt.Dimension(1664, 896));
        jPanel14.setMinimumSize(new java.awt.Dimension(1664, 896));
        jPanel14.setPreferredSize(new java.awt.Dimension(1664, 896));
        jPanel14.setRequestFocusEnabled(false);
        jPanel14.setVerifyInputWhenFocusTarget(false);
        jPanel14.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\OneDrive\\Pictures\\player1profile.png")); // NOI18N
        jLabel1.setText("PLAYER 1 ");
        jPanel14.add(jLabel1);
        jLabel1.setBounds(30, 40, 177, 42);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("X");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton1);
        jButton1.setBounds(1610, 10, 40, 40);

        pick2.setBackground(new java.awt.Color(0, 153, 153));
        pick2.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\OneDrive\\Pictures\\save.png")); // NOI18N
        pick2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 255, 255), new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 102), new java.awt.Color(0, 204, 204)));
        pick2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pick2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pick2ActionPerformed(evt);
            }
        });
        jPanel14.add(pick2);
        pick2.setBounds(730, 770, 220, 41);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\OneDrive\\Pictures\\player2profile.png")); // NOI18N
        jLabel2.setText("PLAYER 2");
        jPanel14.add(jLabel2);
        jLabel2.setBounds(1430, 40, 165, 48);

        jPanel1.setBackground(new java.awt.Color(0,0, 0,50));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 255), new java.awt.Color(0, 204, 255), new java.awt.Color(255, 51, 0), new java.awt.Color(255, 204, 0)));
        jPanel1.setLayout(null);

        charac1b.setBackground(new java.awt.Color(0,0, 0,50));
        charac1b.setForeground(new java.awt.Color(153, 51, 0));
        charac1b.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\rhinoprof.jpg")); // NOI18N
        charac1b.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 255), new java.awt.Color(204, 0, 0), new java.awt.Color(255, 0, 0)));
        charac1b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        charac1b.setMaximumSize(new java.awt.Dimension(140, 330));
        charac1b.setMinimumSize(new java.awt.Dimension(140, 330));
        charac1b.setPreferredSize(new java.awt.Dimension(140, 330));
        charac1b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac1bActionPerformed(evt);
            }
        });
        jPanel1.add(charac1b);
        charac1b.setBounds(620, 20, 140, 330);

        charac2b.setBackground(new java.awt.Color(0,0, 0,50));
        charac2b.setForeground(new java.awt.Color(153, 51, 0));
        charac2b.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\beheadprof.jpg")); // NOI18N
        charac2b.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 255), new java.awt.Color(204, 0, 0), new java.awt.Color(255, 0, 0)));
        charac2b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        charac2b.setMaximumSize(new java.awt.Dimension(140, 330));
        charac2b.setMinimumSize(new java.awt.Dimension(140, 330));
        charac2b.setPreferredSize(new java.awt.Dimension(140, 330));
        charac2b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac2bActionPerformed(evt);
            }
        });
        jPanel1.add(charac2b);
        charac2b.setBounds(470, 20, 140, 330);

        charac3b.setBackground(new java.awt.Color(0,0, 0,50));
        charac3b.setForeground(new java.awt.Color(153, 51, 0));
        charac3b.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\endprof.jpg")); // NOI18N
        charac3b.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 255), new java.awt.Color(204, 0, 0), new java.awt.Color(255, 0, 0)));
        charac3b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        charac3b.setMaximumSize(new java.awt.Dimension(140, 330));
        charac3b.setMinimumSize(new java.awt.Dimension(140, 330));
        charac3b.setPreferredSize(new java.awt.Dimension(140, 330));
        charac3b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac3bActionPerformed(evt);
            }
        });
        jPanel1.add(charac3b);
        charac3b.setBounds(320, 20, 140, 330);

        charac4b.setBackground(new java.awt.Color(0,0, 0,50));
        charac4b.setForeground(new java.awt.Color(153, 51, 0));
        charac4b.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\pdprof.jpg")); // NOI18N
        charac4b.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 255), new java.awt.Color(204, 0, 0), new java.awt.Color(255, 0, 0)));
        charac4b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        charac4b.setMaximumSize(new java.awt.Dimension(140, 330));
        charac4b.setMinimumSize(new java.awt.Dimension(140, 330));
        charac4b.setPreferredSize(new java.awt.Dimension(140, 330));
        charac4b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac4bActionPerformed(evt);
            }
        });
        jPanel1.add(charac4b);
        charac4b.setBounds(170, 20, 140, 330);

        charac5b.setBackground(new java.awt.Color(0,0, 0,50));
        charac5b.setForeground(new java.awt.Color(0,0, 0,50));
        charac5b.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\PaladinBG.png")); // NOI18N
        charac5b.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 255), new java.awt.Color(0, 0, 255), new java.awt.Color(255, 153, 0), new java.awt.Color(255, 0, 0)));
        charac5b.setMaximumSize(new java.awt.Dimension(140, 330));
        charac5b.setMinimumSize(new java.awt.Dimension(140, 330));
        charac5b.setPreferredSize(new java.awt.Dimension(140, 330));
        charac5b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac5bActionPerformed(evt);
            }
        });
        jPanel1.add(charac5b);
        charac5b.setBounds(20, 20, 140, 330);

        jPanel14.add(jPanel1);
        jPanel1.setBounds(850, 90, 780, 370);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0, 50));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 0, 204), new java.awt.Color(255, 153, 0), new java.awt.Color(255, 0, 0)));
        jPanel2.setLayout(null);

        charac1.setBackground(new java.awt.Color(0,0, 0,50));
        charac1.setForeground(new java.awt.Color(153, 51, 0));
        charac1.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\rhinoprof.jpg")); // NOI18N
        charac1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 255), new java.awt.Color(204, 0, 0), new java.awt.Color(255, 0, 0)));
        charac1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        charac1.setMaximumSize(new java.awt.Dimension(140, 330));
        charac1.setMinimumSize(new java.awt.Dimension(140, 330));
        charac1.setPreferredSize(new java.awt.Dimension(140, 330));
        charac1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac1ActionPerformed(evt);
            }
        });
        jPanel2.add(charac1);
        charac1.setBounds(20, 20, 140, 330);

        charac2.setBackground(new java.awt.Color(0,0, 0,50));
        charac2.setForeground(new java.awt.Color(153, 51, 0));
        charac2.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\beheadprof.jpg")); // NOI18N
        charac2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 255), new java.awt.Color(204, 0, 0), new java.awt.Color(255, 0, 0)));
        charac2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        charac2.setMaximumSize(new java.awt.Dimension(140, 330));
        charac2.setMinimumSize(new java.awt.Dimension(140, 330));
        charac2.setPreferredSize(new java.awt.Dimension(140, 330));
        charac2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac2ActionPerformed(evt);
            }
        });
        jPanel2.add(charac2);
        charac2.setBounds(170, 20, 140, 330);

        charac3.setBackground(new java.awt.Color(0,0, 0,50));
        charac3.setForeground(new java.awt.Color(153, 51, 0));
        charac3.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\endprof.jpg")); // NOI18N
        charac3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 255), new java.awt.Color(204, 0, 0), new java.awt.Color(255, 0, 0)));
        charac3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        charac3.setMaximumSize(new java.awt.Dimension(140, 330));
        charac3.setMinimumSize(new java.awt.Dimension(140, 330));
        charac3.setPreferredSize(new java.awt.Dimension(140, 330));
        charac3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac3ActionPerformed(evt);
            }
        });
        jPanel2.add(charac3);
        charac3.setBounds(320, 20, 140, 330);

        charac4.setBackground(new java.awt.Color(0,0, 0,50));
        charac4.setForeground(new java.awt.Color(153, 51, 0));
        charac4.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\pdprof.jpg")); // NOI18N
        charac4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 102, 255), new java.awt.Color(204, 0, 0), new java.awt.Color(255, 0, 0)));
        charac4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        charac4.setMaximumSize(new java.awt.Dimension(140, 330));
        charac4.setMinimumSize(new java.awt.Dimension(140, 330));
        charac4.setPreferredSize(new java.awt.Dimension(140, 330));
        charac4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac4ActionPerformed(evt);
            }
        });
        jPanel2.add(charac4);
        charac4.setBounds(470, 20, 140, 330);

        charac5.setBackground(new java.awt.Color(0,0, 0,50));
        charac5.setForeground(new java.awt.Color(0,0, 0,50));
        charac5.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\PaladinBG.png")); // NOI18N
        charac5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 255), new java.awt.Color(0, 0, 255), new java.awt.Color(255, 153, 0), new java.awt.Color(255, 0, 0)));
        charac5.setMaximumSize(new java.awt.Dimension(140, 330));
        charac5.setMinimumSize(new java.awt.Dimension(140, 330));
        charac5.setPreferredSize(new java.awt.Dimension(140, 330));
        charac5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charac5ActionPerformed(evt);
            }
        });
        jPanel2.add(charac5);
        charac5.setBounds(620, 20, 140, 330);

        jPanel14.add(jPanel2);
        jPanel2.setBounds(40, 90, 780, 370);

        map1.setBackground(new java.awt.Color(0, 0, 0, 50));
        map1.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\bgimg2.png")); // NOI18N
        map1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 0, 255), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 0, 0)));
        map1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        map1.setMaximumSize(new java.awt.Dimension(285, 155));
        map1.setMinimumSize(new java.awt.Dimension(285, 155));
        map1.setPreferredSize(new java.awt.Dimension(285, 155));
        map1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                map1ActionPerformed(evt);
            }
        });
        jPanel14.add(map1);
        map1.setBounds(50, 560, 285, 155);

        map2.setBackground(new java.awt.Color(0, 0, 0, 50));
        map2.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\bgimg3.png")); // NOI18N
        map2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 0, 255), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 0, 0)));
        map2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        map2.setMaximumSize(new java.awt.Dimension(285, 155));
        map2.setMinimumSize(new java.awt.Dimension(285, 155));
        map2.setPreferredSize(new java.awt.Dimension(285, 155));
        map2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                map2ActionPerformed(evt);
            }
        });
        jPanel14.add(map2);
        map2.setBounds(360, 560, 285, 155);

        map3.setBackground(new java.awt.Color(0, 0, 0, 50));
        map3.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\bgimg4.png")); // NOI18N
        map3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 0, 255), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 0, 0)));
        map3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        map3.setMaximumSize(new java.awt.Dimension(285, 155));
        map3.setMinimumSize(new java.awt.Dimension(285, 155));
        map3.setPreferredSize(new java.awt.Dimension(285, 155));
        map3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                map3ActionPerformed(evt);
            }
        });
        jPanel14.add(map3);
        map3.setBounds(670, 560, 285, 155);

        map4.setBackground(new java.awt.Color(0, 0, 0, 50));
        map4.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\bgimg5.png")); // NOI18N
        map4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 255), new java.awt.Color(0, 0, 255), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 0, 0)));
        map4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        map4.setMaximumSize(new java.awt.Dimension(285, 155));
        map4.setMinimumSize(new java.awt.Dimension(285, 155));
        map4.setPreferredSize(new java.awt.Dimension(285, 155));
        map4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                map4ActionPerformed(evt);
            }
        });
        jPanel14.add(map4);
        map4.setBounds(990, 560, 285, 155);

        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\OneDrive\\Pictures\\Saved Pictures\\Choose Map.png")); // NOI18N
        jPanel14.add(jLabel6);
        jLabel6.setBounds(40, 490, 250, 50);

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\OneDrive\\Pictures\\Saved Pictures\\pickbg.gif")); // NOI18N
        jLabel4.setText("1");
        jPanel14.add(jLabel4);
        jLabel4.setBounds(-530, -160, 2350, 1160);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    
    private void sound(String wav, boolean stop){
        try {                                         
            
            File zed = new File(wav);
            AudioInputStream audioStream = null;
            try {
                audioStream = AudioSystem.getAudioInputStream(zed);
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
            }
            Clip c = AudioSystem.getClip();
            
            if(stop){
            c.open(audioStream);
                c.start();
            }else if (!stop){
               c.close(); 
                c.stop();
            }
            
            
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    private void charac1bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac1bActionPerformed
       
        
       sound("src\\sounds\\test-select.wav", charac1b.isSelected());
       sound("src\\sounds\\taric_select.wav", charac1b.isSelected());
       
        noPick();
        picked = 1;
        charac5b.setSelected(false);
        charac4b.setSelected(false);
        charac3b.setSelected(false);
        charac2b.setSelected(false);
    }//GEN-LAST:event_charac1bActionPerformed

    private void charac2bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac2bActionPerformed
        sound("src\\sounds\\test-select.wav", charac2b.isSelected());
        sound("src\\sounds\\none.wav", charac2b.isSelected());
        
        noPick();
        picked = 2;
        charac5b.setSelected(false);
        charac4b.setSelected(false);
        charac3b.setSelected(false);
        charac1b.setSelected(false);
    }//GEN-LAST:event_charac2bActionPerformed

    private void charac3bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac3bActionPerformed
        sound("src\\sounds\\test-select.wav", charac3b.isSelected());
        sound("src\\sounds\\nautilus_select.wav", charac3b.isSelected() );
        
        noPick();
        picked = 3;
        charac5b.setSelected(false);
        charac4b.setSelected(false);
        charac1b.setSelected(false);
        charac2b.setSelected(false);
    }//GEN-LAST:event_charac3bActionPerformed

    private void charac4bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac4bActionPerformed
        sound("src\\sounds\\test-select.wav", charac4b.isSelected());
        sound("src\\sounds\\zed_select.wav", charac4b.isSelected());
        
        noPick();
        picked = 4;
        charac1b.setSelected(false);
        charac3b.setSelected(false);
        charac2b.setSelected(false);
        charac5b.setSelected(false);
        }//GEN-LAST:event_charac4bActionPerformed
   
    private void noPick(){
        if (getChosen() == 0 && getPicked() == 0){
        pick2.setSelected(false);
        pick2.setBorderPainted( false );
        pick2.setFocusPainted( false );
        }else{
        pick2.setSelected(true);
        pick2.setBorderPainted( true );
        pick2.setFocusPainted( true );
        }
    }
    

    private void pick2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pick2ActionPerformed
         if (getChosen() >= 1 && getPicked() >= 1 && getmapinfo() >= 1){
             
             
             
        sound("src\\sounds\\ps4-select-button1.wav", true);
         
        musicPlayer.play3(false);
        
        noPick();
        int randomnum;
            Random random = new Random();
            int min = 1;
            int max = 3;
            int randomNumber = random.nextInt(max - min + 1) + min;
            randomnum = randomNumber;
                if (randomnum == 1){
                    Loading1 l1 = new Loading1();
                    l1.setVisible(true);
                    l1.setLocationRelativeTo(null);
                    this.dispose();
                }
                if (randomnum == 2){
                    Loading2 l2 = new Loading2();
                    l2.setVisible(true);
                    l2.setLocationRelativeTo(null);
                    this.dispose();
                }
                if (randomnum == 3){
                    Loading3 l3 = new Loading3();
                    l3.setVisible(true);
                    l3.setLocationRelativeTo(null);
                    this.dispose();
                }   
            
        }else return;
         
    }//GEN-LAST:event_pick2ActionPerformed

    private void charac4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac4ActionPerformed
      sound("src\\sounds\\test-select.wav", charac4.isSelected());
        sound("src\\sounds\\zed_select.wav", charac4.isSelected());
        noPick();
        chosen = 4;
        charac5.setSelected(false);
        charac3.setSelected(false);
        charac2.setSelected(false);
        charac1.setSelected(false);
    }//GEN-LAST:event_charac4ActionPerformed

    private void charac3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac3ActionPerformed
     sound("src\\sounds\\test-select.wav", charac3.isSelected());
        sound("src\\sounds\\nautilus_select.wav", charac3.isSelected() );
       
        noPick();
        chosen = 3;
        charac5.setSelected(false);
        charac4.setSelected(false);
        charac2.setSelected(false);
        charac1.setSelected(false);
    }//GEN-LAST:event_charac3ActionPerformed

    private void charac2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac2ActionPerformed
       sound("src\\sounds\\test-select.wav", charac2.isSelected());
        sound("src\\sounds\\none.wav", charac2.isSelected());
        
        noPick();
        chosen = 2;
        charac5.setSelected(false);
        charac4.setSelected(false);
        charac3.setSelected(false);
        charac1.setSelected(false);
    }//GEN-LAST:event_charac2ActionPerformed

    private void charac1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac1ActionPerformed
      sound("src\\sounds\\test-select.wav", charac1.isSelected());
       sound("src\\sounds\\taric_select.wav", charac1.isSelected());
        noPick();
        chosen = 1;
        charac5.setSelected(false);
        charac4.setSelected(false);
        charac3.setSelected(false);
        charac2.setSelected(false);
    }//GEN-LAST:event_charac1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void charac5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac5ActionPerformed
        sound("src\\sounds\\test-select.wav", charac5.isSelected());
        sound("src\\sounds\\omen.wav", charac5.isSelected());
        noPick();
        chosen = 5;
        charac1.setSelected(false);
        charac4.setSelected(false);
        charac3.setSelected(false);
        charac2.setSelected(false);
    }//GEN-LAST:event_charac5ActionPerformed

    private void charac5bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charac5bActionPerformed
        sound("src\\sounds\\test-select.wav", charac5b.isSelected());
        sound("src\\sounds\\omen.wav", charac5b.isSelected());
        noPick();
        picked = 5;
        charac1b.setSelected(false);
        charac4b.setSelected(false);
        charac3b.setSelected(false);
        charac2b.setSelected(false);
    }//GEN-LAST:event_charac5bActionPerformed

    private void map3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_map3ActionPerformed
        sound("src\\sounds\\test-select.wav", map3.isSelected());
        noPick();
        lvlmap = 3;
        map1.setSelected(false);
        map2.setSelected(false);
        map4.setSelected(false);
// TODO add your handling code here:
    }//GEN-LAST:event_map3ActionPerformed

    private void map1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_map1ActionPerformed
        sound("src\\sounds\\test-select.wav", map1.isSelected());
        noPick();
        lvlmap = 1;
        map3.setSelected(false);
        map2.setSelected(false);
        map4.setSelected(false);
// TODO add your handling code here:
    }//GEN-LAST:event_map1ActionPerformed

    private void map2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_map2ActionPerformed
        sound("src\\sounds\\test-select.wav", map2.isSelected());
        noPick();
        lvlmap = 2;
        map1.setSelected(false);
        map3.setSelected(false);
        map4.setSelected(false);
    }//GEN-LAST:event_map2ActionPerformed

    private void map4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_map4ActionPerformed
        sound("src\\sounds\\test-select.wav", map4.isSelected());
        noPick();
        lvlmap = 4;
        map1.setSelected(false);
        map2.setSelected(false);
        map3.setSelected(false);
    }//GEN-LAST:event_map4ActionPerformed

private static int chosen;
private static int picked;
private static int lvlmap;

public int getmapinfo(){
    return lvlmap;
}

public int getPicked() {
    return picked;
}

public int getChosen() {
    return chosen;
}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CharacterPick.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CharacterPick.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CharacterPick.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CharacterPick.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            new CharacterPick().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton charac1;
    private javax.swing.JToggleButton charac1b;
    private javax.swing.JToggleButton charac2;
    private javax.swing.JToggleButton charac2b;
    private javax.swing.JToggleButton charac3;
    private javax.swing.JToggleButton charac3b;
    private javax.swing.JToggleButton charac4;
    private javax.swing.JToggleButton charac4b;
    private javax.swing.JToggleButton charac5;
    private javax.swing.JToggleButton charac5b;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToggleButton map1;
    private javax.swing.JToggleButton map2;
    private javax.swing.JToggleButton map3;
    private javax.swing.JToggleButton map4;
    private javax.swing.JButton pick2;
    // End of variables declaration//GEN-END:variables
}