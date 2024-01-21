package org.telegram.telegrambots.abilitybots.api.sender;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * A silent sender that returns {@link Optional} objects upon execution. Mainly used to decrease verboseness of exception handling.
 *
 * @author Abbas Abou Daya
 */
@Slf4j
public class SilentSender {
  private final TelegramClient telegramClient;

  public SilentSender(TelegramClient telegramClient) {
    this.telegramClient = telegramClient;
  }

  public Optional<Message> send(String message, long id) {
    return doSendMessage(message, id, false);
  }

  public Optional<Message> sendMd(String message, long id) {
    return doSendMessage(message, id, true);
  }

  public Optional<Message> forceReply(String message, long id) {
    SendMessage msg = new SendMessage(Long.toString(id), message);
    ForceReplyKeyboard kb = new ForceReplyKeyboard();
    kb.setForceReply(true);
    kb.setSelective(true);
    msg.setReplyMarkup(kb);

    return execute(msg);
  }

  public <T extends Serializable, Method extends BotApiMethod<T>> Optional<T> execute(Method method) {
    try {
      return Optional.ofNullable(telegramClient.execute(method));
    } catch (TelegramApiException e) {
      log.error("Could not execute bot API method", e);
      return Optional.empty();
    }
  }

  public <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callable) {
    try {
      CompletableFuture<T> futureResult = telegramClient.executeAsync(method);
      callable.onResult(method, futureResult.get(5, TimeUnit.SECONDS));
    } catch (TelegramApiException | ExecutionException | InterruptedException | TimeoutException e) {
      callable.onException(method, e);
    }
  }

  private Optional<Message> doSendMessage(String txt, long groupId, boolean format) {
    SendMessage smsg = new SendMessage(Long.toString(groupId), txt);
    smsg.enableMarkdown(format);

    return execute(smsg);
  }
}