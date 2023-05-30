package org.telegram.telegrambots.session;

import org.apache.shiro.session.Session;

import java.io.Serializable;

@SuppressWarnings({"unused", "WeakerAccess"})
public class DefaultChatIdConverter implements ChatIdConverter {
    private long sessionId;

    public DefaultChatIdConverter() {
        super();
    }

    public DefaultChatIdConverter(long sessionId) {
        this();
        this.sessionId = sessionId;
    }

    @Override
    public void setSessionId(Serializable sessionId){
        this.sessionId = (long) sessionId;
    }

    @Override
    public Serializable getSessionId() {
        return sessionId;
    }

    @Override
    public Serializable generateId(Session session) {
        return getSessionId();
    }
}
