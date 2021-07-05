package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.annotations.First;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.annotations.Last;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InterceptorRegistry implements IInterceptorsRegistry {

    private final List<IInterceptor> interceptors = new ArrayList<>();
    private final PayloadStorage payloadStorage = new PayloadStorage();

    @Override
    public void registerInterceptor(IInterceptor iInterceptor) {
        interceptors.add(iInterceptor);
    }

    @Override
    public boolean callBeforeInterceptor(Update update, PayloadStorage storage) {
        for (IInterceptor interceptor : getFirstAnnotatedInterceptor(Stage.BEFORE)){
            if (interceptor.before(update, storage)) return true;
        }
        for (IInterceptor interceptor : getInterceptorWithoutAnnotation()) {
            if (interceptor.before(update, storage)) return true;
        }
        for (IInterceptor interceptor : getLastAnnotation(Stage.BEFORE)){
            if (interceptor.before(update, storage)) return true;
        }

        return false;
    }

    @Override
    public void callAfterInterceptor(Update update, PayloadStorage storage, List<Object> sentMessages) {
        for (IInterceptor interceptor : getFirstAnnotatedInterceptor(Stage.AFTER)){
            interceptor.setSentMessages(sentMessages);
            interceptor.after(update, storage);
        }
        for (IInterceptor interceptor : getInterceptorWithoutAnnotation()){
            interceptor.setSentMessages(sentMessages);
            interceptor.after(update, storage);
        }
        for (IInterceptor interceptor : getLastAnnotation(Stage.AFTER)){
            interceptor.setSentMessages(sentMessages);
            interceptor.after(update, storage);
        }
    }

    public List<IInterceptor> getInterceptorWithoutAnnotation(){
        return interceptors.stream().
                filter(it -> it.getClass().getAnnotation(First.class) == null).
                filter(it -> it.getClass().getAnnotation(Last.class) == null).
                collect(Collectors.toList());
    }

    public List<IInterceptor> getFirstAnnotatedInterceptor(Stage stage){
        return interceptors.
                stream().
                filter(it -> it.getClass().getAnnotation(First.class) != null).
                filter(it -> it.getClass().getAnnotation(First.class).stage() == stage ||
                        it.getClass().getAnnotation(First.class).stage() == Stage.BOTH).
                collect(Collectors.toList());
    }

    public List<IInterceptor> getLastAnnotation(Stage stage){
        return interceptors.
                stream().
                filter(it -> it.getClass().getAnnotation(Last.class) != null).
                filter(it -> it.getClass().getAnnotation(Last.class).stage() == stage ||
                        it.getClass().getAnnotation(Last.class).stage() == Stage.BOTH).
                collect(Collectors.toList());
    }

    @Override
    public PayloadStorage getPayloadStorage() {
        return payloadStorage;
    }

    @Override
    public void clearStorage() {
        payloadStorage.clearStorage();
    }
}
