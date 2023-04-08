package main.tools;

import java.util.ArrayList;
import java.util.List;

public class Animation {

    public record Frame(String imgSrc, float duration) { }

    public final Frame[] frames;
    public final boolean LOOP;
    public boolean finished;

    public Animation(boolean loop, Frame firstFrame, Frame... frames) {
        this.LOOP = loop;
        var framesList = new ArrayList<Frame>();

        framesList.add(firstFrame);
        framesList.addAll(List.of(frames));

        this.frames = framesList.toArray(new Frame[0]);
    }
}