package org.telegram.telegrambots.session;

import lombok.Setter;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.Optional;

public abstract class TelegramLongPollingSessionBot extends TelegramLongPollingBot {
    DefaultSessionManager sessionManager = new DefaultSessionManager();

    @Setter
    ChatIdConverter chatIdConverter = new DefaultChatIdConverter();

    public TelegramLongPollingSessionBot(){
        this.setChatIdConverter(new DefaultChatIdConverter());
    }

    public TelegramLongPollingSessionBot(ChatIdConverter chatIdConverter){
        this.setChatIdConverter(chatIdConverter);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<Session> chatSession;
        if (update.hasMessage()) {
            chatIdConverter.setSessionId(update.getMessage().getChatId());
            AbstractSessionDAO sessionDAO = (AbstractSessionDAO) sessionManager.getSessionDAO();
            sessionDAO.setSessionIdGenerator(chatIdConverter);
            try {
                chatSession = Optional.of(sessionManager.getSession(chatIdConverter));
            } catch (UnknownSessionException e) {
                SessionContext botSession = new DefaultChatSessionContext(update.getMessage().getChatId(), update.getMessage().getFrom().getUserName());
                chatSession = Optional.of(sessionManager.start(botSession));
            }
        } else {
            chatSession = Optional.empty();
        }
        onUpdateReceived(update, chatSession);
    }

    public abstract void onUpdateReceived(Update update, Optional<Session> botSession);
}
