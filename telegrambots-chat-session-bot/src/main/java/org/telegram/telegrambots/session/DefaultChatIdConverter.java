package org.telegram.telegrambots.session;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.shiro.session.Session;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultChatIdConverter implements ChatIdConverter {
    private long sessionId;

    @Override
    public void setSessionId(Serializable sessionId){
        this.sessionId = (long) sessionId;
    };

    @Override
    public Serializable getSessionId() {
        return sessionId;
    }

    @Override
    public Serializable generateId(Session session) {
        return getSessionId();
    }
}
