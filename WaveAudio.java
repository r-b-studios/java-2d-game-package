package engine;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

// Zum Abspielen von .wav-Dateien
public class WaveAudio {

    private Clip clip;

    public WaveAudio(String src) {
        try {
            var url = new File("assets\\" + src).toURI().toURL();
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