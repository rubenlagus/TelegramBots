package org.telegram.abilitybots.api.bot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class ReplyFlowPollTest {
  private DBContext db;
  private ReplyFlowBot bot;

  private MessageSender sender;
  private SilentSender silent;

  @BeforeEach
  void setUp() {
    db = offlineInstance("db");
    bot = new ReplyFlowBot(EMPTY, EMPTY, db);

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
  void repliesHandlePollResponse() {
    Update update = mock(Update.class);
    when(update.hasPoll()).thenReturn(true);
    when(update.hasMessage()).thenReturn(false);

    Poll poll = mock(Poll.class);
    when(poll.getId()).thenReturn("1");
    when(update.getPoll()).thenReturn(poll);

    assertFalse(bot.filterReply(update));
  }

  public static class ReplyFlowBot extends AbilityBot {

    private ReplyFlowBot(String botToken, String botUsername, DBContext db) {
      super(botToken, botUsername, db);
    }

    @Override
    public int creatorId() {
      return 0;
    }

    public ReplyFlow messageFlow() {

      Reply sayMessage = Reply.of(upd -> silent.send("Some message", getChatId(upd)),
              Flag.MESSAGE);

      return ReplyFlow.builder(db)
              .onlyIf(Flag.MESSAGE)
              .next(sayMessage)
              .build();
    }
  }
}
