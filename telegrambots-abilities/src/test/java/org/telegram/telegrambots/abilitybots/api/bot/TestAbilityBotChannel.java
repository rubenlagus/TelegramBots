package org.telegram.telegrambots.abilitybots.api.bot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

import org.telegram.telegrambots.abilitybots.api.toggle.BareboneToggle;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.telegram.telegrambots.abilitybots.api.db.MapDBContext.offlineInstance;

public class TestAbilityBotChannel {
    private ChannelAbilityBot bot;
    private DBContext db;
    private TelegramClient telegramClient;
    private SilentSender silent;

    @BeforeEach
    void setUp() {
        telegramClient = mock(TelegramClient.class);
        silent = spy(new SilentSender(telegramClient));

        db = offlineInstance("db");
        bot = new ChannelAbilityBot(telegramClient, EMPTY, db);
        bot.silent = silent;
        bot.onRegister();
    }

    @AfterEach
    void tearDown() throws IOException {
        db.clear();
        db.close();
    }

    @Test
    void canTriggerAbilityInChannel() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);

        when(update.hasChannelPost()).thenReturn(true);
        when(update.getChannelPost()).thenReturn(message);
        when(message.getChatId()).thenReturn(12345L);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/channel_msg_test");
        when(message.getFrom()).thenReturn(null); // Channel posts dosent have user

        bot.consume(update);

        verify(silent, times(1)).send("testtt", 12345L);
    }

    @Test
    void canTriggerAbilityInEditedChannelPost() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);

        when(update.hasChannelPost()).thenReturn(false);
        when(update.hasEditedChannelPost()).thenReturn(true);
        when(update.getEditedChannelPost()).thenReturn(message);

        when(message.getChatId()).thenReturn(12345L);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/channel_msg_test");
        when(message.getFrom()).thenReturn(null);

        bot.consume(update);

        verify(silent, times(1)).send("testtt", 12345L);
    }

    private static class ChannelAbilityBot extends BaseAbilityBot {
        public ChannelAbilityBot(TelegramClient telegramClient, String botUsername, DBContext db) {
            super(telegramClient, botUsername, db, new BareboneToggle());
        }

        @Override
        public long creatorId() {
            return 228322L;
        }

        public Ability test() {
            return Ability.builder()
                    .name("channel_msg_test")
                    .privacy(Privacy.PUBLIC)
                    .locality(Locality.ALL)
                    .action(ctx -> {
                        silent.send("testtt", ctx.chatId());
                    })
                    .build();
        }
    }
}