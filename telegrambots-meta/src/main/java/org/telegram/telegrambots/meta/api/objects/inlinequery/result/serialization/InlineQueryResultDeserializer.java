package org.telegram.telegrambots.meta.api.objects.inlinequery.result.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.*;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.*;

import java.io.IOException;

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

        switch (node.get("type").asText()) {
            case "article":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultArticle>(){});
            case "audio":
                if (node.has("audio_url")) {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultAudio>(){});
                } else {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultCachedAudio>(){});
                }
            case "contact":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultContact>(){});
            case "document":
                if (node.has("document_url")) {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultDocument>(){});
                } else {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultCachedDocument>(){});
                }
            case "game":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultGame>(){});
            case "gif":
                if (node.has("gif_url")) {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultGif>(){});
                } else {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultCachedGif>(){});
                }
            case "location":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultLocation>(){});
            case "mpeg4_gif":
                if (node.has("mpeg4_url")) {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultMpeg4Gif>(){});
                } else {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultCachedMpeg4Gif>(){});
                }
            case "photo":
                if (node.has("photo_url")) {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultPhoto>(){});
                } else {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultCachedPhoto>(){});
                }
            case "venue":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultVenue>(){});
            case "video":
                if (node.has("video_url")) {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultVideo>(){});
                } else {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultCachedVideo>(){});
                }
            case "voice":
                if (node.has("voice_url")) {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultVoice>(){});
                } else {
                    return objectMapper.readValue(node.toString(),
                            new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultCachedVoice>(){});
                }
            case "sticker":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<InlineQueryResultCachedSticker>(){});
        }

        return null;
    }
}
