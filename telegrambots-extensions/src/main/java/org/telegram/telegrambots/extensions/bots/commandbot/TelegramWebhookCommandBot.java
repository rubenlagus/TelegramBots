package org.telegram.telegrambots.extensions.bots.commandbot;


import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.CommandState;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.UserActivity;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.UserActivityHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This class adds command functionality to the TelegramWebhookBot
 *
 * @author Andrey Korsakov (loolzaaa)
 */
public abstract class TelegramWebhookCommandBot extends TelegramWebhookBot implements CommandBot, ICommandRegistry {

    private final UserActivityHandler userActivityHandler;
    private final CommandRegistry commandRegistry;

    private int userInactivityMaxTime = 20;

    /**
     * Creates a TelegramWebhookCommandBot using default options
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public TelegramWebhookCommandBot() {
        this(new DefaultBotOptions());
    }

    /**
     * Creates a TelegramWebhookCommandBot with custom options and allowing commands with
     * usernames
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options Bot options
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public TelegramWebhookCommandBot(DefaultBotOptions options) {
        this(options, true);
    }

    /**
     * Creates a TelegramWebhookCommandBot
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options                   Bot options
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public TelegramWebhookCommandBot(DefaultBotOptions options, boolean allowCommandsWithUsername) {
        super(options);
        this.userActivityHandler = null;
        this.commandRegistry = new CommandRegistry(allowCommandsWithUsername, this::getBotUsername);
    }

    /**
     * Creates a TelegramWebhookCommandBot using default options
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param botToken the telegram api token
     */
    public TelegramWebhookCommandBot(String botToken) {
        this(new DefaultBotOptions(), botToken);
    }

    /**
     * Creates a TelegramWebhookCommandBot with custom options and allowing commands with
     * usernames
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options  Bot options
     * @param botToken the telegram api token
     */
    public TelegramWebhookCommandBot(DefaultBotOptions options, String botToken) {
        this(options, true, botToken);
    }

    /**
     * Creates a TelegramWebhookCommandBot
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options                   Bot options
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     * @param botToken                  the telegram api token
     */
    public TelegramWebhookCommandBot(DefaultBotOptions options, boolean allowCommandsWithUsername, String botToken) {
        this(options, allowCommandsWithUsername, botToken, null);
    }

    /**
     * Creates a TelegramWebhookCommandBot with custom options,
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
    public TelegramWebhookCommandBot(DefaultBotOptions options,
                                     boolean allowCommandsWithUsername,
                                     String botToken,
                                     UserActivityHandler userActivityHandler) {
        super(options, botToken);
        this.userActivityHandler = userActivityHandler;
        this.commandRegistry = new CommandRegistry(allowCommandsWithUsername, this::getBotUsername);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        // Only one (Message/CallbackQuery) can be present in any given update
        // https://core.telegram.org/bots/api#update
        if (update.hasMessage() || update.hasCallbackQuery()) {
            Message message = update.getMessage();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (message != null && !filter(message)) {
                if (message.isCommand()) {
                    if (userActivityHandler == null) {
                        executeStatelessCommand(update, message);
                    } else {
                        executeStatefulCommand(update, message);
                    }
                    return null;
                } else if (userActivityHandler != null) {
                    continueStatefulCommandWithMessage(update, message);
                    return null;
                }
            } else if (callbackQuery != null && callbackQuery.getMessage() != null && userActivityHandler != null) {
                continueStatefulCommandWithCallbackQuery(update, callbackQuery);
                return null;
            }
        }
        processNonCommandUpdate(update);
        return null;
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

    public void setUserInactivityMaxTime(int userInactivityMaxTime) {
        this.userInactivityMaxTime = userInactivityMaxTime;
    }

    private void executeStatelessCommand(Update update, Message message) {
        if (!commandRegistry.executeCommand(this, message)) {
            processInvalidCommandUpdate(update);
        }
    }

    private void executeStatefulCommand(Update update, Message message) {
        long userId = message.getFrom().getId();
        UserActivity userActivity = new UserActivity();
        userActivityHandler.saveUserActivity(userId, userActivity);
        CommandState<?> resultState = commandRegistry.executeCommand(this, message, userActivity.getCommandState());
        if (!checkCommandState(resultState, userId, userActivity)) {
            processInvalidCommandUpdate(update);
        }
    }

    private void continueStatefulCommandWithMessage(Update update, Message message) {
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
    }

    private void continueStatefulCommandWithCallbackQuery(Update update, CallbackQuery callbackQuery) {
        long userId = callbackQuery.getFrom().getId();
        UserActivity userActivity = userActivityHandler.loadUserActivity(userId);
        if (userActivity != null && validateUserActivity(userActivity)) {
            CommandState<?> resultState = commandRegistry.executeCommand(this, callbackQuery, userActivity.getCommandState());
            if (!checkCommandState(resultState, userId, userActivity)) {
                processInvalidCommandUpdate(update);
            }
        } else {
            processInvalidCommandUpdate(update);
        }
    }

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
        if (now.minusMinutes(userInactivityMaxTime).isBefore(userActivity.getLastActivity())) {
            userActivity.setLastActivity(now);
            return true;
        }
        return false;
    }
}
