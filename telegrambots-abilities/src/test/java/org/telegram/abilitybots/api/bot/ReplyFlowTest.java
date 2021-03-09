package org.telegram.abilitybots.api.bot;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.io.IOException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.telegram.abilitybots.api.bot.TestUtils.USER;
import static org.telegram.abilitybots.api.bot.TestUtils.mockFullUpdate;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;
import static org.telegram.abilitybots.api.objects.ReplyFlow.ReplyFlowBuilder.STATES;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class ReplyFlowTest {
  private static final int INITIAL_STATE = 1;
  private static final int INTERIM_STATE = 2;
  private DBContext db;
  private ReplyFlowBot bot;

  private MessageSender sender;
  private SilentSender silent;

  @BeforeEach
  void setUp() {
    db = offlineInstance("db");
    bot = new ReplyFlowBot(EMPTY, EMPTY, db);
    bot.onRegister();

    sender = mock(MessageSender.class);
    silent = mock(SilentSender.class);

    bot.sender = sender;
    bot.silent = silent;
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  void doesNotReplyIfFirstReplyFlowDoesNotMatch() {
    Update update = mockFullUpdate(bot, USER, "this is not supported");
    long chatId = getChatId(update);

    assertTrue(bot.filterReply(update));

    verify(silent, never()).send("Command me to go left or right!", chatId);
  }

  @Test
  void doesNotReplyIfLaterRepliesAreAttemptedButUserNotInRightState() {
    Update update = mockFullUpdate(bot, USER, "left");
    long chatId = getChatId(update);
    db.<Long, Integer>getMap(STATES).put(chatId, INTERIM_STATE);

    assertTrue(bot.filterReply(update));

    verify(silent, never()).send("Sir, I have gone left.", chatId);
  }

  @Test
  void repliesIfFirstReplyFlowMatches() {
    Update update = mockFullUpdate(bot, USER, "wake up");
    long chatId = getChatId(update);

    assertFalse(bot.filterReply(update));

    verify(silent, only()).send("Command me to go left or right!", chatId);
    assertEquals(INITIAL_STATE, db.<Long, Integer>getMap(STATES).get(chatId), "User is not in the proper initial state");
  }

  @Test
  void stateIsNotResetOnFaultyReply() {
    Update update = mockFullUpdate(bot, USER, "leffffft");
    long chatId = getChatId(update);
    db.<Long, Integer>getMap(STATES).put(chatId, INITIAL_STATE);

    assertTrue(bot.filterReply(update));

    verify(silent, never()).send("I don't know how to go left.", chatId);
    assertEquals(INITIAL_STATE, db.<Long, Integer>getMap(STATES).get(chatId), "User is no longer in the initial state after faulty reply");
  }

  @Test
  void terminalRepliesResetState() {
    Update update = mockFullUpdate(bot, USER, "go left or else");
    long chatId = getChatId(update);
    db.<Long, Integer>getMap(STATES).put(chatId, INTERIM_STATE);

    assertFalse(bot.filterReply(update));

    verify(silent, only()).send("Sir, I have gone left.", chatId);
    assertFalse(db.<Long, Integer>getMap(STATES).containsKey(chatId), "User still has state after terminal reply");
  }

  @Test
  void repliesHandlePollResponse() {
    Update update = mock(Update.class);
    when(update.hasPoll()).thenReturn(true);
    when(update.hasMessage()).thenReturn(false);

    Poll poll = mock(Poll.class);
    when(poll.getId()).thenReturn("1");
    when(update.getPoll()).thenReturn(poll);

    // This should not be processed as a reply, so we wouldn't filter out (true)
    assertTrue(bot.filterReply(update));
  }

  @Test
  void replyFlowsAreWorkingWhenDefinedInAbilities() {
    Update update1 = mockFullUpdate(bot, USER, "one");
    Update update2 = mockFullUpdate(bot, USER, "two");
    long chatId = getChatId(update1);

    // Trigger and verify first reply stage
    assertFalse(bot.filterReply(update1));

    verify(silent, only()).send("First reply", chatId);
    assertTrue(db.<Long, Integer>getMap(STATES).containsKey(chatId), "User is not in initial state");
    // Resetting the mock now helps with verification later
    reset(silent);

    // Trigger and verify second reply stage
    assertFalse(bot.filterReply(update2));

    verify(silent, only()).send("Second reply", chatId);
    assertFalse(db.<Long, Integer>getMap(STATES).containsKey(chatId), "User is still in a state");
  }

  @Test
  void replyFlowsPertainNames() {
    Set<String> replyNames = bot.replies().stream().map(Reply::name).collect(Collectors.toSet());
    assertTrue(replyNames.containsAll(newHashSet("FIRST", "SECOND")));
  }

  public static class ReplyFlowBot extends AbilityBot {

    private ReplyFlowBot(String botToken, String botUsername, DBContext db) {
      super(botToken, botUsername, db);
    }

    @Override
    public long creatorId() {
      return 0;
    }

    public ReplyFlow directionFlow() {
      Reply saidLeft = Reply.of((bot, upd) -> silent.send("Sir, I have gone left.", getChatId(upd)),
          hasMessageWith("go left or else"));

      ReplyFlow leftflow = ReplyFlow.builder(db, 2)
          .action((bot, upd) -> silent.send("I don't know how to go left.", getChatId(upd)))
          .onlyIf(hasMessageWith("left"))
          .next(saidLeft).build();

      Reply saidRight = Reply.of((bot, upd) -> silent.send("Sir, I have gone right.", getChatId(upd)),
          hasMessageWith("right"));

      return ReplyFlow.builder(db, 1)
          .action((bot, upd) -> silent.send("Command me to go left or right!", getChatId(upd)))
          .onlyIf(hasMessageWith("wake up"))
          .next(leftflow)
          .next(saidRight)
          .build();
    }

    public Reply errantReply() {
      return Reply.of(
          (bot, upd) -> {
            throw new RuntimeException("Throwing an exception inside the update consumer");
          },
          (upd) -> {
            throw new RuntimeException("Throwing an exception inside the reply conditions (flags)");
          });
    }

    public Ability replyFlowsWithAbility() {
      Reply replyWithVk = ReplyFlow.builder(db, 2)
          .enableStats("SECOND")
          .action((bot, upd) -> {
            silent.send("Second reply", upd.getMessage().getChatId());
          })
          .onlyIf(hasMessageWith("two"))
          .build();

      Reply replyWithNickname = ReplyFlow.builder(db, 1)
          .enableStats("FIRST")
          .action((bot, upd) -> {
            silent.send("First reply", upd.getMessage().getChatId());
          })
          .onlyIf(hasMessageWith("one"))
          .next(replyWithVk)
          .build();

      return Ability.builder()
          .name("trigger")
          .privacy(Privacy.PUBLIC)
          .locality(Locality.ALL)
          .action(ctx -> silent.send("I'm in an ability", ctx.chatId()))
          .reply(replyWithNickname)
          .build();
    }

    @NotNull
    private Predicate<Update> hasMessageWith(String msg) {
      return upd -> Flag.MESSAGE.test(upd) && upd.getMessage().getText().equalsIgnoreCase(msg);
    }
  }
}
