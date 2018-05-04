package org.telegram.abilitybots.api.bot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.EndUser;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;

import java.io.IOException;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;
import static org.telegram.abilitybots.api.objects.EndUser.endUser;

public class AbilityBotI18nTest {
    public static final EndUser NO_LANGUAGE_USER = endUser(1, "first", "last", "username", null);
    public static final EndUser ITALIAN_USER = endUser(2, "first", "last", "username", Locale.ITALY);

    private DBContext db;
    private DefaultBot bot;

    private NoPublicCommandsBot noCommandsBot;

    private MessageSender sender;
    private SilentSender silent;

    @Before
    public void setUp() {
        db = offlineInstance("db");
        bot = new DefaultBot(EMPTY, EMPTY, db);

        silent = mock(SilentSender.class);

        bot.sender = sender;
        bot.silent = silent;
    }

    @Test
    public void missingPublicCommandsLocalizedCorrectly() {
        NoPublicCommandsBot noCommandsBot = new NoPublicCommandsBot(EMPTY, EMPTY, db);
        noCommandsBot.silent = silent;

        MessageContext context = mock(MessageContext.class);
        when(context.chatId()).thenReturn(Long.valueOf(NO_LANGUAGE_USER.id()));
        when(context.user()).thenReturn(NO_LANGUAGE_USER);

        noCommandsBot.reportCommands().action().accept(context);

        verify(silent, times(1))
                .send("No public commands found.", NO_LANGUAGE_USER.id());

        //

        MessageContext context1 = mock(MessageContext.class);
        when(context1.chatId()).thenReturn(Long.valueOf(ITALIAN_USER.id()));
        when(context1.user()).thenReturn(ITALIAN_USER);

        noCommandsBot.reportCommands().action().accept(context1);

        verify(silent, times(1))
                .send("Non sono presenti comandi pubblici.", ITALIAN_USER.id());
    }


    @After
    public void tearDown() throws IOException {
        db.clear();
        db.close();
    }
}
