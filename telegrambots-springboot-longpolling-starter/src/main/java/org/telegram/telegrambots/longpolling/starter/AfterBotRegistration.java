package org.telegram.telegrambots.longpolling.starter;

import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicated that the Method of a Class extending {@link SpringLongPollingBot} will be called after the bot was registered
 * If the Method has a single Parameter of type {@link BotSession}, the method get passed the bot session the bot was registered with
 * <br><br>
 * <p>The bot session passed is the ones returned by {@link TelegramBotsLongPollingApplication#registerBot(String, LongPollingUpdateConsumer)}</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterBotRegistration {}
