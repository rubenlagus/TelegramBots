package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

/**
 * immutable payload holder
 * @param <T>
 */
public class Payload<T> {

    private final T data;

    private Payload(T data) {
        this.data = data;
    }

    public T getData(){
        return data;
    }

    public static <T> Payload<T> of(T data){
        if (data == null) throw new IllegalArgumentException();

        return new Payload<>(data);
    }

}
