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
        <artifactId>telegrambots-spring-boot-starter</artifactId>
        <version>5.0.1</version>
    </dependency>
```

**Gradle**

```gradle
    implementation 'org.telegram:telegrambots-spring-boot-starter:5.0.1'
```

Motivation
----------
If you are spring boot user it`s better to be in touch with spring starters. This module allows to register bots in spring context automatically and 
also use them as standard spring beans.

How to use
----------
Your main spring boot class should look like this:

```java
@SpringBootApplication
public class YourApplicationMainClass {

	public static void main(String[] args) {		
		SpringApplication.run(YourApplicationMainClass.class, args);
	}
}
```

After that your bot will look like:
```java
  //Standard Spring component annotation
  @Component
  public class YourBotName extends TelegramLongPollingBot {
    //Bot body.
  }
```
Also you could just implement LongPollingBot or WebHookBot interfaces. All this bots will be registered in context and connected to Telegram api.
