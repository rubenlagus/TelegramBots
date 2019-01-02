package org.telegram.telegrambots.meta.api.objects.inlinequery.result.chached;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a link to a voice message stored on the Telegram servers. By default, this
 * voice message will be sent by the user. Alternatively, you can use input_message_content to send
 * a message with the specified content instead of the voice message.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @deprecated  Replaced by {@link org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedVoice}
 */
@Deprecated
public class InlineQueryResultCachedVoice extends org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedVoice {
}
