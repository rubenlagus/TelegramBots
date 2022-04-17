package org.telegram.telegrambots.meta.api.objects.menubutton.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButton;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * JSON serializer for MenuButton type
 */
public class MenuButtonSerializer extends JsonSerializer<MenuButton> {
    @Override
    public void serialize(MenuButton value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(MenuButton.TYPE_FIELD, value.getType());

        if (value instanceof MenuButtonWebApp) {
            MenuButtonWebApp menuButtonWebApp = (MenuButtonWebApp) value;

            gen.writeStringField(MenuButtonWebApp.TEXT_FIELD, menuButtonWebApp.getText());
            gen.writeObjectField(MenuButtonWebApp.WEBAPP_FIELD, menuButtonWebApp.getWebAppInfo());
        }

        gen.writeEndObject();
    }


    @Override
    public void serializeWithType(MenuButton value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(value, gen, serializers);
    }
}
