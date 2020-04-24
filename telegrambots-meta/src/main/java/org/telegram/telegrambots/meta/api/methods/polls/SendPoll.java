package org.telegram.telegrambots.meta.api.methods.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send a native poll.
 * A native poll can't be sent to a private chat.
 *
 * On success, the sent Message is returned.
 */
public class SendPoll extends BotApiMethod<Message> {
    public static final String PATH = "sendPoll";

    private static final String CHATID_FIELD = "chat_id";
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

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername).
     * A native poll can't be sent to a private chat.
     */
    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(QUESTION_FIELD)
    private String question; ///< Poll question, 1-255 characters
    @JsonProperty(OPTIONS_FIELD)
    private List<String> options = new ArrayList<>(); ///< List of answer options, 2-10 strings 1-100 characters each
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

    public SendPoll() {
        super();
    }

    public SendPoll(String chatId, String question, List<String> options) {
        this.chatId = checkNotNull(chatId);
        this.question = checkNotNull(question);
        this.options = checkNotNull(options);
    }

    public SendPoll(Long chatId, String question, List<String> options) {
        this.chatId = checkNotNull(chatId).toString();
        this.question = checkNotNull(question);
        this.options = checkNotNull(options);
    }

    public String getChatId() {
        return chatId;
    }

    public SendPoll setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SendPoll setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public SendPoll setQuestion(String question) {
        this.question = question;
        return this;
    }

    public List<String> getOptions() {
        return options;
    }

    public SendPoll setOptions(List<String> options) {
        this.options = options;
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendPoll setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendPoll setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }


    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendPoll enableNotification() {
        this.disableNotification = null;
        return this;
    }

    public SendPoll disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public SendPoll setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
        return this;
    }

    public String getType() {
        return type;
    }

    public SendPoll setType(String type) {
        this.type = type;
        return this;
    }

    public Boolean getAllowMultipleAnswers() {
        return allowMultipleAnswers;
    }

    public SendPoll setAllowMultipleAnswers(Boolean allowMultipleAnswers) {
        this.allowMultipleAnswers = allowMultipleAnswers;
        return this;
    }

    public Integer getCorrectOptionId() {
        return correctOptionId;
    }

    public SendPoll setCorrectOptionId(Integer correctOptionId) {
        this.correctOptionId = correctOptionId;
        return this;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public SendPoll setClosed(Boolean closed) {
        isClosed = closed;
        return this;
    }

    public Integer getOpenPeriod() {
        return openPeriod;
    }

    public SendPoll setOpenPeriod(Integer openPeriod) {
        this.openPeriod = openPeriod;
        return this;
    }

    public Integer getCloseDate() {
        return closeDate;
    }

    public SendPoll setCloseDate(Integer closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public String getExplanation() {
        return explanation;
    }

    public SendPoll setExplanation(String explanation) {
        this.explanation = explanation;
        return this;
    }

    public String getExplanationParseMode() {
        return explanationParseMode;
    }

    public SendPoll setExplanationParseMode(String explanationParseMode) {
        this.explanationParseMode = explanationParseMode;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending poll", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (question == null || question.isEmpty()) {
            throw new TelegramApiValidationException("Question parameter can't be empty", this);
        }
        if (options == null || options.size() < 2 || options.size() > 10) {
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
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendPoll)) return false;
        SendPoll sendPoll = (SendPoll) o;
        return Objects.equals(chatId, sendPoll.chatId) &&
                Objects.equals(question, sendPoll.question) &&
                Objects.equals(options, sendPoll.options) &&
                Objects.equals(isAnonymous, sendPoll.isAnonymous) &&
                Objects.equals(type, sendPoll.type) &&
                Objects.equals(allowMultipleAnswers, sendPoll.allowMultipleAnswers) &&
                Objects.equals(correctOptionId, sendPoll.correctOptionId) &&
                Objects.equals(isClosed, sendPoll.isClosed) &&
                Objects.equals(disableNotification, sendPoll.disableNotification) &&
                Objects.equals(replyToMessageId, sendPoll.replyToMessageId) &&
                Objects.equals(replyMarkup, sendPoll.replyMarkup) &&
                Objects.equals(openPeriod, sendPoll.openPeriod) &&
                Objects.equals(closeDate, sendPoll.closeDate) &&
                Objects.equals(explanation, sendPoll.explanation) &&
                Objects.equals(explanationParseMode, sendPoll.explanationParseMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, question, options, isAnonymous, type, allowMultipleAnswers, correctOptionId, isClosed,
                disableNotification, replyToMessageId, replyMarkup, openPeriod, closeDate, explanation, explanationParseMode);
    }

    @Override
    public String toString() {
        return "SendPoll{" +
                "chatId='" + chatId + '\'' +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", isAnonymous=" + isAnonymous +
                ", type='" + type + '\'' +
                ", allowMultipleAnswers=" + allowMultipleAnswers +
                ", correctOptionId=" + correctOptionId +
                ", isClosed=" + isClosed +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", openPeriod=" + openPeriod +
                ", closeDate=" + closeDate +
                ", explanation='" + explanation + '\'' +
                ", explanationParseMode='" + explanationParseMode + '\'' +
                '}';
    }
}
