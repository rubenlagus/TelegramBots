package org.telegram.abilitybots.api.objects;

/**
 * Locality identifies the location in which you want your message to be accessed.
 * <p>
 * If locality of your message is set to <code>USER</code>, then the ability will only be executed if its being called in a user private chat.
 *
 * @author Abbas Abou Daya
 */
public enum Locality {
  /**
   * Ability would be valid for groups and private user chats
   */
  ALL,
  /**
   * Only user chats
   */
  USER,
  /**
   * Only group chats
   */
  GROUP
}
