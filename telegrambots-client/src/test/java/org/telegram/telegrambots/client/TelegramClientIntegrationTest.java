package org.telegram.telegrambots.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.http.Body;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class TelegramClientIntegrationTest {

    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().notifier(new ConsoleNotifier(true)))
            .build();

    private static final String TOKEN = "testToken";

    WireMock wireMock;
    OkHttpTelegramClient client;

    @BeforeEach
    void setup() {
        wireMock = wm.getRuntimeInfo().getWireMock();
        client = new OkHttpTelegramClient(TOKEN, wm.getRuntimeInfo().getHttpBaseUrl());
    }

    @Test
    void test_send_message() throws TelegramApiException, ExecutionException, InterruptedException {

        SendMessage method = new SendMessage("someChatId", "someText");
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        mockMethod(method, responseMessage);

        Message parsedMessage = client.executeAsync(method).get();
        Assertions.assertEquals(responseMessage, parsedMessage);
    }

    @Test
    void api_error_throws_exception() {
        SendMessage method = new SendMessage("someChatId", "someText");

        mockErrorMethod(method.getMethod());

        ExecutionException e = Assertions.assertThrows(ExecutionException.class, () -> client.executeAsync(method).get());

        Assertions.assertTrue(e.getCause() instanceof TelegramApiRequestException);

        TelegramApiRequestException exception = (TelegramApiRequestException) e.getCause();

        Assertions.assertEquals(404, exception.getErrorCode());
    }

    <T extends Serializable, Method extends BotApiMethod<T>> void mockMethod(Method method, T result) {
        String response = new ApiResponseTestBuilder<T>().setOk(true).setResult(result).buildJson();

        wireMock.register(post("/bot" + TOKEN + "/" + method.getMethod()).willReturn(ok(response)));
    }

    void mockErrorMethod(String path) {
        String response = new ApiResponseTestBuilder<>().setOk(false).setErrorCode(404).setErrorDescription("Method not found").buildJson();

        wireMock.register(post("/bot" + TOKEN + "/" + path).willReturn(notFound().withResponseBody(Body.fromJsonBytes(response.getBytes()))));
    }
}
