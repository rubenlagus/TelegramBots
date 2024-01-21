package org.telegram.telegrambots.longpolling.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultGetUpdatesGenerator implements Function<Integer, GetUpdates> {
    private static final int GET_UPDATES_LIMIT = 1;
    private static final int GET_UPDATES_TIMEOUT = 1;

    private List<String> allowedUpdates = new ArrayList<>();

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
