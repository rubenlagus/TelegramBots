package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Ability.AbilityBuilder;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyCollection;
import org.telegram.abilitybots.api.toggle.AbilityToggle;

import static org.telegram.abilitybots.api.objects.Ability.builder;
import static org.telegram.abilitybots.api.objects.Flag.CALLBACK_QUERY;
import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Locality.*;
import static org.telegram.abilitybots.api.objects.Privacy.ADMIN;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class DefaultBot extends AbilityBot {
  public static final String FIRST_REPLY_KEY_MESSAGE = "first reply key string";
  public static final String SECOND_REPLY_KEY_MESSAGE = "second reply key string";

  public DefaultBot(String token, String username, DBContext db) {
    super(token, username, db);
  }

  public DefaultBot(String token, String username, DBContext db, AbilityToggle toggle) {
    super(token, username, db, toggle);
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
  public long creatorId() {
    return 1337L;
  }

  public Ability defaultAbility() {
    return getDefaultBuilder()
        .name(DEFAULT)
        .info("dis iz default command")
        .reply(Reply.of((bot, upd) -> silent.send("reply", upd.getMessage().getChatId()), MESSAGE, update -> update.getMessage().getText().equals("must reply")).enableStats("mustreply"))
        .reply((bot, upd) -> silent.send("reply", upd.getCallbackQuery().getMessage().getChatId()), CALLBACK_QUERY)
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
        .enableStats()
        .build();
  }

  public Reply channelPostReply() {
    return Reply.of(
            (bot, upd) -> silent.send("test channel post", upd.getChannelPost().getChatId()),
            Flag.CHANNEL_POST
    );
  }

  public ReplyCollection createReplyCollection() {
    return ReplyCollection.of(
        Reply.of(
                (bot, upd) -> silent.send("first reply answer", upd.getMessage().getChatId()),
                update -> update.getMessage().getText().equalsIgnoreCase(FIRST_REPLY_KEY_MESSAGE)
        ),
        Reply.of(
                (bot, upd) -> silent.send("second reply answer", upd.getMessage().getChatId()),
                update -> update.getMessage().getText().equalsIgnoreCase(SECOND_REPLY_KEY_MESSAGE)
        )
    );
  }

  public Ability testAbility() {
    return getDefaultBuilder().build();
  }
}
