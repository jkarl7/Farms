package farm.application;

@FunctionalInterface
public interface Usecase<I, O> {

    O execute(I input);
}
