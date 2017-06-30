package org.telegram.abilitybots.api.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class Pair<A, B> {
  @JsonProperty("a")
  private final A a;
  @JsonProperty("b")
  private final B b;

  private Pair(A a, B b) {
    this.a = a;
    this.b = b;
  }

  @JsonCreator
  public static <A, B> Pair<A, B> of(@JsonProperty("a") A a, @JsonProperty("b") B b) {
    return new Pair<>(a, b);
  }

  public A a() {
    return a;
  }

  public B b() {
    return b;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Pair<?, ?> pair = (Pair<?, ?>) o;
    return Objects.equals(a, pair.a) &&
        Objects.equals(b, pair.b);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("a", a)
        .add("b", b)
        .toString();
  }
}
