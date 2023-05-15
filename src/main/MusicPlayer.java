package main;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class MusicPlayer {

    private boolean isPlaying = false;
    private Clip clip1;
    private Clip clip2;
    private Clip clip3;
    private Clip clip4;
    
    
    public MusicPlayer() {
        try {
            
            File musicFile = new File("src\\sounds\\RosemaryIslandBattleMusic.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip1 = AudioSystem.getClip();
            clip1.open(audioStream);
            clip1.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip1.setFramePosition(0);
                    if (isPlaying) {
                        clip1.start();
                    }
                }
            });
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
        
        try {
            File musicFile = new File("src\\sounds\\Lobbytheme.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip2 = AudioSystem.getClip();
            clip2.open(audioStream);
            clip2.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip2.setFramePosition(0);
                    if (isPlaying) {
                        clip2.start();
                    }
                }
            });
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
        
        try {
            File musicFile = new File("src\\sounds\\loadingtheme.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip3 = AudioSystem.getClip();
            clip3.open(audioStream);
            clip3.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip3.setFramePosition(0);
                    if (isPlaying) {
                        clip3.start();
                    }
                }
            });
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
        
        try {
            File musicFile = new File("src\\sounds\\pixelbits.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip4 = AudioSystem.getClip();
            clip4.open(audioStream);
            clip4.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip4.setFramePosition(0);
                    if (isPlaying) {
                        clip4.start();
                    }
                }
            });
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
    }

    public void play1(boolean start) {
        if (start && !isPlaying) {
            clip1.start();
            isPlaying = true;
        } else if (!start && isPlaying) {
            clip1.stop();
            isPlaying = false;
        }
    }
    
    public void play2(boolean start) {
        if (start && !isPlaying) {
            clip2.start();
            isPlaying = true;
        } else if (!start && isPlaying) {
            clip2.stop();
            isPlaying = false;
        }
    }
    public void play3(boolean start) {
        if (start && !isPlaying) {
            clip3.start();
            isPlaying = true;
        } else if (!start && isPlaying) {
            clip3.stop();
            isPlaying = false;
        }
    }
    public void play4(boolean start) {
        if (start && !isPlaying) {
            clip4.start();
            isPlaying = true;
        } else if (!start && isPlaying) {
            clip4.stop();
            isPlaying = false;
        }
    }
}
