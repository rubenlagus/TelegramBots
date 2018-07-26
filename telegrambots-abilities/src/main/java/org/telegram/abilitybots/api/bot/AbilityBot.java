package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;

import static org.telegram.abilitybots.api.db.MapDBContext.onlineInstance;

/**
 * The default AbilityBot class implements {@link LongPollingBot}. It delegates all updates to a {@link TelegramLongPollingBot} instance.
 *
 * @author Abbas Abou Daya
 */
public abstract class AbilityBot extends BaseAbilityBot implements LongPollingBot {
  protected AbilityBot(String botToken, String botUsername, DBContext db, DefaultBotOptions botOptions) {
    super(botToken, botUsername, db, botOptions);
  }

  protected AbilityBot(String botToken, String botUsername, DBContext db) {
    this(botToken, botUsername, db, new DefaultBotOptions());
  }

  protected AbilityBot(String botToken, String botUsername, DefaultBotOptions botOptions) {
    this(botToken, botUsername, onlineInstance(botUsername), botOptions);
  }

  protected AbilityBot(String botToken, String botUsername) {
    this(botToken, botUsername, onlineInstance(botUsername));
  }

  @Override
  public void onUpdateReceived(Update update) {
    super.onUpdateReceived(update);
  }

  @Override
  public void clearWebhook() throws TelegramApiRequestException {
    WebhookUtils.clearWebhook(this);
  }
}