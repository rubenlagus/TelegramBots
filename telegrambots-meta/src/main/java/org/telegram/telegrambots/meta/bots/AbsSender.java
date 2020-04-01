package org.telegram.telegrambots.meta.bots;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumb;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updates.GetWebhookInfo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.WebhookInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class AbsSender {
    protected AbsSender() {
    }

    // General methods to execute BotApiMethods

    public <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException {
        if (method == null) {
            throw new TelegramApiException("Parameter method can not be null");
        }
        if (callback == null) {
            throw new TelegramApiException("Parameter callback can not be null");
        }
        sendApiMethodAsync(method, callback);
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        if (method == null) {
            throw new TelegramApiException("Parameter method can not be null");
        }
        return sendApiMethod(method);
    }

    // Send Requests

    public final User getMe() throws TelegramApiException {
        return sendApiMethod(new GetMe());
    }

    public final WebhookInfo getWebhookInfo() throws TelegramApiException {
        GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
        return sendApiMethod(getWebhookInfo);
    }


    // Send Requests Async

    public final void getMeAsync(SentCallback<User> sentCallback) throws TelegramApiException {
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(new GetMe(), sentCallback);
    }

    public final void getWebhookInfoAsync(SentCallback<WebhookInfo> sentCallback) throws TelegramApiException {
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(new GetWebhookInfo(), sentCallback);
    }

    // Specific Send Requests
    public abstract Message execute(SendDocument sendDocument) throws TelegramApiException;

    public abstract Message execute(SendPhoto sendPhoto) throws TelegramApiException;

    public abstract Message execute(SendVideo sendVideo) throws TelegramApiException;

    public abstract Message execute(SendVideoNote sendVideoNote) throws TelegramApiException;

    public abstract Message execute(SendSticker sendSticker) throws TelegramApiException;

    /**
     * Sends a file using Send Audio method (https://core.telegram.org/bots/api#sendaudio)
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public abstract Message execute(SendAudio sendAudio) throws TelegramApiException;

    /**
     * Sends a voice note using Send Voice method (https://core.telegram.org/bots/api#sendvoice)
     * For this to work, your audio must be in an .ogg file encoded with OPUS
     * @param sendVoice Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public abstract Message execute(SendVoice sendVoice) throws TelegramApiException;

    /**
     * Send a media group (https://core.telegram.org/bots/api#sendMediaGroup)
     * @return If success, list of generated messages
     * @throws TelegramApiException If there is any error sending the media group
     */
    public abstract List<Message> execute(SendMediaGroup sendMediaGroup) throws TelegramApiException;

    /**
     * Set chat profile photo (https://core.telegram.org/bots/api#setChatPhoto)
     * @param setChatPhoto Information to set the photo
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error setting the photo.
     */
    public abstract Boolean execute(SetChatPhoto setChatPhoto) throws TelegramApiException;

    /**
     * Adds a new sticker to a set (https://core.telegram.org/bots/api#addStickerToSet)
     * @param addStickerToSet Information of the sticker to set
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error adding the sticker to the set
     */
    public abstract Boolean execute(AddStickerToSet addStickerToSet) throws TelegramApiException;

    /**
     * Set sticker set thumb (https://core.telegram.org/bots/api#setStickerSetThumb)
     * @param setStickerSetThumb Information of the sticker to set
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error setting the thumb to the set
     */
    public abstract Boolean execute(SetStickerSetThumb setStickerSetThumb) throws TelegramApiException;

    /**
     * Creates a new sticker set (https://core.telegram.org/bots/api#createNewStickerSet)
     * @param createNewStickerSet Information of the sticker set to create
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error creating the new sticker set
     */
    public abstract Boolean execute(CreateNewStickerSet createNewStickerSet) throws TelegramApiException;

    /**
     * Upload a new file as sticker (https://core.telegram.org/bots/api#uploadStickerFile)
     * @param uploadStickerFile Information of the file to upload as sticker
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error uploading the new file
     */
    public abstract File execute(UploadStickerFile uploadStickerFile) throws TelegramApiException;

    /**
     * Edit media in a message
     * @param editMessageMedia Information of the new media
     * @return If the edited message was sent by the bot, the edited Message is returned, otherwise True is returned
     * @throws TelegramApiException If there is any error editing the media
     */
    public abstract Serializable execute(EditMessageMedia editMessageMedia) throws TelegramApiException;

    /**
     * Send animation
     * @param sendAnimation Information of the animation
     * @return Sent message
     * @throws TelegramApiException If there is any error sending animation
     */
    public abstract Message execute(SendAnimation sendAnimation) throws TelegramApiException;

    // Simplified methods

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback);

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method) throws TelegramApiException;
}
