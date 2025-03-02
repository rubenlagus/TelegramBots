package org.telegram.telegrambots.mapping.starter.core;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.mapping.starter.annotation.BotRequestMapping;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class BotRequestProcessor implements BeanPostProcessor {

    private final Map<String, BotHandlerMethod> handlers = new HashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            BotRequestMapping annotation = AnnotationUtils.findAnnotation(method, BotRequestMapping.class);
            if (annotation != null) {
                for (String command : annotation.value()) {
                    handlers.put(command, new BotHandlerMethod(bean, method));
                    log.debug("✅ Registered command handler '{}': {}.{}", command, bean.getClass().getSimpleName(), method.getName());
                }
            }
        }
        return bean;
    }

    public void handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText().trim();
            BotHandlerMethod handlerMethod = handlers.get(text);
            if (handlerMethod != null) {
                try {
                    handlerMethod.invoke(update);
                } catch (Exception e) {
                    log.error("Error on calling handler '{}': {}", text, e.getMessage(), e);
                }
            } else {
                log.warn("No handler found for: {}", text);
            }
        }
    }

    private record BotHandlerMethod(Object bean, Method method) {

        void invoke(Update update) throws Exception {
                method.invoke(bean, update);
            }
        }
}
