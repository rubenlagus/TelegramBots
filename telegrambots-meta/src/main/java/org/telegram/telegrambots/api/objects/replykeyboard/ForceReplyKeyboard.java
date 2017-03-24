package org.telegram.telegrambots.api.objects.replykeyboard;



import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Upon receiving a message with this object, Telegram clients will display a reply interface
 * to the user (act as if the user has selected the bot‘s message and tapped ’Reply'). This can be
 * extremely useful if you want to create user-friendly step-by-step interfaces without having to
 * sacrifice privacy mode.
 * @date 22 of June of 2015
 */
public class ForceReplyKeyboard implements ReplyKeyboard {
    private static final String FORCEREPLY_FIELD = "force_reply";
    private static final String SELECTIVE_FIELD = "selective";

    /**
     * Shows reply interface to the user, as if they manually selected the bot‘s message and tapped
     * ’Reply'
     */

    private Boolean force_reply;
    /**
     * Use this parameter if you want to force reply from specific users only. Targets: 1) users
     * that are @mentioned in the text of the Message object; 2) if the bot's message is a reply
     * (has reply_to_message_id), sender of the original message.
     */

    private Boolean selective;

    public ForceReplyKeyboard() {
        super();
        this.force_reply = true;
    }

    public Boolean getForceReply() {
        return force_reply;
    }

    public Boolean getSelective() {
        return selective;
    }

    public ForceReplyKeyboard setSelective(Boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (force_reply == null) {
            throw new TelegramApiValidationException("ForceReply parameter can't not be null", this);
        }
    }

    @Override
    public String toString() {
        return "ForceReplyKeyboard{" +
                "forceReply=" + force_reply +
                ", selective=" + selective +
                '}';
    }
}
