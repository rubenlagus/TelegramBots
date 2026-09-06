package org.telegram.telegrambots.client;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.client.jetty.JettyTelegramClient;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.SetMyProfilePhoto;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.business.SetBusinessAccountProfilePhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditEphemeralMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhotoStatic;
import org.telegram.telegrambots.meta.api.objects.suggestedpost.SuggestedPostParameters;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTelegramClientIntegration {
    private MockWebServer webServer;

    private static final String TOKEN = "testToken";

    private AutoCloseable mockitoCloseable;

    JettyTelegramClient client;

    @BeforeEach
    void setUp() {
        mockitoCloseable = MockitoAnnotations.openMocks(this);
        webServer = new MockWebServer();
        HttpUrl mockUrl = webServer.url("");
        TelegramUrl telegramUrl = TelegramUrl.builder().schema(mockUrl.scheme()).host(mockUrl.host()).port(mockUrl.port()).build();
        client = new JettyTelegramClient(TOKEN, telegramUrl);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockitoCloseable.close();
    }

    @Test
    void testSendMessage() throws TelegramApiException {
        SendMessage method = new SendMessage("someChatId", "someText");
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.execute(method);
        assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void testSendPhoto() throws TelegramApiException {
        SendPhoto method = new SendPhoto("someChatId", new InputFile(getTestFile()));
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.execute(method);
        assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void testSendVideo() throws TelegramApiException {
        SendVideo method = new SendVideo("someChatId", new InputFile(getTestFile()));
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.execute(method);
        assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void testSendVideoNote() throws TelegramApiException {
        SendVideoNote method = new SendVideoNote("someChatId", new InputFile(getTestFile()));
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.execute(method);
        assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void testSendSticker() throws TelegramApiException {
        SendSticker method = new SendSticker("someChatId", new InputFile(getTestFile()));
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.execute(method);
        assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void testSendAudio() throws TelegramApiException {
        SendAudio method = new SendAudio("someChatId", new InputFile(getTestFile()));
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.execute(method);
        assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void testSendVoice() throws TelegramApiException {
        SendVoice method = new SendVoice("someChatId", new InputFile(getTestFile()));
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.execute(method);
        assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void testSendAnimation() throws TelegramApiException {
        SendAnimation method = new SendAnimation("someChatId", new InputFile(getTestFile()));
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.execute(method);
        assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void testDownloadFileAsStream() throws Exception {
        try (InputStream is = client.downloadFileAsStream("someFile")) {
            String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertNotNull(text);
            assertFalse(text.isEmpty());
        }
    }

    @Test
    void testSendMessageException() {
        SendMessage method = new SendMessage("someChatId", "someText");

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSendDocumentException() {
        SendDocument method = new SendDocument("someChatId", new InputFile(getTestFile()));

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSendPhotoException() {
        SendPhoto method = new SendPhoto("someChatId", new InputFile(getTestFile()));

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSendVideoException() {
        SendVideo method = new SendVideo("someChatId", new InputFile(getTestFile()));

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSendVideoNoteException() {
        SendVideoNote method = new SendVideoNote("someChatId", new InputFile(getTestFile()));

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSendStickerException() {
        SendSticker method = new SendSticker("someChatId", new InputFile(getTestFile()));

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSendAudioException() {
        SendAudio method = new SendAudio("someChatId", new InputFile(getTestFile()));

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSendVoiceException() {
        SendVoice method = new SendVoice("someChatId", new InputFile(getTestFile()));

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSendAnimationException() {
        SendAnimation method = new SendAnimation("someChatId", new InputFile(getTestFile()));

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSetBusinessAccountProfilePhoto() throws TelegramApiException {
        InputFile inputFile = new InputFile(getTestFile());
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(inputFile)
                .build();
        
        SetBusinessAccountProfilePhoto method = SetBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .photo(profilePhoto)
                .isPublic(true)
                .build();
    
        mockMethod(method, Boolean.TRUE);
    
        assertTrue(client.execute(method));
    }
    
    @Test
    void testSetBusinessAccountProfilePhotoException() {
        InputFile inputFile = new InputFile(getTestFile());
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(inputFile)
                .build();

        SetBusinessAccountProfilePhoto method = SetBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .photo(profilePhoto)
                .isPublic(true)
                .build();

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void testSetMyProfilePhoto() throws TelegramApiException {
        InputFile inputFile = new InputFile(getTestFile());
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(inputFile)
                .build();

        SetMyProfilePhoto method = SetMyProfilePhoto.builder()
                .photo(profilePhoto)
                .build();

        mockMethod(method, Boolean.TRUE);

        assertTrue(client.execute(method));
    }

    @Test
    void testSetMyProfilePhotoException() {
        InputFile inputFile = new InputFile(getTestFile());
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(inputFile)
                .build();

        SetMyProfilePhoto method = SetMyProfilePhoto.builder()
                .photo(profilePhoto)
                .build();

        mockErrorMethod(method);

        TelegramApiRequestException exception = Assertions.assertThrows(TelegramApiRequestException.class, () -> client.execute(method));
        assertEquals(404, exception.getErrorCode());
    }

    /**
     * Bot API 10.3 wire contract: the ephemeral parameters must reach the server as a single JSON
     * part named ephemeral_message_parameters, and the 10.2 flat parts must be gone. Also pins
     * suggested_post_parameters as JSON rather than a Lombok toString().
     */
    @Test
    void testSendPhotoWritesEphemeralParametersAsJsonPart() throws Exception {
        SendPhoto method = SendPhoto.builder()
                .chatId("someChatId")
                .photo(new InputFile(getTestFile()))
                .ephemeralMessageParameters(EphemeralMessageParameters.builder()
                        .receiverUserId(99L)
                        .callbackQueryId("cq-1")
                        .replaceCallbackQueryMessage(true)
                        .build())
                .suggestedPostParameters(SuggestedPostParameters.builder().sendDate(1700000000).build())
                .build();

        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");
        mockMethod(method, responseMessage);

        client.execute(method);

        String body = webServer.takeRequest().getBody().readUtf8();

        // 1. the nested object is present as a JSON part
        assertTrue(body.contains("name=\"ephemeral_message_parameters\""), body);
        assertTrue(body.contains("\"receiver_user_id\":99"), body);
        assertTrue(body.contains("\"callback_query_id\":\"cq-1\""), body);
        assertTrue(body.contains("\"replace_callback_query_message\":true"), body);

        // 2. the 10.2 flat parts must not exist any more
        assertFalse(body.contains("name=\"receiver_user_id\""), body);
        assertFalse(body.contains("name=\"callback_query_id\""), body);

        // 3. suggested_post_parameters is JSON, not Lombok's toString()
        assertTrue(body.contains("name=\"suggested_post_parameters\""), body);
        assertTrue(body.contains("\"send_date\":1700000000"), body);
        assertFalse(body.contains("SuggestedPostParameters("), body);
    }

    /**
     * Bot API 10.3 made editEphemeralMessageMedia a PartialBotApiMethod so a brand-new file can be
     * uploaded. Asserts the multipart body carries the media part and the scalar parameters.
     */
    @Test
    void testEditEphemeralMessageMediaUploadsNewFile() throws Exception {
        EditEphemeralMessageMedia method = EditEphemeralMessageMedia.builder()
                .chatId("someChatId")
                .receiverUserId(99L)
                .ephemeralMessageId(7)
                .media(new InputMediaPhoto(getTestFile(), "photo.jpg"))
                .build();

        mockMethod(method, true);

        assertTrue(client.execute(method));

        String body = webServer.takeRequest().getBody().readUtf8();
        assertTrue(body.contains("name=\"media\""), body);
        assertTrue(body.contains("name=\"chat_id\""), body);
        assertTrue(body.contains("name=\"receiver_user_id\""), body);
        assertTrue(body.contains("name=\"ephemeral_message_id\""), body);
    }

    @NotNull
    private File getTestFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test_file.txt").getFile());
        return file;
    }

    <T extends Serializable, Method extends PartialBotApiMethod<T>> void mockMethod(Method method, T result) {
        webServer.setDispatcher(getDispatcher(method, true, result));
    }

    private <T extends Serializable, Method extends PartialBotApiMethod<T>> Dispatcher getDispatcher(Method method, boolean success, T result) {
        return new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                try {
                    String path = request.getPath();
                    if (path.endsWith(method.getMethod())) {
                        String response;
                        if (success) {
                            response = new ApiResponseTestBuilder<T>().setOk(true).setResult(result).buildJson();
                        } else {
                            response = new ApiResponseTestBuilder<>().setOk(false).setErrorCode(404).setErrorDescription("Method not found").buildJson();
                        }
                        return new MockResponse()
                                .addHeader("Content-Type", "application/json; charset=utf-8")
                                .addHeader("Cache-Control", "no-cache")
                                .setBody(response);
                    }
                } catch (Exception e) {
                    return new MockResponse().setResponseCode(404).setBody(new ApiResponseTestBuilder<>().setOk(false).setErrorCode(404).setErrorDescription("Method not found").buildJson());
                }
                return new MockResponse().setResponseCode(404).setBody(new ApiResponseTestBuilder<>().setOk(false).setErrorCode(404).setErrorDescription("Method not found").buildJson());
            }
        };
    }

    private <T extends Serializable, Method extends PartialBotApiMethod<T>> void mockErrorMethod(Method method) {
        webServer.setDispatcher(getDispatcher(method, false, null));
    }
}
