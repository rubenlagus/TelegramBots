package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Interceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.PayloadStorage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.util.function.BiConsumer;

/**
 * interceptor that logs the beginning, the end and the duration of the update processing
 */
public class DurationLoggerInterceptor extends Interceptor {
    private static final Logger log = LoggerFactory.getLogger(DurationLoggerInterceptor.class);

    private BiConsumer<Integer, Long> consumer = null;
    private Timestamp startTime;

    /**
     * create interceptor without consumer
     */
    public DurationLoggerInterceptor() { }

    /**
     * @param consumer lambda to which the request time will and update id be sent
     */
    public DurationLoggerInterceptor(BiConsumer<Integer, Long> consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean before(Update update, PayloadStorage storage) {
        log.info("process update {} started", update.getUpdateId());
        startTime = new Timestamp(System.currentTimeMillis());

        return process();
    }

    @Override
    public void after(Update update, PayloadStorage storage) {
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        long spendTime = endTime.getTime() - startTime.getTime();

        log.info("process update {} ended. time spent {}", update.getUpdateId(), spendTime);
        if (consumer == null) return;

        consumer.accept(update.getUpdateId(), spendTime);
    }
}
