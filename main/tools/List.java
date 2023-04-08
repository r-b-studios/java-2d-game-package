package main.tools;

import main.functional.VoidFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

// Eine Subklasse der Klasse ArrayList, die eine ConcurrentModificationException vermeidet
public class List<T> extends ArrayList<T> {

    public List() {
        super();
    }

    public List(Collection<T> collection) {
        super(collection);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        var actions = new ArrayList<VoidFunction>();

        super.forEach(x -> {
            actions.add(() -> {
                action.accept(x);
            });
        });

        actions.forEach(VoidFunction::execute);
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