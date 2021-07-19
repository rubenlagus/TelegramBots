package org.telegram.abilitybots.api.toggle;

import org.telegram.abilitybots.api.objects.Ability;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This custom toggle can be used to customize default abilities supplied by the library. Users can call {@link CustomToggle#toggle} to
 * rename the default abilities or {@link CustomToggle#turnOff} to simply turn off the said ability.
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

  /**
   * @param properties the abilities toggle definition
   * @return the toggle instance
   */
  public CustomToggle config(Properties properties) {
    for (String key : properties.stringPropertyNames()) {
      String value = properties.getProperty(key);
      key = key.toLowerCase();

      // compare with legal configuration names
      for (Property p: Property.values()) {
        if (key.equals(p.key())) {
          String ability = key.split("\\.")[1];
          if (key.contains("enabled") && value.equalsIgnoreCase("false")) {
            this.turnOff(ability);
          }else if (key.contains("toggle")) {
            this.toggle(ability, value);
          }
        }
      }
    }
    return this;
  }

  /**
   * List of all the properties recognized by {@link CustomToggle}.
   * Can be used to programmatically get, set or remove default values.
   */
  public enum Property{
    CLAIM_ENABLED("ability.claim.enabled"),
    CLAIM_TOGGLE("ability.claim.toggle"),

    BAN_ENABLED("ability.ban.enabled"),
    BAN_TOGGLE("ability.ban.toggle"),

    PROMOTE_ENABLED("ability.promote.enabled"),
    PROMOTE_TOGGLE("ability.promote.toggle"),

    DEMOTE_ENABLED("ability.demote.enabled"),
    DEMOTE_TOGGLE("ability.demote.toggle"),

    UNBAN_ENABLED("ability.unban.enabled"),
    UNBAN_TOGGLE("ability.unban.toggle"),

    BACKUP_ENABLED("ability.backup.enabled"),
    BACKUP_TOGGLE("ability.backup.toggle"),

    RECOVER_ENABLED("ability.recover.enabled"),
    RECOVER_TOGGLE("ability.recover.toggle"),

    COMMANDS_ENABLED("ability.commands.enabled"),
    COMMANDS_TOGGLE("ability.commands.toggle"),

    REPORT_ENABLED("ability.report.enabled"),
    REPORT_TOGGLE("ability.report.toggle"),

    STATS_ENABLED("ability.stats.enabled"),
    STATS_TOGGLE("ability.stats.toggle")
    ;

    private final String key;

    Property (final String key){
      this.key = key;
    }

    public String key() {
      return key;
    }
  }

}
