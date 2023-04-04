package main;

import java.util.ArrayList;
import java.util.function.Consumer;

// Liste die ConcurrentModificationException vermeidet
public class List<T> extends ArrayList<T> {

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
}