package org.telegram.abilitybots.api.db;

/**
 * The interface governing a variable for abstract getters and setters.
 * @param <T> the type of the variable
 *
 * @author Abbas Abou Daya
 */
public interface Var<T> {
  /**
   * @return the variable contained
   */
  T get();

  /**
   * @param var the new variable value
   */
  void set(T var);
}
