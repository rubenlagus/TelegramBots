package org.telegram.telegrambots.client.jetty;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;
import org.eclipse.jetty.client.HttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.CallbackReplyMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendLivePhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendRichMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVenue;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.SendMessageResult;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessageMedia;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestJettyCallbackReplies {
    private static final String MESSAGE = "{\"ok\":true,\"result\":{\"message_id\":42,\"date\":123,\"chat\":{\"id\":1,\"type\":\"private\"},\"text\":\"hello\"}}";
    private static final String TRUE = "{\"ok\":true,\"result\":true}";
    private static final JsonMapper MAPPER = new JsonMapper();

    private MockWebServer server;
    private HttpClient httpClient;
    private JettyTelegramClient client;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockWebServer();
        HttpUrl url = server.url("");
        httpClient = new HttpClient();
        httpClient.start();
        client = new JettyTelegramClient(httpClient, "token", TelegramUrl.builder()
                .schema(url.scheme()).host(url.host()).port(url.port()).build());
    }

    @AfterEach
    void tearDown() throws Exception {
        httpClient.stop();
        server.shutdown();
    }

    @Test
    void jsonReturnsMessageAndCallbackSuccess() throws Exception {
        SendMessage ordinary = new SendMessage("chat", "hello");
        respond(MESSAGE);
        SendMessageResult message = client.executeWithResult(ordinary);
        assertFalse(message.isCallbackSuccess());
        assertEquals(42, message.getMessage().getMessageId());
        assertEquals("hello", message.getMessage().getText());
        RecordedRequest normalRequest = request();
        assertEquals("/bottoken/" + ordinary.getMethod(), normalRequest.getPath());
        assertTrue(normalRequest.getHeader("Content-Type").startsWith("application/json"));

        SendMessage callback = SendMessage.builder().text("hello")
                .ephemeralMessageParameters(callbackParameters()).build();
        respond(TRUE);
        SendMessageResult result = client.executeWithResultAsync(callback).get(5, TimeUnit.SECONDS);
        assertTrue(result.isCallbackSuccess());
        assertNull(result.getMessage());
        JsonNode body = MAPPER.readTree(request().getBody().readUtf8());
        assertEquals("cq-1", body.path("ephemeral_message_parameters").path("callback_query_id").asText());
        assertFalse(body.path("ephemeral_message_parameters").has("receiver_user_id"));
        assertFalse(body.has("chat_id"));
        assertFalse(body.has("callbackOnly"));
    }

    @Test
    void multipartAndRichRequestsUseTheResultParser() throws Exception {
        SendPhoto photo = SendPhoto.builder().photo(upload("photo.jpg"))
                .caption("caption").ephemeralMessageParameters(callbackParameters()).build();
        respond(TRUE);
        assertTrue(client.executeWithResultAsync(photo).get(5, TimeUnit.SECONDS).isCallbackSuccess());
        RecordedRequest photoRequest = request();
        assertTrue(photoRequest.getHeader("Content-Type").startsWith("multipart/form-data"));
        String photoBody = photoRequest.getBody().readUtf8();
        assertTrue(photoBody.contains("name=\"photo.jpg\""), photoBody);
        assertTrue(photoBody.contains("upload contents"), photoBody);
        assertTrue(photoBody.contains("name=\"photo\""), photoBody);
        assertTrue(photoBody.contains("name=\"ephemeral_message_parameters\""), photoBody);
        assertTrue(photoBody.contains("\"callback_query_id\":\"cq-1\""), photoBody);
        assertTrue(photoBody.contains("name=\"caption\""), photoBody);

        SendRichMessage rich = SendRichMessage.builder()
                .richMessage(InputRichMessage.builder().html("hello").build())
                .ephemeralMessageParameters(callbackParameters()).build();
        respond(TRUE);
        assertTrue(client.executeWithResultAsync(rich).get(5, TimeUnit.SECONDS).isCallbackSuccess());
        RecordedRequest richRequest = request();
        assertEquals("/bottoken/" + rich.getMethod(), richRequest.getPath());
        String richBody = richRequest.getBody().readUtf8();
        assertTrue(richBody.contains("name=\"rich_message\""), richBody);
        assertTrue(richBody.contains("\"html\":\"hello\""), richBody);
        assertTrue(richBody.contains("\"callback_query_id\":\"cq-1\""), richBody);

        respond(MESSAGE);
        SendMessageResult message = client.executeWithResult(photoWithExistingFile());
        assertEquals(42, message.getMessage().getMessageId());
        assertFalse(message.isCallbackSuccess());
        request();
    }

    @Test
    void animationAndLivePhotoSendOnlyOneKeyboardPart() throws Exception {
        for (CallbackReplyMethod method : List.of(
                SendAnimation.builder().animation(new InputFile("animation-id"))
                        .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build())
                        .ephemeralMessageParameters(callbackParameters()).build(),
                SendLivePhoto.builder().livePhoto(upload("live.jpg")).photo(upload("photo.jpg"))
                        .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build())
                        .ephemeralMessageParameters(callbackParameters()).build())) {
            respond(TRUE);
            assertTrue(client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS).isCallbackSuccess());
            String body = request().getBody().readUtf8();
            assertEquals(1, body.split("name=\"reply_markup\"", -1).length - 1, body);
            if (method instanceof SendLivePhoto) {
                assertEquals(2, body.split("upload contents", -1).length - 1, body);
            }
        }
    }

    @Test
    void richMessageUploadsAttachedMedia() throws Exception {
        InputMediaPhoto media = new InputMediaPhoto(new ByteArrayInputStream("rich upload".getBytes(StandardCharsets.UTF_8)), "rich.jpg");
        SendRichMessage rich = SendRichMessage.builder().chatId("chat")
                .richMessage(InputRichMessage.builder().html("hello")
                        .media(List.of(InputRichMessageMedia.builder().id("image").media(media).build())).build())
                .ephemeralMessageParameters(callbackParameters()).build();
        respond(TRUE);
        assertTrue(client.executeWithResult(rich).isCallbackSuccess());
        String body = request().getBody().readUtf8();
        assertTrue(body.contains("rich upload"), body);
        assertTrue(body.contains("name=\"rich.jpg\""), body);
        assertTrue(body.contains("name=\"rich_message\""), body);
    }

    @Test
    void allMultipartTypesDispatchToTheirExistingBuilders() throws Exception {
        for (CallbackReplyMethod method : multipartMethods()) {
            respond(TRUE);
            assertTrue(client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS).isCallbackSuccess(), method.getClass().getSimpleName());
            RecordedRequest sent = request();
            assertTrue(sent.getHeader("Content-Type").startsWith("multipart/form-data"));
            assertTrue(sent.getBody().readUtf8().contains("\"callback_query_id\":\"cq-1\""));
        }
    }

    @Test
    void allJsonTypesDispatchToJsonTransport() throws Exception {
        EphemeralMessageParameters parameters = callbackParameters();
        List<CallbackReplyMethod> methods = List.of(
                SendMessage.builder().text("hello").ephemeralMessageParameters(parameters).build(),
                SendContact.builder().phoneNumber("123").firstName("name").ephemeralMessageParameters(parameters).build(),
                SendLocation.builder().latitude(1.0).longitude(2.0).ephemeralMessageParameters(parameters).build(),
                SendVenue.builder().latitude(1.0).longitude(2.0).title("title").address("address").ephemeralMessageParameters(parameters).build());
        for (CallbackReplyMethod method : methods) {
            respond(TRUE);
            assertTrue(client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS).isCallbackSuccess());
            RecordedRequest sent = request();
            assertEquals("/bottoken/" + ((PartialBotApiMethod<?>) method).getMethod(), sent.getPath());
            assertTrue(sent.getHeader("Content-Type").startsWith("application/json"));
            JsonNode body = MAPPER.readTree(sent.getBody().readUtf8());
            assertFalse(body.has("chat_id"));
            assertEquals("cq-1", body.path("ephemeral_message_parameters").path("callback_query_id").asText());
        }
    }

    @Test
    void invalidRequestsAreRejectedBeforeSending() {
        SendMessage missingChat = SendMessage.builder().text("hello").build();
        assertThrows(TelegramApiValidationException.class, () -> client.executeWithResult(missingChat));
        SendPhoto invalidBusiness = SendPhoto.builder().photo(new InputFile("file-id"))
                .businessConnectionId("business").ephemeralMessageParameters(callbackParameters()).build();
        ExecutionException failure = assertThrows(ExecutionException.class,
                () -> client.executeWithResultAsync(invalidBusiness).get(5, TimeUnit.SECONDS));
        assertInstanceOf(TelegramApiValidationException.class, failure.getCause());
        assertEquals(0, server.getRequestCount());
    }

    @Test
    void injectedMapperSerializationFailuresAreWrappedWithoutSending() throws Exception {
        SimpleModule module = new SimpleModule().addSerializer(EphemeralMessageParameters.class,
                new ValueSerializer<EphemeralMessageParameters>() {
                    @Override
                    public void serialize(EphemeralMessageParameters value, JsonGenerator generator, SerializationContext context) {
                        throw JacksonException.wrapWithPath(new IllegalStateException("serialization failed"), value, "callback_query_id");
                    }
                });
        HttpUrl url = server.url("");
        JsonMapper mapper = JsonMapper.builder().addModule(module).build();
        JettyTelegramClient customClient = new JettyTelegramClient(mapper, httpClient, "token", TelegramUrl.builder()
                .schema(url.scheme()).host(url.host()).port(url.port()).build());

        SendMessage ordinary = SendMessage.builder().chatId("chat").text("hello")
                .ephemeralMessageParameters(EphemeralMessageParameters.builder().receiverUserId(99L).build()).build();
        TelegramApiException legacy = assertThrows(TelegramApiException.class, () -> customClient.executeAsync(ordinary));
        assertInstanceOf(JacksonException.class, legacy.getCause());

        SendMessage callback = SendMessage.builder().text("hello").ephemeralMessageParameters(callbackParameters()).build();
        TelegramApiException json = assertThrows(TelegramApiException.class, () -> customClient.executeWithResultAsync(callback));
        assertInstanceOf(JacksonException.class, json.getCause());

        SendRichMessage rich = SendRichMessage.builder().richMessage(InputRichMessage.builder().html("hello").build())
                .ephemeralMessageParameters(callbackParameters()).build();
        ExecutionException multipart = assertThrows(ExecutionException.class,
                () -> customClient.executeWithResultAsync(rich).get(5, TimeUnit.SECONDS));
        TelegramApiException wrapped = assertInstanceOf(TelegramApiException.class, multipart.getCause());
        assertInstanceOf(JacksonException.class, wrapped.getCause());
        assertEquals(0, server.getRequestCount());
    }

    @Test
    void malformedAndApiErrorsArePropagated() throws Exception {
        SendMessage method = new SendMessage("chat", "hello");
        for (String invalid : List.of("not json", "{\"ok\":true,\"result\":false}", "{\"ok\":true,\"result\":null}", "{\"ok\":true,\"result\":{}}")) {
            respond(invalid);
            assertThrows(TelegramApiRequestException.class, () -> client.executeWithResult(method));
            request();
            respond(invalid);
            ExecutionException failure = assertThrows(ExecutionException.class,
                    () -> client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS));
            assertInstanceOf(TelegramApiRequestException.class, failure.getCause());
            request();
        }

        String error = "{\"ok\":false,\"error_code\":429,\"description\":\"too many requests\",\"parameters\":{\"retry_after\":7}}";
        server.enqueue(new MockResponse().setResponseCode(429).setBody(error));
        TelegramApiRequestException sync = assertThrows(TelegramApiRequestException.class, () -> client.executeWithResult(method));
        assertEquals(429, sync.getErrorCode());
        assertEquals("too many requests", sync.getApiResponse());
        assertEquals(7, sync.getParameters().getRetryAfter());
        request();
        server.enqueue(new MockResponse().setResponseCode(429).setBody(error));
        ExecutionException async = assertThrows(ExecutionException.class,
                () -> client.executeWithResultAsync(photoWithExistingFile()).get(5, TimeUnit.SECONDS));
        assertEquals(429, assertInstanceOf(TelegramApiRequestException.class, async.getCause()).getErrorCode());
        request();
    }

    @Test
    void transportFailuresCompleteExceptionally() throws Exception {
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START));
        ExecutionException failure = assertThrows(ExecutionException.class,
                () -> client.executeWithResultAsync(new SendMessage("chat", "hello")).get(5, TimeUnit.SECONDS));
        assertNotNull(failure.getCause());
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START));
        assertThrows(TelegramApiException.class, () -> client.executeWithResult(photoWithExistingFile()));
    }

    @Test
    void legacyJsonRejectsCallbackOnlyWithoutSending() throws Exception {
        EphemeralMessageParameters parameters = callbackParameters();
        List<BotApiMethodMessage> methods = List.of(
                SendMessage.builder().chatId("chat").text("hello").ephemeralMessageParameters(parameters).build(),
                SendContact.builder().chatId("chat").phoneNumber("123").firstName("name").ephemeralMessageParameters(parameters).build(),
                SendLocation.builder().chatId("chat").latitude(1.0).longitude(2.0).ephemeralMessageParameters(parameters).build(),
                SendVenue.builder().chatId("chat").latitude(1.0).longitude(2.0).title("title").address("address").ephemeralMessageParameters(parameters).build());
        for (BotApiMethodMessage method : methods) {
            assertThrows(TelegramApiValidationException.class, () -> client.execute(method));
            assertThrows(TelegramApiValidationException.class, () -> client.executeAsync(method));
        }
        assertEquals(0, server.getRequestCount());
    }

    @Test
    void everyLegacyMultipartOverloadRejectsWithoutSending() throws Exception {
        for (CallbackReplyMethod method : multipartMethods()) {
            assertThrows(TelegramApiValidationException.class, () -> legacySync(method), method.getClass().getSimpleName());
            ExecutionException failure = assertThrows(ExecutionException.class, () -> legacyAsync(method), method.getClass().getSimpleName());
            assertInstanceOf(TelegramApiValidationException.class, failure.getCause());
        }
        assertEquals(0, server.getRequestCount());
    }

    @Test
    void legacyNormalMessagesStillWork() throws Exception {
        respond(MESSAGE);
        assertEquals(42, client.execute(new SendMessage("chat", "hello")).getMessageId());
        request();
        respond(MESSAGE);
        assertEquals(42, client.executeAsync(photoWithExistingFile()).get(5, TimeUnit.SECONDS).getMessageId());
        request();
        SendMessage receiver = SendMessage.builder().chatId("chat").text("hello")
                .ephemeralMessageParameters(EphemeralMessageParameters.builder()
                        .receiverUserId(99L).callbackQueryId("cq-1").build()).build();
        respond(MESSAGE);
        assertEquals(42, client.execute(receiver).getMessageId());
        request();
    }

    private List<CallbackReplyMethod> multipartMethods() {
        EphemeralMessageParameters parameters = callbackParameters();
        return List.of(
                SendDocument.builder().chatId("chat").document(new InputFile("file-id")).ephemeralMessageParameters(parameters).build(),
                SendPhoto.builder().chatId("chat").photo(new InputFile("file-id")).ephemeralMessageParameters(parameters).build(),
                SendVideo.builder().chatId("chat").video(new InputFile("file-id")).ephemeralMessageParameters(parameters).build(),
                SendVideoNote.builder().chatId("chat").videoNote(new InputFile("file-id")).ephemeralMessageParameters(parameters).build(),
                SendSticker.builder().chatId("chat").sticker(new InputFile("file-id")).ephemeralMessageParameters(parameters).build(),
                SendAudio.builder().chatId("chat").audio(new InputFile("file-id")).ephemeralMessageParameters(parameters).build(),
                SendVoice.builder().chatId("chat").voice(new InputFile("file-id")).ephemeralMessageParameters(parameters).build(),
                SendAnimation.builder().chatId("chat").animation(new InputFile("file-id")).ephemeralMessageParameters(parameters).build(),
                SendLivePhoto.builder().chatId("chat").livePhoto(new InputFile("video-id")).photo(new InputFile("photo-id")).ephemeralMessageParameters(parameters).build(),
                SendRichMessage.builder().chatId("chat").richMessage(InputRichMessage.builder().html("hello").build()).ephemeralMessageParameters(parameters).build());
    }

    private void legacySync(CallbackReplyMethod method) throws Exception {
        if (method instanceof SendDocument send) client.execute(send);
        else if (method instanceof SendPhoto send) client.execute(send);
        else if (method instanceof SendVideo send) client.execute(send);
        else if (method instanceof SendVideoNote send) client.execute(send);
        else if (method instanceof SendSticker send) client.execute(send);
        else if (method instanceof SendAudio send) client.execute(send);
        else if (method instanceof SendVoice send) client.execute(send);
        else if (method instanceof SendAnimation send) client.execute(send);
        else if (method instanceof SendLivePhoto send) client.execute(send);
        else if (method instanceof SendRichMessage send) client.execute(send);
        else throw new AssertionError(method);
    }

    private void legacyAsync(CallbackReplyMethod method) throws Exception {
        if (method instanceof SendDocument send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendPhoto send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendVideo send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendVideoNote send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendSticker send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendAudio send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendVoice send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendAnimation send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendLivePhoto send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else if (method instanceof SendRichMessage send) client.executeAsync(send).get(5, TimeUnit.SECONDS);
        else throw new AssertionError(method);
    }

    private SendPhoto photoWithExistingFile() {
        return new SendPhoto("chat", new InputFile("file-id"));
    }

    private InputFile upload(String name) {
        return new InputFile(new ByteArrayInputStream("upload contents".getBytes(StandardCharsets.UTF_8)), name);
    }

    private EphemeralMessageParameters callbackParameters() {
        return EphemeralMessageParameters.builder().callbackQueryId("cq-1").build();
    }

    private void respond(String body) {
        server.enqueue(new MockResponse().setHeader("Content-Type", "application/json").setBody(body));
    }

    private RecordedRequest request() throws InterruptedException {
        RecordedRequest request = server.takeRequest(5, TimeUnit.SECONDS);
        assertNotNull(request);
        return request;
    }
}