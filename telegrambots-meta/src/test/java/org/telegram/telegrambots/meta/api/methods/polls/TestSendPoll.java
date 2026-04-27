package org.telegram.telegrambots.meta.api.methods.polls;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.polls.input.InputPollOption;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 9.6
 */
public class TestSendPoll {

    private List<InputPollOption> buildOptions() {
        return Arrays.asList(
                InputPollOption.builder().text("Option 1").build(),
                InputPollOption.builder().text("Option 2").build()
        );
    }

    @Test
    public void testSendPollGetPath() {
        SendPoll sendPoll = SendPoll.builder()
                .chatId("123456789")
                .question("Test question?")
                .options(buildOptions())
                .build();

        assertEquals("sendPoll", sendPoll.getMethod());
    }

    @Test
    public void testSendPollValidation() {
        SendPoll sendPoll = SendPoll.builder()
                .chatId("123456789")
                .question("Test question?")
                .options(buildOptions())
                .build();

        assertDoesNotThrow(sendPoll::validate);
    }

    @Test
    public void testSendPollOpenPeriodMax() {
        // Max open_period is now 2628000 (was 600)
        SendPoll sendPoll = SendPoll.builder()
                .chatId("123456789")
                .question("Test question?")
                .options(buildOptions())
                .openPeriod(2628000)
                .build();

        assertDoesNotThrow(sendPoll::validate);
    }

    @Test
    public void testSendPollOpenPeriodExceedsMax() {
        SendPoll sendPoll = SendPoll.builder()
                .chatId("123456789")
                .question("Test question?")
                .options(buildOptions())
                .openPeriod(2628001)
                .build();

        assertThrows(TelegramApiValidationException.class, sendPoll::validate);
    }

    @Test
    public void testSendPollWithCorrectOptionIds() {
        SendPoll sendPoll = SendPoll.builder()
                .chatId("123456789")
                .question("Test question?")
                .options(buildOptions())
                .type("quiz")
                .correctOptionIds(Arrays.asList(0, 1))
                .build();

        assertEquals(Arrays.asList(0, 1), sendPoll.getCorrectOptionIds());
        assertDoesNotThrow(sendPoll::validate);
    }

    @Test
    public void testSendPollWithDescription() {
        SendPoll sendPoll = SendPoll.builder()
                .chatId("123456789")
                .question("Test question?")
                .options(buildOptions())
                .description("This is a poll description")
                .build();

        assertEquals("This is a poll description", sendPoll.getDescription());
        assertDoesNotThrow(sendPoll::validate);
    }

    @Test
    public void testSendPollDescriptionParseModeConflict() {
        List<MessageEntity> entities = new ArrayList<>();
        entities.add(MessageEntity.builder().type("bold").offset(0).length(4).build());

        SendPoll sendPoll = SendPoll.builder()
                .chatId("123456789")
                .question("Test question?")
                .options(buildOptions())
                .description("Bold text")
                .descriptionParseMode("Markdown")
                .descriptionEntities(entities)
                .build();

        assertThrows(TelegramApiValidationException.class, sendPoll::validate);
    }

    @Test
    public void testSendPollWithNewOptionalParams() {
        SendPoll sendPoll = SendPoll.builder()
                .chatId("123456789")
                .question("Test question?")
                .options(buildOptions())
                .allowsRevoting(true)
                .shuffleOptions(true)
                .allowAddingOptions(true)
                .hideResultsUntilCloses(true)
                .build();

        assertEquals(true, sendPoll.getAllowsRevoting());
        assertEquals(true, sendPoll.getShuffleOptions());
        assertEquals(true, sendPoll.getAllowAddingOptions());
        assertEquals(true, sendPoll.getHideResultsUntilCloses());
        assertDoesNotThrow(sendPoll::validate);
    }
}
