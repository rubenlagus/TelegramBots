package org.telegram.abilitybots.api.bot;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.abilitybots.api.util.Pair;
import org.telegram.abilitybots.api.util.Trio;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.Optional.empty;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.telegram.abilitybots.api.bot.DefaultBot.FIRST_REPLY_KEY_MESSAGE;
import static org.telegram.abilitybots.api.bot.DefaultBot.SECOND_REPLY_KEY_MESSAGE;
import static org.telegram.abilitybots.api.bot.DefaultBot.getDefaultBuilder;
import static org.telegram.abilitybots.api.bot.TestUtils.CREATOR;
import static org.telegram.abilitybots.api.bot.TestUtils.USER;
import static org.telegram.abilitybots.api.bot.TestUtils.mockContext;
import static org.telegram.abilitybots.api.bot.TestUtils.mockFullUpdate;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;
import static org.telegram.abilitybots.api.objects.Flag.DOCUMENT;
import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Locality.GROUP;
import static org.telegram.abilitybots.api.objects.MessageContext.newContext;
import static org.telegram.abilitybots.api.objects.Privacy.ADMIN;
import static org.telegram.abilitybots.api.objects.Privacy.GROUP_ADMIN;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class AbilityBotTest {
  // Messages
  private static final String RECOVERY_MESSAGE = "I am ready to receive the backup file. Please reply to this message with the backup file attached.";
  private static final String RECOVER_SUCCESS = "I have successfully recovered.";

  private static final String[] EMPTY_ARRAY = {};
  private static final long GROUP_ID = 10L;
  private static final String TEST = "test";
  private static final String[] TEXT = {TEST};

  private DefaultBot bot;
  private DefaultAbilities defaultAbs;
  private DBContext db;
  private MessageSender sender;
  private SilentSender silent;

  @BeforeEach
  void setUp() {
    db = offlineInstance("db");
    bot = new DefaultBot(EMPTY, EMPTY, db);
    bot.onRegister();
    defaultAbs = new DefaultAbilities(bot);

    sender = mock(MessageSender.class);
    silent = mock(SilentSender.class);

    bot.sender = sender;
    bot.silent = silent;
  }

  @AfterEach
  void tearDown() throws IOException {
    db.clear();
    db.close();
  }

  @Test
  void sendsPrivacyViolation() {
    Update update = mockFullUpdate(bot, USER, "/admin");

    bot.onUpdateReceived(update);

    verify(silent, times(1)).send("Sorry, you don't have the required access level to do that.", USER.getId());
  }

  @Test
  void sendsLocalityViolation() {
    Update update = mockFullUpdate(bot, USER, "/group");

    bot.onUpdateReceived(update);

    verify(silent, times(1)).send(format("Sorry, %s-only feature.", "group"), USER.getId());

  }

  @Test
  void sendsInputArgsViolation() {
    Update update = mockFullUpdate(bot, USER, "/count 1 2 3");

    bot.onUpdateReceived(update);

    verify(silent, times(1)).send(format("Sorry, this feature requires %d additional inputs.", 4), USER.getId());
  }

  @Test
  void canProcessRepliesIfSatisfyRequirements() {
    Update update = mockFullUpdate(bot, USER, "must reply");

    // False means the update was not pushed down the stream since it has been consumed by the reply
    assertFalse(bot.filterReply(update));
    verify(silent, times(1)).send("reply", USER.getId());
  }

  @Test
  void canProcessUpdatesWithoutUserInfo() {
    Update update = mock(Update.class);
    // At the moment, only poll updates carry no user information
    when(update.hasPoll()).thenReturn(true);

    bot.onUpdateReceived(update);
  }

  @Test
  void getUserHasAllMethodsDefined() {
    Arrays.stream(Update.class.getMethods())
        // filter to all these methods of hasXXX (hasPoll, hasMessage, etc...)
        .filter(method -> method.getName().startsWith("has"))
        // Gotta filter out hashCode
        .filter(method -> method.getReturnType().getName().equals("boolean"))
        .forEach(method -> {
          Update update = mock(Update.class);
          try {
            // Mock the method and make sure it returns true so that it gets processed by the following method
            when(method.invoke(update)).thenReturn(true);
            // Call the getUser function, throws an IllegalStateException if there's an update that can't be processed
            AbilityUtils.getUser(update);
          } catch (IllegalStateException e) {
            throw new RuntimeException(
                format("Found an update variation that is not handled by the getUser util method [%s]", method.getName()), e);
          } catch (NullPointerException | ReflectiveOperationException e) {
            // This is fine, the mock isn't complete and we're only
            // looking for IllegalStateExceptions thrown by the method
          }
        });
  }

  @Test
  void getChatIdCanHandleAllKindsOfUpdates() {
    handlesAllUpdates(AbilityUtils::getUser);
  }

  @Test
  void getUserCanHandleAllKindsOfUpdates() {
    handlesAllUpdates(AbilityUtils::getChatId);
  }

  @Test
  void canBackupDB() throws TelegramApiException {
    MessageContext context = defaultContext();

    defaultAbs.backupDB().action().accept(context);
    deleteQuietly(new java.io.File("backup.json"));

    verify(sender, times(1)).sendDocument(any());
  }

  @Test
  void canReportStatistics() {
    MessageContext context = defaultContext();

    defaultAbs.reportStats().action().accept(context);

    verify(silent, times(1)).send("count: 0\nmustreply: 0", GROUP_ID);
  }

  @Test
  void canReportUpdatedStatistics() {
    Update upd1 = mockFullUpdate(bot, CREATOR, "/count 1 2 3 4");
    bot.onUpdateReceived(upd1);
    Update upd2 = mockFullUpdate(bot, CREATOR, "must reply");
    bot.onUpdateReceived(upd2);

    Mockito.reset(silent);

    Update statUpd = mockFullUpdate(bot, CREATOR, "/stats");
    bot.onUpdateReceived(statUpd);

    verify(silent, times(1)).send("count: 1\nmustreply: 1", CREATOR.getId());
  }

  @Test
  void canRecoverDB() throws TelegramApiException, IOException {
    Update update = mockBackupUpdate();
    Object backup = getDbBackup();
    java.io.File backupFile = createBackupFile(backup);

    // Support for null parameter matching since due to mocking API changes
    when(sender.downloadFile(ArgumentMatchers.<File>isNull())).thenReturn(backupFile);

    defaultAbs.recoverDB().replies().get(0).actOn(bot, update);

    verify(silent, times(1)).send(RECOVER_SUCCESS, GROUP_ID);
    assertEquals(db.getSet(TEST), newHashSet(TEST), "Bot recovered but the DB is still not in sync");
    assertTrue(backupFile.delete(), "Could not delete backup file");
  }

  @Test
  void canFilterOutReplies() {
    Update update = mock(Update.class);
    when(update.hasMessage()).thenReturn(false);

    assertTrue(bot.filterReply(update));
  }

  @Test
  void canDemote() {
    addUsers(USER);
    bot.admins().add(USER.getId());

    MessageContext context = defaultContext();

    defaultAbs.demoteAdmin().action().accept(context);

    Set<Long> actual = bot.admins();
    Set<Long> expected = emptySet();
    assertEquals(expected, actual, "Could not sudont super-admin");
  }

  @Test
  void canPromote() {
    addUsers(USER);

    MessageContext context = defaultContext();

    defaultAbs.promoteAdmin().action().accept(context);

    Set<Long> actual = bot.admins();
    Set<Long> expected = newHashSet(USER.getId());
    assertEquals(expected, actual, "Could not sudo user");
  }

  @Test
  void canBanUser() {
    addUsers(USER);
    MessageContext context = defaultContext();

    defaultAbs.banUser().action().accept(context);

    Set<Long> actual = bot.blacklist();
    Set<Long> expected = newHashSet(USER.getId());
    assertEquals(expected, actual, "The ban was not emplaced");
  }

  @Test
  void canUnbanUser() {
    addUsers(USER);
    bot.blacklist().add(USER.getId());

    MessageContext context = defaultContext();

    defaultAbs.unbanUser().action().accept(context);

    Set<Long> actual = bot.blacklist();
    Set<Long> expected = newHashSet();
    assertEquals(expected, actual, "The ban was not lifted");
  }

  @NotNull
  private MessageContext defaultContext() {
    return mockContext(CREATOR, GROUP_ID, USER.getUserName());
  }

  @Test
  void cannotBanCreator() {
    addUsers(USER, CREATOR);
    MessageContext context = mockContext(USER, GROUP_ID, CREATOR.getUserName());

    defaultAbs.banUser().action().accept(context);

    Set<Long> actual = bot.blacklist();
    Set<Long> expected = newHashSet(USER.getId());
    assertEquals(expected, actual, "Impostor was not added to the blacklist");
  }

  private void addUsers(User... users) {
    Arrays.stream(users).forEach(user -> {
      bot.users().put(user.getId(), user);
      bot.userIds().put(user.getUserName().toLowerCase(), user.getId());
    });
  }

  @Test
  void creatorCanClaimBot() {
    MessageContext context = mockContext(CREATOR, GROUP_ID);

    defaultAbs.claimCreator().action().accept(context);

    Set<Long> actual = bot.admins();
    Set<Long> expected = newHashSet(CREATOR.getId());
    assertEquals(expected, actual, "Creator was not properly added to the super admins set");
  }

  @Test
  void bannedCreatorPassesBlacklistCheck() {
    bot.blacklist().add(CREATOR.getId());
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    User user = mock(User.class);

    mockUser(update, message, user);

    boolean notBanned = bot.checkBlacklist(update);
    assertTrue(notBanned, "Creator is banned");
  }

  @Test
  void canAddUser() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);

    mockAlternateUser(update, message, USER);

    bot.addUser(update);

    Map<String, Long> expectedUserIds = ImmutableMap.of(USER.getUserName(), USER.getId());
    Map<Long, User> expectedUsers = ImmutableMap.of(USER.getId(), USER);
    assertEquals(expectedUserIds, bot.userIds(), "User was not added");
    assertEquals(expectedUsers, bot.users(), "User was not added");
  }

  @Test
  void canEditUser() {
    addUsers(USER);
    Update update = mock(Update.class);
    Message message = mock(Message.class);

    String newUsername = USER.getUserName() + "-test";
    String newFirstName = USER.getFirstName() + "-test";
    String newLastName = USER.getLastName() + "-test";
    long sameId = USER.getId();
    User changedUser = new User(sameId, newFirstName, false, newLastName, newUsername, "en", false, false, false, false, false);

    mockAlternateUser(update, message, changedUser);

    bot.addUser(update);

    Map<String, Long> expectedUserIds = ImmutableMap.of(changedUser.getUserName(), changedUser.getId());
    Map<Long, User> expectedUsers = ImmutableMap.of(changedUser.getId(), changedUser);
    assertEquals(bot.userIds(), expectedUserIds, "User was not properly edited");
    assertEquals(bot.users(), expectedUsers, "User was not properly edited");
  }

  @Test
  void canValidateAbility() {
    Trio<Update, Ability, String[]> invalidPair = Trio.of(null, null, null);
    Ability validAbility = getDefaultBuilder().build();
    Trio<Update, Ability, String[]> validPair = Trio.of(null, validAbility, null);

    assertFalse(bot.validateAbility(invalidPair), "Bot can't validate ability properly");
    assertTrue(bot.validateAbility(validPair), "Bot can't validate ability properly");
  }

  @Test
  void canCheckInput() {
    Update update = mockFullUpdate(bot, USER, "/something");
    Ability abilityWithOneInput = getDefaultBuilder()
        .build();
    Ability abilityWithZeroInput = getDefaultBuilder()
        .input(0)
        .build();

    Trio<Update, Ability, String[]> trioOneArg = Trio.of(update, abilityWithOneInput, TEXT);
    Trio<Update, Ability, String[]> trioZeroArg = Trio.of(update, abilityWithZeroInput, TEXT);

    assertTrue(bot.checkInput(trioOneArg), "Unexpected result when applying token filter");

    trioOneArg = Trio.of(update, abilityWithOneInput, addAll(TEXT, TEXT));
    assertFalse(bot.checkInput(trioOneArg), "Unexpected result when applying token filter");

    assertTrue(bot.checkInput(trioZeroArg), "Unexpected result  when applying token filter");

    trioZeroArg = Trio.of(update, abilityWithZeroInput, EMPTY_ARRAY);
    assertTrue(bot.checkInput(trioZeroArg), "Unexpected result when applying token filter");
  }

  @Test
  void canCheckPrivacy() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    User user = mock(User.class);
    Ability publicAbility = getDefaultBuilder().privacy(PUBLIC).build();
    Ability groupAdminAbility = getDefaultBuilder().privacy(GROUP_ADMIN).build();
    Ability adminAbility = getDefaultBuilder().privacy(ADMIN).build();
    Ability creatorAbility = getDefaultBuilder().privacy(Privacy.CREATOR).build();

    Trio<Update, Ability, String[]> publicTrio = Trio.of(update, publicAbility, TEXT);
    Trio<Update, Ability, String[]> groupAdminTrio = Trio.of(update, groupAdminAbility, TEXT);
    Trio<Update, Ability, String[]> adminTrio = Trio.of(update, adminAbility, TEXT);
    Trio<Update, Ability, String[]> creatorTrio = Trio.of(update, creatorAbility, TEXT);

    mockUser(update, message, user);

    assertTrue(bot.checkPrivacy(publicTrio), "Unexpected result when checking for privacy");
    assertFalse(bot.checkPrivacy(groupAdminTrio), "Unexpected result when checking for privacy");
    assertFalse(bot.checkPrivacy(adminTrio), "Unexpected result when checking for privacy");
    assertFalse(bot.checkPrivacy(creatorTrio), "Unexpected result when checking for privacy");
  }

  @Test
  void canValidateGroupAdminPrivacy() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    User user = mock(User.class);
    Ability groupAdminAbility = getDefaultBuilder().privacy(GROUP_ADMIN).build();

    Trio<Update, Ability, String[]> groupAdminTrio = Trio.of(update, groupAdminAbility, TEXT);

    mockUser(update, message, user);
    when(message.isGroupMessage()).thenReturn(true);

    ChatMember member = mock(ChatMember.class);
    when(member.getStatus()).thenReturn(ChatMemberAdministrator.STATUS);
    when(member.getUser()).thenReturn(user);

    when(silent.execute(any(GetChatAdministrators.class))).thenReturn(Optional.of(newArrayList(member)));

    assertTrue(bot.checkPrivacy(groupAdminTrio), "Unexpected result when checking for privacy");
  }

  @Test
  void canRestrictNormalUsersFromGroupAdminAbilities() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    User user = mock(User.class);
    Ability groupAdminAbility = getDefaultBuilder().privacy(GROUP_ADMIN).build();

    Trio<Update, Ability, String[]> groupAdminTrio = Trio.of(update, groupAdminAbility, TEXT);

    mockUser(update, message, user);
    when(message.isGroupMessage()).thenReturn(true);

    when(silent.execute(any(GetChatAdministrators.class))).thenReturn(empty());

    assertFalse(bot.checkPrivacy(groupAdminTrio), "Unexpected result when checking for privacy");
  }

  @Test
  void canBlockAdminsFromCreatorAbilities() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    User user = mock(User.class);
    Ability creatorAbility = getDefaultBuilder().privacy(Privacy.CREATOR).build();

    Trio<Update, Ability, String[]> creatorTrio = Trio.of(update, creatorAbility, TEXT);

    bot.admins().add(USER.getId());
    mockUser(update, message, user);

    assertFalse(bot.checkPrivacy(creatorTrio), "Unexpected result when checking for privacy");
  }

  @Test
  void canCheckLocality() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    User user = mock(User.class);
    Ability allAbility = getDefaultBuilder().locality(ALL).build();
    Ability userAbility = getDefaultBuilder().locality(Locality.USER).build();
    Ability groupAbility = getDefaultBuilder().locality(GROUP).build();

    Trio<Update, Ability, String[]> publicTrio = Trio.of(update, allAbility, TEXT);
    Trio<Update, Ability, String[]> userTrio = Trio.of(update, userAbility, TEXT);
    Trio<Update, Ability, String[]> groupTrio = Trio.of(update, groupAbility, TEXT);

    mockUser(update, message, user);
    when(message.isUserMessage()).thenReturn(true);

    assertTrue(bot.checkLocality(publicTrio), "Unexpected result when checking for locality");
    assertTrue(bot.checkLocality(userTrio), "Unexpected result when checking for locality");
    assertFalse(bot.checkLocality(groupTrio), "Unexpected result when checking for locality");
  }

  @Test
  void canRetrieveContext() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    Ability ability = getDefaultBuilder().build();
    Trio<Update, Ability, String[]> trio = Trio.of(update, ability, TEXT);

    when(message.getChatId()).thenReturn(GROUP_ID);
    mockUser(update, message, USER);

    Pair<MessageContext, Ability> actualPair = bot.getContext(trio);
    Pair<MessageContext, Ability> expectedPair = Pair.of(newContext(update, USER, GROUP_ID, bot, TEXT), ability);

    assertEquals(expectedPair, actualPair, "Unexpected result when fetching for context");
  }

  @Test
  void defaultGlobalFlagIsTrue() {
    Update update = mock(Update.class);
    assertTrue(bot.checkGlobalFlags(update), "Unexpected result when checking for the default global flags");
  }

  @SuppressWarnings({"NumericOverflow", "divzero"})
  @Test
  void canConsumeUpdate() {
    Ability ability = getDefaultBuilder()
        .action((context) -> {
          int x = 1 / 0;
        }).build();
    MessageContext context = mock(MessageContext.class);

    Pair<MessageContext, Ability> pair = Pair.of(context, ability);

    Assertions.assertThrows(ArithmeticException.class, () -> bot.consumeUpdate(pair));
  }

  @Test
  void canFetchAbility() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);

    String text = "/test";
    when(update.hasMessage()).thenReturn(true);
    when(update.getMessage()).thenReturn(message);
    when(update.getMessage().hasText()).thenReturn(true);
    when(message.getText()).thenReturn(text);

    Trio<Update, Ability, String[]> trio = bot.getAbility(update);

    Ability expected = bot.testAbility();
    Ability actual = trio.b();

    assertEquals(expected, actual, "Wrong ability was fetched");
  }

  @Test
  void canFetchAbilityCaseInsensitive() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);

    String text = "/tESt";
    when(update.hasMessage()).thenReturn(true);
    when(update.getMessage()).thenReturn(message);
    when(update.getMessage().hasText()).thenReturn(true);
    when(message.getText()).thenReturn(text);

    Trio<Update, Ability, String[]> trio = bot.getAbility(update);

    Ability expected = bot.testAbility();
    Ability actual = trio.b();

    assertEquals(expected, actual, "Wrong ability was fetched");
  }

  @Test
  void canFetchDefaultAbility() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);

    String text = "test tags";
    when(update.getMessage()).thenReturn(message);
    when(message.getText()).thenReturn(text);

    Trio<Update, Ability, String[]> trio = bot.getAbility(update);

    Ability expected = bot.defaultAbility();
    Ability actual = trio.b();

    assertEquals(expected, actual, "Wrong ability was fetched");
  }

  @Test
  void canCheckAbilityFlags() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);

    when(update.hasMessage()).thenReturn(true);
    when(update.getMessage()).thenReturn(message);
    when(message.hasDocument()).thenReturn(false);
    when(message.hasText()).thenReturn(true);

    Ability documentAbility = getDefaultBuilder().flag(DOCUMENT, MESSAGE).build();
    Ability textAbility = getDefaultBuilder().flag(Flag.TEXT, MESSAGE).build();

    Trio<Update, Ability, String[]> docTrio = Trio.of(update, documentAbility, TEXT);
    Trio<Update, Ability, String[]> textTrio = Trio.of(update, textAbility, TEXT);

    assertFalse(bot.checkMessageFlags(docTrio), "Unexpected result when checking for message flags");
    assertTrue(bot.checkMessageFlags(textTrio), "Unexpected result when checking for message flags");
  }

  @Test
  void canReportCommands() {
    MessageContext context = mockContext(USER, GROUP_ID);

    defaultAbs.reportCommands().action().accept(context);

    verify(silent, times(1)).send("default - dis iz default command", GROUP_ID);
  }

  @Test
  void canPrintCommandsBasedOnPrivacy() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);

    when(update.hasMessage()).thenReturn(true);
    when(update.getMessage()).thenReturn(message);
    when(message.hasText()).thenReturn(true);
    MessageContext creatorCtx = newContext(update, CREATOR, GROUP_ID, bot);

    defaultAbs.commands().action().accept(creatorCtx);

    String expected = "PUBLIC\n/commands\n/count\n/default - dis iz default command\n/group\n/test\nADMIN\n/admin\n/ban\n/demote\n/promote\n/stats\n/unban\nCREATOR\n/backup\n/claim\n/recover\n/report";
    verify(silent, times(1)).send(expected, GROUP_ID);
  }

  @Test
  void printsOnlyPublicCommandsForNormalUser() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);

    when(update.hasMessage()).thenReturn(true);
    when(update.getMessage()).thenReturn(message);
    when(message.hasText()).thenReturn(true);

    MessageContext userCtx = newContext(update, USER, GROUP_ID, bot);

    defaultAbs.commands().action().accept(userCtx);

    String expected = "PUBLIC\n/commands\n/count\n/default - dis iz default command\n/group\n/test";
    verify(silent, times(1)).send(expected, GROUP_ID);
  }

  @Test
  void canProcessChannelPosts() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    when(message.getChatId()).thenReturn(1L);

    when(update.getChannelPost()).thenReturn(message);
    when(update.hasChannelPost()).thenReturn(true);

    bot.onUpdateReceived(update);

    String expected = "test channel post";
    verify(silent, times(1)).send(expected, 1);
  }

  @Test
  void canProcessRepliesRegisteredInCollection() {
    Update firstUpdate = mock(Update.class);
    Message firstMessage = mock(Message.class);
    when(firstMessage.getText()).thenReturn(FIRST_REPLY_KEY_MESSAGE);
    when(firstMessage.getChatId()).thenReturn(1L);

    Update secondUpdate = mock(Update.class);
    Message secondMessage = mock(Message.class);
    when(secondMessage.getText()).thenReturn(SECOND_REPLY_KEY_MESSAGE);
    when(secondMessage.getChatId()).thenReturn(1L);

    mockUser(firstUpdate, firstMessage, USER);
    mockUser(secondUpdate, secondMessage, USER);


    bot.onUpdateReceived(firstUpdate);
    bot.onUpdateReceived(secondUpdate);

    verify(silent, times(2)).send(anyString(), anyLong());
    verify(silent, times(1)).send("first reply answer", 1);
    verify(silent, times(1)).send("second reply answer", 1);
  }

  private void handlesAllUpdates(Consumer<Update> utilMethod) {
    Arrays.stream(Update.class.getMethods())
        // filter to all these methods of hasXXX (hasPoll, hasMessage, etc...)
        .filter(method -> method.getName().startsWith("has"))
        // Gotta filter out hashCode
        .filter(method -> method.getReturnType().getName().equals("boolean"))
        .forEach(method -> {
          Update update = mock(Update.class);
          try {
            // Mock the method and make sure it returns true so that it gets processed by the following method
            when(method.invoke(update)).thenReturn(true);
            // Call the function, throws an IllegalStateException if there's an update that can't be processed
            utilMethod.accept(update);
          } catch (IllegalStateException e) {
            throw new RuntimeException(
                format("Found an update variation that is not handled by the getChatId util method [%s]", method.getName()), e);
          } catch (NullPointerException | ReflectiveOperationException e) {
            // This is fine, the mock isn't complete and we're only
            // looking for IllegalStateExceptions thrown by the method
          }
        });
  }

  private void mockUser(Update update, Message message, User user) {
    when(update.hasMessage()).thenReturn(true);
    when(update.getMessage()).thenReturn(message);
    when(message.getFrom()).thenReturn(user);
  }

  private void mockAlternateUser(Update update, Message message, User user) {
    when(message.getFrom()).thenReturn(user);
    when(update.hasMessage()).thenReturn(true);
    when(update.getMessage()).thenReturn(message);
  }

  private Update mockBackupUpdate() {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    Message botMessage = mock(Message.class);
    Document document = mock(Document.class);

    when(document.getFileId()).thenReturn("FAKEFILEID");
    when(message.getFrom()).thenReturn(CREATOR);
    when(update.getMessage()).thenReturn(message);
    when(message.getDocument()).thenReturn(document);
    when(botMessage.getText()).thenReturn(RECOVERY_MESSAGE);
    when(message.isReply()).thenReturn(true);
    when(update.hasMessage()).thenReturn(true);
    when(message.hasDocument()).thenReturn(true);
    when(message.getReplyToMessage()).thenReturn(botMessage);
    when(message.getChatId()).thenReturn(GROUP_ID);
    return update;
  }

  private Object getDbBackup() {
    db.getSet(TEST).add(TEST);
    Object backup = db.backup();
    db.clear();
    return backup;
  }

  private java.io.File createBackupFile(Object backup) throws IOException {
    java.io.File backupFile = new java.io.File(TEST);
    BufferedWriter writer = Files.newWriter(backupFile, Charset.defaultCharset());
    writer.write(backup.toString());
    writer.flush();
    writer.close();
    return backupFile;
  }
}
