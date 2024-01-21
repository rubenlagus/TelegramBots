package org.telegram.telegrambots.abilitybots.api.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.abilitybots.api.bot.DefaultBot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TestAbility {
  @Test
  void argumentsCannotBeNegative() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> DefaultBot.getDefaultBuilder().input(-4).build());
  }

  @Test
  void nameCannotBeEmpty() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> DefaultBot.getDefaultBuilder().name("").build());
  }

  @Test
  void nameCannotBeNull() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> DefaultBot.getDefaultBuilder().name(null).build());
  }

  @Test
  void consumerCannotBeNull() {
    Assertions.assertThrows(NullPointerException.class, () -> DefaultBot.getDefaultBuilder().action(null).build());
  }

  @Test
  void localityCannotBeNull() {
    Assertions.assertThrows(NullPointerException.class, () -> DefaultBot.getDefaultBuilder().locality(null).build());
  }

  @Test
  void privacyCannotBeNull() {
    Assertions.assertThrows(NullPointerException.class, () -> DefaultBot.getDefaultBuilder().privacy(null).build());
  }

  @Test
  void nameCannotContainSpaces() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> DefaultBot.getDefaultBuilder().name("test test").build());
  }

  @Test
  void abilityEqualsMethod() {
    Ability ability1 = DefaultBot.getDefaultBuilder().build();
    Ability ability2 = DefaultBot.getDefaultBuilder().build();
    Ability ability3 = DefaultBot.getDefaultBuilder().name("anotherconsumer").build();
    Ability ability4 = DefaultBot.getDefaultBuilder().action((context) -> {
    }).build();

    assertEquals(ability1, ability2, "Abilities should not be equal");
    assertEquals(ability1, ability4, "Abilities should not be equal");
    assertNotEquals(ability1, ability3, "Abilities should be equal");
  }

  @Test
  void abilityBuilderSetStatsEnabledTrueTest() {
    Ability statsEnabledAbility = DefaultBot.getDefaultBuilder().setStatsEnabled(true).build();
    assertTrue(statsEnabledAbility.statsEnabled());
  }

  @Test
  void abilityBuilderSetStatsEnabledFalseTest() {
    Ability statsDisabledAbility = DefaultBot.getDefaultBuilder().setStatsEnabled(false).build();
    assertFalse(statsDisabledAbility.statsEnabled());
  }
}

