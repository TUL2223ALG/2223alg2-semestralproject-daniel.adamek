package cz.tul.alg2.semestral.Utilities;

public class GenericNode<T> {
    protected T value;

    protected GenericNode(T nodeValue) {
        value = nodeValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
