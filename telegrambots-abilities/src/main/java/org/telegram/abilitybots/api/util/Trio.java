package org.telegram.abilitybots.api.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Trio<A, B, C> {
  @JsonProperty("a")
  private final A a;
  @JsonProperty("b")
  private final B b;
  @JsonProperty("c")
  private final C c;

  private Trio(A a, B b, C c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  @JsonCreator
  public static <A, B, C> Trio<A, B, C> of(@JsonProperty("a") A a, @JsonProperty("b") B b, @JsonProperty("c") C c) {
    return new Trio<>(a, b, c);
  }

  public A a() {
    return a;
  }

  public B b() {
    return b;
  }

  public C c() {
    return c;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Trio<?, ?, ?> trio = (Trio<?, ?, ?>) o;
    return Objects.equals(a, trio.a) &&
        Objects.equals(b, trio.b) &&
        Objects.equals(c, trio.c);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b, c);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("a", a)
        .add("b", b)
        .add("c", c)
        .toString();
  }
}
