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
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents one special entity in a text message. For example, hashtags,
 * usernames, URL.
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity implements BotApiObject {
    private static final String TYPE_FIELD = "type";
    private static final String OFFSET_FIELD = "offset";
    private static final String LENGTH_FIELD = "length";
    private static final String URL_FIELD = "url";
    private static final String USER_FIELD = "user";
    private static final String LANGUAGE_FIELD = "language";
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
     * - “code” (monowidth string)
     * - “pre” (monowidth block)
     * - “text_link” (for clickable text URLs)
     * - “text_mention” (for users without usernames)
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private String type;
    @JsonProperty(OFFSET_FIELD)
    @NonNull
    private Integer offset; ///< Offset in UTF-16 code units to the start of the entity
    @JsonProperty(LENGTH_FIELD)
    @NonNull
    private Integer length; ///< Length of the entity in UTF-16 code units
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. For “text_link” only, url that will be opened after user taps on the text
    @JsonProperty(USER_FIELD)
    private User user; ///< Optional. For “text_mention” only, the mentioned user
    @JsonProperty(LANGUAGE_FIELD)
    private String language; ///< Optional. For “pre” only, the programming language of the entity text
    @JsonIgnore
    private String text; ///< Text present in the entity. Computed from offset and length

    protected void computeText(String message) {
        if (message != null) {
            text = message.substring(offset, offset + length);
        }
    }
}
