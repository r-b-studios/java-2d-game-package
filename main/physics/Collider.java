package main.physics;

import main.app.GamePanel;
import main.app.Updater;
import main.entity.Entity;
import main.tools.List;
import main.tools.Padding;

public abstract class Collider implements Updater {

    // Das Entity, zu dem der Collider gehört
    private final Entity entity;
    private final GamePanel panel;

    // beinhaltet alle Entities, deren Collider gerade mit diesem Collider kollidieren
    private final List<Entity> collidingEntities = new List<>();

    public Collider(Entity entity, GamePanel panel) {
        this.entity = entity;
        this.panel = panel;
    }

    @Override
    public final void update() {
        collidingEntities.clear();

        panel.entities.forEach(e -> {

            if (e.collider != null && e != entity) {

                var pad = getPadding();
                var pad2 = e.collider.getPadding();

                // überprüft ob die beiden Entities collidieren
                if (
                    entity.x + pad.left <= e.x + pad2.left + e.width - pad2.right &&
                    entity.x + pad.left + entity.width - pad.right >= e.x + pad2.left &&
                    entity.y + pad.top <= e.y + pad2.top + e.height - pad2.bottom &&
                    entity.y + pad.top + entity.height - pad.bottom >= e.y + pad2.top
                ){
                    // die Distanz auf der Vertikalen Achse aus sich der beiden Entities
                    var hDis = entity.x + pad.left + entity.width - pad.right - e.x + pad2.left;
                    var hDis2 = e.x + pad2.left + e.width - pad2.right - entity.x + pad.left;

                    var vDis = entity.y + pad.top + entity.height - pad.bottom - e.y + pad2.top;
                    var vDis2 = e.y + pad2.top + e.height - pad2.bottom - entity.y + pad.top;

                    var type = hDis > vDis && hDis >= 0 ? Collision.VERTICALLY : Collision.HORIZONTALLY;
                    var type2 = hDis2 > vDis2 && hDis2 >= 0 ? Collision.VERTICALLY : Collision.HORIZONTALLY;

                    this.onCollide(e, type);
                    collidingEntities.add(e);
                }
            }
        });
    }

    public Entity[] getCollidingEntites() {
        return collidingEntities.toArray(new Entity[0]);
    }

    protected void onCollide(Entity other, Collision type) { }

    protected Padding getPadding() {
        return new Padding(0, 0, 0, 0);
    }
}