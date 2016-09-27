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

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;
import org.telegram.telegrambots.api.interfaces.IValidable;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief This object contains information about a game that will be returned as a response to a callback query.
 * @date 16 of September of 2016
 */
public class CallbackGame implements IBotApiObject, IValidable {
    private static final String TITLE_FIELD = "title";
    private static final String ID_FIELD = "game_id";
    private static final String START_PARAMETER_FIELD = "start_parameter";

    @JsonProperty(TITLE_FIELD)
    private String title; ///< Game title, aim at 128 characters or lower
    @JsonProperty(ID_FIELD)
    private Integer gameId; ///< Game identifier
    @JsonProperty(START_PARAMETER_FIELD)
    private String startParameter; ///< Start parameter for the bot URL when users share the game with others. See deep linking for more info.

    public CallbackGame() {
        super();
    }

    public CallbackGame(JSONObject object) {
        super();
        title = object.getString(TITLE_FIELD);
        gameId = object.getInt(ID_FIELD);
        startParameter = object.getString(START_PARAMETER_FIELD);
    }

    public String getTitle() {
        return title;
    }

    public Integer getGameId() {
        return gameId;
    }

    public String getStartParameter() {
        return startParameter;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (gameId == null) {
            throw new TelegramApiValidationException("Id parameter can't be empty", this);
        }
        if (startParameter == null || startParameter.isEmpty()) {
            throw new TelegramApiValidationException("StartParameter parameter can't be empty", this);
        }
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TITLE_FIELD, title);
        gen.writeNumberField(ID_FIELD, gameId);
        gen.writeStringField(START_PARAMETER_FIELD, startParameter);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "CallbackGame{" +
                "title='" + title + '\'' +
                ", gameId=" + gameId +
                ", startParameter='" + startParameter + '\'' +
                '}';
    }
}
