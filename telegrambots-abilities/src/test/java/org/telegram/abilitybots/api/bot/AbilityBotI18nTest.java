package org.telegram.abilitybots.api.bot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.api.objects.User;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.telegram.abilitybots.api.bot.AbilityBotTest.mockContext;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;

public class AbilityBotI18nTest {
  private static final User NO_LANGUAGE_USER = new User(1, "first", false, "last", "username", null);
  private static final User ITALIAN_USER = new User(2, "first", false, "last", "username", "it-IT");

  private DBContext db;
  private NoPublicCommandsBot bot;

  private MessageSender sender;
  private SilentSender silent;

  @Before
  public void setUp() {
    db = offlineInstance("db");
    bot = new NoPublicCommandsBot(EMPTY, EMPTY, db);

    sender = mock(MessageSender.class);
    silent = mock(SilentSender.class);

    bot.sender = sender;
    bot.silent = silent;

  }

  @Test
  public void missingPublicCommandsLocalizedCorrectly1() {
    MessageContext context = mockContext(NO_LANGUAGE_USER);

    bot.reportCommands().action().accept(context);

    verify(silent, times(1))
        .send("No public commands found.", NO_LANGUAGE_USER.getId());
  }

  @Test
  public void missingPublicCommandsLocalizedCorrectly2() {
    MessageContext context = mockContext(ITALIAN_USER);

    bot.reportCommands().action().accept(context);

    verify(silent, times(1))
        .send("Non sono presenti comandi pubblici.", ITALIAN_USER.getId());
  }

  @After
  public void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  public static class NoPublicCommandsBot extends AbilityBot {

    protected NoPublicCommandsBot(String botToken, String botUsername, DBContext db) {
      super(botToken, botUsername, db);
    }

    @Override
    public int creatorId() {
      return 1;
    }
  }
}
