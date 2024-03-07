package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents one special entity in a text message. For example, hashtags,
 * usernames, URL.
 * @author Ruben Bermudez
 * @version 1.0
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class MessageEntity implements BotApiObject {
    private static final String TYPE_FIELD = "type";
    private static final String OFFSET_FIELD = "offset";
    private static final String LENGTH_FIELD = "length";
    private static final String URL_FIELD = "url";
    private static final String USER_FIELD = "user";
    private static final String LANGUAGE_FIELD = "language";
    private static final String CUSTOMEMOJI_FIELD = "custom_emoji_id";
    /**
     * Type of the entity.
     * Currently, can be:
     * - “mention” (@username)
     * - “hashtag” (#hashtag)
     * - “cashtag” ($USD)
     * - “bot_command” (/start@jobs_bot)
     * - “url” (https://telegram.org)
     * - “email” (do-not-reply@telegram.org)
     * - “phone_number” (+1-212-555-0123),
     * - “bold” (bold text)
     * - “italic” (italic text)
     * - “underline” (underlined text)
     * - “strikethrough” (strikethrough text)
     * - “spoiler” (spoiler message)
     * - “blockquote” (block quotation)
     * - “code” (monowidth string)
     * - “pre” (monowidth block)
     * - “text_link” (for clickable text URLs)
     * - “text_mention” (for users without usernames)
     * - "custom_emoji" (for inline custom emoji stickers)
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private String type;
    /**
     * Offset in UTF-16 code units to the start of the entity
     */
    @JsonProperty(OFFSET_FIELD)
    @NonNull
    private Integer offset;
    /**
     * Length of the entity in UTF-16 code units
     */
    @JsonProperty(LENGTH_FIELD)
    @NonNull
    private Integer length;
    /**
     * Optional.
     * For “text_link” only, url that will be opened after user taps on the text
     */
    @JsonProperty(URL_FIELD)
    private String url;
    /**
     * Optional.
     * For “text_mention” only, the mentioned user
     */
    @JsonProperty(USER_FIELD)
    private User user;
    /**
     * Optional.
     * For “pre” only, the programming language of the entity text
     */
    @JsonProperty(LANGUAGE_FIELD)
    private String language;
    /**
     * Optional.
     * For “custom_emoji” only, unique identifier of the custom emoji.
     * Use getCustomEmojiStickers to get full information about the sticker
     */
    @JsonProperty(CUSTOMEMOJI_FIELD)
    private String customEmojiId;
    /**
     * Text present in the entity. Computed from offset and length
     */
    @JsonIgnore
    private String text;

    protected void computeText(String message) {
        if (message != null) {
            text = message.substring(offset, offset + length);
        }
    }
}
