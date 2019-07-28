package org.telegram.telegrambots.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.DownloadFileCallback;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.IOUtils.toInputStream;
import static org.apache.http.HttpVersion.HTTP_1_1;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TelegramFileDownloaderTest {

    private TelegramFileDownloader telegramFileDownloader;

    @Mock
    private DownloadFileCallback<String> downloadFileCallbackMock;

    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpResponse httpResponseMock;

    @Mock
    private HttpEntity httpEntityMock;

    private Supplier<String> tokenSupplierMock = () -> "someToken";


    @Before
    public void setup() throws IOException {


        when(httpResponseMock.getStatusLine()).thenReturn(new BasicStatusLine(HTTP_1_1, 200, "emptyString"));
        when(httpResponseMock.getEntity()).thenReturn(httpEntityMock);

        when(httpEntityMock.getContent()).thenReturn(toInputStream("Some File Content", defaultCharset()));

        when(httpClientMock.execute(any(HttpUriRequest.class))).thenReturn(httpResponseMock);

        telegramFileDownloader = new TelegramFileDownloader(httpClientMock, tokenSupplierMock);
    }

    @Test
    public void testFileDownload() throws TelegramApiException, IOException {
        File returnFile = telegramFileDownloader.downloadFile("someFilePath");
        String content = readFileToString(returnFile, defaultCharset());

        assertEquals("Some File Content", content);
    }

    @Test(expected = TelegramApiException.class)
    public void testDownloadException() throws TelegramApiException {
        when(httpResponseMock.getStatusLine()).thenReturn(new BasicStatusLine(HTTP_1_1, 500, "emptyString"));

        telegramFileDownloader.downloadFile("someFilePath");
    }

    @Test
    public void testAsyncDownload() throws TelegramApiException, IOException {
        final ArgumentCaptor<File> fileArgumentCaptor = ArgumentCaptor.forClass(File.class);

        telegramFileDownloader.downloadFileAsync("someFilePath", downloadFileCallbackMock);

        verify(downloadFileCallbackMock, timeout(100)
                .times(1))
                .onResult(any(), fileArgumentCaptor.capture());

        String content = readFileToString(fileArgumentCaptor.getValue(), defaultCharset());
        assertEquals("Some File Content", content);
    }

    @Test
    public void testAsyncException() throws TelegramApiException {
        final ArgumentCaptor<Exception> exceptionArgumentCaptor = ArgumentCaptor.forClass(Exception.class);

        when(httpResponseMock.getStatusLine()).thenReturn(new BasicStatusLine(HTTP_1_1, 500, "emptyString"));

        telegramFileDownloader.downloadFileAsync("someFilePath", downloadFileCallbackMock);

        verify(downloadFileCallbackMock, timeout(100)
                .times(1))
                .onException(any(), exceptionArgumentCaptor.capture());

        Exception e = exceptionArgumentCaptor.getValue();
        assertThat(e, instanceOf(TelegramApiException.class));
        assertEquals(e.getCause().getCause().getMessage(), "Unexpected Status code while downloading file. Expected 200 got 500");
    }

}
