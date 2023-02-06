package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.util.WebhookUtils;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Base abstract class for a bot that will receive updates using a
 * <a href="https://core.telegram.org/bots/api#setwebhook">webhook</a>
 */
@SuppressWarnings("WeakerAccess")
public abstract class TelegramWebhookBot extends DefaultAbsSender implements WebhookBot {
  /**
   * If this is used getBotToken has to be overridden in order to return the bot token!
   * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
   */
  @Deprecated
  public TelegramWebhookBot() {
    this(new DefaultBotOptions());
  }

  /**
   * If this is used getBotToken has to be overridden in order to return the bot token!
   * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
   */
  @Deprecated
  public TelegramWebhookBot(DefaultBotOptions options) {
    super(options);
  }

  public TelegramWebhookBot(String botToken) {
    this(new DefaultBotOptions(), botToken);
  }

  public TelegramWebhookBot(DefaultBotOptions options, String botToken) {
    super(options, botToken);
  }

  @Override
  public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {
    WebhookUtils.setWebhook(this, this, setWebhook);
  }
}