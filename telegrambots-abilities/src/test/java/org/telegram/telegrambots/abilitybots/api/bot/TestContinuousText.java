package org.telegram.telegrambots.abilitybots.api.bot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.telegram.telegrambots.abilitybots.api.bot.TestUtils.mockFullUpdate;
import static org.telegram.telegrambots.abilitybots.api.db.MapDBContext.offlineInstance;
import static org.telegram.telegrambots.abilitybots.api.objects.Ability.builder;
import static org.telegram.telegrambots.abilitybots.api.objects.Locality.ALL;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.PUBLIC;

public class TestContinuousText {
  private static final User USER = new User(1L, "first", false);

  private DBContext db;

  private SilentSender silent;
  private ContinuousTextBot bot;

  @BeforeEach
  void setUp() {
    db = offlineInstance("db");
    TelegramClient telegramClient = mock(TelegramClient.class);
    bot = new ContinuousTextBot(telegramClient, EMPTY, db);
    bot.onRegister();
    silent = spy(new SilentSender(telegramClient));
    bot.silent = silent;
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  void processesContinuousText() {
    Update update = mockFullUpdate(bot, USER, "/do2");

    bot.consume(update);

    verify(silent, times(1))
        .send("2", USER.getId());
  }

  @Test
  void matchesLongestAbilityName() {
    Update update = mockFullUpdate(bot, USER, "/do1");

    bot.consume(update);

    verify(silent, times(1))
        .send("longer ability name", USER.getId());
  }

  public static class ContinuousTextBot extends AbilityBot {

    public ContinuousTextBot(TelegramClient telegramClient, String username, DBContext db) {
      super(telegramClient, username, db);
    }

    @Override
    public long creatorId() {
      return 1337;
    }

    @Override
    protected boolean allowContinuousText() {
      return true;
    }

    public Ability continuousTextAbility() {
      return builder()
          .name("do")
          .privacy(PUBLIC)
          .locality(ALL)
          .input(0)
          .action(ctx -> silent.send(ctx.firstArg(), ctx.chatId()))
          .build();
    }

    public Ability continuousTextSimilarAbility() {
      return builder()
          .name("do1")
          .privacy(PUBLIC)
          .locality(ALL)
          .input(0)
          .action(ctx -> silent.send("longer ability name", ctx.chatId()))
          .build();
    }
  }
}