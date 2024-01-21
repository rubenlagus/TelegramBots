package org.telegram.telegrambots.abilitybots.api.toggle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.abilitybots.api.bot.DefaultAbilities;
import org.telegram.telegrambots.abilitybots.api.bot.DefaultBot;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.*;
import static org.telegram.telegrambots.abilitybots.api.db.MapDBContext.offlineInstance;

public class TestBareboneToggle {

  private DBContext db;
  private AbilityToggle toggle;
  private DefaultBot bareboneBot;
  private DefaultAbilities defaultAbs;

  @BeforeEach
  void setUp() {
    db = offlineInstance("db");
    toggle = new BareboneToggle();
    bareboneBot = new DefaultBot(null, EMPTY, db, toggle);
    bareboneBot.onRegister();
    defaultAbs = new DefaultAbilities(bareboneBot);
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  public void turnsOffAllAbilities() {
    assertFalse(bareboneBot.getAbilities().containsKey(DefaultAbilities.CLAIM));
  }

  @Test
  public void throwsOnProcessingAbility() {
    Assertions.assertThrows(RuntimeException.class, () -> toggle.processAbility(defaultAbs.claimCreator()));
  }
}