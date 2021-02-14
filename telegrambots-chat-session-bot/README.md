<div align="center">

[![Build Status](https://travis-ci.org/rubenlagus/TelegramBots.svg?branch=master)](https://travis-ci.org/rubenlagus/TelegramBots)
[![Jitpack](https://jitpack.io/v/rubenlagus/TelegramBots.svg)](https://jitpack.io/#rubenlagus/TelegramBots)
[![Telegram](http://trellobot.doomdns.org/telegrambadge.svg)](https://telegram.me/JavaBotsApi)

</div>

Usage
-----

**Maven**

```xml
    <dependency>
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots-chat-session-bot</artifactId>
        <version>5.0.1</version>
    </dependency>
```

**Gradle**

```gradle
    implementation 'org.telegram:telegrambots-chat-session-bot:5.0.1'
```

Motivation
----------
Implementation of bot dialogs require saving some data about current state of conversation.
That brings us to idea of chat session management.

How to use
----------
`Chat session bot` was implemented by using [`Shiro Apache`](https://shiro.apache.org/) session manager.
That allow to manage and store sessions.


To create default Long Polling Session Bot with in-memory store,
you need simply implement `TelegramLongPollingSessionBot`
```java
public class ExampleBotWithSession extends TelegramLongPollingSessionBot {

    @Override
    public void onUpdateReceived(Update update, Optional<Session> optionalSession) {
        //Do some action with update and session        
    }

    @Override
    public String getBotUsername() {
        return "ExampleBotWithSessionBot";
    }

    @Override
    public String getBotToken() {
        return "1234";
    }
}
```

Where session is implementation of `org.apache.shiro.session.Session`