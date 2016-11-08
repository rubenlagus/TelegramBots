package org.telegram.telegrambots;

import org.junit.Assert;
import org.junit.Test;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 07 of November of 2016
 */
public class TestDefaultBotSession {
    @Test
    public void TestDefaultBotSessionIsNotRunninWhenCreated() throws Exception {
        Assert.assertFalse(new DefaultBotSession().isRunning());
    }
}
