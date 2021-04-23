import org.telegram.telegrambots.meta.*;
import org.telegram.telegrambots.meta.exceptions.*;
import org.telegram.telegrambots.updatesreceivers.*;

public class Main {
    public static void main(String[] args) {
        // Edit by your situation
        System.getProperties().put("proxySet","true");
        System.getProperties().put("socksProxyHost","127.0.0.1");
        System.getProperties().put("socksProxyPort","7891");
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new InlineModelBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}