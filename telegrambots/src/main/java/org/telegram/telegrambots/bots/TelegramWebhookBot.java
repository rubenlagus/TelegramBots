package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.WebhookBot;
import org.telegram.telegrambots.util.WebhookUtils;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Base abstract class for a bot that will receive updates using a
 * <a href="https://core.telegram.org/bots/api#setwebhook">webhook</a>
 * @date 14 of January of 2016
 */
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