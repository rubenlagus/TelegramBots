package org.telegram.telegrambots.client.okhttp;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import okio.Timeout;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class OkHttpFutureCallbackTest {
    @Test
    void completesExceptionallyWhenResponseBodyReadFails() {
        SocketException failure = new SocketException("Socket closed");
        ResponseBody body = new FailingResponseBody(failure);
        Request request = new Request.Builder().url("https://api.telegram.org").build();
        Response response = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(body)
                .build();
        OkHttpFutureCallback<Message, SendMessage> callback = new OkHttpFutureCallback<>(
                new SendMessage("chatId", "text"));

        assertDoesNotThrow(() -> callback.onResponse(mock(Call.class), response));

        assertTrue(callback.isCompletedExceptionally());
        ExecutionException exception = assertThrows(ExecutionException.class, callback::get);
        assertSame(failure, exception.getCause());
    }

    private static class FailingResponseBody extends ResponseBody {
        private final BufferedSource source;

        private FailingResponseBody(IOException failure) {
            source = Okio.buffer(new Source() {
                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    throw failure;
                }

                @Override
                public Timeout timeout() {
                    return Timeout.NONE;
                }

                @Override
                public void close() {
                }
            });
        }

        @Override
        public MediaType contentType() {
            return MediaType.get("application/json");
        }

        @Override
        public long contentLength() {
            return -1;
        }

        @Override
        public BufferedSource source() {
            return source;
        }
    }
}
