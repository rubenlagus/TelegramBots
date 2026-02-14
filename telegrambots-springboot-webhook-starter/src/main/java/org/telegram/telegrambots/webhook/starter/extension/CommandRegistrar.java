package org.telegram.telegrambots.webhook.starter.extension;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;

import java.util.Map;

/**
 * Startup registrar that discovers all beans annotated with {@link Command}, validates them,
 * adapts them to {@link IBotCommand}, and registers them in target {@link ICommandRegistry}.
 * <p>
 * Validation rules:
 * <p>
 * - annotated bean must implement {@link ICommand}
 * - target bot registry must exist for {@link Command#bot()}
 * - annotation metadata must be resolvable for discovered bean
 *
 * @author Petrov Makariy (makariyp)
 */
@RequiredArgsConstructor
public class CommandRegistrar implements SmartInitializingSingleton {
    private final ListableBeanFactory beanFactory;
    private final Map<String, ICommandRegistry> registries;

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> commandBeans = beanFactory.getBeansWithAnnotation(Command.class);
        for (Map.Entry<String, Object> bean : commandBeans.entrySet()) {
            String beanName = bean.getKey();
            Object beanInstance = bean.getValue();
            Command meta = beanFactory.findAnnotationOnBean(beanName, Command.class);
            if (meta == null) {
                throw new IllegalStateException("@Command metadata not found for bean: " + beanName);
            }
            if (!(beanInstance instanceof ICommand beanCommand)) {
                throw new IllegalStateException("Bean " + beanName + " is annotated with @Command but does not implement ICommand");
            }
            ICommandRegistry registry = registries.get(meta.bot());
            if (registry == null) {
                throw new IllegalStateException("No ICommandRegistry bean found for bot='" + meta.bot() + "'");
            }
            IBotCommand iBotCommand = new AnnotationBackedBotCommand(meta, beanCommand);
            registry.register(iBotCommand);
        }
    }
}