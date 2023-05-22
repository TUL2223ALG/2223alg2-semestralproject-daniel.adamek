package cz.tul.alg2.semestral.utilities;

public class Pair<K extends Comparable<K>, T extends Comparable<T>> implements Comparable<Pair<K, T>>{
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

    /**
     * @param pair 
     * @return
     */
    @Override
    public int compareTo(Pair<K, T> pair) {
        int cmp = this.first.compareTo(pair.first);
        if (cmp == 0) cmp = this.second.compareTo(pair.second);
        return cmp;
    }
}
