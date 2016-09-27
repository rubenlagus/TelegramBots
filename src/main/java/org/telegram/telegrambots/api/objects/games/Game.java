/*
 * This file is part of TelegramBots.
 *
 * Foobar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.telegram.telegrambots.api.objects.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;
import org.telegram.telegrambots.api.objects.PhotoSize;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief This object represents a game.
 * @date 27 of September of 2016
 */
public class Game implements IBotApiObject {
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String PHOTO_FIELD = "photo";
    private static final String ANIMATION_FIELD = "animation";

    @JsonProperty(TITLE_FIELD)
    private String title; ///< Title of the game
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Description of the game
    @JsonProperty(PHOTO_FIELD)
    private PhotoSize photo; ///< Photo
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation; ///< Optional. Animation

    public Game() {
        super();
    }

    public Game(JSONObject object) {
        super();
        title = object.getString(TITLE_FIELD);
        description = object.getString(DESCRIPTION_FIELD);
        photo = new PhotoSize(object.getJSONObject(PHOTO_FIELD));
        animation = new Animation(object.getJSONObject(ANIMATION_FIELD));
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public PhotoSize getPhoto() {
        return photo;
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TITLE_FIELD, title);
        gen.writeStringField(DESCRIPTION_FIELD, description);
        gen.writeObjectField(PHOTO_FIELD, photo);
        gen.writeObjectField(ANIMATION_FIELD, animation);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photo=" + photo +
                ", animation=" + animation +
                '}';
    }
}
