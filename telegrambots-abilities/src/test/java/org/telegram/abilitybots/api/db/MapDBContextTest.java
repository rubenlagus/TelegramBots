package org.telegram.abilitybots.api.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.telegram.abilitybots.api.objects.EndUser;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.telegram.abilitybots.api.bot.AbilityBot.USERS;
import static org.telegram.abilitybots.api.bot.AbilityBot.USER_ID;
import static org.telegram.abilitybots.api.bot.AbilityBotTest.CREATOR;
import static org.telegram.abilitybots.api.bot.AbilityBotTest.MUSER;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;

public class MapDBContextTest {

  private static final String TEST = "TEST";
  private DBContext db;

  @Before
  public void setUp() {
    db = offlineInstance("db");
  }

  @Test
  public void canRecoverDB() throws IOException {
    Map<Integer, EndUser> users = db.getMap(USERS);
    Map<String, Integer> userIds = db.getMap(USER_ID);
    users.put(CREATOR.id(), CREATOR);
    users.put(MUSER.id(), MUSER);
    userIds.put(CREATOR.username(), CREATOR.id());
    userIds.put(MUSER.username(), MUSER.id());

    db.getSet("AYRE").add(123123);
    Map<Integer, EndUser> originalUsers = newHashMap(users);
    String beforeBackupInfo = db.info(USERS);

    Object jsonBackup = db.backup();
    db.clear();
    boolean recovered = db.recover(jsonBackup);

    Map<Integer, EndUser> recoveredUsers = db.getMap(USERS);
    String afterRecoveryInfo = db.info(USERS);

    assertTrue("Could not recover database successfully", recovered);
    assertEquals("Map info before and after recovery is different", beforeBackupInfo, afterRecoveryInfo);
    assertEquals("Map before and after recovery are not equal", originalUsers, recoveredUsers);
  }

  @Test
  public void canFallbackDBIfRecoveryFails() throws IOException {
    Set<EndUser> users = db.getSet(USERS);
    users.add(CREATOR);
    users.add(MUSER);

    Set<EndUser> originalSet = newHashSet(users);
    Object jsonBackup = db.backup();
    String corruptBackup = "!@#$" + String.valueOf(jsonBackup);
    boolean recovered = db.recover(corruptBackup);

    Set<EndUser> recoveredSet = db.getSet(USERS);

    assertEquals("Recovery was successful from a CORRUPT backup", false, recovered);
    assertEquals("Set before and after corrupt recovery are not equal", originalSet, recoveredSet);
  }

  @Test
  public void canGetSummary() throws IOException {
    String anotherTest = TEST + 1;
    db.getSet(TEST).add(TEST);
    db.getSet(anotherTest).add(anotherTest);

    String actualSummary = db.summary();
    // Name - Type - Number of "rows"
    String expectedSummary = format("%s - Set - 1\n%s - Set - 1", TEST, anotherTest);

    assertEquals("Actual DB summary does not match that of the expected", expectedSummary, actualSummary);
  }

  @Test
  public void canGetInfo() throws IOException {
    db.getSet(TEST).add(TEST);

    String actualInfo = db.info(TEST);
    // JSON
    String expectedInfo = "TEST - Set - 1";

    assertEquals("Actual DB structure info does not match that of the expected", expectedInfo, actualInfo);
  }

  @Test(expected = IllegalStateException.class)
  public void cantGetInfoFromNonexistentDBStructureName() throws IOException {
    db.info(TEST);
  }

  @After
  public void tearDown() throws IOException {
    db.clear();
    db.close();
  }
}
