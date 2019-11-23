package org.telegram.abilitybots.api.toggle;

import org.telegram.abilitybots.api.objects.Ability;

/**
 * This toggle can be used as-is to turn off ALL the default abilities supplied by the library.
 * This is for users who are interested in the barebone functionality of AbilityBot.
 */
public class BareboneToggle implements AbilityToggle {
  @Override
  public boolean isOff(Ability ability) {
    return true;
  }

  @Override
  public Ability processAbility(Ability ab) {
    // Should never hit this
    throw new RuntimeException("Should not process any ability in a vanilla toggle");
  }
}
