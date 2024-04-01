package org.telegram.telegrambots.meta.api.objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Group members categories
 * @deprecated Use {{@link org.telegram.telegrambots.meta.api.objects.chatmember.MemberStatus}}
 */
@Deprecated
public final class MemberStatus {
    public static final String CREATOR = "creator";
    public static final String ADMINISTRATOR = "administrator";
    public static final String MEMBER = "member";
    public static final String LEFT = "left";
    public static final String KICKED = "kicked";
}
