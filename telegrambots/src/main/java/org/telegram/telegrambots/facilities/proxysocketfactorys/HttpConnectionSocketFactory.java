package org.telegram.telegrambots.facilities.proxysocketfactorys;

import org.apache.http.HttpHost;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class HttpConnectionSocketFactory extends PlainConnectionSocketFactory {
    @Override
    public Socket createSocket(final HttpContext context) throws IOException {
        InetSocketAddress socketAddress = (InetSocketAddress) context.getAttribute("socketAddress");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        return new Socket(proxy);
    }

    @Override
    public Socket connectSocket(
            int connectTimeout,
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
