package org.telegram.abilitybots.api.sender;

import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.api.methods.send.*;
import org.telegram.telegrambots.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.WebhookInfo;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.updateshandlers.DownloadFileCallback;
import org.telegram.telegrambots.updateshandlers.SentCallback;

import java.io.Serializable;

/**
 * The default implementation of the {@link MessageSender}. This serves as a proxy to the {@link DefaultAbsSender} methods.
 *
 * @author Abbas Abou Daya
 */
public class DefaultSender implements MessageSender {
  private static final String TAG = MessageSender.class.getName();

  private DefaultAbsSender bot;

  public DefaultSender(DefaultAbsSender bot) {
    this.bot = bot;
  }

  @Override
  public <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException {
    bot.executeAsync(method, callback);
  }

  @Override
  public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
    return bot.execute(method);
  }

  @Override
  public Boolean addStickerToSet(AddStickerToSet addStickerToSet) throws TelegramApiException {
    return bot.addStickerToSet(addStickerToSet);
  }

  @Override
  public Boolean createNewStickerSet(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
    return bot.createNewStickerSet(createNewStickerSet);
  }

  @Override
  public File uploadStickerFile(UploadStickerFile uploadStickerFile) throws TelegramApiException {
    return bot.uploadStickerFile(uploadStickerFile);
  }

  @Override
  public Boolean setChatPhoto(SetChatPhoto setChatPhoto) throws TelegramApiException {
    return bot.setChatPhoto(setChatPhoto);
  }

  @Override
  public java.io.File downloadFile(String path) throws TelegramApiException {
    return bot.downloadFile(path);
  }

  @Override
  public void downloadFileAsync(String path, DownloadFileCallback<String> callback) throws TelegramApiException {
    bot.downloadFileAsync(path, callback);
  }

  @Override
  public java.io.File downloadFile(File file) throws TelegramApiException {
    return bot.downloadFile(file);
  }

  @Override
  public void downloadFileAsync(File file, DownloadFileCallback<File> callback) throws TelegramApiException {
    bot.downloadFileAsync(file, callback);
  }

  @Override
  public User getMe() throws TelegramApiException {
    return bot.getMe();
  }

  @Override
  public WebhookInfo getWebhookInfo() throws TelegramApiException {
    return bot.getWebhookInfo();
  }


  @Override
  public void getMeAsync(SentCallback<User> sentCallback) throws TelegramApiException {
    bot.getMeAsync(sentCallback);
  }

  @Override
  public void getWebhookInfoAsync(SentCallback<WebhookInfo> sentCallback) throws TelegramApiException {
    bot.getWebhookInfoAsync(sentCallback);
  }

  @Override
  public Message sendDocument(SendDocument sendDocument) throws TelegramApiException {
    return bot.sendDocument(sendDocument);
  }

  @Override
  public Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
    return bot.sendPhoto(sendPhoto);
  }

  @Override
  public Message sendVideo(SendVideo sendVideo) throws TelegramApiException {
    return bot.sendVideo(sendVideo);
  }

  @Override
  public Message sendSticker(SendSticker sendSticker) throws TelegramApiException {
    return bot.sendSticker(sendSticker);
  }

  @Override
  public Message sendAudio(SendAudio sendAudio) throws TelegramApiException {
    return bot.sendAudio(sendAudio);
  }

  @Override
  public Message sendVoice(SendVoice sendVoice) throws TelegramApiException {
    return bot.sendVoice(sendVoice);
  }

  @Override
  public Message sendVideoNote(SendVideoNote sendVideoNote) throws TelegramApiException {
    return null;
  }
}
