package main.tools;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class WaveFile {

    private Clip clip;

    public WaveFile(String fileName) {
        try {
            var url = new File("assets\\audio\\" + fileName).toURI().toURL();
            var stream = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(stream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play(boolean loop) {
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.start();
        }
    }

    public void stop() {
        clip.stop();
    }

    public float getLength() {
        return (float) (clip.getMicrosecondLength() / 1_000_000);
    }
}