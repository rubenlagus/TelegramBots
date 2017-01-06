import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingConversationBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * @author Roman_Rahozhyn
 *         12/26/16
 */
public class Main {
    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();



        TelegramLongPollingConversationBot bot = new Bot(null, null);
        bot.register(new DemoConversationCommand("/dialog", "Test demo dialog with bot."));

        telegramBotsApi.registerBot(bot);
    }
}
