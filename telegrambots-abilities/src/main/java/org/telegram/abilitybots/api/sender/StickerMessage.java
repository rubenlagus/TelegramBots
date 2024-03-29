package org.telegram.abilitybots.api.sender;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;

/**
 * A sender interface that replicates {@link DefaultAbsSender} methods.
 *
 * @author Abbas Abou Daya
 */
public interface StickerMessage {

    <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException;

    <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException;
    Boolean addStickerToSet(AddStickerToSet addStickerToSet) throws TelegramApiException;

    Boolean createNewStickerSet(CreateNewStickerSet createNewStickerSet) throws TelegramApiException;

    File uploadStickerFile(UploadStickerFile uploadStickerFile) throws TelegramApiException;
}

