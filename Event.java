package engine;

import java.util.function.Consumer;

public class Event<T> {

    // List mit allen Funktionen (Listener) die ausgeführt, wenn das Event abgefeuert [fire()] wird
    private final List<Consumer<T>> listeners = new List<>();

    // abonniert das Event (fügt Listener hinzu)
    public void subscribe(Consumer<T> listener) {
        listeners.add(listener);
    }

    // führt alle Listener aus
    public void fire(T value) {
        listeners.forEach(l -> {
            l.accept(value);
        });
    }
}