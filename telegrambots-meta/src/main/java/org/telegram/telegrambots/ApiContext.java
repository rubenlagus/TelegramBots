package org.telegram.telegrambots;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.telegram.telegrambots.logging.BotLogger;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class ApiContext {
    private static final Object lock = new Object();
    private static Injector INJECTOR;
    private static Map<Class, Class> bindings = new HashMap<>();
    private static Map<Class, Class> singletonBindings = new HashMap<>();

    public static <T> T getInstance(Class<T> type) {
        return getInjector().getInstance(type);
    }

    public static <T, S extends T> void register(Class<T> type, Class<S> implementation) {
        if (bindings.containsKey(type)) {
            BotLogger.debug("ApiContext", MessageFormat.format("Class {0} already registered", type.getName()));
        }
        bindings.put(type, implementation);
    }

    public static <T, S extends T> void registerSingleton(Class<T> type, Class<S> implementation) {
        if (singletonBindings.containsKey(type)) {
            BotLogger.debug("ApiContext", MessageFormat.format("Class {0} already registered", type.getName()));
        }
        singletonBindings.put(type, implementation);
    }

    private static Injector getInjector() {
        if (INJECTOR == null) {
            synchronized (lock) {
                if (INJECTOR == null) {
                    INJECTOR = Guice.createInjector(new ApiModule());
                }
            }
        }
        return INJECTOR;
    }

    @SuppressWarnings("unchecked")
    private static class ApiModule extends AbstractModule {
        @Override
        protected void configure() {
            for (Map.Entry<Class, Class> binding : bindings.entrySet()) {
                bind(binding.getKey()).to(binding.getValue());
            }
            for (Map.Entry<Class, Class> binding : singletonBindings.entrySet()) {
                bind(binding.getKey()).to(binding.getValue()).in(Singleton.class);
            }
        }
    }
}
