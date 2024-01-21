package org.telegram.telegrambots.abilitybots.api.bot;

import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.toggle.AbilityToggle;
import org.telegram.telegrambots.abilitybots.api.toggle.DefaultToggle;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;

import static org.telegram.telegrambots.abilitybots.api.db.MapDBContext.onlineInstance;

/**
 * A {@link TelegramWebhookBot}-flavor AbilityBot.
 *
 * @author Abbas Abou Daya
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbilityWebhookBot extends BaseAbilityBot implements TelegramWebhookBot {

    private final String botPath;

    protected AbilityWebhookBot(TelegramClient telegramClient, String botUsername, String botPath, DBContext db, AbilityToggle toggle) {
        super(telegramClient, botUsername, db, toggle);
        this.botPath = botPath;
    }

    protected AbilityWebhookBot(TelegramClient telegramClient, String botUsername, String botPath, AbilityToggle toggle) {
        this(telegramClient, botUsername, botPath, onlineInstance(botUsername), toggle);
    }

    protected AbilityWebhookBot(TelegramClient telegramClient, String botUsername, String botPath, DBContext db) {
        this(telegramClient, botUsername, botPath, db, new DefaultToggle());
    }

    protected AbilityWebhookBot(TelegramClient telegramClient, String botUsername, String botPath) {
        this(telegramClient, botUsername, botPath, onlineInstance(botUsername));
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        super.consume(update);
        return null;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }
}