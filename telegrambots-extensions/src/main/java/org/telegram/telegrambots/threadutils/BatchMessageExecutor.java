package org.telegram.telegrambots.threadutils;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Executes a list of messages. A message is send every second to avoid the send limit of telegram. Using one of the setMethods method will override a previously set method list
 */
public class BatchMessageExecutor extends ListMethodExecutor<Message> {

    public void setMethodsByMessageList(List<String> messages, String chatID) {
        List<BotApiMethod<Message>> methods = new ArrayList<>();
        messages.forEach((message) -> methods.add(new SendMessage(chatID, message)));
        setMethods(methods);
    }

    public void setMethodsByChatList(List<String> chatIDs, String message) {
        List<BotApiMethod<Message>> methods = new ArrayList<>();
        chatIDs.forEach((id) -> methods.add(new SendMessage(id, message)));
        setMethods(methods);
    }

    public void setMethodsByHashMap(HashMap<String, String> messages) {
        List<BotApiMethod<Message>> methods = new ArrayList<>();
        messages.forEach((id, message) -> methods.add(new SendMessage(id, message)));
        setMethods(methods);
    }
}
