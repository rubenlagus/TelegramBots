package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.toggle.DefaultToggle;
import org.telegram.abilitybots.api.util.Pair;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.util.WebhookUtils;

import java.util.stream.Stream;

import static org.telegram.abilitybots.api.db.MapDBContext.onlineInstance;

/**
 * A {@link WebhookBot}-flavor AbilityBot. It delegates all updates to a {@link TelegramWebhookBot} instance.
 *
 * @author Abbas Abou Daya
 */
@SuppressWarnings({"WeakerAccess", "rawtypes"})
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
  public BotApiMethod onWebhookUpdateReceived(Update update) {
    return Stream.of(update)
            .filter(super::checkGlobalFlags)
            .filter(super::checkBlacklist)
            .map(super::addUser)
            .filter(super::filterReply)
            .filter(super::hasUser)
            .map(super::getAbility)
            .filter(super::validateAbility)
            .filter(super::checkPrivacy)
            .filter(super::checkLocality)
            .filter(super::checkInput)
            .filter(super::checkMessageFlags)
            .map(super::getContext)
            .map(super::updateStats)
            .map(this::consumeWebhookUpdate)
            .findFirst().orElse(null);
  }

  private BotApiMethod consumeWebhookUpdate(Pair<MessageContext, Ability> pair){
    return pair.b().webhookAction().apply(pair.a());
  }

  @Override
  public void setWebhook(String url, String publicCertificatePath) throws TelegramApiRequestException {
    WebhookUtils.setWebhook(this, url, publicCertificatePath);
  }

  @Override
  public String getBotPath() {
    return botPath;
  }
}