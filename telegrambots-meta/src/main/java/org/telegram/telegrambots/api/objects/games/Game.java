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

import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.PhotoSize;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief This object represents a game.
 * Use BotFather to create and edit games, their short names will act as unique identifiers.
 * @date 27 of September of 2016
 */
public class Game implements BotApiObject {

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
    /**
     * Optional. Brief description of the game or high scores included in the game message.
     * Can be automatically edited to include current high scores for the game
     * when the bot calls setGameScore, or manually edited using editMessageText.
     * 0-4096 characters.
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional. Special entities that appear in text, such as usernames,
     * URLs, bot commands, etc.
     */
    @JsonProperty(TEXTENTITIES_FIELD)
    private List<MessageEntity> entities;
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation; ///< Optional. Animation

    public Game() {
        super();
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
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photo=" + photo +
                ", animation=" + animation +
                '}';
    }
}
