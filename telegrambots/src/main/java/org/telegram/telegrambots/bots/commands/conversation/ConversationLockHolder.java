package org.telegram.telegrambots.bots.commands.conversation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The ConversationLockHolder class.
 * Keep lock for chat and {@link ConversationCommand}.
 *
 * @author jrrakh
 *         12/26/16
 */
public final class ConversationLockHolder {
    private Map<Long, ConversationCommand> lock = new ConcurrentHashMap<>();


    public boolean isLocked(long chatId) {
        return lock.containsKey(chatId);
    }

    public void lock(long chatId, ConversationCommand command) {
        if (!isLocked(chatId)) {
            lock.put(chatId, command);
        }
    }

    public void unlock(long chatId) {
        lock.remove(chatId);
    }

    public ConversationCommand getLockedConversation(long chatId) {
        return lock.get(chatId);
    }
}
