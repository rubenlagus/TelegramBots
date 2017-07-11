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
    return bot.answerInlineQuery(answerInlineQuery);
  }

  @Override
  public Boolean sendChatAction(SendChatAction sendChatAction) throws TelegramApiException {
    return bot.sendChatAction(sendChatAction);
  }

  @Override
  public Message forwardMessage(ForwardMessage forwardMessage) throws TelegramApiException {
    return bot.forwardMessage(forwardMessage);
  }

  @Override
  public Message sendLocation(SendLocation sendLocation) throws TelegramApiException {
    return bot.sendLocation(sendLocation);
  }

  @Override
  public Message sendVenue(SendVenue sendVenue) throws TelegramApiException {
    return bot.sendVenue(sendVenue);
  }

  @Override
  public Message sendContact(SendContact sendContact) throws TelegramApiException {
    return bot.sendContact(sendContact);
  }

  @Override
  public Boolean kickMember(KickChatMember kickChatMember) throws TelegramApiException {
    return bot.kickMember(kickChatMember);
  }

  @Override
  public Boolean unbanMember(UnbanChatMember unbanChatMember) throws TelegramApiException {
    return bot.unbanMember(unbanChatMember);
  }

  @Override
  public Boolean leaveChat(LeaveChat leaveChat) throws TelegramApiException {
    return bot.leaveChat(leaveChat);
  }

  @Override
  public Chat getChat(GetChat getChat) throws TelegramApiException {
    return bot.getChat(getChat);
  }

  @Override
  public List<ChatMember> getChatAdministrators(GetChatAdministrators getChatAdministrators) throws TelegramApiException {
    return bot.getChatAdministrators(getChatAdministrators);
  }

  @Override
  public ChatMember getChatMember(GetChatMember getChatMember) throws TelegramApiException {
    return bot.getChatMember(getChatMember);
  }

  @Override
  public Integer getChatMemberCount(GetChatMemberCount getChatMemberCount) throws TelegramApiException {
    return bot.getChatMemberCount(getChatMemberCount);
  }

  @Override
  public Boolean setChatPhoto(SetChatPhoto setChatPhoto) throws TelegramApiException {
    return bot.setChatPhoto(setChatPhoto);
  }

  @Override
  public Boolean deleteChatPhoto(DeleteChatPhoto deleteChatPhoto) throws TelegramApiException {
    return bot.deleteChatPhoto(deleteChatPhoto);
  }

  @Override
  public void deleteChatPhoto(DeleteChatPhoto deleteChatPhoto, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.deleteChatPhoto(deleteChatPhoto, sentCallback);
  }

  @Override
  public Boolean pinChatMessage(PinChatMessage pinChatMessage) throws TelegramApiException {
    return bot.pinChatMessage(pinChatMessage);
  }

  @Override
  public void pinChatMessage(PinChatMessage pinChatMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.pinChatMessage(pinChatMessage, sentCallback);
  }

  @Override
  public Boolean unpinChatMessage(UnpinChatMessage unpinChatMessage) throws TelegramApiException {
    return bot.unpinChatMessage(unpinChatMessage);
  }

  @Override
  public void unpinChatMessage(UnpinChatMessage unpinChatMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.unpinChatMessage(unpinChatMessage, sentCallback);
  }

  @Override
  public Boolean promoteChatMember(PromoteChatMember promoteChatMember) throws TelegramApiException {
    return bot.promoteChatMember(promoteChatMember);
  }

  @Override
  public void promoteChatMember(PromoteChatMember promoteChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.promoteChatMember(promoteChatMember, sentCallback);
  }

  @Override
  public Boolean restrictChatMember(RestrictChatMember restrictChatMember) throws TelegramApiException {
    return bot.restrictChatMember(restrictChatMember);
  }

  @Override
  public void restrictChatMember(RestrictChatMember restrictChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.restrictChatMember(restrictChatMember, sentCallback);
  }

  @Override
  public Boolean setChatDescription(SetChatDescription setChatDescription) throws TelegramApiException {
    return bot.setChatDescription(setChatDescription);
  }

  @Override
  public void setChatDescription(SetChatDescription setChatDescription, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.setChatDescription(setChatDescription, sentCallback);
  }

  @Override
  public Boolean setChatTite(SetChatTitle setChatTitle) throws TelegramApiException {
    return bot.setChatTitle(setChatTitle);
  }

  @Override
  public void setChatTite(SetChatTitle setChatTitle, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.setChatTitle(setChatTitle, sentCallback);
  }

  @Override
  public String exportChatInviteLink(ExportChatInviteLink exportChatInviteLink) throws TelegramApiException {
    return bot.exportChatInviteLink(exportChatInviteLink);
  }

  @Override
  public void exportChatInviteLinkAsync(ExportChatInviteLink exportChatInviteLink, SentCallback<String> sentCallback) throws TelegramApiException {
    bot.exportChatInviteLinkAsync(exportChatInviteLink, sentCallback);
  }

  @Override
  public Boolean deleteMessage(DeleteMessage deleteMessage) throws TelegramApiException {
    return bot.deleteMessage(deleteMessage);
  }

  @Override
  public void deleteMessageAsync(DeleteMessage deleteMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.deleteMessage(deleteMessage, sentCallback);
  }

  @Override
  public Serializable editMessageText(EditMessageText editMessageText) throws TelegramApiException {
    return bot.editMessageText(editMessageText);
  }

  @Override
  public Serializable editMessageCaption(EditMessageCaption editMessageCaption) throws TelegramApiException {
    return bot.editMessageCaption(editMessageCaption);
  }

  @Override
  public Serializable editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) throws TelegramApiException {
    return bot.editMessageReplyMarkup(editMessageReplyMarkup);
  }

  @Override
  public Boolean answerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException {
    return bot.answerCallbackQuery(answerCallbackQuery);
  }

  @Override
  public UserProfilePhotos getUserProfilePhotos(GetUserProfilePhotos getUserProfilePhotos) throws TelegramApiException {
    return bot.getUserProfilePhotos(getUserProfilePhotos);
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
    return bot.getFile(getFile);
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
    return bot.setGameScore(setGameScore);
  }

  @Override
  public Serializable getGameHighScores(GetGameHighScores getGameHighScores) throws TelegramApiException {
    return bot.getGameHighScores(getGameHighScores);
  }

  @Override
  public Message sendGame(SendGame sendGame) throws TelegramApiException {
    return bot.sendGame(sendGame);
  }

  @Override
  public Boolean deleteWebhook(DeleteWebhook deleteWebhook) throws TelegramApiException {
    return bot.deleteWebhook(deleteWebhook);
  }

  @Override
  public Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
    return bot.sendMessage(sendMessage);
  }

  @Override
  public void sendMessageAsync(SendMessage sendMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.sendMessageAsync(sendMessage, sentCallback);
  }

  @Override
  public void answerInlineQueryAsync(AnswerInlineQuery answerInlineQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.answerInlineQueryAsync(answerInlineQuery, sentCallback);
  }

  @Override
  public void sendChatActionAsync(SendChatAction sendChatAction, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.sendChatActionAsync(sendChatAction, sentCallback);
  }

  @Override
  public void forwardMessageAsync(ForwardMessage forwardMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.forwardMessageAsync(forwardMessage, sentCallback);
  }

  @Override
  public void sendLocationAsync(SendLocation sendLocation, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.sendLocationAsync(sendLocation, sentCallback);
  }

  @Override
  public void sendVenueAsync(SendVenue sendVenue, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.sendVenueAsync(sendVenue, sentCallback);
  }

  @Override
  public void sendContactAsync(SendContact sendContact, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.sendContactAsync(sendContact, sentCallback);
  }

  @Override
  public void kickMemberAsync(KickChatMember kickChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.kickMemberAsync(kickChatMember, sentCallback);
  }

  @Override
  public void unbanMemberAsync(UnbanChatMember unbanChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.unbanMemberAsync(unbanChatMember, sentCallback);
  }

  @Override
  public void leaveChatAsync(LeaveChat leaveChat, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.leaveChatAsync(leaveChat, sentCallback);
  }

  @Override
  public void getChatAsync(GetChat getChat, SentCallback<Chat> sentCallback) throws TelegramApiException {
    bot.getChatAsync(getChat, sentCallback);
  }

  @Override
  public void getChatAdministratorsAsync(GetChatAdministrators getChatAdministrators, SentCallback<ArrayList<ChatMember>> sentCallback) throws TelegramApiException {
    bot.getChatAdministratorsAsync(getChatAdministrators, sentCallback);
  }

  @Override
  public void getChatMemberAsync(GetChatMember getChatMember, SentCallback<ChatMember> sentCallback) throws TelegramApiException {
    bot.getChatMemberAsync(getChatMember, sentCallback);
  }

  @Override
  public void getChatMemberCountAsync(GetChatMemberCount getChatMemberCount, SentCallback<Integer> sentCallback) throws TelegramApiException {
    bot.getChatMemberCountAsync(getChatMemberCount, sentCallback);
  }

  @Override
  public void editMessageTextAsync(EditMessageText editMessageText, SentCallback<Serializable> sentCallback) throws TelegramApiException {
    bot.editMessageTextAsync(editMessageText, sentCallback);
  }

  @Override
  public void editMessageCaptionAsync(EditMessageCaption editMessageCaption, SentCallback<Serializable> sentCallback) throws TelegramApiException {
    bot.editMessageCaptionAsync(editMessageCaption, sentCallback);
  }

  @Override
  public void editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup, SentCallback<Serializable> sentCallback) throws TelegramApiException {
    bot.editMessageReplyMarkup(editMessageReplyMarkup, sentCallback);
  }

  @Override
  public void answerCallbackQueryAsync(AnswerCallbackQuery answerCallbackQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.answerCallbackQueryAsync(answerCallbackQuery, sentCallback);
  }

  @Override
  public void getUserProfilePhotosAsync(GetUserProfilePhotos getUserProfilePhotos, SentCallback<UserProfilePhotos> sentCallback) throws TelegramApiException {
    bot.getUserProfilePhotosAsync(getUserProfilePhotos, sentCallback);
  }

  @Override
  public void getFileAsync(GetFile getFile, SentCallback<File> sentCallback) throws TelegramApiException {
    bot.getFileAsync(getFile, sentCallback);
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
    bot.setGameScoreAsync(setGameScore, sentCallback);
  }

  @Override
  public void getGameHighScoresAsync(GetGameHighScores getGameHighScores, SentCallback<ArrayList<GameHighScore>> sentCallback) throws TelegramApiException {
    bot.getGameHighScoresAsync(getGameHighScores, sentCallback);
  }

  @Override
  public void sendGameAsync(SendGame sendGame, SentCallback<Message> sentCallback) throws TelegramApiException {
    bot.sendGameAsync(sendGame, sentCallback);
  }

  @Override
  public void deleteWebhook(DeleteWebhook deleteWebhook, SentCallback<Boolean> sentCallback) throws TelegramApiException {
    bot.deleteWebhook(deleteWebhook, sentCallback);
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
