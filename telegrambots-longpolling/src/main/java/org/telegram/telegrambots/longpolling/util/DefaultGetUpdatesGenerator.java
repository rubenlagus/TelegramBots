package org.telegram.telegrambots.longpolling.util;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;

import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
public class DefaultGetUpdatesGenerator implements Function<Integer, GetUpdates> {

    public static final DefaultGetUpdatesGenerator INSTANCE = new DefaultGetUpdatesGenerator(List.of());

    private static final int GET_UPDATES_LIMIT = 100;
    private static final int GET_UPDATES_TIMEOUT = 50;

    private final List<String> allowedUpdates;

    @Override
    public GetUpdates apply(Integer lastReceivedUpdate) {
        return GetUpdates
                .builder()
                .limit(GET_UPDATES_LIMIT)
                .timeout(GET_UPDATES_TIMEOUT)
                .offset(lastReceivedUpdate + 1)
                .allowedUpdates(allowedUpdates)
                .build();
    }
}
