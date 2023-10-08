package org.telegram.telegrambots.facilities.proxysocketfactorys;

import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class HttpSSLConnectionSocketFactory extends SSLConnectionSocketFactory {

    public HttpSSLConnectionSocketFactory(final SSLContext sslContext) {
        super(sslContext, new NoopHostnameVerifier());
    }

    @Override
    public Socket createSocket(final HttpContext context) {
        InetSocketAddress socketAddress = (InetSocketAddress) context.getAttribute("socketAddress");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        return new Socket(proxy);
    }

    @Override
    public Socket connectSocket(
            TimeValue connectTimeout,
            Socket socket,
            HttpHost host,
            InetSocketAddress remoteAddress,
            InetSocketAddress localAddress,
            HttpContext context) throws IOException {
        String hostName = host.getHostName();
        int port = remoteAddress.getPort();
        InetSocketAddress unresolvedRemote = InetSocketAddress.createUnresolved(hostName, port);
        return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
    }
}
