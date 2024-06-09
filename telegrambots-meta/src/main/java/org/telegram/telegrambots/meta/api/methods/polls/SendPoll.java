package org.telegram.telegrambots.meta.api.methods.polls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.polls.input.InputPollOption;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send a native poll.
 * A native poll can't be sent to a private chat.
 * <p>
 * On success, the sent Message is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendPoll extends BotApiMethodMessage {
    public static final String PATH = "sendPoll";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String QUESTION_FIELD = "question";
    private static final String OPTIONS_FIELD = "options"; // TODO
    private static final String IS_ANONYMOUS_FIELD = "is_anonymous";
    private static final String TYPE_FIELD = "type";
    private static final String ALLOW_MULTIPLE_ANSWERS_FIELD = "allows_multiple_answers";
    private static final String CORRECT_OPTION_ID_FIELD = "correct_option_id";
    private static final String IS_CLOSED_FIELD = "is_closed";
    private static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    private static final String REPLY_TO_MESSAGE_ID_FIELD = "reply_to_message_id";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String OPEN_PERIOD_FIELD = "open_period";
    private static final String CLOSE_DATE_FIELD = "close_date";
    private static final String EXPLANATION_FIELD = "explanation";
    private static final String EXPLANATION_PARSE_MODE_FIELD = "explanation_parse_mode";
    private static final String EXPLANATION_ENTITIES_FIELD = "explanation_entities";
    private static final String ALLOW_SENDING_WITHOUT_REPLY_FIELD = "allow_sending_without_reply";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String QUESTION_PARSE_MODE_FIELD = "question_parse_mode";
    private static final String QUESTION_ENTITIES_FIELD = "question_entities";

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername).
     * A native poll can't be sent to a private chat.
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;
    /**
     * Poll question, 1-300 characters
     */
    @JsonProperty(QUESTION_FIELD)
    @NonNull
    private String question;
    /**
     * A JSON-serialized list of 2-10 answer options
     */
    @JsonProperty(OPTIONS_FIELD)
    @Singular
    @NonNull
    private List<InputPollOption> options;
    /**
     * Optional
     * True, if the poll needs to be anonymous, defaults to True
     */
    @JsonProperty(IS_ANONYMOUS_FIELD)
    private Boolean isAnonymous;
    /**
     * Optional
     * Poll type, “quiz” or “regular”, defaults to “regular”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    /**
     * Optional
     * True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
     */
    @JsonProperty(ALLOW_MULTIPLE_ANSWERS_FIELD)
    private Boolean allowMultipleAnswers;
    /**
     * Optional
     * 0-based identifier of the correct answer option, required for polls in quiz mode
     */
    @JsonProperty(CORRECT_OPTION_ID_FIELD)
    private Integer correctOptionId;
    /**
     * Optional
     * Pass True, if the poll needs to be immediately closed
     */
    @JsonProperty(IS_CLOSED_FIELD)
    private Boolean isClosed;
    /**
     * Optional.
     * Sends the message silently.
     * Users will receive a notification with no sound.
     */
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification;
    /**
     * Optional.
     * If the message is a reply, ID of the original message
     */
    @JsonProperty(REPLY_TO_MESSAGE_ID_FIELD)
    private Integer replyToMessageId;
    /**
     * Optional.
     * Additional interface options.
     * A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove a reply keyboard
     * or to force a reply from the user.
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private ReplyKeyboard replyMarkup;
    /**
     * Optional.
     * Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
     */
    @JsonProperty(OPEN_PERIOD_FIELD)
    private Integer openPeriod;
    /**
     * Optional. Point in time (Unix timestamp) when the poll will be automatically closed.
     * Must be at least 5 and no more than 600 seconds in the future.
     * Can't be used together with open_period.
     */
    @JsonProperty(CLOSE_DATE_FIELD)
    private Integer closeDate;
    /**
     * Optional.
     * Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll,
     * 0-200 characters with at most 2 line feeds after entities parsing
     */
    @JsonProperty(EXPLANATION_FIELD)
    private String explanation;
    /**
     * Optional.
     * Mode for parsing entities in the explanation.
     * See formatting options for more details.
     */
    @JsonProperty(EXPLANATION_PARSE_MODE_FIELD)
    private String explanationParseMode;
    /**
     * Optional
     * A JSON-serialized list of special entities that appear in the poll explanation.
     * It can be specified instead of explanation_parse_mode
     */
    @JsonProperty(EXPLANATION_ENTITIES_FIELD)
    private List<MessageEntity> explanationEntities;
    /**
     * Optional
     * Pass True, if the message should be sent even if the specified replied-to message is not found
     */
    @JsonProperty(ALLOW_SENDING_WITHOUT_REPLY_FIELD)
    private Boolean allowSendingWithoutReply;
    /**
     * Optional.
     * Protects the contents of sent messages from forwarding and saving
     */
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent;
    /**
     * Optional
     * Description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;
    /**
     * Optional.
     * Unique identifier of the business connection on behalf of which the message will be sent
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;
    /**
     * Optional
     * Mode for parsing entities in the question.
     * See formatting options for more details.
     * Currently, only custom emoji entities are allowed
     */
    @JsonProperty(QUESTION_PARSE_MODE_FIELD)
    private String questionParseMode;
    /**
     * Optional
     * A JSON-serialized list of special entities that appear in the poll question.
     * It can be specified instead of question_parse_mode
     */
    @JsonProperty(QUESTION_ENTITIES_FIELD)
    private List<MessageEntity> questionEntities;


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
        if (openPeriod != null && closeDate != null) {
            throw new TelegramApiValidationException("Only one of Open Period and Close Date are allowed", this);
        }
        if (openPeriod != null && (openPeriod < 5 || openPeriod > 600)) {
            throw new TelegramApiValidationException("Open period can only be between 5 and 600", this);
        }
        if (explanation != null && explanation.length() > 200) {
            throw new TelegramApiValidationException("Explanation can only have up to 200 characters", this);
        }
        if (options.size() < 2 || options.size() > 10) {
            throw new TelegramApiValidationException("Options parameter must be between 2 and 10 item", this);
        }
        for (InputPollOption option : options) {
            option.validate();
        }
        if (explanationParseMode != null && (explanationEntities != null && !explanationEntities.isEmpty())) {
            throw new TelegramApiValidationException("Explanation Parse mode can't be enabled if Entities are provided", this);
        }
        if (questionParseMode != null && (questionEntities != null && !questionEntities.isEmpty())) {
            throw new TelegramApiValidationException("Question Parse mode can't be enabled if Entities are provided", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static abstract class SendPollBuilder<C extends SendPoll, B extends SendPollBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public SendPollBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
