package engine;

import development.Scene;
import java.awt.*;

public abstract class GameObject {

    final List<GameObject> children = new List<>();
    private GameObject parent;
    private Point position;
    private Size size;
    private final List<Component> components = new List<>();

    private int layer;

    void draw(Graphics2D g, int x, int y, int width, int height) { }

    public void load() {
        position = new Point(0, 0);
        size = new Size(Game.settings.tileSize(), Game.settings.tileSize());
    }

    public void update() {
        components.forEach(Component::update);
    }

    protected void addChild(GameObject obj) {
        add(obj, this);
    }

    protected void add(GameObject obj) {
        add(obj, Game.scene);
    }
    
    protected void add(GameObject obj, GameObject parent) {
        obj.parent = parent;
        obj.load();
        children.add(obj);
    }

    public GameObject[] getChildren() {
        return children.toArray(new GameObject[0]);
    }

    public GameObject[] getAllChildren() {
        return getAllChildren(this);
    }

    private GameObject[] getAllChildren(GameObject obj) {
        var result = new List<GameObject>();
        result.addAll(obj);

        obj.children.forEach(c -> {
            result.addAll(getAllChildren(c));
        });

        return result.toArray(new GameObject[0]);
    }

    public void destroy() {
        destroy(this);
    }

    protected void destroy(GameObject obj) {
        if (obj instanceof Scene) {
            System.out.println("Error! Scene objects can't be deleted.");
        } else {
            obj.parent.children.remove(obj);
        }
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public Point getPosition() {
         return position;
    }

    public Point getGlobalPosition() {
        return getGlobalPosition(this);
    }

    private Point getGlobalPosition(GameObject obj) {
        var result = (Point) obj.position.clone();

        if (obj.parent != null) {
            // rekursiver Aufruf
            var parentPos = getGlobalPosition(obj.parent);
            result.x += parentPos.x;
            result.y += parentPos.y;
        }

        return result;
    }

    public void move(float x, float y) {
        position.x += x;
        position.y += y;
    }

    public void setSize(float width, float height) {
        size.width = width;
        size.height = height;
    }

    public Size getSize() {
        return size;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public GameObject getParent() {
        return parent;
    }

    protected float getFPS() {
        return GamePanel.INSTANCE.fps;
    }

    public void setBackground(Color color) {
        GamePanel.INSTANCE.setBackground(color);
    }

    public void addComponent(Component component) {
        component.gameObject = this;
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public <T extends Component> void removeComponents(Class<T> componentClass) {
        components.forEach(c -> {
            if (componentClass.isInstance(c)) {
                components.remove(c);
            }
        });
    }

    public Component[] getComponents() {
        return components.toArray(new Component[0]);
    }

    public <T extends Component> List<T> getComponents(Class<T> componentClass) {
        var result = new List<T>();

        components.forEach(c -> {
            if (componentClass.isInstance(c)) {
                result.add((T) c);
            }
        });

        return result;
    }

    public <T extends Component> boolean hasComponent(Class<T> componentClass) {
        for (var c : components) {
            if (componentClass.isInstance(c)) {
                return true;
            }
        }

        return false;
    }

    protected Point getMousePosition() {
        var mousePos = GamePanel.INSTANCE.getMousePosition();

        if (mousePos == null) {
            return null;
        } else {
            return new Point(
                mousePos.x / Game.settings.pixelSize(),
                mousePos.y / Game.settings.pixelSize()
            );
        }
    }

    protected InputListener getInput() {
        return Game.inputListener;
    }

    protected void setDefaultCursor() {
        GamePanel.INSTANCE.setCursor(GamePanel.INSTANCE.defaultCursor);
    }

    protected void setHoverCursor() {
        GamePanel.INSTANCE.setCursor(GamePanel.INSTANCE.hoverCursor);
    }
}