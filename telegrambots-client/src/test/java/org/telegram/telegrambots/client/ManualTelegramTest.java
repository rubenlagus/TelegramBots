package org.telegram.telegrambots.client;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

// This testsuite is for manually testing the api using a real bot token. It's not executed automatically
public class ManualTelegramTest {
    // Add token as env variable BOT_TOKEN
    private final String TOKEN = System.getenv("BOT_TOKEN");

    private final OkHttpTelegramClient client = new OkHttpTelegramClient(TOKEN);

    public void run() {

    }
    public static void main(String[] args) {
        new ManualTelegramTest().run();
    }
}
