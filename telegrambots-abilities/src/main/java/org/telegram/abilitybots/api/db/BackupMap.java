package org.telegram.abilitybots.api.db;

import org.telegram.abilitybots.api.util.Pair;

import java.util.*;

final class BackupMap<K, V> extends AbstractCollection<Pair<K, V>> implements Collection<Pair<K, V>> {
    private Collection<Pair<K, V>> entries = new HashSet<>();

    public BackupMap(){}

    public BackupMap(Map<K, V> map) {
        map.forEach((key, value) -> entries.add(Pair.of(key, value)));
    }

    public Map<K, V> toMap() {
        Map<K, V> map = new HashMap<>();
        entries.forEach(e -> map.put(e.a(), e.b()));
        return map;
    }

    @Override
    public Iterator<Pair<K, V>> iterator() {
        return entries.iterator();
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean add(Pair<K, V> kvPair) {
        return entries.add(kvPair);
    }
}
