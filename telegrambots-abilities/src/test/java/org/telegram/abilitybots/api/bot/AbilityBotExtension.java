package org.telegram.abilitybots.api.bot;

import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class AbilityBotExtension implements AbilityExtension {
  private String name;

  public AbilityBotExtension(String name) {
    this.name = name;
  }

  public Ability abc() {
    return Ability.builder()
        .name(this.name + "0abc")
        .info("Test ability")
        .locality(ALL)
        .privacy(PUBLIC)
        .action(messageContext -> {
        })
        .build();
  }
}
