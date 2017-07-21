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
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.updateshandlers.DownloadFileCallback;
import org.telegram.telegrambots.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A sender interface that replicates {@link DefaultAbsSender} methods.
 *
 * @author Abbas Abou Daya
 */
public interface MessageSender {
  Optional<Message> send(String message, long id);

  Optional<Message> sendMd(String message, long id);

  Optional<Message> forceReply(String message, long id);

  Boolean answerInlineQuery(AnswerInlineQuery answerInlineQuery) throws TelegramApiException;

  Boolean sendChatAction(SendChatAction sendChatAction) throws TelegramApiException;

  Message forwardMessage(ForwardMessage forwardMessage) throws TelegramApiException;

  Message sendLocation(SendLocation sendLocation) throws TelegramApiException;

  Message sendVenue(SendVenue sendVenue) throws TelegramApiException;

  Message sendContact(SendContact sendContact) throws TelegramApiException;

  Boolean kickMember(KickChatMember kickChatMember) throws TelegramApiException;

  Boolean unbanMember(UnbanChatMember unbanChatMember) throws TelegramApiException;

  Boolean leaveChat(LeaveChat leaveChat) throws TelegramApiException;

  Chat getChat(GetChat getChat) throws TelegramApiException;

  List<ChatMember> getChatAdministrators(GetChatAdministrators getChatAdministrators) throws TelegramApiException;

  ChatMember getChatMember(GetChatMember getChatMember) throws TelegramApiException;

  Integer getChatMemberCount(GetChatMemberCount getChatMemberCount) throws TelegramApiException;

  Boolean setChatPhoto(SetChatPhoto setChatPhoto) throws TelegramApiException;

  Boolean deleteChatPhoto(DeleteChatPhoto deleteChatPhoto) throws TelegramApiException;

