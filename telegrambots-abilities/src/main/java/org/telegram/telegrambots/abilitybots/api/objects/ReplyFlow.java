package org.telegram.telegrambots.abilitybots.api.objects;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

public class ReplyFlow extends Reply {

  private final Set<Reply> nextReplies;

  private ReplyFlow(List<Predicate<Update>> conditions, BiConsumer<BaseAbilityBot, Update> action, Set<Reply> nextReplies, String name) {
    super(conditions, action, name);
    this.nextReplies = nextReplies;
  }

  public static ReplyFlowBuilder builder(DBContext db) {
    return new ReplyFlowBuilder(db);
  }

  public static ReplyFlowBuilder builder(DBContext db, int id) {
    return new ReplyFlowBuilder(db, id);
  }

  public Set<Reply> nextReplies() {
    return nextReplies;
  }

  @Override
  public Stream<Reply> stream() {
    return Stream.concat(Stream.of(this), nextReplies.stream().flatMap(Reply::stream));
  }

  public static class ReplyFlowBuilder {
    public static final String STATES = "user_state_replies";
    private static AtomicInteger replyCounter = new AtomicInteger();
    private final DBContext db;
    private final int id;
    private List<Predicate<Update>> conds;
    private BiConsumer<BaseAbilityBot, Update> action;
    private Set<Reply> nextReplies;
    private String name;
    private Predicate<Update> validator;
    private BiConsumer<BaseAbilityBot, Update> failureAction;

    private ReplyFlowBuilder(DBContext db, int id) {
      conds = new ArrayList<>();
      nextReplies = new HashSet<>();
      this.db = db;
      this.id = id;
    }

    private ReplyFlowBuilder(DBContext db) {
      this(db, replyCounter.getAndIncrement());
    }

    public ReplyFlowBuilder action(BiConsumer<BaseAbilityBot, Update> action) {
      this.action = action;
      return this;
    }

    /**
     * This is validation step of the flow
     * <p>
     * Executes by the folloiwng steps {@code onlyIf} -> {@code validate} -> {@code action}
     * </p>
     * <p>
     * Behavior:
     * <ul>
     * <li>If {@code validator} returns {@code true}:  flow proceeds normally (executes main action, updates state).</li>
     * <li>If {@code validator} returns {@code false}: The {@code failureAction} is executed, the main action is skipped,
     * and the user's state remains unchanged (it is <b>NOT</b> updated to the new state ID).</li>
     * </ul>
     * </p>
     *
     * @param validator     a predicate to check the update (like verifying a password).
     * @param failureAction the callback to execute if validation fails (like sending an error message to the user).
     * @return current {@link ReplyFlowBuilder} instance for chaining
     */
    public ReplyFlowBuilder validate(Predicate<Update> validator, BiConsumer<BaseAbilityBot, Update> failureAction) {
      this.validator = validator;
      this.failureAction = failureAction;
      return this;
    }

    public ReplyFlowBuilder enableStats(String name) {
      this.name = name;
      return this;
    }

    public ReplyFlowBuilder onlyIf(Predicate<Update> pred) {
      conds.add(pred);
      return this;
    }

    public ReplyFlowBuilder next(Reply nextReply) {
      List<Predicate<Update>> statefulConditions = toStateful(nextReply.conditions());
      BiConsumer<BaseAbilityBot, Update> statefulAction = nextReply.action().andThen((unused, upd) -> {
        Serializable stateKey = AbilityUtils.getReplyFlowKey(upd);
        if (stateKey != null) {
          db.<Serializable, Integer>getMap(STATES).remove(stateKey);
        }
      });

      Reply statefulReply = new Reply(statefulConditions, statefulAction, nextReply.name());
      nextReplies.add(statefulReply);
      return this;
    }

    public ReplyFlowBuilder next(ReplyFlow nextReplyFlow) {
      List<Predicate<Update>> statefulConditions = toStateful(nextReplyFlow.conditions());

      ReplyFlow statefulReplyFlow = new ReplyFlow(statefulConditions, nextReplyFlow.action(), nextReplyFlow.nextReplies(), nextReplyFlow.name());
      nextReplies.add(statefulReplyFlow);
      return this;
    }

    public ReplyFlow build() {
      if (action == null)
        action = (bot, upd) -> {};

      BiConsumer<BaseAbilityBot, Update> stateTransition;
      if (nextReplies.size() > 0) {
         stateTransition = (bot, upd) -> {
          Serializable stateKey = AbilityUtils.getReplyFlowKey(upd);
          if (stateKey != null) {
            db.<Serializable, Integer>getMap(STATES).put(stateKey, id);
          }
        };
      } else {
        stateTransition = (bot, upd) -> {
          Serializable stateKey = AbilityUtils.getReplyFlowKey(upd);
          if (stateKey != null) {
            db.<Serializable, Integer>getMap(STATES).remove(stateKey);
          }
        };
      }

      BiConsumer<BaseAbilityBot, Update> statefulAction = (bot, upd) -> {
        if (nextReplies.size() > 0 && AbilityUtils.getReplyFlowKey(upd) == null) {
          return;
        }
        if (validator == null || validator.test(upd)) {
          action.accept(bot, upd);
          stateTransition.accept(bot, upd);
        } else {
          if (failureAction != null) {
            failureAction.accept(bot, upd);
          }
        }
      };

      return new ReplyFlow(conds, statefulAction, nextReplies, name);
    }

    @NotNull
    private List<Predicate<Update>> toStateful(List<Predicate<Update>> conditions) {
      List<Predicate<Update>> statefulConditions = newArrayList(conditions);
      statefulConditions.add(0, upd -> {
        Serializable stateKey = AbilityUtils.getReplyFlowKey(upd);
        if (stateKey == null) {
          return false;
        }
        int stateId = db.<Serializable, Integer>getMap(STATES).getOrDefault(stateKey, -1);
        return id == stateId;
      });
      return statefulConditions;
    }
  }
}
