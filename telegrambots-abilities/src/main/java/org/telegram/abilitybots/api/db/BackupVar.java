package org.telegram.abilitybots.api.db;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class BackupVar<T> {
  @JsonProperty("var")
  private final T var;

  private BackupVar(T var) {
    this.var = var;
  }

  @JsonCreator
  public static <R> BackupVar<R> createVar(@JsonProperty("var") R var) {
    return new BackupVar<>(var);
  }

  public T var() {
    return var;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BackupVar<?> backupVar = (BackupVar<?>) o;
    return Objects.equals(var, backupVar.var);
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
