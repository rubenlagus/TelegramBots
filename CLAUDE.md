C# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java library for creating Telegram bots. It's a multi-module Maven project that provides tools for building both long polling and webhook-based Telegram bots.

## Architecture and Structure

The project follows a modular architecture:
- `telegrambots-meta`: Core API objects and methods definitions
- `telegrambots-longpolling`: Implementation for long polling bots (recommended approach)
- `telegrambots-webhook`: Implementation for webhook bots
- `telegrambots-client`: HTTP client components
- `telegrambots-extensions`: Additional utilities and extensions
- `telegrambots-abilities`: Bot ability management

## Build Process

### Prerequisites
- Java 17+
- Maven 3.9.5+

### Building
```bash
# Clean, compile, and run tests
mvn clean compile test

# Install to local repository
mvn install -Dgpg.skip

# Create a jar with dependencies
mvn package
```

## Development Workflow

### Running Tests
To run specific tests:
```bash
# Run all tests in a module
mvn test -pl telegrambots-meta

# Run a specific test class
mvn test -Dtest=TestClassName -pl telegrambots-meta
```

### Common Development Tasks
1. **Adding new API methods**: Add to `telegrambots-meta` module, then implement in appropriate modules
2. **Creating new bot features**: Extend functionality in relevant modules
3. **Fixing bugs**: Identify the affected module and make changes accordingly

## Key Components

### Core Classes
- `TelegramBotsLongPollingApplication.java`: Main application entry point for long polling bots
- `BotSession.java`: Manages bot session state
- `LongPollingUpdateConsumer.java`: Interface for handling updates in long polling mode
- API objects in `telegrambots-meta` module: Represent Telegram API entities

### Key Concepts
- The library supports both long polling and webhook approaches for receiving updates
- Long polling is recommended by the project maintainers
- Uses Jackson for JSON serialization/deserialization
- Implements HTTP client using OkHttp

## Testing
Tests are written using JUnit 5 and Mockito. Test files are located in `src/test/java` directories within each module.