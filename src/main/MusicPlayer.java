package main;

import java.io.IOException;
import javax.sound.sampled.*;

public class MusicPlayer {

    private Clip clip1;
    private Clip clip2;
    private Clip clip3;
    private Clip clip4;
    
    // Default volume reduction in decibels (e.g., -10.0f makes it quieter)
    private final float DEFAULT_VOLUME_DB = -15.0f;

    public MusicPlayer() {
        clip1 = loadClip("/sounds/RosemaryIslandBattleMusic.wav");
        clip2 = loadClip("/sounds/Lobbytheme.wav");
        clip3 = loadClip("/sounds/loadingtheme.wav");
        clip4 = loadClip("/sounds/pixelbits.wav");
    }

    private Clip loadClip(String path) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url == null) {
                System.err.println("Audio resource not found: " + path);
                return null;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            // Set default volume slightly lower so it's not blasting
            setVolume(clip, DEFAULT_VOLUME_DB);
            
            return clip;
        } catch (Exception e) {
            System.err.println("Error loading music (" + path + "): " + e.getMessage());
            return null;
        }
    }
    
    private void setVolume(Clip clip, float decibels) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(decibels);
        }
    }

    public void stopAll() {
        if (clip1 != null && clip1.isRunning()) clip1.stop();
        if (clip2 != null && clip2.isRunning()) clip2.stop();
        if (clip3 != null && clip3.isRunning()) clip3.stop();
        if (clip4 != null && clip4.isRunning()) clip4.stop();
    }

    public void play1(boolean start) {
        if (clip1 == null) return;
        if (start) {
            stopAll();
            clip1.setFramePosition(0);
            clip1.loop(Clip.LOOP_CONTINUOUSLY); // Seamless looping natively
        } else {
            clip1.stop();
        }
    }

    public void play2(boolean start) {
        if (clip2 == null) return;
        if (start) {
            stopAll();
            clip2.setFramePosition(0);
            clip2.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip2.stop();
        }
    }

    public void play3(boolean start) {
        if (clip3 == null) return;
        if (start) {
            stopAll();
            clip3.setFramePosition(0);
            clip3.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip3.stop();
        }
    }

    public void play4(boolean start) {
        if (clip4 == null) return;
        if (start) {
            stopAll();
            clip4.setFramePosition(0);
            clip4.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip4.stop();
        }
    }
}
