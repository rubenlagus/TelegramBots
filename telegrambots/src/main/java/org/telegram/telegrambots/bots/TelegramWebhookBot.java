package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
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
  public TelegramWebhookBot() {
    this(ApiContext.getInstance(DefaultBotOptions.class));
  }

  public TelegramWebhookBot(DefaultBotOptions options) {
    super(options);
  }

  @Override
  public void setWebhook(String url, String publicCertificatePath) throws TelegramApiRequestException {
    WebhookUtils.setWebhook(this, url, publicCertificatePath);
  }
}