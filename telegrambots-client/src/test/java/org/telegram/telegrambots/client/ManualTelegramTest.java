package org.telegram.telegrambots.client;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

// This testsuite is for manually testing the api using a real bot token. It's not executed automatically
public class ManualTelegramTest {
    // Add token as env variable BOT_TOKEN
    private final String TOKEN = System.getenv("BOT_TOKEN");

    private final OkHttpTelegramClient client = new OkHttpTelegramClient(TOKEN);

    public void run() {
        try {
            Message message = client.execute(SendDocument.builder()
                            .chatId("188215327")
                            .caption("Hello *World*")
                            .parseMode(ParseMode.MARKDOWNV2)
                            .document(new InputFile(new File("/Users/Lukas.Prediger/Downloads/IMG_0938.JPG")))
                    .build());

            System.out.println(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        new ManualTelegramTest().run();
    }
}
