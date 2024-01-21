package org.telegram.telegrambots.meta.api.objects.reactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The reaction is based on an emoji.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ReactionTypeEmoji implements ReactionType {
    private static final String TYPE_FIELD = "type";
    private static final String EMOJI_FIELD = "emoji";

    /**
     * Type of the reaction, always “emoji”
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    @Builder.Default
    private String type = ReactionType.EMOJI_TYPE;
    /**
     * Reaction emoji. Currently, it can be one of "👍", "👎", "❤", "🔥", "🥰", "👏", "😁", "🤔", "🤯", "😱", "🤬",
     * "😢", "🎉", "🤩", "🤮", "💩", "🙏", "👌", "🕊", "🤡", "🥱", "🥴", "😍", "🐳", "❤‍🔥", "🌚", "🌭", "💯", "🤣",
     * "⚡", "🍌", "🏆", "💔", "🤨", "😐", "🍓", "🍾", "💋", "🖕", "😈", "😴", "😭", "🤓", "👻", "👨‍💻", "👀", "🎃",
     * "🙈", "😇", "😨", "🤝", "✍", "🤗", "🫡", "🎅", "🎄", "☃", "💅", "🤪", "🗿", "🆒", "💘", "🙉", "🦄", "😘",
     * "💊", "🙊", "😎", "👾", "🤷‍♂", "🤷", "🤷‍♀", "😡"
     */
    @JsonProperty(EMOJI_FIELD)
    @NonNull
    private String emoji;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (emoji.isEmpty()) {
            throw new TelegramApiValidationException("Emoji parameter can't be empty", this);
        }
        if (ReactionType.EMOJI_TYPE.equals(type)) {
            throw new TelegramApiValidationException("Type must be \"emoji\"", this);
        }
    }
}
