package me.dan.pluginapi.manager;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Manager<K, V> {

    private final Map<K, V> map;

    public Manager() {
        this.map = new HashMap<>();
    }

    public void insert(K key, V value) {
        map.put(keyConvert(key), value);
    }

    public void remove(K key) {
        map.remove(keyConvert(key));
    }

    private K keyConvert(K key) {
        if (key instanceof String) {
            return (K) ((String) key).toLowerCase();
        }
        return key;
    }

    public V get(K key) {
        return map.get(keyConvert(key));
    }

    public Collection<V> getAll() {
        return map.values();
    }


    /**
     * @return Next Available ID IF TYPE IS INTEGER
     */
     public int getNextFreeId() {
         if (map.isEmpty()) {
            return 1;
        }

        for (int i = 1; i <= map.size(); i++) {
            if (map.containsKey(i)) {
                continue;
            }

            return i;
        }

        return map.size() + 1;
    }

}
