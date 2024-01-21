package org.telegram.telegrambots.abilitybots.api.bot;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.abilitybots.api.db.MapDBContext;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class TestReplyFlow {
  private static final int INITIAL_STATE = 1;
  private static final int INTERIM_STATE = 2;
  private DBContext db;
  private ReplyFlowBot bot;

  private TelegramClient telegramClient;
  private SilentSender silent;

  @BeforeEach
  void setUp() {

    telegramClient = mock(TelegramClient.class);
    silent = mock(SilentSender.class);
    db = MapDBContext.offlineInstance("db");
    bot = new ReplyFlowBot(telegramClient, EMPTY, db);
    bot.onRegister();

    bot.silent = silent;
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  void doesNotReplyIfFirstReplyFlowDoesNotMatch() {
    Update update = TestUtils.mockFullUpdate(bot, TestUtils.USER, "this is not supported");
    long chatId = getChatId(update);

    assertTrue(bot.filterReply(update));

    verify(silent, never()).send("Command me to go left or right!", chatId);
  }

  @Test
  void doesNotReplyIfLaterRepliesAreAttemptedButUserNotInRightState() {
    Update update = TestUtils.mockFullUpdate(bot, TestUtils.USER, "left");
    long chatId = getChatId(update);
    db.<Long, Integer>getMap(ReplyFlow.ReplyFlowBuilder.STATES).put(chatId, INTERIM_STATE);

    assertTrue(bot.filterReply(update));

    verify(silent, never()).send("Sir, I have gone left.", chatId);
  }

  @Test
  void repliesIfFirstReplyFlowMatches() {
    Update update = TestUtils.mockFullUpdate(bot, TestUtils.USER, "wake up");
    long chatId = getChatId(update);

    assertFalse(bot.filterReply(update));

    verify(silent, only()).send("Command me to go left or right!", chatId);
    assertEquals(INITIAL_STATE, db.<Long, Integer>getMap(ReplyFlow.ReplyFlowBuilder.STATES).get(chatId), "User is not in the proper initial state");
  }

  @Test
  void stateIsNotResetOnFaultyReply() {
    Update update = TestUtils.mockFullUpdate(bot, TestUtils.USER, "leffffft");
    long chatId = getChatId(update);
    db.<Long, Integer>getMap(ReplyFlow.ReplyFlowBuilder.STATES).put(chatId, INITIAL_STATE);

    assertTrue(bot.filterReply(update));

    verify(silent, never()).send("I don't know how to go left.", chatId);
    assertEquals(INITIAL_STATE, db.<Long, Integer>getMap(ReplyFlow.ReplyFlowBuilder.STATES).get(chatId), "User is no longer in the initial state after faulty reply");
  }

  @Test
  void terminalRepliesResetState() {
    Update update = TestUtils.mockFullUpdate(bot, TestUtils.USER, "go left or else");
    long chatId = getChatId(update);
    db.<Long, Integer>getMap(ReplyFlow.ReplyFlowBuilder.STATES).put(chatId, INTERIM_STATE);

    assertFalse(bot.filterReply(update));

    verify(silent, only()).send("Sir, I have gone left.", chatId);
    assertFalse(db.<Long, Integer>getMap(ReplyFlow.ReplyFlowBuilder.STATES).containsKey(chatId), "User still has state after terminal reply");
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
    Update update1 = TestUtils.mockFullUpdate(bot, TestUtils.USER, "one");
    Update update2 = TestUtils.mockFullUpdate(bot, TestUtils.USER, "two");
    long chatId = getChatId(update1);

    // Trigger and verify first reply stage
    assertFalse(bot.filterReply(update1));

    verify(silent, only()).send("First reply", chatId);
    assertTrue(db.<Long, Integer>getMap(ReplyFlow.ReplyFlowBuilder.STATES).containsKey(chatId), "User is not in initial state");
    // Resetting the mock now helps with verification later
    reset(silent);

    // Trigger and verify second reply stage
    assertFalse(bot.filterReply(update2));

    verify(silent, only()).send("Second reply", chatId);
    assertFalse(db.<Long, Integer>getMap(ReplyFlow.ReplyFlowBuilder.STATES).containsKey(chatId), "User is still in a state");
  }

  @Test
  void replyFlowsPertainNames() {
    Set<String> replyNames = bot.getReplies().stream().map(Reply::name).collect(Collectors.toSet());
    assertTrue(replyNames.containsAll(newHashSet("FIRST", "SECOND")));
  }

  public static class ReplyFlowBot extends AbilityBot {

    private ReplyFlowBot(TelegramClient telegramClient, String botUsername, DBContext db) {
      super(telegramClient, botUsername, db);
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
