package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represent a user's profile pictures.
 * @date 22 of June of 2015
 */
public class UserProfilePhotos implements BotApiObject {

    private static final String TOTALCOUNT_FIELD = "total_count";
    private static final String PHOTOS_FIELD = "photos";


    private Integer total_count; ///< Total number of profile pictures the target user has

    private List<List<PhotoSize>> photos; ///< Requested profile pictures (in up to 4 sizes each)

    public UserProfilePhotos() {
        super();
    }

    public Integer getTotalCount() {
        return total_count;
    }

    public List<List<PhotoSize>> getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        return "UserProfilePhotos{" +
                "totalCount=" + total_count +
                ", photos=" + photos +
                '}';
    }
}
