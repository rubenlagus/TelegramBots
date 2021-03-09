package org.telegram.abilitybots.api.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.telegram.abilitybots.api.bot.TestUtils.CREATOR;
import static org.telegram.abilitybots.api.bot.TestUtils.USER;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;

class MapDBContextTest {
  private static final String USERS = "USERS";
  private static final String USER_ID = "USER_ID";
  private static final String TEST = "TEST";

  private DBContext db;

  @BeforeEach
  void setUp() {
    db = offlineInstance("db");
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  void canRecoverVar() {
    Var<String> test = db.getVar(TEST);
    String val = "abilitybot";
    test.set(val);

    Object backup = db.backup();
    db.clear();
    // db.clear does not clear atomic variables
    // TODO: get clear to remove all non-collection variables in DB
    test.set("somevalue");
    boolean recovered = db.recover(backup);
    String recoveredVal = db.<String>getVar(TEST).get();

    assertTrue(recovered, "Could not recover JSON backup file");
    assertEquals(val, recoveredVal, "Could not properly recover val from Var in DB");
  }

  @Test
  void canRecoverDB() {
    Map<Long, User> users = db.getMap(USERS);
    Map<String, Long> userIds = db.getMap(USER_ID);
    users.put(CREATOR.getId(), CREATOR);
    users.put(USER.getId(), USER);
    userIds.put(CREATOR.getUserName(), CREATOR.getId());
    userIds.put(USER.getUserName(), USER.getId());

    db.getSet("AYRE").add(123123);
    Map<Long, User> originalUsers = newHashMap(users);
    String beforeBackupInfo = db.info(USERS);

    Object jsonBackup = db.backup();
    db.clear();
    boolean recovered = db.recover(jsonBackup);

    Map<Long, User> recoveredUsers = db.getMap(USERS);
    String afterRecoveryInfo = db.info(USERS);

    assertTrue(recovered, "Could not recover database successfully");
    assertEquals(beforeBackupInfo, afterRecoveryInfo, "Map info before and after recovery is different");
    assertEquals(originalUsers, recoveredUsers, "Map before and after recovery are not equal");
  }

  @Test
  void canFallbackDBIfRecoveryFails() {
    Set<User> users = db.getSet(USERS);
    users.add(CREATOR);
    users.add(USER);

    Set<User> originalSet = newHashSet(users);
    Object jsonBackup = db.backup();
    String corruptBackup = "!@#$" + jsonBackup;
    boolean recovered = db.recover(corruptBackup);

    Set<User> recoveredSet = db.getSet(USERS);

    assertFalse(recovered, "Recovery was successful from a CORRUPT backup");
    assertEquals(originalSet, recoveredSet, "Set before and after corrupt recovery are not equal");
  }

  @Test
  void canGetSummary() {
    String anotherTest = TEST + 1;
    db.getSet(TEST).add(TEST);
    db.getSet(anotherTest).add(anotherTest);

    String actualSummary = db.summary();
    // Name - Type - Number of "rows"
    String expectedSummary = format("%s - Set - 1\n%s - Set - 1", TEST, anotherTest);

    assertEquals(expectedSummary, actualSummary, "Actual DB summary does not match that of the expected");
  }

  @Test
  void canGetInfo() {
    db.getSet(TEST).add(TEST);

    String actualInfo = db.info(TEST);
    // JSON
    String expectedInfo = "TEST - Set - 1";

    assertEquals(expectedInfo, actualInfo, "Actual DB structure info does not match that of the expected");
  }

  @Test
  void cantGetInfoFromNonexistentDBStructureName() {
    Assertions.assertThrows(IllegalStateException.class, () -> db.info(TEST));
  }

  @Test
  void canGetAndSetVariables() {
    String varName = "somevar";
    Var<User> var = db.getVar(varName);
    var.set(CREATOR);
    db.commit();

    var = db.getVar(varName);
    assertEquals(var.get(), CREATOR);

    var.set(USER);
    db.commit();

    Var<User> changedVar = db.getVar(varName);
    assertEquals(changedVar.get(), USER);
  }
}
