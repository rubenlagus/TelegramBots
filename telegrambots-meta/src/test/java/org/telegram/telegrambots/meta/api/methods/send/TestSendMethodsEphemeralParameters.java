package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Bot API 10.3 replaced the flat receiver_user_id / callback_query_id parameters of the 13 send
 * methods with the nested ephemeral_message_parameters object. The old names survive as deprecated
 * write-through accessors; only the nested object reaches the wire.
 *
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestSendMethodsEphemeralParameters {
    private final ObjectMapper mapper = new ObjectMapper();

    private static final Long RECEIVER_USER_ID = 42L;
    private static final String CALLBACK_QUERY_ID = "callback_query_id_value";

    /**
     * The multipart-based methods are read through the {@link SendMediaBotMethod} supertype by the
     * Telegram clients, so the parameters must be visible from there and not only on the concrete class.
     */
    @SuppressWarnings("deprecation")
    @ParameterizedTest(name = "{0}")
    @MethodSource("mediaSendMethods")
    public void testMediaSendMethodsExposeEphemeralParameters(String path, SendMediaBotMethod<?> method) {
        assertEquals(RECEIVER_USER_ID, method.getReceiverUserId());
        assertEquals(CALLBACK_QUERY_ID, method.getCallbackQueryId());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("jsonSendMethods")
    public void testJsonSendMethodsSerializeEphemeralParameters(String path, Object method) throws IOException {
        JsonNode root = mapper.valueToTree(method);

        JsonNode nested = root.get("ephemeral_message_parameters");
        assertNotNull(nested, path + " must serialize ephemeral_message_parameters");
        assertEquals(42L, nested.get("receiver_user_id").asLong(), path);
        assertEquals(CALLBACK_QUERY_ID, nested.get("callback_query_id").asText(), path);

        // The 10.2 flat parameters must never appear at the top level again.
        assertFalse(root.has("receiver_user_id"), path);
        assertFalse(root.has("callback_query_id"), path);
    }

    /**
     * Substring assertions are useless here: "receiver_user_id" legitimately appears INSIDE
     * ephemeral_message_parameters. Only a parsed top-level check can prove the flat keys are gone.
     */
    @Test
    public void testSendMessageSerializesOnlyNestedParameters() throws IOException {
        SendMessage method = SendMessage.builder()
                .chatId(1L)
                .text("hi")
                .ephemeralMessageParameters(EphemeralMessageParameters.builder()
                        .receiverUserId(99L)
                        .callbackQueryId("q")
                        .replaceCallbackQueryMessage(true)
                        .build())
                .build();

        JsonNode root = mapper.readTree(mapper.writeValueAsString(method));

        JsonNode nested = root.get("ephemeral_message_parameters");
        assertNotNull(nested);
        assertEquals(99L, nested.get("receiver_user_id").asLong());
        assertEquals("q", nested.get("callback_query_id").asText());
        assertTrue(nested.get("replace_callback_query_message").asBoolean());

        // The 10.2 flat parameters must never reach the wire again.
        assertFalse(root.has("receiver_user_id"));
        assertFalse(root.has("callback_query_id"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDeprecatedBuilderMethodsBridgeToNestedObject() {
        SendMessage method = SendMessage.builder()
                .chatId(1L)
                .text("hi")
                .receiverUserId(99L)
                .callbackQueryId("q")
                .build();

        assertNotNull(method.getEphemeralMessageParameters());
        assertEquals(99L, method.getEphemeralMessageParameters().getReceiverUserId());
        assertEquals("q", method.getEphemeralMessageParameters().getCallbackQueryId());
        assertEquals(99L, method.getReceiverUserId());
        assertEquals("q", method.getCallbackQueryId());
    }

    /**
     * Order independence matters: EphemeralMessageParameters.receiverUserId is required by the API,
     * so a bridge that built the nested object eagerly would throw when callbackQueryId is set first.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testDeprecatedBuilderMethodsAreOrderIndependent() {
        SendMessage method = SendMessage.builder()
                .chatId(1L)
                .text("hi")
                .callbackQueryId("q")
                .receiverUserId(99L)
                .build();

        assertEquals(99L, method.getEphemeralMessageParameters().getReceiverUserId());
        assertEquals("q", method.getEphemeralMessageParameters().getCallbackQueryId());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDeprecatedSettersBridgeToNestedObject() {
        SendMessage method = SendMessage.builder().chatId(1L).text("hi").build();

        method.setCallbackQueryId("q");
        method.setReceiverUserId(99L);

        assertEquals(99L, method.getEphemeralMessageParameters().getReceiverUserId());
        assertEquals("q", method.getEphemeralMessageParameters().getCallbackQueryId());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDeprecatedGettersReturnNullWhenUnset() {
        SendMessage method = SendMessage.builder().chatId(1L).text("hi").build();

        assertNull(method.getReceiverUserId());
        assertNull(method.getCallbackQueryId());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMultipartMethodBridge() {
        SendPhoto method = SendPhoto.builder()
                .chatId(1L)
                .photo(new InputFile("file-id"))
                .receiverUserId(99L)
                .build();

        assertEquals(99L, method.getEphemeralMessageParameters().getReceiverUserId());
        assertEquals(99L, method.getReceiverUserId());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMultipartMethodBridgeSendDocument() {
        SendDocument method = SendDocument.builder()
                .chatId(1L)
                .document(new InputFile("file-id"))
                .callbackQueryId("q")
                .receiverUserId(99L)
                .build();

        assertEquals(99L, method.getEphemeralMessageParameters().getReceiverUserId());
        assertEquals("q", method.getCallbackQueryId());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMultipartMethodBridgeSendVideo() {
        SendVideo method = SendVideo.builder()
                .chatId(1L)
                .video(new InputFile("file-id"))
                .receiverUserId(99L)
                .build();

        assertEquals(99L, method.getEphemeralMessageParameters().getReceiverUserId());
        assertEquals(99L, method.getReceiverUserId());
    }

    @Test
    public void testSendRichMessageAcceptsEphemeralParameters() throws IOException {
        SendRichMessage method = SendRichMessage.builder()
                .chatId(1L)
                .richMessage(InputRichMessage.builder().html("<p>hi</p>").build())
                .ephemeralMessageParameters(EphemeralMessageParameters.builder()
                        .receiverUserId(99L)
                        .build())
                .build();

        assertTrue(mapper.writeValueAsString(method).contains("\"ephemeral_message_parameters\""));
    }

    /**
     * EphemeralMessageParameters.receiverUserId is required by the API but deliberately not @NonNull,
     * so validate() is the only thing enforcing it - and it only runs if the send method calls it.
     */
    @Test
    public void testValidateRejectsEphemeralParametersWithoutReceiver() {
        SendMessage method = SendMessage.builder()
                .chatId(1L)
                .text("hi")
                .ephemeralMessageParameters(new EphemeralMessageParameters())
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testMultipartValidateRejectsEphemeralParametersWithoutReceiver() {
        SendPhoto method = SendPhoto.builder()
                .chatId(1L)
                .photo(new InputFile("file-id"))
                .ephemeralMessageParameters(new EphemeralMessageParameters())
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testValidateAcceptsCompleteEphemeralParameters() {
        SendMessage method = SendMessage.builder()
                .chatId(1L)
                .text("hi")
                .ephemeralMessageParameters(EphemeralMessageParameters.builder().receiverUserId(99L).build())
                .build();

        assertDoesNotThrow(method::validate);
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
