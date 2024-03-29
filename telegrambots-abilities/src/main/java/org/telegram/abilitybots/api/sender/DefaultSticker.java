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
public class DefaultSticker implements StickerMessage {
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
    public Boolean addStickerToSet(AddStickerToSet addStickerToSet) throws TelegramApiException {
        return bot.execute(addStickerToSet);
    }

    @Override
    public Boolean createNewStickerSet(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
        return bot.execute(createNewStickerSet);
    }

    @Override
    public File uploadStickerFile(UploadStickerFile uploadStickerFile) throws TelegramApiException {
        return bot.execute(uploadStickerFile);
    }
}
