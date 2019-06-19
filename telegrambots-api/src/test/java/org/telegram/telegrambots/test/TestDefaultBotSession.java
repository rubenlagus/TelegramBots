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
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.test.Fakes.FakeLongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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

    @Test
    public void testUpdates() throws Exception {
        LongPollingBot bot = Mockito.spy(new FakeLongPollingBot());
        session = getDefaultBotSession(bot);
        AtomicInteger flag = new AtomicInteger();
        Update[] updates = createFakeUpdates(9);
        session.setUpdatesSupplier(createFakeUpdatesSupplier(flag, updates));
        session.start();
        Thread.sleep(1000);
        Mockito.verify(bot, Mockito.never()).onUpdateReceived(Matchers.any());
        flag.compareAndSet(0, 1);
        Thread.sleep(1000);
        Mockito.verify(bot).onUpdateReceived(updates[0]);
        Mockito.verify(bot).onUpdateReceived(updates[1]);
        flag.compareAndSet(2, 3);
        Thread.sleep(1000);
        Mockito.verify(bot).onUpdateReceived(updates[2]);
        Mockito.verify(bot).onUpdateReceived(updates[3]);
        Mockito.verify(bot).onUpdateReceived(updates[4]);
        flag.compareAndSet(4, 5);
        Thread.sleep(1000);
        Mockito.verify(bot).onUpdateReceived(updates[5]);
        Mockito.verify(bot).onUpdateReceived(updates[6]);
        Mockito.verify(bot).onUpdateReceived(updates[7]);
        Mockito.verify(bot).onUpdateReceived(updates[8]);
        session.stop();
    }

    @Test
    public void testBatchUpdates() throws Exception {
        LongPollingBot bot = Mockito.spy(new FakeLongPollingBot());
        session = getDefaultBotSession(bot);
        AtomicInteger flag = new AtomicInteger();
        Update[] updates = createFakeUpdates(9);
        session.setUpdatesSupplier(createFakeUpdatesSupplier(flag, updates));
        session.start();
        Thread.sleep(1000);
        Mockito.verify(bot, Mockito.never()).onUpdateReceived(Matchers.any());
        flag.compareAndSet(0, 1);
        Thread.sleep(1000);
        Mockito.verify(bot).onUpdatesReceived(Arrays.asList(updates[0], updates[1]));
        flag.compareAndSet(2, 3);
        Thread.sleep(1000);
        Mockito.verify(bot).onUpdatesReceived(Arrays.asList(updates[2], updates[3], updates[4]));
        flag.compareAndSet(4, 5);
        Thread.sleep(1000);
        Mockito.verify(bot).onUpdatesReceived(Arrays.asList(updates[5], updates[6], updates[7], updates[8]));
        session.stop();
    }

    private Update[] createFakeUpdates(int count) {
        return IntStream.range(0, count).mapToObj(x -> {
            Update mock = Mockito.mock(Update.class);
            Mockito.when(mock.getUpdateId()).thenReturn(x);
            return mock;
        }).toArray(Update[]::new);
    }

    private DefaultBotSession.UpdatesSupplier createFakeUpdatesSupplier(AtomicInteger flag, Update[] updates) {
        return new DefaultBotSession.UpdatesSupplier() {
            @Override
            public List<Update> getUpdates() throws InterruptedException, Exception {
                if (flag.compareAndSet(1, 2)) {
                    return Arrays.asList(updates[0], updates[1]);
                } else if (flag.compareAndSet(3, 4)) {
                    return Arrays.asList(updates[2], updates[3], updates[4]);
                } else if (flag.compareAndSet(5, 6)) {
                    return Arrays.asList(updates[5], updates[6], updates[7], updates[8]);
                }
                return Collections.emptyList();
            }
        };
    }

    private DefaultBotSession getDefaultBotSession() throws IOException {
        return getDefaultBotSession(new FakeLongPollingBot());
    }

    private DefaultBotSession getDefaultBotSession(LongPollingBot bot) throws IOException {
        HttpResponse response = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, ""));
        response.setStatusCode(200);
        response.setEntity(new StringEntity("{}"));

        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
        Mockito.when(mockHttpClient.execute(Mockito.any(HttpPost.class)))
                .thenReturn(response);
        DefaultBotSession session = new DefaultBotSession();
        session.setCallback(bot);
        session.setOptions(new DefaultBotOptions());
        return session;
    }
}
