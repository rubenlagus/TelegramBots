package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents one special entity in a text message. For example, hashtags,
 * usernames, URL.
 * @date 20 of June of 2015
 */
public class MessageEntity implements BotApiObject {
    /**
     * Class-container for entity type constants
     */
    class EntityTypes {
        /**
         * {@literal @}username
         */
        final public static String MENTION = "mention";

        /**
         * #hashtag
         */
        final public static String HASHTAG = "hashtag";

        /**
         * /bot_command
         */
        final public static String BOT_COMMAND = "bot_command";

        /**
         * https://example.com
         */
        final public static String URL = "url";

        /**
         * email@example.com
         */
        final public static String EMAIL = "email";

        /**
         * Text mention for users without usernames
         */
        final public static String TEXT_MENTION = "text_mention";

        final public static String BOLD_TEXT = "bold";
        final public static String ITALIC_TEXT = "italic";
        final public static String CODE_STRING = "code";
        final public static String CODE_BLOCK = "pre";

        private EntityTypes() {}
    }

    private static final String TYPE_FIELD = "type";
    private static final String OFFSET_FIELD = "offset";
    private static final String LENGTH_FIELD = "length";
    private static final String URL_FIELD = "url";
    private static final String USER_FIELD = "user";
    /**
     * Type of the entity. One of
     * mention (@username),
     * hashtag,
     * bot_command,
     * url,
     * email,
     * bold (bold text),
     * italic (italic text),
     * code (monowidth string),
     * pre (monowidth block),
     * text_link (for clickable text URLs)
     * text_mention (for users without usernames)
     */

    @JsonProperty(TYPE_FIELD)
    private String type;
    @JsonProperty(OFFSET_FIELD)
    private Integer offset; ///< Offset in UTF-16 code units to the start of the entity
    @JsonProperty(LENGTH_FIELD)
    private Integer length; ///< Length of the entity in UTF-16 code units
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. For “text_link” only, url that will be opened after user taps on the text
    @JsonProperty(USER_FIELD)
    private User user; ///< Optional. For “text_mention” only, the mentioned user
    @JsonIgnore
    private String text; ///< Text present in the entity. Computed from offset and length

    public MessageEntity() {
        super();
    }

    public String getType() {
        return type;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLength() {
        return length;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    protected void computeText(String message) {
        if (message != null) {
            text = message.substring(offset, offset + length);
        }
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "type='" + type + '\'' +
                ", offset=" + offset +
                ", length=" + length +
                ", url=" + url +
                ", user=" + user +
                '}';
    }
}
