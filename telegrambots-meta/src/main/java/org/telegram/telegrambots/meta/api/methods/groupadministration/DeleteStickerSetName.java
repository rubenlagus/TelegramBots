package org.telegram.telegrambots.meta.api.methods.groupadministration;

/**
 * @author Ruben Bermudez
 * @version 3.4
 * Use this method to delete a group sticker set from a supergroup.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method.
 * Returns True on success.
 * @deprecated  Replaced by {@link DeleteChatStickerSet}
 */
@Deprecated
public class DeleteStickerSetName extends DeleteChatStickerSet {
}
