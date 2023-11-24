package org.telegram.abilitybots.api.objects;

import org.jetbrains.annotations.NotNull;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

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

  ReplyFlow(List<Predicate<Update>> conditions, BiConsumer<BaseAbilityBot, Update> action, Set<Reply> nextReplies, String name) {
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


}
