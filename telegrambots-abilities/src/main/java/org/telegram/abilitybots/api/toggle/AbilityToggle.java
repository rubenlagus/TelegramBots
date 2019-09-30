package org.telegram.abilitybots.api.toggle;

import org.telegram.abilitybots.api.objects.Ability;

/**
 * This interface can be used to toggle or customize unwanted default abilities by the user.
 */
public interface AbilityToggle {
  /**
   * @param ab the target ability
   * @return true if the ability has been turned off
   */
  boolean isOff(Ability ab);

  /**
   * Abilities that are ON (and have failed the {@link AbilityToggle#isOff} condition) will be processed by this method.
   * @param ab the target ability
   * @return the processed ability
   */
  Ability processAbility(Ability ab);
}
