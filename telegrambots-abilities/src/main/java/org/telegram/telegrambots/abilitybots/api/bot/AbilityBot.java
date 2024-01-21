package org.telegram.telegrambots.abilitybots.api.bot;

import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.toggle.AbilityToggle;
import org.telegram.telegrambots.abilitybots.api.toggle.DefaultToggle;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.telegram.telegrambots.abilitybots.api.db.MapDBContext.onlineInstance;

/**
 * The default AbilityBot class implements {@link LongPollingSingleThreadUpdateConsumer}.
 * It delegates all updates to a {@link TelegramClient} instance.
 *
 * @author Abbas Abou Daya
 */
public abstract class AbilityBot extends BaseAbilityBot {
    protected AbilityBot(TelegramClient telegramClient, String botUsername, DBContext db, AbilityToggle toggle) {
        super(telegramClient, botUsername, db, toggle);
    }

    protected AbilityBot(TelegramClient telegramClient, String botUsername, AbilityToggle toggle) {
        this(telegramClient, botUsername, onlineInstance(botUsername), toggle);
    }

    protected AbilityBot(TelegramClient telegramClient, String botUsername, DBContext db) {
        this(telegramClient, botUsername, db, new DefaultToggle());
    }

    protected AbilityBot(TelegramClient telegramClient, String botUsername) {
        this(telegramClient, botUsername, onlineInstance(botUsername));
    }

    @Override
    public void consume(Update update) {
        super.consume(update);
    }
}