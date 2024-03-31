package org.telegram.telegrambots.client;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumb;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Abstract client implementation which delegates all synchronous methods to the async methods. Preferable for implementations
 */
public abstract class AbstractTelegramClient implements TelegramClient {
    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        try {
            return executeAsync(method).get();
        } catch (Exception e) {
            throw mapException(e, method.getMethod());
        }
    }

    @Override
    public Message execute(SendDocument sendDocument) throws TelegramApiException {
        try {
            return executeAsync(sendDocument).get();
        } catch (Exception e) {
            throw mapException(e, sendDocument.getMethod());
        }
    }

    @Override
    public Message execute(SendPhoto sendPhoto) throws TelegramApiException {
        try {
            return executeAsync(sendPhoto).get();
        } catch (Exception e) {
            throw mapException(e, sendPhoto.getMethod());
        }
    }

    @Override
    public Message execute(SendVideo sendVideo) throws TelegramApiException {
        try {
            return executeAsync(sendVideo).get();
        } catch (Exception e) {
            throw mapException(e, sendVideo.getMethod());
        }
    }

    @Override
    public Message execute(SendVideoNote sendVideoNote) throws TelegramApiException {
        try {
            return executeAsync(sendVideoNote).get();
        } catch (Exception e) {
            throw mapException(e, sendVideoNote.getMethod());
        }
    }

    @Override
    public Message execute(SendSticker sendSticker) throws TelegramApiException {
        try {
            return executeAsync(sendSticker).get();
        } catch (Exception e) {
            throw mapException(e, sendSticker.getMethod());
        }
    }

    @Override
    public Message execute(SendAudio sendAudio) throws TelegramApiException {
        try {
            return executeAsync(sendAudio).get();
        } catch (Exception e) {
            throw mapException(e, sendAudio.getMethod());
        }
    }

    @Override
    public Message execute(SendVoice sendVoice) throws TelegramApiException {
        try {
            return executeAsync(sendVoice).get();
        } catch (Exception e) {
            throw mapException(e, sendVoice.getMethod());
        }
    }

    @Override
    public List<Message> execute(SendMediaGroup sendMediaGroup) throws TelegramApiException {
        try {
            return executeAsync(sendMediaGroup).get();
        } catch (Exception e) {
            throw mapException(e, sendMediaGroup.getMethod());
        }
    }

    @Override
    public Boolean execute(SetChatPhoto setChatPhoto) throws TelegramApiException {
        try {
            return executeAsync(setChatPhoto).get();
        } catch (Exception e) {
            throw mapException(e, setChatPhoto.getMethod());
        }
    }

    @Override
    public Boolean execute(AddStickerToSet addStickerToSet) throws TelegramApiException {
        try {
            return executeAsync(addStickerToSet).get();
        } catch (Exception e) {
            throw mapException(e, addStickerToSet.getMethod());
        }
    }

    @Override
    public Boolean execute(SetStickerSetThumb setStickerSetThumb) throws TelegramApiException {
        try {
            return executeAsync(setStickerSetThumb).get();
        } catch (Exception e) {
            throw mapException(e, setStickerSetThumb.getMethod());
        }
    }

    @Override
    public Boolean execute(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
        try {
            return executeAsync(createNewStickerSet).get();
        } catch (Exception e) {
            throw mapException(e, createNewStickerSet.getMethod());
        }
    }

    @Override
    public File execute(UploadStickerFile uploadStickerFile) throws TelegramApiException {
        try {
            return executeAsync(uploadStickerFile).get();
        } catch (Exception e) {
            throw mapException(e, uploadStickerFile.getMethod());
        }
    }

    @Override
    public Serializable execute(EditMessageMedia editMessageMedia) throws TelegramApiException {
        try {
            return executeAsync(editMessageMedia).get();
        } catch (Exception e) {
            throw mapException(e, editMessageMedia.getMethod());
        }
    }

    @Override
    public Message execute(SendAnimation sendAnimation) throws TelegramApiException {
        try {
            return executeAsync(sendAnimation).get();
        } catch (Exception e) {
            throw mapException(e, sendAnimation.getMethod());
        }
    }

    private TelegramApiException mapException(Exception e, String method) {
        if (e instanceof ExecutionException) {
            if (e.getCause() instanceof TelegramApiException) {
                return (TelegramApiException) e.getCause();
            } else {
                return new TelegramApiException("Unable to execute" + method + "method", e.getCause());
            }
        } else {
            return new TelegramApiException("Unable to execute" + method + "method", e.getCause());
        }
    }
}
