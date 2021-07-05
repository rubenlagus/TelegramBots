package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations.ContentTypeFilterInterceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations.DurationLoggerInterceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations.RegexFilterInterceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations.UserExtractInterceptor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

/**
 * default methods implementation
 *
 * example:
 * <pre>
 *     <code>
 *         // UserExtractInterceptor required
 *         public class ExampleInterceptor extend Interceptor{
 *              @Override
 *              public boolean before(Update update, PayloadStorage storage){
 *                  User user = (User)storage.getPayload(User.class).getData();
 *                  if (user.getUserName().equals("alice")) return interrupt();
 *
 *                  return process();
 *              }
 *         }
 *     </code>
 * </pre>
 *
 * see build in interceptor implementations
 * @see ContentTypeFilterInterceptor
 * @see DurationLoggerInterceptor
 * @see RegexFilterInterceptor
 * @see UserExtractInterceptor
 */
public abstract class Interceptor implements IInterceptor {

    private List<Object> sentMessages;

    /**
     * @return {@link IInterceptor#INTERRUPT}
     */
    public final boolean interrupt(){
        return INTERRUPT;
    }

    /**
     * @return {@link IInterceptor#PROCESS}
     */
    public final boolean process(){
        return PROCESS;
    }

    public boolean before(Update update, PayloadStorage storage){
        return process();
    }

    public void after(Update update, PayloadStorage storage){ }

    @Override
    public final void setSentMessages(List<Object> messages) {
        this.sentMessages = messages;
    }


    /**
     * @param clazz the class of all instances to get
     * @param <T> must be extend {@link PartialBotApiMethod} or {@link BotApiMethod}
     * @throws FeatureDisable if saving sent feature not enabled
     * @return list of sent objects of a given class
     */
    public final <T> List<T> getSendMessages(Class<T> clazz){
        checkFeatureEnable();

        if (BotApiMethod.class.isAssignableFrom(clazz) || PartialBotApiMethod.class.isAssignableFrom(clazz)) {
            //noinspection unchecked
            return sentMessages.
                    stream().
                    filter(it -> it.getClass() == clazz).
                    map(it -> (T) it).
                    collect(Collectors.toList());
        }
        throw new IllegalArgumentException(String.format("class %s is not api methods class " +
                "(not extend BotApiMethod.class or PartialBotApiMethod.class)", clazz));
    }

    public final void checkFeatureEnable(){
        if (sentMessages == null) throw new FeatureDisable();
    }
}
