package org.telegram.abilitybots.api.objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.util.Objects.hash;
import static java.util.Optional.ofNullable;
import static org.telegram.abilitybots.api.util.AbilityUtils.isValidCommandName;

/**
 * An ability is a fully-fledged bot action that contains all the necessary information to process:
 * <ol>
 * <li>A response to a command</li>
 * <li>A post-response to a command</li>
 * <li>A reply to a sequence of actions</li>
 * </ol>
 * <p>
 * In-order to instantiate an ability, you can call {@link Ability#builder()} to get the {@link AbilityBuilder}.
 * Once you're done setting your ability, you'll call {@link AbilityBuilder#build()} to get your constructed ability.
 * <p>
 * The only optional fields in an ability are {@link Ability#info}, {@link Ability#postAction}, {@link Ability#flags} and {@link Ability#replies}.
 *
 * @author Abbas Abou Daya
 */
public final class Ability {
  private static final Logger log = LoggerFactory.getLogger(Ability.class);

  private final String name;
  private final String info;
  private final Locality locality;
  private final Privacy privacy;
  private final int argNum;
  private final boolean statsEnabled;
  private final Consumer<MessageContext> action;
  private final Consumer<MessageContext> postAction;
  private final List<Reply> replies;
  private final List<Predicate<Update>> flags;

  @SafeVarargs
  private Ability(String name, String info, Locality locality, Privacy privacy, int argNum, boolean statsEnabled, Consumer<MessageContext> action, Consumer<MessageContext> postAction, List<Reply> replies, Predicate<Update>... flags) {
    checkArgument(isValidCommandName(name), "Method name can only contain alpha-numeric characters and underscores," +
            " cannot be longer than 31 characters, empty or null", name);
    this.name = name;
    this.info = info;

    this.locality = checkNotNull(locality, "Please specify a valid locality setting. Use the Locality enum class");
    this.privacy = checkNotNull(privacy, "Please specify a valid privacy setting. Use the Privacy enum class");

    checkArgument(argNum >= 0, "The number of arguments the method can handle CANNOT be negative. " +
        "Use the number 0 if the method ignores the arguments OR uses as many as appended");
    this.argNum = argNum;

    this.action = checkNotNull(action, "Method action can't be empty. Please assign a function by using .action() method");
    if (postAction == null)
      log.info(format("No post action was detected for method with name [%s]", name));

    this.flags = ofNullable(flags).map(Arrays::asList).orElse(newArrayList());

    this.postAction = postAction;
    this.replies = replies;
    this.statsEnabled = statsEnabled;
  }

  public static AbilityBuilder builder() {
    return new AbilityBuilder();
  }

  public String name() {
    return name;
  }

  public String info() {
    return info;
  }

  public Locality locality() {
    return locality;
  }

  public Privacy privacy() {
    return privacy;
  }

  public int tokens() {
    return argNum;
  }

  public boolean statsEnabled() {
    return statsEnabled;
  }

  public Consumer<MessageContext> action() {
    return action;
  }

  public Consumer<MessageContext> postAction() {
    return postAction;
  }

  public List<Reply> replies() {
    return replies;
  }

  public List<Predicate<Update>> flags() {
    return flags;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .add("locality", locality)
        .add("privacy", privacy)
        .add("argNum", argNum)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Ability ability = (Ability) o;
    return argNum == ability.argNum &&
        Objects.equal(name, ability.name) &&
        locality == ability.locality &&
        privacy == ability.privacy;
  }

  @Override
  public int hashCode() {
    return hash(name, info, locality, privacy, argNum, action, postAction, replies, flags);
  }

  public static class AbilityBuilder {
    private String name;
    private String info;
    private Privacy privacy;
    private Locality locality;
    private int argNum;
    private boolean statsEnabled;
    private Consumer<MessageContext> action;
    private Consumer<MessageContext> postAction;
    private List<Reply> replies;
    private Predicate<Update>[] flags;

    private AbilityBuilder() {
      statsEnabled = false;
      replies = newArrayList();
    }

    public AbilityBuilder action(Consumer<MessageContext> consumer) {
      this.action = consumer;
      return this;
    }

    public AbilityBuilder name(String name) {
      this.name = name;
      return this;
    }

    public AbilityBuilder info(String info) {
      this.info = info;
      return this;
    }

    public AbilityBuilder flag(Predicate<Update>... flags) {
      this.flags = flags;
      return this;
    }

    public AbilityBuilder locality(Locality type) {
      this.locality = type;
      return this;
    }

    public AbilityBuilder input(int argNum) {
      this.argNum = argNum;
      return this;
    }

    public AbilityBuilder enableStats() {
      statsEnabled = true;
      return this;
    }

    public AbilityBuilder privacy(Privacy privacy) {
      this.privacy = privacy;
      return this;
    }

    public AbilityBuilder post(Consumer<MessageContext> postAction) {
      this.postAction = postAction;
      return this;
    }

    /**
     * @deprecated Please use {@link #reply(BiConsumer, Predicate[])}
     */
    @Deprecated
    @SafeVarargs
    public final AbilityBuilder reply(Consumer<Update> action, Predicate<Update>... conditions) {
      replies.add(Reply.of(action, conditions));
      return this;
    }

    @SafeVarargs
    public final AbilityBuilder reply(BiConsumer<BaseAbilityBot, Update> action, Predicate<Update>... conditions) {
      replies.add(Reply.of(action, conditions));
      return this;
    }

    public final AbilityBuilder reply(Reply reply) {
      replies.add(reply);
      return this;
    }

    public AbilityBuilder basedOn(Ability ability) {
      replies.clear();
      replies.addAll(ability.replies());

      return name(ability.name())
          .info(ability.info())
          .input(ability.tokens())
          .locality(ability.locality())
          .privacy(ability.privacy())
          .action(ability.action())
          .post(ability.postAction());
    }

    public Ability build() {
      return new Ability(name, info, locality, privacy, argNum, statsEnabled, action, postAction, replies, flags);
    }
  }
}
