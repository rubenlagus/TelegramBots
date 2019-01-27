package org.telegram.abilitybots.api.bot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class ExtensionTest {
  private ExtensionUsingBot bot;

  @Before
  public void setUp() {
    bot = new ExtensionUsingBot();
  }

  @Test
  public void methodReturningAbilities() {
    assertTrue("Failed to find Ability in directly declared in root extension/bot", hasAbilityNamed("direct"));
    assertTrue("Failed to find Ability in directly declared in extension returned by method returning the AbilityExtension class", hasAbilityNamed("returningSuperClass0abc"));
    assertTrue("Failed to find Ability in directly declared in extension returned by method returning the AbilityExtension subclass", hasAbilityNamed("returningSubClass0abc"));
  }

  @After
  public void tearDown() throws IOException {
    bot.db.clear();
    bot.db.close();
  }

  private boolean hasAbilityNamed(String name) {
    return bot.abilities().values().stream().map(Ability::name).anyMatch(name::equals);
  }

  public static class ExtensionUsingBot extends AbilityBot {
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

  public static class AbilityBotExtension implements AbilityExtension {
    private String name;

    public AbilityBotExtension(String name) {
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