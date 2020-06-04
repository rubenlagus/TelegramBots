package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 4.7
 * This object represents an animated emoji that displays a random value.
 */
public class Dice implements BotApiObject {
    private static final String VALUE_FIELD = "value";
    private static final String EMOJI_FIELD = "emoji";

    @JsonProperty(VALUE_FIELD)
    private Integer value; ///< Value of the dice, 1-6 for “🎲” and “🎯” base emoji, 1-5 for “🏀” base emoji
    @JsonProperty(EMOJI_FIELD)
    private String emoji; ///< Emoji on which the dice throw animation is based

    public Dice() {
        super();
    }

    public Integer getValue() {
        return value;
    }

    public String getEmoji() {
        return emoji;
    }

    @Override
    public String toString() {
        return "Dice{" +
                "value=" + value +
                ", emoji='" + emoji + '\'' +
                '}';
    }
}
