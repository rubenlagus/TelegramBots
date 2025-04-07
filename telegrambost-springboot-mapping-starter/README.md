# Telegram Bot Auto Configuration

## Overview
This module provides auto-configuration for integrating Telegram Long Polling Bots into a Spring Boot application. It simplifies the setup and management of Telegram bots by automatically registering necessary beans and components.

## Features
- Auto-configuration for Telegram Long Polling Bots
- Spring-managed `SpringLongPollingBot` instances
- Centralized `BotRequestProcessor` for handling incoming updates
- Custom annotation-based bot request mapping (`@BotRequestMapping`)
- Integration with `OkHttpClient` for network communication

## Installation
### Maven
Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambost-springboot-mapping-starter</artifactId>
    <version>${current.version}</version>
</dependency>
```

### Gradle
Add the dependency to your `build.gradle`:

```gradle
dependencies {
    implementation 'org.telegram:telegrambost-springboot-mapping-starter:1.0.0'
}
```

## Configuration
### Properties
Define your bot token in `application.yml`:

```yaml
telegram:
  bot:
    token: YOUR_BOT_TOKEN
```

or in `application.properties`:

```properties
telegram.bot.token=YOUR_BOT_TOKEN
```

## Usage
### Defining a Bot Request Controller
To handle bot commands, define a class annotated with `@Component` and use `@BotRequestMapping`:

```java
import com.flux.spring.boot.telegram.mapping.annotation.BotRequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class BotRequestController {

    @BotRequestMapping("/start")
    public void startCommand(Update update) {
        log.info("/start command received from: {}", update.getMessage().getFrom().getUserName());
    }

    @BotRequestMapping("/help")
    public void helpCommand(Update update) {
        log.info("/help command received from: {}", update.getMessage().getFrom().getUserName());
    }
}
```

## Components
### `SpringLongPollingBot`
This interface is implemented as a Spring Bean and manages long polling updates.

### `BotRequestProcessor`
Handles incoming bot updates and routes them to appropriate handlers.

### `@BotRequestMapping`
Custom annotation for defining bot command handlers.

## Running the Application
Start the Spring Boot application as usual:
```sh
mvn spring-boot:run
```
or
```sh
./gradlew bootRun
```

Once the application is running, the bot will listen for incoming Telegram updates and handle commands accordingly.

## License
This project is licensed under the MIT License.
