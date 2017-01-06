import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingConversationBot;

/**
 * @author Roman_Rahozhyn
 *         12/26/16
 */
public class Bot extends TelegramLongPollingConversationBot {

    private final String token;
    private final String username;

    public Bot(String token, String username) {
        super();
        this.token = token;
        this.username = username;
    }

    public void processNonCommandUpdate(Update update) {
    }

    public String getBotUsername() {
        return username;
    }

    public String getBotToken() {
        return token;
    }
}
