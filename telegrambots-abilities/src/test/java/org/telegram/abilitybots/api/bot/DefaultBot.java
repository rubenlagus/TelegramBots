package org.telegram.abilitybots.api.bot;

import com.google.common.annotations.VisibleForTesting;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Ability.AbilityBuilder;
import org.telegram.abilitybots.api.sender.MessageSender;

import static org.telegram.abilitybots.api.objects.Ability.builder;
import static org.telegram.abilitybots.api.objects.Flag.CALLBACK_QUERY;
import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Locality.*;
import static org.telegram.abilitybots.api.objects.Privacy.ADMIN;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class DefaultBot extends AbilityBot {

  public DefaultBot(String token, String username, DBContext db) {
    super(token, username, db);
  }

  public static AbilityBuilder getDefaultBuilder() {
    return builder()
        .name("test")
        .privacy(PUBLIC)
        .locality(ALL)
        .input(1)
        .action(ctx -> {
        });
  }

  @Override
  public int creatorId() {
    return 1337;
  }

  public Ability defaultAbility() {
    return getDefaultBuilder()
        .name(DEFAULT)
        .info("dis iz default command")
        .reply(upd -> sender.send("reply", upd.getMessage().getChatId()), MESSAGE, update -> update.getMessage().getText().equals("must reply"))
        .reply(upd -> sender.send("reply", upd.getCallbackQuery().getMessage().getChatId()), CALLBACK_QUERY)
        .build();
  }

  public Ability adminAbility() {
    return getDefaultBuilder()
        .name("admin")
        .privacy(ADMIN)
        .build();
  }

  public Ability groupAbility() {
    return getDefaultBuilder()
        .name("group")
        .privacy(PUBLIC)
        .locality(GROUP)
        .build();
  }

  public Ability multipleInputAbility() {
    return getDefaultBuilder()
        .name("count")
        .privacy(PUBLIC)
        .locality(USER)
        .input(4)
        .build();
  }

  public Ability testAbility() {
    return getDefaultBuilder().build();
  }

  @VisibleForTesting
  void setSender(MessageSender sender) {
    this.sender = sender;
  }
}