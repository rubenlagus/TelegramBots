package org.telegram.telegrambots.meta.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputLocationMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultsButton;

import java.time.OffsetTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestSerialization {
    private ObjectMapper mapper = JsonMapper.builder()
            .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .build();

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

        assertTrue(now.isBefore(myTime));
    }

    //@BeforeEach
    void setUp() {
        mapper = JsonMapper.builder()
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .build();
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
        assertEquals("{\"allow_sending_without_reply\":true,\"chat_id\":\"12345\",\"disable_notification\":true,\"heading\":125,\"horizontal_accuracy\":65.0,\"latitude\":20.758069,\"live_period\":100,\"longitude\":-0.005702,\"method\":\"sendlocation\",\"proximity_alert_radius\":100,\"reply_to_message_id\":1}",
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
                .button(InlineQueryResultsButton
                        .builder()
                        .startParameter("parameter")
                        .text("switch")
                        .build())
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
        assertEquals("{\"button\":{\"start_parameter\":\"parameter\",\"text\":\"switch\"},\"cache_time\":1,\"inline_query_id\":\"12345\",\"is_personal\":true,\"method\":\"answerInlineQuery\",\"next_offset\":\"2\",\"results\":[{\"id\":\"1\",\"input_message_content\":{\"horizontal_accuracy\":65.0,\"latitude\":20.758069,\"longitude\":-0.005702},\"title\":\"Title\",\"type\":\"article\"}]}",
                json);
    }
}
