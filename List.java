package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

// Eine Subklasse der Klasse ArrayList, die eine ConcurrentModificationException vermeidet
public class List<T> extends ArrayList<T> {

    public List() {
        super();
    }

    public List(List<T> collection) {
        super(collection);
    }

    @SafeVarargs
    public List(T... elements) {
        super();
        this.addAll(Arrays.asList(elements));
    }

    // vermeidet CurrentModificationException
    @Override
    public void forEach(Consumer<? super T> action) {
        var actions = new ArrayList<Runnable>();

        super.forEach(x -> {
            actions.add(() -> {
                action.accept(x);
            });
        });

        actions.forEach(Runnable::run);
    }

    // removeAll() nimmt nun beliebig viele Elemente mit nur einem Aufruf an
    public void removeAll(T... elements) {
        super.removeAll(Arrays.stream(elements).toList());
    }
    // addAll() nimmt nun beliebig viele Elemente mit nur einem Aufruf an
    public void addAll(T... elements) {
        super.addAll(Arrays.stream(elements).toList());
    }
}