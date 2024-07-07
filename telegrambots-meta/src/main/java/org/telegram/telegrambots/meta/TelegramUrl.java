package org.telegram.telegrambots.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TelegramUrl {
    private String schema;
    private String host;
    private int port;
    private boolean testServer;

    public static final TelegramUrl DEFAULT_URL = new TelegramUrl("https", "api.telegram.org", 443, false);
}
