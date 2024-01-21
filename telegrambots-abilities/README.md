<div align="center">
  <img src="https://github.com/addo37/AbilityBots/blob/gh-pages/images/API%20BOT-03.png?raw=true" alt="abilitybots" width="200" height="200"/>

[![Build Status](https://travis-ci.org/rubenlagus/TelegramBots.svg?branch=master)](https://travis-ci.org/rubenlagus/TelegramBots)
[![Jitpack](https://jitpack.io/v/rubenlagus/TelegramBots.svg)](https://jitpack.io/#rubenlagus/TelegramBots)
[![JavaDoc](http://svgur.com/i/1Ex.svg)](https://addo37.github.io/AbilityBots/)
[![Telegram](http://trellobot.doomdns.org/telegrambadge.svg)](https://telegram.me/JavaBotsApi)
[![ghit.me](https://ghit.me/badge.svg?repo=rubenlagus/TelegramBots)](https://ghit.me/repo/rubenlagus/TelegramBots)

</div>

Usage
-----

**Maven**

```xml
    <dependency>
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots-abilities</artifactId>
        <version>6.9.7.0</version>
    </dependency>
```

**Gradle**

```gradle
    implementation 'org.telegram:telegrambots-abilities:6.9.7.0'
```

**JitPack** - [JitPack](https://jitpack.io/#rubenlagus/TelegramBots/v5.0.1)

**Plain imports** - [Here](https://github.com/rubenlagus/TelegramBots/releases/tag/v5.0.1)

Motivation
----------
Ever since I've started programming bots for Telegram, I've been using the Telegram Bot Java API. It's a basic and nicely done API that is a 1-to-1 translation of the HTTP API exposed by Telegram.

Dealing with a basic API has its advantages and disadvantages. Obviously, there's nothing hidden. If it's there on Telegram, it's here in the Java API.
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

Ability Bot Abstraction
-----------------------
After implementing my fifth bot using that API, I had had it with the amount of **boilerplate code** that was needed for every added feature. Methods were getting overly-complex and readability became subpar.
That is where the notion of another layer of abstraction (AbilityBot) began taking shape.

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
              .action(ctx -> sender.send("Hello world!", ctx.chatId()))
              .post(ctx -> sender.send("Bye world!", ctx.chatId()))
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

The following is a snippet of how this would look like with the plain basic API.

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
           sendMessage(snd);
       } catch (TelegramApiException e) {
           BotLogger.error("Could not send message", TAG, e);
       }
   }
```

I will leave you the choice to decide between the two snippets as to which is more **readable**, **writable** and **testable**.

***You can do so much more with abilities, besides plain commands. Head over to our [examples](#examples) to check out all of its features!***

Objective
---------
The AbilityBot abstraction intends to provide the following:
* New feature is a new **Ability**, a new method - no fuss, zero overhead, no cross-code with other features
* Argument length on a command is as easy as changing a single integer
* Privacy settings per Ability - access levels to Abilities! User | Admin | Creator
* Embedded database - available for every declared ability
* Proxy sender interface - enhances testability; accurate results pre-release

Alongside these exciting core features of the AbilityBot, the following have been introduced:
* The bot automatically maintains an up-to-date set of all the users who have contacted the bot
    * up-to-date: if a user changes their Username, First Name or Last Name, the bot updates the respective field in the embedded-DB
* Backup and recovery for the DB
    * Default implementation relies on JSON/Jackson
* Ban and unban users from accessing your bots
    * The bot will execute the shortest path to discard the update the next time they try to spam
* Promote and demote users as bot administrators
    * Allows admins to execute admin abilities

What's next?
------------
I am looking forward to:
* Provide a trigger to record metrics per ability
* Implement AsyncAbility
* Maintain integration with the latest updates on the basic API
* Enrich the bot with features requested by the community

Examples
-------------------
* [Example Bots](https://github.com/addo37/ExampleBots)

Do you have a project that uses **AbilityBots**? Let us know!

Support
-------
For issues and features, please use GitHub's [issues](https://github.com/rubenlagus/TelegramBots/issues) tab.

For quick feedback, chatting or just having fun, please come and join us in our Telegram Supergroup.

[![Telegram](http://trellobot.doomdns.org/telegrambadge.svg)](https://telegram.me/JavaBotsApi)

Credits
-------
This project would not have been made possible had it not been for [Ruben](https://github.com/rubenlagus)'s work with the [Telegram Bot Java API](https://github.com/rubenlagus/TelegramBots).
I strongly urge you to check out that project and implement a bot to have a sense of how the basic API feels like.
Ruben has done a great job in supplying a clear and straightforward API that conforms to Telegram's HTTP API.
There is also a chat for that API.