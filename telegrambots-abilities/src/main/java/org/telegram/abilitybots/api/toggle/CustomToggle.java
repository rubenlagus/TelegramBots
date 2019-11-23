package org.telegram.abilitybots.api.toggle;

import org.telegram.abilitybots.api.objects.Ability;

import java.util.HashMap;
import java.util.Map;

/**
 * This custom toggle can be used to customize default abilities supplied by the library. Users can call {@link CustomToggle#toggle} to
 * rename the default abilites or {@link CustomToggle#turnOff} to simply turn off the said ability.
 */
public class CustomToggle implements AbilityToggle {
  public static final String OFF = "turn_off_base_ability";

  private final Map<String, String> baseMapping;

  public CustomToggle() {
    baseMapping = new HashMap<>();
  }

  @Override
  public boolean isOff(Ability ability) {
    return OFF.equalsIgnoreCase(baseMapping.get(ability.name()));
  }

  @Override
  public Ability processAbility(Ability ability) {
    if (baseMapping.containsKey(ability.name())) {
      return Ability.builder()
          .basedOn(ability)
          .name(baseMapping.get(ability.name()))
          .build();
    }

    return ability;
  }

  /**
   * @param abilityName the ability you want to change
   * @param targetName the final name for this ability
   * @return the toggle instance
   */
  public CustomToggle toggle(String abilityName, String targetName) {
    baseMapping.put(abilityName, targetName);
    return this;
  }

  /**
   * @param ability the ability name you would like turned off
   * @return the toggle instance
   */
  public CustomToggle turnOff(String ability) {
    baseMapping.put(ability, OFF);
    return this;
  }
}
