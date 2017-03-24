/*
 * This file is part of TelegramBots.
 *
 * TelegramBots is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TelegramBots is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TelegramBots.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.telegram.telegrambots.api.objects.games;



import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.PhotoSize;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief This object represents an animation file.
 * @date 27 of September of 2016
 */
public class Animation implements BotApiObject {
    private static final String FILEID_FIELD = "file_id";
    private static final String THUMB_FIELD = "thumb";
    private static final String FILENAME_FIELD = "file_name";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";


    private String file_id; ///< Unique file identifier

    private PhotoSize thumb; ///< Optional. Animation thumbnail as defined by sender

    private String file_name; ///< Optional. Original animation filename as defined by sender

    private String mimetype; ///< Optional. MIME type of the file as defined by sender

    private Integer fileSize; ///< Optional. File size

    public Animation() {
        super();
    }

    public String getFileId() {
        return file_id;
    }

    public PhotoSize getThumb() {
        return thumb;
    }

    public String getFileName() {
        return file_name;
    }

    public String getMimetype() {
        return mimetype;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return "Animation{" +
                "file_id='" + file_id + '\'' +
                ", thumb=" + thumb +
                ", fileName='" + file_id + '\'' +
                ", mimetype='" + mimetype + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
