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

package org.telegram.telegrambots.updateshandlers;

import org.telegram.telegrambots.api.objects.File;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Callback to execute api method asynchronously
 * @date 10 of September of 2015
 */
public interface DownloadFileCallback {
    /**
     * Called when the request is successful
     * @param method Method executed
     * @param jsonObject Answer from Telegram server
     */
    void onResult(File file, java.io.File output);

    /**
     * Called when the http request throw an exception
     * @param method Method executed
     * @param exception Excepction thrown
     */
    void onException(File file, Exception exception);
}
