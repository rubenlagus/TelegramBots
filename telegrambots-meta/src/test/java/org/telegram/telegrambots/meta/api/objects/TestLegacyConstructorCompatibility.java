package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.chat.ChatFullInfo;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.stories.StoryArea;

import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestLegacyConstructorCompatibility {

    @Test
    void keepsPre104AllArgumentsConstructors() throws NoSuchMethodException {
        assertLegacyConstructor(Message.class, Set.of("tonWalletTransfer", "paidMessagesWaived", "widget"));
        assertLegacyConstructor(ChatFullInfo.class, Set.of("tonWalletCard", "shareTheme"));
        assertLegacyConstructor(Update.class, Set.of("widgetQuery", "sharedWidgetQuery"));
        assertLegacyConstructor(ExternalReplyInfo.class, Set.of("widget"));
    }

    @Test
    void keepsPre104SerializationIds() {
        assertEquals(-6668707729671845680L, ObjectStreamClass.lookup(Message.class).getSerialVersionUID());
        assertEquals(-8054250969089654169L, ObjectStreamClass.lookup(ChatFullInfo.class).getSerialVersionUID());
        assertEquals(-2095618507227580572L, ObjectStreamClass.lookup(StoryArea.class).getSerialVersionUID());
        assertEquals(8960651857740058161L, ObjectStreamClass.lookup(Update.class).getSerialVersionUID());
        assertEquals(-4148306810767063117L, ObjectStreamClass.lookup(ExternalReplyInfo.class).getSerialVersionUID());
    }

    private void assertLegacyConstructor(Class<?> type, Set<String> addedFields) throws NoSuchMethodException {
        Class<?>[] legacyParameters = Arrays.stream(type.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !addedFields.contains(field.getName()))
                .map(Field::getType)
                .toArray(Class<?>[]::new);

        assertNotNull(type.getDeclaredConstructor(legacyParameters));
    }
}