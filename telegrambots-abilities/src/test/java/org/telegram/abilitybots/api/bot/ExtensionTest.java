package org.telegram.abilitybots.api.bot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.telegram.abilitybots.api.objects.Ability;

import static junit.framework.TestCase.assertTrue;

public class ExtensionTest {
  private ExtensionUsingBot bot;

  @Before
  public void setUp() {
    bot = new ExtensionUsingBot();
  }

  @After
  public void teardown() {
    bot = null;
  }


  @Test
  public void methodReturningAbilities() {
    assertTrue("Failed to find Ability in directly declared in root extension/bot", hasAbilityNamed("direct"));
    assertTrue("Failed to find Ability in directly declared in extension returned by method returning the AbilityExtension class", hasAbilityNamed("returningSuperClass0abc"));
    assertTrue("Failed to find Ability in directly declared in extension returned by method returning the AbilityExtension subclass", hasAbilityNamed("returningSubClass0abc"));
  }

  private boolean hasAbilityNamed(String name) {
    return bot.abilities().values().stream().map(Ability::name).anyMatch(name::equals);
  }


}
