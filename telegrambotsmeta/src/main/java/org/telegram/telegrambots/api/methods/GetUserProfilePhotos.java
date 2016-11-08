package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
 * @date 20 of June of 2015
 */
public class GetUserProfilePhotos extends BotApiMethod<UserProfilePhotos> {
    public static final String PATH = "getuserprofilephotos";

    private static final String USERID_FIELD = "user_id";
    private static final String OFFSET_FIELD = "offset";
    private static final String LIMIT_FIELD = "limit";

    @JsonProperty(USERID_FIELD)
    private Integer userId; ///< Unique identifier of the target user
    /**
     * Sequential number of the first photo to be returned. By default, all photos are returned.
     */
    @JsonProperty(OFFSET_FIELD)
    private Integer offset;
    /**
     * Optional. Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
     */
    @JsonProperty(LIMIT_FIELD)
    private Integer limit;

    public GetUserProfilePhotos() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public GetUserProfilePhotos setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public GetUserProfilePhotos setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public GetUserProfilePhotos setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public UserProfilePhotos deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<UserProfilePhotos> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<UserProfilePhotos>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting user profile photos", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null) {
            throw new TelegramApiValidationException("UserId parameter can't be empty", this);
        }
        if (offset == null) {
            throw new TelegramApiValidationException("Offset parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "GetUserProfilePhotos{" +
                "userId=" + userId +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
