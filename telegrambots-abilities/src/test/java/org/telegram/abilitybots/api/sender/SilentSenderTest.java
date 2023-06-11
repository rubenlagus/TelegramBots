package org.telegram.abilitybots.api.sender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SilentSenderTest {
  private SilentSender silent;
  private MessageSender sender;

  @BeforeEach
  void setUp() {
    sender = mock(MessageSender.class);
    silent = new SilentSender(sender);
  }

  @Test
  void returnsEmptyOnError() throws TelegramApiException {
    when(sender.execute(any())).thenThrow(TelegramApiException.class);

    Optional execute = silent.execute(null);

    assertFalse(execute.isPresent(), "Execution of a bot API method with execption results in a nonempty optional");
  }

  @Test
  void returnOptionalOnSuccess() throws TelegramApiException {
    String data = "data";
    when(sender.execute(any())).thenReturn(data);

    Optional execute = silent.execute(null);

    assertEquals(data, execute.get(), "Silent execution resulted in a different object");
  }

  @Test
  void callsAsyncVariantOfExecute() throws TelegramApiException {
    SendMessage methodObject = new SendMessage();
    NoOpCallback callback = new NoOpCallback();

    silent.executeAsync(methodObject, callback);

    verify(sender, only()).executeAsync(methodObject, callback);
  }

  private static class NoOpCallback implements SentCallback<Message> {

    @Override
    public void onResult(BotApiMethod<Message> method, Message response) {

    }

    @Override
    public void onError(BotApiMethod<Message> method, TelegramApiRequestException apiException) {

    }

    @Override
    public void onException(BotApiMethod<Message> method, Exception exception) {

    }
  }
}