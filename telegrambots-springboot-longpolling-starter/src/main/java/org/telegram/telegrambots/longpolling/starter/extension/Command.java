package org.telegram.telegrambots.longpolling.starter.extension;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declares a Spring-managed Telegram command bean that will be auto-registered in command registry on startup.
 * <p>
 * The annotated class must implement {@link ICommand}. Command metadata is used to build an {@code IBotCommand}
 * adapter automatically, so users only implement message processing logic.
 *
 * <p>Example:
 * <pre>{@code
 * @Command(command = "start", description = "Start command", bot = "myWebhookBot")
 * public class StartCommand implements ICommand {
 *     @Override
 *     public void processMessage(TelegramClient telegramClient, Message message, String[] arguments) {
 *         // handle /start
 *     }
 * }
 * }</pre>
 *
 * @author Petrov Makariy (makariyp)
 */
@Component
@Target(TYPE)
@Retention(RUNTIME)
public @interface Command {
    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";
    String command();
    String description() default "";

    /**
     * bot bean name
     */
    String bot();
}
