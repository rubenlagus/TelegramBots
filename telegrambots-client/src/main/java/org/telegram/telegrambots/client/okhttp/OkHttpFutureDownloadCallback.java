package org.telegram.telegrambots.client.okhttp;

import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

class OkHttpFutureDownloadCallback extends AbstractOkHttpFutureCallback<InputStream> {

    @Override
    protected InputStream getResponseValue(ResponseBody body) throws IOException {
        return new ByteArrayInputStream(IOUtils.toByteArray(body.byteStream()));
    }
}
