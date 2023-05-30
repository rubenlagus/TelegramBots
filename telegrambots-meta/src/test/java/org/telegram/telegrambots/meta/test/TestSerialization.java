package org.telegram.telegrambots.meta.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputLocationMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.time.OffsetTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestSerialization {
    private ObjectMapper mapper = new ObjectMapper();

    static {
        System.setProperty("user.timezone", "EST");
    }

    @Data
    public static class MyClass {
        public OffsetTime time;
    }

    @Test
    public void test() throws JsonProcessingException {
        String time = "{\"time\":\"14:30Z\"}";

        mapper.registerModule(new JavaTimeModule());

        OffsetTime now = OffsetTime.now().withHour(9).withMinute(0).withNano(0).withSecond(0);
        OffsetTime myTime = mapper.readValue(time, MyClass.class).time;

        System.err.println("Time: " + myTime.withOffsetSameInstant(ZoneOffset.UTC));
        System.err.println("Now: " + now.withOffsetSameInstant(ZoneOffset.UTC));
        // ahora antes que valor
        System.err.println(now.isBefore(myTime));
    }

    //@BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testSendLocation() throws JsonProcessingException {
        SendLocation location = SendLocation.builder()
                .chatId(12345L)
                .latitude(20.758069)
                .longitude(-0.005702)
                .horizontalAccuracy(65.00000)
                .disableNotification(true)
                .replyToMessageId(1)
                .livePeriod(100)
                .allowSendingWithoutReply(true)
                .heading(125)
                .proximityAlertRadius(100)
                .build();

        String json = mapper.writeValueAsString(location);

        assertNotNull(json);
        assertEquals("{\"chat_id\":\"12345\",\"latitude\":20.758069,\"longitude\":-0.005702,\"disable_notification\":true,\"reply_to_message_id\":1,\"live_period\":100,\"allow_sending_without_reply\":true,\"horizontal_accuracy\":65.0,\"heading\":125,\"proximity_alert_radius\":100,\"method\":\"sendlocation\"}",
                json);
    }

    @Test
    void testAnswerInlineLocation() throws JsonProcessingException {
        AnswerInlineQuery inlineQuery = AnswerInlineQuery
                .builder()
                .inlineQueryId("12345")
                .cacheTime(1)
                .isPersonal(true)
                .nextOffset("2")
                .switchPmText("switch")
                .switchPmParameter("parameter")
                .result(InlineQueryResultArticle
                        .builder()
                        .id("1")
                        .title("Title")
                        .inputMessageContent(InputLocationMessageContent
                                .builder()
                                .latitude(20.758069)
                                .longitude(-0.005702)
                                .horizontalAccuracy(65.00000)
                                .build()
                        )
                        .build())
                .build();

        String json = mapper.writeValueAsString(inlineQuery);

        assertNotNull(json);
        assertEquals("{\"inline_query_id\":\"12345\",\"results\":[{\"type\":\"article\",\"id\":\"1\",\"title\":\"Title\",\"input_message_content\":{\"latitude\":20.758069,\"longitude\":-0.005702,\"horizontal_accuracy\":65.0}}],\"cache_time\":1,\"is_personal\":true,\"next_offset\":\"2\",\"switch_pm_text\":\"switch\",\"switch_pm_parameter\":\"parameter\",\"method\":\"answerInlineQuery\"}",
                json);
    }
}
