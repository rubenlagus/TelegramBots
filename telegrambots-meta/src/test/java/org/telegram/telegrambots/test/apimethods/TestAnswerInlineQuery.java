package org.telegram.telegrambots.test.apimethods;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.util.ArrayList;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestAnswerInlineQuery {
    private AnswerInlineQuery answerInlineQuery;

    @Before
    public void setUp() throws Exception {
        answerInlineQuery = new AnswerInlineQuery();
    }

    @Test
    public void TestInlineQueryIdMustBePresent() throws Exception {
        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            Assert.assertEquals("InlineQueryId can't be empty", e.getMessage());
        }
    }

    @Test
    public void TestInlineQueryIdCanNotBeEmpty() throws Exception {
        answerInlineQuery.setInlineQueryId("");
        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            Assert.assertEquals("InlineQueryId can't be empty", e.getMessage());
        }
    }

    @Test
    public void TestResultsMustBePresent() throws Exception {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            Assert.assertEquals("Results array can't be null", e.getMessage());
        }
    }

    @Test
    public void TestSwitchPmTextCanNotBeEmpty() throws Exception {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            Assert.assertEquals("SwitchPmText can't be empty", e.getMessage());
        }
    }

    @Test
    public void TestSwitchPmParameterIsMandatoryIfSwitchPmTextIsPresent() throws Exception {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("Test Text");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            Assert.assertEquals("SwitchPmParameter can't be empty if switchPmText is present", e.getMessage());
        }
    }

    @Test
    public void TestSwitchPmParameterCanNotBeEmptyIfSwitchPmTextIsPresent() throws Exception {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("Test Text");
        answerInlineQuery.setSwitchPmParameter("");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            Assert.assertEquals("SwitchPmParameter can't be empty if switchPmText is present", e.getMessage());
        }
    }

    @Test
    public void TestSwitchPmParameterContainsUpTo64Chars() throws Exception {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("Test Text");
        answerInlineQuery.setSwitchPmParameter("2AAQlw4BwzXwFNXMk5rReQC3YbhbgNqq4BGqyozjRTtrsok4shsB8u4NXeslfpOsL");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            Assert.assertEquals("SwitchPmParameter can't be longer than 64 chars", e.getMessage());
        }
    }

    @Test
    public void TestSwitchPmParameterOnlyContainsAcceptedCharacters() throws Exception {
        answerInlineQuery.setInlineQueryId("RANDOMEID");
        answerInlineQuery.setResults(new ArrayList<>());
        answerInlineQuery.setSwitchPmText("Test Text");
        answerInlineQuery.setSwitchPmParameter("*");

        try {
            answerInlineQuery.validate();
        } catch (TelegramApiValidationException e) {
            Assert.assertEquals("SwitchPmParameter only allows A-Z, a-z, 0-9 and _ characters", e.getMessage());
        }
    }
}
