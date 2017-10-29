package org.telegram.abilitybots.api.sender;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.Serializable;
import java.util.Optional;

//TODO: DOC
public class SilentSender {
  private static final String TAG = SilentSender.class.getSimpleName();

  private final MessageSender sender;

  public SilentSender(MessageSender sender) {
    this.sender = sender;
  }

  public Optional<Message> send(String message, long id) {
    return doSendMessage(message, id, false);
  }

  public Optional<Message> sendMd(String message, long id) {
    return doSendMessage(message, id, true);
  }

  public Optional<Message> forceReply(String message, long id) {
    SendMessage msg = new SendMessage();
    msg.setText(message);
    msg.setChatId(id);
    msg.setReplyMarkup(new ForceReplyKeyboard());

    return execute(msg);
  }

  private Optional<Message> doSendMessage(String txt, long groupId, boolean format) {
    SendMessage smsg = new SendMessage();
    smsg.setChatId(groupId);
    smsg.setText(txt);
    smsg.enableMarkdown(format);

    return execute(smsg);
  }

  private <T extends Serializable, Method extends BotApiMethod<T>> Optional<T> execute(Method method) {
    try {
      return Optional.ofNullable(sender.execute(method));
    } catch (TelegramApiException e) {
      BotLogger.error("Could not execute bot API method", TAG, e);
      return Optional.empty();
    }
  }

  private <T extends Serializable, Method extends BotApiMethod<T>> Optional<T> executeAsync(Method method) {
    try {
      return Optional.ofNullable(sender.execute(method));
    } catch (TelegramApiException e) {
      BotLogger.error("Could not execute bot API method", TAG, e);
      return Optional.empty();
    }

  }
}
