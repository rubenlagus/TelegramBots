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
import org.telegram.telegrambots.api.objects.User;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents one row of a game high scores table
 * @date 25 of September of 2016
 */
public class GameHighScore implements IBotApiObject {
    private static final String POSITION_FIELD = "position";
    private static final String USER_FIELD = "user";
    private static final String SCORE_FIELD = "score";

    @JsonProperty(POSITION_FIELD)
    private Integer position; ///< Position in the game high score table
    @JsonProperty(USER_FIELD)
    private User user; ///< User
    @JsonProperty(SCORE_FIELD)
    private Integer score; ///< Score

    public GameHighScore(JSONObject object) {
        position = object.getInt(POSITION_FIELD);
        user = new User(object.getJSONObject(USER_FIELD));
        score = object.getInt(SCORE_FIELD);
    }

    public Integer getPosition() {
        return position;
    }

    public User getUser() {
        return user;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(POSITION_FIELD, position);
        gen.writeObjectField(USER_FIELD, user);
        gen.writeNumberField(SCORE_FIELD, score);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "GameHighScore{" +
                "position=" + position +
                ", user=" + user +
                ", score=" + score +
                '}';
    }
}
