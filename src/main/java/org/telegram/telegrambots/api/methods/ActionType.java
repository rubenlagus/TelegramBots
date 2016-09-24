package org.telegram.telegrambots.api.methods;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Types of actions for SendChatAction method.
 * @date 20 of June of 2016
 */
public enum ActionType {
    TYPING("typing"),
    RECORDVIDEO("record_video"),
    RECORDAUDIO("record_audio"),
    UPLOADPHOTO("upload_photo"),
    UPLOADVIDEO("upload_video"),
    UPLOADAUDIO("upload_audio"),
    UPLOADDOCUMENT("upload_document"),
    FINDLOCATION("find_location");

    private String text;

    ActionType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
