package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.annotations;

import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Stage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * indicate that interceptor must execute lastly
 * if more one interceptor annotated order not defined
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Last {

    /**
     * specifies to apply the given annotation to the method before or after
     */
    Stage stage();

}