  void deleteChatPhoto(DeleteChatPhoto deleteChatPhoto, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  Boolean pinChatMessage(PinChatMessage pinChatMessage) throws TelegramApiException;

  void pinChatMessage(PinChatMessage pinChatMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  Boolean unpinChatMessage(UnpinChatMessage unpinChatMessage) throws TelegramApiException;

  void unpinChatMessage(UnpinChatMessage unpinChatMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  Boolean promoteChatMember(PromoteChatMember promoteChatMember) throws TelegramApiException;

  void promoteChatMember(PromoteChatMember promoteChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  Boolean restrictChatMember(RestrictChatMember restrictChatMember) throws TelegramApiException;

  void restrictChatMember(RestrictChatMember restrictChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  Boolean setChatDescription(SetChatDescription setChatDescription) throws TelegramApiException;

  void setChatDescription(SetChatDescription setChatDescription, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  Boolean setChatTite(SetChatTitle setChatTitle) throws TelegramApiException;

  void setChatTite(SetChatTitle setChatTitle, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  String exportChatInviteLink(ExportChatInviteLink exportChatInviteLink) throws TelegramApiException;

  void exportChatInviteLinkAsync(ExportChatInviteLink exportChatInviteLink, SentCallback<String> sentCallback) throws TelegramApiException;

  Boolean deleteMessage(DeleteMessage deleteMessage) throws TelegramApiException;

  void deleteMessageAsync(DeleteMessage deleteMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  Serializable editMessageText(EditMessageText editMessageText) throws TelegramApiException;

  Serializable editMessageCaption(EditMessageCaption editMessageCaption) throws TelegramApiException;

  Serializable editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) throws TelegramApiException;

  Boolean answerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException;

  UserProfilePhotos getUserProfilePhotos(GetUserProfilePhotos getUserProfilePhotos) throws TelegramApiException;

  java.io.File downloadFile(String path) throws TelegramApiException;

  void downloadFileAsync(String path, DownloadFileCallback<String> callback) throws TelegramApiException;

  java.io.File downloadFile(File file) throws TelegramApiException;

  void downloadFileAsync(File file, DownloadFileCallback<File> callback) throws TelegramApiException;

  File getFile(GetFile getFile) throws TelegramApiException;

  User getMe() throws TelegramApiException;

  WebhookInfo getWebhookInfo() throws TelegramApiException;

  Serializable setGameScore(SetGameScore setGameScore) throws TelegramApiException;

  Serializable getGameHighScores(GetGameHighScores getGameHighScores) throws TelegramApiException;

  Message sendGame(SendGame sendGame) throws TelegramApiException;

  Boolean deleteWebhook(DeleteWebhook deleteWebhook) throws TelegramApiException;

  Message sendMessage(SendMessage sendMessage) throws TelegramApiException;

  void sendMessageAsync(SendMessage sendMessage, SentCallback<Message> sentCallback) throws TelegramApiException;

  void answerInlineQueryAsync(AnswerInlineQuery answerInlineQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  void sendChatActionAsync(SendChatAction sendChatAction, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  void forwardMessageAsync(ForwardMessage forwardMessage, SentCallback<Message> sentCallback) throws TelegramApiException;

  void sendLocationAsync(SendLocation sendLocation, SentCallback<Message> sentCallback) throws TelegramApiException;

  void sendVenueAsync(SendVenue sendVenue, SentCallback<Message> sentCallback) throws TelegramApiException;

  void sendContactAsync(SendContact sendContact, SentCallback<Message> sentCallback) throws TelegramApiException;

  void kickMemberAsync(KickChatMember kickChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  void unbanMemberAsync(UnbanChatMember unbanChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  void leaveChatAsync(LeaveChat leaveChat, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  void getChatAsync(GetChat getChat, SentCallback<Chat> sentCallback) throws TelegramApiException;

  void getChatAdministratorsAsync(GetChatAdministrators getChatAdministrators, SentCallback<ArrayList<ChatMember>> sentCallback) throws TelegramApiException;

  void getChatMemberAsync(GetChatMember getChatMember, SentCallback<ChatMember> sentCallback) throws TelegramApiException;

  void getChatMemberCountAsync(GetChatMemberCount getChatMemberCount, SentCallback<Integer> sentCallback) throws TelegramApiException;

  void editMessageTextAsync(EditMessageText editMessageText, SentCallback<Serializable> sentCallback) throws TelegramApiException;

  void editMessageCaptionAsync(EditMessageCaption editMessageCaption, SentCallback<Serializable> sentCallback) throws TelegramApiException;

  void editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup, SentCallback<Serializable> sentCallback) throws TelegramApiException;

  void answerCallbackQueryAsync(AnswerCallbackQuery answerCallbackQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  void getUserProfilePhotosAsync(GetUserProfilePhotos getUserProfilePhotos, SentCallback<UserProfilePhotos> sentCallback) throws TelegramApiException;

  void getFileAsync(GetFile getFile, SentCallback<File> sentCallback) throws TelegramApiException;

  void getMeAsync(SentCallback<User> sentCallback) throws TelegramApiException;

  void getWebhookInfoAsync(SentCallback<WebhookInfo> sentCallback) throws TelegramApiException;

  void setGameScoreAsync(SetGameScore setGameScore, SentCallback<Serializable> sentCallback) throws TelegramApiException;

  void getGameHighScoresAsync(GetGameHighScores getGameHighScores, SentCallback<ArrayList<GameHighScore>> sentCallback) throws TelegramApiException;

  void sendGameAsync(SendGame sendGame, SentCallback<Message> sentCallback) throws TelegramApiException;

  void deleteWebhook(DeleteWebhook deleteWebhook, SentCallback<Boolean> sentCallback) throws TelegramApiException;

  Message sendDocument(SendDocument sendDocument) throws TelegramApiException;

  Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException;

  Message sendVideo(SendVideo sendVideo) throws TelegramApiException;

  Message sendSticker(SendSticker sendSticker) throws TelegramApiException;

  Message sendAudio(SendAudio sendAudio) throws TelegramApiException;

  Message sendVoice(SendVoice sendVoice) throws TelegramApiException;
}
