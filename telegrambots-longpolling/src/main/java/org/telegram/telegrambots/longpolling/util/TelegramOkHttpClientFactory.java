package org.telegram.telegrambots.longpolling.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import okhttp3.Authenticator;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramOkHttpClientFactory {
    @NoArgsConstructor
    public static class DefaultOkHttpClientCreator implements Supplier<OkHttpClient> {
        @Override
        public OkHttpClient get() {
            return getBaseClient().build();
        }

        @NonNull
        protected static OkHttpClient.Builder getBaseClient() {
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(100); // Max requests
            dispatcher.setMaxRequestsPerHost(100); // Max per host

            return new OkHttpClient()
                    .newBuilder()
                    .dispatcher(dispatcher)
                    .connectionPool(new ConnectionPool(
                            100,
                            75,
                            TimeUnit.SECONDS
                    ))
                    .readTimeout(100, TimeUnit.SECONDS) // Time to read from server
                    .writeTimeout(70, TimeUnit.SECONDS) // Time to write to server
                    .connectTimeout(75, TimeUnit.SECONDS); // Max Connect timeout
        }
    }

    @RequiredArgsConstructor
    public static class HttpProxyOkHttpClientCreator extends DefaultOkHttpClientCreator {
        private final Supplier<Proxy> proxySupplier;
        private final Supplier<Authenticator> authenticatorSupplier;

        @Override
        public OkHttpClient get() {
            OkHttpClient.Builder okHttpClientBuilder = getBaseClient();

            // Proxy
            ofNullable(proxySupplier.get()).ifPresent(okHttpClientBuilder::proxy);
            ofNullable(authenticatorSupplier.get()).ifPresent(okHttpClientBuilder::proxyAuthenticator);

            return okHttpClientBuilder.build();
        }
    }

    @RequiredArgsConstructor
    public static class SocksProxyOkHttpClientCreator extends DefaultOkHttpClientCreator {
        private final Supplier<Proxy> proxySupplier;

        @Override
        public OkHttpClient get() {
            OkHttpClient.Builder okHttpClientBuilder = getBaseClient();

            // Proxy
            ofNullable(proxySupplier.get()).ifPresent(okHttpClientBuilder::proxy);

            return okHttpClientBuilder.build();
        }
    }

    /**
     * @deprecated Use {@link HttpProxyOkHttpClientCreator} instead
     */
    @Deprecated
    public static class ProxyOkHttpClientCreator extends HttpProxyOkHttpClientCreator {
        public ProxyOkHttpClientCreator(Supplier<Proxy> proxySupplier, Supplier<Authenticator> authenticatorSupplier) {
            super(proxySupplier, authenticatorSupplier);
        }
    }
}
