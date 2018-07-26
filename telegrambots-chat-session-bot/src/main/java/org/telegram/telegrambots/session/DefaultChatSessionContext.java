package org.telegram.telegrambots.session;

import org.apache.shiro.session.mgt.SessionContext;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public class DefaultChatSessionContext extends HashMap<String, Object> implements SessionContext {
    private long sessionId;
    private String host;

    public DefaultChatSessionContext(long sessionId, String host) {
        this.sessionId = sessionId;
        this.host = host;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Serializable getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(Serializable serializable) {
        this.sessionId = (long) serializable;
    }
}
