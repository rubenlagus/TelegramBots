package org.telegram.telegrambots.bots;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.GetMe;
import org.telegram.telegrambots.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.api.methods.games.GetGameHighScores;
import org.telegram.telegrambots.api.methods.games.SetGameScore;
import org.telegram.telegrambots.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMemberCount;
import org.telegram.telegrambots.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.api.methods.groupadministration.LeaveChat;
import org.telegram.telegrambots.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendChatAction;
import org.telegram.telegrambots.api.methods.send.SendContact;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendGame;
import org.telegram.telegrambots.api.methods.send.SendLocation;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.methods.send.SendVenue;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.methods.send.SendVoice;
import org.telegram.telegrambots.api.methods.updates.GetWebhookInfo;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.ChatMember;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.api.objects.WebhookInfo;
import org.telegram.telegrambots.api.objects.games.GameHighScore;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.updateshandlers.DownloadFileCallback;
import org.telegram.telegrambots.updateshandlers.SentCallback;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Implementation of all the methods needed to interact with Telegram Servers
 * @date 14 of January of 2016
 */
@SuppressWarnings("unused")
public abstract class AbsSender {
    private static final ContentType TEXT_PLAIN_CONTENT_TYPE = ContentType.create("text/plain", StandardCharsets.UTF_8);

    private final ExecutorService exe = Executors.newSingleThreadExecutor();
    private final BotOptions options;
    private volatile CloseableHttpClient httpclient;
    private volatile RequestConfig requestConfig;
    private static final int SOCKET_TIMEOUT = 75 * 1000;

    AbsSender(BotOptions options) {
        this.options = options;
        httpclient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                .setMaxConnTotal(100)
                .build();

        RequestConfig.Builder configBuilder = RequestConfig.copy(RequestConfig.custom().build());
        if (options.hasProxy()) {
            configBuilder.setProxy(new HttpHost(options.getProxyHost(), options.getProxyPort()));
        }

        requestConfig = configBuilder.setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(SOCKET_TIMEOUT).build();
    }

    /**
     * Returns the token of the bot to be able to perform Telegram Api Requests
     * @return Token of the bot
     */
    public abstract String getBotToken();

    public final BotOptions getOptions() {
        return options;
    }

    // Send Requests

