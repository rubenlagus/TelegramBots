package org.telegram.telegrambots.test;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.test.Fakes.FakeLongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Test for DefaultBotSession
 */
public class TestDefaultBotSession {
    DefaultBotSession session;

    @Before
    public void setUp() throws Exception {
        session = getDefaultBotSession();
    }

    @After
    public void tearDown() throws Exception {
        if (session != null && session.isRunning()) {
            session.stop();
        }
    }

    @Test
    public void TestDefaultBotSessionIsNotRunningWhenCreated() throws Exception {
        Assert.assertFalse(session.isRunning());
    }

    @Test
    public void TestDefaultBotSessionCanBeStartedAfterCreation() throws Exception {
        session = getDefaultBotSession();
        session.start();
        Assert.assertTrue(session.isRunning());
    }

    @Test(expected = IllegalStateException.class)
    public void TestDefaultBotSessionCanNotBeStoppedAfterCreation() throws Exception {
        session = getDefaultBotSession();
        session.stop();
    }

    @Test(expected = IllegalStateException.class)
    public void TestDefaultBotSessionCanNotBeStartedIfAlreadyStarted() throws Exception {
        session = getDefaultBotSession();
        session.start();
        session.start();
    }

    @Test
    public void TestDefaultBotSessionCanBeStoppedIfStarted() throws Exception {
        session = getDefaultBotSession();
        session.start();
        session.stop();
        Assert.assertFalse(session.isRunning());
    }

    @Test(expected = IllegalStateException.class)
    public void TestDefaultBotSessionCanNotBeStoppedIfAlreadyStopped() throws Exception {
        session = getDefaultBotSession();
        session.start();
        session.stop();
        session.stop();
    }

    private DefaultBotSession getDefaultBotSession() throws IOException {
        HttpResponse response = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, ""));
        response.setStatusCode(200);
        response.setEntity(new StringEntity("{}"));

        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
        Mockito.when(mockHttpClient.execute(Mockito.any(HttpPost.class)))
                .thenReturn(response);
        DefaultBotSession session = new DefaultBotSession();
        session.setCallback(new FakeLongPollingBot());
        session.setOptions(new DefaultBotOptions());
        return session;
    }
}
