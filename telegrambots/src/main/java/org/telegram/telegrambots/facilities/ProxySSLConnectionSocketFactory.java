package org.telegram.telegrambots.facilities;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.Objects;

/**
 * {@link SSLConnectionSocketFactory} implementation that passes a supplied {@link Proxy} instance to all created sockets.
 * This implementation allows a telegram bot to use a socks5 proxy without forcing all JVM connections to use the same proxy.
 *
 * @author Vitaly Ogoltsov &lt;vitaly.ogoltsov@me.com&gt;
 */
@SuppressWarnings("WeakerAccess")
public class ProxySSLConnectionSocketFactory extends SSLConnectionSocketFactory {

    private final Proxy proxy;

    public ProxySSLConnectionSocketFactory(SSLContext sslContext, Proxy proxy) {
        super(sslContext);
        this.proxy = Objects.requireNonNull(proxy);
    }

    @Override
    public Socket createSocket(final HttpContext context) {
        return new Socket(proxy);
    }

    @Override
    public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                InetSocketAddress localAddress, HttpContext context) throws IOException {
        // Convert address to unresolved
        InetSocketAddress unresolvedRemote = InetSocketAddress
                .createUnresolved(host.getHostName(), remoteAddress.getPort());
        return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
    }

}
