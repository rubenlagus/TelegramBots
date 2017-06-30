package org.telegram.abilitybots.api.objects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.telegram.abilitybots.api.bot.DefaultBot.getDefaultBuilder;

public class AbilityTest {
  @Test(expected = IllegalArgumentException.class)
  public void argumentsCannotBeNegative() {
    getDefaultBuilder().input(-4).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void nameCannotBeEmpty() {
    getDefaultBuilder().name("").build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void nameCannotBeNull() {
    getDefaultBuilder().name(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void consumerCannotBeNull() {
    getDefaultBuilder().action(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void localityCannotBeNull() {
    getDefaultBuilder().locality(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void privacyCannotBeNull() {
    getDefaultBuilder().privacy(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void nameCannotContainSpaces() {
    getDefaultBuilder().name("test test").build();
  }

  @Test
  public void abilityEqualsMethod() {
    Ability ability1 = getDefaultBuilder().build();
    Ability ability2 = getDefaultBuilder().build();
    Ability ability3 = getDefaultBuilder().name("anotherconsumer").build();
    Ability ability4 = getDefaultBuilder().action((context) -> {
    }).build();

    assertEquals("Abilities should not be equal", ability1, ability2);
    assertEquals("Abilities should not be equal", ability1, ability4);
    assertNotEquals("Abilities should be equal", ability1, ability3);
  }
}