    public final Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        }

        return sendApiMethod(sendMessage);
    }

    public final Boolean answerInlineQuery(AnswerInlineQuery answerInlineQuery) throws TelegramApiException {
        if (answerInlineQuery == null) {
            throw new TelegramApiException("Parameter answerInlineQuery can not be null");
        }

        return sendApiMethod(answerInlineQuery);
    }

    public final Boolean sendChatAction(SendChatAction sendChatAction) throws TelegramApiException {
        if (sendChatAction == null) {
            throw new TelegramApiException("Parameter sendChatAction can not be null");
        }

        return sendApiMethod(sendChatAction);
    }

    public final Message forwardMessage(ForwardMessage forwardMessage) throws TelegramApiException {
        if (forwardMessage == null) {
            throw new TelegramApiException("Parameter forwardMessage can not be null");
        }

        return sendApiMethod(forwardMessage);
    }

    public final Message sendLocation(SendLocation sendLocation) throws TelegramApiException {
        if (sendLocation == null) {
            throw new TelegramApiException("Parameter sendLocation can not be null");
        }

        return sendApiMethod(sendLocation);
    }

    public final Message sendVenue(SendVenue sendVenue) throws TelegramApiException {
        if (sendVenue == null) {
            throw new TelegramApiException("Parameter sendVenue can not be null");
        }

        return sendApiMethod(sendVenue);
    }

    public final Message sendContact(SendContact sendContact) throws TelegramApiException {
        if (sendContact == null) {
            throw new TelegramApiException("Parameter sendContact can not be null");
        }

        return sendApiMethod(sendContact);
    }

    public final Boolean kickMember(KickChatMember kickChatMember) throws TelegramApiException {
        if (kickChatMember == null) {
            throw new TelegramApiException("Parameter kickChatMember can not be null");
        }
        return sendApiMethod(kickChatMember);
    }

    public final Boolean unbanMember(UnbanChatMember unbanChatMember) throws TelegramApiException {
        if (unbanChatMember == null) {
            throw new TelegramApiException("Parameter unbanChatMember can not be null");
        }
        return sendApiMethod(unbanChatMember);
    }

    public final Boolean leaveChat(LeaveChat leaveChat) throws TelegramApiException {
        if (leaveChat == null) {
            throw new TelegramApiException("Parameter leaveChat can not be null");
        }
        return sendApiMethod(leaveChat);
    }

    public final Chat getChat(GetChat getChat) throws TelegramApiException {
        if (getChat == null) {
            throw new TelegramApiException("Parameter getChat can not be null");
        }
        return sendApiMethod(getChat);
    }

    public final List<ChatMember> getChatAdministrators(GetChatAdministrators getChatAdministrators) throws TelegramApiException {
        if (getChatAdministrators == null) {
            throw new TelegramApiException("Parameter getChatAdministrators can not be null");
        }
        return sendApiMethod(getChatAdministrators);
    }

    public final ChatMember getChatMember(GetChatMember getChatMember) throws TelegramApiException {
        if (getChatMember == null) {
            throw new TelegramApiException("Parameter getChatMember can not be null");
        }
        return sendApiMethod(getChatMember);
    }

    public final Integer getChatMemberCount(GetChatMemberCount getChatMemberCount) throws TelegramApiException {
        if (getChatMemberCount == null) {
            throw new TelegramApiException("Parameter getChatMemberCount can not be null");
        }
        return sendApiMethod(getChatMemberCount);
    }

    public final Message editMessageText(EditMessageText editMessageText) throws TelegramApiException {
        if (editMessageText == null) {
            throw new TelegramApiException("Parameter editMessageText can not be null");
        }
        return sendApiMethod(editMessageText);
    }

    public final Message editMessageCaption(EditMessageCaption editMessageCaption) throws TelegramApiException {
        if (editMessageCaption == null) {
            throw new TelegramApiException("Parameter editMessageCaption can not be null");
        }
        return sendApiMethod(editMessageCaption);
    }

    public final Message editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) throws TelegramApiException {
        if (editMessageReplyMarkup == null) {
            throw new TelegramApiException("Parameter editMessageReplyMarkup can not be null");
        }
        return sendApiMethod(editMessageReplyMarkup);
    }

    public final Boolean answerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException {
        if (answerCallbackQuery == null) {
            throw new TelegramApiException("Parameter answerCallbackQuery can not be null");
        }
        return sendApiMethod(answerCallbackQuery);
    }

    public final UserProfilePhotos getUserProfilePhotos(GetUserProfilePhotos getUserProfilePhotos) throws TelegramApiException {
        if (getUserProfilePhotos == null) {
            throw new TelegramApiException("Parameter getUserProfilePhotos can not be null");
        }

        return sendApiMethod(getUserProfilePhotos);
    }

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
        GetMe getMe = new GetMe();

        return sendApiMethod(getMe);
    }

    public final WebhookInfo getWebhookInfo() throws TelegramApiException {
        GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
        return sendApiMethod(getWebhookInfo);
    }

    public final java.io.File downloadFile(File file) throws TelegramApiException {
        if(file == null){
            throw new TelegramApiException("Parameter file can not be null");
        }
        String url = MessageFormat.format(File.FILEBASEURL, getBotToken(), file.getFilePath());
        java.io.File output;
        try {
            output = java.io.File.createTempFile(file.getFileId(), ".tmp");
            FileUtils.copyURLToFile(new URL(url), output);
        } catch (MalformedURLException e) {
            throw new TelegramApiException("Wrong url for file: " + url);
        } catch (IOException e) {
            throw new TelegramApiRequestException("Error downloading the file", e);
        }

        return output;
    }

    public final Serializable setGameScore(SetGameScore setGameScore) throws TelegramApiException {
        if(setGameScore == null){
            throw new TelegramApiException("Parameter setGameScore can not be null");
        }
        return sendApiMethod(setGameScore);
    }

    public final Serializable getGameHighScores(GetGameHighScores getGameHighScores) throws TelegramApiException {
        if(getGameHighScores == null){
            throw new TelegramApiException("Parameter getGameHighScores can not be null");
        }
        return sendApiMethod(getGameHighScores);
    }

    public final Message sendGame(SendGame sendGame) throws TelegramApiException {
        if(sendGame == null){
            throw new TelegramApiException("Parameter sendGame can not be null");
        }
        return sendApiMethod(sendGame);
    }

    // Send Requests Async

    public final void sendMessageAsync(SendMessage sendMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendMessage, sentCallback);
    }

    public final void answerInlineQueryAsync(AnswerInlineQuery answerInlineQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerInlineQuery == null) {
            throw new TelegramApiException("Parameter answerInlineQuery can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(answerInlineQuery, sentCallback);
    }

    public final void sendChatActionAsync(SendChatAction sendChatAction, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (sendChatAction == null) {
            throw new TelegramApiException("Parameter sendChatAction can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendChatAction, sentCallback);
    }

    public final void forwardMessageAsync(ForwardMessage forwardMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (forwardMessage == null) {
            throw new TelegramApiException("Parameter forwardMessage can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(forwardMessage, sentCallback);
    }

    public final void sendLocationAsync(SendLocation sendLocation, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendLocation == null) {
            throw new TelegramApiException("Parameter sendLocation can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendLocation, sentCallback);
    }

    public final void sendVenueAsync(SendVenue sendVenue, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendVenue == null) {
            throw new TelegramApiException("Parameter sendVenue can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendVenue, sentCallback);
    }

    public final void sendContactAsync(SendContact sendContact, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendContact == null) {
            throw new TelegramApiException("Parameter sendContact can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendContact, sentCallback);
    }

    public final void kickMemberAsync(KickChatMember kickChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (kickChatMember == null) {
            throw new TelegramApiException("Parameter kickChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(kickChatMember, sentCallback);
    }

    public final void unbanMemberAsync(UnbanChatMember unbanChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (unbanChatMember == null) {
            throw new TelegramApiException("Parameter unbanChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(unbanChatMember, sentCallback);
    }

    public final void leaveChatAsync(LeaveChat leaveChat, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (leaveChat == null) {
            throw new TelegramApiException("Parameter leaveChat can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(leaveChat, sentCallback);
    }

    public final void getChatAsync(GetChat getChat, SentCallback<Chat> sentCallback) throws TelegramApiException {
        if (getChat == null) {
            throw new TelegramApiException("Parameter getChat can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChat, sentCallback);
    }

    public final void getChatAdministratorsAsync(GetChatAdministrators getChatAdministrators, SentCallback<ArrayList<ChatMember>> sentCallback) throws TelegramApiException {
        if (getChatAdministrators == null) {
            throw new TelegramApiException("Parameter getChatAdministrators can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChatAdministrators, sentCallback);
    }

    public final void getChatMemberAsync(GetChatMember getChatMember, SentCallback<ChatMember> sentCallback) throws TelegramApiException {
        if (getChatMember == null) {
            throw new TelegramApiException("Parameter getChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChatMember, sentCallback);
    }

    public final void getChatMemberCountAsync(GetChatMemberCount getChatMemberCount, SentCallback<Integer> sentCallback) throws TelegramApiException {
        if (getChatMemberCount == null) {
            throw new TelegramApiException("Parameter getChatMemberCount can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getChatMemberCount, sentCallback);
    }

    public final void editMessageTextAsync(EditMessageText editMessageText, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (editMessageText == null) {
            throw new TelegramApiException("Parameter editMessageText can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageText, sentCallback);
    }

    public final void editMessageCaptionAsync(EditMessageCaption editMessageCaption, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (editMessageCaption == null) {
            throw new TelegramApiException("Parameter editMessageCaption can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageCaption, sentCallback);
    }

    public final void editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (editMessageReplyMarkup == null) {
            throw new TelegramApiException("Parameter editMessageReplyMarkup can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageReplyMarkup, sentCallback);
    }

    public final void answerCallbackQueryAsync(AnswerCallbackQuery answerCallbackQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerCallbackQuery == null) {
            throw new TelegramApiException("Parameter answerCallbackQuery can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(answerCallbackQuery, sentCallback);
    }

    public final void getUserProfilePhotosAsync(GetUserProfilePhotos getUserProfilePhotos, SentCallback<UserProfilePhotos> sentCallback) throws TelegramApiException {
        if (getUserProfilePhotos == null) {
            throw new TelegramApiException("Parameter getUserProfilePhotos can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getUserProfilePhotos, sentCallback);
    }

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
        GetMe getMe = new GetMe();
        sendApiMethodAsync(getMe, sentCallback);
    }

    public final void getWebhookInfoAsync(SentCallback<WebhookInfo> sentCallback) throws TelegramApiException {
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
        sendApiMethodAsync(getWebhookInfo, sentCallback);
    }

    public final void setGameScoreAsync(SetGameScore setGameScore, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (setGameScore == null) {
            throw new TelegramApiException("Parameter setGameScore can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(setGameScore, sentCallback);
    }

    public final void getGameHighScoresAsync(GetGameHighScores getGameHighScores, SentCallback<ArrayList<GameHighScore>> sentCallback) throws TelegramApiException {
        if (getGameHighScores == null) {
            throw new TelegramApiException("Parameter getGameHighScores can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getGameHighScores, sentCallback);
    }

    public final void sendGameAsync(SendGame sendGame, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendGame == null) {
            throw new TelegramApiException("Parameter sendGame can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(sendGame, sentCallback);
    }

    public final void downloadFileAsync(File file, DownloadFileCallback callback) throws TelegramApiException {
        if(file == null){
            throw new TelegramApiException("Parameter file can not be null");
        }
        if (callback == null) {
            throw new TelegramApiException("Parameter callback can not be null");
        }

        exe.submit(new Runnable() {
            @Override
            public void run() {
                String url = MessageFormat.format(File.FILEBASEURL, getBotToken(), file.getFilePath());
                try {
                    java.io.File output = java.io.File.createTempFile(file.getFileId(), ".tmp");
                    FileUtils.copyURLToFile(new URL(url), output);
                    callback.onResult(file, output);
                } catch (MalformedURLException e) {
                    callback.onException(file, new TelegramApiException("Wrong url for file: " + url));
                } catch (IOException e) {
                    callback.onException(file, new TelegramApiRequestException("Error downloading the file", e));
                }
            }
        });
    }

    // Specific Send Requests

    public final Message sendDocument(SendDocument sendDocument) throws TelegramApiException {
        String responseContent;

        try {
            String url = getBaseUrl() + SendDocument.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            if (sendDocument.isNewDocument()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendDocument.CHATID_FIELD, sendDocument.getChatId());
                if (sendDocument.getNewDocumentFile() != null) {
                    builder.addBinaryBody(SendDocument.DOCUMENT_FIELD, sendDocument.getNewDocumentFile());
                } else if (sendDocument.getNewDocumentStream() != null) {
                    builder.addBinaryBody(SendDocument.DOCUMENT_FIELD, sendDocument.getNewDocumentStream(), ContentType.APPLICATION_OCTET_STREAM, sendDocument.getDocumentName());
                } else {
                    builder.addBinaryBody(SendDocument.DOCUMENT_FIELD, new java.io.File(sendDocument.getDocument()), ContentType.APPLICATION_OCTET_STREAM, sendDocument.getDocumentName());
                }
                if (sendDocument.getReplyMarkup() != null) {
                    builder.addTextBody(SendDocument.REPLYMARKUP_FIELD, sendDocument.getReplyMarkup().toJson().toString(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendDocument.getReplyToMessageId() != null) {
                    builder.addTextBody(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplyToMessageId().toString());
                }
                if (sendDocument.getCaption() != null) {
                    builder.addTextBody(SendDocument.CAPTION_FIELD, sendDocument.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendDocument.getDisableNotification() != null) {
                    builder.addTextBody(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendDocument.CHATID_FIELD, sendDocument.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendDocument.DOCUMENT_FIELD, sendDocument.getDocument()));
                if (sendDocument.getReplyMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendDocument.REPLYMARKUP_FIELD, sendDocument.getReplyMarkup().toJson().toString()));
                }
                if (sendDocument.getReplyToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplyToMessageId().toString()));
                }
                if (sendDocument.getCaption() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendDocument.CAPTION_FIELD, sendDocument.getCaption()));
                }
                if (sendDocument.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send document", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
            throw new TelegramApiRequestException("Error at sendDocument", jsonObject);
        }

        return new Message(jsonObject.getJSONObject(Constants.RESPONSEFIELDRESULT));
    }

    public final Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
        String responseContent;
        try {
            String url = getBaseUrl() + SendPhoto.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            if (sendPhoto.isNewPhoto()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendPhoto.CHATID_FIELD, sendPhoto.getChatId());
                if (sendPhoto.getNewPhotoFile() != null) {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, sendPhoto.getNewPhotoFile());
                } else if (sendPhoto.getNewPhotoStream() != null) {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, sendPhoto.getNewPhotoStream(), ContentType.APPLICATION_OCTET_STREAM, sendPhoto.getPhotoName());
                } else {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, new java.io.File(sendPhoto.getPhoto()), ContentType.APPLICATION_OCTET_STREAM, sendPhoto.getPhotoName());
                }
                if (sendPhoto.getReplyMarkup() != null) {
                    builder.addTextBody(SendPhoto.REPLYMARKUP_FIELD, sendPhoto.getReplyMarkup().toJson().toString(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendPhoto.getReplyToMessageId() != null) {
                    builder.addTextBody(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplyToMessageId().toString());
                }
                if (sendPhoto.getCaption() != null) {
                    builder.addTextBody(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendPhoto.getDisableNotification() != null) {
                    builder.addTextBody(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendPhoto.CHATID_FIELD, sendPhoto.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendPhoto.PHOTO_FIELD, sendPhoto.getPhoto()));
                if (sendPhoto.getReplyMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendPhoto.REPLYMARKUP_FIELD, sendPhoto.getReplyMarkup().toJson().toString()));
                }
                if (sendPhoto.getReplyToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplyToMessageId().toString()));
                }
                if (sendPhoto.getCaption() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption()));
                }
                if (sendPhoto.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send photo", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
            throw new TelegramApiRequestException("Error at sendPhoto", jsonObject);
        }

        return new Message(jsonObject.getJSONObject(Constants.RESPONSEFIELDRESULT));
    }

    public final Message sendVideo(SendVideo sendVideo) throws TelegramApiException {
        String responseContent;
        try {
            String url = getBaseUrl() + SendVideo.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            if (sendVideo.isNewVideo()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendVideo.CHATID_FIELD, sendVideo.getChatId());
                if (sendVideo.getNewVideoFile() != null) {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, sendVideo.getNewVideoFile());
                } else if (sendVideo.getNewVideoStream() != null) {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, sendVideo.getNewVideoStream(), ContentType.APPLICATION_OCTET_STREAM, sendVideo.getVideoName());
                } else {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, new java.io.File(sendVideo.getVideo()), ContentType.APPLICATION_OCTET_STREAM, sendVideo.getVideoName());
                }
                if (sendVideo.getReplyMarkup() != null) {
                    builder.addTextBody(SendVideo.REPLYMARKUP_FIELD, sendVideo.getReplyMarkup().toJson().toString(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendVideo.getReplyToMessageId() != null) {
                    builder.addTextBody(SendVideo.REPLYTOMESSAGEID_FIELD, sendVideo.getReplyToMessageId().toString());
                }
                if (sendVideo.getCaption() != null) {
                    builder.addTextBody(SendVideo.CAPTION_FIELD, sendVideo.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendVideo.getDuration() != null) {
                    builder.addTextBody(SendVideo.DURATION_FIELD, sendVideo.getDuration().toString());
                }
                if (sendVideo.getWidth() != null) {
                    builder.addTextBody(SendVideo.WIDTH_FIELD, sendVideo.getWidth().toString());
                }
                if (sendVideo.getHeight() != null) {
                    builder.addTextBody(SendVideo.HEIGHT_FIELD, sendVideo.getHeight().toString());
                }
                if (sendVideo.getDisableNotification() != null) {
                    builder.addTextBody(SendVideo.DISABLENOTIFICATION_FIELD, sendVideo.getDisableNotification().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendVideo.CHATID_FIELD, sendVideo.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendVideo.VIDEO_FIELD, sendVideo.getVideo()));
                if (sendVideo.getReplyMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.REPLYMARKUP_FIELD, sendVideo.getReplyMarkup().toJson().toString()));
                }
                if (sendVideo.getReplyToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.REPLYTOMESSAGEID_FIELD, sendVideo.getReplyToMessageId().toString()));
                }
                if (sendVideo.getCaption() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.CAPTION_FIELD, sendVideo.getCaption()));
                }
                if (sendVideo.getDuration() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.DURATION_FIELD, sendVideo.getDuration().toString()));
                }
                if (sendVideo.getWidth() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.WIDTH_FIELD, sendVideo.getWidth().toString()));
                }
                if (sendVideo.getHeight() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.HEIGHT_FIELD, sendVideo.getHeight().toString()));
                }
                if (sendVideo.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.DISABLENOTIFICATION_FIELD, sendVideo.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
            throw new TelegramApiRequestException("Error at sendVideo", jsonObject);
        }

        return new Message(jsonObject.getJSONObject(Constants.RESPONSEFIELDRESULT));
    }

    public final Message sendSticker(SendSticker sendSticker) throws TelegramApiException {
        String responseContent;

        try {
            String url = getBaseUrl() + SendSticker.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            if (sendSticker.isNewSticker()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendSticker.CHATID_FIELD, sendSticker.getChatId());
                if (sendSticker.getNewStickerFile() != null) {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, sendSticker.getNewStickerFile());
                } else if (sendSticker.getNewStickerStream() != null) {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, sendSticker.getNewStickerStream(), ContentType.APPLICATION_OCTET_STREAM, sendSticker.getStickerName());
                } else {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, new java.io.File(sendSticker.getSticker()), ContentType.APPLICATION_OCTET_STREAM, sendSticker.getStickerName());
                }
                if (sendSticker.getReplyMarkup() != null) {
                    builder.addTextBody(SendSticker.REPLYMARKUP_FIELD, sendSticker.getReplyMarkup().toJson().toString(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendSticker.getReplyToMessageId() != null) {
                    builder.addTextBody(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplyToMessageId().toString());
                }
                if (sendSticker.getDisableNotification() != null) {
                    builder.addTextBody(SendSticker.DISABLENOTIFICATION_FIELD, sendSticker.getDisableNotification().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendSticker.CHATID_FIELD, sendSticker.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendSticker.STICKER_FIELD, sendSticker.getSticker()));
                if (sendSticker.getReplyMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendSticker.REPLYMARKUP_FIELD, sendSticker.getReplyMarkup().toJson().toString()));
                }
                if (sendSticker.getReplyToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplyToMessageId().toString()));
                }
                if (sendSticker.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendSticker.DISABLENOTIFICATION_FIELD, sendSticker.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
            throw new TelegramApiRequestException("Error at sendSticker", jsonObject);
        }

        return new Message(jsonObject.getJSONObject(Constants.RESPONSEFIELDRESULT));
    }

    /**
     * Sends a file using Send Audio method (https://core.telegram.org/bots/api#sendaudio)
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public final Message sendAudio(SendAudio sendAudio) throws TelegramApiException {
        String responseContent;


        try {
            String url = getBaseUrl() + SendAudio.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            if (sendAudio.isNewAudio()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendAudio.CHATID_FIELD, sendAudio.getChatId());
                if (sendAudio.getNewAudioFile() != null) {
                    builder.addBinaryBody(SendAudio.AUDIO_FIELD, sendAudio.getNewAudioFile());
                } else if (sendAudio.getNewAudioStream() != null) {
                    builder.addBinaryBody(SendAudio.AUDIO_FIELD, sendAudio.getNewAudioStream(), ContentType.APPLICATION_OCTET_STREAM, sendAudio.getAudioName());
                } else {
                    builder.addBinaryBody(SendAudio.AUDIO_FIELD, new java.io.File(sendAudio.getAudio()), ContentType.create("audio/mpeg"), sendAudio.getAudioName());
                }
                if (sendAudio.getReplyMarkup() != null) {
                    builder.addTextBody(SendAudio.REPLYMARKUP_FIELD, sendAudio.getReplyMarkup().toJson().toString(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendAudio.getReplyToMessageId() != null) {
                    builder.addTextBody(SendAudio.REPLYTOMESSAGEID_FIELD, sendAudio.getReplyToMessageId().toString());
                }
                if (sendAudio.getPerformer() != null) {
                    builder.addTextBody(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer());
                }
                if (sendAudio.getTitle() != null) {
                    builder.addTextBody(SendAudio.TITLE_FIELD, sendAudio.getTitle());
                }
                if(sendAudio.getDuration() != null){
                    builder.addTextBody(SendAudio.DURATION_FIELD, sendAudio.getDuration().toString());
                }
                if (sendAudio.getDisableNotification() != null) {
                    builder.addTextBody(SendAudio.DISABLENOTIFICATION_FIELD, sendAudio.getDisableNotification().toString());
                }
                if (sendAudio.getCaption() != null) {
                    builder.addTextBody(SendAudio.CAPTION_FIELD, sendAudio.getCaption());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendAudio.CHATID_FIELD, sendAudio.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendAudio.AUDIO_FIELD, sendAudio.getAudio()));
                if (sendAudio.getReplyMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.REPLYMARKUP_FIELD, sendAudio.getReplyMarkup().toJson().toString()));
                }
                if (sendAudio.getReplyToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.REPLYTOMESSAGEID_FIELD, sendAudio.getReplyToMessageId().toString()));
                }
                if (sendAudio.getPerformer() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer()));
                }
                if (sendAudio.getTitle() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.TITLE_FIELD, sendAudio.getTitle()));
                }
                if (sendAudio.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.DISABLENOTIFICATION_FIELD, sendAudio.getDisableNotification().toString()));
                }
                if (sendAudio.getCaption() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.CAPTION_FIELD, sendAudio.getCaption()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);

        /* if we got not an "ok" with false, we have a response like that
         * 
         * {"description":"[Error]: Bad Request: chat not found","error_code":400,"ok":false}
         */
        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
            throw new TelegramApiRequestException("Error at sendAudio", jsonObject);
        }

        // and if not, we can expect a "result" section. and out of this can a new Message object be built
        return new Message(jsonObject.getJSONObject(Constants.RESPONSEFIELDRESULT));
    }

    /**
     * Sends a voice note using Send Voice method (https://core.telegram.org/bots/api#sendvoice)
     * For this to work, your audio must be in an .ogg file encoded with OPUS
     * @param sendVoice Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public final Message sendVoice(SendVoice sendVoice) throws TelegramApiException {
        String responseContent;

        try {
            String url = getBaseUrl() + SendVoice.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            if (sendVoice.isNewVoice()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendVoice.CHATID_FIELD, sendVoice.getChatId());
                if (sendVoice.getNewVoiceFile() != null) {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, sendVoice.getNewVoiceFile());
                } else if (sendVoice.getNewVoiceStream() != null) {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, sendVoice.getNewVoiceStream(), ContentType.APPLICATION_OCTET_STREAM, sendVoice.getVoiceName());
                } else {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, new java.io.File(sendVoice.getVoice()), ContentType.create("audio/ogg"), sendVoice.getVoiceName());
                }
                if (sendVoice.getReplyMarkup() != null) {
                    builder.addTextBody(SendVoice.REPLYMARKUP_FIELD, sendVoice.getReplyMarkup().toJson().toString(), TEXT_PLAIN_CONTENT_TYPE);
                }
                if (sendVoice.getReplyToMessageId() != null) {
                    builder.addTextBody(SendVoice.REPLYTOMESSAGEID_FIELD, sendVoice.getReplyToMessageId().toString());
                }
                if (sendVoice.getDisableNotification() != null) {
                    builder.addTextBody(SendVoice.DISABLENOTIFICATION_FIELD, sendVoice.getDisableNotification().toString());
                }
                if (sendVoice.getDuration() != null) {
                    builder.addTextBody(SendVoice.DURATION_FIELD, sendVoice.getDuration().toString());
                }
                if (sendVoice.getCaption() != null) {
                    builder.addTextBody(SendVoice.CAPTION_FIELD, sendVoice.getCaption());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendVoice.CHATID_FIELD, sendVoice.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendVoice.VOICE_FIELD, sendVoice.getVoice()));
                if (sendVoice.getReplyMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.REPLYMARKUP_FIELD, sendVoice.getReplyMarkup().toJson().toString()));
                }
                if (sendVoice.getReplyToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.REPLYTOMESSAGEID_FIELD, sendVoice.getReplyToMessageId().toString()));
                }
                if (sendVoice.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.DISABLENOTIFICATION_FIELD, sendVoice.getDisableNotification().toString()));
                }
                if (sendVoice.getDuration() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.DURATION_FIELD, sendVoice.getDuration().toString()));
                }
                if (sendVoice.getCaption() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.CAPTION_FIELD, sendVoice.getCaption()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
            throw new TelegramApiRequestException("Error at sendVoice", jsonObject);
        }

        return new Message(jsonObject.getJSONObject(Constants.RESPONSEFIELDRESULT));
    }

    // Simplified methods

    private <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback) {
        //noinspection Convert2Lambda
        exe.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    method.validate();
                    String url = getBaseUrl() + method.getPath();
                    HttpPost httppost = new HttpPost(url);
                    httppost.setConfig(requestConfig);
                    httppost.addHeader("charset", StandardCharsets.UTF_8.name());
                    httppost.setEntity(new StringEntity(method.toJson().toString(), ContentType.APPLICATION_JSON));
                    try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                        HttpEntity ht = response.getEntity();
                        BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                        String responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);

                        JSONObject jsonObject = new JSONObject(responseContent);
                        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                            callback.onError(method, jsonObject);
                        }
                        callback.onResult(method, jsonObject);
                    }
                } catch (IOException | TelegramApiValidationException e) {
                    callback.onException(method, e);
                }

            }
        });
    }

    private <T extends Serializable> T sendApiMethod(BotApiMethod<T> method) throws TelegramApiException {
        method.validate();
        String responseContent;
        try {
            String url = getBaseUrl() + method.getPath();
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            httppost.addHeader("charset", StandardCharsets.UTF_8.name());
            httppost.setEntity(new StringEntity(method.toJson().toString(), ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getPath() + " method", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
            throw new TelegramApiRequestException("Error at " + method.getPath(), jsonObject);
        }

        return method.deserializeResponse(jsonObject);
    }

    private String getBaseUrl() {
        return Constants.BASEURL + getBotToken() + "/";
    }
}
