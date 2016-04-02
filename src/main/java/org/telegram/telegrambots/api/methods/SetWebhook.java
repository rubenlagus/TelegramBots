package org.telegram.telegrambots.api.methods;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to specify a url and receive incoming updates via an outgoing webhook.
 * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url,
 * containing a JSON-serialized Update. In case of an unsuccessful request,
 * we will give up after a reasonable amount of attempts.
 * @date 20 of June of 2015
 */
public class SetWebhook {
    public static final String PATH = "setwebhook";

    public static final String URL_FIELD = "url";
    private String url; ///< Optional. HTTPS url to send updates to. Use an empty string to remove webhook integration
    public static final String CERTIFICATE_FIELD = "certificate";
    private String certificateFile; ///< Optional. Upload your public key certificate so that the root certificate in use can be checked

    public SetWebhook() {
        this.url = "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCertificateFile() {
        return certificateFile;
    }

    public void setCertificateFile(String certificateFile) {
        this.certificateFile = certificateFile;
    }

    @Override
    public String toString() {
        return "SetWebhook{" +
                "url='" + url + '\'' +
                ", certificateFile='" + certificateFile + '\'' +
                '}';
    }
}
