# Testing
It is super important to be able to test your bot prior to "release". In this case, release would mean that you're presenting the bot to your designated audience. Nobody likes bots that are buggy, faulty and do clumsy actions.
As developers, we appreciate frameworks that provide an ease in testing. Of course, you might no tbe able to catch all bugs that can occur in production, but you'd be far more comfortable in releasing a bot that is well-tested.

## Limitations

The issue with the basic API is that all DefaultAbsSender methods (the bot methods you use to send message) are statically defined without interfacing. If you declare your bot and try to do some testing, you won't be able to know that you've executed a method... unless you actually execute it! As an example:
```java
public void sayHello() {
  SendMessage snd = new SendMessage();
  snd.setText("Hello!");
  snd.setChatId(123);
  
  try {
    // We want to test that we actually sent out this message with the contents "Hello!"
    execute(snd);
  } catch (TelegramApiException e) {}
}
```

This is how you would define a method that says hello in the basic API. How do you go around testing it? If you do attempt to Junit test this method, what will you be testing? If you change the method signature to return the string sent, then you can test the hello message content. However, can you test that you've actually `executed` the command?

## Mock Testing
*This section assumes you're familiar with mock testing. Mock testing is basically replacing a real object X with a fake object Y (a mock) of the same type. By doing that, you're able to test whether certain functions were executed.*

Obviously, you can't, but there's a twist to it. You can always mock the whole bot, but with that you're also mocking the method `sayHello` when you actually need its contents and code! We need to extract the bot-sending-specific-methods into their own interface and try to mock that interface instead.

## MessageSender Interface
All ability bots declare two utility objects.
### The Sender Object
The `sender` object is an implementation of the [MessageSender](../../telegrambots-abilities/src/main/java/org/telegram/abilitybots/api/sender/MessageSender.java) interface. The interface mirrors 
all the bot sending methods. A user can supply his own MessageSender, but the AbilityBot module specifies a [DefaultSender](../../telegrambots-abilities/src/main/java/org/telegram/abilitybots/api/sender/DefaultSender.java) As you might guess, the default sender is simply a proxy for the bot API methods.

### The Silent Object
The `silent` object is exactly like the sender object, but silent. Its methods return `Optional<T>`. On exception, it will be an empty optional. The sender object is provided to reduce verboseness of the code (reducing try-catch blocks with something more elegant).
 ## AbilityBot Testing
 Let's suppose that you have an ability that says "Hello World!" declared as such:
 ```java
public Ability saysHelloWorld() {
    return Ability.builder()
        .name("hello")
        .info("Says hello world!")
        .privacy(PUBLIC)
        .locality(ALL)
        .action(ctx -> {
          try{
            sender.execute(new SendMessage().setChatId(ctx.getChatId()).setText("Hello World!"));
          } catch (TelgramApiException e){}
        })
        .build();
  }
```

The test for this ability would be:

```java
  @Test
  public void canSayHelloWorld() {
    Update upd = new Update();
    // Create a new User - User is a class similar to Telegram User
    User user = new User(USER_ID, "Abbas", false, "Abou Daya", "addo37", null);
    // This is the context that you're used to, it is the necessary conumer item for the ability
    MessageContext context = MessageContext.newContext(upd, user, CHAT_ID);

    // We consume a context in the lamda declaration, so we pass the context to the action logic
    bot.saysHelloWorld().action().accept(context);

    // We verify that the silent sender was called only ONCE and sent Hello World to CHAT_ID
    // The silent sender here is a mock!
    Mockito.verify(silent, times(1)).send("Hello World!", CHAT_ID);
  }
```

The comments explain every step in the test. In a single assertion with Mockito, we assert that:
* We've sent the message once
* The message content was "Hello World!"
* The message was sent to a specific chat ID

There are some preparations involved before we can perform such a test. Here's the full code snippet for running this test:
```java
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.MapDBContext;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.*;

public class ExampleBotTest {
  public static final int USER_ID = 1337;
  public static final long CHAT_ID = 1337L;

  // Your bot handle here
  private ExampleBot bot;
  // Your sender here. Also you can create MessageSender
  private SilentSender silent;

  @Before
  public void setUp() {
    // Create your bot
    bot = new ExampleBot();
    // Call onRegister() to initialize abilities etc. 
    bot.onRegister();
    // Create a new sender as a mock
    silent = mock(SilentSender.class);
    // Set your bot silent sender to the mocked sender
    // THIS is the line that prevents your bot from communicating with Telegram servers when it's running its own abilities
    // All method calls will go through the mocked interface -> which would do nothing except logging the fact that you've called this function with the specific arguments
    // Create setter in your bot
    bot.setSilentSender(silent);
  }

  @Test
  public void canSayHelloWorld() {
    Update upd = new Update();
    // Create a new User - User is a class similar to Telegram User
    User user = new User(USER_ID, "Abbas", false, "Abou Daya", "addo37", null);
    // This is the context that you're used to, it is the necessary conumer item for the ability
    MessageContext context = MessageContext.newContext(upd, user, CHAT_ID);

    // We consume a context in the lamda declaration, so we pass the context to the action logic
    bot.saysHelloWorld().action().accept(context);

    // We verify that the silent sender was called only ONCE and sent Hello World to CHAT_ID
    // The silent sender here is a mock!
    Mockito.verify(silent, times(1)).send("Hello World!", CHAT_ID);
  }
}
```

## DB Abilities
What if the ability performs a DB interaction? We don't want testing procedures to modify the database of the bot.

This is where we differentiate between an online DB and an offline DB. The online DB is the default DB when the bot is instantiated. However, AbilityBot supplies a constructor that reveals a DBContext argument. We can supply another instance of a DB (an offline one) so that the tests don't modify our online DB.

In ExampleBot, we do:
```java
  public ExampleBot(DBContext db) {
    super(BOT_TOKEN, BOT_USERNAME, db);
  }
```

In ExampleBotTest:
```java
public class ExampleBotTest {
  ...
  
  private DBContext db;
  private MessageSender sender;

@Before
  public void setUp() {
    // Offline instance will get deleted at JVM shutdown
    db = MapDBContext.offlineInstance("test");
    bot = new ExampleBot(db);
    bot.onRegister();
    
    ...
  }
  ...

  // We should clear the DB after every test as such
  @After
  public void tearDown() {
    db.clear();
  }
}
```

## Silent Testing
As mentioned before, we also have another object that is able to send messages silently called `silent`. The constructor of the silent sender requires a MessageSender object. If your abilities use the `silent` object, be sure to:
```java
public class ExampleBotTest {
  ...
  private DBContext db;
  private MessageSender sender;

  @Before
  public void setUp() {
    bot = new ExampleBot(db);
    bot.onRegister();
    sender = mock(MessageSender.class);
    SilentSender silent = new SilentSender(sender);
    // Create setter in your bot 
    bot.setSilentSender(silent); 
    ...
  }
  
  ...
}
```

Do note that in your test assertions, don't use the silent object. Mocked assertion require the mock object. If you recall, the silent object uses the sender object, so your tests will still be correct if you're asserting on the `sender` object rather than the silent one.