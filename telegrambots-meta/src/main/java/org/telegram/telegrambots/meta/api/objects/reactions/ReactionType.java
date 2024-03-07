package org.telegram.telegrambots.meta.api.objects.reactions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.reactions.serialization.ReactionDeserializer;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes the type of reaction.
 */
@JsonDeserialize(using = ReactionDeserializer.class)
public interface ReactionType extends Validable, BotApiObject {
    String EMOJI_TYPE = "emoji";
    String CUSTOM_EMOJI_TYPE = "custom_emoji";
}
