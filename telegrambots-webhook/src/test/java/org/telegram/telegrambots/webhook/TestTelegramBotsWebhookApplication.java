package org.telegram.telegrambots.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.common.webhook.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
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
        webhookOptions = WebhookOptions.builder().enableRequestLogging(true).build();

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
    public void tearDown() throws Exception {
        application.close();
    }

    @Test
    public void testWhenUpdateIsReceivedOnWebhookUpdateReceivedIsCalled() throws IOException {
        application.registerBot(telegramWebhookBot);

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
    public void testAfterRestartServerExistingBotsAreRegistered() throws IOException {
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
    public void testUnregisterBotRemoveHandler() throws IOException {
        application.registerBot(telegramWebhookBot);
        application.unregisterBot(telegramWebhookBot);

        Request request = new Request.Builder()
                .url("http://127.0.0.1:" + webhookOptions.getPort() + "/test")
                .headers(Headers.of(headers))
                .post(RequestBody.create(objectMapper.writeValueAsString(update), MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).execute();

        assertNull(telegramWebhookBot.updateReceived);
    }

    @Test
    public void testRegisterBotWithSamePathOverridePreviousOne() throws IOException {
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
    public void testServerStartOnCreation() throws TelegramApiException, IOException {
        assertTrue(application.isRunning());
    }

    @Test
    public void testStartAndStopMethodsKillServer() throws TelegramApiException, IOException {
        application.stop();

        assertFalse(application.isRunning());

        application.start();

        assertTrue(application.isRunning());
    }



    private static class TestTelegramWebhookBot implements TelegramWebhookBot {
        public Update updateReceived;
        private String path;

        public TestTelegramWebhookBot(String path) {
            this.path = path;
        }

        @Override
        public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
            updateReceived = update;
            return null;
        }

        @Override
        public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {

        }

        @Override
        public String getBotPath() {
            return path;
        }
    }
}
