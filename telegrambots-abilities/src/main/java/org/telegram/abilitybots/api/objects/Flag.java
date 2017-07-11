package org.telegram.abilitybots.api.objects;

import org.telegram.abilitybots.api.objects.Ability.AbilityBuilder;
import org.telegram.telegrambots.api.objects.Update;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

/**
 * Flags are an conditions that are applied on an {@link Update}.
 * <p>
 * They can be used on {@link AbilityBuilder#flag(Flag...)} and on the post conditions in {@link AbilityBuilder#reply(Consumer, Predicate[])}.
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

  // Message Flags
  REPLY(update -> update.getMessage().isReply()),
  DOCUMENT(upd -> upd.getMessage().hasDocument()),
  TEXT(upd -> upd.getMessage().hasText()),
  PHOTO(upd -> upd.getMessage().hasPhoto()),
  LOCATION(upd -> upd.getMessage().hasLocation()),
  CAPTION(upd -> nonNull(upd.getMessage().getCaption()));

  private final Predicate<Update> predicate;

  Flag(Predicate<Update> predicate) {
    this.predicate = predicate;
  }

  public boolean test(Update update) {
    return nonNull(update) && predicate.test(update);
  }
}
