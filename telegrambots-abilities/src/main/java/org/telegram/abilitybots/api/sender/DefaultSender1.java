package org.telegram.abilitybots.api.sender;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;
public class DefaultSender1 implements MessageSender1{
    private static final String TAG = MessageSender.class.getName();

    private DefaultAbsSender bot;

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException {

    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        return null;
    }

    @Override
    public Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
        return bot.execute(sendPhoto);
    }

    @Override
    public Message sendVideo(SendVideo sendVideo) throws TelegramApiException {
        return bot.execute(sendVideo);
    }

    @Override
    public Message sendSticker(SendSticker sendSticker) throws TelegramApiException {
        return bot.execute(sendSticker);
    }

    @Override
    public Message sendAudio(SendAudio sendAudio) throws TelegramApiException {
        return bot.execute(sendAudio);
    }

    @Override
    public Message sendVoice(SendVoice sendVoice) throws TelegramApiException {
        return bot.execute(sendVoice);
    }
    @Override
      public Message sendVideoNote(SendVideoNote sendVideoNote) {
        return null;
      }
}
