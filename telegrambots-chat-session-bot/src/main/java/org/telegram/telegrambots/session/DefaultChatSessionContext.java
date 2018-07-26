package org.telegram.telegrambots.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.session.mgt.SessionContext;

import java.io.Serializable;
import java.util.HashMap;

@AllArgsConstructor
public class DefaultChatSessionContext extends HashMap<String, Object> implements SessionContext {
    private long sessionId;
    @Setter
    @Getter
    private String host;

    @Override
    public Serializable getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(Serializable serializable) {
        this.sessionId = (long) serializable;
    }
}
