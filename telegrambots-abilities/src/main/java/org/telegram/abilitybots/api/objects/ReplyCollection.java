package org.telegram.abilitybots.api.objects;

import java.util.Collection;

public class ReplyCollection {

    public final Collection<Reply> replies;

    public ReplyCollection(Collection<Reply> replies) {
        this.replies = replies;
    }

    public Collection<Reply> getReplies() {
        return replies;
    }

}
