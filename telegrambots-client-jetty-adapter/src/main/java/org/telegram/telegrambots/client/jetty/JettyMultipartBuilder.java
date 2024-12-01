package org.telegram.telegrambots.client.jetty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.client.InputStreamRequestContent;
import org.eclipse.jetty.client.MultiPartRequestContent;
import org.eclipse.jetty.client.PathRequestContent;
import org.eclipse.jetty.client.Request;
import org.eclipse.jetty.client.StringRequestContent;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.MultiPart;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.paid.InputPaidMedia;
import org.telegram.telegrambots.meta.api.objects.stickers.InputSticker;

import java.io.IOException;
import java.util.List;

/**
 * @author Valeriy Kucherenko
 * @since 05.09.2024
 */
public class JettyMultipartBuilder {
    private final MultiPartRequestContent multiPart = new MultiPartRequestContent();
    private final ObjectMapper mapper;

    public JettyMultipartBuilder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public MultiPartRequestContent build() {
        multiPart.close();
        return multiPart;
    }

    /**
     * Add field to the builder if value is not null
     * @param fieldName the field name to add to the multipart
     * @param value the nullable value to add
     * @return the builder
     */
    public JettyMultipartBuilder addPart(String fieldName, String value) {
        if (value != null) {
            multiPart.addPart(new MultiPart.ContentSourcePart(fieldName, null, HttpFields.EMPTY, new StringRequestContent(value)));
        }
        return this;
    }

    /**
     * Add field to the builder if value is not null. The value is converted using toString()
     * @param fieldName the field name to add to the multipart
     * @param value the nullable value to add
     * @return the builder
     */
    public JettyMultipartBuilder addPart(String fieldName, Object value) {
        if (value != null) {
            this.addPart(fieldName, value.toString());
        }
        return this;
    }

    /**
     * Add field to the builder if value is not null. The value is converted using ObjectMapper.writeValueAsString()
     * @param fieldName the field name to add to the multipart
     * @param value the nullable value to add
     * @return the builder
     */
    public JettyMultipartBuilder addJsonPart(String fieldName, Object value) throws JsonProcessingException {
        if (value != null) {
            this.addPart(fieldName, mapper.writeValueAsString(value));
        }
        return this;
    }

    public JettyMultipartBuilder addInputFile(String fileField, InputFile file, boolean addField) throws IOException {
        if (file == null) {
            return this;
        }

        if (file.isNew()) {
            Request.Content body = null;
            if (file.getNewMediaFile() != null) {
                body = new PathRequestContent("application/octet-stream", file.getNewMediaFile().toPath());
            } else if (file.getNewMediaStream() != null) {
                body = new InputStreamRequestContent("application/octet-stream", file.getNewMediaStream());
            }
            if (body != null) {
                multiPart.addPart(new MultiPart.ContentSourcePart(file.getMediaName(), file.getMediaName(), HttpFields.EMPTY, body));
            }
        }

        if (addField) {
            this.addPart(fileField, file.getAttachName());
        }

        return this;
    }

    public JettyMultipartBuilder addMedia(InputMedia media) throws IOException {
        if (media == null) {
            return this;
        }

        if (media.isNewMedia()) {
            Request.Content body = null;
            if (media.getNewMediaFile() != null) {
                body = new PathRequestContent("application/octet-stream", media.getNewMediaFile().toPath());
            } else if (media.getNewMediaStream() != null) {
                body = new InputStreamRequestContent("application/octet-stream", media.getNewMediaStream());
            }
            if (body != null) {
                multiPart.addPart(new MultiPart.ContentSourcePart(media.getMediaName(), media.getMediaName(), HttpFields.EMPTY, body));
            }
        }

        return this;
    }

    public JettyMultipartBuilder addMedia(InputPaidMedia media) throws IOException {
        if (media == null) {
            return this;
        }

        if (media.isNewMedia()) {
            Request.Content body = null;
            if (media.getNewMediaFile() != null) {
                body = new PathRequestContent("application/octet-stream", media.getNewMediaFile().toPath());
            } else if (media.getNewMediaStream() != null) {
                body = new InputStreamRequestContent("application/octet-stream", media.getNewMediaStream());
            }
            if (body != null) {
                multiPart.addPart(new MultiPart.ContentSourcePart(media.getMediaName(), media.getMediaName(), HttpFields.EMPTY, body));
            }
        }

        return this;
    }

    public JettyMultipartBuilder addInputStickers(String stickersField, List<InputSticker> stickers) throws IOException {
        for (InputSticker sticker : stickers) {
            addInputFile(null, sticker.getSticker(), false);
        }

        addJsonPart(stickersField, stickers);

        return this;
    }
}
