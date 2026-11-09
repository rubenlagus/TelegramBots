package org.telegram.telegrambots.meta.api.methods.send;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSendCallbackReplyValidation {
    private record SendCase(Class<? extends PartialBotApiMethod<?>> type, Supplier<?> builder,
                            Class<?>[] constructorTypes, Object[] constructorValues) {
        @Override
        public String toString() {
            return type.getSimpleName();
        }
    }

    private static Stream<SendCase> sendMethods() {
        InputFile file = new InputFile("file-id");
        InputRichMessage rich = InputRichMessage.builder().html("hello").build();
        return Stream.of(
                new SendCase(SendMessage.class, () -> SendMessage.builder().text("hello"),
                        new Class<?>[]{String.class, String.class}, new Object[]{"123", "hello"}),
                new SendCase(SendRichMessage.class, () -> SendRichMessage.builder().richMessage(rich),
                        new Class<?>[]{String.class, InputRichMessage.class}, new Object[]{"123", rich}),
                new SendCase(SendAnimation.class, () -> SendAnimation.builder().animation(file),
                        new Class<?>[]{String.class, InputFile.class}, new Object[]{"123", file}),
                new SendCase(SendAudio.class, () -> SendAudio.builder().audio(file),
                        new Class<?>[]{String.class, InputFile.class}, new Object[]{"123", file}),
                new SendCase(SendContact.class, () -> SendContact.builder().phoneNumber("123").firstName("name"),
                        new Class<?>[]{String.class, String.class, String.class}, new Object[]{"123", "123", "name"}),
                new SendCase(SendDocument.class, () -> SendDocument.builder().document(file),
                        new Class<?>[]{String.class, InputFile.class}, new Object[]{"123", file}),
                new SendCase(SendLivePhoto.class, () -> SendLivePhoto.builder().livePhoto(file).photo(file),
                        new Class<?>[]{String.class, InputFile.class, InputFile.class}, new Object[]{"123", file, file}),
                new SendCase(SendLocation.class, () -> SendLocation.builder().latitude(1.0).longitude(2.0),
                        new Class<?>[]{String.class, Double.class, Double.class}, new Object[]{"123", 1.0, 2.0}),
                new SendCase(SendPhoto.class, () -> SendPhoto.builder().photo(file),
                        new Class<?>[]{String.class, InputFile.class}, new Object[]{"123", file}),
                new SendCase(SendSticker.class, () -> SendSticker.builder().sticker(file),
                        new Class<?>[]{String.class, InputFile.class}, new Object[]{"123", file}),
                new SendCase(SendVenue.class, () -> SendVenue.builder().latitude(1.0).longitude(2.0).title("title").address("address"),
                        new Class<?>[]{String.class, Double.class, Double.class, String.class, String.class},
                        new Object[]{"123", 1.0, 2.0, "title", "address"}),
                new SendCase(SendVideo.class, () -> SendVideo.builder().video(file),
                        new Class<?>[]{String.class, InputFile.class}, new Object[]{"123", file}),
                new SendCase(SendVideoNote.class, () -> SendVideoNote.builder().videoNote(file),
                        new Class<?>[]{String.class, InputFile.class}, new Object[]{"123", file}),
                new SendCase(SendVoice.class, () -> SendVoice.builder().voice(file),
                        new Class<?>[]{String.class, InputFile.class}, new Object[]{"123", file})
        );
    }

    private static Stream<SendCase> bridgeSendMethods() {
        return sendMethods().filter(sendCase -> sendCase.type() != SendRichMessage.class);
    }

    private static Object builderCall(Object builder, String name, Class<?> parameterType, Object value) throws Exception {
        return builder.getClass().getSuperclass().getMethod(name, parameterType).invoke(builder, value);
    }

    private static PartialBotApiMethod<?> build(Object builder) throws Exception {
        return (PartialBotApiMethod<?>) builder.getClass().getSuperclass().getMethod("build").invoke(builder);
    }

    private static void set(PartialBotApiMethod<?> method, String name, Class<?> type, Object value) throws Exception {
        method.getClass().getMethod(name, type).invoke(method, value);
    }

    private static EphemeralMessageParameters ephemeral(Long receiver, String callback) {
        return EphemeralMessageParameters.builder().receiverUserId(receiver).callbackQueryId(callback).build();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sendMethods")
    public void testIdentifierAndBusinessCombinations(SendCase sendCase) throws Exception {
        Object[][] cases = {
                {null, null, null, false},
                {null, null, ephemeral(null, null), false},
                {null, null, ephemeral(null, ""), false},
                {null, null, ephemeral(42L, null), false},
                {null, null, ephemeral(42L, "query"), false},
                {null, null, ephemeral(null, "query"), true},
                {"", null, ephemeral(null, "query"), true},
                {"", null, null, false},
                {"123", null, null, true},
                {"123", null, ephemeral(null, null), false},
                {"123", null, ephemeral(null, ""), false},
                {"123", null, ephemeral(42L, null), true},
                {"123", null, ephemeral(42L, "query"), true},
                {"123", null, ephemeral(null, "query"), true},
                {null, "business", ephemeral(null, "query"), false},
                {"123", "business", ephemeral(null, "query"), false},
                {"123", "", ephemeral(null, "query"), false},
                {"123", "business", ephemeral(42L, "query"), true},
                {"123", "business", null, true}
        };
        for (Object[] test : cases) {
            PartialBotApiMethod<?> method = build(sendCase.builder().get());
            set(method, "setChatId", String.class, test[0]);
            set(method, "setBusinessConnectionId", String.class, test[1]);
            set(method, "setEphemeralMessageParameters", EphemeralMessageParameters.class, test[2]);
            if ((boolean) test[3]) {
                assertDoesNotThrow(method::validate, Arrays.toString(test));
            } else {
                assertThrows(TelegramApiValidationException.class, method::validate, Arrays.toString(test));
            }
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sendMethods")
    public void testBuilderAndNestedValidation(SendCase sendCase) throws Exception {
        Object builder = sendCase.builder().get();
        builderCall(builder, "chatId", String.class, null);
        builderCall(builder, "ephemeralMessageParameters", EphemeralMessageParameters.class, ephemeral(null, "query"));
        PartialBotApiMethod<?> method = build(builder);
        CallbackReplyMethod callback = assertInstanceOf(CallbackReplyMethod.class, method);
        assertNull(callback.getChatId());
        assertNull(callback.getBusinessConnectionId());
        assertEquals("query", callback.getEphemeralMessageParameters().getCallbackQueryId());
        assertDoesNotThrow(method::validate);

        set(method, "setReplyParameters", ReplyParameters.class, new ReplyParameters());
        assertDoesNotThrow(method::validate);
        set(method, "setReplyParameters", ReplyParameters.class, null);
        set(method, "setChatId", String.class, "123");
        set(method, "setEphemeralMessageParameters", EphemeralMessageParameters.class, ephemeral(null, ""));
        assertThrows(TelegramApiValidationException.class, method::validate);

        Object businessBuilder = sendCase.builder().get();
        builderCall(businessBuilder, "businessConnectionId", String.class, "business");
        builderCall(businessBuilder, "ephemeralMessageParameters", EphemeralMessageParameters.class, ephemeral(null, "query"));
        PartialBotApiMethod<?> businessMethod = build(businessBuilder);
        assertThrows(TelegramApiValidationException.class, businessMethod::validate);

        Object longChatBuilder = sendCase.builder().get();
        builderCall(longChatBuilder, "chatId", Long.class, 123L);
        PartialBotApiMethod<?> longChatMethod = build(longChatBuilder);
        assertEquals("123", ((CallbackReplyMethod) longChatMethod).getChatId());
        assertDoesNotThrow(longChatMethod::validate);
        set(longChatMethod, "setChatId", Long.class, 456L);
        assertEquals("456", ((CallbackReplyMethod) longChatMethod).getChatId());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sendMethods")
    public void testConstructorCompatibility(SendCase sendCase) throws Exception {
        Constructor<?> original = sendCase.type().getConstructor(sendCase.constructorTypes());
        PartialBotApiMethod<?> method = (PartialBotApiMethod<?>) original.newInstance(sendCase.constructorValues());
        assertDoesNotThrow(method::validate);
        assertEquals("123", ((CallbackReplyMethod) method).getChatId());

        Object[] noChat = sendCase.constructorValues().clone();
        noChat[0] = null;
        method = (PartialBotApiMethod<?>) original.newInstance(noChat);
        set(method, "setEphemeralMessageParameters", EphemeralMessageParameters.class, ephemeral(null, "query"));
        PartialBotApiMethod<?> callbackMethod = method;
        assertDoesNotThrow(callbackMethod::validate);

        for (int i = 1; i < noChat.length; i++) {
            Object[] missing = sendCase.constructorValues().clone();
            missing[i] = null;
            InvocationTargetException failure = assertThrows(InvocationTargetException.class, () -> original.newInstance(missing));
            assertInstanceOf(NullPointerException.class, failure.getCause());
        }

        Class<?>[] remainingTypes = Arrays.copyOfRange(sendCase.constructorTypes(), 1, sendCase.constructorTypes().length);
        Object[] remainingValues = Arrays.copyOfRange(sendCase.constructorValues(), 1, sendCase.constructorValues().length);
        assertNotNull(sendCase.type().getConstructor(remainingTypes).newInstance(remainingValues));

        PartialBotApiMethod<?> built = build(sendCase.builder().get());
        Field[] fields = Arrays.stream(sendCase.type().getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()) && !field.isSynthetic())
                .toArray(Field[]::new);
        Class<?>[] allTypes = Arrays.stream(fields).map(Field::getType).toArray(Class<?>[]::new);
        Object[] allValues = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            allValues[i] = fields[i].get(built);
        }
        assertNotNull(sendCase.type().getConstructor(allTypes).newInstance(allValues));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("bridgeSendMethods")
    public void testDeprecatedBridges(SendCase sendCase) throws Exception {
        Object builder = sendCase.builder().get();
        builderCall(builder, "callbackQueryId", String.class, "query");
        PartialBotApiMethod<?> method = build(builder);
        assertDoesNotThrow(method::validate);
        assertEquals("query", method.getClass().getMethod("getCallbackQueryId").invoke(method));
        assertNull(method.getClass().getMethod("getReceiverUserId").invoke(method));

        set(method, "setReceiverUserId", Long.class, 42L);
        assertThrows(TelegramApiValidationException.class, method::validate);
        set(method, "setChatId", String.class, "123");
        assertDoesNotThrow(method::validate);
        set(method, "setCallbackQueryId", String.class, "other");
        assertEquals("other", ((CallbackReplyMethod) method).getEphemeralMessageParameters().getCallbackQueryId());
        assertEquals(42L, ((CallbackReplyMethod) method).getEphemeralMessageParameters().getReceiverUserId());

        Object reverseBuilder = sendCase.builder().get();
        builderCall(reverseBuilder, "receiverUserId", Long.class, 42L);
        builderCall(reverseBuilder, "callbackQueryId", String.class, "query");
        builderCall(reverseBuilder, "chatId", String.class, "123");
        PartialBotApiMethod<?> reverseMethod = build(reverseBuilder);
        assertDoesNotThrow(reverseMethod::validate);
        assertEquals(42L, ((CallbackReplyMethod) reverseMethod).getEphemeralMessageParameters().getReceiverUserId());
        assertEquals("query", ((CallbackReplyMethod) reverseMethod).getEphemeralMessageParameters().getCallbackQueryId());
    }

    @Test
    public void testCallbackOnlyJsonOmitsChatAndReceiver() {
        SendMessage method = SendMessage.builder().text("hello")
                .ephemeralMessageParameters(ephemeral(null, "query")).build();
        JsonNode json = new JsonMapper().valueToTree(method);
        assertFalse(json.has("chat_id"));
        assertFalse(json.has("receiver_user_id"));
        assertFalse(json.has("callback_query_id"));
        assertEquals("query", json.path("ephemeral_message_parameters").path("callback_query_id").asText());
        assertFalse(json.path("ephemeral_message_parameters").has("receiver_user_id"));
    }

    @Test
    public void testEphemeralParametersDirectly() {
        assertThrows(TelegramApiValidationException.class, () -> ephemeral(null, null).validate());
        assertThrows(TelegramApiValidationException.class, () -> ephemeral(null, "").validate());
        assertDoesNotThrow(() -> ephemeral(null, "query").validate());
        assertDoesNotThrow(() -> ephemeral(42L, null).validate());
        assertNull(EphemeralMessageParameters.builder().callbackQueryId("query").build().getReceiverUserId());
    }

    @Test
    public void testOtherNestedValidationStillRuns() {
        SendRichMessage rich = SendRichMessage.builder().richMessage(new InputRichMessage())
                .ephemeralMessageParameters(ephemeral(null, "query")).build();
        SendPhoto photo = SendPhoto.builder().photo(new InputFile(""))
                .ephemeralMessageParameters(ephemeral(null, "query")).build();
        assertThrows(TelegramApiValidationException.class, rich::validate);
        assertThrows(TelegramApiValidationException.class, photo::validate);
    }
}