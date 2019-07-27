package org.telegram.abilitybots.api.db;

import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface represents the high-level methods exposed to the user when handling an {@link Update}.
 * Example usage:
 * <p><code>Ability.builder().action(ctx -> {db.getSet(USERS); doSomething();})</code></p>
 * {@link BaseAbilityBot} contains a handle on the <code>db</code> that the user can use inside his declared abilities.
 *
 * @author Abbas Abou Daya
 */
public interface DBContext extends Closeable {
  /**
   * @param name the unique name of the {@link List}
   * @param <T>  the type that the List holds
   * @return the List with the specified name
   */
  <T> List<T> getList(String name);

  /**
   * @param name the unique name of the {@link Map}
   * @param <K>  the type of the Map keys
   * @param <V>  the type of the Map values
   * @return the Map with the specified name
   */
  <K, V> Map<K, V> getMap(String name);

  /**
   * @param name the unique name of the {@link Set}
   * @param <T>  the type that the Set holds
   * @return the Set with the specified name
   */
  <T> Set<T> getSet(String name);

  /**
   * @param name the unique name of the {@link Var}
   * @param <T>  the type that the variable holds
   * @return the variable with the specified name
   */
  <T> Var<T> getVar(String name);

  /**
   * @return a high-level summary of the database structures (Sets, Lists, Maps, ...) present.
   */
  String summary();

  /**
   * Implementations of this method are free to return any object such as XML, JSON, etc...
   *
   * @return a backup of the DB
   */
  Object backup();

  /**
   * The object passed to this method need to conform to the implementation of the {@link DBContext#backup()} method.
   *
   * @param backup the backup of the database containing all the structures
   * @return <tt>true</tt> if the database successfully recovered
   */
  boolean recover(Object backup);

  /**
   * @param name the name of the data structure
   * @return the high-level information of the structure
   */
  String info(String name);

  /**
   * Commits the database to its persistent layer. Implementations are free to not implement this method as it is not compulsory.
   */
  void commit();

  /**
   * Clears the data structures present in the database.
   * <p>
   * This method does not delete the data-structure themselves, but leaves them empty.
   */
  void clear();

  /**
   * @param name the name of the data structure
   * @return <tt>true</tt> if this database contains the specified structure name
   */
  boolean contains(String name);
}