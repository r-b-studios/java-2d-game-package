package engine;

public class Animator extends Component {

    private final List<AnimationFrame> frames = new List<>();;
    private float cooldown;
    private int animationIndex;

    public Animator(AnimationFrame startFrame, AnimationFrame... frames) {
        setFrames(startFrame, frames);
    }

    @Override
    void update() {
        if (cooldown > 0) {
            cooldown -= 1f / gameObject.getFPS();
        } else {
            if (animationIndex < frames.size() - 1) {
                animationIndex++;
            } else {
                animationIndex = 0;
            }

            var frame = frames.get(animationIndex);
            frame.animation().run();

            // zieht die überlaufene Zeit vom neu gestzten Cooldown ab, um auszugleichen
            var tmp = cooldown;
            cooldown = frame.duration() - tmp;
        }
    }

    public void setFrames(AnimationFrame firstFrame, AnimationFrame... frames) {
        this.frames.clear();
        this.frames.add(firstFrame);
        this.frames.addAll(frames);
        firstFrame.animation().run();
    }
}