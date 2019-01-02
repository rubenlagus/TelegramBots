package org.telegram.telegrambots.meta.api.objects.inlinequery.result.chached;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a link to a file stored on the Telegram servers. By default, this file will be
 * sent by the user with an optional caption. Alternatively, you can use input_message_content to
 * send a message with the specified content instead of the file.
 * @note Currently, only pdf-files and zip archives can be sent using this method.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @deprecated  Replaced by {@link org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedDocument}
 */
@Deprecated
public class InlineQueryResultCachedDocument  extends org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedDocument {
}
