package org.telegram.telegrambots.meta.generics;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendPaidMedia;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.ReplaceStickerInSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumbnail;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TelegramClient {

    <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException;

    <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException;

    // Specific Send Requests
    Message execute(SendDocument sendDocument) throws TelegramApiException;

    Message execute(SendPhoto sendPhoto) throws TelegramApiException;

    Message execute(SendVideo sendVideo) throws TelegramApiException;

    Message execute(SendVideoNote sendVideoNote) throws TelegramApiException;

    Message execute(SendSticker sendSticker) throws TelegramApiException;

    /**
     * Sends a file using Send Audio method
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     * @see <a href="https://core.telegram.org/bots/api#sendaudio)">https://core.telegram.org/bots/api#sendaudio)</a>
     */
    Message execute(SendAudio sendAudio) throws TelegramApiException;

    /**
     * Sends a voice note using Send Voice method
     * For this to work, your audio must be in an .ogg file encoded with OPUS
     * @param sendVoice Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     * @see <a href="https://core.telegram.org/bots/api#sendvoice">https://core.telegram.org/bots/api#sendvoice)</a>
     */
    Message execute(SendVoice sendVoice) throws TelegramApiException;

    /**
     * Send a media group
     * @return If success, list of generated messages
     * @throws TelegramApiException If there is any error sending the media group
     * @see <a href="https://core.telegram.org/bots/api#sendMediaGroup">https://core.telegram.org/bots/api#sendMediaGroup</a>
     */
    List<Message> execute(SendMediaGroup sendMediaGroup) throws TelegramApiException;

    /**
     * Send a paid media
     * @return If success, list of generated messages
     * @throws TelegramApiException If there is any error sending the media group
     * @see <a href="https://core.telegram.org/bots/api#sendMediaGroup">https://core.telegram.org/bots/api#sendMediaGroup</a>
     */
    List<Message> execute(SendPaidMedia sendPaidMedia) throws TelegramApiException;

    /**
     * Set chat profile photo
     * @param setChatPhoto Information to set the photo
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error setting the photo.
     * @see <a href="https://core.telegram.org/bots/api#setChatPhoto">https://core.telegram.org/bots/api#setChatPhoto</a>
     */
    Boolean execute(SetChatPhoto setChatPhoto) throws TelegramApiException;

    /**
     * Adds a new sticker to a set
     * @param addStickerToSet Information of the sticker to set
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error adding the sticker to the set
     * @see <a href="https://core.telegram.org/bots/api#addStickerToSet">https://core.telegram.org/bots/api#addStickerToSet</a>
     */
    Boolean execute(AddStickerToSet addStickerToSet) throws TelegramApiException;

    /**
     * Replace a sticker in a set
     * @param replaceStickerInSet Information of the sticker to set
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error adding the sticker to the set
     * @see <a href="https://core.telegram.org/bots/api#replaceStickerInSet">https://core.telegram.org/bots/api#replaceStickerInSet</a>
     */
    Boolean execute(ReplaceStickerInSet replaceStickerInSet) throws TelegramApiException;

    /**
     * Set sticker set thumb
     * @param setStickerSetThumbnail Information of the sticker to set
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error setting the thumb to the set
     * @see <a href="https://core.telegram.org/bots/api#setStickerSetThumbail">https://core.telegram.org/bots/api#setStickerSetThumbail</a>
     */
    Boolean execute(SetStickerSetThumbnail setStickerSetThumbnail) throws TelegramApiException;

    /**
     * Creates a new sticker set ≈
     * @param createNewStickerSet Information of the sticker set to create
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error creating the new sticker set
     * @see <a href="https://core.telegram.org/bots/api#setStickerSetThumbail">https://core.telegram.org/bots/api#setStickerSetThumbail</a>
     */
    Boolean execute(CreateNewStickerSet createNewStickerSet) throws TelegramApiException;

    /**
     * Upload a new file as sticker
     * @param uploadStickerFile Information of the file to upload as sticker
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error uploading the new file
     * @see <a href="https://core.telegram.org/bots/api#uploadStickerFile">https://core.telegram.org/bots/api#uploadStickerFile</a>
     */
    File execute(UploadStickerFile uploadStickerFile) throws TelegramApiException;

    /**
     * Edit media in a message
     * @param editMessageMedia Information of the new media
     * @return If the edited message is not an inline message, the edited Message is returned, otherwise True is returned
     * @throws TelegramApiException If there is any error editing the media
     */
    Serializable execute(EditMessageMedia editMessageMedia) throws TelegramApiException;

    java.io.File downloadFile(File file) throws TelegramApiException;

    default java.io.File downloadFile(String filePath) throws TelegramApiException {
        return downloadFile(new File(null, null, null, filePath));
    }

    InputStream downloadFileAsStream(File file) throws TelegramApiException;

    default InputStream downloadFileAsStream(String filePath) throws TelegramApiException {
        return downloadFileAsStream(new File(null, null, null, filePath));
    }

    /**
     * Send animation
     * @param sendAnimation Information of the animation
     * @return Sent message
     * @throws TelegramApiException If there is any error sending animation
     */
    Message execute(SendAnimation sendAnimation) throws TelegramApiException;

    CompletableFuture<Message> executeAsync(SendDocument sendDocument);

    CompletableFuture<Message> executeAsync(SendPhoto sendPhoto);

    CompletableFuture<Message> executeAsync(SendVideo sendVideo);

    CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote);

    CompletableFuture<Message> executeAsync(SendSticker sendSticker);

    /**
     * Sends a file using the <a href="https://core.telegram.org/bots/api#sendaudio">Send Audio</a> method
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     */
    CompletableFuture<Message> executeAsync(SendAudio sendAudio);

    /**
     * Sends a voice note using the <a href="https://core.telegram.org/bots/api#sendvoice">Send Voice</a> method
     * For this to work, your audio must be in an .ogg file encoded with OPUS
     * @param sendVoice Information to send
     * @return If success, the sent Message is returned
     */
    CompletableFuture<Message> executeAsync(SendVoice sendVoice);

    /**
     * Send a media group
     * @return If success, list of generated messages
     * @see <a href="https://core.telegram.org/bots/api#sendMediaGroup">https://core.telegram.org/bots/api#sendMediaGroup</a>
     */
    CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup);

    /**
     * Send a paid media
     * @return If success, list of generated messages
     * @see <a href="https://core.telegram.org/bots/api#sendMediaGroup">https://core.telegram.org/bots/api#sendMediaGroup</a>
     */
    CompletableFuture<List<Message>> executeAsync(SendPaidMedia sendPaidMedia);

    /**
     * Set chat profile photo
     * @param setChatPhoto Information to set the photo
     * @return If success, true is returned
     * @see <a href="https://core.telegram.org/bots/api#setChatPhoto">https://core.telegram.org/bots/api#setChatPhoto</a>
     */
    CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto);

    /**
     * Adds a new sticker to a set
     * @param addStickerToSet Information of the sticker to set
     * @return If success, true is returned
     * @see <a href="https://core.telegram.org/bots/api#addStickerToSet">https://core.telegram.org/bots/api#addStickerToSet</a>
     */
    CompletableFuture<Boolean> executeAsync(AddStickerToSet addStickerToSet);

    /**
     * Replace a sticker in a set
     * @param replaceStickerInSet Information of the sticker to set
     * @return If success, true is returned
     * @see <a href="https://core.telegram.org/bots/api#replaceStickerInSet">https://core.telegram.org/bots/api#replaceStickerInSet</a>
     */
    CompletableFuture<Boolean> executeAsync(ReplaceStickerInSet replaceStickerInSet);

    /**
     * Set sticker set thumb
     * @param setStickerSetThumbnail Information of the sticker to set
     * @return If success, true is returned
     * @see <a href="https://core.telegram.org/bots/api#setStickerSetThumbail">https://core.telegram.org/bots/api#setStickerSetThumbail</a>
     */
    CompletableFuture<Boolean> executeAsync(SetStickerSetThumbnail setStickerSetThumbnail);

    /**
     * Creates a new sticker set
     * @param createNewStickerSet Information of the sticker set to create
     * @return If success, true is returned
     * @see <a href="https://core.telegram.org/bots/api#createNewStickerSet">https://core.telegram.org/bots/api#createNewStickerSet</a>
     */
    CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet);

    /**
     * Upload a new file as sticker
     * @param uploadStickerFile Information of the file to upload as sticker
     * @return If success, true is returned
     * @see <a href="https://core.telegram.org/bots/api#uploadStickerFile">https://core.telegram.org/bots/api#uploadStickerFile</a>
     */
    CompletableFuture<File> executeAsync(UploadStickerFile uploadStickerFile);

    /**
     * Edit media in a message
     * @param editMessageMedia Information of the new media
     * @return If the edited message is not an inline message, the edited Message is returned, otherwise True is returned
     */
    CompletableFuture<Serializable> executeAsync(EditMessageMedia editMessageMedia);

    /**
     * Send animation
     * @param sendAnimation Information of the animation
     * @return Sent message
     */
    CompletableFuture<Message> executeAsync(SendAnimation sendAnimation);

    CompletableFuture<java.io.File> downloadFileAsync(File file);

    default CompletableFuture<java.io.File> downloadFileAsync(String filePath) {
        return downloadFileAsync(new File(null, null, null, filePath));
    };

    CompletableFuture<InputStream> downloadFileAsStreamAsync(File file);

    default CompletableFuture<InputStream> downloadFileAsStreamAsync(String filePath) {
        return downloadFileAsStreamAsync(new File(null, null, null, filePath));
    }
}
