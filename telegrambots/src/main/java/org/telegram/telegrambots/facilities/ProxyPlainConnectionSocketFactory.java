package org.telegram.telegrambots.facilities;

import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import java.net.Proxy;
import java.net.Socket;
import java.util.Objects;

/**
 * {@link PlainConnectionSocketFactory} implementation that passes a supplied {@link Proxy} instance to all created sockets.
 * This implementation allows a telegram bot to use a socks5 proxy without forcing all JVM connections to use the same proxy.
 *
 * @author Vitaly Ogoltsov &lt;vitaly.ogoltsov@me.com&gt;
 */
@SuppressWarnings("WeakerAccess")
public class ProxyPlainConnectionSocketFactory extends PlainConnectionSocketFactory {

    private final Proxy proxy;

    public ProxyPlainConnectionSocketFactory(Proxy proxy) {
        this.proxy = Objects.requireNonNull(proxy);
    }

    @Override
    public Socket createSocket(HttpContext context) {
        return new Socket(proxy);
    }

}
