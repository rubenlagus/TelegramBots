package org.telegram.telegrambots.abilitybots.api.bot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.telegram.telegrambots.abilitybots.api.bot.TestUtils.mockContext;
import static org.telegram.telegrambots.abilitybots.api.db.MapDBContext.offlineInstance;

class TestAbilityBotI18n {
  private static final User NO_LANGUAGE_USER = new User(1L, "first", false, "last", "username", null, false, false, false, false, false, false, false);
  private static final User ITALIAN_USER = new User(2L, "first", false, "last", "username", "it-IT", false, false, false, false, false, false, false);

  private DBContext db;
  private NoPublicCommandsBot bot;
  private DefaultAbilities defaultAbs;

  private TelegramClient telegramClient;
  private SilentSender silent;

  @BeforeEach
  void setUp() {
    telegramClient = mock(TelegramClient.class);
    silent = spy(new SilentSender(telegramClient));

    db = offlineInstance("db");
    bot = new NoPublicCommandsBot(telegramClient, EMPTY, db);
    bot.onRegister();
    bot.silent = silent;
    defaultAbs = new DefaultAbilities(bot);
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  void missingPublicCommandsLocalizedInEnglishByDefault() {
    MessageContext context = mockContext(NO_LANGUAGE_USER);

    defaultAbs.reportCommands().action().accept(context);

    verify(silent, times(1))
        .send("No available commands found.", NO_LANGUAGE_USER.getId());
  }

  @Test
  void missingPublicCommandsLocalizedInItalian() {
    MessageContext context = mockContext(ITALIAN_USER);

    defaultAbs.reportCommands().action().accept(context);

    verify(silent, times(1))
        .send("Non sono presenti comandi disponibile.", ITALIAN_USER.getId());
  }

  public static class NoPublicCommandsBot extends AbilityBot {

    NoPublicCommandsBot(TelegramClient telegramClient, String botUsername, DBContext db) {
      super(telegramClient, botUsername, db);
    }

    @Override
    public long creatorId() {
      return 1;
    }
  }
}
