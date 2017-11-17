package org.telegram.telegrambots.api.objects.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.api.interfaces.Validable;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.InputStream;

/**
 * @author Ruben Bermudez
 * @version 3.5
 */
public abstract class InputMedia<T> implements InputBotApiObject, Validable {
    protected static final String TYPE_FIELD = "type";
    private static final String MEDIA_FIELD = "media";
    private static final String CAPTION_FIELD = "caption";

    @JsonProperty(MEDIA_FIELD)
    /**
     * File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended),
     * pass an HTTP URL for Telegram to get a file from the Internet, or pass "attach://<file_attach_name>"
     * to upload a new one using multipart/form-data under <file_attach_name> name.
     */
    private String media;
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the media to be sent, 0-200 characters

    @JsonIgnore
    private boolean isNewMedia; ///< True to upload a new media, false to use a fileId or URL
    @JsonIgnore
    private String mediaName; ///< Name of the media to upload
    @JsonIgnore
    private File newMediaFile; ///< New media file
    @JsonIgnore
    private InputStream newMediaStream; ///< New media stream

    public InputMedia() {
        super();
    }

    public InputMedia(String media, String caption) {
        this.media = media;
        this.caption = caption;
    }

    public String getMedia() {
        return media;
    }

    public File getMediaFile() {
        return newMediaFile;
    }

    public InputStream getNewMediaStream() {
        return newMediaStream;
    }

    public String getMediaName() {
        return mediaName;
    }

    @JsonIgnore
    public boolean isNewMedia() {
        return isNewMedia;
    }

    /**
     * Use this setter to send an existing file (using file_id) or an url.
     * @param media File_id or URL of the file to send
     * @return This object
     */
    public T setMedia(String media) {
        this.media = media;
        this.isNewMedia = false;
        return (T) this;
    }

    /**
     * Use this setter to send new file.
     * @param mediaFile File to send
     * @return This object
     */
    public T setMedia(File mediaFile, String fileName) {
        this.newMediaFile = mediaFile;
        this.isNewMedia = true;
        this.mediaName = fileName;
        this.media = "attach://" + fileName;
        return (T) this;
    }

    /**
     * Use this setter to send new file as stream.
     * @param mediaStream File to send
     * @return This object
     */
    public T setMedia(InputStream mediaStream, String fileName) {
        this.newMediaStream = mediaStream;
        this.isNewMedia = true;
        this.mediaName = fileName;
        this.media = "attach://" + fileName;
        return (T) this;
    }

    public String getCaption() {
        return caption;
    }

    public InputMedia setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (isNewMedia) {
            if (mediaName == null || mediaName.isEmpty()) {
                throw new TelegramApiValidationException("Media name can't be empty", this);
            }
            if (newMediaFile == null && newMediaStream == null) {
                throw new TelegramApiValidationException("Media can't be empty", this);
            }
        } else if (media == null || media.isEmpty()) {
            throw new TelegramApiValidationException("Media can't be empty", this);
        }
    }

    @JsonProperty(TYPE_FIELD)
    public abstract String getType();
}
