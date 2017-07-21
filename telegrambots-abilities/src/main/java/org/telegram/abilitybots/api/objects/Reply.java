package org.telegram.abilitybots.api.objects;

import com.google.common.base.MoreObjects;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.asList;

/**
 * A reply consists of update conditionals and an action to be applied on the update.
 * <p>
 * If an update satisfies the {@link Reply#conditions}set by the reply, then it's safe to {@link Reply#actOn(Update)}.
 *
 * @author Abbas Abou Daya
 */
public final class Reply {
  public final List<Predicate<Update>> conditions;
  public final Consumer<Update> action;

  private Reply(List<Predicate<Update>> conditions, Consumer<Update> action) {
    this.conditions = conditions;
    this.action = action;
  }

  @SafeVarargs
  public static Reply of(Consumer<Update> action, Predicate<Update>... conditions) {
    return new Reply(asList(conditions), action);
  }

  public boolean isOkFor(Update update) {
    // The following variable is required to avoid bug #JDK-8044546
    BiFunction<Boolean, Predicate<Update>, Boolean> stateAnd = (state, cond) -> state && cond.test(update);
    return conditions.stream().reduce(true, stateAnd, Boolean::logicalAnd);
  }

  public void actOn(Update update) {
    action.accept(update);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Reply reply = (Reply) o;
    return Objects.equals(conditions, reply.conditions) &&
        Objects.equals(action, reply.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conditions, action);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("conditions", conditions)
        .add("action", action)
        .toString();
  }
}
