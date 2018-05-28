package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.WebhookBot;

import static org.telegram.abilitybots.api.db.MapDBContext.onlineInstance;

/**
 * A {@link WebhookBot}-flavor AbilityBot. It delegates all updates to a {@link TelegramWebhookBot} instance.
 *
 * @author Abbas Abou Daya
 */
public abstract class AbilityWebhookBot extends BaseAbilityBot implements WebhookBot {
  private final TelegramWebhookBot bot;

  protected AbilityWebhookBot(String botToken, String botUsername, String botPath, DBContext db, DefaultBotOptions botOptions) {
    super(botToken, botUsername, db, botOptions);

    bot = new TelegramWebhookBot() {

      @Override
      public BotApiMethod onWebhookUpdateReceived(Update update) {
        AbilityWebhookBot.this.onUpdateReceived(update);
        return null;
      }

      @Override
      public String getBotUsername() {
        return botUsername;
      }

      @Override
      public String getBotToken() {
        return botToken;
      }

      @Override
      public String getBotPath() {
        return botPath;
      }
    };
  }

  protected AbilityWebhookBot(String botToken, String botUsername, String botPath, DBContext db) {
    this(botToken, botUsername, botPath, db, new DefaultBotOptions());
  }

  protected AbilityWebhookBot(String botToken, String botUsername, String botPath, DefaultBotOptions botOptions) {
    this(botToken, botUsername, botPath, onlineInstance(botUsername), botOptions);
  }

  protected AbilityWebhookBot(String botToken, String botUsername, String botPath) {
    this(botToken, botUsername, botPath, onlineInstance(botUsername));
  }

  @Override
  public BotApiMethod onWebhookUpdateReceived(Update update) {
    return bot.onWebhookUpdateReceived(update);
  }

  @Override
  public void setWebhook(String url, String publicCertificatePath) throws TelegramApiRequestException {
    bot.setWebhook(url, publicCertificatePath);
  }

  @Override
  public String getBotPath() {
    return bot.getBotPath();
  }
}