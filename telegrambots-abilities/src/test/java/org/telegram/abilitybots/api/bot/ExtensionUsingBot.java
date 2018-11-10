package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;

import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class ExtensionUsingBot extends AbilityBot {
  public ExtensionUsingBot() {
    super("", "", offlineInstance("testing"));
  }


  @Override
  public int creatorId() {
    return 0;
  }

  public AbilityBotExtension methodReturningExtensionSubClass() {
    return new AbilityBotExtension("returningSubClass");
  }

  public AbilityExtension methodReturningExtensionSuperClass() {
    return new AbilityBotExtension("returningSuperClass");
  }

  public Ability methodReturningAbility() {
    return Ability.builder()
        .name("direct")
        .info("Test ability")
        .locality(ALL)
        .privacy(PUBLIC)
        .action(messageContext -> {
        })
        .build();
  }


}
