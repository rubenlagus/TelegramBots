package org.telegram.telegrambots.starter.Annotation;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation defines to which class of the bot will this class be registered
 * Annotated class must extend {@link BotCommand}
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface TelegramCommand {

    Class<? extends TelegramLongPollingCommandBot> commandBot();

}
