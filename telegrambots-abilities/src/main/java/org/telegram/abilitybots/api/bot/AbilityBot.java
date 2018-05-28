package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.LongPollingBot;

import java.util.List;

import static org.telegram.abilitybots.api.db.MapDBContext.onlineInstance;

/**
 * The default AbilityBot class implements {@link LongPollingBot}. It delegates all updates to a {@link TelegramLongPollingBot} instance.
 *
 * @author Abbas Abou Daya
 */
public abstract class AbilityBot extends BaseAbilityBot implements LongPollingBot {
  private final TelegramLongPollingBot bot;

  protected AbilityBot(String botToken, String botUsername, DBContext db, DefaultBotOptions botOptions) {
    super(botToken, botUsername, db, botOptions);

    bot = new TelegramLongPollingBot() {
      @Override
      public void onUpdateReceived(Update update) {
        AbilityBot.this.onUpdateReceived(update);
      }

      @Override
      public String getBotUsername() {
        return botUsername;
      }

      @Override
      public String getBotToken() {
        return botToken;
      }
    };
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
  public void onUpdatesReceived(List<Update> updates) {
    bot.onUpdatesReceived(updates);
  }

  @Override
  public void clearWebhook() throws TelegramApiRequestException {
    bot.clearWebhook();
  }

  @Override
  public void onClosing() {
    bot.onClosing();
  }
}