package org.telegram.telegrambots.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@SuppressWarnings({"WeakerAccess", "OptionalUsedAsFieldOrParameterType", "unused"})
public abstract class TelegramLongPollingSessionBot extends TelegramLongPollingBot {
    DefaultSessionManager sessionManager;

    ChatIdConverter chatIdConverter;
    /**
     * If this is used getBotToken has to be overridden in order to return the bot token!
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public TelegramLongPollingSessionBot(){
        this(new DefaultChatIdConverter());
    }

    /**
     * If this is used getBotToken has to be overridden in order to return the bot token!
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public TelegramLongPollingSessionBot(ChatIdConverter chatIdConverter){
        this(chatIdConverter, new DefaultBotOptions());
    }

    /**
     * If this is used getBotToken has to be overridden in order to return the bot token!
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public TelegramLongPollingSessionBot(ChatIdConverter chatIdConverter, DefaultBotOptions defaultBotOptions){
        this(chatIdConverter, defaultBotOptions, null);
    }

    public TelegramLongPollingSessionBot(String botToken){
        this(new DefaultChatIdConverter(), botToken);
    }

    public TelegramLongPollingSessionBot(ChatIdConverter chatIdConverter, String botToken){
        this(chatIdConverter, new DefaultBotOptions(), botToken);
    }

    public TelegramLongPollingSessionBot(ChatIdConverter chatIdConverter, DefaultBotOptions defaultBotOptions, String botToken){
        super(defaultBotOptions, botToken);
        this.setSessionManager(new DefaultSessionManager());
        this.setChatIdConverter(chatIdConverter);
        AbstractSessionDAO sessionDAO = (AbstractSessionDAO) sessionManager.getSessionDAO();
        sessionDAO.setSessionIdGenerator(chatIdConverter);
    }


    public void setSessionManager(DefaultSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setChatIdConverter(ChatIdConverter chatIdConverter) {
        this.chatIdConverter = chatIdConverter;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<Session> chatSession;
        Message message;
        if (update.hasMessage()) {
            message = update.getMessage();
        } else if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getMessage() instanceof Message) {
                message = (Message) update.getCallbackQuery().getMessage();
            } else {
                return;
            }
        } else {
            chatSession = Optional.empty();
            onUpdateReceived(update, chatSession);
            return;
        }
        chatIdConverter.setSessionId(message.getChatId());
        chatSession = this.getSession(message);
        onUpdateReceived(update, chatSession);
    }

    public Optional<Session> getSession(Message message){
        try {
            return Optional.of(sessionManager.getSession(chatIdConverter));
        } catch (UnknownSessionException e) {
            SessionContext botSession = new DefaultChatSessionContext(message.getChatId(), message.getFrom().getUserName());
            return Optional.of(sessionManager.start(botSession));
        }
    }

    public abstract void onUpdateReceived(Update update, Optional<Session> botSession);
}
