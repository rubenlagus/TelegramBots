package org.telegram.telegrambots.facilities.filedownloader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.DownloadFileCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * Wraps the file downloading code into one class.
 * @author Chase22
 * @version 1.0
 */
public class TelegramFileDownloader {
    private final HttpClient httpClient;
    //TODO Replace with concrete token once deprecations are removed
    private final Supplier<String> botTokenSupplier;

    public TelegramFileDownloader(final Supplier<String> botTokenSupplier) {
        this.botTokenSupplier = botTokenSupplier;
        httpClient = HttpClients.createDefault();
    }

    public TelegramFileDownloader(final HttpClient httpClient, final Supplier<String> botTokenSupplier) {
        this.httpClient = httpClient;
        this.botTokenSupplier = botTokenSupplier;
    }

    public final java.io.File downloadFile(String filePath) throws TelegramApiException {
        String tempFileName = Long.toString(System.currentTimeMillis());
        return downloadFile(filePath, getTempFile(tempFileName));
    }

    public final java.io.File downloadFile(File file) throws TelegramApiException {
        return downloadFile(file, getTempFile(file.getFileId()));
    }

    public final java.io.File downloadFile(File file, java.io.File outputFile) throws TelegramApiException {
        if (file == null) {
            throw new TelegramApiException("Parameter file can not be null");
        }
        String url = file.getFileUrl(botTokenSupplier.get());
        return downloadToFile(url, outputFile);
    }

    public final java.io.File downloadFile(String filePath, java.io.File outputFile) throws TelegramApiException {
        if (filePath == null || filePath.isEmpty()) {
            throw new TelegramApiException("Parameter file can not be null or empty");
        }
        String url = File.getFileUrl(botTokenSupplier.get(), filePath);
        return downloadToFile(url, outputFile);
    }

    public final InputStream downloadFileAsStream(String filePath) throws TelegramApiException {
        try {
            return getFileDownloadStreamFuture(File.getFileUrl(botTokenSupplier.get(), filePath)).get();
        } catch (InterruptedException e) {
            throw new TelegramApiException("Error downloading file", e);
        } catch (ExecutionException e) {
            throw new TelegramApiException("Error downloading file", e.getCause());
        }
    }

    public final InputStream downloadFileAsStream(File file) throws TelegramApiException {
        try {
            return getFileDownloadStreamFuture(file.getFileUrl(botTokenSupplier.get())).get();
        } catch (InterruptedException e) {
            throw new TelegramApiException("Error downloading file", e);
        } catch (ExecutionException e) {
            throw new TelegramApiException("Error downloading file", e.getCause());
        }
    }

    public final void downloadFileAsync(String filePath, DownloadFileCallback<String> callback) throws TelegramApiException {
        if (filePath == null || filePath.isEmpty()) {
            throw new TelegramApiException("Parameter filePath can not be null or empty");
        }
        if (callback == null) {
            throw new TelegramApiException("Parameter callback can not be null");
        }
        String url = File.getFileUrl(botTokenSupplier.get(), filePath);
        String tempFileName = Long.toString(System.currentTimeMillis());

        getFileDownloadFuture(url, getTempFile(tempFileName))
                .thenAccept(output -> callback.onResult(filePath, output))
                .exceptionally(throwable -> {
                    // Unwrap java.util.concurrent.CompletionException
                    if (throwable instanceof CompletionException) {
                        callback.onException(filePath, new TelegramApiException("Error downloading file", throwable.getCause()));
                    } else {
                        callback.onException(filePath, new TelegramApiException("Error downloading file", throwable));
                    }
                    return null;
                });
    }

    public final void downloadFileAsync(File file, DownloadFileCallback<File> callback) throws TelegramApiException {
        if (file == null) {
            throw new TelegramApiException("Parameter file can not be null");
        }
        if (callback == null) {
            throw new TelegramApiException("Parameter callback can not be null");
        }
        String url = file.getFileUrl(botTokenSupplier.get());
        String tempFileName = file.getFileId();

        getFileDownloadFuture(url, getTempFile(tempFileName))
                .thenAccept(output -> callback.onResult(file, output))
                .exceptionally(throwable -> {
                    callback.onException(file, new TelegramApiException("Error downloading file", throwable));
                    return null;
                });

    }

    private java.io.File getTempFile(String tempFileName) throws TelegramApiException {
        try {
            return java.io.File.createTempFile(tempFileName, ".tmp");
        } catch (IOException e) {
            throw new TelegramApiException("Error downloading file", e);
        }
    }

    private java.io.File downloadToFile(String url, java.io.File output) throws TelegramApiException {
        try {
            return getFileDownloadFuture(url, output).get();
        } catch (InterruptedException e) {
            throw new TelegramApiException("File Download got interrupted", e);
        } catch (ExecutionException e) {
            throw new TelegramApiException("Error downloading file", e.getCause());
        }
    }

    private CompletableFuture<java.io.File> getFileDownloadFuture(String url, java.io.File output) {
        return getFileDownloadStreamFuture(url).thenApply(stream -> {
            try {
                copyInputStreamToFile(stream, output);
                return output;
            } catch (IOException e) {
                throw new DownloadFileException("Error writing downloaded file", e);
            }
        });
    }

    private CompletableFuture<InputStream> getFileDownloadStreamFuture(final String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse response = httpClient.execute(new HttpGet(url));
                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == SC_OK) {
                    return response.getEntity().getContent();
                } else {
                    throw new TelegramApiException("Unexpected Status code while downloading file. Expected 200 got " + statusCode);
                }
            } catch (IOException | TelegramApiException e) {
                throw new DownloadFileException("Error downloading file", e);
            }
        });
    }

}
