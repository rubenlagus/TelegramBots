package org.telegram.telegrambots.meta.api.objects.message;

import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class TestSendMessageResult {
    private final SendMessage method = new SendMessage("1", "hello");
    private final JsonMapper mapper = new JsonMapper();

    @Test
    void parsesBothVariantsAndRoundTripsResult() throws Exception {
        SendMessageResult message = SendMessageResult.deserializeResponse("{\"ok\":true,\"result\":{\"message_id\":3,"
                + "\"date\":1,\"chat\":{\"id\":1,\"type\":\"private\"}}}", method);
        assertFalse(message.isCallbackSuccess());
        assertEquals(3, message.getMessage().getMessageId());
        assertEquals(message, mapper.readValue(mapper.writeValueAsString(message), SendMessageResult.class));
        SendMessageResult callback = SendMessageResult.deserializeResponse("{\"ok\":true,\"result\":true}", method);
        assertTrue(callback.isCallbackSuccess());
        assertNull(callback.getMessage());
        assertEquals(callback, mapper.readValue(mapper.writeValueAsString(callback), SendMessageResult.class));
        assertThrows(IllegalArgumentException.class, () -> new SendMessageResult(null, false));
        assertThrows(IllegalArgumentException.class, () -> new SendMessageResult(message.getMessage(), true));
    }

    @Test
    void rejectsMalformedResponsesAndPreservesTelegramError() {
        for (String invalid : new String[]{"not-json", "{}", "{\"ok\":null}", "{\"ok\":true}",
                "{\"ok\":true,\"result\":false}", "{\"ok\":true,\"result\":[]}", "{\"ok\":true,\"result\":{}}"}) {
            assertThrows(TelegramApiRequestException.class, () -> SendMessageResult.deserializeResponse(invalid, method));
        }
        TelegramApiRequestException error = assertThrows(TelegramApiRequestException.class, () ->
                SendMessageResult.deserializeResponse("{\"ok\":false,\"error_code\":429,\"description\":\"slow down\","
                        + "\"parameters\":{\"retry_after\":4}}", method));
        assertEquals(429, error.getErrorCode());
        assertEquals("slow down", error.getApiResponse());
        assertEquals(4, error.getParameters().getRetryAfter());
    }

    @Test
    void legacyOnlyCustomClientKeepsGenericExecution() throws Exception {
        Message message = mapper.readValue("{\"message_id\":1,\"date\":1,\"chat\":{\"id\":1,\"type\":\"private\"}}", Message.class);
        TelegramClient custom = (TelegramClient) Proxy.newProxyInstance(TelegramClient.class.getClassLoader(),
                new Class<?>[]{TelegramClient.class}, (proxy, called, args) -> {
                    if (called.isDefault()) return InvocationHandler.invokeDefault(proxy, called, args);
                    if (called.getName().equals("execute")) return message;
                    if (called.getName().equals("executeAsync")) return CompletableFuture.completedFuture(message);
                    throw new UnsupportedOperationException();
                });
        assertSame(message, custom.execute(method));
        assertSame(message, custom.executeAsync(method).get());
        assertThrows(UnsupportedOperationException.class, () -> custom.executeWithResult(method));
        assertThrows(UnsupportedOperationException.class, () -> custom.executeWithResultAsync(method));
    }
}