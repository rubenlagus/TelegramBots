package org.telegram.telegrambots.starter;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestSpringWebhookBot
{
    @Test
    void nullTestSpringWebhookBot()
    {
        // Arrange and Act
        SpringWebhookBot.TestSpringWebhookBot webhook = new SpringWebhookBot.TestSpringWebhookBot(null);

        // Assert
        assertNull(webhook.getSetWebhook());
    }

    @Test
    void NotnullTestSpringWebhookBot()
    {
        // Arrange
        SetWebhook setWebhook = new SetWebhook();

        // Act
        SpringWebhookBot.TestSpringWebhookBot webhook = new SpringWebhookBot.TestSpringWebhookBot(setWebhook);

        //Assert
        assertNotNull(webhook.getSetWebhook());
    }

}
