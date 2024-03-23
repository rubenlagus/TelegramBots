package org.telegram.telegrambots.meta.api.methods;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Types of actions for SendChatAction method.
 */
public enum ActionType {
    TYPING("typing"),
    RECORD_VIDEO("record_video"),
    RECORD_VIDEO_NOTE("record_video_note"),
    RECORD_VOICE("record_voice"),
    UPLOAD_PHOTO("upload_photo"),
    UPLOAD_VIDEO("upload_video"),
    UPLOAD_VIDEO_NOTE("upload_video_note"),
    UPLOAD_VOICE("upload_voice"),
    UPLOAD_DOCUMENT("upload_document"),
    FIND_LOCATION("find_location");

    private final String text;

    ActionType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static ActionType get(String text) {
        if (text == null) {
            return null;
        }
        switch (text) {
            case "typing":
                return TYPING;
            case "record_video":
                return RECORD_VIDEO;
            case "record_video_note":
                return RECORD_VIDEO_NOTE;
            case "record_audio":
            case "record_voice":
                return RECORD_VOICE;
            case "upload_photo":
                return UPLOAD_PHOTO;
            case "upload_video":
                return UPLOAD_VIDEO;
            case "upload_video_note":
                return UPLOAD_VIDEO_NOTE;
            case "upload_audio":
            case "upload_voice":
                return UPLOAD_VOICE;
            case "upload_document":
                return UPLOAD_DOCUMENT;
            case "find_location":
                return FIND_LOCATION;
            default:
                return null;
        }
    }
}
