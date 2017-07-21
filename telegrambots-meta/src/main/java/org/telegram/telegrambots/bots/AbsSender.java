package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.api.methods.*;
import org.telegram.telegrambots.api.methods.games.GetGameHighScores;
import org.telegram.telegrambots.api.methods.games.SetGameScore;
import org.telegram.telegrambots.api.methods.groupadministration.*;
import org.telegram.telegrambots.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.api.methods.pinnedmessages.UnpinChatMessage;
import org.telegram.telegrambots.api.methods.send.*;
import org.telegram.telegrambots.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.api.methods.updates.GetWebhookInfo;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.*;
import org.telegram.telegrambots.api.objects.games.GameHighScore;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.ArrayList;
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

    @Deprecated
    public final Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        }

        return sendApiMethod(sendMessage);
    }

    @Deprecated
    public final Boolean answerInlineQuery(AnswerInlineQuery answerInlineQuery) throws TelegramApiException {
        if (answerInlineQuery == null) {
            throw new TelegramApiException("Parameter answerInlineQuery can not be null");
        }

        return sendApiMethod(answerInlineQuery);
    }

    @Deprecated
    public final Boolean sendChatAction(SendChatAction sendChatAction) throws TelegramApiException {
        if (sendChatAction == null) {
            throw new TelegramApiException("Parameter sendChatAction can not be null");
        }

        return sendApiMethod(sendChatAction);
    }

    @Deprecated
    public final Message forwardMessage(ForwardMessage forwardMessage) throws TelegramApiException {
        if (forwardMessage == null) {
            throw new TelegramApiException("Parameter forwardMessage can not be null");
        }

        return sendApiMethod(forwardMessage);
    }

    @Deprecated
    public final Message sendLocation(SendLocation sendLocation) throws TelegramApiException {
        if (sendLocation == null) {
            throw new TelegramApiException("Parameter sendLocation can not be null");
        }

        return sendApiMethod(sendLocation);
    }

    @Deprecated
    public final Message sendVenue(SendVenue sendVenue) throws TelegramApiException {
        if (sendVenue == null) {
            throw new TelegramApiException("Parameter sendVenue can not be null");
        }

        return sendApiMethod(sendVenue);
    }

    @Deprecated
    public final Message sendContact(SendContact sendContact) throws TelegramApiException {
        if (sendContact == null) {
            throw new TelegramApiException("Parameter sendContact can not be null");
        }

        return sendApiMethod(sendContact);
    }

    @Deprecated
    public final Boolean kickMember(KickChatMember kickChatMember) throws TelegramApiException {
        if (kickChatMember == null) {
            throw new TelegramApiException("Parameter kickChatMember can not be null");
        }
        return sendApiMethod(kickChatMember);
    }

    @Deprecated
    public final Boolean unbanMember(UnbanChatMember unbanChatMember) throws TelegramApiException {
        if (unbanChatMember == null) {
            throw new TelegramApiException("Parameter unbanChatMember can not be null");
        }
        return sendApiMethod(unbanChatMember);
    }

    @Deprecated
    public final Boolean leaveChat(LeaveChat leaveChat) throws TelegramApiException {
        if (leaveChat == null) {
            throw new TelegramApiException("Parameter leaveChat can not be null");
        }
        return sendApiMethod(leaveChat);
    }

    @Deprecated
    public final Chat getChat(GetChat getChat) throws TelegramApiException {
        if (getChat == null) {
            throw new TelegramApiException("Parameter getChat can not be null");
        }
        return sendApiMethod(getChat);
    }

    @Deprecated
    public final String exportChatInviteLink(ExportChatInviteLink exportChatInviteLink) throws TelegramApiException {
        if (exportChatInviteLink == null) {
            throw new TelegramApiException("Parameter exportChatInviteLink can not be null");
        }
        return sendApiMethod(exportChatInviteLink);
    }

    @Deprecated
    public final List<ChatMember> getChatAdministrators(GetChatAdministrators getChatAdministrators) throws TelegramApiException {
        if (getChatAdministrators == null) {
            throw new TelegramApiException("Parameter getChatAdministrators can not be null");
        }
        return sendApiMethod(getChatAdministrators);
    }

    @Deprecated
    public final ChatMember getChatMember(GetChatMember getChatMember) throws TelegramApiException {
        if (getChatMember == null) {
            throw new TelegramApiException("Parameter getChatMember can not be null");
        }
        return sendApiMethod(getChatMember);
    }

    @Deprecated
    public final Integer getChatMemberCount(GetChatMemberCount getChatMemberCount) throws TelegramApiException {
        if (getChatMemberCount == null) {
            throw new TelegramApiException("Parameter getChatMemberCount can not be null");
        }
        return sendApiMethod(getChatMemberCount);
    }

    @Deprecated
    public final Serializable editMessageText(EditMessageText editMessageText) throws TelegramApiException {
        if (editMessageText == null) {
            throw new TelegramApiException("Parameter editMessageText can not be null");
        }
        return sendApiMethod(editMessageText);
    }

    @Deprecated
    public final Serializable editMessageCaption(EditMessageCaption editMessageCaption) throws TelegramApiException {
        if (editMessageCaption == null) {
            throw new TelegramApiException("Parameter editMessageCaption can not be null");
        }
        return sendApiMethod(editMessageCaption);
    }

    @Deprecated
    public final Serializable editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) throws TelegramApiException {
        if (editMessageReplyMarkup == null) {
            throw new TelegramApiException("Parameter editMessageReplyMarkup can not be null");
        }
        return sendApiMethod(editMessageReplyMarkup);
    }

    @Deprecated
    public final Boolean answerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException {
        if (answerCallbackQuery == null) {
            throw new TelegramApiException("Parameter answerCallbackQuery can not be null");
        }
        return sendApiMethod(answerCallbackQuery);
    }

    @Deprecated
    public final UserProfilePhotos getUserProfilePhotos(GetUserProfilePhotos getUserProfilePhotos) throws TelegramApiException {
        if (getUserProfilePhotos == null) {
            throw new TelegramApiException("Parameter getUserProfilePhotos can not be null");
        }

        return sendApiMethod(getUserProfilePhotos);
    }

    @Deprecated
    public final File getFile(GetFile getFile) throws TelegramApiException {
        if(getFile == null){
            throw new TelegramApiException("Parameter getFile can not be null");
        }
        else if(getFile.getFileId() == null){
            throw new TelegramApiException("Attribute file_id in parameter getFile can not be null");
        }
        return sendApiMethod(getFile);
    }

    public final User getMe() throws TelegramApiException {
        return sendApiMethod(new GetMe());
    }

    public final WebhookInfo getWebhookInfo() throws TelegramApiException {
        GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
        return sendApiMethod(getWebhookInfo);
    }

    @Deprecated
    public final Serializable setGameScore(SetGameScore setGameScore) throws TelegramApiException {
        if(setGameScore == null){
            throw new TelegramApiException("Parameter setGameScore can not be null");
        }
        return sendApiMethod(setGameScore);
    }

    @Deprecated
    public final Serializable getGameHighScores(GetGameHighScores getGameHighScores) throws TelegramApiException {
        if(getGameHighScores == null){
            throw new TelegramApiException("Parameter getGameHighScores can not be null");
        }
        return sendApiMethod(getGameHighScores);
    }

    @Deprecated
    public final Message sendGame(SendGame sendGame) throws TelegramApiException {
        if(sendGame == null){
            throw new TelegramApiException("Parameter sendGame can not be null");
        }
        return sendApiMethod(sendGame);
    }

    @Deprecated
    public final Boolean deleteWebhook(DeleteWebhook deleteWebhook) throws TelegramApiException {
        if(deleteWebhook == null){
            throw new TelegramApiException("Parameter deleteWebhook can not be null");
        }
        return sendApiMethod(deleteWebhook);
    }

    @Deprecated
    public final Message sendInvoice(SendInvoice sendInvoice) throws TelegramApiException {
        if(sendInvoice == null){
            throw new TelegramApiException("Parameter sendInvoice can not be null");
        }
        return sendApiMethod(sendInvoice);
    }

    @Deprecated
    public final Boolean answerShippingQuery(AnswerShippingQuery answerShippingQuery) throws TelegramApiException {
        if(answerShippingQuery == null){
            throw new TelegramApiException("Parameter answerShippingQuery can not be null");
        }
        return sendApiMethod(answerShippingQuery);
    }

    @Deprecated
    public final Boolean answerPreCheckoutQuery(AnswerPreCheckoutQuery answerPreCheckoutQuery) throws TelegramApiException {
        if(answerPreCheckoutQuery == null){
            throw new TelegramApiException("Parameter answerPreCheckoutQuery can not be null");
        }
        return sendApiMethod(answerPreCheckoutQuery);
    }

    @Deprecated
    public final Boolean deleteMessage(DeleteMessage deleteMessage) throws TelegramApiException {
        if(deleteMessage == null){
            throw new TelegramApiException("Parameter deleteMessage can not be null");
        }
        return sendApiMethod(deleteMessage);
    }

    @Deprecated
    public final Boolean deleteChatPhoto(DeleteChatPhoto deleteChatPhoto) throws TelegramApiException {
        if(deleteChatPhoto == null){
            throw new TelegramApiException("Parameter deleteChatPhoto can not be null");
        }
        return sendApiMethod(deleteChatPhoto);
    }

    @Deprecated
    public final Boolean pinChatMessage(PinChatMessage pinChatMessage) throws TelegramApiException {
        if(pinChatMessage == null){
            throw new TelegramApiException("Parameter pinChatMessage can not be null");
        }
        return sendApiMethod(pinChatMessage);
    }

    @Deprecated
    public final Boolean unpinChatMessage(UnpinChatMessage unpinChatMessage) throws TelegramApiException {
        if(unpinChatMessage == null){
            throw new TelegramApiException("Parameter unpinChatMessage can not be null");
        }
        return sendApiMethod(unpinChatMessage);
    }

    @Deprecated
    public final Boolean promoteChatMember(PromoteChatMember promoteChatMember) throws TelegramApiException {
        if(promoteChatMember == null){
            throw new TelegramApiException("Parameter promoteChatMember can not be null");
        }
        return sendApiMethod(promoteChatMember);
    }

    @Deprecated
    public final Boolean restrictChatMember(RestrictChatMember restrictChatMember) throws TelegramApiException {
        if(restrictChatMember == null){
            throw new TelegramApiException("Parameter restrictChatMember can not be null");
        }
        return sendApiMethod(restrictChatMember);
    }

    @Deprecated
    public final Boolean setChatDescription(SetChatDescription setChatDescription) throws TelegramApiException {
        if(setChatDescription == null){
            throw new TelegramApiException("Parameter setChatDescription can not be null");
        }
        return sendApiMethod(setChatDescription);
    }

    @Deprecated
    public final Boolean setChatTitle(SetChatTitle setChatTitle) throws TelegramApiException {
        if(setChatTitle == null){
            throw new TelegramApiException("Parameter setChatTitle can not be null");
        }
        return sendApiMethod(setChatTitle);
    }

    // Send Requests Async

    @Deprecated
    public final void sendMessageAsync(SendMessage sendMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendMessage, sentCallback);
    }

    @Deprecated
    public final void answerInlineQueryAsync(AnswerInlineQuery answerInlineQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerInlineQuery == null) {
            throw new TelegramApiException("Parameter answerInlineQuery can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(answerInlineQuery, sentCallback);
    }

    @Deprecated
    public final void sendChatActionAsync(SendChatAction sendChatAction, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (sendChatAction == null) {
            throw new TelegramApiException("Parameter sendChatAction can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendChatAction, sentCallback);
    }

    @Deprecated
    public final void forwardMessageAsync(ForwardMessage forwardMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (forwardMessage == null) {
            throw new TelegramApiException("Parameter forwardMessage can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(forwardMessage, sentCallback);
    }

    @Deprecated
    public final void sendLocationAsync(SendLocation sendLocation, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendLocation == null) {
            throw new TelegramApiException("Parameter sendLocation can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendLocation, sentCallback);
    }

    @Deprecated
    public final void sendVenueAsync(SendVenue sendVenue, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendVenue == null) {
            throw new TelegramApiException("Parameter sendVenue can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendVenue, sentCallback);
    }

    @Deprecated
    public final void sendContactAsync(SendContact sendContact, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendContact == null) {
            throw new TelegramApiException("Parameter sendContact can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendContact, sentCallback);
    }

    @Deprecated
    public final void kickMemberAsync(KickChatMember kickChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (kickChatMember == null) {
            throw new TelegramApiException("Parameter kickChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(kickChatMember, sentCallback);
    }

    @Deprecated
    public final void unbanMemberAsync(UnbanChatMember unbanChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (unbanChatMember == null) {
            throw new TelegramApiException("Parameter unbanChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(unbanChatMember, sentCallback);
    }

    @Deprecated
    public final void leaveChatAsync(LeaveChat leaveChat, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (leaveChat == null) {
            throw new TelegramApiException("Parameter leaveChat can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(leaveChat, sentCallback);
    }

    @Deprecated
    public final void getChatAsync(GetChat getChat, SentCallback<Chat> sentCallback) throws TelegramApiException {
        if (getChat == null) {
            throw new TelegramApiException("Parameter getChat can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChat, sentCallback);
    }

    @Deprecated
    public final void exportChatInviteLinkAsync(ExportChatInviteLink exportChatInviteLink, SentCallback<String> sentCallback) throws TelegramApiException {
        if (exportChatInviteLink == null) {
            throw new TelegramApiException("Parameter exportChatInviteLink can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(exportChatInviteLink, sentCallback);
    }

    @Deprecated
    public final void getChatAdministratorsAsync(GetChatAdministrators getChatAdministrators, SentCallback<ArrayList<ChatMember>> sentCallback) throws TelegramApiException {
        if (getChatAdministrators == null) {
            throw new TelegramApiException("Parameter getChatAdministrators can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChatAdministrators, sentCallback);
    }

    @Deprecated
    public final void getChatMemberAsync(GetChatMember getChatMember, SentCallback<ChatMember> sentCallback) throws TelegramApiException {
        if (getChatMember == null) {
            throw new TelegramApiException("Parameter getChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChatMember, sentCallback);
    }

    @Deprecated
    public final void getChatMemberCountAsync(GetChatMemberCount getChatMemberCount, SentCallback<Integer> sentCallback) throws TelegramApiException {
        if (getChatMemberCount == null) {
            throw new TelegramApiException("Parameter getChatMemberCount can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getChatMemberCount, sentCallback);
    }

    @Deprecated
    public final void editMessageTextAsync(EditMessageText editMessageText, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (editMessageText == null) {
            throw new TelegramApiException("Parameter editMessageText can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageText, sentCallback);
    }

    @Deprecated
    public final void editMessageCaptionAsync(EditMessageCaption editMessageCaption, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (editMessageCaption == null) {
            throw new TelegramApiException("Parameter editMessageCaption can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageCaption, sentCallback);
    }

    @Deprecated
    public final void editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (editMessageReplyMarkup == null) {
            throw new TelegramApiException("Parameter editMessageReplyMarkup can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageReplyMarkup, sentCallback);
    }

    @Deprecated
    public final void answerCallbackQueryAsync(AnswerCallbackQuery answerCallbackQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerCallbackQuery == null) {
            throw new TelegramApiException("Parameter answerCallbackQuery can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(answerCallbackQuery, sentCallback);
    }

    @Deprecated
    public final void getUserProfilePhotosAsync(GetUserProfilePhotos getUserProfilePhotos, SentCallback<UserProfilePhotos> sentCallback) throws TelegramApiException {
        if (getUserProfilePhotos == null) {
            throw new TelegramApiException("Parameter getUserProfilePhotos can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getUserProfilePhotos, sentCallback);
    }

    @Deprecated
    public final void getFileAsync(GetFile getFile, SentCallback<File> sentCallback) throws TelegramApiException {
        if (getFile == null) {
            throw new TelegramApiException("Parameter getFile can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getFile, sentCallback);
    }

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

    @Deprecated
    public final void setGameScoreAsync(SetGameScore setGameScore, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (setGameScore == null) {
            throw new TelegramApiException("Parameter setGameScore can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(setGameScore, sentCallback);
    }

    @Deprecated
    public final void getGameHighScoresAsync(GetGameHighScores getGameHighScores, SentCallback<ArrayList<GameHighScore>> sentCallback) throws TelegramApiException {
        if (getGameHighScores == null) {
            throw new TelegramApiException("Parameter getGameHighScores can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getGameHighScores, sentCallback);
    }

    @Deprecated
    public final void sendGameAsync(SendGame sendGame, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendGame == null) {
            throw new TelegramApiException("Parameter sendGame can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(sendGame, sentCallback);
    }

    @Deprecated
    public final void deleteWebhook(DeleteWebhook deleteWebhook, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (deleteWebhook == null) {
            throw new TelegramApiException("Parameter deleteWebhook can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(deleteWebhook, sentCallback);
    }

    @Deprecated
    public final void sendInvoice(SendInvoice sendInvoice, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendInvoice == null) {
            throw new TelegramApiException("Parameter sendInvoice can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(sendInvoice, sentCallback);
    }

    @Deprecated
    public final void answerShippingQuery(AnswerShippingQuery answerShippingQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerShippingQuery == null) {
            throw new TelegramApiException("Parameter answerShippingQuery can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(answerShippingQuery, sentCallback);
    }

    @Deprecated
    public final void answerPreCheckoutQuery(AnswerPreCheckoutQuery answerPreCheckoutQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerPreCheckoutQuery == null) {
            throw new TelegramApiException("Parameter answerPreCheckoutQuery can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(answerPreCheckoutQuery, sentCallback);
    }

    @Deprecated
    public final void deleteMessage(DeleteMessage deleteMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (deleteMessage == null) {
            throw new TelegramApiException("Parameter deleteMessage can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(deleteMessage, sentCallback);
    }

    @Deprecated
    public final void deleteChatPhoto(DeleteChatPhoto deleteChatPhoto, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (deleteChatPhoto == null) {
            throw new TelegramApiException("Parameter deleteChatPhoto can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(deleteChatPhoto, sentCallback);
    }

    @Deprecated
    public final void pinChatMessage(PinChatMessage pinChatMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (pinChatMessage == null) {
            throw new TelegramApiException("Parameter pinChatMessage can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(pinChatMessage, sentCallback);
    }

    @Deprecated
    public final void unpinChatMessage(UnpinChatMessage unpinChatMessage, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (unpinChatMessage == null) {
            throw new TelegramApiException("Parameter unpinChatMessage can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(unpinChatMessage, sentCallback);
    }

    @Deprecated
    public final void promoteChatMember(PromoteChatMember promoteChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (promoteChatMember == null) {
            throw new TelegramApiException("Parameter promoteChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(promoteChatMember, sentCallback);
    }

    @Deprecated
    public final void restrictChatMember(RestrictChatMember restrictChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (restrictChatMember == null) {
            throw new TelegramApiException("Parameter restrictChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(restrictChatMember, sentCallback);
    }

    @Deprecated
    public final void setChatDescription(SetChatDescription setChatDescription, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (setChatDescription == null) {
            throw new TelegramApiException("Parameter setChatDescription can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(setChatDescription, sentCallback);
    }

    @Deprecated
    public final void setChatTitle(SetChatTitle setChatTitle, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (setChatTitle == null) {
            throw new TelegramApiException("Parameter setChatTitle can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(setChatTitle, sentCallback);
    }

    // Specific Send Requests
    public abstract Message sendDocument(SendDocument sendDocument) throws TelegramApiException;

    public abstract Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException;

    public abstract Message sendVideo(SendVideo sendVideo) throws TelegramApiException;

    public abstract Message sendVideoNote(SendVideoNote sendVideoNote) throws TelegramApiException;

    public abstract Message sendSticker(SendSticker sendSticker) throws TelegramApiException;

    /**
     * Sends a file using Send Audio method (https://core.telegram.org/bots/api#sendaudio)
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public abstract Message sendAudio(SendAudio sendAudio) throws TelegramApiException;

    /**
     * Sends a voice note using Send Voice method (https://core.telegram.org/bots/api#sendvoice)
     * For this to work, your audio must be in an .ogg file encoded with OPUS
     * @param sendVoice Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public abstract Message sendVoice(SendVoice sendVoice) throws TelegramApiException;

    /**
     * Set chat profile photo (https://core.telegram.org/bots/api#setChatPhoto)
     * @param setChatPhoto Information to set the photo
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error setting the photo.
     */
    public abstract Boolean setChatPhoto(SetChatPhoto setChatPhoto) throws TelegramApiException;

    /**
     * Adds a new sticker to a set (https://core.telegram.org/bots/api#addStickerToSet)
     * @param addStickerToSet Information of the sticker to set
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error adding the sticker to the set
     */
    public abstract Boolean addStickerToSet(AddStickerToSet addStickerToSet) throws TelegramApiException;

    /**
     * Creates a new sticker set (https://core.telegram.org/bots/api#createNewStickerSet)
     * @param createNewStickerSet Information of the sticker set to create
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error creating the new sticker set
     */
    public abstract Boolean createNewStickerSet(CreateNewStickerSet createNewStickerSet) throws TelegramApiException;

    /**
     * Upload a new file as sticker (https://core.telegram.org/bots/api#uploadStickerFile)
     * @param uploadStickerFile Information of the file to upload as sticker
     * @return If success, true is returned
     * @throws TelegramApiException If there is any error uploading the new file
     */
    public abstract File uploadStickerFile(UploadStickerFile uploadStickerFile) throws TelegramApiException;

    // Simplified methods

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback);

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method) throws TelegramApiException;
}
