package org.telegram.abilitybots.api.objects;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

/**
 * A reply consists of update conditionals and an action to be applied on the update.
 * <p>
 * If an update satisfies the {@link Reply#conditions} set by the reply, then it's safe to {@link Reply#actOn(Update)}.
 *
 * @author Abbas Abou Daya
 */
public class Reply {
  public final List<Predicate<Update>> conditions;
  public final BiConsumer<BaseAbilityBot, Update> action;
  private boolean statsEnabled;
  private String name;

  Reply(List<Predicate<Update>> conditions, BiConsumer<BaseAbilityBot, Update> action) {
    this.conditions = ImmutableList.<Predicate<Update>>builder()
            .addAll(conditions)
            .build();
    this.action = action;
    statsEnabled = false;
  }

  Reply(List<Predicate<Update>> conditions, BiConsumer<BaseAbilityBot, Update> action, String name) {
    this(conditions, action);
    if (Objects.nonNull(name)) {
      enableStats(name);
    }
  }

  /**
   * @deprecated Please use {@link #Reply(List, BiConsumer)}
   */
  @Deprecated
  Reply(List<Predicate<Update>> conditions, Consumer<Update> action) {
    this.conditions = ImmutableList.<Predicate<Update>>builder()
        .addAll(conditions)
        .build();
    this.action = ((baseAbilityBot, update) -> action.accept(update));
    statsEnabled = false;
  }

  /**
   * @deprecated Please use {@link #Reply(List, BiConsumer, String)}
   */
  @Deprecated
  Reply(List<Predicate<Update>> conditions, Consumer<Update> action, String name) {
    this(conditions, action);
    if (Objects.nonNull(name)) {
      enableStats(name);
    }
  }

  public static Reply of(BiConsumer<BaseAbilityBot, Update> action, List<Predicate<Update>> conditions) {
    return new Reply(conditions, action);
  }

  @SafeVarargs
  public static Reply of(BiConsumer<BaseAbilityBot, Update> action, Predicate<Update>... conditions) {
    return Reply.of(action, newArrayList(conditions));
  }

  /**
   * @deprecated Please use {@link #of(BiConsumer, List)}
   */
  @Deprecated
  public static Reply of(Consumer<Update> action, List<Predicate<Update>> conditions) {
    return new Reply(conditions, action);
  }

  /**
   * @deprecated Please use {@link #of(BiConsumer, Predicate[])}
   */
  @Deprecated
  @SafeVarargs
  public static Reply of(Consumer<Update> action, Predicate<Update>... conditions) {
    return Reply.of(action, newArrayList(conditions));
  }

  public boolean isOkFor(Update update) {
    // The following variable is required to avoid bug #JDK-8044546
    BiFunction<Boolean, Predicate<Update>, Boolean> stateAnd = (state, cond) -> state && cond.test(update);
    return conditions.stream().reduce(true, stateAnd, Boolean::logicalAnd);
  }

  public void actOn(BaseAbilityBot bot, Update update) {
    action.accept(bot, update);
  }

  public List<Predicate<Update>> conditions() {
    return conditions;
  }

  public BiConsumer<BaseAbilityBot, Update> action() {
    return action;
  }

  public Stream<Reply> stream(){
    return Stream.of(this);
  }

  public Reply enableStats(String name) {
    this.name = name;
    statsEnabled = true;
    return this;
  }

  public boolean statsEnabled() {
    return statsEnabled;
  }

  public String name() {
    return name;
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
