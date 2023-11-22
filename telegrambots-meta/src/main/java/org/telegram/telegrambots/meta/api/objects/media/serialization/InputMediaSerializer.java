package org.telegram.telegrambots.meta.api.objects.media.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * JSON serializer for InputMedia type
 */
public class InputMediaSerializer extends JsonSerializer<InputMedia> {
    @Override
    public void serialize(InputMedia value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(InputMedia.MEDIA_FIELD, value.getMedia());
        gen.writeStringField(InputMedia.TYPE_FIELD, value.getType());
        if (value.getCaption() != null) {
            gen.writeStringField(InputMedia.CAPTION_FIELD, value.getCaption());
        }
        if (value.getParseMode() != null) {
            gen.writeStringField(InputMedia.PARSEMODE_FIELD, value.getParseMode());
        }
        if (value.getCaptionEntities() != null) {
            gen.writeArrayFieldStart(InputMedia.CAPTIONENTITIES_FIELD);
            for (MessageEntity entity : value.getCaptionEntities()) {
                gen.writeObject(entity);
            }
            gen.writeEndArray();
        }

        serializeAAInputMedia(value, gen);

        gen.writeEndObject();
    }

    private void  serializeAAInputMedia(InputMedia value, JsonGenerator gen) throws IOException {
        if (value instanceof InputMediaAudio) {
            serializeInputMediaAudio(value, gen);
        } else if (value instanceof InputMediaAnimation) {
            serializeInputMediaAnimation(value, gen);
        } else if (value instanceof InputMediaDocument) {
            serializeInputMediaDocument(value, gen);
        } else if (value instanceof InputMediaPhoto) {
            serializeInputMediaPhoto(value, gen);
        } else if (value instanceof InputMediaVideo) {
            serializeInputMediaVideo(value, gen);
        }
    }

    private void serializeInputMediaAudio(InputMedia value, JsonGenerator gen) throws IOException {
        InputMediaAudio audio = (InputMediaAudio) value;
        if (audio.getThumbnail() != null) {
            gen.writeStringField(InputMediaAudio.THUMBNAIL_FIELD, audio.getThumbnail().getAttachName());
        }
        if (audio.getDuration() != null) {
            gen.writeNumberField(InputMediaAudio.DURATION_FIELD, audio.getDuration());
        }
        if (audio.getPerformer() != null) {
            gen.writeStringField(InputMediaAudio.PERFORMER_FIELD, audio.getPerformer());
        }
        if (audio.getTitle() != null) {
            gen.writeStringField(InputMediaAudio.TITLE_FIELD, audio.getTitle());
        }
    }

    private void serializeInputMediaAnimation(InputMedia value, JsonGenerator gen) throws IOException {
        InputMediaAnimation animation = (InputMediaAnimation) value;
        if (animation.getThumbnail() != null) {
            gen.writeStringField(InputMediaAnimation.THUMBNAIL_FIELD, animation.getThumbnail().getAttachName());
        }
        if (animation.getDuration() != null) {
            gen.writeNumberField(InputMediaAnimation.DURATION_FIELD, animation.getDuration());
        }
        if (animation.getHeight() != null) {
            gen.writeNumberField(InputMediaAnimation.HEIGHT_FIELD, animation.getHeight());
        }
        if (animation.getWidth() != null) {
            gen.writeNumberField(InputMediaAnimation.WIDTH_FIELD, animation.getWidth());
        }
        if (animation.getHasSpoiler() != null) {
            gen.writeBooleanField(InputMediaAnimation.HASSPOILER_FIELD, animation.getHasSpoiler());
        }
    }

    private void serializeInputMediaDocument(InputMedia value, JsonGenerator gen) throws IOException {
        InputMediaDocument document = (InputMediaDocument) value;
        if (document.getThumbnail() != null) {
            gen.writeStringField(InputMediaDocument.THUMBNAIL_FIELD, document.getThumbnail().getAttachName());
        }
    }

    private void serializeInputMediaPhoto(InputMedia value, JsonGenerator gen) throws IOException {
        InputMediaPhoto photo = (InputMediaPhoto) value;
        if (photo.getHasSpoiler() != null) {
            gen.writeBooleanField(InputMediaPhoto.HASSPOILER_FIELD, photo.getHasSpoiler());
        }
    }

    private void serializeInputMediaVideo(InputMedia value, JsonGenerator gen) throws IOException {
        InputMediaVideo video = (InputMediaVideo) value;
        if (video.getThumbnail() != null) {
            gen.writeStringField(InputMediaVideo.THUMBNAIL_FIELD, video.getThumbnail().getAttachName());
        }
        if (video.getDuration() != null) {
            gen.writeNumberField(InputMediaVideo.DURATION_FIELD, video.getDuration());
        }
        if (video.getHeight() != null) {
            gen.writeNumberField(InputMediaVideo.HEIGHT_FIELD, video.getHeight());
        }
        if (video.getWidth() != null) {
            gen.writeNumberField(InputMediaVideo.WIDTH_FIELD, video.getWidth());
        }
        if (video.getSupportsStreaming() != null) {
            gen.writeBooleanField(InputMediaVideo.SUPPORTSSTREAMING_FIELD, video.getSupportsStreaming());
        }
        if (video.getHasSpoiler() != null) {
            gen.writeBooleanField(InputMediaVideo.HASSPOILER_FIELD, video.getHasSpoiler());
        }
    }

    @Override
    public void serializeWithType(InputMedia value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(value, gen, serializers);
    }
}
