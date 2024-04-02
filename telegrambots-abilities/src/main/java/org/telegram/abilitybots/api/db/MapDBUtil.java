package org.telegram.abilitybots.api.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.util.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;
import static org.mapdb.Serializer.JAVA;

public class MapDBUtil {
    private static final Logger log = LoggerFactory.getLogger(MapDBUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> localCopy(DB db) {
        return db.getAll().entrySet().stream().map(entry -> {
            Object struct = entry.getValue();
            if (struct instanceof Set)
                return Pair.of(entry.getKey(), newHashSet((Set) struct));
            else if (struct instanceof List)
                return Pair.of(entry.getKey(), newArrayList((List) struct));
            else if (struct instanceof Map)
                return Pair.of(entry.getKey(), new BackupMap((Map) struct));
            else if (struct instanceof Atomic.Var)
                return Pair.of(entry.getKey(), BackupVar.createVar(((Atomic.Var) struct).get()));
            return Pair.of(entry.getKey(), struct);
        }).collect(toMap(pair -> (String) pair.a(), Pair::b));
    }

    public static void doRecover(DB db, Map<String, Object> backupData) {
        db.getAllNames().forEach(name -> {
            Object struct = db.get(name);
            if (struct instanceof Collection)
                ((Collection) struct).clear();
            else if (struct instanceof Map)
                ((Map) struct).clear();
        });

        backupData.forEach((name, value) -> {
            if (value instanceof Set) {
                Set entrySet = (Set) value;
                ((Set) db.get(name)).addAll(entrySet);
            } else if (value instanceof BackupMap) {
                Map<Object, Object> entryMap = ((BackupMap) value).toMap();
                ((Map) db.get(name)).putAll(entryMap);
            } else if (value instanceof List) {
                List entryList = (List) value;
                ((List) db.get(name)).addAll(entryList);
            } else if (value instanceof BackupVar) {
                ((Atomic.Var) db.get(name)).set(((BackupVar) value).var());
            } else {
                log.error(format("Unable to identify object type during DB recovery, entry name: %s", name));
            }
        });
        db.commit();
    }

    public static String writeAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.info(format("Failed to read the JSON representation of object: %s", obj), e);
            return "Error reading required data...";
        }
    }

    public static Object readBackup(DB db, String backup) throws IOException {
        Map<String, Object> snapshot = localCopy(db);
        Map<String, Object> backupData = objectMapper.readValue(backup, new TypeReference<HashMap<String, Object>>() {});
        doRecover(db, backupData);
        return snapshot;
    }

    public static String summary(DB db) {
        return StreamSupport.stream(db.getAllNames().spliterator(), false)
                .map(name -> {
                    Object struct = db.get(name);
                    if (isNull(struct))
                        throw new IllegalStateException(format("DB structure with name [%s] does not exist", name));

                    if (struct instanceof Set)
                        return format("%s - Set - %d", name, ((Set) struct).size());
                    else if (struct instanceof List)
                        return format("%s - List - %d", name, ((List) struct).size());
                    else if (struct instanceof Map)
                        return format("%s - Map - %d", name, ((Map) struct).size());
                    else
                        return format("%s - %s", name, struct.getClass().getSimpleName());
                })
                .reduce(new StringJoiner("\n"), StringJoiner::add, StringJoiner::merge)
                .toString();
    }
}
