package org.telegram.abilitybots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Basic POJO to track ability and reply hits. The current implementation is NOT thread safe.
 *
 * @author Abbas Abou Daya
 */
public final class Stats implements Serializable {
  @JsonProperty
  private final String name;
  @JsonProperty
  private long hits;

  private Stats(String name) {
    this.name = name;
  }

  @JsonCreator
  public static Stats createStats(@JsonProperty("name") String name, @JsonProperty("hits") long hits) {
    return new Stats(name);
  }

  public String name() {
    return name;
  }

  public long hits() {
    return hits;
  }

  public void hit() {
    hits++;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Stats that = (Stats) o;
    return hits == that.hits &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, hits);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .add("hits", hits)
        .toString();
  }
}
