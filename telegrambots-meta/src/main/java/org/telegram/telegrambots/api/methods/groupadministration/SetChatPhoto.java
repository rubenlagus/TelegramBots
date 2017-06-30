package org.telegram.telegrambots.api.methods.groupadministration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to set a new profile photo for the chat. Photos can't be changed for private chats.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 *
 * @apiNote In regular groups (non-supergroups), this method will only work if the ‘All Members Are Admins’ setting is off in the target group.
 */
public class SetChatPhoto extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "setChatPhoto";

    public static final String CHATID_FIELD = "chat_id";
    public static final String PHOTO_FIELD = "photo";

    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    private String photoName; ///< Name of new chat photo
    private InputStream photoStream; ///< New chat photo as InputStream, uploaded using multipart/form-data
    private File photo; ///< New chat photo as File, uploaded using multipart/form-data

    public SetChatPhoto() {
        super();
    }

    public SetChatPhoto(String chatId, File photo) {
        super();
        this.chatId = checkNotNull(chatId);
        this.photo = checkNotNull(photo);
    }

    public SetChatPhoto(String chatId, InputStream photoStream, String photoName) {
        super();
        this.chatId = checkNotNull(chatId);
        this.photoStream = checkNotNull(photoStream);
        this.photoName = checkNotNull(photoName);
    }


    public SetChatPhoto(Long chatId, File photo) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.photo = checkNotNull(photo);
    }

    public SetChatPhoto(Long chatId, InputStream photoStream, String photoName) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.photoStream = checkNotNull(photoStream);
        this.photoName = checkNotNull(photoName);
    }

    public String getChatId() {
        return chatId;
    }

    public SetChatPhoto setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SetChatPhoto setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public SetChatPhoto setPhoto(File file) {
        this.photo = file;
        return this;
    }

    public SetChatPhoto setNewPhoto(String photoName, InputStream inputStream) {
        Objects.requireNonNull(photoName, "photoName cannot be null!");
        Objects.requireNonNull(inputStream, "inputStream cannot be null!");
        this.photoName = photoName;
        this.photoStream = inputStream;
        return this;
    }

    public String getPhotoName() {
        return photoName;
    }

    public InputStream getPhotoStream() {
        return photoStream;
    }

    public File getPhoto() {
        return photo;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error setting chat photo", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (photo == null) {
            if (photoStream == null) {
                throw new TelegramApiValidationException("Photo parameter is required", this);
            } else if (photoName == null || photoName.isEmpty()){
                throw new TelegramApiValidationException("Photo name can't be empty", this);
            }
        }
    }

    @Override
    public String toString() {
        return "SetChatPhoto{" +
                "chatId='" + chatId + '\'' +
                ", photoName='" + photoName + '\'' +
                ", photoStream=" + photoStream +
                ", photo=" + photo +
                '}';
    }
}
