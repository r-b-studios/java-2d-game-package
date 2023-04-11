package engine;

import java.util.function.BiConsumer;

public class Collider extends Component {

    private final Padding padding;
    private final BiConsumer<GameObject, Collision> onCollide;

    public Collider() {
        this(Padding.ZERO, (g, e) -> { });
    }

    public Collider(Padding padding) {
        this(padding, (g, e) -> { });
    }

    public Collider(BiConsumer<GameObject, Collision> onCollide) {
        this(Padding.ZERO, onCollide);
    }

    public Collider(Padding padding, BiConsumer<GameObject, Collision> onCollide) {
        this.padding = padding;
        this.onCollide = onCollide;
    }

    @Override
    void update() {
        if (padding != null) {
            var list = new List<>(Game.scene.getAllChildren());

            list.forEach(obj -> {
                if (obj != gameObject) {
                    obj.getComponents(Collider.class).forEach(c2 -> {
                        var pad = this.padding;
                        var pad2 = c2.padding;

                        if (
                            gameObject.getGlobalPosition().x + pad.left() <= obj.getGlobalPosition().x + pad2.left() + obj.getSize().width - pad2.right() &&
                            gameObject.getGlobalPosition().x + pad.left() + gameObject.getSize().width - pad.right() >= obj.getGlobalPosition().x + pad2.left() &&
                            gameObject.getGlobalPosition().y + pad.top() <= obj.getGlobalPosition().y + pad2.top() + obj.getSize().height - pad2.bottom() &&
                            gameObject.getGlobalPosition().y + pad.top() + gameObject.getSize().height - pad.bottom() >= obj.getGlobalPosition().y + pad2.top()
                        ) {
                            var hDis = gameObject.getGlobalPosition().x + pad.left() + gameObject.getSize().width - pad.right() - obj.getGlobalPosition().x + pad2.left();
                            var hDis2 = obj.getGlobalPosition().x + pad2.left() + obj.getSize().width - pad2.right() - gameObject.getGlobalPosition().x + pad.left();

                            var vDis = gameObject.getGlobalPosition().y + pad.top() + gameObject.getSize().height - pad.bottom() - obj.getGlobalPosition().y + pad2.top();
                            var vDis2 = obj.getGlobalPosition().y + pad2.top() + obj.getSize().height - pad2.bottom() - gameObject.getGlobalPosition().y + pad.top();

                            var type = hDis > vDis && hDis >= 0 ? Collision.VERTICALLY : Collision.HORIZONTALLY;
                            var type2 = hDis2 > vDis2 && hDis2 >= 0 ? Collision.VERTICALLY : Collision.HORIZONTALLY;

                            onCollide.accept(obj, type);
                            c2.onCollide.accept(gameObject, type2);
                        }
                    });
                }
            });
        }
    }
}