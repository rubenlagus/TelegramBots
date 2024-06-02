package org.telegram.telegrambots.abilitybots.api.sender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestSilentSender {
  private SilentSender silent;
  private TelegramClient telegramClient;

  @BeforeEach
  void setUp() {
    telegramClient = mock(TelegramClient.class);
    silent = spy(new SilentSender(telegramClient));
  }

  @Test
  void returnsEmptyOnError() throws TelegramApiException {
    when(telegramClient.execute(isNull(BotApiMethod.class))).thenThrow(TelegramApiException.class);

    Optional<Serializable> execute = silent.execute(null);

    assertFalse(execute.isPresent(), "Execution of a bot API method with execption results in a nonempty optional");
  }

  @Test
  void returnOptionalOnSuccess() throws TelegramApiException {
    String data = "data";
    when(telegramClient.execute(isNull(BotApiMethod.class))).thenReturn(data);

    Optional<Serializable> execute = silent.execute(null);

    assertEquals(data, execute.get(), "Silent execution resulted in a different object");
  }

  @Test
  void callsAsyncVariantOfExecute() throws TelegramApiException {
    when(telegramClient.executeAsync(any(BotApiMethod.class))).thenReturn(CompletableFuture.completedFuture(null));

    SendMessage methodObject = new SendMessage("1234", "Text");
    NoOpCallback callback = new NoOpCallback();

    silent.executeAsync(methodObject, callback);

    verify(telegramClient, only()).executeAsync(methodObject);
  }

  private static class NoOpCallback implements SentCallback<Message> {

    @Override
    public void onResult(BotApiMethod<Message> method, Message response) {

    }

    @Override
    public void onException(BotApiMethod<Message> method, Exception exception) {

    }
  }
}