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

package org.telegram.telegrambots.meta.api.methods.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * Use this method to set the score of the specified user in a game.
 * On success, if the message was sent by the bot, returns the edited Message,
 * otherwise returns True.
 *
 * Returns an error, if the new score is not greater than the user's current score in
 * the chat and force is False.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SetGameScore extends BotApiMethod<Serializable> {
    public static final String PATH = "setGameScore";

    private static final String USER_ID_FIELD = "user_id";
    private static final String SCORE_FIELD = "score";
    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String DISABLEEDITMESSAGE_FIELD = "disable_edit_message";
    private static final String FORCE_FIELD = "force";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Optional	Required if inline_message_id is not specified. Unique identifier for the target chat (or username of the target channel in the format @channelusername)
    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId; ///< Optional	Required if inline_message_id is not specified. Unique identifier of the sent message
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId; ///< Optional	Required if chat_id and message_id are not specified. Identifier of the inline message
    @JsonProperty(DISABLEEDITMESSAGE_FIELD)
    private Boolean disableEditMessage; ///< Optional	Pass True, if the game message should not be automatically edited to include the current scoreboard. Defaults to False
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId; ///< User identifier
    @JsonProperty(SCORE_FIELD)
    @NonNull
    private Integer score; ///< New score, must be positive
    @JsonProperty(FORCE_FIELD)
    private Boolean force; ///< Optional. Pass True, if the high score is allowed to decrease. This can be useful when fixing mistakes or banning cheaters

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Serializable deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error setting game score", result);
            }
        } catch (IOException e) {
            try {
                ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                        new TypeReference<ApiResponse<Message>>() {
                        });
                if (result.getOk()) {
                    return result.getResult();
                } else {
                    throw new TelegramApiRequestException("Error setting game score", result);
                }
            } catch (IOException e2) {
                throw new TelegramApiRequestException("Unable to deserialize response", e2);
            }
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null) {
            throw new TelegramApiValidationException("UserId parameter can't be empty", this);
        }
        if (score == null) {
            throw new TelegramApiValidationException("Score parameter can't be empty", this);
        }
        if (inlineMessageId == null) {
            if (chatId == null || chatId.isEmpty()) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (messageId == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chatId != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (messageId != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
        }
    }
}
