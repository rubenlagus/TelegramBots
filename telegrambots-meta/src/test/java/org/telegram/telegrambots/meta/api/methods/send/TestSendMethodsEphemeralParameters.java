package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Bot API 10.2 added receiver_user_id and callback_query_id to all 13 send methods.
 *
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestSendMethodsEphemeralParameters {
    private static final Long RECEIVER_USER_ID = 42L;
    private static final String CALLBACK_QUERY_ID = "callback_query_id_value";

    /**
     * The multipart-based methods are read through the {@link SendMediaBotMethod} supertype by the
     * Telegram clients, so the parameters must be visible from there and not only on the concrete class.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("mediaSendMethods")
    public void testMediaSendMethodsExposeEphemeralParameters(String path, SendMediaBotMethod<?> method) {
        assertEquals(RECEIVER_USER_ID, method.getReceiverUserId());
        assertEquals(CALLBACK_QUERY_ID, method.getCallbackQueryId());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("jsonSendMethods")
    public void testJsonSendMethodsSerializeEphemeralParameters(String path, Object method) throws IOException {
        String json = new ObjectMapper().writeValueAsString(method);

        assertTrue(json.contains("\"receiver_user_id\":42"), json);
        assertTrue(json.contains("\"callback_query_id\":\"" + CALLBACK_QUERY_ID + "\""), json);
    }

    private static Stream<Arguments> mediaSendMethods() {
        InputFile file = new InputFile("file_id");
        return Stream.of(
                Arguments.of("sendAnimation", SendAnimation.builder()
                        .chatId("12345").animation(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendAudio", SendAudio.builder()
                        .chatId("12345").audio(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendDocument", SendDocument.builder()
                        .chatId("12345").document(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendLivePhoto", SendLivePhoto.builder()
                        .chatId("12345").livePhoto(file).photo(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendPhoto", SendPhoto.builder()
                        .chatId("12345").photo(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendSticker", SendSticker.builder()
                        .chatId("12345").sticker(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendVideo", SendVideo.builder()
                        .chatId("12345").video(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendVideoNote", SendVideoNote.builder()
                        .chatId("12345").videoNote(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendVoice", SendVoice.builder()
                        .chatId("12345").voice(file)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build())
        );
    }

    private static Stream<Arguments> jsonSendMethods() {
        return Stream.of(
                Arguments.of("sendMessage", SendMessage.builder()
                        .chatId("12345").text("Hello")
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendContact", SendContact.builder()
                        .chatId("12345").phoneNumber("+34123456789").firstName("John")
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendLocation", SendLocation.builder()
                        .chatId("12345").latitude(40.416775).longitude(-3.703790)
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build()),
                Arguments.of("sendVenue", SendVenue.builder()
                        .chatId("12345").latitude(40.416775).longitude(-3.703790)
                        .title("Puerta del Sol").address("Puerta del Sol, Madrid")
                        .receiverUserId(RECEIVER_USER_ID).callbackQueryId(CALLBACK_QUERY_ID).build())
        );
    }
}
