package org.telegram.telegrambots.meta.api.methods.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send a native poll.
 * A native poll can't be sent to a private chat.
 *
 * On success, the sent Message is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SendPoll extends BotApiMethodMessage {
    public static final String PATH = "sendPoll";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";
    private static final String QUESTION_FIELD = "question";
    private static final String OPTIONS_FIELD = "options";
    private static final String ISANONYMOUS_FIELD = "is_anonymous";
    private static final String TYPE_FIELD = "type";
    private static final String ALLOWMULTIPLEANSWERS_FIELD = "allows_multiple_answers";
    private static final String CORRECTOPTIONID_FIELD = "correct_option_id";
    private static final String ISCLOSED_FIELD = "is_closed";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";
    private static final String OPENPERIOD_FIELD = "open_period";
    private static final String CLOSEDATE_FIELD = "close_date";
    private static final String EXPLANATION_FIELD = "explanation";
    private static final String EXPLANATIONPARSEMODE_FIELD = "explanation_parse_mode";
    private static final String EXPLANATION_ENTITIES_FIELD = "explanation_entities";
    private static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";
    private static final String PROTECTCONTENT_FIELD = "protect_content";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername).
     * A native poll can't be sent to a private chat.
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    @JsonProperty(MESSAGETHREADID_FIELD)
    private Integer messageThreadId;
    @JsonProperty(QUESTION_FIELD)
    @NonNull
    private String question; ///< Poll question, 1-300 characters
    @JsonProperty(OPTIONS_FIELD)
    @Singular
    @NonNull
    private List<String> options; ///< List of answer options, 2-10 strings 1-100 characters each
    @JsonProperty(ISANONYMOUS_FIELD)
    private Boolean isAnonymous; ///< Optional	True, if the poll needs to be anonymous, defaults to True
    @JsonProperty(TYPE_FIELD)
    private String type; ///< Optional	Poll type, “quiz” or “regular”, defaults to “regular”
    @JsonProperty(ALLOWMULTIPLEANSWERS_FIELD)
    private Boolean allowMultipleAnswers; ///< Optional	True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
    @JsonProperty(CORRECTOPTIONID_FIELD)
    private Integer correctOptionId; ///< Optional	0-based identifier of the correct answer option, required for polls in quiz mode
    @JsonProperty(ISCLOSED_FIELD)
    private Boolean isClosed; ///< Optional	Pass True, if the poll needs to be immediately closed
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    @JsonProperty(REPLYMARKUP_FIELD)
    @JsonDeserialize()
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    @JsonProperty(OPENPERIOD_FIELD)
    private Integer openPeriod; ///< Optional. Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
    @JsonProperty(CLOSEDATE_FIELD)
    private Integer closeDate; ///< Optional. Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than 600 seconds in the future. Can't be used together with open_period.
    @JsonProperty(EXPLANATION_FIELD)
    private String explanation; ///< Optional. Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters with at most 2 line feeds after entities parsing
    @JsonProperty(EXPLANATIONPARSEMODE_FIELD)
    private String explanationParseMode; ///< Optional. Mode for parsing entities in the explanation. See formatting options for more details.
    @JsonProperty(EXPLANATION_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> explanationEntities; ///< Optional. List of special entities that appear in the poll explanation, which can be specified instead of parse_mode
    @JsonProperty(ALLOWSENDINGWITHOUTREPLY_FIELD)
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
    @JsonProperty(PROTECTCONTENT_FIELD)
    private Boolean protectContent; ///< Optional. Protects the contents of sent messages from forwarding and saving
    /**
     * Optional
     * Description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    public void enableNotification() {
        this.disableNotification = null;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (question.isEmpty()) {
            throw new TelegramApiValidationException("Question parameter can't be empty", this);
        }
        if (options.size() < 2 || options.size() > 10) {
            throw new TelegramApiValidationException("Options parameter must be between 2 and 10 item", this);
        }
        if (openPeriod != null && closeDate != null) {
            throw new TelegramApiValidationException("Only one of Open Period and Close Date are allowed", this);
        }
        if (openPeriod != null && (openPeriod < 5 || openPeriod > 600)) {
            throw new TelegramApiValidationException("Open period can only be between 5 and 600", this);
        }
        if (explanation != null && explanation.length() > 200) {
            throw new TelegramApiValidationException("Explanation can only have up to 200 characters", this);
        }
        if (options.parallelStream().anyMatch(x -> x.isEmpty() || x.length() > 100)) {
            throw new TelegramApiValidationException("Options parameter values must be between 1 and 100 chars length", this);
        }
        if (explanationParseMode != null && (explanationEntities != null && !explanationEntities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }


    public static class SendPollBuilder {

        @Tolerate
        public SendPollBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
