package org.telegram.telegrambots.meta.api.objects.inlinequery.result.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.*;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class InlineQueryResultDeserializer extends StdDeserializer<InlineQueryResult> {
    private final ObjectMapper objectMapper;

    public InlineQueryResultDeserializer() {
        this(null);
    }

    private InlineQueryResultDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public InlineQueryResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        return (InlineQueryResult) objectMapper.readValue(node.toString(), getInlineQueryResultArticle(node));
    }

    private TypeReference<?> getInlineQueryResultArticle(JsonNode node){

        switch (node.get("type").asText()) {
            case "article":
                return new TypeReference<InlineQueryResultArticle>(){};
            case "audio":
                return node.has("audio_url")
                        ? new TypeReference<InlineQueryResultAudio>(){}
                        : new TypeReference<InlineQueryResultCachedAudio>(){};
            case "contact":
                return new TypeReference<InlineQueryResultContact>(){};
            case "document":
                return node.has("document_url")
                        ? new TypeReference<InlineQueryResultDocument>(){}
                        : new TypeReference<InlineQueryResultCachedDocument>(){};
            case "game":
                return new TypeReference<InlineQueryResultGame>(){};
            case "gif":
                return node.has("gif_url")
                        ? new TypeReference<InlineQueryResultGif>(){}
                        : new TypeReference<InlineQueryResultCachedGif>(){};
            case "location":
                return new TypeReference<InlineQueryResultLocation>(){};
            case "mpeg4_gif":
                return node.has("mpeg4_url")
                        ? new TypeReference<InlineQueryResultMpeg4Gif>(){}
                        : new TypeReference<InlineQueryResultCachedMpeg4Gif>(){};
            case "photo":
                return node.has("photo_url")
                        ? new TypeReference<InlineQueryResultPhoto>(){}
                        : new TypeReference<InlineQueryResultCachedPhoto>(){};
            case "venue":
                return new TypeReference<InlineQueryResultVenue>(){};
            case "video":
                return node.has("video_url")
                        ? new TypeReference<InlineQueryResultVideo>(){}
                        : new TypeReference<InlineQueryResultCachedVideo>(){};
            case "voice":
                return node.has("voice_url")
                        ? new TypeReference<InlineQueryResultVoice>(){}
                        : new TypeReference<InlineQueryResultCachedVoice>(){};
            case "sticker":
                return new TypeReference<InlineQueryResultCachedSticker>(){};
        }
        return null;
    }
}
