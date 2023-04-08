package main.functional;

@FunctionalInterface
public interface ResultFunction<R> {
    R execute();
}