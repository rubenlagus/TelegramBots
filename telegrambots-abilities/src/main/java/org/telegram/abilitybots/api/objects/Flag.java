package org.telegram.abilitybots.api.objects;

import org.telegram.abilitybots.api.objects.Ability.AbilityBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

/**
 * Flags are an conditions that are applied on an {@link Update}.
 * <p>
 * They can be used on {@link AbilityBuilder#flag(Predicate[])} and on the post conditions in {@link AbilityBuilder#reply(BiConsumer, Predicate[])}.
 *
 * @author Abbas Abou Daya
 */
public enum Flag implements Predicate<Update> {
  // Update Flags
  NONE(update -> true),
  MESSAGE(Update::hasMessage),
  CALLBACK_QUERY(Update::hasCallbackQuery),
  CHANNEL_POST(Update::hasChannelPost),
  EDITED_CHANNEL_POST(Update::hasEditedChannelPost),
  EDITED_MESSAGE(Update::hasEditedMessage),
  INLINE_QUERY(Update::hasInlineQuery),
  CHOSEN_INLINE_QUERY(Update::hasChosenInlineQuery),
  SHIPPING_QUERY(Update::hasShippingQuery),
  PRECHECKOUT_QUERY(Update::hasPreCheckoutQuery),
  POLL(Update::hasPoll),
  POLL_ANSWER(Update::hasPollAnswer),
  MY_CHAT_MEMBER(Update::hasMyChatMember),
  CHAT_MEMBER(Update::hasChatMember),
  CHAT_JOIN_REQUEST(Update::hasChatJoinRequest),


  // Message Flags
  REPLY(upd -> MESSAGE.test(upd) && upd.getMessage().isReply()),
  DOCUMENT(upd -> MESSAGE.test(upd) && upd.getMessage().hasDocument()),
  TEXT(upd -> MESSAGE.test(upd) && upd.getMessage().hasText()),
  PHOTO(upd -> MESSAGE.test(upd) && upd.getMessage().hasPhoto()),
  LOCATION(upd -> MESSAGE.test(upd) && upd.getMessage().hasLocation()),
  CAPTION(upd -> MESSAGE.test(upd) && nonNull(upd.getMessage().getCaption()));

  private final Predicate<Update> predicate;

  Flag(Predicate<Update> predicate) {
    this.predicate = predicate;
  }

  public boolean test(Update update) {
    return nonNull(update) && predicate.test(update);
  }
}
