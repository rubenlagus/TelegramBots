Motivation
----------
After implementing your own bot via the basic API, you'll quickly realize how verbose it is. Once you get multiple commands up and running, your routing logic and handling per command start to become cumbersome.
Dealing with a basic API has its advantages and disadvantages. Obviously, there's nothing hidden. If it's there on Telegram, it's here in the Java API. However, can we do better than just a basic API?

When you want to implement a feature in your bot, you start asking these questions:

* The **WHO**?
    * Who is going to use this feature? Should they be allowed to use all the features?
* The **WHAT**?
    * Under what conditions should I allow this feature?
    * Should the message have a photo? A document? Oh, maybe a callback query?
* The **HOW**?
    * If my bot crashes, how can I resume my operation?
    * Should I utilize a DB?
    * How can I separate logic execution of different features?
    * How can I unit-test my feature outside of Telegram?

Every time you write a command or a feature, you will need to answer these questions and ensure that your feature logic works.

Ability
----------------------
Simply put, the abilities module was developed to make your life easier. Whether you're counting numbers, fetching images or handling large data, AbilityBot is here to augment your development.

The AbilityBot abstraction intends to provide the following:
* New feature is a new **Ability**, a new method - no fuss, zero overhead, no cross-code with other features
* Argument length on a command is as easy as changing a single integer
* Privacy settings per Ability - access levels to Abilities! User | Admin | Creator
* Embedded database - available for every declared ability
* Proxy sender interface - enhances testability; accurate results pre-release

Alongside these exciting core features of the AbilityBot, the following have been introduced:
* The bot automatically maintains an up-to-date set of all the users who have contacted the bot
* Backup and recovery for the DB
* Ban and unban users from accessing your bots
* Promote and demote users as bot administrators

Abstraction
--------------

The AbilityBot abstraction defines a new object, named **Ability**. An ability combines conditions, flags, action, post-action and replies.
As an example, here is a code-snippet of an ability that creates a ***/hello*** command:

```java
public Ability sayHelloWorld() {
    return Ability
              .builder()
              .name("hello")
              .info("says hello world!")
              .input(0)
              .locality(USER)
              .privacy(ADMIN)
              .action(ctx -> silent.send("Hello world!", ctx.chatId()))
              .post(ctx -> silent.send("Bye world!", ctx.chatId()))
              .build();
}
```
Here is a breakdown of the above code snippet:
* *.name()* - the name of the ability (essentially, this is the command)
* *.info()* - provides information for the command
    * More on this later, but it basically centralizes command information in-code.
* *.input()* - the number of input arguments needed, 0 is for do-not-care
* *.locality()* - this answers where you want the ability to be available
    * In GROUP, USER private chats or ALL (both)
* *.privacy()* - this answers who you want to access your ability
    * CREATOR, ADMIN, or everyone as PUBLIC
* *.action()* - the feature logic resides here (a lambda function that takes a *MessageContext*)
    * *MessageContext* provides fast accessors for the **chatId**, **user** and the underlying **update**. It also conforms to the specifications of the basic API.
* *.post()* - the logic executed **after** your main action finishes execution

The `silent` object is created with every AbilityBot. It provides helper and utility functions that execute "silently". In this context, silent execution of bot API methods are ones that don't throw an exception. However, all methods in silent return an Optional object. If an exception occurs, the optional would be empty. The developer would still be able to 
manage errors by checking for the presence of the optional `.isPresent()`. This decreases verboseness while still being able to execute routines correctly.
Do note that:
* You can still access the bot's methods and functions inside the lambda function in your action definition. That includes all the DefaultAbsSender methods execute, executeAsync, setChatPhoto, etc....
* `silent` uses another accessible object named `sender`. Refer to [[Bot Testing|Bot-Testing]] for the main use case of sender as an interface to all bot methods.

With abilities, you can specify the context of the feature. If you only want the command to be available for groups, then you can set `.locality(GROUP)`. If it is a very sensitive command that only admins should have access to, then set `.privacy(ADMIN)`.
This allows for abilities with protection guarantees on who can use it and where it can be used.

All abilities have access to the following important methods.
* `users()` - Returns a map of ID -> User
* `userIds()` - Returns a map of Username -> ID
* `blacklist()` - Returns a set of IDs of banned users
* `admins()` - Returns a set of IDs of bot administrators

`users()` and `userIds()` accumulate data of all the users who have contacted your bot. Even when a user changes some information (like his or her nickname), the bot will be able to detect the change and update its DB accordingly.

The following is a snippet of how this ability would look like with the plain basic API. 
```java
   @Override
   public void onUpdateReceived(Update update) {
       // Global checks...
       // Switch, if, logic to route to hello world method
       // Execute method
   }

   public void sayHelloWorld(Update update) {
       if (!update.hasMessage() || !update.getMessage().isUserMessage() || !update.getMessage().hasText() || update.getMessage.getText().isEmpty())
           return;
       User maybeAdmin = update.getMessage().getFrom();
       /* Query DB for if the user is an admin, can be SQL, Reddis, Ignite, etc...
          If user is not an admin, then return here.
       */

       SendMessage snd = new SendMessage();
       snd.setChatId(update.getMessage().getChatId());
       snd.setText("Hello world!");

       try {
           execute(snd);
       } catch (TelegramApiException e) {
           BotLogger.error("Could not send message", TAG, e);
       }
   }
```

I will leave you the choice to decide between the two snippets as to which is more **readable**, **writable** and **testable**.