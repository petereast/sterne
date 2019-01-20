import java.util.NoSuchElementException;
import java.util.function.Function;

public class Option<T> {
    private T thing;
    private boolean isSome;

    public boolean present() {
        return this.isSome;
    }

    public static <O> Option<O> of(O thing) {
        if (thing == null) {
            return new Option(false, null);
        } else {
            return new Option(true, thing);
        }
    }

    public Option(boolean isSome, T thing) {
        this.isSome = isSome;
        this.thing = thing;
    }

    public <O> Option<O> map(Function<T, O> fn) {

        if (this.isSome) {
            return new Option(true, fn.apply(this.thing));
        } else {
            return new Option(false, null);
        }
    }

    public T get() throws NoSuchElementException {
        if (!this.isSome) {
            throw new NoSuchElementException();
        } else {
            return this.thing;
        }
    }

    public T getOrElse(T other) {
        if (!this.isSome){
            return other;
        } else {
            return this.thing;
        }
    }

    public T getOrNull() {
        return this.thing;
    }

    public <O> Option<O> replace(O other) {
        if (this.isSome) {
            return new Option(true, this.thing);
        } else {
            return Option.of(other);
        }
    }
}
