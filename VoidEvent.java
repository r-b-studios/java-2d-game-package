package engine;

public class VoidEvent {
    // List mit allen Funktionen (Listener) die ausgeführt, wenn das Event abgefeuert [fire()] wird
    private final List<Runnable> listeners = new List<>();

    // abonniert das Event (fügt Listener hinzu)
    public void subscribe(Runnable listener) {
        listeners.add(listener);
    }

    // führt alle Listener aus
    public void fire() {
        listeners.forEach(Runnable::run);
    }
}