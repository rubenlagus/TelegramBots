package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * This object represent a user's profile pictures.
 * @author Ruben Bermudez
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilePhotos implements BotApiObject {

    private static final String TOTALCOUNT_FIELD = "total_count";
    private static final String PHOTOS_FIELD = "photos";

    /**
     * Total number of profile pictures the target user has
     */
    @JsonProperty(TOTALCOUNT_FIELD)
    private Integer totalCount;
    /**
     * Requested profile pictures (in up to 4 sizes each)
     */
    @JsonProperty(PHOTOS_FIELD)
    private List<List<PhotoSize>> photos;
}
