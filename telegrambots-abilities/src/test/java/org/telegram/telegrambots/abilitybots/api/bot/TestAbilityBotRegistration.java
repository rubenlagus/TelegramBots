package org.telegram.telegrambots.abilitybots.api.bot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.telegram.telegrambots.abilitybots.api.bot.TestUtils.USER;
import static org.telegram.telegrambots.abilitybots.api.bot.TestUtils.mockFullUpdate;
import static org.telegram.telegrambots.abilitybots.api.db.MapDBContext.offlineInstance;

class TestAbilityBotRegistration {
  private DefaultBot bot;
  private DBContext db;
  private SilentSender silent;

  @BeforeEach
  void setUp() {
    TelegramClient telegramClient = mock(TelegramClient.class);
    silent = spy(new SilentSender(telegramClient));

    db = offlineInstance("db");
    bot = new DefaultBot(telegramClient, EMPTY, db);
    bot.silent = silent;
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  void registersAbilitiesOnFirstUpdateWithoutExplicitOnRegister() {
    Update update = mockFullUpdate(bot, USER, "must reply");

    assertDoesNotThrow(() -> bot.consume(update));

    verify(silent, times(1)).send("reply", USER.getId());
  }

  @Test
  void onRegisterCanBeCalledMoreThanOnce() {
    bot.onRegister();
    int abilities = bot.getAbilities().size();
    int replies = bot.getReplies().size();

    assertDoesNotThrow(() -> bot.onRegister());

    assertEquals(abilities, bot.getAbilities().size());
    assertEquals(replies, bot.getReplies().size());
  }

  @Test
  void onRegisterAfterFirstUpdateDoesNotRegisterAgain() {
    bot.consume(mockFullUpdate(bot, USER, "must reply"));

    assertDoesNotThrow(() -> bot.onRegister());
  }
}
