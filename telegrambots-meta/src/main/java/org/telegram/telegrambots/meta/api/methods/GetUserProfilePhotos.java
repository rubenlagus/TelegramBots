package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class GetUserProfilePhotos extends BotApiMethod<UserProfilePhotos> {
    public static final String PATH = "getuserprofilephotos";

    private static final String USERID_FIELD = "user_id";
    private static final String OFFSET_FIELD = "offset";
    private static final String LIMIT_FIELD = "limit";

    @JsonProperty(USERID_FIELD)
    @NonNull
    private Long userId; ///< Unique identifier of the target user
    /**
     * Optional. Sequential number of the first photo to be returned. By default, all photos are returned.
     */
    @JsonProperty(OFFSET_FIELD)
    private Integer offset;
    /**
     * Optional. Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
     */
    @JsonProperty(LIMIT_FIELD)
    private Integer limit;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public UserProfilePhotos deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, UserProfilePhotos.class);
    }
}
