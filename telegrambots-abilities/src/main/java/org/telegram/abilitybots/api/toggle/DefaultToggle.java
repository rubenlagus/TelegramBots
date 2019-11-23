package org.telegram.abilitybots.api.toggle;

import org.telegram.abilitybots.api.objects.Ability;

/**
 * If the user does not supply a toggle to their constructor, the default toggle will be instantiated.
 * This default toggle allows all the default abilities to be registered.
 */
public class DefaultToggle implements AbilityToggle {
  @Override
  public boolean isOff(Ability ability) {
    return false;
  }

  @Override
  public Ability processAbility(Ability ab) {
    return ab;
  }
}
