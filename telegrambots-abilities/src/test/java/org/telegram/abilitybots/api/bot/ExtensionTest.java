package org.telegram.abilitybots.api.bot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

class ExtensionTest {
  private ExtensionUsingBot bot;

  @BeforeEach
  void setUp() {
    bot = new ExtensionUsingBot();
    bot.onRegister();
  }

  @AfterEach
  void tearDown() throws IOException {
    bot.db.clear();
    bot.db.close();
  }

  @Test
  void methodReturningAbilities() {
    assertTrue(hasAbilityNamed("direct"), "Failed to find Ability in directly declared in root extension/bot");
    assertTrue(hasAbilityNamed("returningSuperClass0abc"), "Failed to find Ability in directly declared in extension returned by method returning the AbilityExtension class");
    assertTrue(hasAbilityNamed("returningSubClass0abc"), "Failed to find Ability in directly declared in extension returned by method returning the AbilityExtension subclass");
    assertTrue(hasAbilityNamed("addedInConstructor0abc"), "Failed to find Ability in directly declared in extension added in the constructor");
  }

  private boolean hasAbilityNamed(String name) {
    return bot.abilities().values().stream().map(Ability::name).anyMatch(name::equals);
  }

  public static class ExtensionUsingBot extends AbilityBot {
    ExtensionUsingBot() {
      super("", "", offlineInstance("testing"));
      addExtension(new AbilityBotExtension("addedInConstructor"));
    }

    @Override
    public long creatorId() {
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

  public static class AbilityBotExtension implements AbilityExtension {
    private String name;

    AbilityBotExtension(String name) {
      this.name = name;
    }

    public Ability abc() {
      return Ability.builder()
          .name(name + "0abc")
          .info("Test ability")
          .locality(ALL)
          .privacy(PUBLIC)
          .action(ctx -> {
          })
          .build();
    }
  }
}