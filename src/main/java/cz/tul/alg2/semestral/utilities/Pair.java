package cz.tul.alg2.semestral.utilities;

public class Pair<K,T> {
    public K first;
    public T second;
    /**
     * The Pair function is a constructor that creates a new Pair object.
     *
     * @param first Set the key of a pair
     * @param second Store the second value in a pair
     */
    public Pair(K first, T second) {
        this.first = first;
        this.second = second;
    }
}
