package org.telegram.abilitybots.api.objects;

import org.jetbrains.annotations.NotNull;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

public class ReplyFlow extends Reply {

  private final Set<Reply> nextReplies;

  private ReplyFlow(List<Predicate<Update>> conditions, Consumer<Update> action, Set<Reply> nextReplies) {
    super(conditions, action);
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
    private Consumer<Update> action;
    private Set<Reply> nextReplies;

    private ReplyFlowBuilder(DBContext db, int id) {
      conds = new ArrayList<>();
      nextReplies = new HashSet<>();
      this.db = db;
      this.id = id;
    }

    private ReplyFlowBuilder(DBContext db) {
      this(db, replyCounter.getAndIncrement());
    }

    public ReplyFlowBuilder action(Consumer<Update> action) {
      this.action = action;
      return this;
    }

    public ReplyFlowBuilder onlyIf(Predicate<Update> pred) {
      conds.add(pred);
      return this;
    }

    public ReplyFlowBuilder next(Reply nextReply) {
      List<Predicate<Update>> statefulConditions = toStateful(nextReply.conditions());
      Consumer<Update> statefulAction = nextReply.action().andThen(upd -> {
        Long chatId = AbilityUtils.getChatId(upd);
        db.<Long, Integer>getMap(STATES).remove(chatId);
      });

      Reply statefulReply = Reply.of(statefulAction, statefulConditions);
      nextReplies.add(statefulReply);
      return this;
    }

    public ReplyFlowBuilder next(ReplyFlow nextReplyFlow) {
      List<Predicate<Update>> statefulConditions = toStateful(nextReplyFlow.conditions());

      ReplyFlow statefulReplyFlow = new ReplyFlow(statefulConditions, nextReplyFlow.action(), nextReplyFlow.nextReplies());
      nextReplies.add(statefulReplyFlow);
      return this;
    }

    public ReplyFlow build() {
      if (action == null)
        action = upd -> {};
      Consumer<Update> statefulAction = action.andThen(upd -> {
        Long chatId = AbilityUtils.getChatId(upd);
        db.<Long, Integer>getMap(STATES).put(chatId, id);
      });

      return new ReplyFlow(conds, statefulAction, nextReplies);
    }

    @NotNull
    private List<Predicate<Update>> toStateful(List<Predicate<Update>> conditions) {
      List<Predicate<Update>> statefulConditions = newArrayList(conditions);
      statefulConditions.add(0, upd -> {
        Long chatId = AbilityUtils.getChatId(upd);
        int stateId = db.<Long, Integer>getMap(STATES).getOrDefault(chatId, -1);
        return id == stateId;
      });
      return statefulConditions;
    }
  }
}
