package me.wilhelm.api.algorithms;

public class HashMapHelper {

    public static <K,V> int countValues(java.util.HashMap<K,V> map, V value) {
        int count = 0;
        for (K key : map.keySet()) {
            if (map.get(key).equals(value))
                count++;
        }
        return count;
    }

}
