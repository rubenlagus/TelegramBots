package org.telegram.abilitybots.api.objects;

/**
 * Privacy represents a restriction on <b>who</b> can use the ability.
 *
 * @author Abbas Abou Daya
 */
public enum Privacy {
  /**
   * Anybody who is not a bot admin or its creator will be considered as a public user.
   */
  PUBLIC,
  /**
   * Only group admins would get to initiate this command.
   */
  GROUP_ADMIN,
  /**
   * A global admin of the bot, regardless of the group the bot is in.
   */
  ADMIN,
  /**
   * The creator of the bot.
   */
  CREATOR
}
