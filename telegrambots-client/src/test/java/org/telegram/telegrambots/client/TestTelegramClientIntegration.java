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
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.business.SetBusinessAccountProfilePhoto;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.description.GetMyDescription;
import org.telegram.telegrambots.meta.api.methods.description.GetMyShortDescription;
import org.telegram.telegrambots.meta.api.methods.description.SetMyDescription;
import org.telegram.telegrambots.meta.api.methods.description.SetMyShortDescription;
import org.telegram.telegrambots.meta.api.methods.name.GetMyName;
import org.telegram.telegrambots.meta.api.methods.name.SetMyName;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.description.BotDescription;
import org.telegram.telegrambots.meta.api.objects.description.BotShortDescription;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.name.BotName;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhotoStatic;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTelegramClientIntegration {
    private MockWebServer webServer;

    private static final String TOKEN = "testToken";

    private AutoCloseable mockitoCloseable;

    OkHttpTelegramClient client;

    @BeforeEach
    void setUp() {
        mockitoCloseable = MockitoAnnotations.openMocks(this);
        webServer = new MockWebServer();
        HttpUrl mockUrl = webServer.url("");
        TelegramUrl telegramUrl = TelegramUrl.builder().schema(mockUrl.scheme()).host(mockUrl.host()).port(mockUrl.port()).build();
        client = new OkHttpTelegramClient(TOKEN, telegramUrl);
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
    void testGetMyName() throws TelegramApiException {
        var method = new GetMyName();

        var expected = new BotName(TestData.BOT_USER.getFirstName());
        mockMethod(method, expected);

        var actual = client.execute(method);
        assertEquals(expected, actual);
    }

    @Test
    void testSetMyName() throws TelegramApiException {
        var method = new SetMyName();
        method.setName(TestData.BOT_USER.getFirstName());

        mockMethod(method, Boolean.TRUE);

        assertTrue(client.execute(method));
    }

    @Test
    void testGetMyDescription() throws TelegramApiException {
        var method = new GetMyDescription();

        var expected = new BotDescription("description");
        mockMethod(method, expected);

        var actual = client.execute(method);
        assertEquals(expected, actual);
    }

    @Test
    void testSetMyDescription() throws TelegramApiException {
        var method = new SetMyDescription();
        method.setDescription("description");

        mockMethod(method, Boolean.TRUE);

        assertTrue(client.execute(method));
    }

    @Test
    void testGetMyShortDescription() throws TelegramApiException {
        var method = new GetMyShortDescription();

        var expected = new BotShortDescription("short");
        mockMethod(method, expected);

        var actual = client.execute(method);
        assertEquals(expected, actual);
    }

    @Test
    void testSetMyShortDescription() throws TelegramApiException {
        var method = new SetMyShortDescription();
        method.setShortDescription("description");

        mockMethod(method, Boolean.TRUE);

        assertTrue(client.execute(method));
    }

    @Test
    void testGetMyCommands() throws TelegramApiException {
        var method = new GetMyCommands();

        var expected = new ArrayList<BotCommand>();
        expected.add(BotCommand.builder()
                .command("command")
                .description("description")
                .build());
        mockMethod(method, expected);

        var actual = client.execute(method);
        assertEquals(expected, actual);
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
