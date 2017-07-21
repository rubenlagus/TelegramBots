package org.telegram.abilitybots.api.sender;

import org.telegram.telegrambots.api.methods.*;
import org.telegram.telegrambots.api.methods.games.GetGameHighScores;
import org.telegram.telegrambots.api.methods.games.SetGameScore;
import org.telegram.telegrambots.api.methods.groupadministration.*;
import org.telegram.telegrambots.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.api.methods.pinnedmessages.UnpinChatMessage;
import org.telegram.telegrambots.api.methods.send.*;
import org.telegram.telegrambots.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.*;
import org.telegram.telegrambots.api.objects.games.GameHighScore;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.updateshandlers.DownloadFileCallback;
import org.telegram.telegrambots.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * The default implementation of the {@link MessageSender}. This serves as a proxy to the {@link DefaultAbsSender} methods.
 * <p>Most of the methods below will be directly calling the bot's similar functions. However, there are some methods introduced to ease sending messages such as:</p>
 * <ol>
 * <li>{@link DefaultMessageSender#sendMd(String, long)} - with markdown</li>
 * <li>{@link DefaultMessageSender#send(String, long)} - without markdown</li>
 * </ol>
 *
 * @author Abbas Abou Daya
 */
public class DefaultMessageSender implements MessageSender {
  private static final String TAG = MessageSender.class.getName();

  private DefaultAbsSender bot;

  public DefaultMessageSender(DefaultAbsSender bot) {
    this.bot = bot;
  }

  @Override
  public Optional<Message> send(String message, long id) {
    return doSendMessage(message, id, false);
  }

  @Override
  public Optional<Message> sendMd(String message, long id) {
    return doSendMessage(message, id, true);
  }

  @Override
  public Optional<Message> forceReply(String message, long id) {
    SendMessage msg = new SendMessage();
    msg.setText(message);
    msg.setChatId(id);
    msg.setReplyMarkup(new ForceReplyKeyboard());

    return optionalSendMessage(msg);
  }

  @Override
  public Boolean answerInlineQuery(AnswerInlineQuery answerInlineQuery) throws TelegramApiException {
    return bot.execute(answerInlineQuery);
  }

  @Override
  public Boolean sendChatAction(SendChatAction sendChatAction) throws TelegramApiException {
    return bot.execute(sendChatAction);
  }

  @Override
  public Message forwardMessage(ForwardMessage forwardMessage) throws TelegramApiException {
    return bot.execute(forwardMessage);
  }

  @Override
  public Message sendLocation(SendLocation sendLocation) throws TelegramApiException {
    return bot.execute(sendLocation);
  }

  @Override
  public Message sendVenue(SendVenue sendVenue) throws TelegramApiException {
    return bot.execute(sendVenue);
  }

  @Override
  public Message sendContact(SendContact sendContact) throws TelegramApiException {
    return bot.execute(sendContact);
  }

  @Override
  public Boolean kickMember(KickChatMember kickChatMember) throws TelegramApiException {
    return bot.execute(kickChatMember);
  }

  @Override
  public Boolean unbanMember(UnbanChatMember unbanChatMember) throws TelegramApiException {
    return bot.execute(unbanChatMember);
  }

  @Override
  public Boolean leaveChat(LeaveChat leaveChat) throws TelegramApiException {
    return bot.execute(leaveChat);
  }

  @Override
  public Chat getChat(GetChat getChat) throws TelegramApiException {
    return bot.execute(getChat);
  }

  @Override
  public List<ChatMember> getChatAdministrators(GetChatAdministrators getChatAdministrators) throws TelegramApiException {
    return bot.execute(getChatAdministrators);
  }

  @Override
  public ChatMember getChatMember(GetChatMember getChatMember) throws TelegramApiException {
    return bot.execute(getChatMember);
  }

  @Override
  public Integer getChatMemberCount(GetChatMemberCount getChatMemberCount) throws TelegramApiException {
    return bot.execute(getChatMemberCount);
  }

  @Override
  public Boolean setChatPhoto(SetChatPhoto setChatPhoto) throws TelegramApiException {
    return bot.setChatPhoto(setChatPhoto);
  }

  @Override
  public Boolean deleteChatPhoto(DeleteChatPhoto deleteChatPhoto) throws TelegramApiException {
    return bot.execute(deleteChatPhoto);
  }

  @Override
  public void deleteChatPhoto(DeleteChatPhoto deleteChatPhoto, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(deleteChatPhoto, sentCallback);
  }

  @Override
  public Boolean pinChatMessage(PinChatMessage pinChatMessage) throws TelegramApiException {
    return bot.execute(pinChatMessage);
  }

  @Override
  public void pinChatMessage(PinChatMessage pinChatMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(pinChatMessage, sentCallback);
  }

  @Override
  public Boolean unpinChatMessage(UnpinChatMessage unpinChatMessage) throws TelegramApiException {
    return bot.execute(unpinChatMessage);
  }

  @Override
  public void unpinChatMessage(UnpinChatMessage unpinChatMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(unpinChatMessage, sentCallback);
  }

  @Override
  public Boolean promoteChatMember(PromoteChatMember promoteChatMember) throws TelegramApiException {
    return bot.execute(promoteChatMember);
  }

  @Override
  public void promoteChatMember(PromoteChatMember promoteChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(promoteChatMember, sentCallback);
  }

  @Override
  public Boolean restrictChatMember(RestrictChatMember restrictChatMember) throws TelegramApiException {
    return bot.execute(restrictChatMember);
  }

  @Override
  public void restrictChatMember(RestrictChatMember restrictChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(restrictChatMember, sentCallback);
  }

  @Override
  public Boolean setChatDescription(SetChatDescription setChatDescription) throws TelegramApiException {
    return bot.execute(setChatDescription);
  }

  @Override
  public void setChatDescription(SetChatDescription setChatDescription, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(setChatDescription, sentCallback);
  }

  @Override
  public Boolean setChatTite(SetChatTitle setChatTitle) throws TelegramApiException {
    return bot.execute(setChatTitle);
  }

  @Override
  public void setChatTite(SetChatTitle setChatTitle, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(setChatTitle, sentCallback);
  }

  @Override
  public String exportChatInviteLink(ExportChatInviteLink exportChatInviteLink) throws TelegramApiException {
    return bot.execute(exportChatInviteLink);
  }

  @Override
  public void exportChatInviteLinkAsync(ExportChatInviteLink exportChatInviteLink, SentCallback<String> sentCallback) throws TelegramApiException {
    bot.executeAsync(exportChatInviteLink, sentCallback);
  }

  @Override
  public Boolean deleteMessage(DeleteMessage deleteMessage) throws TelegramApiException {
    return bot.execute(deleteMessage);
  }

  @Override
  public void deleteMessageAsync(DeleteMessage deleteMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(deleteMessage, sentCallback);
  }

  @Override
  public Serializable editMessageText(EditMessageText editMessageText) throws TelegramApiException {
    return bot.execute(editMessageText);
  }

  @Override
  public Serializable editMessageCaption(EditMessageCaption editMessageCaption) throws TelegramApiException {
    return bot.execute(editMessageCaption);
  }

  @Override
  public Serializable editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) throws TelegramApiException {
    return bot.execute(editMessageReplyMarkup);
  }

  @Override
  public Boolean answerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException {
    return bot.execute(answerCallbackQuery);
  }

  @Override
  public UserProfilePhotos getUserProfilePhotos(GetUserProfilePhotos getUserProfilePhotos) throws TelegramApiException {
    return bot.execute(getUserProfilePhotos);
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
  public File getFile(GetFile getFile) throws TelegramApiException {
    return bot.execute(getFile);
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
  public Serializable setGameScore(SetGameScore setGameScore) throws TelegramApiException {
    return bot.execute(setGameScore);
  }

  @Override
  public Serializable getGameHighScores(GetGameHighScores getGameHighScores) throws TelegramApiException {
    return bot.execute(getGameHighScores);
  }

  @Override
  public Message sendGame(SendGame sendGame) throws TelegramApiException {
    return bot.execute(sendGame);
  }

  @Override
  public Boolean deleteWebhook(DeleteWebhook deleteWebhook) throws TelegramApiException {
    return bot.execute(deleteWebhook);
  }

  @Override
  public Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
    return bot.execute(sendMessage);
  }

  @Override
  public void sendMessageAsync(SendMessage sendMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.executeAsync(sendMessage, sentCallback);
  }

  @Override
  public void answerInlineQueryAsync(AnswerInlineQuery answerInlineQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(answerInlineQuery, sentCallback);
  }

  @Override
  public void sendChatActionAsync(SendChatAction sendChatAction, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(sendChatAction, sentCallback);
  }

  @Override
  public void forwardMessageAsync(ForwardMessage forwardMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.executeAsync(forwardMessage, sentCallback);
  }

  @Override
  public void sendLocationAsync(SendLocation sendLocation, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.executeAsync(sendLocation, sentCallback);
  }

  @Override
  public void sendVenueAsync(SendVenue sendVenue, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.executeAsync(sendVenue, sentCallback);
  }

  @Override
  public void sendContactAsync(SendContact sendContact, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.executeAsync(sendContact, sentCallback);
  }

  @Override
  public void kickMemberAsync(KickChatMember kickChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(kickChatMember, sentCallback);
  }

  @Override
  public void unbanMemberAsync(UnbanChatMember unbanChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(unbanChatMember, sentCallback);
  }

  @Override
  public void leaveChatAsync(LeaveChat leaveChat, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(leaveChat, sentCallback);
  }

  @Override
  public void getChatAsync(GetChat getChat, SentCallback<Chat> sentCallback) throws TelegramApiException {
    bot.executeAsync(getChat, sentCallback);
  }

  @Override
  public void getChatAdministratorsAsync(GetChatAdministrators getChatAdministrators, SentCallback<ArrayList<ChatMember>> sentCallback) throws TelegramApiException {
    bot.executeAsync(getChatAdministrators, sentCallback);
  }

  @Override
  public void getChatMemberAsync(GetChatMember getChatMember, SentCallback<ChatMember> sentCallback) throws TelegramApiException {
    bot.executeAsync(getChatMember, sentCallback);
  }

  @Override
  public void getChatMemberCountAsync(GetChatMemberCount getChatMemberCount, SentCallback<Integer> sentCallback) throws TelegramApiException {
    bot.executeAsync(getChatMemberCount, sentCallback);
  }

  @Override
  public void editMessageTextAsync(EditMessageText editMessageText, SentCallback<Serializable> sentCallback) throws TelegramApiException {
    bot.executeAsync(editMessageText, sentCallback);
  }

  @Override
  public void editMessageCaptionAsync(EditMessageCaption editMessageCaption, SentCallback<Serializable> sentCallback) throws TelegramApiException {
    bot.executeAsync(editMessageCaption, sentCallback);
  }

  @Override
  public void editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup, SentCallback<Serializable> sentCallback) throws TelegramApiException {
    bot.executeAsync(editMessageReplyMarkup, sentCallback);
  }

  @Override
  public void answerCallbackQueryAsync(AnswerCallbackQuery answerCallbackQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(answerCallbackQuery, sentCallback);
  }

  @Override
  public void getUserProfilePhotosAsync(GetUserProfilePhotos getUserProfilePhotos, SentCallback<UserProfilePhotos> sentCallback) throws TelegramApiException {
    bot.executeAsync(getUserProfilePhotos, sentCallback);
  }

  @Override
  public void getFileAsync(GetFile getFile, SentCallback<File> sentCallback) throws TelegramApiException {
    bot.executeAsync(getFile, sentCallback);
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
  public void setGameScoreAsync(SetGameScore setGameScore, SentCallback<Serializable> sentCallback) throws TelegramApiException {
    bot.executeAsync(setGameScore, sentCallback);
  }

  @Override
  public void getGameHighScoresAsync(GetGameHighScores getGameHighScores, SentCallback<ArrayList<GameHighScore>> sentCallback) throws TelegramApiException {
    bot.executeAsync(getGameHighScores, sentCallback);
  }

  @Override
  public void sendGameAsync(SendGame sendGame, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.executeAsync(sendGame, sentCallback);
  }

  @Override
  public void deleteWebhook(DeleteWebhook deleteWebhook, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.executeAsync(deleteWebhook, sentCallback);
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

  private Optional<Message> doSendMessage(String txt, long groupId, boolean format) {
    SendMessage smsg = new SendMessage();
    smsg.setChatId(groupId);
    smsg.setText(txt);
    smsg.enableMarkdown(format);

    return optionalSendMessage(smsg);
  }

  private Optional<Message> optionalSendMessage(SendMessage smsg) {
    try {
      return ofNullable(sendMessage(smsg));
    } catch (TelegramApiException e) {
      BotLogger.error("Could not send message", TAG, e);
      return empty();
    }
  }
}
