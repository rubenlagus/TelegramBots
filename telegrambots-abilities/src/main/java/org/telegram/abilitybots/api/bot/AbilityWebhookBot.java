package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.toggle.DefaultToggle;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.util.WebhookUtils;

import static org.telegram.abilitybots.api.db.MapDBContext.onlineInstance;

/**
 * A {@link WebhookBot}-flavor AbilityBot. It delegates all updates to a {@link TelegramWebhookBot} instance.
 *
 * @author Abbas Abou Daya
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbilityWebhookBot extends BaseAbilityBot implements WebhookBot {

    private final String botPath;

    protected AbilityWebhookBot(String botToken, String botUsername, String botPath, DBContext db, AbilityToggle toggle, DefaultBotOptions botOptions) {
        super(botToken, botUsername, db, toggle, botOptions);
        this.botPath = botPath;
    }

    protected AbilityWebhookBot(String botToken, String botUsername, String botPath, AbilityToggle toggle, DefaultBotOptions options) {
        this(botToken, botUsername, botPath, onlineInstance(botUsername), toggle, options);
    }

    protected AbilityWebhookBot(String botToken, String botUsername, String botPath, DBContext db, AbilityToggle toggle) {
        this(botToken, botUsername, botPath, db, toggle, new DefaultBotOptions());
    }

    protected AbilityWebhookBot(String botToken, String botUsername, String botPath, DBContext db, DefaultBotOptions options) {
        this(botToken, botUsername, botPath, db, new DefaultToggle(), options);
    }

    protected AbilityWebhookBot(String botToken, String botUsername, String botPath, DefaultBotOptions botOptions) {
        this(botToken, botUsername, botPath, onlineInstance(botUsername), botOptions);
    }

    protected AbilityWebhookBot(String botToken, String botUsername, String botPath, AbilityToggle toggle) {
        this(botToken, botUsername, botPath, onlineInstance(botUsername), toggle);
    }

    protected AbilityWebhookBot(String botToken, String botUsername, String botPath, DBContext db) {
        this(botToken, botUsername, botPath, db, new DefaultToggle());
    }

    protected AbilityWebhookBot(String botToken, String botUsername, String botPath) {
        this(botToken, botUsername, botPath, onlineInstance(botUsername));
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        super.onUpdateReceived(update);
        return null;
    }

    @Override
    public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {
        WebhookUtils.setWebhook(this, this, setWebhook);
    }

    @Override
    public String getBotPath() {
        return botPath;
    }
}