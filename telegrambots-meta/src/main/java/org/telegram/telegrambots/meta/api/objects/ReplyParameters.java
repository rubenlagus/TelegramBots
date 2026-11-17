package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.richtext.RichText;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextConcat;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextCustomEmoji;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextMathematicalExpression;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Describes reply parameters for the message that is being sent.
 * @author Ruben Bermudez
 * @version 10.2
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyParameters implements BotApiObject, Validable {
    private static final long serialVersionUID = -4041620488663382981L;
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String EPHEMERAL_MESSAGE_ID_FIELD = "ephemeral_message_id";
    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String ALLOW_SENDING_WITHOUT_REPLY_FIELD = "allow_sending_without_reply";
    private static final String QUOTE_PARSE_MODE_FIELD = "quote_parse_mode";
    private static final String QUOTE_FIELD = "quote";
    private static final String QUOTE_ENTITIES_FIELD = "quote_entities";
    private static final String QUOTE_POSITION_FIELD = "quote_position";
    private static final String CHECKLIST_TASK_ID_FIELD = "checklist_task_id";
    private static final String POLL_OPTION_ID_FIELD = "poll_option_id";
    private static final String QUOTE_RICH_TEXTS_FIELD = "quote_rich_texts";

    /**
     * Optional.
     * Identifier of the message that will be replied to in the current chat, or in the chat chat_id if it is specified
     * @apiNote Required if ephemeralMessageId isn't specified.
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Optional.
     * Identifier of the incoming ephemeral message that will be replied to in the current chat.
     * A reply to an ephemeral message must itself be an ephemeral message.
     * An ephemeral message may only be replied to within 15 seconds of being sent.
     * @apiNote Required if messageId isn't specified.
     */
    @JsonProperty(EPHEMERAL_MESSAGE_ID_FIELD)
    private Integer ephemeralMessageId;
    /**
     * Optional.
     * If the message to be replied to is from a different chat, unique identifier for the chat or username of the channel (in the format @channelusername)
     * @apiNote Not supported for messages sent on behalf of a business account and messages from channel direct messages chats.
     */
    @JsonProperty(CHAT_ID_FIELD)
    private String chatId;

    /**
     * Optional.
     * Identifier of the specific checklist task to be replied to
     */
    @JsonProperty(CHECKLIST_TASK_ID_FIELD)
    private Integer checklistTaskId;
    /**
     * Optional.
     * Persistent identifier of the specific poll option to be replied to
     */
    @JsonProperty(POLL_OPTION_ID_FIELD)
    private String pollOptionId;

    /**
     * Optional.
     * Pass True if the message should be sent even if the specified message to be replied to is not found; can be used only for replies in the same chat and forum topic.
     * @apiNote Always False for replies in another chat or forum topic.
     * @apiNote Always True for messages sent on behalf of a business account.
     */
    @JsonProperty(ALLOW_SENDING_WITHOUT_REPLY_FIELD)
    private Boolean allowSendingWithoutReply;
    /**
     * Optional.
     * Quoted part of the message to be replied to; 0-1024 characters after entities parsing.
     * The quote must be an exact substring of the message to be replied to, including bold, italic, underline, strikethrough, spoiler, custom_emoji, and date_time entities.
     * The message will fail to send if the quote isn't found in the original message.
     */
    @JsonProperty(QUOTE_FIELD)
    private String quote;
    /**
     *  Optional.
     *  Mode for parsing entities in the quote. See formatting options for more details.
     */
    @JsonProperty(QUOTE_PARSE_MODE_FIELD)
    private String quoteParseMode;
    /**
     *  Optional.
     *  A JSON-serialized list of special entities that appear in the quote.
     *  It can be specified instead of quote_parse_mode.
     */
    @JsonProperty(QUOTE_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> quoteEntities;
    /**
     * Optional. Position of the quote in the original message in UTF-16 code units.
     * For rich messages, this is the position in the plain texts of all quotable texts joined by line feeds.
     */
    @JsonProperty(QUOTE_POSITION_FIELD)
    private Integer quotePosition;

    /**
     * Optional. Quoted parts of the rich message, one per quoted text in order of appearance.
     * Separate texts include paragraphs, headings, preformatted blocks, footers, mathematical expressions,
     * quotation texts and credits, details summaries, table captions and cells, and media captions and credits.
     * Texts of blocks nested in lists, block quotations, details, collages and slideshows are separate too.
     * The first and last may be partial; all others and mathematical expressions must be quoted in full.
     * Formatting and nesting must match the original. Buttons, anchors and dividers cannot be quoted.
     * Up to 32 texts with a total length of 1024 characters may be specified. Only plain text, bold,
     * italic, underline, strikethrough, spoiler, subscript, superscript, date-time, custom emoji,
     * mathematical expressions, and arrays of them are kept; other types are replaced with their text.
     * If non-empty, quote, quote_parse_mode and quote_entities are ignored.
     */
    @JsonProperty(QUOTE_RICH_TEXTS_FIELD)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RichText> quoteRichTexts;

    public ReplyParameters(Integer messageId) {
        this.messageId = messageId;
    }

    public ReplyParameters(Integer messageId, Integer ephemeralMessageId, String chatId, Integer checklistTaskId,
                           String pollOptionId, Boolean allowSendingWithoutReply, String quote, String quoteParseMode,
                           List<MessageEntity> quoteEntities, Integer quotePosition) {
        this(messageId, ephemeralMessageId, chatId, checklistTaskId, pollOptionId, allowSendingWithoutReply,
                quote, quoteParseMode, quoteEntities, quotePosition, null);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (messageId == null && ephemeralMessageId == null) {
            throw new TelegramApiValidationException("Either messageId or ephemeralMessageId parameter must be provided", this);
        }
        if (chatId != null && chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty string", this);
        }
        if (quoteRichTexts != null && !quoteRichTexts.isEmpty()) {
            if (quoteRichTexts.size() > 32) {
                throw new TelegramApiValidationException("quoteRichTexts must contain at most 32 text objects", this);
            }
            int totalLength = 0;
            for (RichText quoteRichText : quoteRichTexts) {
                int textLength = richTextLength(quoteRichText);
                if (textLength < 0) {
                    throw new TelegramApiValidationException("quoteRichTexts contains an unsupported or null text", this);
                }
                totalLength += textLength;
                if (totalLength > 1024) {
                    throw new TelegramApiValidationException("quoteRichTexts must not exceed 1024 characters", this);
                }
            }
        }
    }

    private int richTextLength(RichText richText) {
        if (richText == null) {
            return -1;
        }
        if (richText instanceof RichTextPlain plain) {
            return plain.getText() == null ? -1 : plain.getText().codePointCount(0, plain.getText().length());
        }
        if (richText instanceof RichTextConcat concat) {
            int totalLength = 0;
            if (concat.getTexts() == null) {
                return -1;
            }
            for (RichText text : concat.getTexts()) {
                int textLength = richTextLength(text);
                if (textLength < 0) {
                    return -1;
                }
                totalLength += textLength;
                if (totalLength > 1024) {
                    return totalLength;
                }
            }
            return totalLength;
        }
        if (richText instanceof RichTextMathematicalExpression expression) {
            String value = expression.getExpression();
            return value == null ? -1 : value.codePointCount(0, value.length());
        }
        if (richText instanceof RichTextCustomEmoji customEmoji) {
            String value = customEmoji.getAlternativeText();
            return value == null ? -1 : value.codePointCount(0, value.length());
        }
        try {
            Method getText = richText.getClass().getMethod("getText");
            Object value = getText.invoke(richText);
            if (value instanceof RichText nested) {
                return richTextLength(nested);
            }
            if (value instanceof String text) {
                return text.codePointCount(0, text.length());
            }
        } catch (ReflectiveOperationException ignored) {
            return -1;
        }
        return -1;
    }

    public static class ReplyParametersBuilder {

        @Tolerate
        public ReplyParameters.ReplyParametersBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
