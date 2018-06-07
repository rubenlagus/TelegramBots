package org.telegram.telegrambots.session;

import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

public interface ChatIdConverter extends SessionKey, SessionIdGenerator {
    public void setSessionId(Serializable sessionId);
}