/*
 * This file is part of TelegramBots.
 *
 * TelegramBots is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TelegramBots is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TelegramBots.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.telegram.telegrambots.api.objects.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.PhotoSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief This object represents a game.
 * Use BotFather to create and edit games, their short names will act as unique identifiers.
 * @date 27 of September of 2016
 */
public class Game implements IBotApiObject {
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String PHOTO_FIELD = "photo";
    private static final String ANIMATION_FIELD = "animation";
    private static final String TEXT_FIELD = "text";
    private static final String TEXTENTITIES_FIELD = "text_entities";

    @JsonProperty(TITLE_FIELD)
    private String title; ///< Title of the game
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Description of the game
    @JsonProperty(PHOTO_FIELD)
    private List<PhotoSize> photo; ///< Photo
    @JsonProperty(TEXT_FIELD)
    /**
     * Optional. Brief description of the game or high scores included in the game message.
     * Can be automatically edited to include current high scores for the game
     * when the bot calls setGameScore, or manually edited using editMessageText.
     * 0-4096 characters.
     */
    private String text;
    @JsonProperty(TEXTENTITIES_FIELD)
    /**
     * Optional. Special entities that appear in text, such as usernames,
     * URLs, bot commands, etc.
     */
    private List<MessageEntity> entities;
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation; ///< Optional. Animation

    public Game() {
        super();
    }

    public Game(JSONObject object) {
        super();
        title = object.getString(TITLE_FIELD);
        description = object.getString(DESCRIPTION_FIELD);
        this.photo = new ArrayList<>();
        JSONArray photos = object.getJSONArray(PHOTO_FIELD);
        for (int i = 0; i < photos.length(); i++) {
            this.photo.add(new PhotoSize(photos.getJSONObject(i)));
        }
        if (object.has(TEXT_FIELD)) {
            text = object.getString(TEXT_FIELD);
        }
        if (object.has(TEXTENTITIES_FIELD)) {
            this.entities = new ArrayList<>();
            JSONArray entities = object.getJSONArray(TEXTENTITIES_FIELD);
            for (int i = 0; i < entities.length(); i++) {
                this.entities.add(new MessageEntity(entities.getJSONObject(i)));
            }
        }
        if (object.has(ANIMATION_FIELD)) {
            animation = new Animation(object.getJSONObject(ANIMATION_FIELD));
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<PhotoSize> getPhoto() {
        return photo;
    }

    public Animation getAnimation() {
        return animation;
    }

    public String getText() {
        return text;
    }

    public boolean hasEntities() {
        return entities != null && !entities.isEmpty();
    }

    public List<MessageEntity> getEntities() {
        return entities;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TITLE_FIELD, title);
        gen.writeStringField(DESCRIPTION_FIELD, description);
        gen.writeArrayFieldStart(PHOTO_FIELD);
        for (PhotoSize photoSize : photo) {
            gen.writeObject(photoSize);
        }
        gen.writeEndArray();
        if (animation != null) {
            gen.writeObjectField(ANIMATION_FIELD, animation);
        }
        if (text != null) {
            gen.writeStringField(TEXT_FIELD, text);
        }
        if (entities != null) {
            gen.writeArrayFieldStart(TEXTENTITIES_FIELD);
            for (MessageEntity entity : entities) {
                gen.writeObject(entity);
            }
            gen.writeEndArray();
        }
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
