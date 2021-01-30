# Telegram Bot Java Library
[![Telegram](/TelegramBots.svg)](https://telegram.me/JavaBotsApi)


[![Build Status](https://travis-ci.org/rubenlagus/TelegramBots.svg?branch=master)](https://travis-ci.org/rubenlagus/TelegramBots)
[![Jitpack](https://jitpack.io/v/rubenlagus/TelegramBots.svg)](https://jitpack.io/#rubenlagus/TelegramBots)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.telegram/telegrambots/badge.svg)](http://mvnrepository.com/artifact/org.telegram/telegrambots)
[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](https://github.com/rubenlagus/TelegramBots/blob/master/LICENSE)

A simple to use library to create Telegram Bots in Java

## Contributions
Feel free to fork this project, work on it and then make a pull request against **DEV** branch. Most of the times I will accept them if they add something valuable to the code.

Please, **DO NOT PUSH ANY TOKEN OR API KEY**, I will never accept a pull request with that content.

## Webhooks vs GetUpdates
Both ways are supported, but I recommend long polling method.

## Usage

Just import add the library to your project with one of these options:

  1. Using Maven Central Repository:

```xml
    <dependency>
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots</artifactId>
        <version>5.0.1</version>
    </dependency>
```

  2. Using Gradle: 

```gradle
    implementation 'org.telegram:telegrambots:5.0.1'
```

  3. Using Jitpack from [here](https://jitpack.io/#rubenlagus/TelegramBots/5.0.1)
  4. Download the jar(including all dependencies) from [here](https://mvnrepository.com/artifact/org.telegram/telegrambots/5.0.1)

In order to use Long Polling mode, just create your own bot extending `org.telegram.telegrambots.bots.TelegramLongPollingBot`.

If you like to use Webhook, extend `org.telegram.telegrambots.bots.TelegramWebhookBot`


Once done, you just need to create a `org.telegram.telegrambots.meta.TelegramBotsApi`and register your bots:

```java

    // Example taken from https://github.com/rubenlagus/TelegramBotsExample
    public class Main {
        public static void main(String[] args) {
            try {
                TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                telegramBotsApi.registerBot(new ChannelHandlers());
                telegramBotsApi.registerBot(new DirectionsHandlers());
                telegramBotsApi.registerBot(new RaeHandlers());
                telegramBotsApi.registerBot(new WeatherHandlers());
                telegramBotsApi.registerBot(new TransifexHandlers());
                telegramBotsApi.registerBot(new FilesHandlers());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

```

For detailed explanation, visite our [How To](https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started) (thanks Clevero)


## Example bots
Open them and send them */help* command to get some information about their capabilities:

https://telegram.me/weatherbot (**Use custom keyboards**)

https://telegram.me/directionsbot (**Basic messages**)

https://telegram.me/filesbot (**Send files by file_id**)

https://telegram.me/TGlanguagesbot (**Send files uploding them**)

https://telegram.me/RaeBot (**Inline support**)

https://telegram.me/SnowcrashBot (**Webhook support**)

You can see code for those bots at [TelegramBotsExample](https://github.com/rubenlagus/TelegramBotsExample) project.

## Telegram Bot API
This library use [Telegram bot API](https://core.telegram.org/bots), you can find more information following the link.

## Questions or Suggestions
Feel free to create issues [here](https://github.com/rubenlagus/TelegramBots/issues) as you need or join the [chat](https://telegram.me/JavaBotsApi)

## Powered by Intellij
<p align="center">
   <a href="https://www.jetbrains.com/?from=TelegramBots"><img src="jetbrains.png" width="75"></a>
</p>


## License 
MIT License

Copyright (c) 2016 Ruben Bermudez

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
