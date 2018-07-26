package org.telegram.abilitybots.api.sender;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SilentSenderTest {
  private SilentSender silent;
  private MessageSender sender;

  @Before
  public void setUp() {
    sender = mock(MessageSender.class);
    silent = new SilentSender(sender);
  }

  @Test
  public void returnsEmptyOnError() throws TelegramApiException {
    when(sender.execute(any())).thenThrow(TelegramApiException.class);

    Optional execute = silent.execute(null);

    assertFalse("Execution of a bot API method with execption results in a nonempty optional", execute.isPresent());
  }

  @Test
  public void returnOptionalOnSuccess() throws TelegramApiException {
    String data = "data";
    when(sender.execute(any())).thenReturn(data);

    Optional execute = silent.execute(null);

    assertEquals("Silent execution resulted in a different object", data, execute.get());
  }
}