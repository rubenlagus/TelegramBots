package org.telegram.abilitybots.api.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.Optional;


/**
 * A silent sender that returns {@link Optional} objects upon execution. Mainly used to decrease verboseness of exception handling.
 *
 * @author Abbas Abou Daya
 */
public class SilentSender {
  private static final Logger log = LoggerFactory.getLogger(SilentSender.class);

  private final MessageSender sender;

  public SilentSender(MessageSender sender) {
    this.sender = sender;
  }

  /**
   * A validator for markdown style message to avoid exception from Telegram API.
   * @author SUSTechDXiang
   * @version 1.0
   * @return
   */
  public Optional<Message> sendMdValidator(String message, long id) {

    boolean bold = true;
    boolean italic = true;
    boolean inline = true;
    boolean inline_URL = true;
    boolean language = true;
    boolean valid = true;
    for (int i = 0; i < message.length(); i++){
      //judges if it is end of an entity
      if (!bold && message.charAt(i) == '*' )
        bold = true;
      if (!italic && message.charAt(i) == '_' )
        italic = true;
      if (!inline && message.charAt(i) == '`')
        inline = true;
      if (!inline_URL && message.charAt(i) == ']')
        inline_URL = true;
      if (!language && (i < message.length() - 2 && message.charAt(i) == '`' && message.charAt(i+1) == '`' && message.charAt(i+2) == '`')) {
        language = true;
        i +=2;
      }
      //judges if it is start of an entity
      if (valid && message.charAt(i) == '*' )
        bold = false;
      if (valid && message.charAt(i) == '_' )
        italic = false;
      if (valid && message.charAt(i) == '[' )
        inline_URL = false;
      if (valid && (i < message.length() - 1 && message.charAt(i) == '`' && message.charAt(i+1) != '`'))
        inline = false;
      if (valid && (i < message.length() - 5 && message.charAt(i) == '`' && message.charAt(i+1) == '`' && message.charAt(i+2) == '`')) {
        language = false;
        i +=2;
      }
      //skips the escape character
      if (valid & message.charAt(i) == '\\')
        i++;

      //if an entity does not end, it is not a valid markdown
      valid = bold && italic && inline && inline_URL && language ;
    }

    return doSendMessage(message, id, valid);
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
    msg.setChatId(Long.toString(id));
    ForceReplyKeyboard kb = new ForceReplyKeyboard();
    kb.setForceReply(true);
    kb.setSelective(true);
    msg.setReplyMarkup(kb);

    return execute(msg);
  }

  public <T extends Serializable, Method extends BotApiMethod<T>> Optional<T> execute(Method method) {
    try {
      return Optional.ofNullable(sender.execute(method));
    } catch (TelegramApiException e) {
      log.error("Could not execute bot API method", e);
      return Optional.empty();
    }
  }

  public <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void
  executeAsync(Method method, Callback callable) {
    try {
      sender.executeAsync(method, callable);
    } catch (TelegramApiException e) {
      log.error("Could not execute bot API method", e);
    }
  }

  private Optional<Message> doSendMessage(String txt, long groupId, boolean format) {
    SendMessage smsg = new SendMessage();
    smsg.setChatId(Long.toString(groupId));
    smsg.setText(txt);
    smsg.enableMarkdown(format);

    return execute(smsg);
  }
}