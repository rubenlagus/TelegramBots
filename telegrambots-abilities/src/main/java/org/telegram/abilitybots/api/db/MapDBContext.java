package org.telegram.abilitybots.api.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.util.Pair;

import java.io.IOException;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.StreamSupport.stream;
import static org.mapdb.Serializer.JAVA;

/**
 * An implementation of {@link DBContext} that relies on a {@link DB}.
 *
 * @author Abbas Abou Daya
 * @see <a href="https://github.com/jankotek/mapdb">MapDB project</a>
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class MapDBContext implements DBContext {
  private static final Logger log = LoggerFactory.getLogger(MapDBContext.class);

  private final DB db;
  private final ObjectMapper objectMapper;

  public MapDBContext(DB db) {
    this.db = db;

    objectMapper = new ObjectMapper();
    objectMapper.enableDefaultTyping();
  }

  /**
   * This DB returned by this method does not trigger deletion on JVM shutdown.
   *
   * @param name name of the DB file
   * @return an online instance of {@link MapDBContext}
   */
  public static DBContext onlineInstance(String name) {
    DB db = DBMaker
        .fileDB(name)
        .fileMmapEnableIfSupported()
        .closeOnJvmShutdown()
        .transactionEnable()
        .make();

    return new MapDBContext(db);
  }

  /**
   * This DB returned by this method gets deleted on JVM shutdown.
   *
   * @param name name of the DB file
   * @return an offline instance of {@link MapDBContext}
   */
  public static DBContext offlineInstance(String name) {
    DB db = DBMaker
        .fileDB(name)
        .fileMmapEnableIfSupported()
        .closeOnJvmShutdown()
        .transactionEnable()
        .fileDeleteAfterClose()
        .make();

    return new MapDBContext(db);
  }

  @Override
  public <T> List<T> getList(String name) {
    return (List<T>) db.<T>indexTreeList(name, Serializer.JAVA).createOrOpen();
  }

  @Override
  public <K, V> Map<K, V> getMap(String name) {
    return db.<K, V>hashMap(name, JAVA, JAVA).createOrOpen();
  }

  @Override
  public <T> Set<T> getSet(String name) {
    return (Set<T>) db.<T>hashSet(name, JAVA).createOrOpen();
  }

  @Override
  public <T> Var<T> getVar(String name) {
    return new MapDBVar<>((Atomic.Var<T>) db.atomicVar(name).createOrOpen());
  }

  @Override
  public String summary() {
    return stream(db.getAllNames().spliterator(), false)
        .map(this::info)
        .reduce(new StringJoiner("\n"), StringJoiner::add, StringJoiner::merge)
        .toString();
  }

  @Override
  public Object backup() {
    Map<String, Object> collectedMap = localCopy();
    return writeAsString(collectedMap);
  }

  @Override
  public boolean recover(Object backup) {
    Map<String, Object> snapshot = localCopy();

    try {
      Map<String, Object> backupData = objectMapper.readValue(backup.toString(), new TypeReference<HashMap<String, Object>>() {
      });
      doRecover(backupData);
      return true;
    } catch (IOException e) {
      log.error(format("Could not recover DB data from file with String representation %s", backup), e);
      // Attempt to fallback to data snapshot before recovery
      doRecover(snapshot);
      return false;
    }
  }

  @Override
  public String info(String name) {
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
  }

  @Override
  public void commit() {
    db.commit();
  }

  @Override
  public void clear() {
    db.getAllNames().forEach(name -> {
      Object struct = db.get(name);
      if (struct instanceof Collection)
        ((Collection) struct).clear();
      else if (struct instanceof Map)
        ((Map) struct).clear();
    });
    commit();
  }

  @Override
  public boolean contains(String name) {
    return db.exists(name);
  }

  @Override
  public void close() {
    db.close();
  }

  /**
   * @return a local non-thread safe copy of the database
   */
  private Map<String, Object> localCopy() {
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

  private void doRecover(Map<String, Object> backupData) {
    clear();
    backupData.forEach((name, value) -> {
      if (value instanceof Set) {
        Set entrySet = (Set) value;
        getSet(name).addAll(entrySet);
      } else if (value instanceof BackupMap) {
        Map<Object, Object> entryMap = ((BackupMap) value).toMap();
        getMap(name).putAll(entryMap);
      } else if (value instanceof List) {
        List entryList = (List) value;
        getList(name).addAll(entryList);
      } else if (value instanceof BackupVar) {
        getVar(name).set(((BackupVar) value).var());
      } else {
        log.error(format("Unable to identify object type during DB recovery, entry name: %s", name));
      }
    });
    commit();
  }

  private String writeAsString(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.info(format("Failed to read the JSON representation of object: %s", obj), e);
      return "Error reading required data...";
    }
  }
}
