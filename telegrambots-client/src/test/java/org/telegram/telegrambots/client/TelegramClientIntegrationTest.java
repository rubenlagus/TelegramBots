package org.telegram.telegrambots.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class TelegramClientIntegrationTest {

    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().notifier(new ConsoleNotifier(true)))
            .build();

    @Test
    void test_send_message() throws TelegramApiException, ExecutionException, InterruptedException, JsonProcessingException, NoSuchFieldException, IllegalAccessException {

        SendMessage method = new SendMessage("someChatId", "someText");
        Message responseMessage = new Message();
        responseMessage.setChat(TestData.GROUP_CHAT);
        responseMessage.setFrom(TestData.TEST_USER);
        responseMessage.setText("someText");

        String botToken = "TestToken";

        WireMock wireMock = wm.getRuntimeInfo().getWireMock();
        mockMethod(method, botToken, wireMock, responseMessage);

        TelegramClient client = new TelegramClient(botToken, wm.getRuntimeInfo().getHttpBaseUrl());

        Message parsedMessage = client.execute(method).get();
        Assertions.assertEquals("someText", parsedMessage.getText());
        Assertions.assertEquals(responseMessage, parsedMessage);

        // Do some testing...
    }

    <T extends Serializable, Method extends BotApiMethod<T>> void mockMethod(Method method, String token, WireMock wireMock, T result) throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
        String response = new ApiResponseTestBuilder<T>().setOk(true).setResult(result).buildJson();

        wireMock.register(post("/bot" + token + "/" + method.getMethod()).willReturn(ok(response)));
    }
}
