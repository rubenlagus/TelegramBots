package org.telegram.telegrambots.meta.test.apimethods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
class TestAnswerInlineQuery {
    private AnswerInlineQuery answerInlineQuery;

    @BeforeEach
    void setUp() {
        answerInlineQuery = new AnswerInlineQuery();
    }

    @Test
    void TestInlineQueryIdMustBePresent() {
        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            assertEquals("InlineQueryId can't be empty", e.getMessage());
        }
    }

    @Test
    void TestInlineQueryIdCanNotBeEmpty() {
        answerInlineQuery.setInlineQueryId("");
        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            assertEquals("InlineQueryId can't be empty", e.getMessage());
        }
    }

    @Test
    void TestResultsMustBePresent() {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            assertEquals("Results array can't be null", e.getMessage());
        }
    }

    @Test
    void TestSwitchPmTextCanNotBeEmpty() {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            assertEquals("SwitchPmText can't be empty", e.getMessage());
        }
    }

    @Test
    void TestSwitchPmParameterIsMandatoryIfSwitchPmTextIsPresent() {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("Test Text");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            assertEquals("SwitchPmParameter can't be empty if switchPmText is present", e.getMessage());
        }
    }

    @Test
    void TestSwitchPmParameterCanNotBeEmptyIfSwitchPmTextIsPresent() {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("Test Text");
        answerInlineQuery.setSwitchPmParameter("");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            assertEquals("SwitchPmParameter can't be empty if switchPmText is present", e.getMessage());
        }
    }

    @Test
    void TestSwitchPmParameterContainsUpTo64Chars() {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("Test Text");
        answerInlineQuery.setSwitchPmParameter("2AAQlw4BwzXwFNXMk5rReQC3YbhbgNqq4BGqyozjRTtrsok4shsB8u4NXeslfpOsL");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            assertEquals("SwitchPmParameter can't be longer than 64 chars", e.getMessage());
        }
    }

    @Test
    void TestSwitchPmParameterOnlyContainsAcceptedCharacters() {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("Test Text");
        answerInlineQuery.setSwitchPmParameter("*");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            assertEquals("SwitchPmParameter only allows A-Z, a-z, 0-9, _ and - characters", e.getMessage());
        }
    }
}
