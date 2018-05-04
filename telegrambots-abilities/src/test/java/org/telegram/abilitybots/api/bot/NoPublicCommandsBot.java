package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.db.DBContext;

public class NoPublicCommandsBot extends AbilityBot {


    protected NoPublicCommandsBot(String botToken, String botUsername, DBContext db) {
        super(botToken, botUsername, db);
    }

    @Override
    public int creatorId() {
        return 0;
    }
}