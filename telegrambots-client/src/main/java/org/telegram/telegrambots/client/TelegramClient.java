package org.telegram.telegrambots.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class TelegramClient {
    @Nonnull
    private final OkHttpClient client;
    @Nonnull
    private final String botToken;
    @Nonnull
    private final String baseUrl;
    @Nonnull
    private final ObjectMapper objectMapper = new ObjectMapper();

    TelegramClient(@Nonnull OkHttpClient client, @Nonnull String botToken, @Nonnull String baseUrl) {
        this.client = Objects.requireNonNull(client);
        this.botToken = Objects.requireNonNull(botToken);
        this.baseUrl = Objects.requireNonNull(baseUrl);
    }

    TelegramClient(@Nonnull OkHttpClient client, @Nonnull String botToken) {
        this(client, botToken, "https://api.telegram.org");
    }

    TelegramClient(@Nonnull String botToken, @Nonnull String baseUrl) {
        this(new OkHttpClient.Builder().build(), botToken, baseUrl);
    }

    TelegramClient(@Nonnull String botToken) {
        this(new OkHttpClient.Builder().build(), botToken);
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> execute(@Nonnull Method method) throws TelegramApiException {
        //Intellij is a bit too optimistic with @NonNull here
        //noinspection ConstantConditions
        if (method == null) {
            throw new TelegramApiException("Parameter method can not be null");
        }
        method.validate();

        try {
            String url = buildUrl(method.getMethod());
            String body = objectMapper.writeValueAsString(method);
            Map<String, String> headers = Map.ofEntries(
                    Map.entry("charset", StandardCharsets.UTF_8.name()),
                    Map.entry("content-type", "application/json")
            );

            Request request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .post(RequestBody.create(body, MediaType.parse("application/json")))
                    .build();

            return sendRequest(method, request);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getMethod() + " method", e);
        }
    }

    public final CompletableFuture<Message> execute(SendDocument sendDocument) throws TelegramApiException {
        assertParamNotNull(sendDocument, "sendDocument");

        sendDocument.validate();
        try {
            String url = buildUrl(SendDocument.PATH);

            MultipartBody.Builder builder = new MultipartBody.Builder();

            builder.addFormDataPart(SendDocument.CHATID_FIELD, sendDocument.getChatId());

            addInputFile(builder, sendDocument.getDocument(), SendDocument.DOCUMENT_FIELD, true);
            if (sendDocument.getReplyMarkup() != null) {
                builder.addFormDataPart(SendDocument.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendDocument.getReplyMarkup()));
            }
            if (sendDocument.getReplyToMessageId() != null) {
                builder.addFormDataPart(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplyToMessageId().toString());
            }
            if (sendDocument.getCaption() != null) {
                builder.addFormDataPart(SendDocument.CAPTION_FIELD, sendDocument.getCaption());
                if (sendDocument.getParseMode() != null) {
                    builder.addFormDataPart(SendDocument.PARSEMODE_FIELD, sendDocument.getParseMode());
                }
            }
            if (sendDocument.getDisableNotification() != null) {
                builder.addFormDataPart(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString());
            }
            if (sendDocument.getProtectContent() != null) {
                builder.addFormDataPart(SendDocument.PROTECTCONTENT_FIELD, sendDocument.getProtectContent().toString());
            }

            if (sendDocument.getAllowSendingWithoutReply() != null) {
                builder.addFormDataPart(SendDocument.ALLOWSENDINGWITHOUTREPLY_FIELD, sendDocument.getAllowSendingWithoutReply().toString());
            }
            if (sendDocument.getDisableContentTypeDetection() != null) {
                builder.addFormDataPart(SendDocument.DISABLECONTENTTYPEDETECTION_FIELD, sendDocument.getDisableContentTypeDetection().toString());
            }
            if (sendDocument.getCaptionEntities() != null) {
                builder.addFormDataPart(SendDocument.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendDocument.getCaptionEntities()));
            }

            if (sendDocument.getThumb() != null) {
                addInputFile(builder, sendDocument.getThumb(), SendDocument.THUMB_FIELD, false);
                builder.addFormDataPart(SendDocument.THUMB_FIELD, sendDocument.getThumb().getAttachName());
            }

            MultipartBody multipart = builder.build();
            Request httpPost = new Request.Builder().url(url).post(multipart).build();

            return sendRequest(sendDocument, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send document", e);
        }
    }

    @NotNull
    private <T extends Serializable, Method extends PartialBotApiMethod<T>> OkHttpFutureCallback<T, Method> sendRequest(
            @NotNull Method method, @NotNull Request request
    ) {
        OkHttpFutureCallback<T, Method> callback = new OkHttpFutureCallback<>(method);
        client.newCall(request).enqueue(callback);

        return callback;
    }

    @Nonnull
    private String buildUrl(@Nonnull String methodName) {
        return baseUrl + "/bot" + botToken + "/" + methodName;
    }

    private void addInputFile(MultipartBody.Builder builder, InputFile file, String fileField, boolean addField) throws IOException {
        if (file.isNew()) {
            if (file.getNewMediaFile() != null) {
                builder.addPart(RequestBody.create(file.getNewMediaFile(), MediaType.parse("application/octet-stream")));
            } else if (file.getNewMediaStream() != null) {
                builder.addPart(RequestBody.create(file.getNewMediaStream().readAllBytes(), MediaType.parse("application/octet-stream")));
            }
        }

        if (addField) {
            builder.addFormDataPart(fileField, file.getAttachName());
        }
    }

    private void assertParamNotNull(Object param, String paramName) throws TelegramApiException {
        if (param == null) {
            throw new TelegramApiException("Parameter " + paramName + " can not be null");
        }
    }

}
