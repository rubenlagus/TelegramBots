import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.LongPollingBot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smaznet on 3/21/17.
 */
public class MyTestBot  extends TelegramLongPollingBot{
    public void onUpdateReceived(Update update) {
if (update.hasMessage()){
    try {
        List<List<InlineKeyboardButton>> btns=new ArrayList<List<InlineKeyboardButton>>();
        List<InlineKeyboardButton> btn=new ArrayList<InlineKeyboardButton>();
        btn.add(new InlineKeyboardButton().setText("Test")
                .setCallbackData("test"));
        btns.add(btn);
        sendMessage(new SendMessage().setChatId(update.getMessage().getChatId()).setText("Done")
                .setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(btns)));
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}
if (update.hasCallbackQuery()){
    try {
        answerCallbackQuery(new AnswerCallbackQuery().setCallbackQueryId(update.getCallbackQuery().getId()).setText("OK"));
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}
    }

    public String getBotUsername() {
        return "......";
    }

    public String getBotToken() {
        return "........";
    }
}
