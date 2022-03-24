package org.telegram.telegrambots.meta.api.methods;

public class CopyMessageOptions {
    private String parseMode = null;

    public String enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWN;
        }
        return this.parseMode;
    }

    public String enableHtml(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.HTML;
        }
        return this.parseMode;

    }

    public String enableMarkdownV2(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWNV2;
        }
        return this.parseMode;
    }
}
