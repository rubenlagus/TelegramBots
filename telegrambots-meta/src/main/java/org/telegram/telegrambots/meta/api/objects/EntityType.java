package org.telegram.telegrambots.meta.api.objects;

/**
 * Types of messages entities
 * @author Ruben Bermudez
 * @version 1.0
 */
@SuppressWarnings("WeakerAccess")
public class EntityType {
    /**
     * @username
     */
    public static final String MENTION = "mention";
    /**
     * #hashtag
     */
    public static final String HASHTAG = "hashtag";
    /**
     * $USD
     */
    public static final String CASHTAG = "cashtag";
    /**
     * /botcommand
     */
    public static final String BOTCOMMAND = "bot_command";
    /**
     * http://url.url
     */
    public static final String URL = "url";
    /**
     * email@email.com
     */
    public static final String EMAIL = "email";
    /**
     * +4299999999
     */
    public static final String PHONENUMBER = "phone_number";
    /**
     * Bold text
     */
    public static final String BOLD = "bold";
    /**
     * Italic text
     */
    public static final String ITALIC = "italic";
    /**
     * Monowidth string
     */
    public static final String CODE = "code";
    /**
     * Monowidth block
     */
    public static final String PRE = "pre";
    /**
     * Clickable urls
     */
    public static final String TEXTLINK = "text_link";
    /**
     * for users without usernames
     */
    public static final String TEXTMENTION = "text_mention";
    /**
     * spoiler message
     */
    public static final String SPOILER = "spoiler"; 

    private EntityType() {
    }
}

