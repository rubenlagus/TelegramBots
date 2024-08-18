package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedAudio;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedDocument;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedGif;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedMpeg4Gif;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedPhoto;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedSticker;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedVideo;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedVoice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents one result of an inline query.
 *
 * @apiNote All URLs passed in inline query results will be available to end users and therefore must be assumed to be public.
 */
//@JsonDeserialize(using = InlineQueryResultDeserializer.class)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InlineQueryResultArticle.class),
        @JsonSubTypes.Type(value = InlineQueryResultAudio.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedAudio.class),
        @JsonSubTypes.Type(value = InlineQueryResultContact.class),
        @JsonSubTypes.Type(value = InlineQueryResultDocument.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedDocument.class),
        @JsonSubTypes.Type(value = InlineQueryResultGame.class),
        @JsonSubTypes.Type(value = InlineQueryResultGif.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedGif.class),
        @JsonSubTypes.Type(value = InlineQueryResultLocation.class),
        @JsonSubTypes.Type(value = InlineQueryResultMpeg4Gif.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedMpeg4Gif.class),
        @JsonSubTypes.Type(value = InlineQueryResultPhoto.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedPhoto.class),
        @JsonSubTypes.Type(value = InlineQueryResultVenue.class),
        @JsonSubTypes.Type(value = InlineQueryResultVideo.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedVideo.class),
        @JsonSubTypes.Type(value = InlineQueryResultVoice.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedVoice.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedSticker.class),
})
public interface InlineQueryResult extends Validable, BotApiObject {
    List<String> VALIDTHUMBTYPES = Collections.unmodifiableList(Arrays.asList("image/jpeg", "image/gif", "video/mp4"));

}
