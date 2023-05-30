package org.telegram.abilitybots.api.sender;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.WebhookInfo;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.DownloadFileCallback;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;

/**
 * A sender interface that replicates {@link DefaultAbsSender} methods.
 *
 * @author Abbas Abou Daya
 */
public interface MessageSender {

  <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException;

  <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException;

  Boolean addStickerToSet(AddStickerToSet addStickerToSet) throws TelegramApiException;

  Boolean createNewStickerSet(CreateNewStickerSet createNewStickerSet) throws TelegramApiException;

  File uploadStickerFile(UploadStickerFile uploadStickerFile) throws TelegramApiException;

  Boolean setChatPhoto(SetChatPhoto setChatPhoto) throws TelegramApiException;

  java.io.File downloadFile(String path) throws TelegramApiException;

  void downloadFileAsync(String path, DownloadFileCallback<String> callback) throws TelegramApiException;

  java.io.File downloadFile(File file) throws TelegramApiException;

  void downloadFileAsync(File file, DownloadFileCallback<File> callback) throws TelegramApiException;

  User getMe() throws TelegramApiException;

  WebhookInfo getWebhookInfo() throws TelegramApiException;

  void getMeAsync(SentCallback<User> sentCallback) throws TelegramApiException;

  void getWebhookInfoAsync(SentCallback<WebhookInfo> sentCallback) throws TelegramApiException;

  Message sendDocument(SendDocument sendDocument) throws TelegramApiException;

  Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException;

  Message sendVideo(SendVideo sendVideo) throws TelegramApiException;

  Message sendAudio(SendAudio sendAudio) throws TelegramApiException;

  Message sendVoice(SendVoice sendVoice) throws TelegramApiException;

  Message sendVideoNote(SendVideoNote sendVideoNote) throws TelegramApiException;

  Message sendSticker(SendSticker sendSticker) throws TelegramApiException;
}
