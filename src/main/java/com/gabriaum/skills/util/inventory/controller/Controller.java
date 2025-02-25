package com.gabriaum.skills.util.inventory.controller;

import java.util.HashMap;
import java.util.Map;

public class Controller<K, V> {

    private final Map<K, V> map = new HashMap<K, V>();

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public Map<K, V> getMap() {
        return map;
    }

    public void remove(K key) {
        map.remove(key);
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public boolean containsValue(V value) {
        return map.containsValue(value);
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void replace(K key, V value) {
        map.replace(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }
}
