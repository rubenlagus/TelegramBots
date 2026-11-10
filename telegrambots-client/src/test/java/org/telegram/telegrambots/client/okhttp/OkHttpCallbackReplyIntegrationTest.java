package org.telegram.telegrambots.client.okhttp;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.SetMyProfilePhoto;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditEphemeralMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.message.SendMessageResult;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhotoStatic;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class OkHttpCallbackReplyIntegrationTest {
    private static final String TRUE_RESPONSE = "{\"ok\":true,\"result\":true}";
    private static final String MESSAGE_RESPONSE = "{\"ok\":true,\"result\":{\"message_id\":42,\"date\":123,\"chat\":{\"id\":99,\"type\":\"private\"},\"text\":\"hello\"}}";
    private final JsonMapper mapper = new JsonMapper();
    private MockWebServer server;
    private OkHttpTelegramClient client;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        HttpUrl url = server.url("/");
        client = new OkHttpTelegramClient(new OkHttpClient.Builder().retryOnConnectionFailure(false).build(), "token", TelegramUrl.builder()
                .schema(url.scheme()).host(url.host()).port(url.port()).build());
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void syncJsonReturnsMessageAndPreservesLegacyNormalSend() throws Exception {
        SendMessage method = SendMessage.builder().chatId("99").text("hello").build();
        enqueue(MESSAGE_RESPONSE);
        SendMessageResult result = client.executeWithResult(method);
        assertFalse(result.isCallbackSuccess());
        assertEquals(42, result.getMessage().getMessageId());
        RecordedRequest request = request();
        assertEquals("/bottoken/sendmessage", request.getPath());
        JsonNode body = mapper.readTree(request.getBody().readUtf8());
        assertEquals("99", body.path("chat_id").asText());
        assertEquals("hello", body.path("text").asText());
        assertFalse(body.has("callbackOnly"));

        enqueue(MESSAGE_RESPONSE);
        assertEquals(42, client.execute(method).getMessageId());
        request();
    }

    @Test
    void syncAndAsyncJsonSendCallbackOnlyAndParseTrue() throws Exception {
        SendMessage method = SendMessage.builder().text("hello").ephemeralMessageParameters(callback()).build();
        enqueue(TRUE_RESPONSE);
        assertTrue(client.executeWithResult(method).isCallbackSuccess());
        assertCallbackJson(request());

        enqueue(TRUE_RESPONSE);
        SendMessageResult result = client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS);
        assertTrue(result.isCallbackSuccess());
        assertNull(result.getMessage());
        assertCallbackJson(request());
    }

    @Test
    void configuredMapperIsUsedForJsonAndMultipart() throws Exception {
        client = clientWithMapper(ephemeralMapper(false));
        SendMessage legacy = SendMessage.builder().chatId("99").text("hello")
                .ephemeralMessageParameters(EphemeralMessageParameters.builder().receiverUserId(123L).build()).build();
        enqueue(MESSAGE_RESPONSE);
        assertEquals(42, client.execute(legacy).getMessageId());
        assertTrue(request().getBody().readUtf8().contains("configured-value"));

        SendMessage callback = SendMessage.builder().text("hello").ephemeralMessageParameters(callback()).build();
        enqueue(TRUE_RESPONSE);
        assertTrue(client.executeWithResult(callback).isCallbackSuccess());
        assertTrue(request().getBody().readUtf8().contains("configured-value"));

        List<CallbackReplyMethod> multipart = List.of(
                SendRichMessage.builder().richMessage(InputRichMessage.builder().html("hello").build())
                        .ephemeralMessageParameters(callback()).build(),
                SendPhoto.builder().photo(new InputFile("file-id")).ephemeralMessageParameters(callback()).build());
        for (CallbackReplyMethod method : multipart) {
            enqueue(TRUE_RESPONSE);
            assertTrue(client.executeWithResult(method).isCallbackSuccess());
            assertTrue(request().getBody().readUtf8().contains("configured-value"));
        }
    }

    @Test
    void serializationFailuresAreReportedAsTelegramApiExceptions() throws Exception {
        client = clientWithMapper(ephemeralMapper(true));
        SendMessage legacy = SendMessage.builder().chatId("99").text("hello")
                .ephemeralMessageParameters(EphemeralMessageParameters.builder().receiverUserId(123L).build()).build();
        TelegramApiException legacyError = assertThrows(TelegramApiException.class, () -> client.executeAsync(legacy));
        assertInstanceOf(JacksonException.class, legacyError.getCause());

        SendMessage callback = SendMessage.builder().text("hello").ephemeralMessageParameters(callback()).build();
        TelegramApiException asyncJsonError = assertThrows(TelegramApiException.class, () -> client.executeWithResultAsync(callback));
        assertInstanceOf(JacksonException.class, asyncJsonError.getCause());
        TelegramApiException syncJsonError = assertThrows(TelegramApiException.class, () -> client.executeWithResult(callback));
        assertInstanceOf(JacksonException.class, syncJsonError.getCause());

        List<CallbackReplyMethod> multipart = List.of(
                SendRichMessage.builder().richMessage(InputRichMessage.builder().html("hello").build())
                        .ephemeralMessageParameters(callback()).build(),
                SendPhoto.builder().photo(new InputFile("file-id")).ephemeralMessageParameters(callback()).build());
        for (CallbackReplyMethod method : multipart) {
            ExecutionException asyncError = assertThrows(ExecutionException.class,
                    () -> client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS));
            TelegramApiException cause = assertInstanceOf(TelegramApiException.class, asyncError.getCause());
            assertInstanceOf(JacksonException.class, cause.getCause());
            TelegramApiException syncError = assertThrows(TelegramApiException.class, () -> client.executeWithResult(method));
            assertInstanceOf(JacksonException.class, syncError.getCause());
        }
        assertEquals(0, server.getRequestCount());
    }

    @Test
    void otherMultipartSerializationFailuresCompleteFuturesExceptionally() throws Exception {
        JsonMapper failingMapper = new JsonMapper() {
            @Override
            public String writeValueAsString(Object value) {
                throw JacksonException.wrapWithPath(new IllegalStateException("Intentional serialization failure"), value, "test");
            }
        };
        client = clientWithMapper(failingMapper);
        SetMyProfilePhoto profile = SetMyProfilePhoto.builder()
                .photo(InputProfilePhotoStatic.builder().photo(new InputFile("file-id")).build()).build();
        EditEphemeralMessageMedia edit = EditEphemeralMessageMedia.builder().chatId("99").receiverUserId(123L)
                .ephemeralMessageId(42).media(new InputMediaPhoto("file-id")).build();

        for (CompletableFuture<?> future : List.of(client.executeAsync(profile), client.executeAsync(edit))) {
            ExecutionException error = assertThrows(ExecutionException.class, () -> future.get(5, TimeUnit.SECONDS));
            TelegramApiException cause = assertInstanceOf(TelegramApiException.class, error.getCause());
            assertInstanceOf(JacksonException.class, cause.getCause());
        }
        assertEquals(0, server.getRequestCount());
    }

    @Test
    void animationAndLivePhotoSendOnlyOneKeyboardPart() throws Exception {
        for (CallbackReplyMethod method : List.of(
                SendAnimation.builder().animation(new InputFile("animation-id"))
                        .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build())
                        .ephemeralMessageParameters(callback()).build(),
                SendLivePhoto.builder().livePhoto(upload("live.jpg")).photo(upload("photo.jpg"))
                        .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build())
                        .ephemeralMessageParameters(callback()).build())) {
            enqueue(TRUE_RESPONSE);
            assertTrue(client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS).isCallbackSuccess());
            String body = request().getBody().readUtf8();
            assertEquals(1, body.split("name=\"reply_markup\"", -1).length - 1, body);
            if (method instanceof SendLivePhoto) {
                assertEquals(2, body.split("upload contents", -1).length - 1, body);
            }
        }
    }

    @Test
    void asyncRichAndMultipartUseExistingBuilders() throws Exception {
        SendRichMessage rich = SendRichMessage.builder().richMessage(InputRichMessage.builder().html("hello").build())
                .ephemeralMessageParameters(callback()).build();
        enqueue(TRUE_RESPONSE);
        assertTrue(client.executeWithResultAsync(rich).get(5, TimeUnit.SECONDS).isCallbackSuccess());
        RecordedRequest richRequest = request();
        assertEquals("/bottoken/sendRichMessage", richRequest.getPath());
        String richBody = richRequest.getBody().readUtf8();
        assertTrue(richBody.contains("name=\"rich_message\""), richBody);
        assertCallbackMultipart(richBody);

        InputFile upload = new InputFile(new ByteArrayInputStream("photo bytes".getBytes(StandardCharsets.UTF_8)), "photo.jpg");
        SendPhoto photo = SendPhoto.builder().photo(upload).caption("caption").ephemeralMessageParameters(callback()).build();
        enqueue(MESSAGE_RESPONSE);
        SendMessageResult result = client.executeWithResultAsync(photo).get(5, TimeUnit.SECONDS);
        assertFalse(result.isCallbackSuccess());
        assertEquals(42, result.getMessage().getMessageId());
        RecordedRequest photoRequest = request();
        assertEquals("/bottoken/sendphoto", photoRequest.getPath());
        String photoBody = photoRequest.getBody().readUtf8();
        assertTrue(photoBody.contains("photo bytes"), photoBody);
        assertTrue(photoBody.contains("name=\"caption\""), photoBody);
        assertCallbackMultipart(photoBody);
    }

    @Test
    void legacyMediaStillAcceptsReceiverBasedEphemeralSends() throws Exception {
        SendPhoto photo = SendPhoto.builder().chatId("99").photo(new InputFile("file-id"))
                .ephemeralMessageParameters(EphemeralMessageParameters.builder()
                        .receiverUserId(123L).callbackQueryId("callback-1").build()).build();
        enqueue(MESSAGE_RESPONSE);
        assertEquals(42, client.execute(photo).getMessageId());
        String body = request().getBody().readUtf8();
        assertTrue(body.contains("\"receiver_user_id\":123"), body);
        assertTrue(body.contains("name=\"chat_id\""), body);
    }

    @Test
    void syncMultipartAndRichAcceptCallbackSuccess() throws Exception {
        SendRichMessage rich = SendRichMessage.builder().richMessage(InputRichMessage.builder().html("hello").build())
                .ephemeralMessageParameters(callback()).build();
        enqueue(TRUE_RESPONSE);
        assertTrue(client.executeWithResult(rich).isCallbackSuccess());
        assertCallbackMultipart(request().getBody().readUtf8());

        SendPhoto photo = SendPhoto.builder().photo(new InputFile("file-id"))
                .ephemeralMessageParameters(callback()).build();
        enqueue(TRUE_RESPONSE);
        assertTrue(client.executeWithResult(photo).isCallbackSuccess());
        assertCallbackMultipart(request().getBody().readUtf8());
    }

    @Test
    void validationFailuresDoNotReachTransport() throws Exception {
        SendMessage invalidJson = SendMessage.builder().text("").ephemeralMessageParameters(callback()).build();
        assertThrows(TelegramApiValidationException.class, () -> client.executeWithResult(invalidJson));
        assertThrows(TelegramApiValidationException.class, () -> client.executeWithResultAsync(invalidJson));

        SendPhoto invalidMedia = SendPhoto.builder().photo(new InputFile("file-id"))
                .businessConnectionId("business").ephemeralMessageParameters(callback()).build();
        assertThrows(TelegramApiValidationException.class, () -> client.executeWithResult(invalidMedia));
        ExecutionException error = assertThrows(ExecutionException.class,
                () -> client.executeWithResultAsync(invalidMedia).get(5, TimeUnit.SECONDS));
        assertInstanceOf(TelegramApiValidationException.class, error.getCause());
        assertEquals(0, server.getRequestCount());
    }

    @Test
    void malformedAndApiErrorsPropagateSynchronouslyAndAsynchronously() throws Exception {
        SendMessage method = SendMessage.builder().text("hello").ephemeralMessageParameters(callback()).build();
        for (String response : List.of("not json", "{\"ok\":true,\"result\":false}", "{\"ok\":true}")) {
            enqueue(response);
            assertThrows(TelegramApiRequestException.class, () -> client.executeWithResult(method));
            request();
            enqueue(response);
            ExecutionException error = assertThrows(ExecutionException.class,
                    () -> client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS));
            assertInstanceOf(TelegramApiRequestException.class, error.getCause());
            request();
        }
        String apiError = "{\"ok\":false,\"error_code\":429,\"description\":\"Too Many Requests\",\"parameters\":{\"retry_after\":3}}";
        server.enqueue(new MockResponse().setResponseCode(429).setBody(apiError));
        TelegramApiRequestException error = assertThrows(TelegramApiRequestException.class, () -> client.executeWithResult(method));
        assertEquals(429, error.getErrorCode());
        assertEquals(3, error.getParameters().getRetryAfter());
        request();
        server.enqueue(new MockResponse().setResponseCode(429).setBody(apiError));
        ExecutionException asyncError = assertThrows(ExecutionException.class,
                () -> client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS));
        TelegramApiRequestException cause = assertInstanceOf(TelegramApiRequestException.class, asyncError.getCause());
        assertEquals(429, cause.getErrorCode());
        assertEquals(3, cause.getParameters().getRetryAfter());
        request();
    }

    @Test
    void transportFailuresCompleteTheFutureExceptionally() throws Exception {
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));
        SendMessage method = SendMessage.builder().text("hello").ephemeralMessageParameters(callback()).build();
        ExecutionException error = assertThrows(ExecutionException.class,
                () -> client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS));
        assertInstanceOf(IOException.class, error.getCause());
        request();
    }

    @Test
    void asyncMultipartAndRichPropagateApiAndParserErrors() throws Exception {
        List<CallbackReplyMethod> methods = List.of(
                SendRichMessage.builder().richMessage(InputRichMessage.builder().html("hello").build())
                        .ephemeralMessageParameters(callback()).build(),
                SendPhoto.builder().photo(new InputFile("file-id")).ephemeralMessageParameters(callback()).build());
        for (CallbackReplyMethod method : methods) {
            enqueue("{\"ok\":true,\"result\":false}");
            ExecutionException malformed = assertThrows(ExecutionException.class,
                    () -> client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS));
            assertInstanceOf(TelegramApiRequestException.class, malformed.getCause());
            request();
            enqueue("{\"ok\":false,\"error_code\":400,\"description\":\"Bad Request\"}");
            ExecutionException error = assertThrows(ExecutionException.class,
                    () -> client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS));
            assertEquals(400, assertInstanceOf(TelegramApiRequestException.class, error.getCause()).getErrorCode());
            request();
        }
    }

    @Test
    void legacyOverloadsAndGenericJsonNeverSendCallbackOnly() throws Exception {
        List<CallbackReplyMethod> methods = callbackMethods();
        for (CallbackReplyMethod method : methods) {
            assertTrue(method.isCallbackOnly());
            if (method instanceof BotApiMethod<?>) {
                @SuppressWarnings("unchecked")
                BotApiMethod<Message> generic = (BotApiMethod<Message>) method;
                assertThrows(TelegramApiValidationException.class, () -> client.execute(generic));
                assertThrows(TelegramApiValidationException.class, () -> client.executeAsync(generic));
            } else {
                InvocationTargetException sync = assertThrows(InvocationTargetException.class,
                        () -> client.getClass().getMethod("execute", method.getClass()).invoke(client, method));
                assertInstanceOf(TelegramApiValidationException.class, sync.getCause(), method.getClass().getSimpleName());
                try {
                    CompletableFuture<?> future = (CompletableFuture<?>) client.getClass()
                            .getMethod("executeAsync", method.getClass()).invoke(client, method);
                    ExecutionException error = assertThrows(ExecutionException.class, () -> future.get(5, TimeUnit.SECONDS));
                    assertInstanceOf(TelegramApiValidationException.class, error.getCause(), method.getClass().getSimpleName());
                } catch (InvocationTargetException e) {
                    assertInstanceOf(TelegramApiValidationException.class, e.getCause(), method.getClass().getSimpleName());
                }
            }
        }
        SendMessage withChat = SendMessage.builder().chatId("99").text("hello")
                .ephemeralMessageParameters(callback()).build();
        assertThrows(TelegramApiValidationException.class, () -> client.execute(withChat));
        assertThrows(TelegramApiValidationException.class, () -> client.executeAsync(withChat));
        assertEquals(0, server.getRequestCount());
    }

    @Test
    void allSupportedRequestsUseOptInTransport() throws Exception {
        for (CallbackReplyMethod method : callbackMethods()) {
            enqueue(TRUE_RESPONSE);
            assertTrue(client.executeWithResultAsync(method).get(5, TimeUnit.SECONDS).isCallbackSuccess(),
                    method.getClass().getSimpleName());
            RecordedRequest request = request();
            assertEquals("/bottoken/" + ((PartialBotApiMethod<?>) method).getMethod(), request.getPath());
            String body = request.getBody().readUtf8();
            assertTrue(body.contains("callback-1"), method.getClass().getSimpleName() + ": " + body);
        }
        assertEquals(14, server.getRequestCount());
    }

    private List<CallbackReplyMethod> callbackMethods() {
        InputFile file = new InputFile("file-id");
        EphemeralMessageParameters parameters = callback();
        return List.of(
                SendMessage.builder().text("hello").ephemeralMessageParameters(parameters).build(),
                SendContact.builder().phoneNumber("123").firstName("name").ephemeralMessageParameters(parameters).build(),
                SendLocation.builder().latitude(1.0).longitude(2.0).ephemeralMessageParameters(parameters).build(),
                SendVenue.builder().latitude(1.0).longitude(2.0).title("title").address("address").ephemeralMessageParameters(parameters).build(),
                SendRichMessage.builder().richMessage(InputRichMessage.builder().html("hello").build()).ephemeralMessageParameters(parameters).build(),
                SendDocument.builder().document(file).ephemeralMessageParameters(parameters).build(),
                SendPhoto.builder().photo(file).ephemeralMessageParameters(parameters).build(),
                SendVideo.builder().video(file).ephemeralMessageParameters(parameters).build(),
                SendVideoNote.builder().videoNote(file).ephemeralMessageParameters(parameters).build(),
                SendSticker.builder().sticker(file).ephemeralMessageParameters(parameters).build(),
                SendAudio.builder().audio(file).ephemeralMessageParameters(parameters).build(),
                SendVoice.builder().voice(file).ephemeralMessageParameters(parameters).build(),
                SendAnimation.builder().animation(file).ephemeralMessageParameters(parameters).build(),
                SendLivePhoto.builder().livePhoto(file).photo(file).ephemeralMessageParameters(parameters).build());
    }

    private EphemeralMessageParameters callback() {
        return EphemeralMessageParameters.builder().callbackQueryId("callback-1").build();
    }

    private InputFile upload(String name) {
        return new InputFile(new ByteArrayInputStream("upload contents".getBytes(StandardCharsets.UTF_8)), name);
    }

    private OkHttpTelegramClient clientWithMapper(JsonMapper jsonMapper) {
        HttpUrl url = server.url("/");
        return new OkHttpTelegramClient(jsonMapper, new OkHttpClient.Builder().retryOnConnectionFailure(false).build(),
                "token", TelegramUrl.builder().schema(url.scheme()).host(url.host()).port(url.port()).build());
    }

    private JsonMapper ephemeralMapper(boolean fail) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(EphemeralMessageParameters.class, new ValueSerializer<EphemeralMessageParameters>() {
            @Override
            public void serialize(EphemeralMessageParameters value, JsonGenerator generator, SerializationContext context) {
                if (fail) {
                    context.reportMappingProblem("Intentional serialization failure");
                }
                generator.writeStartObject();
                generator.writeStringProperty("callback_query_id", "configured-value");
                generator.writeEndObject();
            }
        });
        return JsonMapper.builder().addModule(module).build();
    }

    private void enqueue(String response) {
        server.enqueue(new MockResponse().setBody(response));
    }

    private RecordedRequest request() throws InterruptedException {
        RecordedRequest request = server.takeRequest(5, TimeUnit.SECONDS);
        assertNotNull(request);
        return request;
    }

    private void assertCallbackJson(RecordedRequest request) throws IOException {
        JsonNode body = mapper.readTree(request.getBody().readUtf8());
        assertFalse(body.has("chat_id"));
        assertFalse(body.has("callbackOnly"));
        assertEquals("callback-1", body.path("ephemeral_message_parameters").path("callback_query_id").asText());
        assertFalse(body.path("ephemeral_message_parameters").has("receiver_user_id"));
    }

    private void assertCallbackMultipart(String body) {
        assertTrue(body.contains("name=\"ephemeral_message_parameters\""), body);
        assertTrue(body.contains("\"callback_query_id\":\"callback-1\""), body);
        assertFalse(body.contains("name=\"chat_id\""), body);
        assertFalse(body.contains("receiver_user_id"), body);
    }
}