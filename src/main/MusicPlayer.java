package main;

import java.io.IOException;
import javax.sound.sampled.*;

public class MusicPlayer {

    private boolean isPlaying = false;
    private Clip clip1;
    private Clip clip2;
    private Clip clip3;
    private Clip clip4;

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
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.setFramePosition(0);
                    if (isPlaying) {
                        clip.start();
                    }
                }
            });
            return clip;
        } catch (Exception e) {
            System.err.println("Error loading music (" + path + "): " + e.getMessage());
            return null;
        }
    }

    public void play1(boolean start) {
        if (clip1 == null)
            return;
        if (start && !isPlaying) {
            clip1.start();
            isPlaying = true;
        } else if (!start && isPlaying) {
            clip1.stop();
            isPlaying = false;
        }
    }

    public void play2(boolean start) {
        if (clip2 == null)
            return;
        if (start && !isPlaying) {
            clip2.start();
            isPlaying = true;
        } else if (!start && isPlaying) {
            clip2.stop();
            isPlaying = false;
        }
    }

    public void play3(boolean start) {
        if (clip3 == null)
            return;
        if (start && !isPlaying) {
            clip3.start();
            isPlaying = true;
        } else if (!start && isPlaying) {
            clip3.stop();
            isPlaying = false;
        }
    }

    public void play4(boolean start) {
        if (clip4 == null)
            return;
        if (start && !isPlaying) {
            clip4.start();
            isPlaying = true;
        } else if (!start && isPlaying) {
            clip4.stop();
            isPlaying = false;
        }
    }
}
