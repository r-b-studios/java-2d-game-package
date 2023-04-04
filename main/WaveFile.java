package main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// Klasse zur vereinfachten Wiedergabe von Audio-Dateien
public class WaveFile {

    private Clip clip;

    public WaveFile(String src) {
        // Lädt den Audio-Clip
        try {
            var url = new File("assets\\audio\\" + src).toURI().toURL();
            var audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // spielt die Audio-Datei ab
    public void play() {
        assert clip != null;
        clip.start();
    }

    // beendet die Wiedergabe der Audio-Datei
    public void stop() {
        assert clip != null;
        clip.stop();
    }

    // gibt die Länge der Audio-Datei in Sekunden an
    public float getDuration() {
        assert clip != null;
        return (float) (clip.getMicrosecondLength() / Math.pow(10, 6));
    }

    // gibt die aktuelle Position der Wiedergabe in Sekunden an
    public float getPosition() {
        assert clip != null;
        return (float) (clip.getMicrosecondPosition() / Math.pow(10, 6));
    }
}