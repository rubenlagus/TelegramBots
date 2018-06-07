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
        <artifactId>telegrambots-chat-session-bot</artifactId>
        <version>3.6.1</version>
    </dependency>
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