package org.telegram.abilitybots.api.sender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
}