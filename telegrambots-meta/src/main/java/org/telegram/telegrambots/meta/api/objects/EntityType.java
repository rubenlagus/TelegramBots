package org.telegram.telegrambots.meta.api.objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *  Types of messages entities
 */
@SuppressWarnings("WeakerAccess")
public class EntityType {
    public static final String MENTION = "mention"; ///< @username
    public static final String HASHTAG = "hashtag"; ///< #hashtag
    public static final String CASHTAG = "cashtag"; ///< $USD
    public static final String BOTCOMMAND = "bot_command"; ///< /botcommand
    public static final String URL = "url"; ///< http://url.url
    public static final String EMAIL = "email"; ///< email@email.com
    public static final String PHONENUMBER = "phone_number"; ///< +4299999999
    public static final String BOLD = "bold"; ///< Bold text
    public static final String ITALIC = "italic"; ///< Italic text
    public static final String CODE = "code"; ///< Monowidth string
    public static final String PRE = "pre"; ///< Monowidth block
    public static final String TEXTLINK = "text_link"; ///< Clickable urls
    public static final String TEXTMENTION = "text_mention"; ///< for users without usernames
    public static final String SPOILER = "spoiler"; ///< spoiler message

    private EntityType() {
    }
}

