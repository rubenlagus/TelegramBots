package org.telegram.telegrambots.starter;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicated that the Method of a Class extending {@link LongPollingBot} will be called after the bot was registered
 * If the Method has a single Parameter of type {@link BotSession}, the method get passed the bot session the bot was registered with
 * <br><br>
 * <p>The bot session passed is the ones returned by {@link TelegramBotsApi#registerBot(LongPollingBot)}</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterBotRegistration {}
