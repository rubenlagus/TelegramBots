package org.telegram.telegrambots.facilities.filedownloader;

/**
 * Runtime Exception to wrap Exceptions thrown during file download
 */
@SuppressWarnings("WeakerAccess")
public class DownloadFileException extends RuntimeException {
    public DownloadFileException(final String message, final Throwable e) {
        super(message, e);
    }
}
