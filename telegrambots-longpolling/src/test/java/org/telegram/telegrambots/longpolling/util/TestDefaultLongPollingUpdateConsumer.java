package org.telegram.telegrambots.longpolling.util;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class TestDefaultLongPollingUpdateConsumer {

    @Test
    public void testUpdatesProcessedOnSeparateThread() throws Exception {
        Thread callingThread = Thread.currentThread();
        AtomicReference<Thread> processingThread = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        DefaultLongPollingUpdateConsumer consumer = new DefaultLongPollingUpdateConsumer() {
            @Override
            public void consume(Update update) {
                processingThread.set(Thread.currentThread());
                latch.countDown();
            }
        };

        Update update = new Update();
        update.setUpdateId(1);
        consumer.consume(List.of(update));

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertNotSame(callingThread, processingThread.get());

        consumer.close();
    }

    @Test
    public void testEachInstanceHasOwnExecutor() throws Exception {
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        AtomicReference<Thread> thread1 = new AtomicReference<>();
        AtomicReference<Thread> thread2 = new AtomicReference<>();

        DefaultLongPollingUpdateConsumer consumer1 = new DefaultLongPollingUpdateConsumer() {
            @Override
            public void consume(Update update) {
                thread1.set(Thread.currentThread());
                latch1.countDown();
            }
        };

        DefaultLongPollingUpdateConsumer consumer2 = new DefaultLongPollingUpdateConsumer() {
            @Override
            public void consume(Update update) {
                thread2.set(Thread.currentThread());
                latch2.countDown();
            }
        };

        Update update = new Update();
        update.setUpdateId(1);
        consumer1.consume(List.of(update));
        consumer2.consume(List.of(update));

        assertTrue(latch1.await(5, TimeUnit.SECONDS));
        assertTrue(latch2.await(5, TimeUnit.SECONDS));
        assertNotSame(thread1.get(), thread2.get());

        consumer1.close();
        consumer2.close();
    }

    @Test
    public void testCloseStopsProcessing() throws Exception {
        CountDownLatch firstLatch = new CountDownLatch(1);
        AtomicBoolean secondProcessed = new AtomicBoolean(false);

        DefaultLongPollingUpdateConsumer consumer = new DefaultLongPollingUpdateConsumer() {
            @Override
            public void consume(Update update) {
                if (update.getUpdateId() == 1) {
                    firstLatch.countDown();
                } else {
                    secondProcessed.set(true);
                }
            }
        };

        Update update1 = new Update();
        update1.setUpdateId(1);
        consumer.consume(List.of(update1));
        assertTrue(firstLatch.await(5, TimeUnit.SECONDS));

        consumer.close();

        Update update2 = new Update();
        update2.setUpdateId(2);
        consumer.consume(List.of(update2));

        Thread.sleep(200);
        assertFalse(secondProcessed.get(), "No updates should be processed after close()");
    }

    @Test
    public void testUpdatesProcessedInOrder() throws Exception {
        List<Integer> processed = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(3);

        DefaultLongPollingUpdateConsumer consumer = new DefaultLongPollingUpdateConsumer() {
            @Override
            public void consume(Update update) {
                processed.add(update.getUpdateId());
                latch.countDown();
            }
        };

        Update u1 = new Update(); u1.setUpdateId(1);
        Update u2 = new Update(); u2.setUpdateId(2);
        Update u3 = new Update(); u3.setUpdateId(3);
        consumer.consume(List.of(u1, u2, u3));

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals(List.of(1, 2, 3), processed);

        consumer.close();
    }

    @Test
    public void testConsumerOneDoesNotBlockConsumerTwo() throws Exception {
        CountDownLatch blockingLatch = new CountDownLatch(1);
        CountDownLatch consumer2Latch = new CountDownLatch(1);

        DefaultLongPollingUpdateConsumer blockingConsumer = new DefaultLongPollingUpdateConsumer() {
            @Override
            public void consume(Update update) {
                try {
                    blockingLatch.await();
                } catch (InterruptedException ignored) {
                }
            }
        };

        DefaultLongPollingUpdateConsumer consumer2 = new DefaultLongPollingUpdateConsumer() {
            @Override
            public void consume(Update update) {
                consumer2Latch.countDown();
            }
        };

        Update update = new Update();
        update.setUpdateId(1);
        blockingConsumer.consume(List.of(update));
        consumer2.consume(List.of(update));

        assertTrue(consumer2Latch.await(5, TimeUnit.SECONDS),
                "consumer2 should process independently of the blocked consumer1");

        blockingLatch.countDown();
        blockingConsumer.close();
        consumer2.close();
    }
}
