package org.telegram.telegrambots.longpolling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.telegram.telegrambots.longpolling.util.DefaultGetUpdatesGenerator;
import org.telegram.telegrambots.longpolling.util.ExponentialBackOff;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestTelegramBotsLongPollingApplicationIntegration {
    private MockWebServer webServer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private TelegramUrl telegramUrl;
    private TelegramBotsLongPollingApplication application;

    @Spy
    private ExponentialBackOff exponentialBackOff = new ExponentialBackOff();

    private AutoCloseable mockitoCloseable;

    @BeforeEach
    public void setUp() {
        mockitoCloseable = MockitoAnnotations.openMocks(this);
        webServer = new MockWebServer();
        HttpUrl mockUrl = webServer.url("");
        telegramUrl = TelegramUrl.builder().schema(mockUrl.scheme()).host(mockUrl.host()).port(mockUrl.port()).build();
        application = new TelegramBotsLongPollingApplication(
                ObjectMapper::new,
                new TelegramOkHttpClientFactory.DefaultOkHttpClientCreator(),
                Executors::newSingleThreadScheduledExecutor,
                () -> exponentialBackOff
                );
    }

    @AfterEach
    public void tearDown() throws Exception {
        application.close();
        mockitoCloseable.close();
    }

    @Test
    public void testIsRunningCheck() {
        try {
            List<Update> updateReceived = new ArrayList<>();

            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            assertTrue(application.isRunning());

            application.registerBot("TOKEN",
                    () -> telegramUrl,
                    new DefaultGetUpdatesGenerator(),
                    (LongPollingSingleThreadUpdateConsumer) update -> updateReceived.add(update));

            await().atMost(5, TimeUnit.SECONDS).until(() -> updateReceived.size() == 2);

            assertTrue(application.isRunning());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testStopExceptionWhenAlreadyStopped() {
        try {
            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            assertTrue(application.isRunning());

            application.stop();
            application.stop();
            fail("Failed to raise exception when stopping already stopped apps");
        } catch (TelegramApiException e) {
            assertEquals("App is not running", e.getMessage());
        }
    }

    @Test
    public void testStartExceptionWhenAlreadyStarted() {
        try {
            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            assertTrue(application.isRunning());

            application.start();
            fail("Failed to raise exception when starting already started apps");
        } catch (TelegramApiException e) {
            assertEquals("App is already running", e.getMessage());
        }
    }

    @Test
    public void testStartStop() {
        try {
            List<Update> updateReceived = new ArrayList<>();

            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            assertTrue(application.isRunning());

            application.stop();

            assertFalse(application.isRunning());

            application.registerBot("TOKEN",
                    () -> telegramUrl,
                    new DefaultGetUpdatesGenerator(),
                    (LongPollingSingleThreadUpdateConsumer) update -> updateReceived.add(update));

            assertFalse(application.isRunning());

            await().timeout(5, TimeUnit.SECONDS).until(updateReceived::isEmpty);

            application.start();

            assertTrue(application.isRunning());

            await().timeout(5, TimeUnit.SECONDS).until(() -> !updateReceived.isEmpty());

            application.stop();

            assertFalse(application.isRunning());

            assertEquals(2, updateReceived.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testUnregisterBot() {
        try {
            List<Update> updateReceived = new ArrayList<>();

            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            application.stop();

            application.registerBot("TOKEN",
                    () -> telegramUrl,
                    new DefaultGetUpdatesGenerator(),
                    (LongPollingSingleThreadUpdateConsumer) update -> updateReceived.add(update));

            application.unregisterBot("TOKEN");

            application.start();

            await().timeout(5, TimeUnit.SECONDS).until(updateReceived::isEmpty);

            assertTrue(updateReceived.isEmpty());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testRegisterSameBotTwiceRaisesException() {
        try {

            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            assertTrue(application.isRunning());

            application.registerBot("TOKEN",
                    () -> telegramUrl,
                    new DefaultGetUpdatesGenerator(),
                    (LongPollingSingleThreadUpdateConsumer) update -> {

                    });

            application.registerBot("TOKEN",
                    () -> telegramUrl,
                    new DefaultGetUpdatesGenerator(),
                    (LongPollingSingleThreadUpdateConsumer) update -> {
                    });

            fail("Exception not raised when duplicating bots");
        } catch (TelegramApiException e) {
            assertEquals("Bot is already registered", e.getMessage());
        }
    }

    @Test
    public void testUnregisterNonRegisteredBotRaisesException() {
        try {

            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            assertTrue(application.isRunning());

            application.unregisterBot("TOKEN");

            fail("Exception not raised when duplicating bots");
        } catch (TelegramApiException e) {
            assertEquals("Bot is not registered", e.getMessage());
        }
    }

    @Test
    public void testUpdatesAreReceived() {
        try {
            List<Update> updateReceived = new ArrayList<>();

            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            application.registerBot("TOKEN",
                    () -> telegramUrl,
                    new DefaultGetUpdatesGenerator(),
                    (LongPollingSingleThreadUpdateConsumer) update -> updateReceived.add(update));

            await().atMost(5, TimeUnit.SECONDS).until(() -> updateReceived.size() == 2);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testOutOfOrderUpdatesAreIgnored() {
        try {
            List<Update> updateReceived = new ArrayList<>();

            Dispatcher dispatcher = getDispatcher(List.of(getFakeUpdates1(), getFakeUpdates1(), getFakeUpdates2()));
            webServer.setDispatcher(dispatcher);

            application.registerBot("TOKEN",
                    () -> telegramUrl,
                    new DefaultGetUpdatesGenerator(),
                    (LongPollingSingleThreadUpdateConsumer) update -> updateReceived.add(update));

            await().atMost(5, TimeUnit.SECONDS).until(() -> updateReceived.size() == 4);

            assertEquals(4, updateReceived.stream().map(Update::getUpdateId).distinct().count());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testWhenErrorResponseBackOffIsExecuted() {
        try {
            List<Update> updateReceived = new ArrayList<>();

            Dispatcher dispatcher = getDispatcher(List.of("WRONGRESPONSE", getFakeUpdates1()));
            webServer.setDispatcher(dispatcher);

            application.registerBot("TOKEN",
                    () -> telegramUrl,
                    new DefaultGetUpdatesGenerator(),
                    (LongPollingSingleThreadUpdateConsumer) update -> updateReceived.add(update));


            await().atMost(5, TimeUnit.SECONDS).until(() -> updateReceived.size() == 2);

            verify(exponentialBackOff, times(1)).nextBackOffMillis();
        } catch (Exception e) {
            fail(e);
        }
    }

    @NonNull
    private Dispatcher getDispatcher(List<Object> responses) {
        return new Dispatcher() {
            @NonNull
            @Override
            public MockResponse dispatch(@NonNull RecordedRequest request) throws InterruptedException {
                try {
                    switch (request.getPath()) {
                        case "/botTOKEN/deleteWebhook":
                            return mockResponse(ApiResponse.<Boolean>builder().ok(true).result(true).build());
                        case "/botTOKEN/getupdates":
                            if (responses.size() >= request.getSequenceNumber()) {
                                return mockResponse(responses.get(request.getSequenceNumber() - 1));
                            } else {
                                return mockResponse(ApiResponse.<List<Update>>builder().ok(true).result(new ArrayList<>()).build());
                            }
                    }
                } catch (Exception e) {
                    return new MockResponse().setResponseCode(404);
                }
                return new MockResponse().setResponseCode(404);
            }
        };
    }

    private MockResponse mockResponse(Object responseObject) throws JsonProcessingException {
        return new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(objectMapper.writeValueAsString(responseObject));
    }

    private ApiResponse<List<Update>> getFakeUpdates1() {
        Update update1 = new Update();
        update1.setUpdateId(1);
        Update update2 = new Update();
        update2.setUpdateId(2);
        return ApiResponse.<List<Update>>builder().ok(true).result(List.of(update1, update2)).build();
    }

    private ApiResponse<List<Update>> getFakeUpdates2() {
        Update update1 = new Update();
        update1.setUpdateId(3);
        Update update2 = new Update();
        update2.setUpdateId(4);
        return ApiResponse.<List<Update>>builder().ok(true).result(List.of(update1, update2)).build();
    }
}
