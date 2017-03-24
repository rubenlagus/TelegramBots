import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * Created by smaznet on 3/21/17.
 */
public class Main {

    public static void main(String[] args){
        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi=new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MyTestBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
