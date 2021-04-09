package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import java.util.HashMap;
import java.util.Map;

public class PayloadStorage {

    private final Map<Class<?>, Payload<?>> payloads = new HashMap<>();

    /**
     * @param clazz payload data class using for identify payload
     * @param payload instance of payload with data
     */
    public void addPayload(Class<?> clazz, Payload<?> payload){
        if (payload.getData().getClass() != clazz) throw new IllegalArgumentException();

        payloads.put(clazz, payload);
    }

    /**
     * @param clazz identifier of payload
     * @return objects from payload by giving class
     */
    public Payload<?> getPayload(Class<?> clazz){
        return payloads.get(clazz);
    }

    public int getPayloadSize(){
        return payloads.size();
    }

    /**
     * delete all payloads
     */
    public void clearStorage(){
        payloads.clear();
    }

}
