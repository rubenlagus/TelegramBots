package org.telegram.telegrambots.extensions.bots.commandbot;


import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.CommandState;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.UserActivity;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.UserActivityHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This class adds command functionality to the TelegramLongPollingBot
 *
 * @author Timo Schulz (Mit0x2)
 */
public abstract class TelegramLongPollingCommandBot extends TelegramLongPollingBot implements CommandBot, ICommandRegistry {

    private final UserActivityHandler userActivityHandler;
    private final CommandRegistry commandRegistry;

    /**
     * Creates a TelegramLongPollingCommandBot using default options
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @deprecated Use {@link #TelegramLongPollingCommandBot(String)}
     */
    @Deprecated
    public TelegramLongPollingCommandBot() {
        this(new DefaultBotOptions());
    }

    /**
     * Creates a TelegramLongPollingCommandBot with custom options and allowing commands with
     * usernames
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options     Bot options
     *
     * @deprecated Use {@link #TelegramLongPollingCommandBot(DefaultBotOptions, String)}
     */
    @Deprecated
    public TelegramLongPollingCommandBot(DefaultBotOptions options) {
        this(options, true);
    }

    /**
     * Creates a TelegramLongPollingCommandBot
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options                   Bot options
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     *
     * @deprecated Use {@link #TelegramLongPollingCommandBot(DefaultBotOptions, boolean, String)}
     */
    @Deprecated
    public TelegramLongPollingCommandBot(DefaultBotOptions options, boolean allowCommandsWithUsername) {
        super(options);
        this.userActivityHandler = null;
        this.commandRegistry = new CommandRegistry(allowCommandsWithUsername, this::getBotUsername);
    }

    /**
     * Creates a TelegramLongPollingCommandBot using default options
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param botToken Bot token
     */
    public TelegramLongPollingCommandBot(String botToken) {
        this(new DefaultBotOptions(), botToken);
    }

    /**
     * Creates a TelegramLongPollingCommandBot with custom options
     * and allowing commands with usernames
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options     Bot options
     * @param botToken    Bot token
     */
    public TelegramLongPollingCommandBot(DefaultBotOptions options, String botToken) {
        this(options, true, botToken);
    }

    /**
     * Creates a TelegramLongPollingCommandBot with custom options,
     * allowing commands with usernames and without user activity handler
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options                   Bot options
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     * @param botToken                  Bot token
     */
    public TelegramLongPollingCommandBot(DefaultBotOptions options, boolean allowCommandsWithUsername, String botToken) {
        this(options, allowCommandsWithUsername, botToken, null);
    }

    /**
     * Creates a TelegramLongPollingCommandBot with custom options,
     * allowing commands with usernames and user activity handler
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options                   Bot options
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     * @param botToken                  Bot token
     * @param userActivityHandler       user activity handler implementation
     *                                  for save command state between messages
     */
    public TelegramLongPollingCommandBot(DefaultBotOptions options,
                                         boolean allowCommandsWithUsername,
                                         String botToken,
                                         UserActivityHandler userActivityHandler) {
        super(options, botToken);
        this.userActivityHandler = userActivityHandler;
        this.commandRegistry = new CommandRegistry(allowCommandsWithUsername, this::getBotUsername);
    }

    @Override
    public final void onUpdateReceived(Update update) {
        // Only one (Message/CallbackQuery) can be present in any given update
        // https://core.telegram.org/bots/api#update
        if (update.hasMessage() || update.hasCallbackQuery()) {
            Message message = update.getMessage();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (message != null && !filter(message)) {
                if (message.isCommand()) {
                    if (userActivityHandler == null) {
                        if (!commandRegistry.executeCommand(this, message)) {
                            //we have received a not registered command, handle it as invalid
                            processInvalidCommandUpdate(update);
                        }
                    } else {
                        long userId = message.getFrom().getId();
                        UserActivity userActivity = new UserActivity();
                        userActivityHandler.saveUserActivity(userId, userActivity);
                        CommandState<?> resultState = commandRegistry.executeCommand(this, message, userActivity.getCommandState());
                        if (!checkCommandState(resultState, userId, userActivity)) {
                            processInvalidCommandUpdate(update);
                        }
                    }
                    return;
                } else if (userActivityHandler != null) {
                    long userId = message.getFrom().getId();
                    UserActivity userActivity = userActivityHandler.loadUserActivity(userId);
                    if (userActivity != null && validateUserActivity(userActivity)) {
                        CommandState<?> resultState = commandRegistry.executeCommand(this, message, userActivity.getCommandState());
                        if (!checkCommandState(resultState, userId, userActivity)) {
                            processInvalidCommandUpdate(update);
                        }
                    } else {
                        processInvalidCommandUpdate(update);
                    }
                    return;
                }
            } else if (callbackQuery != null && callbackQuery.getMessage() != null && userActivityHandler != null) {
                long userId = callbackQuery.getMessage().getFrom().getId();
                UserActivity userActivity = userActivityHandler.loadUserActivity(userId);
                if (userActivity != null && validateUserActivity(userActivity)) {
                    CommandState<?> resultState = commandRegistry.executeCommand(this, callbackQuery, userActivity.getCommandState());
                    if (!checkCommandState(resultState, userId, userActivity)) {
                        processInvalidCommandUpdate(update);
                    }
                } else {
                    processInvalidCommandUpdate(update);
                }
                return;
            }
        }
        processNonCommandUpdate(update);
    }

    @Override
    public final boolean register(IBotCommand botCommand) {
        return commandRegistry.register(botCommand);
    }

    @Override
    public final Map<IBotCommand, Boolean> registerAll(IBotCommand... botCommands) {
        return commandRegistry.registerAll(botCommands);
    }

    @Override
    public final boolean deregister(IBotCommand botCommand) {
        return commandRegistry.deregister(botCommand);
    }

    @Override
    public final Map<IBotCommand, Boolean> deregisterAll(IBotCommand... botCommands) {
        return commandRegistry.deregisterAll(botCommands);
    }

    @Override
    public final Collection<IBotCommand> getRegisteredCommands() {
        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        commandRegistry.registerDefaultAction(defaultConsumer);
    }

    @Override
    public final IBotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistry.getRegisteredCommand(commandIdentifier);
    }

    /**
     * @return Bot username
     */
    @Override
    public abstract String getBotUsername();

    private boolean checkCommandState(CommandState<?> commandState, long userId, UserActivity userActivity) {
        if (commandState != null) {
            if (commandState.getState() != null) {
                userActivity.setCommandState(commandState);
                userActivityHandler.saveUserActivity(userId, userActivity);
            } else {
                userActivityHandler.removeUserActivity(userId);
            }
            return true;
        } else {
            userActivityHandler.removeUserActivity(userId);
            return false;
        }
    }

    private boolean validateUserActivity(UserActivity userActivity) {
        LocalDateTime now = LocalDateTime.now();
        if (now.minusMinutes(20).isBefore(userActivity.getLastActivity())) {
            userActivity.setLastActivity(now);
            return true;
        }
        return false;
    }
}
