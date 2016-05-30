package org.telegram.telegrambots.updatesreceivers;

import com.sun.jersey.api.json.JSONConfiguration;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.bots.ITelegramWebhookBot;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Webhook to receive updates
 * @date 20 of June of 2015
 */
public class Webhook {
    private final String KEYSTORE_SERVER_FILE;
    private final String KEYSTORE_SERVER_PWD;

    private final RestApi restApi;
    private final String internalUrl;

    public Webhook(String keyStore, String keyStorePassword, String internalUrl) throws TelegramApiException {
        this.KEYSTORE_SERVER_FILE = keyStore;
        this.KEYSTORE_SERVER_PWD = keyStorePassword;
        validateServerKeystoreFile(keyStore);
        this.internalUrl = internalUrl;
        this.restApi = new RestApi();
    }

    public void registerWebhook(ITelegramWebhookBot callback) {
        restApi.registerCallback(callback);
    }

    public void startServer() throws TelegramApiException {
        SSLContextConfigurator sslContext = new SSLContextConfigurator();

        // set up security context
        sslContext.setKeyStoreFile(KEYSTORE_SERVER_FILE); // contains server keypair
        sslContext.setKeyStorePass(KEYSTORE_SERVER_PWD);

        ResourceConfig rc = new ResourceConfig();
        rc.register(restApi);
        rc.register(JacksonFeature.class);
        rc.property(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        final HttpServer grizzlyServer = GrizzlyHttpServerFactory.createHttpServer(
                getBaseURI(),
                rc,
                true,
                new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(false));
        try {
            grizzlyServer.start();
        } catch (IOException e) {
            throw new TelegramApiException("Error starting webhook server", e);
        }
    }

    private URI getBaseURI() {
        return URI.create(internalUrl);
    }

    private static void validateServerKeystoreFile(String keyStore) throws TelegramApiException {
        File file = new File(keyStore);
        if (!file.exists() || !file.canRead()) {
            throw new TelegramApiException("Can't find or access server keystore file.");
        }
    }
}
