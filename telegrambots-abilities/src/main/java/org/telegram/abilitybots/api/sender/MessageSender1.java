package org.telegram.abilitybots.api.sender;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;

/**
 * A sender interface that replicates {@link DefaultAbsSender} methods.
 *
 * @author Abbas Abou Daya
 */
public interface MessageSender1 {
    /*
    * Refactored code - Interfaces related to send functionality.
     */
    <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException;

    <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException;


    Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException;

    Message sendVideo(SendVideo sendVideo) throws TelegramApiException;

    Message sendAudio(SendAudio sendAudio) throws TelegramApiException;

    Message sendVoice(SendVoice sendVoice) throws TelegramApiException;

    Message sendVideoNote(SendVideoNote sendVideoNote) throws TelegramApiException;

    Message sendSticker(SendSticker sendSticker) throws TelegramApiException;
}

