package org.telegram.telegrambots.meta.api.objects.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Represents the rights of a business bot.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessBotRights implements BotApiObject {
    private static final String CAN_REPLY_FIELD = "can_reply";
    private static final String CAN_READ_MESSAGES_FIELD = "can_read_messages";
    private static final String CAN_DELETE_OUTGOING_MESSAGES_FIELD = "can_delete_outgoing_messages";
    private static final String CAN_DELETE_ALL_MESSAGES_FIELD = "can_delete_all_messages";
    private static final String CAN_EDIT_NAME_FIELD = "can_edit_name";
    private static final String CAN_EDIT_BIO_FIELD = "can_edit_bio";
    private static final String CAN_EDIT_PROFILE_PHOTO_FIELD = "can_edit_profile_photo";
    private static final String CAN_EDIT_USERNAME_FIELD = "can_edit_username";
    private static final String CAN_CHANGE_GIFT_SETTINGS_FIELD = "can_change_gift_settings";
    private static final String CAN_VIEW_GIFTS_AND_STARS_FIELD = "can_view_gifts_and_stars";
    private static final String CAN_CONVERT_GIFTS_TO_STARS_FIELD = "can_convert_gifts_to_stars";
    private static final String CAN_TRANSFER_AND_UPGRADE_GIFTS_FIELD = "can_transfer_and_upgrade_gifts";
    private static final String CAN_TRANSFER_STARS_FIELD = "can_transfer_stars";
    private static final String CAN_MANAGE_STORIES_FIELD = "can_manage_stories";

    /**
     * Optional.
     * True, if the bot can send and edit messages in the private chats that had incoming messages in the last 24 hours
     */
    @JsonProperty(CAN_REPLY_FIELD)
    private Boolean canReply;
    /**
     * Optional.
     * True, if the bot can mark incoming private messages as read
     */
    @JsonProperty(CAN_READ_MESSAGES_FIELD)
    private Boolean canReadMessages;
    /**
     * Optional.
     * True, if the bot can delete messages sent by the bot
     */
    @JsonProperty(CAN_DELETE_OUTGOING_MESSAGES_FIELD)
    private Boolean canDeleteOutgoingMessages;
    /**
     * Optional.
     * True, if the bot can delete all private messages in managed chats
     */
    @JsonProperty(CAN_DELETE_ALL_MESSAGES_FIELD)
    private Boolean canDeleteAllMessages;
    /**
     * Optional.
     * True, if the bot can edit the first and last name of the business account
     */
    @JsonProperty(CAN_EDIT_NAME_FIELD)
    private Boolean canEditName;
    /**
     * Optional.
     * True, if the bot can edit the bio of the business account
     */
    @JsonProperty(CAN_EDIT_BIO_FIELD)
    private Boolean canEditBio;
    /**
     * Optional.
     * True, if the bot can edit the profile photo of the business account
     */
    @JsonProperty(CAN_EDIT_PROFILE_PHOTO_FIELD)
    private Boolean canEditProfilePhoto;
    /**
     * Optional.
     * True, if the bot can edit the username of the business account
     */
    @JsonProperty(CAN_EDIT_USERNAME_FIELD)
    private Boolean canEditUsername;
    /**
     * Optional.
     * True, if the bot can change the privacy settings pertaining to gifts for the business account
     */
    @JsonProperty(CAN_CHANGE_GIFT_SETTINGS_FIELD)
    private Boolean canChangeGiftSettings;
    /**
     * Optional.
     * True, if the bot can view gifts and the amount of Telegram Stars owned by the business account
     */
    @JsonProperty(CAN_VIEW_GIFTS_AND_STARS_FIELD)
    private Boolean canViewGiftsAndStars;
    /**
     * Optional.
     * True, if the bot can convert regular gifts owned by the business account to Telegram Stars
     */
    @JsonProperty(CAN_CONVERT_GIFTS_TO_STARS_FIELD)
    private Boolean canConvertGiftsToStars;
    /**
     * Optional.
     * True, if the bot can transfer and upgrade gifts owned by the business account
     */
    @JsonProperty(CAN_TRANSFER_AND_UPGRADE_GIFTS_FIELD)
    private Boolean canTransferAndUpgradeGifts;
    /**
     * Optional.
     * True, if the bot can transfer Telegram Stars received by the business account to its own account, or use them to upgrade and transfer gifts
     */
    @JsonProperty(CAN_TRANSFER_STARS_FIELD)
    private Boolean canTransferStars;
    /**
     * Optional.
     * True, if the bot can post, edit and delete stories on behalf of the business account
     */
    @JsonProperty(CAN_MANAGE_STORIES_FIELD)
    private Boolean canManageStories;
}
