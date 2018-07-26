package org.telegram.abilitybots.api.db;

import com.google.common.base.MoreObjects;
import org.mapdb.Atomic;

import java.util.Objects;

/**
 * The MapDB variant for {@link DBContext#getVar(String)}.
 *
 * @param <T> the type of the inner variable
 */
public final class MapDBVar<T> implements Var<T> {
  private Atomic.Var<T> var;

  public MapDBVar(Atomic.Var<T> var) {
    this.var = var;
  }

  @Override
  public T get() {
    return var.get();
  }

  @Override
  public void set(T var) {
    this.var.set(var);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MapDBVar<?> mapDBVar = (MapDBVar<?>) o;
    return Objects.equals(var, mapDBVar.var);
  }

  @Override
  public int hashCode() {
    return Objects.hash(var);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("var", var)
        .toString();
  }
}
