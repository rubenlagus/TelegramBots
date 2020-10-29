package org.telegram.abilitybots.api.toggle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.abilitybots.api.bot.DefaultAbilities;
import org.telegram.abilitybots.api.bot.DefaultBot;
import org.telegram.abilitybots.api.db.DBContext;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.*;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;

public class BareboneToggleTest {

  private DBContext db;
  private AbilityToggle toggle;
  private DefaultBot bareboneBot;
  private DefaultAbilities defaultAbs;

  @BeforeEach
  void setUp() {
    db = offlineInstance("db");
    toggle = new BareboneToggle();
    bareboneBot = new DefaultBot(EMPTY, EMPTY, db, toggle);
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
    assertFalse(bareboneBot.abilities().containsKey(DefaultAbilities.CLAIM));
  }

  @Test
  public void throwsOnProcessingAbility() {
    Assertions.assertThrows(RuntimeException.class, () -> toggle.processAbility(defaultAbs.claimCreator()));
  }
}