package org.telegram.telegrambots.abilitybots.api.toggle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.abilitybots.api.bot.DefaultAbilities;
import org.telegram.telegrambots.abilitybots.api.bot.DefaultBot;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.telegram.telegrambots.abilitybots.api.db.MapDBContext.offlineInstance;

class TestCustomToggle {
  private DBContext db;
  private AbilityToggle toggle;
  private DefaultBot customBot;
  private String filename;

  @BeforeEach
  void setUp() {
    db = offlineInstance("db");
    filename = "src/test/resources/toggle.properties";
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  public void canTurnOffAbilities() {
    toggle = new CustomToggle().turnOff(DefaultAbilities.CLAIM);
    customBot = new DefaultBot(null, EMPTY, db, toggle);
    customBot.onRegister();
    assertFalse(customBot.getAbilities().containsKey(DefaultAbilities.CLAIM));
  }

  @Test
  public void canProcessAbilities() {
    String targetName = DefaultAbilities.CLAIM + "1toggle";
    toggle = new CustomToggle().toggle(DefaultAbilities.CLAIM, targetName);
    customBot = new DefaultBot(null, EMPTY, db, toggle);
    customBot.onRegister();

    assertTrue(customBot.getAbilities().containsKey(targetName));
  }

  @Test
  public void canTurnOffAbilitiesThroughProperties() {
    Properties properties = new Properties();
    try {
      properties.load(Files.newInputStream(Paths.get(filename)));
      toggle = new CustomToggle().config(properties);
    } catch (IOException e) {
      System.out.println("No such file");
    }

    customBot = new DefaultBot(null, EMPTY, db, toggle);
    customBot.onRegister();

    assertFalse(customBot.getAbilities().containsKey(DefaultAbilities.CLAIM));
  }

  @Test
  public void canProcessAbilitiesThroughProperties() {
    Properties properties = new Properties();
    try {
      properties.load(Files.newInputStream(Paths.get(filename)));
      toggle = new CustomToggle().config(properties);
    } catch (IOException e) {
      System.out.println("No such file");
    }

    customBot = new DefaultBot(null, EMPTY, db, toggle);
    customBot.onRegister();

    String targetName = "restrict";
    assertTrue(customBot.getAbilities().containsKey(targetName));
  }

}