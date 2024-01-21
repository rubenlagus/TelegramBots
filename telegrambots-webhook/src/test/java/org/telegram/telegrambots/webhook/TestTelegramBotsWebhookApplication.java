package org.telegram.telegrambots.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestTelegramBotsWebhookApplication {
    // TODO Add test exceptions
    // TODO Add Tests HTTPS

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TestTelegramWebhookBot telegramWebhookBot;
    private WebhookOptions webhookOptions;
    private OkHttpClient httpClient;
    private Map<String, String> headers;
    private Update update;
    private TelegramBotsWebhookApplication application;

    @BeforeEach
    public void setUp() throws TelegramApiException {
        httpClient = new OkHttpClient.Builder().build();
        webhookOptions = WebhookOptions.builder().port(15847).enableRequestLogging(true).build();

        headers = Map.ofEntries(
                Map.entry("charset", StandardCharsets.UTF_8.name()),
                Map.entry("content-type", "application/json")
        );

        update = new Update();
        update.setUpdateId(1234567);

        telegramWebhookBot = new TestTelegramWebhookBot("/test");
        application = new TelegramBotsWebhookApplication(webhookOptions);
    }

    @AfterEach
    public void tearDown() throws TelegramApiException {
        application.close();
    }

    @Test
    public void testWhenUpdateIsReceivedOnWebhookUpdateReceivedIsCalled() throws IOException, TelegramApiException {
        application.registerBot(telegramWebhookBot);

        Request request = new Request.Builder()
                .url("http://127.0.0.1:" + webhookOptions.getPort() + "/test")
                .headers(Headers.of(headers))
                .post(RequestBody.create(objectMapper.writeValueAsString(update), MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).execute();

        assertNotNull(telegramWebhookBot.updateReceived);
        assertTrue(telegramWebhookBot.isSetWebhookCalled());
        assertFalse(telegramWebhookBot.isDeleteWebhookCalled());
        assertEquals(update.getUpdateId(), telegramWebhookBot.updateReceived.getUpdateId());
    }

    @Test
    public void testAfterRestartServerExistingBotsAreRegistered() throws IOException, TelegramApiException {
        application.registerBot(telegramWebhookBot);

        application.stop();
        assertFalse(application.isRunning());
        application.start();
        assertTrue(application.isRunning());

        Request request = new Request.Builder()
                .url("http://127.0.0.1:" + webhookOptions.getPort() + "/test")
                .headers(Headers.of(headers))
                .post(RequestBody.create(objectMapper.writeValueAsString(update), MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).execute();

        assertNotNull(telegramWebhookBot.updateReceived);
        assertEquals(update.getUpdateId(), telegramWebhookBot.updateReceived.getUpdateId());
    }

    @Test
    public void testUnregisterBotRemoveHandler() throws IOException, TelegramApiException {
        application.registerBot(telegramWebhookBot);
        application.unregisterBot(telegramWebhookBot);

        Request request = new Request.Builder()
                .url("http://127.0.0.1:" + webhookOptions.getPort() + "/test")
                .headers(Headers.of(headers))
                .post(RequestBody.create(objectMapper.writeValueAsString(update), MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).execute();

        assertNull(telegramWebhookBot.updateReceived);
        assertTrue(telegramWebhookBot.isSetWebhookCalled());
        assertTrue(telegramWebhookBot.isDeleteWebhookCalled());
    }

    @Test
    public void testRegisterBotWithSamePathOverridePreviousOne() throws IOException, TelegramApiException {
        TestTelegramWebhookBot secondBot = new TestTelegramWebhookBot("/test");
        application.registerBot(telegramWebhookBot);
        application.registerBot(secondBot);

        Request request = new Request.Builder()
                .url("http://127.0.0.1:" + webhookOptions.getPort() + "/test")
                .headers(Headers.of(headers))
                .post(RequestBody.create(objectMapper.writeValueAsString(update), MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).execute();

        assertNull(telegramWebhookBot.updateReceived);
        assertNotNull(secondBot.updateReceived);
        assertEquals(update.getUpdateId(), secondBot.updateReceived.getUpdateId());
    }

    @Test
    public void testWhenUpdateIsReceivedOnWebhookUpdateReceivedIsCalledOnCorrectBot() throws TelegramApiException, IOException {
        application.registerBot(telegramWebhookBot);
        TestTelegramWebhookBot telegramWebhookBot2 = new TestTelegramWebhookBot("/test2");
        application.registerBot(telegramWebhookBot2);

        Request request = new Request.Builder()
                .url("http://127.0.0.1:" + webhookOptions.getPort() + "/test")
                .headers(Headers.of(headers))
                .post(RequestBody.create(objectMapper.writeValueAsString(update), MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).execute();

        assertNotNull(telegramWebhookBot.updateReceived);
        assertEquals(update.getUpdateId(), telegramWebhookBot.updateReceived.getUpdateId());
        assertNull(telegramWebhookBot2.updateReceived);
    }

    @Test
    public void testWhenUpdateIsReceivedOnWebhookUpdateReceivedIsNotCalledIfWrongPath() throws TelegramApiException, IOException {
        application.registerBot(telegramWebhookBot);

        Request request = new Request.Builder()
                .url("http://127.0.0.1:" + webhookOptions.getPort() + "/testWrong")
                .headers(Headers.of(headers))
                .post(RequestBody.create(objectMapper.writeValueAsString(update), MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).execute();

        assertNull(telegramWebhookBot.updateReceived);
    }


    @Test
    public void testServerStartOnCreation() {
        assertTrue(application.isRunning());
    }

    @Test
    public void testStartAndStopMethodsKillServer() throws TelegramApiException {
        application.stop();

        assertFalse(application.isRunning());

        application.start();

        assertTrue(application.isRunning());
    }


    @Getter
    private static class TestTelegramWebhookBot extends DefaultTelegramWebhookBot {
        private final boolean exceptionWhenSettingWebhook;
        private final boolean exceptionWhenDeletingWebhook;
        private final String path;
        private Update updateReceived;
        private boolean setWebhookCalled;
        private boolean deleteWebhookCalled;

        public TestTelegramWebhookBot(String path) {
            this(path, false, false);
        }

        public TestTelegramWebhookBot(String botPath, boolean exceptionWhenSettingWebhook, boolean exceptionWhenDeletingWebhook) {
            super(botPath, null, null, null);
            this.path = botPath;
            this.exceptionWhenSettingWebhook = exceptionWhenSettingWebhook;
            this.exceptionWhenDeletingWebhook = exceptionWhenDeletingWebhook;
        }

        @Override
        public void runSetWebhook() {
            setWebhookCalled = true;
        }

        @Override
        public void runDeleteWebhook() {
            deleteWebhookCalled = true;
        }

        @Override
        public BotApiMethod<?> consumeUpdate(Update update) {
            updateReceived = update;
            return null;
        }

        @Override
        public String getBotPath() {
            return path;
        }
    }
}
