package org.telegram.telegrambots.meta.api.methods;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Types of actions for SendChatAction method.
 */
public enum ActionType {
    TYPING("typing"),
    RECORDVIDEO("record_video"),
    RECORDVIDEONOTE("record_video_note"),
    RECORDVOICE("record_voice"),
    UPLOADPHOTO("upload_photo"),
    UPLOADVIDEO("upload_video"),
    UPLOADVIDEONOTE("upload_video_note"),
    UPLOADVOICE("upload_voice"),
    UPLOADDOCUMENT("upload_document"),
    FINDLOCATION("find_location");

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
                return RECORDVIDEO;
            case "record_video_note":
                return RECORDVIDEONOTE;
            case "record_audio":
            case "record_voice":
                return RECORDVOICE;
            case "upload_photo":
                return UPLOADPHOTO;
            case "upload_video":
                return UPLOADVIDEO;
            case "upload_video_note":
                return UPLOADVIDEONOTE;
            case "upload_audio":
            case "upload_voice":
                return UPLOADVOICE;
            case "upload_document":
                return UPLOADDOCUMENT;
            case "find_location":
                return FINDLOCATION;
            default:
                return null;
        }
    }
}
