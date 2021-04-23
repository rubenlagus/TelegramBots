import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.ArrayList;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;


public class InlineModelBot extends AbilityBot {

    public static String BOT_TOKEN = "1714168652:AAFtrzghoEGCmw31JOh6ev_2KpLu_eEOBV0";
    public static String BOT_USERNAME = "InlineModelBot";

    public InlineModelBot(){
        super(BOT_TOKEN,BOT_USERNAME);
    }

    public void sendInlineKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Inline model below.");

        // Create InlineKeyboardMarkup object
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        // Create the keyboard (list of InlineKeyboardButton list)
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Create a list for buttons
        List<InlineKeyboardButton> Buttons = new ArrayList<InlineKeyboardButton>();
        // Initialize each button, the text must be written
        InlineKeyboardButton youtube= new InlineKeyboardButton("youtube");
        // Also must use exactly one of the optional fields,it can edit  by set method
        youtube.setUrl("https://www.youtube.com");
        // Add button to the list
        Buttons.add(youtube);
        // Initialize each button, the text must be written
        InlineKeyboardButton github= new InlineKeyboardButton("github");
        // Also must use exactly one of the optional fields,it can edit  by set method
        github.setUrl("https://github.com");
        // Add button to the list
        Buttons.add(github);
        keyboard.add(Buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            // Send the message
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long creatorId(){
        return 123456;
    }

    public Ability getInlineModelBot() {
        return Ability
                .builder()
                .name("inline")
                .info("get the inline model.")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> sendInlineKeyboard(String.valueOf(ctx.chatId())))
                .post(ctx -> silent.send("Please choose the website.", ctx.chatId()))
                .build();
    }
}