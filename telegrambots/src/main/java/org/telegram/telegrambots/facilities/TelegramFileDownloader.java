package org.telegram.telegrambots.facilities;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.DownloadFileCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;

@SuppressWarnings("Duplicates")
public class TelegramFileDownloader {
    private DefaultAbsSender sender;
    ExecutorService exe;
    CloseableHttpClient httpClient = HttpClients.createDefault();

    public final java.io.File downloadFile(String filePath) throws TelegramApiException {
        if (filePath == null || filePath.isEmpty()) {
            throw new TelegramApiException("Parameter file can not be null");
        }
        String url = File.getFileUrl(sender.getBotToken(), filePath);
        String tempFileName = Long.toString(System.currentTimeMillis());
        return downloadToTemporaryFileWrappingExceptions(url, tempFileName);
    }

    public final java.io.File downloadFile(File file) throws TelegramApiException {
        assertParamNotNull(file, "file");
        String url = file.getFileUrl(sender.getBotToken());
        String tempFileName = file.getFileId();
        return downloadToTemporaryFileWrappingExceptions(url, tempFileName);
    }

    public final void downloadFileAsync(String filePath, DownloadFileCallback<String> callback) throws TelegramApiException {
        if (filePath == null || filePath.isEmpty()) {
            throw new TelegramApiException("Parameter filePath can not be null");
        }
        assertParamNotNull(callback, "callback");
        String url = File.getFileUrl(sender.getBotToken(), filePath);
        String tempFileName = Long.toString(System.currentTimeMillis());
        exe.submit(getDownloadFileAsyncJob(filePath, callback, url, tempFileName));
    }

    public final void downloadFileAsync(File file, DownloadFileCallback<File> callback) throws TelegramApiException {
        assertParamNotNull(file, "file");
        assertParamNotNull(callback, "callback");
        String url = file.getFileUrl(sender.getBotToken());
        String tempFileName = file.getFileId();
        exe.submit(getDownloadFileAsyncJob(file, callback, url, tempFileName));
    }

    private <T> Runnable getDownloadFileAsyncJob(T fileIdentifier, DownloadFileCallback<T> callback, String url, String tempFileName) {
        //noinspection Convert2Lambda
        return new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onResult(fileIdentifier, downloadToTemporaryFile(url, tempFileName));
                } catch (MalformedURLException e) {
                    callback.onException(fileIdentifier, new TelegramApiException("Wrong url for file: " + url));
                } catch (IOException e) {
                    callback.onException(fileIdentifier, new TelegramApiRequestException("Error downloading the file", e));
                }
            }
        };
    }

    private java.io.File downloadToTemporaryFileWrappingExceptions(String url, String tempFileName) throws TelegramApiException {
        try {
            return downloadToTemporaryFile(url, tempFileName);
        } catch (MalformedURLException e) {
            throw new TelegramApiException("Wrong url for file: " + url);
        } catch (IOException e) {
            throw new TelegramApiRequestException("Error downloading the file", e);
        }
    }

    private java.io.File downloadToTemporaryFile(String url, String tempFileName) throws IOException {
        final java.io.File output = java.io.File.createTempFile(tempFileName, ".tmp");

        final HttpGet get = new HttpGet(url);
        final HttpResponse response = httpClient.execute(get);

        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            final InputStream content = response.getEntity().getContent();

            FileUtils.copyInputStreamToFile(content, output);
        } else {
            throw new IOException("Unexpected Status code. Expected 200 got + statusCode");
        }
        return output;
    }

    private void assertParamNotNull(Object param, String paramName) throws TelegramApiException {
        if (param == null) {
            throw new TelegramApiException("Parameter " + paramName + " can not be null");
        }
    }
}
