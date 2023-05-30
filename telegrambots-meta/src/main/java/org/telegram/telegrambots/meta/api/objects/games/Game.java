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
package org.telegram.telegrambots.meta.api.objects.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.List;

/**
 * This object represents a game.
 * @author Ruben Bermudez
 * @version 2.4
 * @apiNote Use BotFather to create and edit games, their short names will act as unique identifiers.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Game implements BotApiObject {

    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String PHOTO_FIELD = "photo";
    private static final String ANIMATION_FIELD = "animation";
    private static final String TEXT_FIELD = "text";
    private static final String TEXTENTITIES_FIELD = "text_entities";

    /**
     * Title of the game
     */
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title;
    /**
     * Description of the game
     */
    @JsonProperty(DESCRIPTION_FIELD)
    @NonNull
    private String description;
    /**
     * Photo
     */
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private List<PhotoSize> photo;
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
    /**
     * Optional.
     * Animation
     */
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation;

    public boolean hasEntities() {
        return entities != null && !entities.isEmpty();
    }
}
