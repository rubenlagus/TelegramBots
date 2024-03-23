package org.telegram.telegrambots.webhook.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(
        classes = {
                TestTelegramBotsWebhookUpdateHandler.TestApplication.class,
                TelegramBotStarterConfiguration.class,
                TestTelegramBotsWebhookUpdateHandler.WebhookBotConfig.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TestTelegramBotsWebhookUpdateHandler {

    private static final AtomicReference<Update> updateReceived = new AtomicReference<>(null);
    private static final AtomicReference<Update> updateReceived2 = new AtomicReference<>(null);

    private static final AtomicBoolean setWebhookCalled = new AtomicBoolean(false);
    private static final AtomicBoolean deleteWebhookCalled = new AtomicBoolean(false);

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    public TelegramBotsSpringWebhookApplication telegramApplication;

    @Autowired
    @Qualifier("webhookBot")
    public SpringTelegramWebhookBot testBot;

    @Autowired
    @Qualifier("webhookBot2")
    public SpringTelegramWebhookBot testBot2;

    @BeforeEach
    public void setUp() throws TelegramApiException {
        if (!telegramApplication.isRunning()) {
            telegramApplication.start();
        }
        updateReceived.set(null);
        updateReceived2.set(null);
        setWebhookCalled.set(false);
        deleteWebhookCalled.set(false);
    }

    @AfterEach
    public void cleanUp() throws TelegramApiException {
        if (!telegramApplication.isRunning()) {
            telegramApplication.start();
        }
        telegramApplication.unregisterBot(testBot);
        telegramApplication.unregisterBot(testBot2);
        updateReceived.set(null);
        updateReceived2.set(null);
        setWebhookCalled.set(false);
        deleteWebhookCalled.set(false);
    }

    @Test
    public void testStartStopApplication() {
        try {
            assertFalse(setWebhookCalled.get());
            assertFalse(deleteWebhookCalled.get());

            telegramApplication.registerBot(testBot);

            assertTrue(setWebhookCalled.get());
            assertFalse(deleteWebhookCalled.get());

            Update update = new Update();
            update.setUpdateId(12345);

            telegramApplication.stop();

            assertTrue(setWebhookCalled.get());
            assertTrue(deleteWebhookCalled.get());

            ResponseEntity<GetMe> response = this.restTemplate.exchange("http://localhost:" + port + "/mytestbot",
                    HttpMethod.POST,
                    new HttpEntity<>(update),
                    GetMe.class
            );

            assertFalse(response.getStatusCode().is2xxSuccessful());
            assertNull(updateReceived.get());

            setWebhookCalled.set(false);
            deleteWebhookCalled.set(false);

            telegramApplication.start();

            assertTrue(setWebhookCalled.get());
            assertFalse(deleteWebhookCalled.get());

            ResponseEntity<GetMe> response2 = this.restTemplate.exchange("http://localhost:" + port + "/mytestbot",
                    HttpMethod.POST,
                    new HttpEntity<>(update),
                    GetMe.class
            );
            assertTrue(response2.getStatusCode().is2xxSuccessful());

            await().atMost(10, TimeUnit.SECONDS).until(() -> updateReceived.get() != null);

            assertNotNull(response2);
            assertNotNull(response2.getBody());
            assertNotNull(updateReceived.get());
            assertEquals(12345, updateReceived.get().getUpdateId());
        } catch (TelegramApiException e) {
            fail(e);
        }
    }

    @Test
    public void testUpdatesReachCorrectBot() throws Exception {
        telegramApplication.registerBot(testBot);
        telegramApplication.registerBot(testBot2);

        Update update = new Update();
        update.setUpdateId(12345);

        ResponseEntity<GetMe> response = this.restTemplate.exchange("http://localhost:" + port + "/mytestbot",
                HttpMethod.POST,
                new HttpEntity<>(update),
                GetMe.class
        );
        assertTrue(response.getStatusCode().is2xxSuccessful());

        await().atMost(10, TimeUnit.SECONDS).until(() -> updateReceived.get() != null);

        assertNotNull(response.getBody());
        assertNull(updateReceived2.get());
        assertNotNull(updateReceived.get());
        assertEquals(12345, updateReceived.get().getUpdateId());

        ResponseEntity<GetMe> response2 = this.restTemplate.exchange("http://localhost:" + port + "/mytestbot2",
                HttpMethod.POST,
                new HttpEntity<>(update),
                GetMe.class
        );
        assertTrue(response2.getStatusCode().is2xxSuccessful());

        await().atMost(10, TimeUnit.SECONDS).until(() -> updateReceived2.get() != null);

        assertNotNull(response2.getBody());
        assertNotNull(updateReceived2.get());
        assertEquals(12345, updateReceived2.get().getUpdateId());
    }

    @Test
    public void testUnregisterBotRemoveItFromMapping() throws Exception {
        telegramApplication.registerBot(testBot);

        Update update = new Update();
        update.setUpdateId(12345);

        ResponseEntity<GetMe> response = this.restTemplate.exchange("http://localhost:" + port + "/mytestbot",
                HttpMethod.POST,
                new HttpEntity<>(update),
                GetMe.class
        );
        assertTrue(response.getStatusCode().is2xxSuccessful());

        await().atMost(10, TimeUnit.SECONDS).until(() -> updateReceived.get() != null);

        assertNotNull(response.getBody());
        assertNotNull(updateReceived.get());
        assertEquals(12345, updateReceived.get().getUpdateId());

        telegramApplication.unregisterBot(testBot);

        update.setUpdateId(99999);

        ResponseEntity<GetMe> response2 = this.restTemplate.exchange("http://localhost:" + port + "/mytestbot2",
                HttpMethod.POST,
                new HttpEntity<>(update),
                GetMe.class
        );
        assertTrue(response2.getStatusCode().is2xxSuccessful());

        await().atMost(10, TimeUnit.SECONDS).until(() -> updateReceived.get() != null);

        assertNull(response2.getBody());
        assertNotNull(updateReceived.get());
        assertEquals(12345, updateReceived.get().getUpdateId());
    }

    @Test
    public void testSetAndDeleteWebhookCalled() throws Exception {
        assertFalse(setWebhookCalled.get());
        assertFalse(deleteWebhookCalled.get());
        
        telegramApplication.registerBot(testBot);
        
        assertTrue(setWebhookCalled.get());
        assertFalse(deleteWebhookCalled.get());

        telegramApplication.unregisterBot(testBot);

        assertTrue(setWebhookCalled.get());
        assertTrue(deleteWebhookCalled.get());
    }

    @TestConfiguration
    static class WebhookBotConfig {
        @Bean(name = "webhookBot")
        public SpringTelegramWebhookBot webhookBot() {
            return new SpringTelegramWebhookBot(
                    "mytestbot",
                    update -> {
                        updateReceived.set(update);
                        return new GetMe();
                    },
                    () -> setWebhookCalled.set(true),
                    () -> deleteWebhookCalled.set(true)
            );
        }

        @Bean(name = "webhookBot2")
        public SpringTelegramWebhookBot webhookBot2() {
            return new SpringTelegramWebhookBot(
                    "mytestbot2",
                    update -> {
                        updateReceived2.set(update);
                        return new GetMe();
                    },
                    () -> {},
                    () -> {}
            );
        }
    }

    @SpringBootApplication
    public static class TestApplication {
        public static void main(String[] args) {
            SpringApplication.run(TestApplication.class, args);
        }

    }
}
