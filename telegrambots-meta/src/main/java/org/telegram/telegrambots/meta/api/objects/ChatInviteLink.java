package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 5.1
 *
 * Represents an invite link for a chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatInviteLink implements BotApiObject {
    private static final String INVITELINK_FIELD = "invite_link";
    private static final String CREATOR_FIELD = "creator";
    private static final String ISPRIMARY_FIELD = "is_primary";
    private static final String ISREVOKED_FIELD = "is_revoked";
    private static final String EXPIREDATE_FIELD = "expire_date";
    private static final String MEMBERLIMIT_FIELD = "member_limit";
    private static final String NAME_FIELD = "name";
    private static final String PENDINGJOINREQUESTCOUNT_FIELD = "pending_join_request_count";
    private static final String CREATESJOINREQUEST_FIELD = "creates_join_request";

    /**
     * The invite link.
     * If the link was created by another chat administrator, then the second part of the link will be replaced with “…”.
     */
    @JsonProperty(INVITELINK_FIELD)
    private String inviteLink;
    @JsonProperty(CREATOR_FIELD)
    private User creator; ///< Creator of the link
    @JsonProperty(ISPRIMARY_FIELD)
    private Boolean isPrimary; ///< True, if the link is primary
    @JsonProperty(ISREVOKED_FIELD)
    private Boolean isRevoked; ///< True, if the link is revoked
    @JsonProperty(EXPIREDATE_FIELD)
    private Integer expireDate; ///< Optional. Point in time (Unix timestamp) when the link will expire or has been expired
    /**
     * Optional.
     *
     * Maximum number of users that can be members of the chat simultaneously after joining the chat via this invite link; 1-99999
     */
    @JsonProperty(MEMBERLIMIT_FIELD)
    private Integer memberLimit;
    @JsonProperty(NAME_FIELD)
    private String name; ///< Optional. Invite link name
    @JsonProperty(PENDINGJOINREQUESTCOUNT_FIELD)
    private Integer pendingJoinRequestCount; ///< Optional. Number of pending join requests created using this link
    /**
     * True, if users joining the chat via the link need to be approved by chat administrators
     */
    @JsonProperty(CREATESJOINREQUEST_FIELD)
    private Boolean createsJoinRequest;
}
